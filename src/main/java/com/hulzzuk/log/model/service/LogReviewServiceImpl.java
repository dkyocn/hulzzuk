package com.hulzzuk.log.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.log.model.dao.LogReviewDao;
import com.hulzzuk.log.model.vo.LogReviewVO;

@Service
public class LogReviewServiceImpl implements LogReviewService {

	    @Autowired
	    private LogReviewDao logReviewDao;

	    @Override
	    public int insertLogReview(LogReviewVO review) {
	        return logReviewDao.insertLogReveiw(review);
	    }

	
		
}
