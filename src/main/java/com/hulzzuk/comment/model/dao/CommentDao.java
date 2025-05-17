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

	// 댓글 단일 조회
	public CommentVO getCommentById(Long commentId){
		return sqlSessionTemplate.selectOne("commentMapper.getCommentById", commentId);
	}

	// 댓글 생성
	public int createComment(CommentVO commentVO){
		return sqlSessionTemplate.insert("commentMapper.insertComment", commentVO);
	}

	// 댓글 삭제
	public int deleteComment(long commentId){
		return sqlSessionTemplate.delete("commentMapper.deleteComment", commentId);
	}

	//Log 댓글조회 
	public List<CommentVO> getLogComment(Long logId) {
		return sqlSessionTemplate.selectList("commentMapper.getLogComment", logId);
	}
}
