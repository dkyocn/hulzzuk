package com.hulzzuk.log.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hulzzuk.log.model.service.LogPlaceService;
import com.hulzzuk.log.model.service.LogReviewService;
import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.plan.model.service.PlanService;
import com.hulzzuk.plan.model.vo.PlanVO;

@Controller
@RequestMapping("/logReview")
public class LogReviewController {

    @Autowired
    private PlanService planService;

    @Autowired
    private LogReviewService logReviewService;

    
    private LogPlaceService logPlaceService;
	  
    //로그작성을 위한 planID세부사항 불러오
    @RequestMapping("insertPage.do")
    public String showLogInsertPage(@RequestParam("planId") int planId, Model model) {
        List<LogPlaceVO> day1Places = logPlaceService.getPlacesByPlanDay(planId, 1);  // day1 리스트 조회
        List<LogPlaceVO> day2Places = logPlaceService.getPlacesByPlanDay(planId, 2);  // day2 리스트 조회
        PlanVO plan = logPlaceService.fetchPlanById(planId); // 플랜 정보 조회

        model.addAttribute("plan", plan);
        model.addAttribute("day1PlaceList", day1Places);
        model.addAttribute("day2PlaceList", day2Places);

        return "logs/logInsert";  //logs/logInsert.jsp
    }
	
	
	
	
}
