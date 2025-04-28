package com.hulzzuk.user.model.service;

import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpSession;

public interface UserService {
	// 내 정보 보기
	ModelAndView selectUser(ModelAndView mv, String userId);
	
	// 로그인
	ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status);
	
	// 로그아웃
	ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status);
	
}
