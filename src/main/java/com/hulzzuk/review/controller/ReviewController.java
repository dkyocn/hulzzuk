package com.hulzzuk.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.service.ReviewService;

@Controller
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * 리뷰 리스트 조회 메서드
	 * @param mv  ModelAndView
	 * @param locId  맛집,  즐길거리,  숙소 아이디
	 * @param locationEnum  숙소, 맛집, 즐길거리
	 * @param sortEnum  최신순, 이름순
	 * @return ModelAndView
	 */
	  @RequestMapping("list.do")
	    public ModelAndView getReviewList(ModelAndView mv,
	    									@RequestParam(name = "locId", required = false) String locId,
	    									@RequestParam(name = "locationEnum", required = false) LocationEnum locationEnum,
	                                        @RequestParam(name = "sortEnum", required = false) SortEnum sortEnum) {

	        return reviewService.getReviewList(locId,locationEnum,sortEnum, mv);
	    }
	
		
//		  // 리뷰 생성
//		  
//		  @RequestMapping("create.do") public ModelAndView
//		  insertLocationReview(ModelAndView mv,
//		  
//		  @RequestParam(name = "locationEnum") LocationEnum locationEnum) { return
//		  reviewService.insertLocationReview }
		 

	
	// 리뷰 삭제
	@RequestMapping("delete.do")
	public ModelAndView deleteReview(ModelAndView mv, 
										@RequestParam(name = "reviewId") long reviewId,
										@RequestParam(name = "userId") String userId) {
		
		return reviewService.deleteReview(mv, reviewId, userId);
	}
}
