package com.hulzzuk.user.model.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface UserService {
	
	// 로그인
	ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status);
	
	// 로그아웃
	ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status);

	// 이메일 인증번호 발송
	ModelAndView sendMailAuthMethod(ModelAndView mv, HttpSession session, HttpServletRequest request,
			String mode, String userId, int width, int height);

	// 이메일
	void sendMail(String userId, String title, String message);
	
	// 인증번호 검증
	ModelAndView verifyCode(String mode, String inputCode, String userId,
            ModelAndView mv, HttpSession session, HttpServletRequest request);
	
	// 비밀번호 유효성 검사
	ModelAndView pwdValidateMethod(ModelAndView mv, String newPwd);
	
	// 비밀번호 일치 확인
	ModelAndView pwdConfirmMethod(ModelAndView mv, HttpSession session, HttpServletRequest request, 
			String newPwd, String pwdConfirm);
	
	// 비밀번호 업데이트
	ModelAndView pwdUpdateMethod(ModelAndView mv, HttpSession session, 
			HttpServletRequest request, String newPwd);
	
	// 내 정보 보기
	ModelAndView selectUser(ModelAndView mv, HttpSession session,  String userId);

	ModelAndView moveInfoUpdate(ModelAndView mv, String userId);
	
	// 회원 가입
	ModelAndView insertUser(ModelAndView mv, HttpServletRequest request, UserVO user);
	
	// 회원탈퇴 안내
	ModelAndView deleteGuide(ModelAndView mv, HttpSession session, String userId);
	
	// 회원 탈퇴
	String deleteUser(HttpServletRequest request, HttpSession session, SessionStatus status);

	// 프로필 사진 저장
	Map<String, Object> profileUpload(MultipartFile mfile, HttpServletRequest request);

	// 프로필 수정
	Map<String, Object> updateProfile(HttpServletRequest request, UserVO userVO);
	
}
