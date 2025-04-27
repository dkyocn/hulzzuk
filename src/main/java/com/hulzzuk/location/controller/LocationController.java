package com.hulzzuk.location.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	    public ModelAndView getLocationList(ModelAndView mv,
	                                        @RequestParam(name = "keyword", required = false) String keyword,
	                                        @RequestParam(name = "locationEnum") LocationEnum locationEnum,
	                                        @RequestParam(name = "sortEnum", required = false) SortEnum sortEnum,
	                                        @RequestParam(name = "page", required = false) String  page,
	                                        @RequestParam(name = "limit", required = false) String limit) {

	        return locationService.getLocationList(locationEnum, keyword, page, limit, sortEnum, mv);
	    }


}
