package com.hulzzuk.love.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.love.model.service.LoveService;
import com.hulzzuk.love.model.vo.LoveVO;
import com.hulzzuk.user.model.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("love")
public class LoveController {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private LoveService loveService;
	
	// My 찜 페이지 이동
	@RequestMapping(value = "page.do") 
	@ResponseBody
	public ModelAndView moveLovePage(ModelAndView mv, HttpSession session,
			@RequestParam(name = "logCategory", defaultValue = "ALL") String category) {
	    return loveService.selectAllLoveList(mv, session, category);
	}
	
	// 여행지 찜 등록
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertLove(HttpSession session, @RequestParam(name = "locationEnum") LocationEnum locationEnum, 
			@RequestParam("locId") String locId) {
	    return loveService.insertLove(session, locationEnum, locId);
	}
	
	// 여행지 찜 중복 확인
	@RequestMapping(value = "check.do")
	@ResponseBody
	public Map<String, Object> checkLoveStatus(HttpSession session,
	        @RequestParam("locationEnum") LocationEnum locationEnum,
	        @RequestParam("locId") String locId) {
	    return loveService.checkLoveStatus(session, locationEnum, locId);
	}
	
	// 여행지 찜 해제
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteLove(HttpSession session,
	        @RequestParam("locationEnum") LocationEnum locationEnum,
	        @RequestParam("locId") String locId) {
	    return loveService.deleteLove(session, locationEnum, locId);
	}

	
	// -----------------------------------------------------------------
	
	
	// 로그 찜 등록
	@RequestMapping(value = "logCreate.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertLogLove(HttpSession session, @RequestParam("logId") Long logId) {
		return loveService.insertLogLove(session, logId);
	}
	
	// 로그 찜 중복 확인
	@RequestMapping(value = "logCheck.do")
	@ResponseBody
	public Map<String, Object> checkLogLoveStatus(HttpSession session, @RequestParam("logId") Long logId) {
		return loveService.checkLogLoveStatus(session, logId);
	}
	
	// 로그 찜 해제
	@RequestMapping(value = "logDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteLogLove(HttpSession session, @RequestParam("logId") Long logId) {
		return loveService.deleteLogLove(session, logId);
	}
	
	// ---------------------------------------------------------------------
	
	
	/*
	 * @RequestMapping(value = "page.do") public ModelAndView
	 * getLovePage(ModelAndView mv, HttpSession session,
	 * 
	 * @RequestParam(name = "logCategory", defaultValue = "ALL") String category) {
	 * return mv; }
	 */
	 
	
}
 