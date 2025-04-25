package com.hulzzuk.plan.controller;

import com.hulzzuk.plan.model.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @RequestMapping("page.do")
    public ModelAndView getPlanPage(ModelAndView mv,
                                    @RequestParam(name = "action", required = false) String action,
                                    @RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "page", required = false) String page,
                                    @RequestParam(name = "limit", required = false) String limit) {

        return planService.getPlanPage(action,keyword,page,limit,mv);
    }

    @RequestMapping("select.do")
    public void getPlanById() {

    }
}
