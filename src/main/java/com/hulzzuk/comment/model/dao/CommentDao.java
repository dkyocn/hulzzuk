package com.hulzzuk.comment.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.comment.model.vo.CommentVO;

@Repository("commentDao")
public class CommentDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// voc 댓글 조회
	public List<CommentVO> getVocComment(Long vocId){
		return sqlSessionTemplate.selectList("commentMapper.getVocComment", vocId);
	}

	// voc 댓글 생성
	
	
	// voc 댓글 삭제
	
}
