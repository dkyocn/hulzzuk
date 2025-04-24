package com.hulzzuk.plan.model.service;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.plan.model.dao.PlanDao;
import com.hulzzuk.plan.model.vo.PlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service("planService")
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Override
    public ModelAndView getPlanPage(String action, String keyword, String page, String slimit, ModelAndView mv) {

        // 페이징 처리
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }

        // 한 페이지에 출력할 목록 갯수 기본 10개로 지정함
        int limit = 10;
        if (slimit != null) {
            limit = Integer.parseInt(slimit);
        }

        // 검색결과가 적용된 총 목록 갯수 조회해서, 총 페이지 수 계산함
        int listCount = planDao.countPlan(keyword);
        // 페이지 관련 항목들 계산 처리
        Paging paging = new Paging(keyword, listCount, limit, currentPage, "page.do");
        paging.calculate();

        // 마이바티스 매퍼에서 사용되는 메소드는 Object 1개만 전달할 수 있음

        // 서비스 모델로 페이징 적용된 목록 조회 요청하고 결과받기
        List<PlanVO> planPage = planDao.getPlanPage(keyword, paging);

        if (planPage != null && planPage.size() > 0) { // 조회 성공시
            // ModelAndView : Model + View
            mv.addObject("list", planPage); // request.setAttribute("list", list) 와 같음
            mv.addObject("paging", paging);
            mv.addObject("action", action);
            mv.addObject("keyword", keyword);

            mv.setViewName("plan/planPage");
        }

        return mv;
    }
}
