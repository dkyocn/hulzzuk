package com.hulzzuk.plan.model.dao;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.plan.model.vo.PlanLocVO;
import com.hulzzuk.plan.model.vo.PlanUserVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("planDao")
public class PlanDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public PlanVO getPlanById(long planId) {
        return sqlSessionTemplate.selectOne("planMapper.getPlanById", planId);
    }

    // 일정 페이지 조회
    public List<PlanVO> getPlanPage(String userId, Paging paging) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("paging", paging);
        return sqlSessionTemplate.selectList("planMapper.getPlanPage", params);
    }

    // 일정 개수 조회
    public int countPlan(String userId) {
        return sqlSessionTemplate.selectOne("planMapper.countPlan", userId);
    }

    // 일정 생성 - 시퀀스 1
    public void insertPlan(PlanVO planVO) {
        sqlSessionTemplate.insert("planMapper.insertPlan", planVO);
    }

    // 일정 생성 - 시퀀스 2
    public void insertPlanLoc(PlanLocVO planLocVO) {
        sqlSessionTemplate.insert("planLocMapper.insertPlanLoc", planLocVO);
    }

    // 일정 사용자 데이터 저장
    public int insertPlanUser(PlanUserVO planUserVO) {
        return sqlSessionTemplate.insert("planUserMapper.insertPlanUser", planUserVO);
    }

    // 일정 수정 - 시퀀스 1
    public void updatePlan(PlanVO planVO) {
        sqlSessionTemplate.update("planMapper.updatePlan", planVO);
    }

    // 일정 삭제
    public int deletePlan(long planId) {
        return sqlSessionTemplate.delete("planMapper.deletePlan", planId);
    }

    // 일정 장소 단일 삭제
    public int deletePlanLoc(Map<String, Object> deleteLocation) {
        return sqlSessionTemplate.delete("planLocMapper.deletePlanLocDay", deleteLocation);
    }

    public int deletePlanLocById(long id) {
        return sqlSessionTemplate.delete("planLocMapper.deletePlanLocById", id);
    }

    public List<PlanLocVO> getPlanLocByPlanId(long planId) {
        return sqlSessionTemplate.selectList("planLocMapper.getPlanLocByPlanId", planId);
    }

    // 상세페이지 일정  추가
	public List<PlanVO> getLocPlanList(String userId) {
		HashMap<String, Object> list = new HashMap<>();
		list.put("userId", userId);
        return sqlSessionTemplate.selectList("planMapper.getLocPlanList", list);
	}
	
	// 시퀀스 갯수 찾기
	public int findseq( long planId,  int planDay) {
		Map<String, Object> params = new HashMap<>();
	    params.put("planId", planId);
	    params.put("planDay", planDay);
		return sqlSessionTemplate.selectOne("planLocMapper.findseq", params);
	}

    public PlanUserVO getPlanUser(PlanUserVO planUserVO) {
        return sqlSessionTemplate.selectOne("planUserMapper.selectPlanUser", planUserVO);
    }
}
