package com.hulzzuk.log.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hulzzuk.log.model.service.LogReviewService;
import com.hulzzuk.log.model.vo.LogPlaceVO;

@Controller
public class LogReviewController {
	
	
	    @Autowired
	    private LogReviewService logReviewService;
/**  >>>>>>>>로그 탭화면 뷰 까지Log Controller에서 하고.Insert쪽만 담당함.
	 //로그작성을 위한 planID세부사항 불러오
    @RequestMapping("insertPage.do")
    public String showLogInsertPage(@RequestParam("planId") int planId, Model model) {
        List<LogPlaceVO> day1Places = logReviewService.getPlacesByPlanDay(planId, 1);  // day1 리스트 조회
        System.out.println(">>>> 유니 여기 도착했어 첫날리스트.!");
        // 데이터 비어있는지 확인위해서 임시 넣었음. 
        System.out.println("✅ day1PlaceList size: " + day1Places.size());
        for (LogPlaceVO p : day1Places) {
            System.out.println("📍 " + p);
        }
        
        List<LogPlaceVO> day2Places = logReviewService.getPlacesByPlanDay(planId, 2);  // day2 리스트 조회
        LogPlaceVO plan = logReviewService.fetchPlanForLog(planId); // 플랜 정보 조회

        model.addAttribute("plan", plan);
        model.addAttribute("day1PlaceList", day1Places);
        model.addAttribute("day2PlaceList", day2Places);
        System.out.println(">>>> 유니 여기 도착했어 장소불러오니?!");
        return "logs/logInsert";  //logs/logInsert.jsp
    }
  
    @GetMapping("/log/test")
    public String testSelectPlaces(Model model) {
        int planId = 29;
        int planDay = 1;

        List<LogPlaceVO> placeList = logReviewService.selectByPlanDay(planId, planDay);

        System.out.println("✅ Day1 장소 수: " + placeList.size());
        for (LogPlaceVO vo : placeList) {
            System.out.println("➡️ " + vo);
        }

        model.addAttribute("places", placeList);
      
        return "log/testLog";  // JSP 테스트 페이지 만들어도 되고 아무거나 리턴
        
    }
   **/ 
}
