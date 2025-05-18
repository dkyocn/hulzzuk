package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogCommentVO;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;


public interface LogService {

    List<LogVO> getLogList(Paging paging);
    List<PlanVO> selectPlanIdList(String userId);
    
    

    int getLogCount();

    LogVO getLogById(long id);    //*************로그디테일뷰 에서도 ( 타이틀이랑 기본정보 불러옴)- 나머지는 getReviewListByLogId


    void updateLog(LogVO logVo);

    void deleteLog(long id);
    
    List<LogVO> getLogPage(int start, int amount);
    
// 상세페이지 리뷰 갯수 조회
 	int logCount(String locId, LocationEnum locationEnum);
 	// 상세페이지 리뷰 리스트 조회
 	List<LogVO> getLocLogList(String locId, LocationEnum locationEnum);
 	
	PlanVO getPlanById(Long planId);

	
    //MyTripLog조회페이 
	List<LogVO> selectLogsByUser(String userId);
    
  //좋아요순 조회하기
	List<LogVO> getLogListByLove();
	
//로그 인서트 	
	//로그생성하기이후 logId생성 이후 중계테이블에 insert 
	long insertLog(LogVO log);
	
	void insertTripLog(LogReviewVO review);
	
	//이미지저장후 데이터베이스 저장(로그아이디생성됨)
	void updateLogImage(long logId, String imagePath);
	
	//전체 장소 리스트 조회 (Day1/Day2 전부 포함)
	List<LogReviewVO> getPlaceListByPlanId(long planId);
   
	//특정 Day의 장소만 조회 (예: Day1 또는 Day2만) // 로그디테일뷰에서도 조회함.
	List<LogReviewVO> getPlacesByPlanDay(long planId, int planDay);
	
	

	
	//아이디추가하고 최신아이디조회
	Long getRecentLogIdByUserIdAndTitle(String userId, String logTitle);
	
//로그상세보기 DetailView
	
	//새로추가된 리뷰  (상세보기)   //댓글 대댓글
	List<LogReviewVO> getLogDetailViewByLogId(long logId);   //*************로그디테일뷰 에서도 
	
	List<LogReviewVO> getReviewListByLogId(Long logId); // selectLogReviewList로그인서트용임 .
	//디테일뷰 전용 getReviewListByLogId() 메서드
	
	//로그컨텐트얻기위한 임시테스트후 적용함 
	List<LogReviewVO> getReviewsByLogId(Long logId);
	//로그컨텐트얻기위한 3차시도 
	List<LogReviewVO> getPlacesByPlanDay(Long planId, int planDay);
	
	/** 이후삭제  >>> 다시도입  **/ 
	List<LogCommentVO> getCommentsByLogId(Long logId); //*************로그디테일뷰 에서도  // 전체 댓글/대댓글
	//List<LogCommentVO> getRepliesByCommentIds(List<Long> commentIds); // 대댓글만-- 이후삭제예//
	
	
	
	//댓글입력 
	int insertLogComment(LogCommentVO comment);
	//대댓글입력 
	int insertReplyComment(LogCommentVO reply);
	
	
	List<LogCommentVO> getTopLevelComments(Long logId);  // 댓글만 (PARENT_ID is null)
	List<LogCommentVO> getRecommentsByCommentId(Long commentId);
	
	//댓글 삭제 및 업데이
	int deleteLogComment(Long commentId);
	int updateLogComment(LogCommentVO comment);
	
	



}
