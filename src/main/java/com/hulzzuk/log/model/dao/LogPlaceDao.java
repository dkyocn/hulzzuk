package com.hulzzuk.log.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.plan.model.vo.PlanVO;

@Repository
public class LogPlaceDao {

	@Autowired
	private SqlSession sqlSession;
	
	public PlanVO fetchPlanById(long planId) {
	    return sqlSession.selectOne("logMapper.getPlanById", planId);
	}

	public List<LogPlaceVO> selectByPlanDay(int planId, int planDay) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("planId", planId);
	    params.put("planDay", planDay);
	    
	    return sqlSession.selectList("logPlaceMapper.selectPlacesByPlanDay", params);
	}

}
