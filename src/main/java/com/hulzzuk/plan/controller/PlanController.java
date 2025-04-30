package com.hulzzuk.plan.controller;

import com.hulzzuk.plan.model.service.PlanService;
import com.hulzzuk.plan.model.vo.PlanVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

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
    public ModelAndView moveUpdatePage(ModelAndView mv, @RequestParam(name = "planId") long planId,
                                       @RequestParam(name = "page", required = false) String page) {

        // id로 plan 조회

        mv.setViewName("plan/updatePlan");

        return mv;
    }

    @RequestMapping("update.do")
    public ModelAndView updatePlan(ModelAndView mv, PlanVO planVO) {

        mv.addObject("planVO", planVO);

        mv.setViewName("plan/updatePlan");
        return mv;
    }

    @RequestMapping("moveCreate.do")
    public String moveCreatePage() {
        return "plan/createPlanFirst";
    }

    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    public ModelAndView createPlan(ModelAndView mv, HttpServletRequest request,
                                   @RequestParam(name = "planName") String title,
                                   @RequestParam(name = "selectedDates") String selectedDates,
                                   @RequestParam(name = "selectedLocations") String selectedLocations) {

        return planService.createPlan(mv, request, title, selectedDates, selectedLocations);
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
}
