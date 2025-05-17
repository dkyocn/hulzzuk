package com.hulzzuk.recomment.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hulzzuk.recomment.model.dao.RecommentDao;
import com.hulzzuk.recomment.model.vo.RecommentVO;

@Service("recommentService")
public class RecommentServiceImpl implements RecommentService{
	
	@Autowired
	private RecommentDao recommentDao;
	
	// voc 댓글 조회
			@Override
			public List<RecommentVO> getVocRecomment(Long commentId) {
			    // DAO에서 댓글 리스트 가져오기
			    List<RecommentVO> recommentList = recommentDao.getVocRecomment(commentId);
			    return recommentList;
			}

}
