package com.hulzzuk.user.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.hulzzuk.user.controller.KakaoLoginAuth;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.SocialUserVO;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Service("socialLoginService")
public class SocialLoginServiceImpl implements SocialLoginService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;

	@Autowired
	private KakaoLoginAuth kakaoLoginAuth;
	
	// 카카오 토큰 발급 요청
	@Override
	public String getAccessToken(String code) {
	    JsonNode tokenNode = kakaoLoginAuth.getAccessToken(code);
	    return tokenNode.path("access_token").asText();
	}
	
	/*
	 * // 카카오 사용자 정보 요청
	 * 
	 * @Override public Map<String, String> getUserInfo(String accessToken) {
	 * JsonNode userInfo = kakaoLoginAuth.getKakaoUserInfo(accessToken);
	 * 
	 * String kakaoId = userInfo.path("id").asText(); JsonNode properties =
	 * userInfo.path("properties"); JsonNode account =
	 * userInfo.path("kakao_account");
	 * 
	 * String nickname = properties.path("nickname").asText(); String email =
	 * account.path("email").asText(""); String profile_image =
	 * properties.path("profile").asText();
	 * 
	 * Map<String, String> info = new HashMap<>(); info.put("userId", email);
	 * info.put("nickname", nickname); info.put("profile_image", profile_image);
	 * return info; }
	 */

	
	// 카카오 소셜로그인
	@Override
	public ModelAndView kakaoLogin(String code, HttpSession session, ModelAndView mv){
		
		// 1. accessToken 발급
		String accessToken = getAccessToken(code);
		session.setAttribute("accessToken", accessToken);
		
		// 2. 사용자 정보 추출
		SocialUserVO socialUser = kakaoLoginAuth.getKakaoUserInfo(accessToken);
		
		// 3. SocialUserVO → UserVO 변환
	    UserVO user = convertToUserVO(socialUser);
		
	    // 4. 기존 회원 확인 -> 저장 (컨트롤러의 kakao.do 가 얘를 호출해. 호출하기 전에 accessToken이랑 사용자 정보 요청함. 이 메소드 안에서 1,2 결과 가져옴)
	    UserVO loginUser = userDao.selectUser(user.getUserId());
	    session.setAttribute("authUserId", user.getUserId());
	    
	    if (loginUser != null) {
	        // 로그인 처리
	        session.setAttribute("loginUser", loginUser);
	        mv.setViewName("redirect:/main.do");
	    } else {
	        // 회원가입 처리 (간편 로그인 방식)
	        
	    	 int result = insertSocialUser(user);
	         if (result > 0) {
	             session.setAttribute("loginUser", user);
	             mv.setViewName("redirect:/main.do");
	         } else {
	             mv.addObject("message", "회원가입 중 오류가 발생했습니다.");
	             mv.setViewName("common/errorPage");
	         }
	    }
		return mv;
	}
	
	// 성별 변환
	private String mapGender(String gender) {
		String kGender = new String();
	    if ("male".equalsIgnoreCase(gender)) { 
	    	kGender = "M";
	    }
	    else if ("female".equalsIgnoreCase(gender)) {
	    	kGender = "F";
	    }
	    return kGender;
	}

	// 생일 변환
	private Date mapBirthday(String birthyear, String birthday) {
		
		if (birthyear != null && birthday != null && birthday.length() == 4) {
	        try {
	            int year = Integer.parseInt(birthyear);
	            int month = Integer.parseInt(birthday.substring(0, 2));
	            int day = Integer.parseInt(birthday.substring(2, 4));

	            LocalDate localDate = LocalDate.of(year, month, day);
	            return Date.valueOf(localDate);  // 정상 변환
	        } catch (Exception e) {
	            // 생년월일 형식이 잘못되면 null 반환
	            return null;
	        }
	    }
	    return null;  // 입력값 부족 시 null
	}

	// SocialUserVO -> UserVO 변환
	public UserVO convertToUserVO(SocialUserVO socialUser) {
	    UserVO user = new UserVO();

	    // 닉네임 및 프로필
	    if (socialUser.getKakaoAccount() != null) {
	        SocialUserVO.KakaoAccountVO kakao = socialUser.getKakaoAccount();
	        
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
	    //    user.setGender(mapGender(kakao.getGender()));  // "male" → "M", "female" → "F"
	    //    String birthyear = kakao.getBirthyear();
	     //   String birthday = kakao.getBirthday();
	     //   user.setUserAge(mapBirthday(birthyear, birthday));  // 생일을 Date로 변환
	        user.setUserId(kakao.getEmail());
	    }

	    // 키: 고유 식별자용으로 사용 가능
	    user.setUserKey("KAKAO_" + socialUser.getId());

	    // 소셜 로그인 경로
	    user.setUserPath("kakao");

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
