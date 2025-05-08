package com.hulzzuk.plan.model.service;

import com.hulzzuk.common.vo.Paging;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

public interface PlanService {

    // 일정 페이지 조회
    ModelAndView getPlanPage(HttpServletRequest request, String page, String limit, ModelAndView modelAndView);
    // 일정 생성 - 시퀀스 1
    ModelAndView createPlan(ModelAndView modelAndView, HttpServletRequest request, String title, String selectedDatesJson, String selectedLocationsJson);
    // 일정 생성 - 시퀀스 2
    ModelAndView createPlanSecond(ModelAndView modelAndView, HttpServletRequest request, long planId, String day1Locations, String day2Locations);
    // 일정 삭제
    void deletePlan(long planId);
}
