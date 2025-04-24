package com.hulzzuk.location.model.service;

import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;

public interface LocationService {
	
	ModelAndView getLocationById(LocationEnum locationEnum, ModelAndView mv,String locId);
}
