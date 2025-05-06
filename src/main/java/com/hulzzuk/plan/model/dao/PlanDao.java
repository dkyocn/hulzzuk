package com.hulzzuk.plan.model.dao;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.plan.model.vo.PlanLocVO;
import com.hulzzuk.plan.model.vo.PlanUserVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("planDao")
public class PlanDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

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

    // 일정 삭제
    public int deletePlan(long planId) {
        return sqlSessionTemplate.delete("planMapper.deletePlan", planId);
    }
}
