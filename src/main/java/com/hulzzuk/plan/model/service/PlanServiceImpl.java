package com.hulzzuk.plan.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.plan.model.dao.PlanDao;
import com.hulzzuk.plan.model.vo.PlanLocVO;
import com.hulzzuk.plan.model.vo.PlanUserVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.service.UserService;
import com.hulzzuk.user.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;


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
            Paging paging = new Paging(null, listCount, limit, currentPage, "page.do");
            paging.calculate();

            // 마이바티스 매퍼에서 사용되는 메소드는 Object 1개만 전달할 수 있음

            // 서비스 모델로 페이징 적용된 목록 조회 요청하고 결과받기
            List<PlanVO> planPage = planDao.getPlanPage(loginUser.getUserId(), paging);
                // ModelAndView : Model + View
                mv.addObject("list", planPage); // request.setAttribute("list", list) 와 같음
                mv.addObject("paging", paging);

                mv.setViewName("plan/planPage");
        } else {
            // 로그인 요청
            mv.addObject("fail", "Y");
            mv.setViewName("redirect:/user/loginSelect.do");
        }


        return mv;
    }

    @Override
    public ModelAndView createPlan(ModelAndView modelAndView, HttpServletRequest request, String title, String selectedDate, String selectedLocations) {

        String userID;
        long planId;
        PlanVO planVO;

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
            if (planId == 0) {
                throw new IllegalArgumentException(ErrorCode.PLAN_INSERT_ERROR.getMessage());
            }

            // TB_PL_USER 저장
            if (planDao.insertPlanUser(new PlanUserVO(userID, planId)) == 0) {
                throw new IllegalArgumentException(ErrorCode.PL_USER_INSERT_ERROR.getMessage());
            }

            modelAndView.addObject("plan", planVO);
            modelAndView.setViewName("plan/planSecond");

        } else {
            // 재로그인 요청
            modelAndView.addObject("fail", "Y");
            modelAndView.setViewName("user/loginPage");
        }

        return modelAndView;
    }

    @Override
    public Map<String, Object> addLocation(Map<String, Object> addLocation) {
    	
    	
        try {
        	int seq;
            Long planId = Long.parseLong(addLocation.get("planId").toString());
            String locId = addLocation.get("locId").toString();
            String locEnum = addLocation.get("locEnum").toString();
            int planDay = Integer.parseInt(addLocation.get("planDay").toString());
            
            System.out.println("넘어온 planId: " + planId);
            System.out.println("넘어온 planDay: " + planDay);
            
            if(addLocation.get("seq")  ==  null) {
            	int currentSeq = planDao.findseq(planId, planDay);
            	System.out.println("현재 시퀀스 개수: " + currentSeq); // 또는 로그로 출력
                seq = currentSeq + 1;
            }else {
            	seq = Integer.parseInt(addLocation.get("seq").toString());
            }

            PlanLocVO planLocVO = new PlanLocVO();

            switch (locEnum) {
                case "ACCO":
                    planLocVO = new PlanLocVO(planId, locId, null, null, planDay, seq);
                    break;
                case "REST":
                    planLocVO = new PlanLocVO(planId, null, null, locId, planDay, seq);
                    break;
                case "ATTR":
                    planLocVO = new PlanLocVO(planId, null, locId, null, planDay, seq);
                    break;
            }

            // 전달 받은 planLocVO 저장
            planDao.insertPlanLoc(planLocVO);

            Map<String, Object> map = new HashMap<>();
            map.put("successYN", true);
            return map;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorCode.PL_TRIP_INSERT_ERROR.getMessage());
        }
    }

    @Override
    public Map<String, Object> deleteLocation(List<Map<String, Object>> deleteLocations) {

        deleteLocations.forEach(map -> {
            if(planDao.deletePlanLoc(map) == 0) {
                throw new RuntimeException(ErrorCode.PL_TRIP_DELETE_ERROR.getMessage());
            }
        });

        Map<String, Object> map = new HashMap<>();
        map.put("successYN", true);
        return map;
    }

    @Override
    public Map<String, Object> updateLocation( Map<String, Object> updateLocation) {

        // planId에 맞는 loc 일괄 삭제
        if(planDao.deletePlanLocById(Long.parseLong(updateLocation.get("planId").toString())) == 0) {
            throw new RuntimeException(ErrorCode.PL_TRIP_DELETE_ERROR.getMessage());
        }

        // trip pl 일괄 등록
        // day 1 등록
        insertPlanLoc((List<Map<String, Object>>) updateLocation.get("day1Locations"), 1, Long.parseLong(updateLocation.get("planId").toString()));
        // day 2 등록
        if(updateLocation.size() == 3) { // day 2가 있을 때만 등록
            insertPlanLoc((List<Map<String, Object>>) updateLocation.get("day2Locations"), 2, Long.parseLong(updateLocation.get("planId").toString()));
        }

       Map<String, Object> map = new HashMap<>();
        map.put("successYN", true);
        return map;
    }

    private void insertPlanLoc(List<Map<String, Object>> dayLocations, int planDay, long planId) {
        for (int i = 0; i < dayLocations.size(); i++) {
            Map<String, Object> map = dayLocations.get(i);
            switch ((String) map.get("category")) {
                case "ACCO":
                    planDao.insertPlanLoc(new PlanLocVO(planId, (String) map.get("id"), null, null, planDay, i + 1));
                    break;
                case "REST":
                    planDao.insertPlanLoc(new PlanLocVO(planId, null, null, (String) map.get("id"), planDay, i + 1));
                    break;
                case "ATTR":
                    planDao.insertPlanLoc(new PlanLocVO(planId, null, (String) map.get("id"), null, planDay, i + 1));
                    break;
                default:
                    throw new IllegalArgumentException(ErrorCode.PL_TRIP_INSERT_ERROR.getMessage());

            }
        }
    }

    @Override
    public Map<String, Object> getPlLocation(long planId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("planLoc",planDao.getPlanLocByPlanId(planId));
        return map;
    }

    @Override
    public ModelAndView updatePlan(ModelAndView modelAndView, HttpServletRequest request, long planId, String title, String selectedDatesJson, String selectedLocations) {

        PlanVO planVO;

        // selectedDate sql.Date로 변경
        String[] selectDates = selectedDatesJson.split(",");

        // request에서 현재 로그인된 사용자 찾기
        UserVO loginUser = (UserVO) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {

            planVO = switch (selectDates.length) {
                case 1 ->
                        new PlanVO(title, selectedLocations, Date.valueOf(selectDates[0]), Date.valueOf(selectDates[0]));
                case 2 ->
                        new PlanVO(title, selectedLocations, Date.valueOf(selectDates[0]), Date.valueOf(selectDates[1]));
                default -> throw new IllegalArgumentException(ErrorCode.PLAN_OUT_OF_BOUNDS.getMessage());
            };

            // planVo id 저장
            planVO.setPlanId(planId);

            // Plan 수정
            planDao.updatePlan(planVO);
            if (planVO.getPlanId() == 0) {
                throw new IllegalArgumentException(ErrorCode.PLAN_UPDATE_ERROR.getMessage());
            }


            modelAndView.addObject("planLoc", planDao.getPlanLocByPlanId(planId));
            modelAndView.addObject("plan", planVO);
            modelAndView.setViewName("plan/planSecond");

        } else {
            // 재로그인 요청
            modelAndView.addObject("fail", "Y");
            modelAndView.setViewName("user/loginPage");
        }

        return modelAndView;
    }

    @Override
    public ModelAndView getPlanById (ModelAndView modelAndView,long planId){
        PlanVO planVO = planDao.getPlanById(planId);

        modelAndView.addObject("plan", planVO);
        modelAndView.setViewName("plan/updatePlanFirst");
        return modelAndView;
    }

    @Override
    public void deletePlan ( long planId){
        int successYN = planDao.deletePlan(planId);

        if (successYN == 0) {
            throw new IllegalArgumentException(ErrorCode.PLAN_DELETE_ERROR.getMessage());
        }
    }

    // 상세페이지 일정 추가
	@Override
	public ModelAndView getLocPlanList(ModelAndView mv, HttpServletRequest request) {
		UserVO loginUser = (UserVO) request.getSession().getAttribute("loginUser");
		 if (loginUser != null) {
			 List<PlanVO> planList = planDao.getLocPlanList(loginUser.getUserId());
             // ModelAndView : Model + View
             mv.addObject("planList", planList); // request.setAttribute("list", list) 와 같음
             mv.setViewName("plan/locPlanList");
		 }else {
	            // 로그인 요청
	            mv.addObject("fail", "Y");
	            mv.setViewName("user/loginPage");
	        }
		return mv;
	}

    @Override
    public  Map<String, String> shareUser(HttpSession httpSession, long planId, String userId) {

        Map<String, String> map = new HashMap<>();

        String authUserId = (String) httpSession.getAttribute("authUserId");

        // 유저 조회
        UserVO shareUserVO = userDao.selectUser(userId);
        UserVO loginUserVO = userDao.selectUser(authUserId);
        PlanVO planVO = planDao.getPlanById(planId);

        PlanUserVO planUserVO = new PlanUserVO(userId, planId);



        // 있으면 공유 없으면 다른 사용자 재 입력
        if (shareUserVO == null) {
            map.put("successYN", "false");
        } else {
            if(planDao.getPlanUser(planUserVO) != null) {
                map.put("successYN", "share");
            } else {
                if(planVO == null) {
                    throw new IllegalArgumentException(ErrorCode.PLAN_NOT_FOUND.getMessage());
                } else {
                    // 저장
                    if(planDao.insertPlanUser(planUserVO) == 0 ) {
                        throw new RuntimeException(ErrorCode.PL_USER_INSERT_ERROR.getMessage());
                    }

                    userService.sendMail(userId,"[HULZZUK] 일정 공유 안내", loginUserVO.getUserNick()+" 이(가) "+planVO.getPlanTitle()+" 일정을 공유하였습니다.\n\n(본 메일은 발신 전용입니다.)");

                    map.put("successYN", "true");
                }
            }
        }
        return map;
    }
}
