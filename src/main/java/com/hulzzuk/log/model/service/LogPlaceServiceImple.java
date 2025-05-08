package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.log.model.dao.LogPlaceDao;
import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.plan.model.vo.PlanVO;

@Service
public class LogPlaceServiceImple implements LogPlaceService {
	
	@Autowired
	private LogPlaceDao logPlaceDao;

	
	@Override
	public PlanVO fetchPlanById(long planId) {
		return logPlaceDao.fetchPlanById(planId);
	}


	@Override
	public PlanVO getPlanById(int planId) {
		return logPlaceDao.fetchPlanById(planId);
	}


	@Override
	public List<LogPlaceVO> getPlacesByPlanDay(int planId, int planDay) {
		return logPlaceDao.selectByPlanDay(planId,planDay);
	}


}
