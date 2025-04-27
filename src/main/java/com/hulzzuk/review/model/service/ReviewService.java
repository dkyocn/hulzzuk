package com.hulzzuk.review.model.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;

public interface ReviewService {
	
	// 리뷰 리스트 조회
	ModelAndView getReviewList(String locId, LocationEnum locationEnum, SortEnum sortEnum, ModelAndView mv);
	ModelAndView deleteReview(ModelAndView mv, long reviewId, String userId);
}
