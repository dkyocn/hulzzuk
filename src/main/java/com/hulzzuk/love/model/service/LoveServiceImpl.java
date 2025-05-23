package com.hulzzuk.love.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.dao.LocationDao;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;
import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.love.model.dao.LoveDao;
import com.hulzzuk.love.model.vo.LoveVO;
import com.hulzzuk.user.model.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Service("loveService")
public class LoveServiceImpl implements LoveService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private LoveDao loveDao;

	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private LogDao logDao;
	
	// 여행지 찜 등록
	@Override
	public Map<String, Object> insertLove(HttpSession session, LocationEnum locationEnum, String locId) {
		Map<String, Object> result = new HashMap<>();
		String sessionUserId = (String) session.getAttribute("authUserId");
		
		LoveVO loveVO = new LoveVO();
		
		try {
	        // 필수값 체크: userId는 필수, 4개 대상 중 하나는 반드시 존재
	        if (sessionUserId.isBlank() || locationEnum == null && locId.isBlank()) {
	            result.put("success", false);
	            result.put("message", "잘못된 요청입니다.");
	            return result;
	        }else {
	        	int successYN = 0;
	        	// 중복 확인
	        	if (loveDao.selectLoveExists(loveVO) > 0) {
	                result.put("success", false);
	                result.put("message", "이미 찜한 항목입니다.");
	                return result;
	            }
	        	loveVO.setUserId(sessionUserId);
	        	switch(locationEnum) {
	        	case ACCO : loveVO.setAccoId(locId);
	        				successYN = loveDao.insertAccoLove(loveVO);
	        				break;
	        	case REST : loveVO.setRestId(locId);
	        				successYN = loveDao.insertRestLove(loveVO);
	        				break;
	        	case ATTR : loveVO.setAttrId(locId);
	        				successYN = loveDao.insertAttrLove(loveVO);
	        				break;
	        	}
	        	
		        if (successYN > 0) {
		        	// 찜 등록 성공
		            result.put("success", true);
		        } else {
		        	// 찜 등록 실패
		            result.put("success", false);
		            result.put("message", "찜 등록에 실패했습니다.");
		        }
	        }
	    } catch (Exception e) {
	        result.put("success", false);
	    }
	    return result;
	}
	
	// 여행지 찜 중복 확인
	@Override
	public Map<String, Object> checkLoveStatus(HttpSession session, LocationEnum locationEnum, String locId) {
	    Map<String, Object> result = new HashMap<>();
	    String userId = (String) session.getAttribute("authUserId");

	    if (userId == null || locationEnum == null || locId == null || locId.isBlank()) {
	        result.put("loved", false); // 로그인 안 됐거나 파라미터 이상 시 찜 안 한 것으로 처리
	        return result;
	    }

	    LoveVO loveVO = new LoveVO();
	    loveVO.setUserId(userId);

	    switch (locationEnum) {
	        case ACCO -> loveVO.setAccoId(locId);
	        case REST -> loveVO.setRestId(locId);
	        case ATTR -> loveVO.setAttrId(locId);
	    }

	    // 기존 중복 확인 로직 그대로 사용
	    boolean isLoved = loveDao.selectLoveExists(loveVO) > 0;
	    result.put("loved", isLoved);

	    return result;
	}

	// 여행지 찜 개수 조회
	public int getLocLoveCount(String locId, LocationEnum locationEnum) {
		int loveCount = loveDao.getLocLoveCount(locId, locationEnum);
	
		return loveCount == 0 ? 0 : loveCount; 
	}
	
	// 여행지 찜 해제
	@Override
	public Map<String, Object> deleteLove(HttpSession session, LocationEnum locationEnum, String locId) {
	    Map<String, Object> result = new HashMap<>();
	    String sessionUserId = (String) session.getAttribute("authUserId");
	    logger.info("sessionId : " + sessionUserId);

	    if (sessionUserId == null || locationEnum == null || locId == null || locId.isBlank()) {
	        result.put("success", false);
	        result.put("message", "유효하지 않은 요청입니다.");
	        return result;
	    }

	    LoveVO loveVO = new LoveVO();
	    loveVO.setUserId(sessionUserId);

	    switch (locationEnum) {
	        case ACCO -> loveVO.setAccoId(locId);
	        case REST -> loveVO.setRestId(locId);
	        case ATTR -> loveVO.setAttrId(locId);
	    }
	    try {
	        int deleted = loveDao.deleteLoveByCondition(loveVO);
	        result.put("success", deleted > 0);
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "삭제 중 오류 발생: " + e.getMessage());
	    }

	    return result;
	}

	
	
	// --------------------------------------------------------------------------
	
	
	
	// 로그 찜 등록
	@Override
	public Map<String, Object> insertLogLove(HttpSession session, Long logId) {
		Map<String, Object> result = new HashMap<>();
		String userId = (String) session.getAttribute("authUserId");
		
		if (userId == null || logId == null) {
	        result.put("success", false);
	        result.put("message", "잘못된 요청입니다.");
	        return result;
	    }
		
		LoveVO loveVO = new LoveVO();
		loveVO.setUserId(userId);
    	loveVO.setLogId(logId);
    	
        try {
        	// 중복 확인
        	if (loveDao.selectLogLoveExists(loveVO) > 0) {
                result.put("success", false);
                result.put("message", "이미 찜한 항목입니다.");
                return result;
            }
        	
        	int successYN = loveDao.insertLogLove(loveVO);
			
	        if (successYN > 0) {
	        	// 찜 등록 성공
	            result.put("success", true);
	        } else {
	        	// 찜 등록 실패
	            result.put("success", false);
	            result.put("message", "찜 등록에 실패했습니다.");
	        }
	    }catch (Exception e) {
	        result.put("success", false);
	    }
	    return result;
	}
	
	// 로그 찜 중복 확인
	@Override
	public Map<String, Object> checkLogLoveStatus(HttpSession session, Long logId) {
		Map<String, Object> result = new HashMap<>();
	    String userId = (String) session.getAttribute("authUserId");

	    if (userId == null || logId == null) {
	        result.put("loved", false); // 로그인 안 됐거나 파라미터 이상 시 찜 안 한 것으로 처리
	        return result;
	    }

	    LoveVO loveVO = new LoveVO();
	    loveVO.setUserId(userId);
	    loveVO.setLogId(logId);
	    
	    // 기존 중복 확인 로직 그대로 사용
	    boolean isLoved = loveDao.selectLogLoveExists(loveVO) > 0;
	    result.put("loved", isLoved);

	    return result;
	}
	
	// 로그 찜 개수 조회
	public int getLogLoveCount(Long logId) { 
		int loveCount = loveDao.getLogLoveCount(logId);
  
		return loveCount == 0 ? 0 : loveCount; 
	}
	 
	// 로그 찜 해제
	@Override 
	public Map<String, Object> deleteLogLove(HttpSession session, Long logId) { 
		Map<String, Object> result = new HashMap<>();
	    String userId = (String) session.getAttribute("authUserId");
	    logger.info("sessionId : " + userId);

	    if (userId == null || logId == null) {
	        result.put("success", false);
	        result.put("message", "유효하지 않은 요청입니다.");
	        return result;
	    }

	    LoveVO loveVO = new LoveVO();
	    loveVO.setUserId(userId);
	    loveVO.setLogId(logId);

	    try {
	        int deleted = loveDao.deleteLoveByCondition(loveVO);
	        result.put("success", deleted > 0);
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "삭제 중 오류 발생: " + e.getMessage());
	    }

	    return result;
	}
	 
	
	// ---------------------------------------------------------------
	
	
	// 전체 찜 리스트
	@Override
	public ModelAndView selectAllLoveList(ModelAndView mv, HttpSession session, String category) {
	    Map<String, Object> result = new HashMap<>();
	    String userId = (String) session.getAttribute("authUserId");
	    
	    // 세션 없을 경우 로그인 페이지로 이동
	    if(userId == null) {
	    	mv.setViewName("redirect:/user/loginSelect.do");
	    	return mv;
	    }
	    
	    // 1. 찜 정보 불러오기
	    List<LoveVO> loveList = loveDao.selectAllLoveList(userId);
	    logger.info("sessionUserId : " + userId);

	    // 2. 분류용 리스트 생성
	    List<String> accoIds = new ArrayList<>();
	    List<String> restIds = new ArrayList<>();
	    List<String> attrIds = new ArrayList<>();
	    List<Long> logIds = new ArrayList<>();

	    for (LoveVO love : loveList) {
	        if (love.getAccoId() != null) accoIds.add(love.getAccoId());
	        if (love.getRestId() != null) restIds.add(love.getRestId());
	        if (love.getAttrId() != null) attrIds.add(love.getAttrId());
	        if (love.getLogId() != null) logIds.add(love.getLogId());
	    }

	    // 3. Location&Log 리스트 구성
	    List<LocationVO> locationList = new ArrayList<>();
	    List<LogVO> logList = new ArrayList<>();
	    
	    switch (category) {
		    case "ACCO":
			    for (String id : accoIds) {
			    	LocationVO locationVO = locationDao.getAccoById(id);
			    	locationVO.setLocationEnum(LocationEnum.ACCO);
			        locationList.add(locationVO);
			    }
			    break;
		    case "REST":
			    for (String id : restIds) {
			    	LocationVO locationVO = locationDao.getRestById(id);
			    	locationVO.setLocationEnum(LocationEnum.REST);
			        locationList.add(locationVO);
			    }
			    break;
		    case "ATTR":
			    for (String id : attrIds) {
			    	LocationVO locationVO = locationDao.getAttrById(id);
			    	locationVO.setLocationEnum(LocationEnum.ATTR);
			        locationList.add(locationVO);
			    }
			    break;
		    case "LOG":
		    	if (!logIds.isEmpty()) {
		    		logList = logDao.getLogListByIds(logIds);
		    	}
		    	break;
		    case "ALL":
		    default:
                for (String id : accoIds) {
                    LocationVO vo = locationDao.getAccoById(id);
                    vo.setLocationEnum(LocationEnum.ACCO);
                    locationList.add(vo);
                }
                for (String id : restIds) {
                    LocationVO vo = locationDao.getRestById(id);
                    vo.setLocationEnum(LocationEnum.REST);
                    locationList.add(vo);
                }
                for (String id : attrIds) {
                    LocationVO vo = locationDao.getAttrById(id);
                    vo.setLocationEnum(LocationEnum.ATTR);
                    locationList.add(vo);
                }
                if (!logIds.isEmpty()) {
                    logList = logDao.getLogListByIds(logIds);
                }
                break;
	    }


	    mv.addObject("location", locationList);
	    mv.addObject("log", logList);
	    mv.setViewName("love/loveView");
	    
	    logger.info(">>>> locationList size: " + locationList.size());
	    logger.info(">>>> logList size: " + logList.size());
	    return mv;
	}

}
