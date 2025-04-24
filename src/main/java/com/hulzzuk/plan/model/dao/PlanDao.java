package com.hulzzuk.plan.model.dao;

import com.hulzzuk.common.vo.Paging;
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

    public List<PlanVO> getPlanPage(String keyword, Paging paging) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("paging", paging);
        return sqlSessionTemplate.selectList("planMapper.getPlanPage", map);
    }

    public int countPlan(String keyword) {
        return sqlSessionTemplate.selectOne("planMapper.countPlan", keyword);
    }
}
