package com.hulzzuk.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	// 내 정보 보기 페이지
	@RequestMapping(value = "select.do", method = RequestMethod.GET, produces = "application/json; charset:UTF-8")
	public ModelAndView userDetailMethod(@RequestParam("userId") String userId) {
		
	    return userService.selectUser(userId);
	}
}
