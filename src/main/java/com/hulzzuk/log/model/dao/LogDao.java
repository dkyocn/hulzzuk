package com.hulzzuk.log.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;

@Repository
public class LogDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    // 페이징 처리된 로그 리스트 조회
    public List<LogVO> getLogList(Paging paging) {
        return sqlSession.selectList("logMapper.getLogList", paging);
    }

    // 전체 로그 수 조회
    public int getLogCount() {
        return sqlSession.selectOne("logMapper.getLogCount");
    }

    // ID로 단일 로그 조회
    public LogVO getLogById(long id) {
        return sqlSession.selectOne("logMapper.getLogById", id);
    }

    // 로그 생성
    public void createLog(LogVO logVo) {
        sqlSession.insert("logMapper.createLog", logVo);
    }

    // 로그 수정
    public void updateLog(LogVO logVo) {
        sqlSession.update("logMapper.updateLog", logVo);
    }

    // 로그 삭제
    public void deleteLog(long id) {
        sqlSession.delete("logMapper.deleteLog", id);
    }

    // 시작 위치와 수량에 따른 로그 목록 조회 (페이지용)
    public List<LogVO> getLogPage(int start, int amount) {
        Map<String, Object> param = new HashMap<>();
        param.put("start", start);
        param.put("amount", amount);

        return sqlSession.selectList("logMapper.getLogList", param);
    }

    // 로그인한 사용자의 여행 계획 리스트 조회
    public List<PlanVO> selectPlanIdList(String userId) {
        return sqlSession.selectList("logMapper.selectPlanIdList", userId);
    }

    //로그 작성을 위한 여행일정조회 
    public PlanVO getPlanById(Long planId) {
        return sqlSession.selectOne("logMapper.getPlanById", planId);
    }
    
    //*****************************LogPlaceDao 로부터 병함
    public PlanVO fetchPlanById(long planId) {
    return sqlSession.selectOne("logMapper.getPlanById", planId);
}

    public List<LogPlaceVO> selectByPlanDay(int planId, int planDay) {
    Map<String, Object> params = new HashMap<>();
    params.put("planId", planId);
    params.put("planDay", planDay);
    
    return sqlSession.selectList("logPlaceMapper.selectPlacesByPlanDay", params);
}
  //*****************************LogResolvedDao 로부터 병함
    public int insertLogReveiw(LogReviewVO review) {
		return sqlSession.insert("logReviewMapper.insertLogReview", review);
	}
    
}