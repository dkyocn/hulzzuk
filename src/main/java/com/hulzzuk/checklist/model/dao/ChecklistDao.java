package com.hulzzuk.checklist.model.dao;

import com.hulzzuk.checklist.model.vo.ChecklistVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("ChecklistDao")
public class ChecklistDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    // 체크리스트 조회
    public List<ChecklistVO> getChecklistList(long planId, int num) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("planId", planId);
        map.put("num", num);

        return sqlSessionTemplate.selectList("checkListMapper.getCheckListList", map);
    }

    // 체크리스트 항목 생성
    public void createChecklistContent(ChecklistVO checklistVO) {
        sqlSessionTemplate.insert("checkListMapper.createChecklistContent", checklistVO);
    }

    // 체크리스트 항목 체크 여부
    public int updateCheckYN(ChecklistVO checklistVO) {
        return sqlSessionTemplate.update("checkListMapper.updateCheckYN", checklistVO);
    }

    // 체크리스트 항목 삭제
    public int deleteChecklistContent(long checkId) {
        return sqlSessionTemplate.delete("checkListMapper.deleteChecklistContent", checkId);
    }
}
