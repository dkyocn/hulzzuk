package com.hulzzuk.user.model.service;

import java.net.http.HttpRequest;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	// 패스워드 암호화 처리
	private static final PasswordEncryptor passwordEncryptor = new PasswordEncryptor();

	// 마이페이지
	@Override
	public ModelAndView selectUser(ModelAndView mv, String userId) {

		UserVO user = userDao.selectUser(userId);

		if (user != null) {
			mv.setViewName("user/infoPage"); 
			mv.addObject("user", user); 
		}else {
        	throw new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage());
		}
		
		return mv;
	}

	// 로그인
	@Override
	public ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status) {

		UserVO loginUser = userDao.selectUser(user.getUserId());
	
		if (loginUser != null && PasswordEncryptor.isPasswordMatch(user.getUserPwd(), loginUser.getUserPwd())) {
			session.setAttribute("loginUser", loginUser);
			status.setComplete(); // 로그인 성공 결과를 보냄
			mv.setViewName("redirect:/main.do");	// common/main.jsp로 이동
			session.setMaxInactiveInterval(30 * 60);
		} else { 	// 로그인 실패 시
			mv.addObject("fail", "N");
			mv.setViewName("user/loginPage");
		}
		
		return mv;
	}

	// 로그아웃
	@Override
	public ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status) {
		
		// 해당 세션 객체가 있으면 리턴받고, 없으면 null 리턴받음
		if (session != null) {
			// 해당 세션객체가 있다면, 세션 객체를 없앰
			session.invalidate();
			status.setComplete();
			mv.setViewName("redirect:/main.do");
		} else {
			mv.addObject("message", "로그인 세션이 존재하지 않습니다.");
			mv.setViewName("redirect:/main.do");
		}
		
		return mv;
	}
	
	// 이메일 인증번호
	@Override
	public ModelAndView sendMailMethod(ModelAndView mv, HttpSession session, HttpServletRequest request, 
			String userId, int width, int height) {
		
		
		logger.info("userId : "+userId);
//		logger.info("UserVO : " + userDao.selectUser(userId).toString());
		// 아이디가 있으면 이메일 전송, 아이디가 없으면 회원가입 페이지로
		// 비밀번호 재설정 할 때 입력했던 아이디 값을 파라미터로 같이 넘겨줘야 함
		if (userDao.selectUser(userId) != null) {
			try {
				
				// 인증번호 생성
				String authCode = generateAuthCode();
				
				// 인증번호 세션에 저장 (검증 시 필요)
				session.setAttribute("authCode", authCode);
				session.setAttribute("authUserId", userId);
				
				String to = userId; 		// 수신자 이메일
				String from = "jungdongju99@gmail.com"; // 발신자 이메일
				String password = "egzibfksztflconr"; 	// 발신자 비밀번호
				String host = "smtp.gmail.com"; 		// 구글 메일 서버 호스트 이름

				// SMTP 프로토콜 설정
				Properties props = new Properties();
				props.setProperty("mail.smtp.host", host);
				props.setProperty("mail.smtp.port", "587");
				props.setProperty("mail.smtp.auth", "true");
				props.setProperty("mail.smtp.starttls.enable", "true");

				// 보내는 사람 계정 정보 설정
				Session mailSession = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, password);
					}
				});

				// 메일 내용 작성
				Message msg = new MimeMessage(mailSession);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				msg.setSubject("[HULZZUK] 비밀번호 재설정 인증번호 안내");
				msg.setText("요청하신 비밀번호 재설정을 위한 인증번호입니다.\n\n"
                        + "인증번호: " + authCode + "\n\n"
                        + "해당 인증번호를 입력창에 정확히 입력해주세요.\n\n"
                        + "(본 메일은 발신 전용입니다.)");

				// 메일 보내기
				Transport.send(msg);
			    mv.addObject("message", "인증번호가 전송되었습니다.");
		        mv.addObject("actionUrl", request.getContextPath() + "/user/mailPopUp.do");
		        mv.addObject("width", width);
		        mv.addObject("height", height);

				mv.setViewName("common/popUp");
			} catch (Exception e) {				
				throw new IllegalArgumentException(ErrorCode.MAIL_SEND_FAIL.getMessage());
			}
			
		}else {
	        mv.addObject("message", "입력하신 아이디가 잘못되었습니다.<br> 회원가입 페이지로 이동하시겠습니까?");
	        mv.addObject("actionUrl", "${pageContext.servletContext.contextPath}/user/moveJoin.do");
	        mv.addObject("width", width);
	        mv.addObject("height", height);

			mv.setViewName("common/popUp");
		}
		return mv;
	}

	
	// 인증번호 생성 메소드
	private String generateAuthCode() {
		 Random random = new Random();
		    int code = 100000 + random.nextInt(900000); // 100000 ~ 999999 사이 숫자
		    return String.valueOf(code);
	}
	
	// 인증번호 검증 메소드
	@Override
	public ModelAndView verifyCode(String inputCode, String userId,
            ModelAndView mv, HttpSession session) {
		
		String authCode = (String) session.getAttribute("authCode");
		String authUserId = (String) session.getAttribute("authUserId");
		logger.info(inputCode);
		logger.info(authCode);
		if (authCode != null && authCode.equals(inputCode)) {
			mv.addObject("userId", authUserId);
			mv.setViewName("user/newPwdPage");
		}else {
			mv.addObject("mailFalse", "N");
			mv.addObject("userId", userId);
			mv.setViewName("user/mail");
		}
		return mv;
	}
}

















