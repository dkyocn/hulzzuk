package com.hulzzuk.log.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;


@Repository
public class LogReviewDao {

    @Autowired
    private SqlSessionTemplate sqlSession;
    
    //*****************************LogReviewDao 로부터병합후 다시 분
    // 후기 등록
    public int insertLog(LogVO log) {
	    return sqlSession.insert("logReviewMapper.insertLog", log);
	}
    
    public int insertLogReview(LogReviewVO review) {
		return sqlSession.insert("logReviewMapper.insertLogReview", review);// 3개로 분기는 여기서 나눠주기. 
	}
    // 특정 날짜(planDay)의 장소 리스트 불러오기
   

	public List<LogReviewVO> selectByPlanDay(@Param("planId") Long planId, @Param("day") int day){
	
		 // 1. 파라미터를 맵으로 묶어서 전달
		Map<String, Object> params = new HashMap<>();
	    params.put("planId", planId);
	    params.put("planDay", day);
	    
	    
	 // 2. MyBatis 쿼리 호출
	    return sqlSession.selectList("logReviewMapper.selectPlacesByPlanDay", params);
	}
	
	// planId로 단일 일정 조회
	public LogReviewVO fetchPlanById(int planId) {
	    return sqlSession.selectOne("logReviewMapper.getPlanById", planId);
	}

	


}
