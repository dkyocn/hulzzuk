package com.hulzzuk.review.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.review.model.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	  // 마이 리뷰 조회
	  @RequestMapping("select.do")
	  public ModelAndView getMyReviewList(ModelAndView mv, 
			  							@RequestParam(name = "userId") String userId) {
		  return reviewService.getMyReviewList(mv,userId);
	  }
		
	  // 리뷰 생성
	  
	  
	// 리뷰 삭제
		@RequestMapping("delete.do")
	    @ResponseBody
	    public String deleteReview(@RequestParam(name = "reviewIds") String reviewIds) {
	        // 삭제 후 목록 페이지로 리다이렉트
			reviewService.deleteReview(reviewIds);
	        return "success";
	    }
	
	// 리뷰 삭제 => 팝업 페이지 연결
	@RequestMapping("moveDelete.do")
	public ModelAndView moveDeleteReview(ModelAndView mv, HttpServletRequest request,
										@RequestParam(name = "reviewIds") String reviewIds,
										@RequestParam(name = "message") String message,
                                       @RequestParam(name = "width") int width,
                                       @RequestParam(name = "height") int height) {
		
		// URL 인코딩
        String encodedReviewIds = URLEncoder.encode(reviewIds, StandardCharsets.UTF_8);
		

		mv.addObject("reviewIds", reviewIds);
		mv.addObject("message", message);
        mv.addObject("actionUrl", request.getContextPath() + "/review/delete.do?reviewIds="+ encodedReviewIds);
        mv.addObject("width", width);
        mv.addObject("height", height);
		
        mv.setViewName("common/popUp");
        
        return mv;
	}
}
