package com.hulzzuk.user.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;

@Repository("UserDao")
public class UserDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 내 정보 보기
	public UserVO selectUser(String userId) {
		return sqlSessionTemplate.selectOne("userMapper.selectUser", userId);
	}
	
	// 비밀번호 업데이트
	public int pwdUpdateMethod(String userId, String encryptedPwd) {
		HashMap<String, String> h1 = new HashMap<String, String>();
		h1.put("userId", userId);
		h1.put("userPwd", encryptedPwd);
		
		return sqlSessionTemplate.update("userMapper.updatePwd", h1);
	}
	
}
