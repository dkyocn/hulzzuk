package com.hulzzuk.location.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;

import java.util.Map;

public interface LocationService {
	//  상세페이지 요청 처리용 메소드
	ModelAndView getLocationById(LocationEnum locationEnum, ModelAndView mv,String locId);
	// 검색 페이지 리스트 조회
	ModelAndView getLocationPage( LocationEnum locationEnum, String keyword, String page, String limit, SortEnum sortEnum, ModelAndView mv);
	// 리스트 갯수 조회
    int getLocationListCount(String keyword, SortEnum sortEnum, LocationEnum locationEnum);
	ModelAndView getLocationList(String keyword, LocationEnum locationEnum, ModelAndView mv);
	Map<String, Object> getLocation(LocationEnum locationEnum, String locId);
	Map<String, Double> getDistance(Map<String, String> locationMap);
	ModelAndView getTop3LocList(ModelAndView mv);
}
