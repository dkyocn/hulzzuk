package com.hulzzuk.love.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.love.model.vo.LoveVO;

import jakarta.servlet.http.HttpSession;

public interface LoveService {
	// 여행지 찜 등록
	Map<String, Object> insertLove(HttpSession session, LocationEnum locationEnum, String locId);
	
	// 여행지 찜 중복 확인
	Map<String, Object> checkLoveStatus(HttpSession session, LocationEnum locationEnum, String locId);

	// 여행지 찜 개수 조회
	int getLocLoveCount(String locId, LocationEnum locationEnum);
	
	// 여행지 찜 해제
	Map<String, Object> deleteLove(HttpSession session, LocationEnum locationEnum, String locId); 
	
	
	
	
	// 로그 찜 등록
	Map<String, Object> insertLogLove(HttpSession session, Long logId);
	
	// 로그 찜 중복 확인
	Map<String, Object> checkLogLoveStatus(HttpSession session, Long logId);
	
	// 로그 찜 개수 조회
	int getLogLoveCount(Long logId);

	// 로그 찜 해제
	Map<String, Object> deleteLogLove(HttpSession session, Long logId);
	
	// 전체 찜 리스트
	ModelAndView selectAllLoveList(ModelAndView mv, HttpSession session, String category);

	
	/*
	 * // 여행지 찜 리스트 List<LoveVO> selectLocationLoveList(String userId);
	 * 
	 * // 로그 찜 리스트 List<LoveVO> selectLoveList(String userId);
	 */



	

}
