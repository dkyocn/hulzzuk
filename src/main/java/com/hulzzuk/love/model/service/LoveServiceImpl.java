package com.hulzzuk.love.model.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.love.model.dao.LoveDao;
import com.hulzzuk.love.model.vo.LoveVO;
import com.hulzzuk.user.model.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Service("loveService")
public class LoveServiceImpl implements LoveService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private LoveDao loveDao;

	// 찜 등록
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
	        	switch(locationEnum) {
	        	case ACCO : loveVO.setAccoId(locId);
	        				loveVO.setUserId(sessionUserId);
	        				successYN = loveDao.insertAccoLove(loveVO);
	        				
	        				break;
	        	case REST : loveVO.setRestId(locId);
	        				loveVO.setUserId(sessionUserId);
	        				successYN = loveDao.insertRestLove(loveVO);
	        				break;
	        	case ATTR : loveVO.setAttrId(locId);
	        				loveVO.setUserId(sessionUserId);
	        				successYN = loveDao.insertLogLove(loveVO);
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
	
	
	// 찜 해제
	@Override
	public Map<String, Object> deleteLove(long loveId) {
		Map<String, Object> result = new HashMap<>();

	    try {
	        LoveVO love = new LoveVO();
	        love.setLoveId(loveId);

	        int deleteResult = loveDao.deleteLove(love);

	        if (deleteResult > 0) {
	        	// 찜 삭제 성공
	            result.put("success", true);
	        } else {
	            result.put("success", false);
	        }

	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "서버 오류: " + e.getMessage());
	    }

	    return result;
	}
}
