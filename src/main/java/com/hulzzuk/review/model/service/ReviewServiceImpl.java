package com.hulzzuk.review.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;
import com.hulzzuk.review.model.dao.ReviewDao;
import com.hulzzuk.review.model.vo.ReviewVO;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDao reviewDao;
	
	// 리뷰 리스트 조회
	@Override
	public ModelAndView getReviewList(String locId, LocationEnum locationEnum,  SortEnum sortEnum, ModelAndView mv) {
	
        List<ReviewVO> reviewList = reviewDao.getReviewList(locId, locationEnum, sortEnum);

        mv.addObject("locationEnum", locationEnum);
        mv.addObject("sortEnum", sortEnum);
        mv.setViewName("review/reviewListView");
        
        
        return mv;
	}

	// 리뷰 삭제
	@Override
	public ModelAndView deleteReview(ModelAndView mv, long reviewId, String userId) {
		int result = reviewDao.deleteReview(reviewId, userId);
		
		if(result > 0) {
			mv.addObject("msg", "리뷰 삭제 완료");
		} else {
			mv.addObject("msg", "리뷰 삭제 실패");
		}
		mv.setViewName("common/popUp");
		
		return mv;
	}
	

}
