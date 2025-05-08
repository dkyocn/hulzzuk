package com.hulzzuk.plan.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.plan.model.dao.PlanDao;
import com.hulzzuk.plan.model.vo.PlanLocVO;
import com.hulzzuk.plan.model.vo.PlanUserVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import com.hulzzuk.user.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hulzzuk.location.model.enumeration.LocationEnum.ACCO;

@Service("planService")
public class PlanServiceImpl implements PlanService {

    private static final Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);

    @Autowired
    private PlanDao planDao;

    @Override
    public ModelAndView getPlanPage(HttpServletRequest request, String page, String slimit, ModelAndView mv) {

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

        UserVO loginUser = (UserVO) request.getSession().getAttribute("loginUser");

        if (loginUser != null) {
            // 검색결과가 적용된 총 목록 갯수 조회해서, 총 페이지 수 계산함
            int listCount = planDao.countPlan(loginUser.getUserId());
            // 페이지 관련 항목들 계산 처리
            Paging paging = new Paging(null,listCount, limit, currentPage, "page.do");
            paging.calculate();

            // 마이바티스 매퍼에서 사용되는 메소드는 Object 1개만 전달할 수 있음

            // 서비스 모델로 페이징 적용된 목록 조회 요청하고 결과받기
            List<PlanVO> planPage = planDao.getPlanPage(loginUser.getUserId(),paging);

            if (planPage != null && !planPage.isEmpty()) { // 조회 성공시
                // ModelAndView : Model + View
                mv.addObject("list", planPage); // request.setAttribute("list", list) 와 같음
                mv.addObject("paging", paging);

                mv.setViewName("plan/planPage");
            }
        } else {
            // 로그인 요청
            mv.addObject("fail", "N");
            mv.setViewName("user/loginPage");
        }


        return mv;
    }

    @Override
    public ModelAndView createPlan(ModelAndView modelAndView, HttpServletRequest request, String title, String selectedDate, String selectedLocations) {

        String userID = "";
        long planId = 0;
        PlanVO planVO = new PlanVO();

        // selectedDate sql.Date로 변경
        String[] selectDates = selectedDate.split(",");

        // request에서 현재 로그인된 사용자 찾기
        UserVO loginUser = (UserVO) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            userID = loginUser.getUserId();

            planVO = switch (selectDates.length) {
                case 1 ->
                        new PlanVO(title, selectedLocations, Date.valueOf(selectDates[0]), Date.valueOf(selectDates[0]));
                case 2 ->
                        new PlanVO(title, selectedLocations, Date.valueOf(selectDates[0]), Date.valueOf(selectDates[1]));
                default -> throw new IllegalArgumentException(ErrorCode.PLAN_OUT_OF_BOUNDS.getMessage());
            };


            // Plan 저장
            planDao.insertPlan(planVO);
            planId = planVO.getPlanId();
            if(planId == 0) {
                throw new IllegalArgumentException(ErrorCode.PLAN_INSERT_ERROR.getMessage());
            }

            // TB_PL_USER 저장
            if(planDao.insertPlanUser(new PlanUserVO(userID,planId)) == 0) {
                throw new IllegalArgumentException(ErrorCode.PL_USER_INSERT_ERROR.getMessage());
            }

            modelAndView.addObject("plan", planVO);
            modelAndView.setViewName("plan/createPlanSecond");

        } else {
            // 재로그인 요청
            modelAndView.addObject("fail", "Y");
            modelAndView.setViewName("user/loginPage");
        }

        return modelAndView;
    }

    @Override
    public ModelAndView createPlanSecond(ModelAndView mv, HttpServletRequest request, long planId, String day1Locations, String day2Locations) {

        log.info("planId  : {}", planId);
        log.info("day1Locations : {}", day1Locations);
        log.info("day2Locations : {}", day2Locations);
        try {
            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> day1 = objectMapper.readValue(day1Locations, List.class);
            for(int i = 0; i < day1.size(); i++) {
                Map<String, Object> map = day1.get(i);
                log.info("locEnum : {}", map.get("locEnum"));
                switch ((String)map.get("locEnum")){
                    case "ACCO":
                        planDao.insertPlanLoc(new PlanLocVO(planId, (String) map.get("id"),null,null,i+1,1));
                        break;
                    case "REST":
                        planDao.insertPlanLoc(new PlanLocVO(planId,null, null, (String) map.get("id"),i+1,1));
                        break;
                    case "ATTR":
                        planDao.insertPlanLoc(new PlanLocVO(planId, null, (String) map.get("id"),null,i+1,1));
                        break;
                        default:
                            throw new IllegalArgumentException(ErrorCode.PL_TRIP_INSERT_ERROR.getMessage());

                }

            }
            if(!day1Locations.isEmpty()) {
                List<Map<String, Object>> day2 = objectMapper.readValue(day2Locations, List.class);
                for(int i = 0; i < day2.size(); i++) {
                    Map<String, Object> map = day2.get(i);
                    log.info("locEnum : {}", map.get("locEnum"));
                    log.info("day2 : {}", map.get("day"));
                    switch ((String)map.get("locEnum")){
                        case "ACCO":
                            planDao.insertPlanLoc(new PlanLocVO(planId, (String) map.get("id"),null,null,i+1,2));
                            break;
                        case "REST":
                            planDao.insertPlanLoc(new PlanLocVO(planId,null, null, (String) map.get("id"),i+1,2));
                            break;
                        case "ATTR":
                            planDao.insertPlanLoc(new PlanLocVO(planId, null, (String) map.get("id"),null,i+1,2));
                            break;
                        default:
                            throw new IllegalArgumentException(ErrorCode.PL_TRIP_INSERT_ERROR.getMessage());

                    }
                }
            }

           return getPlanPage(request,null,null,mv);
        } catch (Exception e) {
            throw new RuntimeException(ErrorCode.PLAN_INSERT_ERROR.getMessage());
        }
    }

    @Override
    public void deletePlan(long planId) {
        int successYN = planDao.deletePlan(planId);

        if (successYN == 0) {
            throw new IllegalArgumentException(ErrorCode.PLAN_DELETE_ERROR.getMessage());
        }
    }
}
