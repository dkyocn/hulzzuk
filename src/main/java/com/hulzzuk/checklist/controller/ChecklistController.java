package com.hulzzuk.checklist.controller;

import com.hulzzuk.checklist.model.service.ChecklistService;
import com.hulzzuk.checklist.model.vo.ChecklistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chkList")
public class ChecklistController {

    @Autowired
    private ChecklistService checklistService;

    /**
     * 체크리스트 항목 조회
     * @param mv
     * @param planId
     * @return
     */
    @RequestMapping("list.do")
    public ModelAndView getChecklist(ModelAndView mv, @RequestParam("planId") long planId) {
        return checklistService.getChecklist(mv, planId);
    }

    /**
     * 체크리스트 vo 리스트 조회
     * @param planId
     * @return
     */
    @RequestMapping("voList.do")
    @ResponseBody
    public Map<String, List<ChecklistVO>> getChecklistVOList(@RequestParam("planId") long planId) {
        return checklistService.getChecklistVOList(planId);
    }

    /**
     * 체크리스트 항목 생성
     * @param checklistVO
     * @return
     */
    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    @ResponseBody
    public ChecklistVO createCheckListContent(@RequestBody ChecklistVO checklistVO) {
        return checklistService.createCheckListContent(checklistVO);
    }

    /**
     * 체크리스트 체크여부
     * @param checklistVO
     * @return
     */
    @RequestMapping(value = "update.do", method =  RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateCheckYN(@RequestBody ChecklistVO checklistVO) {
        return checklistService.updateCheckYN(checklistVO);
    }

    /**
     * 체크리스트 항목 삭제
     * @param checkId
     */
    @RequestMapping(value = "delete.do", method =  RequestMethod.POST)
    @ResponseBody
    public Map<String, String> deleteChecklistContent(@RequestParam("checkId") long checkId) {
        return checklistService.deleteChecklistContent(checkId);
    }
}
