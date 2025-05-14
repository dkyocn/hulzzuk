package com.hulzzuk.user.model.service;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpSession;

public interface SocialLoginService {
	// 카카오 소셜 로그인
	ModelAndView kakaoLogin(String code, HttpSession session, ModelAndView mv);

	// 소셜 로그인 회원 가입
	int insertSocialUser(UserVO newUser);

	// access 토큰 요청
	String getAccessToken(String code);
}
