package com.hulzzuk.review.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.service.ReviewService;
import com.hulzzuk.review.model.vo.ReviewVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	  // 마이 리뷰 조회
	  @RequestMapping("select.do")
	  public ModelAndView getMyReviewList(HttpSession session, ModelAndView mv, 
			  							@RequestParam(name = "userId") String userId) {
		  return reviewService.getMyReviewList(session, mv,userId);
	  }
		
	  // 리뷰 생성 => 생성 페이지로 이동
	  @RequestMapping("moveCreate.do")
	  public ModelAndView moveCreateReview(ModelAndView mv, @RequestParam("locationEnum") LocationEnum locationEnum, @RequestParam(name = "locId") String locId) {
		  mv.addObject("locationEnum", locationEnum);
		  mv.addObject("locId", locId);
		  
		  mv.setViewName("review/createReview");
	        
	        return mv;
	  }
	  
	  // 리뷰 생성 => insert 문 실행
	  @RequestMapping("create.do")
	  public ModelAndView createReview(ModelAndView mv, @RequestParam("locationEnum") LocationEnum locationEnum,
			  HttpServletRequest request,
              @RequestParam("locId") String locId,
              ReviewVO reviewVO) {

		// locId와 enum을 서비스에 그대로 전달
		return reviewService.createReview(mv,locationEnum, request, locId, reviewVO);
	  }
	  
	  // 리뷰 생성 => 생성 확인 팝업페이지 연결
	  @RequestMapping("moveCreatePopUp.do")
	  public ModelAndView moveCreatePopUp(ModelAndView mv, HttpServletRequest request,
											@RequestParam(name = "reviewId") Long reviewId,
											@RequestParam(name = "message") String message,
											@RequestParam(name = "width") int width,
											@RequestParam(name = "height") int height) {
			
			mv.addObject("reviewId", reviewId);
			mv.addObject("message", message);
			mv.addObject("actionUrl", request.getContextPath() + "/review/create.do?reviewIds="+ reviewId);
			mv.addObject("width", width);
			mv.addObject("height", height);
			
			mv.setViewName("common/popUp");
			
			return mv;
	  }
	  
	  // 리뷰 생성 => summerNote 이미지파일 경로 지정
	  @RequestMapping("imagePath.do")
	  public ModelAndView imagePath(ModelAndView mv) {
		  return mv;
	  }
	  
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
