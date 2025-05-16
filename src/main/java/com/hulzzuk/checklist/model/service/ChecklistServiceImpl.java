package com.hulzzuk.checklist.model.service;

import com.hulzzuk.checklist.model.dao.ChecklistDao;
import com.hulzzuk.checklist.model.vo.ChecklistVO;
import com.hulzzuk.common.enumeration.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("checklistServiceImpl")
public class ChecklistServiceImpl implements ChecklistService {

    @Autowired
    private ChecklistDao checklistDao;

    @Override
    public ModelAndView getChecklist(ModelAndView mv, long planId) {

        List<ChecklistVO> checklistVOList1 = checklistDao.getChecklistList(planId,1);
        List<ChecklistVO> checklistVOList2 = checklistDao.getChecklistList(planId,2);
        List<ChecklistVO> checklistVOList3 = checklistDao.getChecklistList(planId,3);

        mv.addObject("checklistList1", checklistVOList1);
        mv.addObject("checklistList2", checklistVOList2);
        mv.addObject("checklistList3", checklistVOList3);
        mv.addObject("planId", planId);
        mv.setViewName("checklist/checklistPopup");
        return mv;
    }

    @Override
    public Map<String, List<ChecklistVO>> getChecklistVOList(long planId) {
        Map<String, List<ChecklistVO>> response = new HashMap<>();
        response.put("checklistList", checklistDao.getChecklistList(planId,0));
        return response;
    }

    @Override
    public ChecklistVO createCheckListContent(ChecklistVO checklistVO) {
        checklistDao.createChecklistContent(checklistVO);

        if(checklistVO.getCheckId() == 0) {
            throw new RuntimeException(ErrorCode.CHECKLIST_INSERT_ERROR.getMessage());
        }

        return checklistVO;
    }

    @Override
    public Map<String, String> updateCheckYN(ChecklistVO checklistVO) {
        HashMap<String, String> map = new HashMap<>();
        if (checklistDao.updateCheckYN(checklistVO) == 0) {
            throw new RuntimeException(ErrorCode.CHECKLIST_UPDATE_ERROR.getMessage());
        } else {
            map.put("status",  "success");
        }
        return map;
    }

    @Override
    public Map<String, String> deleteChecklistContent(long checkId) {
        HashMap<String, String> map = new HashMap<>();
        if(checklistDao.deleteChecklistContent(checkId) == 0) {
            throw new RuntimeException(ErrorCode.CHECKLIST_DELETE_ERROR.getMessage());
        } else {
            map.put("status",  "success");
        }
        return map;
    }
}
