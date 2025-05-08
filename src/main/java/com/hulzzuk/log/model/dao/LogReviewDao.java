package com.hulzzuk.log.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.log.model.vo.LogReviewVO;

@Repository
public class LogReviewDao {
	
	@Autowired
	private SqlSession sqlSession;
	


	public int insertLogReveiw(LogReviewVO review) {
		return sqlSession.insert("logReviewMapper.insertLogReview", review);
	}
	
}
