package com.hulzzuk.user.model.service;


import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpSession;

public interface SocialLoginService {
	// 카카오 소셜 로그인
	ModelAndView kakaoLogin(String code, HttpSession session, ModelAndView mv);

	// 네이버 소셜 로그인
	ModelAndView naverLogin(ModelAndView mv, HttpSession session, String code, String state);
	
	// 소셜 로그인 회원 가입
	int insertSocialUser(UserVO newUser);

	// 카카오 access 토큰 요청
	String getKakaoAccessToken(String code);
	
	// 네이버 access 토큰 요청
	// String getNaverAccessToken(HttpSession session, String code, String state);
}
