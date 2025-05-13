package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.dao.LogReviewDao;
import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
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
//	  @Override
//		public List<LogPlaceVO> getPlacesByPlanDay(int planId, int planDay) {
//			return logReviewDao.selectByPlanDay(planId,planDay);
//		}
	@Override
	public LogPlaceVO fetchPlanForLog(int planId) {
		 return logReviewDao.fetchPlanById(planId);
	}
	
	
		
	

	
}
