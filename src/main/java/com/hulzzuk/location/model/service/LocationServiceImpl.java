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
import com.hulzzuk.log.model.service.LogService;
import com.hulzzuk.review.model.service.ReviewService;

@Service("locationService")
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private LogService logService;
	
	//  상세페이지 요청 처리용 메소드
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
			mv.addObject("locationEnum", locationEnum);
			mv.addObject("reviewCount", reviewService.reviewCount(locId, locationEnum));
			mv.addObject("reviewList", reviewService.getReviewList(locId, locationEnum, null));
			mv.addObject("userNicks", reviewService.getUserNicks(locId, locationEnum, null));
			mv.addObject("logCount", logService.logCount(locId, locationEnum));
			mv.addObject("logList", logService.getLocLogList(locId, locationEnum));
			mv.setViewName("location/locationDetailView");
			return mv;
		} else {
			throw new IllegalArgumentException("");
		}
	}

	// 검색 페이지 리스트 조회
	@Override
	public ModelAndView getLocationPage(LocationEnum locationEnum, String keyword, String page, String limit,
			SortEnum sortEnum, ModelAndView mv) {
		// 페이징 처리
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }
        
        //한 페이지에 출력할 목록 갯수 기본 15개로 지정함
        int pageLimit = 15; // 기본 15개
        if (limit != null) {
            pageLimit = Integer.parseInt(limit);
        }

        if(locationEnum .equals(LocationEnum.ALL)) {
        	// 숙소
        	int accoListCount = getLocationListCount(keyword, sortEnum, locationEnum.ACCO);
        	Paging accoPaging = new Paging(keyword, accoListCount, pageLimit, currentPage, "page.do");
        	accoPaging.calculate();
        	List<LocationVO> accoList = locationDao.getLocationPage(locationEnum.ACCO, keyword, accoPaging, sortEnum);

            mv.addObject("accoList", accoList);
            mv.addObject("accoPaging", accoPaging);
            
            // 맛집
            int restListCount = getLocationListCount(keyword, sortEnum, locationEnum.REST);
        	Paging restPaging = new Paging(keyword, restListCount, pageLimit, currentPage, "page.do");
        	restPaging.calculate();
        	List<LocationVO> restList = locationDao.getLocationPage(locationEnum.REST, keyword, restPaging, sortEnum);

            mv.addObject("restList", restList);
            mv.addObject("restPaging", restPaging);
            
            // 즐길거리
            int attrListCount = getLocationListCount(keyword, sortEnum, locationEnum.ATTR);
        	Paging attrPaging = new Paging(keyword, attrListCount, pageLimit, currentPage, "page.do");
        	attrPaging.calculate();
        	List<LocationVO> attrList = locationDao.getLocationPage(locationEnum.ATTR, keyword, attrPaging, sortEnum);

            mv.addObject("attrList", attrList);
            mv.addObject("attrPaging", attrPaging);
        	mv.setViewName("location/locationAllListView");
        }else {
        	// 총 목록 갯수 조회해서. 총 페이지 수 계산함
            int listCount = getLocationListCount(keyword, sortEnum, locationEnum);
            // 페이지 관련 항목들 계산 처리
            Paging paging = new Paging(keyword, listCount, pageLimit, currentPage, "page.do");
            paging.calculate();

            List<LocationVO> locationList = locationDao.getLocationPage(locationEnum, keyword, paging, sortEnum);

            mv.addObject("list", locationList);
            mv.addObject("paging", paging);
            mv.addObject("locationEnum", locationEnum);
        	mv.setViewName("location/locationListView");
        }
        mv.addObject("keyword",keyword);
        mv.addObject("sortEnum", sortEnum);
        return mv;
	}

	// 리스트 갯수 조회
	@Override
	public int getLocationListCount(String keyword, SortEnum sortEnum, LocationEnum locationEnum) {
		return locationDao.getLocationListCount(locationEnum, keyword, sortEnum);
    }

}
