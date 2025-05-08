package com.hulzzuk.log.model.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.vo.FileNameChange;
import com.hulzzuk.log.model.service.LogService;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.user.model.vo.UserVO;
import com.hulzzuk.plan.model.vo.PlanVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/log")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    // 로그 목록 조회 페이지
    @GetMapping("/page.do")
    public ModelAndView getLogPage(@RequestParam(name="page", defaultValue="1") int page) {
        int amount = 15; // 한 페이지당 15개
        int start = (page - 1) * amount;

        int totalCount = logService.getLogCount(); // 총 데이터 수
        List<LogVO> logList = logService.getLogPage(start, amount);

        ModelAndView mav = new ModelAndView("logs/log"); // /WEB-INF/views/logs/log.jsp
        mav.addObject("logs", logList);
        mav.addObject("page", page);
        mav.addObject("totalCount", totalCount);
        mav.addObject("amount", amount);
        return mav;
    }
    
    // 장소 로그 조회하기
    


    // GET 요청으로 작성 여행선택 페이지 띄우기
    @GetMapping("/selectPID.do")
    public ModelAndView SelectPlanIdPage(
    		@RequestParam(name="planId", required = false) Long  planId, // param으로 
    		HttpSession session) {
        // 세션에서 로그인 유저 ID 가져오기
    	UserVO loginUser = (UserVO) session.getAttribute("loginUser");
    	if(loginUser ==null ) {
    		return new ModelAndView("redirect:/user/login.do"); // 로그인페이지
    	}
    	
      //  String userId = loginUser.getUserId();
       
    	 ModelAndView mav = new ModelAndView("logs/SelectPlanId"); // /WEB-INF/views/logs/SelectPlanId.jsp
       
        List<PlanVO> planList = logService.selectPlanIdList(loginUser.getUserId());
        mav.addObject("planList", planList);
        
        if (planId != null) {
            // planId로 무언가 로직 실행하거나 redirect
            System.out.println("선택된 planId: " + planId);
            
            mav.setViewName("redirect:/log/create.do?planId=" + planId); // 예시
        }
        
        return mav;
    }
    

    // GET 요청으로 작성 폼 띄우기
    @GetMapping("/create.do")
    public ModelAndView showCreateLogPage(@RequestParam(name="planId", required=false) Long planId){
    	 ModelAndView mav = new ModelAndView("logs/logCreate"); // /WEB-INF/views/logs/logCreate.jsp
  
    	 //if (planId != null) {
    	 //       List<PlaceVO> placeList = logService.getPlaceListByPlanId(planId);
    	  //      mav.addObject("placeList", placeList);
    	 //   }
    	 
    	return mav;
    }

    // 데이터 저장 처리
    @RequestMapping(value="/create.do", method=RequestMethod.POST)
    public ModelAndView getlogCreatePage(LogVO logVo, 
                                         @RequestParam(name="ofile", required=false) MultipartFile mfile, 
                                         HttpServletRequest request) {
        logger.info("create.do: {}", logVo);

        // 게시글 첨부파일저장폴더 경로 저장
        String savePath = request.getSession().getServletContext().getRealPath("/resources/images/logList");

        // 첨부파일이 있을 때
        if (mfile != null && !mfile.isEmpty()) {
            try {
                // 파일 이름 추출
                String fileName = mfile.getOriginalFilename();
                String renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmss");

                // 폴더 없으면 생성
                java.io.File dir = new java.io.File(savePath);
                if (!dir.exists()) dir.mkdirs();

                // 파일 실제 저장
                java.io.File destFile = new java.io.File(savePath + "/" + renameFileName);
                mfile.transferTo(destFile);

                // DB에 저장될 이미지 경로 설정
                logVo.setImagePath("/resources/images/logList/" + renameFileName);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("파일 저장 중 오류 발생", e);
            }
        }

        // DB에 저장
        logService.createLog(logVo);
        logger.info("insert result: 생성완료 / logId = {}", logVo.getLogId());

        return new ModelAndView("redirect:/log/page.do?logId=" + logVo.getLogId()); // 저장 후 상세보기로 이동해야
    }

    // 상세보기 페이지
    @GetMapping("/detail.do")
    public ModelAndView viewLogDetail(@RequestParam("logId") Long logId) {
        LogVO log = logService.getLogById(logId);
        ModelAndView mav = new ModelAndView("logs/logDetailView"); // /WEB-INF/views/logs/logDetailView.jsp
        mav.addObject("log", log);
        return mav;
    }
} 
