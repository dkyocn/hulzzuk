package com.hulzzuk.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public ModelAndView selectUser(String userId) {
		
		UserVO user = userDao.selectUser(userId);
		ModelAndView mv = new ModelAndView();
		
		  if (user != null) {
	            mv.setViewName("user/infoPage"); // user/myPage.jsp로 이동
	            mv.addObject("user", user);    // 모델에 user 객체 추가
	        } else {
	        	throw new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage());
	        }
		  
		return mv;
	}
}
