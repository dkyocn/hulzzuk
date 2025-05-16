package com.hulzzuk.log.controller;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.util.FileSaveUtility;
import com.hulzzuk.common.vo.FileNameChange;
import com.hulzzuk.log.model.service.LogReviewService;
import com.hulzzuk.log.model.service.LogService;
import com.hulzzuk.log.model.vo.LogCommentVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/log")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;
   
    @Autowired
	private LogReviewService logReviewService;
	
	@Autowired
	private ServletContext context;

	@Autowired
	private FileSaveUtility fileSaveUtility;
	
	// 대표 이미지 저장 경로
	private String SAVE_DIR;

    // 로그 목록 조회 페이지
    @RequestMapping(value = "/page.do", method = RequestMethod.GET)
    public ModelAndView getLogPage(@RequestParam(name = "page", defaultValue = "1") int page) {
        int amount = 15; // 한 페이지당 15개씩 출력
        int start = (page - 1) * amount;

        int totalCount = logService.getLogCount(); // 전체 로그 수
        List<LogVO> logList = logService.getLogPage(start, amount); // 현재 페이지 로그 목록

        ModelAndView mav = new ModelAndView("logs/log"); // logs/log.jsp로 이동
        mav.addObject("logs", logList);
        mav.addObject("page", page);
        mav.addObject("totalCount", totalCount);
        mav.addObject("amount", amount);
        return mav;
    }
    // 좋아요 순으로 조회 (필터) 
    @RequestMapping("/loveRank.do")
    public ModelAndView getLoveRankLogPage() {
        List<LogVO> logList = logService.getLogListByLove();
        ModelAndView mav = new ModelAndView("logs/log");
        mav.addObject("logs", logList);
        return mav;
    }
    //내여행조회 (필터) 
    @RequestMapping(value = "/myTripLog.do", method = RequestMethod.GET)
    public ModelAndView selectMyTripLog(HttpSession session) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return new ModelAndView("redirect:/user/login.do");
        }

        List<LogVO> logList = logService.selectLogsByUser(loginUser.getUserId());

        ModelAndView mav = new ModelAndView("logs/log");  // 여기 통일
        mav.addObject("logs", logList);
        mav.addObject("filter", "my"); // 필터값 넘기기 (선택 유지용)
        return mav;
    }
    
    // 여행선택 페이지 띄우기
    @RequestMapping(value = "/selectPID.do", method = RequestMethod.GET)
    public ModelAndView SelectPlanIdPage(@RequestParam(name = "planId", required = false) Long planId,
                                         HttpSession session) {
        // 세션에서 로그인 유저 ID 가져오기
    	UserVO loginUser = (UserVO) session.getAttribute("loginUser");
    	//로그인 안된경우 -> 로그인 페이지
    	if(loginUser ==null ) {
    		// 로그인 후원래 가려던 페이지 기억해두기
    		session.setAttribute("redirectAfterLogin", "/log/selectPID.do");
    		return new ModelAndView("redirect:/user/login.do"); // 로그인페이지
    	}
    	
      //  String userId = loginUser.getUserId();
        // 로그인된경우:
        List<PlanVO> planList = logService.selectPlanIdList(loginUser.getUserId());
    	ModelAndView mav = new ModelAndView("logs/SelectPlanId"); // /WEB-INF/views/logs/SelectPlanId.jsp
        mav.addObject("planList", planList);
        
        //planId 넘어온경우:  바로 log 생성 화면으로 리다이렉
        if (planId != null) {
            System.out.println("선택된 planId: " + planId);
            mav.setViewName("redirect:/log/create.do?planId=" + planId); // 예시
        }
        
        return mav;
    }
    

    // 로그작성 폼 
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public ModelAndView showCreateLogPage(@RequestParam(name = "planId", required = false) Long planId) {
        ModelAndView mav = new ModelAndView("logs/logInsert");    // /WEB-INF/views/logs/logInsert.jsp
    
    	if (planId != null) {
    	    PlanVO plan = logService.getPlanById(planId);
    	 // 여행 장소 리스트 (day1, day2)
            List<LogReviewVO> day1Places = logService.getPlacesByPlanDay(planId, 1);
            List<LogReviewVO> day2Places = logService.getPlacesByPlanDay(planId, 2);
         // Day2 존재 여부 확인  //jsp 따
            boolean hasDay2 = plan.getPlanStartDate() != null && plan.getPlanEndDate() != null &&
                              !plan.getPlanStartDate().equals(plan.getPlanEndDate());
    	 
         mav.addObject("plan", plan);
         mav.addObject("day1PlaceList", day1Places);
         mav.addObject("day2PlaceList", day2Places);
         mav.addObject("hasDay2", !day2Places.isEmpty());// <-- 내용이 없으면 표시하지 않음.
         
         System.out.println("🔥 day1PlaceList size = " + day1Places.size());
         System.out.println("🔥 day2PlaceList size = " + day2Places.size());
     }
    	 
	     return mav;
    }

 // ✅ [Ajax] 로그 메타 정보 저장 (제목, 이미지 경로, 날짜 등)
 // ✅ logId는 시퀀스로 생성되어 반환됨
    @RequestMapping(value = "/saveMeta.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Long saveMetaOnly(@RequestBody(required = false) LogVO log, HttpSession session) {
        if (log == null) {
            System.out.println("GET 요청으로 들어왔거나 요청 본문이 없음");
            return null;
        }


    // 1. 로그인 유저 확인
    UserVO loginUser = (UserVO) session.getAttribute("loginUser");
    if (loginUser == null) return null;
    System.out.println("로그인 유저 확인: " + loginUser);

    // null 방지 로직 (✅ imagePath, logStartDate, logEndDate)
    if (log.getImagePath() == null || log.getImagePath().trim().isEmpty()) {
        log.setImagePath("/resources/images/logList/no_image.jpg");
    }
    if (log.getLogStartDate() == null) {
        log.setLogStartDate(new Date(System.currentTimeMillis()));
    }
    if (log.getLogEndDate() == null) {
        log.setLogEndDate(new Date(System.currentTimeMillis()));
    }

    // 로그인한 사용자 정보는 서버에서 보장해줘야 하므로 userId만 강제로 넣음
    log.setUserId(loginUser.getUserId());

    // 생성/수정 날짜는 서버 시각으로 
    log.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
    log.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));

    // ✅ 1. INSERT
    logService.insertLog(log);

    // ✅ 2. SELECT 최근 저장된 logId 반환
    Long savedLogId = logService.getRecentLogIdByUserIdAndTitle(log.getUserId(), log.getLogTitle());
    return savedLogId;
}


// ✅ 최근 저장된 logId 조회 전용 API
@RequestMapping(value = "/log/getRecentLogId.do", method = RequestMethod.POST)
@ResponseBody
public Long getRecentLogId(@RequestBody Map<String, String> params) {
    String userId = params.get("userId");
    String logTitle = params.get("logTitle");
    return logService.getRecentLogIdByUserIdAndTitle(userId, logTitle);
}



   //후기 데이터만 JSON으로 받아서 저장
	/**
    * [Ajax] 전체 리뷰 리스트 JSON으로 받아 저장 (대표 이미지, 제목 제외)
    * - /log/reviewInsertAll.do  후기 JSON 저장 전용 (Ajax)
    */
   @RequestMapping(value = "/reviewInsertAll.do", method = RequestMethod.POST)
   @ResponseBody
   public String insertAllReviewsAjax(@RequestBody List<LogReviewVO> reviewList) {
       int result = logReviewService.insertLogReviews(reviewList);
       return result > 0 ? "success" : "fail";
   }
   
//Ajax 이미지 업로드
   @RequestMapping(value = "/uploadImage.do", method = RequestMethod.POST)
   @ResponseBody
   public String uploadImage(@RequestParam("logImage") MultipartFile file, HttpServletRequest request) {
	    if (file.isEmpty()) {
	        return "/hulzzuk/resources/images/logList/no_image.jpg";
	    }

       try {
    	// 서버에 저장될 실제 경로
    	   String uploadDir = request.getServletContext().getRealPath("/resources/images/logList/");
           File dir = new File(uploadDir);
           if (!dir.exists()) dir.mkdirs();

        /// 고유 파일명 생성 (확장자 포함)
           String originalFileName = file.getOriginalFilename();
           String extension = "";
           if (originalFileName != null && originalFileName.contains(".")) {
               extension = originalFileName.substring(originalFileName.lastIndexOf("."));
           }
           String savedFileName = UUID.randomUUID().toString() + extension;  // 확장자 붙이기

           // 파일 저장
           File destination = new File(uploadDir + File.separator + savedFileName);
           file.transferTo(destination);

           // 웹에서 접근 가능한 경로 반환
           return "/resources/images/logList/" + savedFileName;
           
       } catch (Exception e) {
           e.printStackTrace();
           return "/hulzzuk/resources/images/logList/no_image.jpg";
       }
   }
   
   
// 로그 상세보기 + 후기 리스트
@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
public ModelAndView viewLogDetail(@RequestParam("logId") Long logId, HttpSession session) {
    UserVO loginUser = (UserVO) session.getAttribute("loginUser");
    if (loginUser == null) {
        session.setAttribute("redirectAfterLogin", "/log/detail.do?logId=" + logId);
        return new ModelAndView("redirect:/user/login.do");
    }

    // 1. 로그 정보 가져오기
    LogVO log = logService.getLogById(logId);

    // 2. 리뷰 정보 가져오기
    List<LogReviewVO> reviews = logService.getReviewsByLogId(logId);

    // 3. 댓글 목록 가져오기
    List<LogCommentVO> comments = logService.getCommentsByLogId(logId);

    // 4. 댓글 ID만 추출 (대댓글 조회용)
    List<Long> commentIdList = comments.stream()
        .map(LogCommentVO::getCommentId)
        .collect(Collectors.toList());

    // 5. 대댓글 목록 가져오기 (commentIdList가 있을 경우만)
    List<LogCommentVO> replies;
    if (commentIdList == null || commentIdList.isEmpty()) {
        replies = Collections.emptyList();
    } else {
        replies = logService.getRepliesByCommentIds(commentIdList);
    }

    // 로그 확인용 출력
    System.out.println("logVO = " + log);

    // View에 데이터 전달
    ModelAndView mav = new ModelAndView("logs/logDetailView");
    mav.addObject("log", log);
    mav.addObject("reviews", reviews);
    mav.addObject("comments", comments);
    mav.addObject("replies", replies);
    return mav;
}
    
    
    //댓글등록 
    @RequestMapping(value = "/commentInsert.do", method = RequestMethod.POST)
    public String insertComment(LogCommentVO comment, HttpSession session) {
        comment.setUserId((String) session.getAttribute("loginId"));
        logService.insertComment(comment);
        return "redirect:/log/detail.do?logId=" + comment.getLogId();
    }

}

