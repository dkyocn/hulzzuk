package com.hulzzuk.user.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hulzzuk.common.vo.FileNameChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.user.controller.KakaoLoginAuth;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private KakaoLoginAuth kakaoLoginAuth;
	
	// 패스워드 암호화 처리
	private static final PasswordEncryptor passwordEncryptor = new PasswordEncryptor();

	// 로그인
	@Override
	public ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status) {
		
		UserVO loginUser = userDao.selectUser(user.getUserId());
		
		if (loginUser != null && PasswordEncryptor.isPasswordMatch(user.getUserPwd(), loginUser.getUserPwd())) {
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("authUserId", loginUser.getUserId());
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
			  // 소셜 로그인 여부 확인
			
	        UserVO user = (UserVO) session.getAttribute("loginUser");
	        String sns = user.getUserPath();
	        String accessToken = (String) session.getAttribute("accessToken");

	        if (sns != null && accessToken != null) {
	            switch (sns) {
	                case "kakao":
	                	kakaoLoginAuth.logOut(accessToken);
	                	//kakaoLoginAuth.disconnect(accessToken); 
	                    break;
	                /*
	                case "naver":
	                    naverLoginAuth.logOut(accessToken); // Naver 로그아웃 호출
	                    break;
	                */
	            }
	        }
			
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
	public ModelAndView sendMailAuthMethod(ModelAndView mv, HttpSession session, HttpServletRequest request,
			String mode, String userId, int width, int height) {
		
		logger.info("userId : "+ userId);
		logger.info("mode : " + mode);
		
		boolean userExits = (userDao.selectUser(userId) != null);
		
		// 비밀번호 재설정 페이지(mode : reset)에서 아이디가 있으면 이메일 전송, 아이디가 없으면 회원가입 페이지로
		// 회원가입 페이지(mode : join)에서 아이디가 있으면 로그인 페이지로, 아이디 없으면 이메일 전송
		// 비밀번호 재설정 할 때 입력했던 아이디 값을 파라미터로 같이 넘겨줘야 함
		if (("resetSend".equals(mode) && userExits) || "joinSend".equals(mode) && !userExits) {
			// 인증번호 생성
			String authCode = generateAuthCode();

			// 인증번호 세션에 저장 (검증 시 필요)
			session.setAttribute("authCode", authCode);
			session.setAttribute("authUserId", userId);

			sendMail(userId,"[HULZZUK] 비밀번호 재설정 인증번호 안내","요청하신 비밀번호 재설정을 위한 인증번호입니다.\n\n"
					+ "인증번호: " + authCode + "\n\n"
					+ "해당 인증번호를 입력창에 정확히 입력해주세요.\n\n"
					+ "(본 메일은 발신 전용입니다.)");

			 // 성공: 팝업 메시지 + 닫기만
			mv.addObject("message", "인증번호가 전송되었습니다.");
			mv.addObject("action", "close");  // 팝업에서 닫기만
	    } else {
	    	if("resetSend".equals(mode)) {
		        // 실패: 팝업 메시지 + 회원가입 페이지로 이동
		        mv.addObject("message", "입력하신 아이디가 잘못되었습니다.<br>회원가입 페이지로 이동하시겠습니까?");
		        mv.addObject("action", "redirect");  // 팝업에서 부모창 이동
		        mv.addObject("moveUrl", request.getContextPath() + "/user/moveJoin.do");
	    	} else if("joinSend".equals(mode)){
	    		mv.addObject("message", "이미 존재하는 아이디입니다.<br>로그인 페이지로 이동하시겠습니까?");
	    		mv.addObject("action", "redirect");
	    		mv.addObject("moveUrl", request.getContextPath() + "/user/login.do");
	    	}
	    }

	    mv.addObject("width", width);
	    mv.addObject("height", height);
	    mv.setViewName("common/popUp");
	    return mv;
	}

	// 인증번호 생성 메소드
	private String generateAuthCode() {
		 Random random = new Random();
		    int code = 100000 + random.nextInt(900000); // 100000 ~ 999999 사이 숫자
		    return String.valueOf(code);
	}

	// 메일 전송 메서드
	@Override
	public void sendMail(String userId, String title, String message) {
		try{

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
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userId));
			msg.setSubject(title);
			msg.setText(message);

			// 메일 보내기
			Transport.send(msg);
		}  catch (Exception e) {
			throw new IllegalArgumentException(ErrorCode.MAIL_SEND_FAIL.getMessage());
		}
	}
	
	// 인증번호 검증 메소드
	@Override
	public ModelAndView verifyCode(String mode, String inputCode, String userId,
            ModelAndView mv, HttpSession session, HttpServletRequest request) {
		
		logger.info("userId : "+ userId);
		logger.info("mode : " + mode);
		
		String authCode = (String) session.getAttribute("authCode");
		String authUserId = (String) session.getAttribute("authUserId");
		
		boolean isVerified = (authCode != null && authCode.equals(inputCode));
		
		if(isVerified) {
			mv.addObject("userId", authUserId);
			
			if("resetVerify".equals(mode)) {
				mv.setViewName("user/userPwdPage");
			}else if("joinVerify".equals(mode)) {
				mv.addObject("message", "인증이 완료되었습니다.");
		        mv.addObject("action", "close"); 
		        mv.addObject("actionUrl", request.getContextPath() + "/user/verifyPopUp.do");
		        mv.setViewName("common/postPopUp");
			}
		}else{
			mv.addObject("mailFalse", "N");
			mv.addObject("userId", userId);
			
			if("resetVerify".equals(mode)) {
			mv.setViewName("user/mail");
			}else if("joinVerify".equals(mode)) {
				mv.addObject("message", "인증번호가 일치하지 않습니다.");
				mv.addObject("action", "close");
				mv.addObject("actionUrl", request.getContextPath() + "/user/verifyPopUp.do");
				mv.setViewName("common/postPopUp");
			}
		}
		return mv;
	}
	
	// 비밀번호 유효성 검사 메소드
	@Override
	public ModelAndView pwdValidateMethod(ModelAndView mv, String userPwd) {
	    String message = "";
	    String color = "red";

	    if (userPwd == null || userPwd.trim().isEmpty()) {
	        message = "비밀번호는 8~16자의 영문, 숫자, 특수문자만 가능합니다. (사용 가능한 특수문자 : ~ ! @ # $)";
	        color = "gray";
	    }

	    if (userPwd != null && (userPwd.length() < 8 || userPwd.length() > 16)) {
	        message = "! 8~16자 사이여야 합니다.";
	    }

	    if (userPwd != null && userPwd.contains(" ")) {
	        message = "! 공백은 사용할 수 없습니다.";
	    }

	    if (userPwd != null && !userPwd.matches("^[A-Za-z0-9~!@#$]*$")) {
	        message = "! 허용되지 않은 특수문자가 포함되었습니다.";
	    }

	    if (userPwd != null &&
	        (!userPwd.matches(".*[A-Za-z].*") || 
	         !userPwd.matches(".*[0-9].*") || 
	         !userPwd.matches(".*[~!@#$].*"))) {
	        message = "! 영문, 숫자, 특수문자(~!@#$)를 모두 포함해야 합니다.";
	    }

	    if (userPwd != null &&
	        userPwd.length() >= 8 && userPwd.length() <= 16 &&
	        !userPwd.contains(" ") &&
	        userPwd.matches("^[A-Za-z0-9~!@#$]*$") &&
	        userPwd.matches(".*[A-Za-z].*") &&
	        userPwd.matches(".*[0-9].*") &&
	        userPwd.matches(".*[~!@#$].*")) {
	        message = "사용 가능한 비밀번호입니다.";
	        color = "green";
	    }

	    mv.addObject("msg", message);
	    mv.addObject("color", color);
	    mv.setViewName("user/userPwdPage"); // 비밀번호 입력 페이지로 이동
	    return mv;
	}

	
	// 비밀번호 일치 확인 메소드
	@Override
	public ModelAndView pwdConfirmMethod(ModelAndView mv, HttpSession session, HttpServletRequest request, 
			String userPwd, String pwdConfirm) {
		//logger.info(userPwd);
		//logger.info(pwdConfirm);

		// 유효성 검사 조건
	    boolean isValid = userPwd != null &&
	                      userPwd.length() >= 8 && userPwd.length() <= 16 &&
	                      !userPwd.contains(" ") &&
	                      userPwd.matches("^[A-Za-z0-9~!@#$]*$") &&
	                      userPwd.matches(".*[A-Za-z].*") &&
	                      userPwd.matches(".*[0-9].*") &&
	                      userPwd.matches(".*[~!@#$].*");

	    // 1. 유효성 검사 먼저 실패 시
	    if (!isValid) {
	        mv.addObject("message", "유효하지 않은 비밀번호입니다.<br>조건을 다시 확인해주세요.");
	        mv.addObject("actionUrl", request.getContextPath() + "/user/pwdConfirmPopUp.do");
	        mv.addObject("width", 350);
	        mv.addObject("height", 300);
	        mv.setViewName("common/postPopUp");
	        return mv;
	    }
	  
	    // 2. 비밀번호 일치 검사 - 비밀번호 불일치
		if (StringUtils.isBlank(userPwd) || StringUtils.isBlank(pwdConfirm) || !userPwd.equals(pwdConfirm)) {	
			 session.setAttribute("mismatch", true);
			
			 mv.addObject("message", "비밀번호가 일치하지 않습니다.");
	         mv.addObject("actionUrl", request.getContextPath() + "/user/pwdConfirmPopUp.do");
	         mv.addObject("width", 350);
	         mv.addObject("height", 300);
	         mv.setViewName("common/postPopUp");
	        return mv;
	    }
		
		// 3. 유효성 + 비밀번호 일치 둘 다 통과 시
		 mv.addObject("message", "비밀번호가 일치합니다.");
         mv.addObject("actionUrl", request.getContextPath() + "/user/pwdConfirmPopUp.do");
         mv.addObject("width", 350);
         mv.addObject("height", 300);
         mv.setViewName("common/postPopUp"); // 팝업 띄울 페이지
         return mv;
	}
	
	// 비밀번호 변경 메소드
	@Override
	public ModelAndView pwdUpdateMethod(ModelAndView mv, HttpSession session, 
			HttpServletRequest request, String userPwd) {
		 // 비밀번호 암호화
        String encryptedPwd = passwordEncryptor.encryptSHA256(userPwd).toUpperCase();

        // 세션에서 ID 불러오기
        String authUserId = (String)session.getAttribute("authUserId");
        
        if(StringUtils.isBlank(authUserId)) {
        	throw new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage());
        }else {
        	
            // DAO를 통한 DB 업데이트 (성공 시 1 반환 가정)
            int result = userDao.pwdUpdateMethod(authUserId, encryptedPwd);

            if (result > 0) {
                // 성공 시 팝업 메시지 정보 추가
                mv.addObject("message", "비밀번호 변경이 완료되었습니다.<br>로그인 페이지로 이동하시겠습니까?");
                mv.addObject("actionUrl", request.getContextPath() + "/user/pwdUpdatePopUp.do");
                mv.addObject("width", 350);
                mv.addObject("height", 300);
                
                mv.setViewName("common/postPopUp"); // 팝업 띄울 페이지
            } else if(result <= 0) {
                // 실패 시 다시 입력 페이지로 이동
                mv.addObject("fail", "Y");  // <c:if test="${fail eq 'Y'}"> 조건 등에서 사용
                mv.addObject("userId", authUserId);
                mv.setViewName("user/userPwdPage");
            }
            
    		return mv;
        }
	}
	
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

	@Override
	public ModelAndView moveInfoUpdate(ModelAndView mv, String userId) {

		UserVO userVO = userDao.selectUser(userId);

		if (userVO == null) {
			throw new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage());
		}

		mv.addObject("user",userVO);
		mv.setViewName("user/infoUpdate");
		return mv;
	}

	// 회원 가입
	@Override
	public ModelAndView insertUser(ModelAndView mv, HttpServletRequest request, UserVO user) {
		// 필수값 유효성 검사
		if (StringUtils.isBlank(user.getUserId()) || StringUtils.isBlank(user.getUserPwd())
			|| StringUtils.isBlank(user.getGender()) || user.getUserAge() == null) {
			request.getSession().setAttribute("joinSuccess", false);
			mv.addObject("message", "모든 항목을 입력해주세요.");
			mv.addObject("action", "close");
			mv.addObject("actionUrl", request.getContextPath() + "/user/userJoinPopUp.do");
			mv.addObject("width", 350);
            mv.addObject("height", 300);
            mv.addObject("user", user);
            
            mv.setViewName("common/postPopUp");
            
            return mv;
		}
		
		// 비밀번호 암호화
		String encryptedPwd = passwordEncryptor.encryptSHA256(user.getUserPwd()).toUpperCase();
		user.setUserPwd(encryptedPwd);
		
		// 닉네임 생성 (이메일 앞부분)
		//user.setUserNick(user.getUserId().substring(0, user.getUserId().indexOf("@")));
		
		String baseNick = user.getUserId().substring(0, user.getUserId().indexOf("@")); String finalNick = baseNick;
		  
		// DB에 닉네임이 존재하는지 확인하고 중복될 경우 숫자 붙이기 
		int suffix = 1; 
		while (userDao.countNickName(finalNick) > 0) { 
			finalNick = baseNick + String.format("%02d", suffix++); // asdf01, asdf02 ... 
		}
		  
		user.setUserNick(finalNick);
		 
		
		// 기본값 설정
	    user.setUserKey(null);                 // null 처리
	    user.setUserPath("NOMAL");             // 고정값
	    user.setUserRefreshCode(null);         // null 처리
	    user.setAdminYN("N");                  // 일반 사용자

	    // 4. DB 저장
	    int result = userDao.insertUser(user);
		
	    if (result > 0) {
	    	request.getSession().setAttribute("joinSuccess", true);
	        mv.addObject("message", "회원가입이 완료되었습니다.<br>로그인 페이지로 이동하시겠습니까?");
	        mv.addObject("actionUrl", request.getContextPath() + "/user/userJoinPopUp.do");
	    } else {
	    	request.getSession().setAttribute("joinSuccess", false);
	        mv.addObject("message", "회원가입에 실패했습니다.<br>다시 시도해주세요.");
	        mv.addObject("action", "close");
	        mv.addObject("actionUrl", request.getContextPath() + "/user/userJoinPopUp.do");
	    }

	    mv.addObject("width", 350);
	    mv.addObject("height", 300);
	    mv.setViewName("common/postPopUp");
	    return mv;
	}
	
	// 회원 탈퇴 안내 (info 페이지에서 이동)
	@Override
	public ModelAndView deleteGuide(ModelAndView mv, HttpSession session, @RequestParam("userId") String userId) {

		String sessionUserId = (String) session.getAttribute("authUserId");
		
		if (sessionUserId == null || !sessionUserId.equals(userId)) {
			mv.setViewName("redirect:/user/infoPage.do?fail=Y");	// 세션 없을 경우
	    } else if(userDao.deleteUser(userId) > 0) {
			mv.setViewName("user/deleteUser");	// 세션 있을 경우
		} else {
		    mv.setViewName("redirect:/user/infoPage.do?fail=E"); // 탈퇴 실패 시
		}

		return mv;
	}
	
	// 회원 탈퇴
	@Override
	public String deleteUser(HttpServletRequest request, HttpSession session, SessionStatus status) {
		// 세션에서 ID 불러오기
        String authUserId = (String)session.getAttribute("authUserId");
        String deleteYN = new String();
        
        // 해당 세션 객체가 있으면 리턴받고, 없으면 null 리턴받음
 		if (session != null) {
 			  // 소셜 로그인 여부 확인
 			
 	        UserVO user = (UserVO) session.getAttribute("loginUser");
 	        String sns = user.getUserPath();
 	        String accessToken = (String) session.getAttribute("accessToken");

	        if (sns != null && accessToken != null) {
	            switch (sns) {
	                case "kakao":
	                	kakaoLoginAuth.disconnect(accessToken);
	                	//kakaoLoginAuth.disconnect(accessToken); 
	                    break;
	                /*
	                case "naver":
	                    naverLoginAuth.logOut(accessToken); // Naver 로그아웃 호출
	                    break;
	                */
	            }
	        }
			// 세션 없애기
			session.invalidate();
			status.setComplete();
			
			// 탈퇴 처리
			int result = userDao.deleteUser(authUserId);
	
			if(result > 0) {
				deleteYN = "success|" + request.getContextPath() + "/main.do";
			}else {
				throw new IllegalArgumentException(ErrorCode.USER_DELETE_ERROR.getMessage());
			}
		}
		return deleteYN;
 	}

	@Override
	public Map<String, Object> profileUpload(MultipartFile mfile, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<>();

		// 게시글 첨부파일저장폴더 경로 저장
		String path = System.getProperty("user.dir");
		String savePath = request.getSession().getServletContext().getRealPath("/resources/images/user/"+request.getSession().getAttribute("authUserId"));

		// 첨부파일이 있을 때
		if (mfile != null && !mfile.isEmpty()) {
			try {
				// 파일 이름 추출
				String fileName = mfile.getOriginalFilename();
				String renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmss");

				// 폴더 없으면 생성
				java.io.File dir = new java.io.File(savePath);
				if (!dir.exists()) dir.mkdirs();

				// 파일 실제 저장
				java.io.File destFile = new java.io.File(savePath + "/" + renameFileName);
				mfile.transferTo(destFile);
				savePath = savePath + "/" + renameFileName;

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		map.put("filePath", savePath);
		map.put("successYN", true);

		return map;
	}

	@Override
	public Map<String, Object> updateProfile(HttpServletRequest request, UserVO userVO) {
		HashMap<String, Object> map = new HashMap<>();
		String authUserId = (String) request.getSession().getAttribute("authUserId");

		UserVO loginUser = userDao.selectUser(authUserId);

		if (loginUser == null) {
			throw new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage());
		}

		// vo 저장
		loginUser.setUserProfile(userVO.getUserProfile());
		loginUser.setUserNick(userVO.getUserNick());
		loginUser.setGender(userVO.getGender());
		loginUser.setUserAge(userVO.getUserAge());

		if(userDao.updateUser(loginUser) == 0) {
			throw new IllegalArgumentException(ErrorCode.USER_UPDATE_ERROR.getMessage());
		}

		map.put("success", true);

		return map;
	}
}	

















