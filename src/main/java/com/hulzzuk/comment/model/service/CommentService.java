package com.hulzzuk.comment.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hulzzuk.comment.model.vo.CommentVO;
import com.hulzzuk.voc.model.enumeration.VocEnum;

@Service("commentService")
public interface CommentService {
	// voc 댓글 조회
	List<CommentVO> getVocComment(Long vocId);
	// voc 댓글 생성
	
	// voc 댓글 삭제
	
}
