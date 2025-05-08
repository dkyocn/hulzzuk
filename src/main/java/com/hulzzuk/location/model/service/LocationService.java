package com.hulzzuk.location.model.service;

<<<<<<< HEAD
import java.util.HashMap;

=======
import com.hulzzuk.location.model.vo.LocationVO;
>>>>>>> 1efd3d1c217c70ee61e1e9e19cbbee3fb98f8cc9
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;

import java.util.Map;

public interface LocationService {
	//  상세페이지 요청 처리용 메소드
	ModelAndView getLocationById(LocationEnum locationEnum, ModelAndView mv,String locId);
<<<<<<< HEAD
	// 검색 페이지 리스트 조회
	ModelAndView getLocationPage( LocationEnum locationEnum, String keyword, String page, String limit, SortEnum sortEnum, ModelAndView mv);
	// 리스트 갯수 조회
=======
	ModelAndView getLocationPage( LocationEnum locationEnum, String keyword, String page, String limit, SortEnum sortEnum, ModelAndView mv);
>>>>>>> 1efd3d1c217c70ee61e1e9e19cbbee3fb98f8cc9
    int getLocationListCount(String keyword, SortEnum sortEnum, LocationEnum locationEnum);
	ModelAndView getLocationList(String keyword, LocationEnum locationEnum, ModelAndView mv);
	Map<String, Object> getLocation(LocationEnum locationEnum, String locId);
	Map<String, Double> getDistance(Map<String, String> locationMap);
}
