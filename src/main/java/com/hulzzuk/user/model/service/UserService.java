package com.hulzzuk.user.model.service;

import java.net.http.HttpRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface UserService {
	// 내 정보 보기
	ModelAndView selectUser(ModelAndView mv, String userId);
	
	// 로그인
	ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status);
	
	// 로그아웃
	ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status);

	// 이메일 인증
	ModelAndView sendMailMethod(ModelAndView mv, HttpSession session, HttpServletRequest request,
			String userId, int width, int height);
	
	// 인증번호 검증
	ModelAndView verifyCode(String inputCode, String userId,
            ModelAndView mv, HttpSession session);
	
}
