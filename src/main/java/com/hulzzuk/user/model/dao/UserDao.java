package com.hulzzuk.user.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.user.model.vo.UserVO;


@Repository("UserDao")
public class UserDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 비밀번호 업데이트
	public int pwdUpdateMethod(String userId, String encryptedPwd) {
		HashMap<String, String> h1 = new HashMap<String, String>();
		h1.put("userId", userId);
		h1.put("userPwd", encryptedPwd);
		
		return sqlSessionTemplate.update("userMapper.updatePwd", h1);
	}
	
	// 내 정보 보기
	public UserVO selectUser(String userId) {
		return sqlSessionTemplate.selectOne("userMapper.selectUser", userId);
	}
	
	// 회원 가입
	public int insertUser(UserVO user) {
		return sqlSessionTemplate.insert("userMapper.insertUser", user);
	}
	
	// 회원 탈퇴
	public int deleteUser(String userId) {
		return sqlSessionTemplate.delete("userMapper.deleteUser", userId);
	}
}

