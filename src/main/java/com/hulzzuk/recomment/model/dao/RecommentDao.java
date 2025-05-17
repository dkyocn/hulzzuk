package com.hulzzuk.recomment.model.dao;

import java.util.List;

import com.hulzzuk.comment.model.vo.CommentVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.recomment.model.vo.RecommentVO;


@Repository("recommentDao")
public class RecommentDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<RecommentVO> getVocRecomment(Long commentId){
		return sqlSessionTemplate.selectList("recommentMapper.getVocRecomment", commentId);
	}

	// 단일 조회
	public RecommentVO getRecommentById(Long id){
		return sqlSessionTemplate.selectOne("recommentMapper.getRecommentById", id);
	}

	// 댓글 생성
	public int createRecomment(RecommentVO recommentVO){
		return sqlSessionTemplate.insert("recommentMapper.insertComment", recommentVO);
	}

	// 댓글 삭제
	public int deleteRecomment(long recommentId){
		return sqlSessionTemplate.delete("recommentMapper.deleteComment", recommentId);
	}
}
