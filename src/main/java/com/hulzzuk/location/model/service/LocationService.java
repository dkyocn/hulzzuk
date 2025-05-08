package com.hulzzuk.location.model.service;

import com.hulzzuk.location.model.vo.LocationVO;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;

import java.util.Map;

public interface LocationService {
	
	ModelAndView getLocationById(LocationEnum locationEnum, ModelAndView mv,String locId);
	ModelAndView getLocationPage( LocationEnum locationEnum, String keyword, String page, String limit, SortEnum sortEnum, ModelAndView mv);
    int getLocationListCount(String keyword, SortEnum sortEnum, LocationEnum locationEnum);
	ModelAndView getLocationList(String keyword, LocationEnum locationEnum, ModelAndView mv);
	Map<String, Object> getLocation(LocationEnum locationEnum, String locId);
	Map<String, Double> getDistance(Map<String, String> locationMap);
}
