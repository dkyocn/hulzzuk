package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.dao.LogReviewDao;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;

@Service
public class LogReviewServiceImpl implements LogReviewService {

	 @Autowired
	    private LogReviewDao logReviewDao;
	


//************LogReviewServiceImpl merged
	  @Override
	    public int insertLogReview(LogReviewVO review) {
	        return logReviewDao.insertLogReview(review);
	    }

	@Override
	public LogReviewVO fetchPlanForLog(int planId) {
		 return logReviewDao.fetchPlanById(planId);
	}
	@Override
	public long insertLog(LogVO log) {
		logReviewDao.insertLog(log);
        return log.getLogId(); // Mapper에서 selectKey로 채워진 값
    }
	
	@Override
	public int insertLogReviews(List<LogReviewVO> reviews) {
	    int result = 0;

	    for (LogReviewVO review : reviews) {
	        //  내용이 없으면 skip (공백, null, <p><br></p> 등 제거)
	        if (review.getLogContent() == null || review.getLogContent().replaceAll("\\s|<.*?>", "").isEmpty()) {
	            continue;
	        }

	        result += logReviewDao.insertLogReview(review);
	    }

	    return result;
	}
	

	

	
}
