package com.hulzzuk.plan.controller;

import com.hulzzuk.plan.model.service.PlanService;
import com.hulzzuk.plan.model.vo.PlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    /**
     * plan 페이지를 조회하는 메서드
     * @param mv
     * @param keyword
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("page.do")
    public ModelAndView getPlanPage(ModelAndView mv,
                                    @RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "page", required = false) String page,
                                    @RequestParam(name = "limit", required = false) String limit) {

        return planService.getPlanPage(keyword,page,limit,mv);
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
        return "plan/createPlan";
    }

    @RequestMapping("create.do")
    public void createPlan() {

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
//        planService.deletePlan(planId);
        // 삭제 후 목록 페이지로 리다이렉트
        return "success";
    }
}
