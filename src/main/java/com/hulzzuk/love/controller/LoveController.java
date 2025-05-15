package com.hulzzuk.love.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("love")
public class LoveController {

	@Autowired
	private LoveService loveService;
	
	// My 찜 페이지 이동
	@RequestMapping(value = "moveLove.do") 
	@ResponseBody
	public ModelAndView moveLovePage(ModelAndView mv, HttpSession session) {
	    return loveService.selectAllLoveList(mv, session);
	}
	
	// 찜 등록
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertLove(HttpSession session, @RequestParam(name = "locationEnum") LocationEnum locationEnum, 
			@RequestParam("locId") String locId) {
	    return loveService.insertLove(session, locationEnum, locId);
	}
	
	// 찜 여부 반환
	@RequestMapping(value = "check.do")
	@ResponseBody
	public Map<String, Object> checkLove(HttpSession session,
	        @RequestParam("locationEnum") LocationEnum locationEnum,
	        @RequestParam("locId") String locId) {
	    return loveService.checkLoveStatus(session, locationEnum, locId);
	}
	/*
	 * // 찜 카운트
	 * 
	 * @RequestMapping(value = "count.do")
	 * 
	 * @ResponseBody public Map<String, Object> getLoveCount(@RequestParam("locId")
	 * String locId,
	 * 
	 * @RequestParam("locationEnum") LocationEnum locationEnum) { int count =
	 * loveService.loveCount(locId, locationEnum); Map<String, Object> result = new
	 * HashMap<>(); result.put("count", count); return result; }
	 */
	
	// 찜 해제
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteLove(HttpSession session,
	        @RequestParam("locationEnum") LocationEnum locationEnum,
	        @RequestParam("locId") String locId) {
	    return loveService.deleteLove(session, locationEnum, locId);
	}

	/*
	 * @RequestMapping(value = "delete.do", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Map<String, Object> deleteLove(@RequestParam("loveId")
	 * long loveId) { return loveService.deleteLove(loveId); }
	 */
	
}
