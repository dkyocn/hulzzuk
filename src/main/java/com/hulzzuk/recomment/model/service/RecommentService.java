package com.hulzzuk.recomment.model.service;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import com.hulzzuk.recomment.model.vo.RecommentVO;
import org.springframework.web.bind.annotation.RequestParam;

public interface RecommentService {

	List<RecommentVO> getVocRecomment(Long commentId);

	Map<String, Object> createRecomment(HttpSession session, Long id, String content);

	Map<String, Object> deleteComment(HttpSession session, Long id);
}
