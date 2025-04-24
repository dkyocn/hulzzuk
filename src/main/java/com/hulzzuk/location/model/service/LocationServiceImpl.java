package com.hulzzuk.location.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.dao.LocationDao;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;

@Service("locationService")
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	private LocationDao locationDao;

	@Override
	public ModelAndView getLocationById(LocationEnum locationEnum, ModelAndView mv, String locId) {
		
		LocationVO locationVO = null;
		switch(locationEnum) {
		case  ACCO : locationVO =  locationDao.getAccoById(locId);	break;
		case REST : locationVO =  locationDao.getRestById(locId);		break;
		case ATTR : locationVO =  locationDao.getAttrById(locId);		break;
		}
		
		
		if(locationVO != null) {
			mv.addObject("location", locationVO);
			mv.setViewName("location/locationDetailView");
			return mv;
		} else {
			throw new IllegalArgumentException("");
		}
	}

}
