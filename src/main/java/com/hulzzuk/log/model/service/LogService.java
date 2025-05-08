package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;

public interface LogService {

    List<LogVO> getLogList(Paging paging);
    List<PlanVO> selectPlanIdList(String userId);
    
    

    int getLogCount();

    LogVO getLogById(long id);

 
    void createLog(LogVO logVo);

    //void createLogPlace(LogPlaceVO logPlaceVo);

    void updateLog(LogVO logVo);

  //  void updateLogPlace(LogPlaceVO logPlaceVo);

    void deleteLog(long id);
    
    List<LogVO> getLogPage(int start, int amount);
	PlanVO getPlanById(Long planId);
	
	//**********LogReviewService에서부터 병합 
	int insertLogReview(LogReviewVO review);
	
	//**********LogPlaceService에서부터 병합 
	PlanVO fetchPlanById(long planId);
	
	//추가 
	PlanVO getPlanById(int planId);
    List<LogPlaceVO> getPlacesByPlanDay(int planId, int planDay);
	
	
}