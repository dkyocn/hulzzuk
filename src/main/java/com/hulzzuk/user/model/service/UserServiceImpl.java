package com.hulzzuk.user.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;

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
			mv.setViewName("user/infoPage"); // user/myPage.jsp로 이동
			mv.addObject("user", user); // 모델에 user 객체 추가
		}else {
        	throw new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage());
		}
		
		return mv;
	}


	@Override
	public ModelAndView loginMethod(ModelAndView mv, UserVO user, HttpSession session, SessionStatus status) {

		//아이디로 사용자를 디비에서 찾아와. userVO 다 가져와. 없으면 밑에 띄우기 (alert 아님)
		UserVO loginUser = userDao.selectUser(user.getUserId());
	
		if (loginUser != null && PasswordEncryptor.isPasswordMatch(user.getUserPwd(), loginUser.getUserPwd())) {
			session.setAttribute("loginUser", loginUser);
			status.setComplete(); // 로그인 성공 결과를 보냄 (HttpStatus 200 코드 보냄)
			mv.setViewName("redirect:/main.do");	// common/main.jsp로 이동
			session.setMaxInactiveInterval(30 * 60);
		} else { 	// 로그인 실패 시
			mv.setViewName("redirect:/user/login.do");
		}

		return mv;
	}


	@Override
	public ModelAndView logoutMethod(ModelAndView mv, HttpSession session, SessionStatus status) {
		
		// 해당 세션 객체가 있으면 리턴받고, 없으면 null 리턴받음
		if (session != null) {
			// 해당 세션객체가 있다면, 세션 객체를 없앰
			session.invalidate();
			status.setComplete();
			mv.setViewName("redirect:/main.do");
		} else {
			// 세션 자동 타임아웃으로 세션 객체가 없을 때
			// 에러 메세지를 error.jsp 로 내보내기함 => 서블릿이라면 RequestDispatcher 의 forward() 사용함
			// Spring 에서는 Model 클래스를 제공함 => request.setAttribute("에러메세지");
			// Model 의 addAttribute() 메소드 사용함
			// Model 객체 생성은 메소드 안에서 직접하지 않고 메소드 매개변수로 선언함
			mv.addObject("message", "로그인 세션이 존재하지 않습니다.");
			mv.setViewName("redirect:/main.do");
		}
		return mv;
	}
	
	
}

















