package com.hulzzuk.love.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.love.model.vo.LoveVO;

import jakarta.servlet.http.HttpSession;

@Repository("LoveDao")
public class LoveDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 숙소 찜 등록
	public int insertAccoLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertAccoLove", love);
	}
	
	// 맛집 찜 등록
	public int insertRestLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertRestLove", love);
	}
	
	// 즐길거리 찜 등록
	public int insertAttrLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertAttrLove", love);
	}
	
	// 여행지 찜 중복 확인
	public int selectLoveExists(LoveVO love) {
	    return sqlSessionTemplate.selectOne("loveMapper.selectLoveExists", love);
	}

	// 여행지 찜 개수 조회
	public int getLocLoveCount(String locId, LocationEnum locationEnum) {
		HashMap<String, String> map = new HashMap<>(); 
		map.put("locId", locId);
		map.put("locationEnum", locationEnum != null ? locationEnum.name() : null);
		return sqlSessionTemplate.selectOne("loveMapper.getLocLoveCount", map); 
	}
	
    // 여행지 찜 삭제
 	public int deleteLoveByCondition(LoveVO love) {
 		return sqlSessionTemplate.delete("loveMapper.deleteLoveByCondition", love);
 	}
 	
 	// ----------------------------------------------------
 	
	// 로그 찜 등록
	public int insertLogLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertLogLove", love);
	}

	// 로그 찜 중복 확인
	public int selectLogLoveExists(LoveVO love) {
		return sqlSessionTemplate.selectOne("loveMapper.selectLogLoveExists", love);
	}
	
	// 로그 찜 개수 조회
	public int getLogLoveCount(Long logId) { 
		HashMap<String, Long> map = new HashMap<>(); 
		map.put("logId", logId); 
		return sqlSessionTemplate.selectOne("loveMapper.getLogLoveCount", map); 
	}
	 
	// 로그 찜 삭제 
 	public int deleteLogLove(LoveVO love) { 
 		return sqlSessionTemplate.delete("loveMapper.deleteLoveByLogId", love); 
 	}
	 
 	
 	// 전체 찜 리스트
 	public List<LoveVO> selectAllLoveList(String userId){ 
 		return sqlSessionTemplate.selectList("loveMapper.selectAllLoveList", userId); 
	}
	 
 	
 	// 여행지 찜 리스트
 	public List<LoveVO> selectLocationLoveList(String userId) {
 	    return sqlSessionTemplate.selectList("loveMapper.selectPlaceLoveList", userId);
 	}


 	// 로그 찜 리스트
 	public List<LoveVO> selectLogLoveList(String userId){
 		return sqlSessionTemplate.selectList("loveMapper.selectLogLoveList", userId);
 	};

 	
}
