package com.hulzzuk.plan.model.service;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.plan.model.vo.PlanLocVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlanService {

    // 일정 단일 조회
    ModelAndView getPlanById(ModelAndView modelAndView ,long planId);
    // 일정 페이지 조회
    ModelAndView getPlanPage(HttpServletRequest request, String page, String limit, ModelAndView modelAndView);
    // 일정 생성 - 시퀀스 1
    ModelAndView createPlan(ModelAndView modelAndView, HttpServletRequest request, String title, String selectedDatesJson, String selectedLocationsJson);
    // 일정 생성 - 시퀀스 2
    ModelAndView createPlanSecond(ModelAndView modelAndView, HttpServletRequest request, long planId, String day1Locations, String day2Locations);
    // 장소 저장
    Map<String, Object> addLocation(Map<String, Object> addLocation);
    // 장소 삭제
    Map<String, Object> deleteLocation(List<Map<String, Object>> deleteLocation);
    // 장소 수정
    Map<String, Object> updateLocation( Map<String, Object> updateLocation);
    // 장소 조회
    Map<String, Object> getPlLocation(long planId);
    // 일정 수정 - 시퀀스 1
    ModelAndView updatePlan(ModelAndView modelAndView, HttpServletRequest request, long planId, String title, String selectedDatesJson, String selectedLocationsJson);
    // 일정 삭제
    void deletePlan(long planId);
    // 상세페이지 일정 추가
	ModelAndView getLocPlanList(ModelAndView mv, HttpServletRequest request);
}
