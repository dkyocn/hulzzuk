package com.hulzzuk.review.model.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.dao.LocationDao;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.dao.ReviewDao;
import com.hulzzuk.review.model.vo.ReviewVO;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	private static final Logger logger =  LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private LocationDao locationDao;
	
	// 리뷰 갯수 조회
	@Override
	public int reviewCount(String locId, LocationEnum locationEnum) {
		int reviewCount = reviewDao.reviewCount(locId, locationEnum);
		 
        return reviewCount;
	}
	
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
		
	// 리뷰 별점 평균 조회
		public Double getAvgRating(String locId, LocationEnum locationEnum) {
		    Double avgRating = reviewDao.getAvgRating(locId, locationEnum);
		    return avgRating != null ? avgRating : 0.0;
		}

	// 내 리뷰 조회
	@Override
	public ModelAndView getMyReviewList(ModelAndView mv, String userId) {
		UserVO user = userDao.selectUser(userId);
		List<ReviewVO> reviewList = reviewDao.getMyReviewList(userId);
		
		 HashMap<Long, String> result = new HashMap<>();
		 
		 for (ReviewVO reviewVO : reviewList) {
			 if( reviewVO.getAccoId() != null) {
				 result.put(reviewVO.getReviewId(), locationDao.getAccoName(reviewVO.getAccoId()));
			 }
			 if( reviewVO.getRestId() != null) {
				 result.put(reviewVO.getReviewId(), locationDao.getRestName(reviewVO.getRestId()));
			 } 
			 if( reviewVO.getAttrId() != null) {
				 result.put(reviewVO.getReviewId(), locationDao.getAccoName(reviewVO.getAttrId()));
			 } 			 
		 }
		 mv.addObject("locName", result);		 
	
		
		
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
	@Override
	public ModelAndView createReview(ModelAndView mv,LocationEnum locationEnum, HttpServletRequest request, String locId, ReviewVO reviewVO) {
		int successYN = 0;
	    switch (locationEnum) {
	        case ACCO:
	            reviewVO.setAccoId(locId);
	            reviewVO.setUserId(((UserVO)request.getSession().getAttribute("loginUser")).getUserId());
	            reviewVO.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
	            logger.info("session User :  " + ((UserVO)request.getSession().getAttribute("loginUser")).getUserId() );
	            logger.info("User ID :  "+reviewVO.getUserId());
	            successYN =  reviewDao.insertAccoReview(reviewVO);
	            break;
	        case REST:
	            reviewVO.setRestId(locId);
	            reviewVO.setUserId(((UserVO)request.getSession().getAttribute("loginUser")).getUserId());
	            reviewVO.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
	            reviewVO.setUserRev(0);
	            successYN = reviewDao.insertRestReview(reviewVO);
	            break;
	        case ATTR:
	            reviewVO.setAttrId(locId);
	            reviewVO.setUserId(((UserVO)request.getSession().getAttribute("loginUser")).getUserId());
	            reviewVO.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
	            reviewVO.setUserRev(0);
	            successYN =  reviewDao.insertAttrReview(reviewVO);
	            break;
	        default:
	            throw new IllegalArgumentException("지원하지 않는 장소 유형입니다.");
	    }
	    if(successYN == 0) {
	    throw new  IllegalArgumentException("저장 안되었음요");
	    }else {
	    	mv.setViewName("redirect:/loc/select.do?locationEnum="+locationEnum + "&locId="+locId);
	    }
	    return mv;
	}
	
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
