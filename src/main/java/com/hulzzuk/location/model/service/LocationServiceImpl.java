package com.hulzzuk.location.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.dao.LocationDao;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;
import com.hulzzuk.review.model.service.ReviewService;

@Service("locationService")
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private ReviewService reviewService;
	

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
			
			mv.addObject("reviewList", reviewService.getReviewList(locId, locationEnum, null));
			mv.addObject("userNicks", reviewService.getUserNicks(locId, locationEnum, null));
			mv.setViewName("location/locationDetailView");
			return mv;
		} else {
			throw new IllegalArgumentException("");
		}
	}

	@Override
	public ModelAndView getLocationList(LocationEnum locationEnum, String keyword, String page, String limit,
			SortEnum sortEnum, ModelAndView mv) {
		// 페이징 처리
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }
        
        //한 페이지에 출력할 목록 갯수 기본 10개로 지정함
        int pageLimit = 15; // 기본 10개
        if (limit != null) {
            pageLimit = Integer.parseInt(limit);
        }

        // 총 목록 갯수 조회해서. 총 페이지 수 계산함
        int listCount = getLocationListCount(keyword, sortEnum, locationEnum);
        // 페이지 관련 항목들 계산 처리
        Paging paging = new Paging(keyword, listCount, pageLimit, currentPage, "page.do");
        paging.calculate();

        List<LocationVO> locationList = locationDao.getLocationList(locationEnum, keyword, paging, sortEnum);

        mv.addObject("list", locationList);
        mv.addObject("paging", paging);
        mv.addObject("keyword", paging.getKeyword());
        mv.addObject("locationEnum", locationEnum);
        mv.addObject("sortEnum", sortEnum);
        if(locationEnum == locationEnum.ALL) {
        	mv.setViewName("location/locationAllListView");
        }else {
        	mv.setViewName("location/locationListView");
        }
        
        
        return mv;

	}

	@Override
	public int getLocationListCount(String keyword, SortEnum sortEnum, LocationEnum locationEnum) {
		return locationDao.getLocationListCount(locationEnum, keyword, sortEnum);
    }

}
