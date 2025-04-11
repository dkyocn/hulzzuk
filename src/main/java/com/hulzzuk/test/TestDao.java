package com.hulzzuk.test;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDao {
	
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	public String connectTest() {
		return sessionTemplate.selectOne("testMapper.getCurrentDate");
	}

}
