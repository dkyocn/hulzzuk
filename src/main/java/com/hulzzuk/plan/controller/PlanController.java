package com.hulzzuk.plan.controller;

import com.hulzzuk.plan.model.service.PlanService;
import com.hulzzuk.plan.model.vo.PlanVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    /**
     * plan 페이지를 조회하는 메서드
     * @param mv ModelAndView
     * @param page 조회 페이지
     * @param limit 한페이지 조회할 개수
     * @return ModelAndView
     */
    @RequestMapping("page.do")
    public ModelAndView getPlanPage(ModelAndView mv, HttpServletRequest request,
                                    @RequestParam(name = "page", required = false) String page,
                                    @RequestParam(name = "limit", required = false) String limit) {

        return planService.getPlanPage(request,page,limit,mv);
    }

    @RequestMapping("select.do")
    public ModelAndView getPlanById(ModelAndView mv,
                                    @RequestParam(name = "planId") long planId,
                                    @RequestParam(name = "page", required = false) String page) {
        return mv;
    }

    @RequestMapping("moveUpdate.do")
    public ModelAndView moveUpdatePage(ModelAndView mv, @RequestParam(name = "planId") long planId) {

        return planService.getPlanById(mv,planId);
    }

    @RequestMapping(value = "update.do", method =  RequestMethod.POST)
    public ModelAndView updatePlan(ModelAndView mv, HttpServletRequest httpServletRequest,
                                   @RequestParam(name = "planId")  long planId,
                                   @RequestParam(name = "planName") String title,
                                   @RequestParam(name = "selectedDates") String selectedDates,
                                   @RequestParam(name = "selectedLocations") String selectedLocations) {
        return planService.updatePlan(mv, httpServletRequest, planId, title, selectedDates, selectedLocations);
    }

    /**
     * 생성 페이지 이동 메서드
     * @return 생성 시퀀스 1 페이지
     */
    @RequestMapping("moveCreate.do")
    public String moveCreatePage() {
        return "plan/createPlanFirst";
    }

    /**
     * 시퀀스 1 생성 메서드
     * @param mv
     * @param request
     * @param title
     * @param selectedDates
     * @param selectedLocations
     * @return
     */
    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    public ModelAndView createPlan(ModelAndView mv, HttpServletRequest request,
                                   @RequestParam(name = "planName") String title,
                                   @RequestParam(name = "selectedDates") String selectedDates,
                                   @RequestParam(name = "selectedLocations") String selectedLocations) {

        return planService.createPlan(mv, request, title, selectedDates, selectedLocations);
    }

    @RequestMapping(value = "addLocation.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addLocation(@RequestBody Map<String, Object> addLocation) {
    	Map<String, Object> response = planService.addLocation(addLocation);
        return planService.addLocation(addLocation);
    }

    @RequestMapping(value = "deleteLocations.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteLocation(@RequestBody List<Map<String, Object>> deleteLocation) {
        return planService.deleteLocation(deleteLocation);
    }

    @RequestMapping(value = "updateLocations.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateLocation(@RequestBody Map<String, Object> updateLocation) {
        return planService.updateLocation(updateLocation);
    }

    @RequestMapping(value = "getPlLocations.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPlLocation(@RequestParam("planId") long planId) {
        return planService.getPlLocation(planId);
    }

    @RequestMapping("moveDelete.do")
    public ModelAndView moveDeletePage(ModelAndView mv,
                                       @RequestParam(name = "planId") long planId,
                                       @RequestParam(name = "message") String message,
                                       @RequestParam(name = "actionUrl") String actionUrl,
                                       @RequestParam(name = "width") int width,
                                       @RequestParam(name = "height") int height) {
        mv.addObject("planId", planId);
        mv.addObject("message", message);
        mv.addObject("actionUrl", actionUrl);
        mv.addObject("width", width);
        mv.addObject("height", height);

        mv.setViewName("common/popUp");

        return mv;
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public String deletePlan(@RequestParam(name = "planId") long planId) {
        // 삭제 로직
        planService.deletePlan(planId);
        // 삭제 후 목록 페이지로 리다이렉트
        return "success";
    }

    
    // 상세페이지 일정 추가
    @RequestMapping("LocDetailMovePlan.do")
    public ModelAndView getLocPlanList(ModelAndView mv, HttpServletRequest request) {
    	return planService.getLocPlanList(mv, request);
    }
    
    @RequestMapping("moveSharePopUp.do")
    public ModelAndView moveShareUserPopUp(ModelAndView mv, @RequestParam(name = "planId") long planId) {
        mv.addObject("planId", planId);
        mv.setViewName("plan/sharePopUp");
        return mv;
    }

    @RequestMapping(value = "shareUser.do", method =  RequestMethod.POST)
    @ResponseBody
    public Map<String, String> shareUser(HttpSession httpSession, @RequestParam(name = "planId") long planId,
                                         @RequestParam(name = "userId") String userId) {
        return planService.shareUser(httpSession,planId, userId);
    }
}
