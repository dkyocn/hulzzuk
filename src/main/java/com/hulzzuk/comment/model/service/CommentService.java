package com.hulzzuk.comment.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import com.hulzzuk.comment.model.vo.CommentVO;
import com.hulzzuk.voc.model.enumeration.VocEnum;

public interface CommentService {
	// voc 댓글 조회
	List<CommentVO> getVocComment(Long vocId);

	// 댓글 생성
	Map<String, Object> createComment(HttpSession session, String type, long id, String content);

	// 댓글 삭제
	Map<String, Object> deleteComment(HttpSession session, long id); 
	
}
