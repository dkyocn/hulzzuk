package com.hulzzuk.log.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulzzuk.comment.model.dao.CommentDao;
import com.hulzzuk.comment.model.service.CommentService;
import com.hulzzuk.comment.model.vo.CommentVO;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.dao.LogReviewDao;
import com.hulzzuk.log.model.vo.LogCommentVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;
import com.hulzzuk.recomment.model.service.RecommentService;
import com.hulzzuk.recomment.model.vo.RecommentVO;


@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<LogVO> getLogList(Paging paging) {
        return logDao.getLogList(paging);
    }

    @Override
    public int getLogCount() {
        return logDao.getLogCount();
    }
    
    //로그디테일뷰  **********************컨텐트포함 나머지는 하단에서 getReviewListByLogId
    @Override
    public LogVO getLogById(long id) {
        return logDao.getLogById(id);
    }

    @Override
    public List<PlanVO> selectPlanIdList(String userId) {
        return logDao.selectPlanIdList(userId);
    }
  

    @Override
    public void updateLog(LogVO logVo) {
    	logDao.updateLog(logVo);
    }


    @Override
    public void deleteLog(long id) {
    	logDao.deleteLog(id);
    }
    @Override
    public List<LogVO> getLogPage(int start, int amount) {
        return logDao.getLogPage(start, amount);
    }

	// 상세페이지 로그 갯수 조회
 	@Override
 	public int logCount(String locId, LocationEnum locationEnum) {
 		int logCount = logDao.logCount(locId, locationEnum);

         return logCount;
 	}

 	// 상세페이지 로그 리스트 조회
 	public List<LogVO> getLocLogList(String locId, LocationEnum locationEnum) {
 		// 로그 id 조회
 		List<Long> logIdList = logDao.getLogId(locId, locationEnum);
 		// 로그 조회 + 리스트
 		List<LogVO> logList = new ArrayList<>();
 		for(Long logId : logIdList  ) {
 			List<LogVO> singleLog = logDao.getLogSelectOne(logId.longValue());
 			if(singleLog != null && !singleLog.isEmpty()) {
 				logList.addAll(singleLog);
 			}
        }
         return logList;
 	}
	
	@Override
	public PlanVO getPlanById(Long planId) {
		return logDao.getPlanById(planId);
	}

	//MyTripLog조회 
	@Override
	public List<LogVO> selectLogsByUser(String userId) {
		return logDao.selectLogsByUser(userId);
	}

	@Override
	public List<LogVO> getLogListByLove() {
		return logDao.getLogListByLove();
	}


	@Override
	public long insertLog(LogVO log) {
		    int result = sqlSession.insert("logMapper.insertLog", log);
		    System.out.println("🔥 DAO result: " + result);
		    System.out.println("🔥 생성된 logId: " + log.getLogId());  // 꼭 확인
		  
		if (result > 0) {
			logDao.insertLog(log); // 리턴값 없이 void이면 logId 세팅 안됨
	        return log.getLogId();  // MyBatis가 selectKey로 세팅한 값
	    } else {
	        throw new RuntimeException("로그 insert 실패");
	    }	
	}
	// 로그아이드 인서트후 연결해 중계테이블로 인서트 
	@Override
	public void insertTripLog(LogReviewVO review) {
	 logDao.insertTripLog(review);
	}

	//이미지 업데이트  저장
	@Override
    public void updateLogImage(long logId, String imagePath) {
        logDao.updateLogImage(logId, imagePath);
    }
	//전체 장소 리스트 조회 (Day1/Day2 전부 포함)
	@Override
	public List<LogReviewVO> getPlaceListByPlanId(long planId) {
	    return logDao.getPlaceListByPlanId(planId);
	}
	//특정 Day의 장소만 조회 (예: Day1 또는 Day2만)
	@Override
	public List<LogReviewVO> getPlacesByPlanDay(long planId, int planDay) {
	    return logDao.getPlacesByPlanDay(planId, planDay);
	}


	@Override
	public Long getRecentLogIdByUserIdAndTitle(String userId, String logTitle) {
		return logDao.selectRecentLogId(userId, logTitle);
	}

	//LogDetailView (content 포함)
	@Override
	public List<LogReviewVO> getReviewListByLogId(Long logId) {
		return logDao.getReviewsByLogId(logId); // 여기서 reviews 로 이름 바뀜!
	}

	//0517 로그컨텐트테스트
	@Override
	public List<LogReviewVO> getPlacesByPlanDay(Long planId, int planDay) {
		return logDao.getPlacesByPlanDay(planId, planDay);  // logMapper.xml의 getPlacesByPlanDay 연결됨
	} 
//
	//0517 로그컨텐트테스트
	@Override
	public List<LogReviewVO> getReviewsByLogId(Long logId) {
		 return logDao.getReviewsByLogId(logId);  // logMapper.xml의 getReviewsByLogId 연결됨
		// return logDao.selectPlacesByPlanDayByLogId(logId);  //이전은 안맞았
	}


	
	//로그디테일뷰 **********************
	@Override
    public List<LogReviewVO> getLogDetailViewByLogId(long logId) {
		// 1. 로그 정보 가져오기
	    LogVO log = logDao.getLogById(logId);
	    Long planId = log.getPlanId();

	    // 일단 Day1/Day2 두 번 반복해서 합치기
	    List<LogReviewVO> merged = new ArrayList<>();
	    for (int day = 1; day <= 2; day++) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("planId", planId);
	        param.put("planDay", day);
	        //List<LogReviewVO> placeList = logDao.selectPlacesByPlanDay(param);
	        List<LogReviewVO> placeList = logDao.selectPlacesByPlanDay(param);   // 잘못가져오는것 같아서 이걸로변
	        
	        List<LogReviewVO> reviewList = logDao.selectLogReviewList(logId);

	        for (LogReviewVO place : placeList) {
	            for (LogReviewVO review : reviewList) {
	                boolean same =
	                    Objects.equals(place.getAccoId(), review.getAccoId()) &&
	                    Objects.equals(place.getRestId(), review.getRestId()) &&
	                    Objects.equals(place.getAttrId(), review.getAttrId());

	                if (same) {
	                    place.setLogContent(review.getLogContent());
	                    break;
	                }
	            }
	        }
	        merged.addAll(placeList);
	    }

	    return merged;
	}

	@Override
	public int insertLogComment(LogCommentVO comment) {  
		return logDao.insertLogComment(comment);
		
	}
	//대댓글 입력 등록.
	@Override
	public int insertReplyComment(LogCommentVO reply) {
		return logDao.insertReplyComment(reply);
		
	}

	@Override
	public List<LogCommentVO> getCommentsByLogId(Long logId) {
		return logDao.getCommentsByLogId(logId);
	}

	@Override
	public List<LogCommentVO> getTopLevelComments(Long logId) {
		return logDao.getTopLevelComments(logId);
	}

	@Override
	public List<LogCommentVO> getRecommentsByCommentId(Long commentId) {
		return logDao.getRecommentsByCommentId(commentId);
	}
	//댓글삭제 
	@Override
	public int deleteLogComment(Long commentId) {
	    return logDao.deleteLogComment(commentId);
	}
	// 댓글수정 
	@Override
	public int updateLogComment(LogCommentVO comment) {
	    return logDao.updateLogComment(comment);
	}

	
	
	
	
	


}