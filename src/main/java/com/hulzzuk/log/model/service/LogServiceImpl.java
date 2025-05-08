package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.dao.PlanDao;
import com.hulzzuk.plan.model.vo.PlanVO;
import com.hulzzuk.review.model.vo.ReviewVO;


@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public List<LogVO> getLogList(Paging paging) {
        return logDao.getLogList(paging);
    }

    @Override
    public int getLogCount() {
        return logDao.getLogCount();
    }

    @Override
    public LogVO getLogById(long id) {
        return logDao.getLogById(id);
    }

    @Override
    public List<PlanVO> selectPlanIdList(String userId) {
        return logDao.selectPlanIdList(userId);
    }
    @Override
    public void createLog(LogVO logVo) {
    	logDao.createLog(logVo);
    }

  
//    @Override
//    public void createLogPlace(LogPlaceVO logPlaceVo) {
//        LogDao.createLogPlace(logPlaceVo);
//    }

    @Override
    public void updateLog(LogVO logVo) {
    	logDao.updateLog(logVo);
    }

//    @Override
//    public void updateLogPlace(LogPlaceVO logPlaceVo) {
//        LogDao.updateLogPlace(logPlaceVo);
//    }

    @Override
    public void deleteLog(long id) {
    	logDao.deleteLog(id);
    }
    @Override
    public List<LogVO> getLogPage(int start, int amount) {
        return logDao.getLogPage(start, amount);
    }
    
 // 상세페이지 로그 갯수 조회
 	@Override
 	public int logCount(String locId, LocationEnum locationEnum) {
 		int logCount = logDao.logCount(locId, locationEnum);
 		 
         return logCount;
 	}
 	
 	// 상세페이지 로그 리스트 조회
 	public List<LogVO> getLocLogList(String locId, LocationEnum locationEnum) {
         List<LogVO> logList = logDao.getLocLogList(locId, locationEnum);
  
         return logList;
 	}

	@Override
	public PlanVO getPlanById(Long planId) {
		return logDao.getPlanById(planId);
	}
	//************LogReviewServiceImple merged
	@Override
	public int insertLogReview(LogReviewVO review) {
	     return logDao.insertLogReveiw(review);
	}
	
	
	//************LogPlaceServiceImple merged
	
	@Override
	public PlanVO fetchPlanById(long planId) {
		return logDao.fetchPlanById(planId);
	}


	@Override
	public PlanVO getPlanById(int planId) {
		return logDao.fetchPlanById(planId);
	}


	@Override
	public List<LogPlaceVO> getPlacesByPlanDay(int planId, int planDay) {
		return logDao.selectByPlanDay(planId,planDay);
	}
	
	
}
