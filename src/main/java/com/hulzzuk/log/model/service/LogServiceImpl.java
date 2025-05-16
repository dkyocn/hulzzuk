package com.hulzzuk.log.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.dao.LogReviewDao;
import com.hulzzuk.log.model.vo.LogCommentVO;
import com.hulzzuk.log.model.vo.LogReviewVO;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.vo.PlanVO;


@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;
    @Autowired
    private SqlSession sqlSession;
    
   // @Autowired
    //private LogReviewDao logReviewDao; // 로그탭안의 내용 조회를 위해서 주입

    @Override
    public List<LogVO> getLogList(Paging paging) {
        return logDao.getLogList(paging);
    }

    @Override
    public int getLogCount() {
        return logDao.getLogCount();
    }
    
    //로그디테일뷰  **********************
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


	//로그디테일뷰 **********************
	@Override
    public List<LogReviewVO> getReviewsByLogId(long logId) {
        return logDao.getReviewsByLogId(logId);
    }

	//로그디테일뷰 **********************
	@Override
	public List<LogCommentVO> getCommentsByLogId(Long logId) {
		return logDao.getCommentsByLogId(logId);
	}

	//로그디테일뷰 **********************
	@Override
    public List<LogCommentVO> getRepliesByCommentIds(List<Long> commentIdList) {
        return logDao.getRepliesByCommentIds(commentIdList);
    }
	//로그 댓글 입력 
	@Override
	public void insertComment(LogCommentVO comment) {
		logDao.insertTopLevelComment(comment);
	}
	
	/**
	 * 
	 * @Override
public LogVO selectRecentLogId() {
    return logDao.selectRecentLogId();
}

@Override
public void insertLog(LogVO log) {
    logDao.insertLog(log);
}

@Override
public void insertTripLog(LogReviewVO review) {
    logDao.insertTripLog(review);
}
	 * 
	 */

//	@Override
//	public List<LogPlaceVO> getPlacesByPlanDay(Long planId, int day) {
//		
//		return logReviewDao.selectByPlanDay(planId,day);
//	}
//
//	@Override
//	public List<LogReviewVO> getReviewsByLogId(Long logId) {
//		 return logDao.getReviewsByLogId(logId);
//	}
	
//	
//	@Override
//    public List<LogCommentVO> getCommentTreeByLogId(Long logId) {
//        List<LogCommentVO> comments = logDao.getTopLevelComments(logId);
//        for (LogCommentVO comment : comments) {
//            List<LogCommentVO> replies = logDao.getRepliesByParentId(comment.getCommentId());
//            comment.setReplies(replies);
//        }
//        return comments;
//    }

//	@Override
//    public void insertComment(LogCommentVO comment) {
//        if (comment!=null && comment.getParentCommentId() == null) {
//            logDao.insertTopLevelComment(comment);   //댓글 
//        } else {
//            logDao.insertReplyComment(comment);   //대댓글    
//            			//   void insertReplyComment(LogCommentVO comment); 인터페이스필요없음.분기해서내부에서만쓰므
//        }
//    }
	
	
}
