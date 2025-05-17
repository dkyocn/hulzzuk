package com.hulzzuk.comment.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.comment.model.dao.CommentDao;
import com.hulzzuk.comment.model.vo.CommentVO;

@Service("commentService")
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;

	// voc 댓글 조회
	@Override
	public List<CommentVO> getVocComment(Long vocId) {
	    // DAO에서 댓글 리스트 가져오기
	    List<CommentVO> commentList = commentDao.getVocComment(vocId);
	    return commentList;
	}
	
	// voc 댓글 생성
	
	// voc 댓글 삭제

}
