package com.hulzzuk.user.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.service.UserService;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	// 로그인 페이지 이동
	@RequestMapping(value = "login.do", method = RequestMethod.GET) 
	public ModelAndView moveLoginPage(ModelAndView mv) {
		
		mv.setViewName("user/loginPage");
		
		return mv; 
	}
	
	// 로그인 처리
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status) {
		return userService.loginMethod(mv, user, session, status);
	}
	
	// 로그아웃 처리
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	public ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status) {
		return userService.logoutMethod(mv, session, status);
	}
	
	// 이메일 인증번호 페이지 이동
	@RequestMapping(value = "moveSendMail.do")
	public ModelAndView moveSendMailMethod(ModelAndView mv, HttpSession session) {
	    String userId = (String) session.getAttribute("authUserId");
	    mv.addObject("userId", userId); // 이걸 JSP에 넘김
		
		mv.setViewName("user/mail");
		
		return mv;
	}
	
	// 이메일 인증번호 받기
	@RequestMapping(value = "sendMail.do")
	public ModelAndView sendMailMethod(ModelAndView mv, HttpSession session, HttpServletRequest request,
			  @RequestParam(name = "mode") String mode,
			  @RequestParam(name = "userId") String userId,
              @RequestParam(name = "width") String width,
              @RequestParam(name = "height") String  height) {
		
		return userService.sendMailMethod(mv, session, request, mode, userId, Integer.parseInt(width), Integer.parseInt(height));
	}
	
	// 인증번호 검증 처리
	@RequestMapping(value = "verifyCode.do", method = RequestMethod.POST)
	public ModelAndView verifyCode(
			@RequestParam("mode") String mode,
			@RequestParam("inputCode") String inputCode,
			@RequestParam("userId") String userId,
            ModelAndView mv, HttpSession session, HttpServletRequest request) {
		
		return userService.verifyCode(mode, inputCode, userId, mv, session, request);
	}
	
	// 인증번호 검증 팝업 확인버튼 동작 (확인 누르면 postPopUp.jsp의 함수에 success값 전달하여 window.close() 실행됨)
	@RequestMapping(value = "verifyPopUp.do", method = RequestMethod.POST)
	@ResponseBody
	public String verifyPopUpMethod() {
		return "success";
	}
	
	 // 비밀번호 유효성 검사
	 @RequestMapping(value = "pwdValidate.do", method = RequestMethod.POST) 
	 public ModelAndView pwdValidateMethod(ModelAndView mv, @RequestParam("userPwd") String userPwd) {
		 return mv;
	}
 
	// 비밀번호 일치 확인 메소드
	@RequestMapping(value = "pwdConfirm.do", method = RequestMethod.POST)
	public ModelAndView pwdConfirmMethod(ModelAndView mv, HttpSession session, HttpServletRequest request, 
			@RequestParam("userPwd") String userPwd,
			@RequestParam("pwdConfirm") String pwdConfirm) {
		return userService.pwdConfirmMethod(mv, session, request, userPwd, pwdConfirm);
	}
	
	// 비밀번호 일치 확인 팝업 확인버튼 동작 (확인 누르면 postPopUp.jsp의 함수에 success값 전달하여 window.close() 실행됨)
	@RequestMapping(value = "pwdConfirmPopUp.do", method = RequestMethod.POST)
	@ResponseBody
	public String pwdConfirmPopUpMethod() {
		return "success";
	}
	
	// 비밀번호 재설정
	@RequestMapping(value = "pwdUpdate.do", method = RequestMethod.POST)
	public ModelAndView pwdUpdateMethod(ModelAndView mv, HttpSession session, HttpServletRequest request, 
			@RequestParam("userPwd") String userPwd) {
		return userService.pwdUpdateMethod(mv, session, request, userPwd);
	}
	
	// 비밀번호 재설정 팝업 확인버튼 동작
	@RequestMapping(value = "pwdUpdatePopUp.do", method = RequestMethod.POST)
	@ResponseBody
	public String pwdUpdatePopUpMethod(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return "success|" + contextPath + "/user/login.do";
	}
		
	// 마이 페이지
	@RequestMapping(value = "select.do")
	public ModelAndView userDetailMethod(ModelAndView mv, @RequestParam("userId") String userId) {
	    return userService.selectUser(mv, userId);
	}
	
	// 회원 가입 페이지 이동
	@RequestMapping(value = "moveJoin.do", method = RequestMethod.GET) 
	public ModelAndView moveJoinPage(ModelAndView mv, UserVO user) {
		
		mv.setViewName("user/joinPage");
		
		return mv; 
	}
	
	// 회원 가입
	@RequestMapping(value = "join.do", method = RequestMethod.POST)
	public ModelAndView insertUser(ModelAndView mv, HttpServletRequest request, UserVO user) {
		return userService.insertUser(mv, request, user);
	}
	
	// 회원 가입 팝업 확인버튼 동작
	@RequestMapping(value = "userJoinPopUp.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertUserPopUpMethod(HttpServletRequest request) {
		HttpSession session = request.getSession();
	    Boolean joinSuccess = (Boolean) session.getAttribute("joinSuccess");

	    // 사용 후 제거 (중복 방지)
	    session.removeAttribute("joinSuccess");

	    String contextPath = request.getContextPath();
	    if (Boolean.TRUE.equals(joinSuccess)) {
	        return "success|" + contextPath + "/user/login.do";  // 로그인 이동
	    } else {
	        return "success";  // 그냥 닫기
	    }
	}
	
	// 회원 탈퇴
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	public ModelAndView deleteUser(ModelAndView mv, HttpServletRequest request, HttpSession session, @RequestParam("userId") String userId) {
		return userService.deleteUser(mv, request, session, userId);
	}
	
	// 회원 탈퇴 팝업 확인버튼 동작
	@RequestMapping(value = "deleteConfirmPopUp.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteConfirmPopUpMethod(HttpServletRequest request) {
		HttpSession session = request.getSession();
	    Boolean deleteSuccess = (Boolean) session.getAttribute("deleteSuccess");

	    // 사용 후 제거 (중복 방지)
	    session.removeAttribute("deleteSuccess");

	    String contextPath = request.getContextPath();
	    if (Boolean.TRUE.equals(deleteSuccess)) {
	        return "success|" + contextPath + "/main.do";  // 메인페이지 이동
	    } else {
	        return "success";  // 그냥 닫기
	    }
	}
	
}












