package com.hulzzuk.location.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.service.LocationService;

@Controller
public class LocationController {
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private LocationService locationService;
	
//  상세보기 요청 처리용 메소드
	@RequestMapping("select.do")
	public ModelAndView locationDetailMethod(@RequestParam(name = "locId") String locId, ModelAndView mv, LocationEnum locationEnum) {

		return locationService.getLocationById(locationEnum, mv, locId);

	}
	
	// 요청처리하는 메소드(db 까지 연결되는 요청)
	
	/**
	 * 전체 목록보기 요청 처리용 메소드
	 * @param mv
	 * @param locationEnum
	 * @param keyword
	 * @param pageCriteria 
	 * @param sortEnum
	 * @return
	 */
	/*@RequestMapping("page.do")
	public ModelAndView getLocationList(ModelAndView mv, LocationEnum locationEnum, @RequestParam(name="keyword", required= false) String keyword, 
			SortEnum sortEnum) {
		
		ArrayList<Location> list = locationService.selectList(paging);

		if (list != null && list.size() > 0) { // 조회 성공시
			// ModelAndView : Model + View
			mv.addObject("list", list); // request.setAttribute("list", list) 와 같음
			mv.addObject("paging", paging);

			mv.setViewName("location/locationListView");
		} else { // 조회 실패시
			mv.addObject("message", currentPage + "페이지에 출력할 숙소 목록 조회 실패!");
			mv.setViewName("common/error");
		}

		return mv;
	}*/
	
}
