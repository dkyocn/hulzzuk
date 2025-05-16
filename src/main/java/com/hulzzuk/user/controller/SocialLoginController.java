package com.hulzzuk.user.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.service.SocialLoginService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("auth")
public class SocialLoginController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private SocialLoginService socialLoginService;

	// step1의 6번에서 인가코드를 자동으로 받음 -> 근데 얘 저장 안 해도 됨
	// access 토큰을 발급하는 메소드 (step2-1번)
	@RequestMapping(value = "kakao.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpSession session, ModelAndView mv) {
		return socialLoginService.kakaoLogin(code, session, mv);
	}

	
	@RequestMapping(value = "naver.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView naverLogin(ModelAndView mv, HttpSession session, 
			@RequestParam("code") String code, @RequestParam("state") String state) {
		return socialLoginService.naverLogin(mv, session, code, state);
	}
	
	
	
}
