package com.hulzzuk.log.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogCommentVO;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;

@Repository
public class LogDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    // 페이징 처리된 로그 리스트 조회
    public List<LogVO> getLogList(Paging paging) {
        return sqlSession.selectList("logMapper.getLogList", paging);
    }
    
    
    public List<LogVO> getLogListByIds(List<Long> logIds) {
        return sqlSession.selectList("logMapper.getLogListByIds", logIds);
    }

    // 전체 로그 수 조회
    public int getLogCount() {
        return sqlSession.selectOne("logMapper.getLogCount");
    }

    // ID로 단일 로그 조회  // 로그 기본 정보 *************************************** /** 로그 상세 조회 */
    public LogVO getLogById(long id) {
        return sqlSession.selectOne("logMapper.getLogById", id);
    }

    // 로그 생성 (리턴은 줄수. 로그아이디 로그에 셋팅)
    public void insertLog(LogVO log) {
        sqlSession.insert("logMapper.insertLog", log);
    }
    public void insertTripLog(LogReviewVO review) {
        sqlSession.insert("logMapper.insertTripLog", review);
    }
 

    // 로그 수정
    public void updateLog(LogVO logVo) {
        sqlSession.update("logMapper.updateLog", logVo);
    }

    // 로그 삭제
    public void deleteLog(long id) {
        sqlSession.delete("logMapper.deleteLog", id);
    }

    // 시작 위치와 수량에 따른 로그 목록 조회 (페이지용)
    public List<LogVO> getLogPage(int start, int amount) {
        Map<String, Object> param = new HashMap<>();
        param.put("start", start);
        param.put("amount", amount);

        return sqlSession.selectList("logMapper.getLogList", param);
    }

    // 로그인한 사용자의 여행 계획 리스트 조회
    public List<PlanVO> selectPlanIdList(String userId) {
        return sqlSession.selectList("logMapper.selectPlanIdList", userId);
    }
  
    
 // 로그갯수 조회
 	public int logCount(String locId, LocationEnum locationEnum) {
 		HashMap<String, String> map = new HashMap<>();
         map.put("locId", locId);
         map.put("locationEnum",  locationEnum != null ? locationEnum.name() : null);
         return sqlSession.selectOne("logReviewMapper.logCount", map);
 	}
 	
 	// 로그 id로 로그 조회
 	public List<Long> getLogId(String locId, LocationEnum locationEnum) {
 		HashMap<String, Object> map = new HashMap<>();
 		map.put("locId", locId);
         map.put("locationEnum",  locationEnum != null ? locationEnum.name() : null);

         return sqlSession.selectList("logReviewMapper.getLogId", map);
     }
 	
 	// 로그 리스트 조회
 	public List<LogVO> getLogSelectOne(Long logId) {
 		HashMap<String, Object> map = new HashMap<>();
 		map.put("logId", logId);
 		
         return sqlSession.selectList("logMapper.getLogSelectOne", map);
     }

    //로그 작성을 위한 여행일정조회 
    public PlanVO getPlanById(Long planId) {
        return sqlSession.selectOne("logMapper.getPlanById", planId);
    }

//CommentsByPlaceId라서지워도 될텐데.....추후확인  //////////////////////////////////////////////////////////////////////////////////////
	public List<LogReviewVO> getReviewsByLogId(Long logId) {
		 return sqlSession.selectList("logMapper.getCommentsByPlaceId", logId);
	}
   
	//MyTripLog조회 /** 사용자별 로그 리스트 조회 */
	public List<LogVO> selectLogsByUser(String userId) {
	    return sqlSession.selectList("logMapper.selectLogsByUser", userId);
	}
	// 좋아요 순 조회  
	public List<LogVO> getLogListByLove() {		
		return sqlSession.selectList("logMapper.getLogListByLove");
	}
	
	//이미지업로드

	public void updateLogImage(long logId, String imagePath) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("logId", logId);
        paramMap.put("imagePath", imagePath);

        int result = sqlSession.update("logMapper.updateLogImage", paramMap);
        if (result <= 0) {
            throw new RuntimeException("이미지 경로 업데이트 실패");
        }
    }
	//	//전체 장소 리스트 조회 (Day1/Day2 전부 포함)
	 public List<LogReviewVO> getPlaceListByPlanId(long planId) {
	        return sqlSession.selectList("logMapper.getPlaceListByPlanId", planId);
	    }
	//특정 Day의 장소만 조회 (예: Day1 또는 Day2만)
	 public List<LogReviewVO> getPlacesByPlanDay(long planId, int planDay) {
		    Map<String, Object> params = new HashMap<>();
		    params.put("planId", planId);
		    params.put("planDay", planDay);

		    return sqlSession.selectList("logMapper.getPlacesByPlanDay", params);
		} 
	 
	 //새로추가된리뷰   // 장소별 콘텐츠 (LogReviewVO) 가져오기 ************************로그디테일///** 장소별 콘텐츠 (LogReviewVO) 가져오기 - 로그 디테일용 */
	 public List<LogReviewVO> getReviewsByLogId(long logId) {
		 return sqlSession.selectList("logMapper.getReviewsByLogId", logId);
 }
	 
/**메소드 분리방식 적용해보기 **/ 
	 
	 
	 
	// LogDao.java
	public List<LogReviewVO> getReviewList(long logId) {
		 return sqlSession.selectList("logMapper.getReviewByLogId", logId);
	}
	//로그조회용. /** 최근 생성된 로그 1건 조회 */  
	public Long selectRecentLogId(String userId, String logTitle) {
		 Map<String, Object> params = new HashMap<>();
		    params.put("userId", userId);
		    params.put("logTitle", logTitle);
	
		    return sqlSession.selectOne("logMapper.selectRecentLogId", params);
		}
	
	

	
	
	public List<LogCommentVO> getCommentsByLogId(Long logId) {
		return sqlSession.selectList("logMapper.getCommentsByLogId", logId);
	}

	public List<LogCommentVO> getRepliesByCommentIds(List<Long> commentIdList) {
		 return sqlSession.selectList("logMapper.getRepliesByCommentIds", commentIdList);
	}

	

	 // 댓글(부모가 없는) 목록 반환 ************************로그디테일///************************로그디테일/// /** 부모 댓글 목록 조회 */
		public List<LogCommentVO> getTopLevelComments(Long logId) {
			return sqlSession.selectList("logMapper.selectTopLevelComments", logId);
		}
		// 특정 댓글의 대댓글 목록 반환  ************************로그디테일///************************로그디테일////** 대댓글 목록 조회 */
		public List<LogCommentVO> getRepliesByParentId(Long commentId) {
			return sqlSession.selectList("logMapper.selectRepliesByParentId", commentId);
	    }
		//************************로그디테일///************************로그디테일  **********************로그디테일//** 댓글 저장 */
		public void insertTopLevelComment(LogCommentVO comment) {
			sqlSession.insert("logMapper.insertTopLevelComment", comment); //정상 처리
		}
		//************************로그디테일///************************로그디테일  **********************로그디테일//** 대댓글 저장 */
		public void insertReplyComment(LogCommentVO comment) {
			sqlSession.insert("logMapper.insertTopLevelComment", comment); //정상 처리
		}


		 public void insertReply(LogCommentVO reply) {
		        sqlSession.insert("logMapper.insertReply", reply);
		 }

}
