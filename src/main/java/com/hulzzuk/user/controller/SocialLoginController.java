package com.hulzzuk.user.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.hulzzuk.user.model.service.SocialLoginService;
import com.hulzzuk.user.model.service.UserService;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("auth")
public class SocialLoginController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private SocialLoginService socialLoginService;

	@Autowired
	private KakaoLoginAuth kakaoLoginAuth;

	@Autowired
	public void setKakaoLoginAuth(KakaoLoginAuth kakaoLoginAuth) {
		this.kakaoLoginAuth = kakaoLoginAuth;
	}

	/*
	 * @Autowired private NaverLoginAuth naverLoginAuth; private String apiResult =
	 * null;
	 * 
	 * @Autowired private void setNaverLoginAuth(NaverLoginAuth naverLoginAuth) {
	 * this.naverLoginAuth = naverLoginAuth; }
	 */

	// step1의 6번에서 인가코드를 자동으로 받음 -> 근데 얘 저장 안 해도 됨
	// access 토큰을 발급하는 메소드 (step2-1번)
	@RequestMapping(value = "kakao.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpSession session, ModelAndView mv) {
		return socialLoginService.kakaoLogin(code, session, mv);
	}

	// 카카오
	/*
	 * @RequestMapping(value = "kcallback.do", produces = "application/json", method
	 * = { RequestMethod.GET, RequestMethod.POST }) public ModelAndView
	 * kakaoLogin(@RequestParam String code, ModelAndView mv, HttpSession session)
	 * throws Exception { return mv; }
	 */// end kakaoLogin()

	/*
	 * @RequestMapping(value = "/ncallback.do", method = { RequestMethod.GET,
	 * RequestMethod.POST }) public String naverCallback(ModelAndView
	 * mv, @RequestParam String code, @RequestParam String state, HttpSession
	 * session) throws IOException, ParseException, InterruptedException,
	 * ExecutionException { System.out.println("네이버 로그인성공!"); OAuth2AccessToken
	 * oauthToken; oauthToken = naverLoginAuth.getAccessToken(session, code, state);
	 * 
	 * // 1. 로그인 사용자 정보를 읽어온다. apiResult =
	 * naverLoginAuth.getUserProfile(oauthToken); // String형식의 json데이터
	 * 
	 * // 2. String형식인 apiResult를 json형태로 바꿈 JSONParser parser = new JSONParser();
	 * Object obj = parser.parse(apiResult); JSONObject jsonObj = (JSONObject) obj;
	 * 
	 * // 3. 데이터 파싱 JSONObject response_obj = (JSONObject) jsonObj.get("response");
	 * 
	 * UserVO user = new UserVO();
	 * 
	 * user.setUserId((String) response_obj.get("email")); user.setUserNick((String)
	 * response_obj.get("nickname")); // user.setUserProfile((String)
	 * response_obj.get("profile_image")); // user.setUserPath("naver");
	 * 
	 * int result1 = userService.insertSocialUser(user);
	 * 
	 * user = userService.selectSocialUser(user.getUserId());
	 * 
	 * return "redirect:/main.do"; }
	 */

	// 로그아웃 하기 ( 카카오, 네이버 로그아웃 토큰 만료로 처리 안함)
	/*
	 * @RequestMapping("testlogout.do") public String
	 * logoutMethod(HttpServletRequest request, ModelAndView mv) { HttpSession
	 * session = request.getSession(false);// 있는 세션 가져오기
	 * 
	 * if (session != null) {// 로그인 상태면 String sns = (String)
	 * session.getAttribute("sns"); String accessToken =
	 * (String)session.getAttribute("access_token"); if (sns != null) { switch (sns)
	 * { case "kakao": this.kakaoLoginAuth.logOut(accessToken);
	 * //this.kakaoLoginAuth.disconnect(accessToken); break;
	 * 
	 * case "naver": this.naverLoginAuth.logOut(accessToken); break;
	 * 
	 * } } session.invalidate(); return "redirect:/main.do"; } else {// 이미 로그아웃 된 상태
	 * mv.addObject("message", "로그인한 상태가 아닙니다"); return "common/error"; } }
	 */
}
