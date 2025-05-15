package com.hulzzuk.user.model.service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.KakaoUserVO;
import com.hulzzuk.user.model.vo.NaverUserVO;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Service("socialLoginService")
public class SocialLoginServiceImpl implements SocialLoginService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;

	@Autowired
	private KakaoLoginAuth kakaoLoginAuth;
	
	// 카카오 연동 객체 자동 생성
	@Autowired
	public void setKakaoLoginAuth(KakaoLoginAuth kakaoLoginAuth) {
		this.kakaoLoginAuth = kakaoLoginAuth;
	}
	
	@Autowired
	private NaverLoginAuth naverLoginAuth;
	//private String apiResult = null;

	// 네이버 연동 객체 자동 생성
	@Autowired
	private void setNaverLoginAuth(NaverLoginAuth naverLoginAuth) {
		this.naverLoginAuth = naverLoginAuth;
	}
		
	// 카카오 토큰 발급 요청
	@Override
	public String getKakaoAccessToken(String code) {
	    JsonNode tokenNode = kakaoLoginAuth.getKakaoAccessToken(code);

	    return tokenNode.path("access_token").asText();
	}
	
	// 카카오 소셜로그인
	@Override
	public ModelAndView kakaoLogin(String code, HttpSession session, ModelAndView mv){
		
		// 1. accessToken 가져오기
		String accessToken = getKakaoAccessToken(code);
		session.setAttribute("accessToken", accessToken);
		
		// 2. 사용자 정보 추출
		KakaoUserVO kakaoUser = kakaoLoginAuth.getKakaoUserInfo(accessToken);
		
		// 3. KakaoUserVO → UserVO 변환
	    UserVO user = kConvertToUserVO(kakaoUser);
		
	    // 4. 기존 회원 확인 -> 저장 (컨트롤러의 kakao.do 가 얘를 호출해. 호출하기 전에 accessToken이랑 사용자 정보 요청함. 이 메소드 안에서 1,2 결과 가져옴)
	    UserVO loginUser = userDao.selectUser(user.getUserId());
	    session.setAttribute("authUserId", user.getUserId());
	    
	    if (loginUser != null) {
	        // 로그인 처리
	        session.setAttribute("loginUser", loginUser);
	        session.setAttribute("authUserId", user.getUserId());
	        mv.setViewName("redirect:/main.do");
	    } else {
	        // 회원가입 처리 (간편 로그인 방식)
	        
	    	 int result = insertSocialUser(user);
	         if (result > 0) {
	             session.setAttribute("loginUser", user);
	 	         session.setAttribute("authUserId", user.getUserId());
	             mv.setViewName("redirect:/main.do");
	         } else {
	             mv.addObject("message", "회원가입 중 오류가 발생했습니다.");
	             mv.setViewName("common/errorPage");
	         }
	    }
		return mv;
	}
	
	// 네이버 토큰 발급 요청
	/*
	 * @Override public String getNaverAccessToken(HttpSession session, String code,
	 * String state) { OAuth2AccessToken token;
	 * 
	 * String accessToken = new String(); try { token =
	 * naverLoginAuth.getNaverAccessToken(session, code, state); if(token != null) {
	 * accessToken = token.getAccessToken(); } } catch (Exception e) {
	 * e.printStackTrace(); } return accessToken; }
	 */

	
	@Override
	public ModelAndView naverLogin(ModelAndView mv, HttpSession session, String code, String state) {
		try {
			// 1. oauthToken 가져오기
			OAuth2AccessToken oauthToken;
			oauthToken = naverLoginAuth.getNaverAccessToken(session, code, state);
			session.setAttribute("oauthToken", oauthToken);
			
			// 2. 사용자 정보 추출
			NaverUserVO naverUser = naverLoginAuth.getUserProfile(oauthToken); // String형식의 json데이터
	
			// 3. NaverUserVO → UserVO 변환
		    UserVO user = nConvertToUserVO(naverUser);
			
		    // 4. 기존 회원 확인 -> 저장 (컨트롤러의 naver.do 가 얘를 호출해. 호출하기 전에 accessToken이랑 사용자 정보 요청함. 이 메소드 안에서 1,2 결과 가져옴)
		    UserVO loginUser = userDao.selectUser(user.getUserId());
		    session.setAttribute("authUserId", user.getUserId());
		    
		    if (loginUser != null) {
		        // 로그인 처리
		        session.setAttribute("loginUser", loginUser);
		        session.setAttribute("authUserId", user.getUserId());
		        mv.setViewName("redirect:/main.do");
		    } else {
		        // 회원가입 처리 (간편 로그인 방식)
		        
		    	 int result = insertSocialUser(user);
		         if (result > 0) {
		             session.setAttribute("loginUser", user);
		 	         session.setAttribute("authUserId", user.getUserId());
		             mv.setViewName("redirect:/main.do");
		         } else {
		             mv.addObject("message", "회원가입 중 오류가 발생했습니다.");
		             mv.setViewName("common/errorPage");
		         }
		    }
	    } catch (Exception e) {
	        e.printStackTrace();  // 또는 log.error("Naver login failed", e);
	        mv.addObject("message", "네이버 로그인 중 오류가 발생했습니다.");
	        mv.setViewName("common/errorPage");
	    }
		return mv;
	}
	

	// 생일 변환
	private Date mapBirthday(String birthyear, String birthday) {
		
		if (!(birthyear.isBlank()) && !(birthday.isBlank())) {
	        try {
	            int year = Integer.parseInt(birthyear);
	            int month = Integer.parseInt(birthday.substring(0, 2));
	            int day = Integer.parseInt(birthday.substring(3, 5));

	            LocalDate localDate = LocalDate.of(year, month, day);
	            return Date.valueOf(localDate);  // 정상 변환
	        } catch (Exception e) {
	            // 생년월일 형식이 잘못되면 null 반환
	            return null;
	        }
	    }
	    return null;  // 입력값 부족 시 null
	}

	// KakaoUserVO -> UserVO 변환
	public UserVO kConvertToUserVO(KakaoUserVO kakaoUser) {
	    UserVO user = new UserVO();

	    // 닉네임 및 프로필
	    if (kakaoUser.getKakaoAccount() != null) {
	        KakaoUserVO.KakaoAccountVO kakao = kakaoUser.getKakaoAccount();
	        
	        String baseKNick = kakao.getProfile().getNickname();
	        if (baseKNick == null || baseKNick.isBlank()) {
	            baseKNick = "kakaoUser"; // 기본 닉네임 지정
	        }

	        String finalKNick = baseKNick;

	        // DB에 닉네임이 존재하는지 확인하고 중복될 경우 숫자 붙이기
	        int suffix = 1;
	        while (userDao.countNickName(finalKNick) > 0) {
	            finalKNick = baseKNick + String.format("%02d", suffix++);  // kakaoUser01, kakaoUser02 ...
	        }
	        user.setUserNick(finalKNick);
	        
	        user.setUserProfile(kakao.getProfile().getProfileImageUrl());
	        user.setUserId(kakao.getEmail());
	    }

	    // 키: 고유 식별자용으로 사용 가능
	    user.setUserKey("KAKAO_" + kakaoUser.getId());

	    // 소셜 로그인 경로
	    user.setUserPath("KAKAO");

	    // refresh_token은 accessToken 발급 시 별도로 저장 가능
	    user.setUserRefreshCode(null); // 필요시 저장

	    // 관리자 여부 기본 N
	    user.setAdminYN("N");

	    return user;
	}

	// NaverUserVO -> UserVO 변환
	public UserVO nConvertToUserVO(NaverUserVO naverUser) {
	    UserVO user = new UserVO();
	    NaverUserVO.ResponseVO naver = naverUser.getResponse();
	    // 닉네임 및 프로필
	    if (naverUser.getResponse() != null) {
	        
	        
	        String baseNNick = naver.getNickname();
	        if (baseNNick == null || baseNNick.isBlank()) {
	        	baseNNick = "naverUser"; // 기본 닉네임 지정
	        }

	        String finalNNick = baseNNick;

	        // DB에 닉네임이 존재하는지 확인하고 중복될 경우 숫자 붙이기
	        int suffix = 1;
	        while (userDao.countNickName(finalNNick) > 0) {
	        	finalNNick = baseNNick + String.format("%02d", suffix++);  // kakaoUser01, kakaoUser02 ...
	        }
	        user.setUserNick(finalNNick);
	        
	        user.setUserProfile(naver.getProfile_image());
	        user.setGender(naver.getGender());  
	        
	        String birthyear = naver.getBirthyear();
	        String birthday = naver.getBirthday();
	        user.setUserAge(mapBirthday(birthyear, birthday));  // 생일을 Date로 변환
	        
	        user.setUserId(naver.getEmail());
	    }

	    // 키: 고유 식별자용으로 사용 가능
	    user.setUserKey("NAVER_" + naver.getId());

	    // 소셜 로그인 경로
	    user.setUserPath("NAVER");

	    // refresh_token은 accessToken 발급 시 별도로 저장 가능
	    user.setUserRefreshCode(null); // 필요시 저장

	    // 관리자 여부 기본 N
	    user.setAdminYN("N");

	    return user;
	}
	

	// 소셜 로그인 회원가입
	public int insertSocialUser(UserVO user) {
		return userDao.insertSocialUser(user);
	}
}
