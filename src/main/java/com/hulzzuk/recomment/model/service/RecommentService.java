package com.hulzzuk.recomment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hulzzuk.recomment.model.vo.RecommentVO;

public interface RecommentService {

	List<RecommentVO> getVocRecomment(Long commentId);

}
