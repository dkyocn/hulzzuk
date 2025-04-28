package com.hulzzuk.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.service.UserService;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	// 마이 페이지
	@RequestMapping(value = "select.do", method = RequestMethod.GET)
	public ModelAndView userDetailMethod(ModelAndView mv, @RequestParam("userId") String userId) {
	    return userService.selectUser(mv, userId);
	}
	
	// 로그인 페이지 이동
	@RequestMapping(value = "login.do", method = RequestMethod.GET) 
	public ModelAndView moveLoginPage(ModelAndView mv) {
		
		mv.addObject("fail", "N");
		mv.setViewName("user/loginPage");
		
		return mv; 
	}
	
	// 로그인 처리
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status) {
		return userService.loginMethod(mv, user, session, status);
	}
	
	// 로그아웃 처리
	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status) {
		return userService.logoutMethod(mv, session, status);
	}
	
	
}












