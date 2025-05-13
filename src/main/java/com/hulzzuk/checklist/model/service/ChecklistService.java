package com.hulzzuk.checklist.model.service;

import com.hulzzuk.checklist.model.vo.ChecklistVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

public interface ChecklistService {

    // 체크리스트 조회
    ModelAndView getChecklist(ModelAndView mv, long planId);
    Map<String, List<ChecklistVO>> getChecklistVOList(long planId);
    // 체크리스트 항목 생성
    ChecklistVO createCheckListContent(ChecklistVO checklistVO);
    // 체크리스트 체크 여부
    Map<String, String> updateCheckYN(ChecklistVO checklistVO);
    // 체크리스트 항목 삭제
    Map<String, String> deleteChecklistContent(long id);
}
