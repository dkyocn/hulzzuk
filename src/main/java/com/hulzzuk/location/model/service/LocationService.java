package com.hulzzuk.location.model.service;

import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;

public interface LocationService {
	
	ModelAndView getLocationById(LocationEnum locationEnum, ModelAndView mv,String locId);
	ModelAndView getLocationList( LocationEnum locationEnum, String keyword, String page, String limit, SortEnum sortEnum, ModelAndView mv);
    int getLocationListCount(String keyword, SortEnum sortEnum, LocationEnum locationEnum);

}
