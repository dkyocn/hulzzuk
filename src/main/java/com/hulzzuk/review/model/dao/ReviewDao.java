package com.hulzzuk.review.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.vo.ReviewVO;

@Repository("reviewDao")
public class ReviewDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 리뷰 리스트 조회
	public List<ReviewVO> getReviewList(String locId, LocationEnum locationEnum, SortEnum sortEnum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("locId", locId);
        map.put("locationEnum",  locationEnum != null ? locationEnum.name() : null);
        map.put("sortEnum", sortEnum != null ? sortEnum.name() : null);

        return sqlSessionTemplate.selectList("reviewMapper.getReviewList", map);
    }
	
	// 리뷰 삭제
	public int deleteReview(long reviewId, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("reviewId", reviewId);
		map.put("userId", userId);

		return sqlSessionTemplate.delete("reviewMapper.deleteReview", map);
	}
}
