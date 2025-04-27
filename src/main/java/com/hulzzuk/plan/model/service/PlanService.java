package com.hulzzuk.plan.model.service;

import com.hulzzuk.common.vo.Paging;
import org.springframework.web.servlet.ModelAndView;

public interface PlanService {

    ModelAndView getPlanPage(String keyword, String page, String limit, ModelAndView modelAndView);
}
