package com.hulzzuk.user.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.user.model.vo.UserVO;

@Repository("UserDao")
public class UserDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public UserVO selectUser(String userId) {
		return sqlSessionTemplate.selectOne("userMapper.selectUser", userId);
	}
}
