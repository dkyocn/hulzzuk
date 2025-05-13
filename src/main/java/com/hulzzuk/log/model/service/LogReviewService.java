package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.log.model.vo.LogPlaceVO;
import com.hulzzuk.log.model.vo.LogReviewVO;


public interface LogReviewService {

	//**********LogReviewService에서부터 병합 
	 /**
     * 후기 저장
     * @param review 후기 VO
     * @return insert 결과
     */
	int insertLogReview(LogReviewVO review);
	
	/**
     * 특정 플랜 ID와 Day 번호에 해당하는 장소 목록 조회 (후기 작성용)
     * @param planId 플랜 ID
     * @param planDay Day1 또는 Day2
     * @return 장소 목록
     */
	//List<LogPlaceVO> getPlacesByPlanDay(int planId, int i);   // 후기 작성

	  /**
     * 후기 작성 페이지 상단에 표시할 플랜 정보 조회 (플랜 제목, 날짜 등 포함)
     * @param planId 플랜 ID
     * @return 단일 플랜 정보 객체
     */
	LogPlaceVO fetchPlanForLog(int planId);  // 후기페이지상단용 플

	 //List<LogPlaceVO> selectByPlanDay(int planId, int planDay); // ←테스트용 추가
	
	 //List<LogPlaceVO> getPlacesByPlanDay(Long planId, int i);   // 로그데이터 탭 안의 내용 불러오기 
}
