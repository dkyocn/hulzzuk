package com.hulzzuk.review.model.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.vo.ReviewVO;

@Service("reviewService")
public interface ReviewService {
	
	// 리뷰 리스트 조회
	List<ReviewVO> getReviewList(String locId, LocationEnum locationEnum,  SortEnum sortEnum);
	// 리뷰 UserNick 조회
	 HashMap<String, String> getUserNicks(String locId, LocationEnum locationEnum, SortEnum sortEnum);
	// 내리뷰 조회
	ModelAndView getMyReviewList(ModelAndView mv, String userId);
	// 리뷰 한 개 조회
	ReviewVO getReviewById(long reviewId);
	// 리뷰 생성
	
	// 리뷰 삭제
	void deleteReview(String reviewIds);
	
	
	
}
