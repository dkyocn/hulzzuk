package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.plan.model.vo.PlanVO;

public interface LogPlaceService {
	PlanVO fetchPlanById(long planId);
	
	//추가 
	PlanVO getPlanById(int planId);
    List<LogPlaceVO> getPlacesByPlanDay(int planId, int planDay);
}
