package com.hulzzuk.location.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hulzzuk.location.model.vo.LocationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.service.LocationService;
import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.common.vo.Paging;

@Controller
@RequestMapping("loc")
public class LocationController {
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private LocationService locationService;
	
//  상세보기 요청 처리용 메소드
	  @RequestMapping("select.do") 
	  public ModelAndView locationDetailMethod(@RequestParam(name = "locId") String locId, 
			  ModelAndView mv,@RequestParam(name = "locationEnum") LocationEnum locationEnum) {
	  
	  return locationService.getLocationById(locationEnum, mv, locId);
	  
	  }
	
	// 검색페이지(페이징 처리)
	  @RequestMapping("page.do")
	    public ModelAndView getLocationPage(ModelAndView mv,
	                                        @RequestParam(name = "keyword", required = false) String keyword,
	                                        @RequestParam(name = "locationEnum") LocationEnum locationEnum,
	                                        @RequestParam(name = "sortEnum", required = false) SortEnum sortEnum,
	                                        @RequestParam(name = "page", required = false) String  page,
	                                        @RequestParam(name = "limit", required = false) String limit) {

	        return locationService.getLocationPage(locationEnum, keyword, page, limit, sortEnum, mv);
	    }

		// location list 조회
		@RequestMapping("list.do")
		public ModelAndView getLocationList(ModelAndView mv,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam(name = "locationEnum") LocationEnum locationEnum) {
			return locationService.getLocationList(keyword, locationEnum, mv);
		}

		// location 단일 조회 ( Map return)
		@RequestMapping("getLocation.do")
		@ResponseBody
		public Map<String, Object> getLocationById(@RequestParam(name ="locId") String locId, @RequestParam(name = "locationEnum") LocationEnum locationEnum) {
		  return locationService.getLocation(locationEnum, locId);
		}

		// 두 장소의 거리 계산
		@RequestMapping(value = "getDistance.do", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Double> getDistance(@RequestBody Map<String, String> locationMap) {
			return locationService.getDistance(locationMap);
		}
		
		@RequestMapping(value = "locTop3.do")
		@ResponseBody
		public Map<String, List<LocationVO>> getTop3LocList(){
			return locationService.getTop3LocList();
		}
}
