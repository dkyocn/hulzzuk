package com.hulzzuk.review.model.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.dao.ReviewDao;
import com.hulzzuk.review.model.vo.ReviewVO;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private UserDao userDao;
	
	// 리뷰 리스트 조회
	public List<ReviewVO> getReviewList(String locId, LocationEnum locationEnum,  SortEnum sortEnum) {
        List<ReviewVO> reviewList = reviewDao.getReviewList(locId, locationEnum, sortEnum);
        
        return reviewList;
	}
	
	// 리뷰 닉네임 조회
		public HashMap<String, String> getUserNicks(String locId, LocationEnum locationEnum,  SortEnum sortEnum) {
	        List<ReviewVO> reviewList = reviewDao.getReviewList(locId, locationEnum, sortEnum);
	        
	       HashMap<String, String> userNicks  = new HashMap<>();

	        for(ReviewVO reviewVO : reviewList  ) {
	        	UserVO user = userDao.selectUser(reviewVO.getUserId());
	        	userNicks.put(reviewVO.getUserId(), user.getUserNick());
	        }
	        
	        return userNicks;
		}

	// 내 리뷰 조회
	@Override
	public ModelAndView getMyReviewList(ModelAndView mv, String userId) {
		UserVO user = userDao.selectUser(userId);
		List<ReviewVO> reviewList = reviewDao.getMyReviewList(userId);

        mv.addObject("user", user);
        mv.addObject("reviewList", reviewList);
        mv.setViewName("review/myReview");
        
        
        return mv;
	}
	
	// 리뷰 한 개 조회
	@Override
	public ReviewVO getReviewById(long reviewId) {
	    return reviewDao.getReviewById(reviewId);
	}
	
	// 리뷰 생성
	
	
	// 리뷰 삭제
	@Override
	public void deleteReview(String reviewIds) {
		String[] reviews = reviewIds.split(",");
		for(String id : reviews) {
			int result = reviewDao.deleteReview(Long.parseLong(id));
			
			if(result <=  0) {
				throw new IllegalArgumentException(ErrorCode.REVIEW_NOT_FOUND.getMessage());
			}
		}
	}

	
	

}
