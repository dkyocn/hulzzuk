package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogPlaceVO;
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
	/**
     * 특정 플랜 ID와 Day 번호에 해당하는 장소 목록 조회 (후기 작성용)
     * @param planId 플랜 ID
     * @param planDay Day1 또는 Day2
     * @return
	List<LogPlaceVO> getPlacesByPlanDay(Long planId, int i);   // 로그데이터 탭 안의 내용 불러오기 
	

//  //**********LogPlaceService에서부터 병합 
//  	PlanVO fetchPlanById(long planId);
//  	
//  	//추가 
//  	PlanVO getPlanById(Long planId);
//      List<LogPlaceVO> getPlacesByPlanDay(Long planId, int Day);
//
//      
      /**
       * LogService → "로그 전체 관리" 용도
       * public interface LogService {
    List<LogVO> getLogList(Paging paging);
    LogVO getLogById(long id);
    void createLog(LogVO logVo);
    void updateLog(LogVO logVo);
    void deleteLog(long id);

    List<LogVO> getLogPage(int start, int amount);
    int getLogCount();
}
       */
	List<LogPlaceVO> getPlacesByPlanDay(Long planId, int day );
	
	
}