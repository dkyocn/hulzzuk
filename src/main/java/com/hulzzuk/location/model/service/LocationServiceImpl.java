package com.hulzzuk.location.model.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.dao.LocationDao;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;
import com.hulzzuk.log.model.service.LogService;
import com.hulzzuk.love.model.service.LoveService;
import com.hulzzuk.review.model.service.ReviewService;

@Service("locationService")
public class LocationServiceImpl implements LocationService{

	private static final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private LoveService loveService;
	
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
			// 평균 평점 계산
	        Double avgRating = reviewService.getAvgRating(locId, locationEnum);
	        
	        // 찜 개수 계산
	        int loveCount = loveService.getLocLoveCount(locId, locationEnum);
	        
			mv.addObject("location", locationVO);
			mv.addObject("locationEnum", locationEnum);
			mv.addObject("avgRating", avgRating != null ? avgRating : 0.0);  // 평균 평점 추가
			mv.addObject("loveCount", loveCount == 0 ? 0 : loveCount);	// 찜 개수 추가
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
        
        if(keyword != null && !keyword.isEmpty()) {
        	JsonNode kakaoloc = getLocationInfo(keyword, locationEnum);
            JsonNode kakaolocList = kakaoloc.get("documents");
            
            if(kakaolocList != null && kakaolocList.isArray()) {
            	kakaolocList.forEach(JsonNode  -> {
                	String id = JsonNode.get("id").toString();
    
                	switch(locationEnum) {
                	case ACCO : 
                		LocationVO acco = locationDao.getAccoById(JsonNode.get("id").asText());
                		if(acco == null) {
                			if("AD5".equals(JsonNode.get("category_group_code").asText())) {
	                			acco = convertJsonToLocationVO(JsonNode, locationEnum);
	                			int result = locationDao.insertAcco(acco);
	                			if(result <= 0) {
	                				log.warn("숙소 insert 실패");
	                			}
                			}
                		}
                		break;
                	case REST : 
                		LocationVO rest = locationDao.getRestById(JsonNode.get("id").asText());	
                		if(rest == null) {
                			if("CE7".equals(JsonNode.get("category_group_code").asText()) || "FD6".equals(JsonNode.get("category_group_code").asText())) {
	                			rest = convertJsonToLocationVO(JsonNode, locationEnum);
	                			int result = locationDao.insertRest(rest);
	                			if(result <= 0) {
	                				log.warn("맛집 insert 실패");
	                			}
                			}
                		}
                		break;
                	case ATTR : 
                		LocationVO attr = locationDao.getAttrById(JsonNode.get("id").asText());	
                		if(attr == null) {
                			attr = convertJsonToLocationVO(JsonNode, locationEnum);
                			if("CT1".equals(JsonNode.get("category_group_code").asText()) || "AT4".equals(JsonNode.get("category_group_code").asText())) {
	                			int result = locationDao.insertAttr(attr);
	                			if(result <= 0) {
	                				log.warn("즐길거리 insert 실패");
	                			}
                			}
                		}
                		break;
                	case ALL : locationDao.getAccoById(JsonNode.get("id").asText());	
                						   locationDao.getRestById(JsonNode.get("id").asText());
                						   locationDao.getAttrById(JsonNode.get("id").asText());	break;
                	}
                });
            }
        }
        
        if(locationEnum .equals(LocationEnum.ALL)) {
        	// 숙소 리스트 조회
        	int accoListCount = getLocationListCount(keyword, sortEnum, locationEnum.ACCO);
        	Paging accoPaging = new Paging(keyword, accoListCount, pageLimit, currentPage, "page.do");
        	accoPaging.calculate();
        	List<LocationVO> accoList = locationDao.getLocationPage(locationEnum.ACCO, keyword, accoPaging, sortEnum);

        	for(LocationVO vo : accoList) {
        		// 찜 개수 계산
                int loveCount = loveService.getLocLoveCount(vo.getLocId(), locationEnum.ACCO);
                vo.setLoveCount(loveCount);
        	}
            
            mv.addObject("accoList", accoList);
            mv.addObject("accoPaging", accoPaging);
            
            // 맛집
            int restListCount = getLocationListCount(keyword, sortEnum, locationEnum.REST);
        	Paging restPaging = new Paging(keyword, restListCount, pageLimit, currentPage, "page.do");
        	restPaging.calculate();
        	List<LocationVO> restList = locationDao.getLocationPage(locationEnum.REST, keyword, restPaging, sortEnum);

        	for(LocationVO vo : restList) {
        		// 찜 개수 계산
                int loveCount = loveService.getLocLoveCount(vo.getLocId(), locationEnum.REST);
                vo.setLoveCount(loveCount);
        	}
            
            mv.addObject("restList", restList);
            mv.addObject("restPaging", restPaging);
            
            // 즐길거리
            int attrListCount = getLocationListCount(keyword, sortEnum, locationEnum.ATTR);
        	Paging attrPaging = new Paging(keyword, attrListCount, pageLimit, currentPage, "page.do");
        	attrPaging.calculate();
        	List<LocationVO> attrList = locationDao.getLocationPage(locationEnum.ATTR, keyword, attrPaging, sortEnum);

        	for(LocationVO vo : attrList) {
        		// 찜 개수 계산
                int loveCount = loveService.getLocLoveCount(vo.getLocId(), locationEnum.ATTR);
                vo.setLoveCount(loveCount);
        	}
            
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

            for(LocationVO vo : locationList) {
                int loveCount = loveService.getLocLoveCount(vo.getLocId(), locationEnum);
                vo.setLoveCount(loveCount);
            }
            
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

	@Override
	public ModelAndView getLocationList(String keyword, LocationEnum locationEnum, ModelAndView mv) {

		List<LocationVO> locationList = locationDao.getLocationList(locationEnum, keyword);

		mv.addObject("locationList", locationList);
		mv.setViewName("location/locationPopUp");
		return mv;
	}

	@Override
	public Map<String, Object> getLocation(LocationEnum locationEnum, String locId) {
		Map<String, Object> map = new HashMap<>();

		map.put("category", locationEnum);
		switch(locationEnum) {
			case ACCO :
				map.put("locationVo", locationDao.getAccoById(locId));
				break;
			case REST:
				map.put("locationVo", locationDao.getRestById(locId));
				break;
			case ATTR :
				map.put("locationVo", locationDao.getAttrById(locId));
				break;
		}


		return map;
	}

	@Override
	public Map<String, Double> getDistance(Map<String, String> locationMap) {
		LocationVO fromLocVO = null;
		LocationVO toLocVO = null;

		Map<String, Double> map = new HashMap<>();

		String[] ids = locationMap.keySet().toArray(new String[0]);

        log.info("ids: {}", Arrays.toString(ids));

		for (int i=0; i<ids.length; i++) {
			switch(LocationEnum.valueOf(locationMap.get(ids[i]))) {
				case LocationEnum.ACCO : if (i == 0) {
					fromLocVO = locationDao.getAccoById(ids[i]);
				} else {
					toLocVO = locationDao.getAccoById(ids[i]);
				} 	break;
				case LocationEnum.REST : if (i == 0) {
					fromLocVO = locationDao.getRestById(ids[i]);
				} else {
					toLocVO = locationDao.getRestById(ids[i]);
				}	break;
				case LocationEnum.ATTR : if (i == 0) {
					fromLocVO = locationDao.getAttrById(ids[i]);
				} else {
					toLocVO = locationDao.getAttrById(ids[i]);
				}	break;
			}
		}

		map.put("distance", calculateDistance(fromLocVO, toLocVO));

		return map;
	}

	private double calculateDistance(LocationVO fromLocVO, LocationVO toLocVO) {


		// 위도와 경도 추출 (getX(): 위도, getY(): 경도)
		double lat1 = fromLocVO.getX(); // 위도 (latitude)
		double lon1 = fromLocVO.getY(); // 경도 (longitude)
		double lat2 = toLocVO.getX();
		double lon2 = toLocVO.getY();

		// 지구 반지름 (km)
		final double R = 6371.0;

		// 위도와 경도를 라디안으로 변환
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		// Haversine 공식
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c;

		// 소수점 첫째 자리까지 반올림
		return Math.round(distance * 10) / 10.0;
	}

	 public JsonNode getLocationInfo(String keyword, LocationEnum locationEnum) {
	        final String requestUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query="+ keyword;
	        
	        HttpClient client = HttpClientBuilder.create().build();
	        HttpGet get = new HttpGet(requestUrl);
	        
	        get.setHeader("Authorization", "KakaoAK 7e7c827b84e5f3847ec771f158f01cdc");

	        JsonNode returnNode = null;

	        try {
	            var response = client.execute(get);
	            ObjectMapper mapper = new ObjectMapper();
	            returnNode = mapper.readTree(response.getEntity().getContent());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return returnNode;
	    }
	 
	 public String convertJsonToLocationImage(String placeName) {
		 placeName = URLEncoder.encode(placeName, StandardCharsets.UTF_8);
		 
		 final String requestUrl = "https://dapi.kakao.com/v2/search/image?query="+ placeName;
		 
		 HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(requestUrl);
        
        get.setHeader("Authorization", "KakaoAK 7e7c827b84e5f3847ec771f158f01cdc");

        JsonNode returnNode = null;
        String imageUrl = new String();
        try {
            var response = client.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());
            JsonNode kakaolocList = returnNode.get("documents");
            imageUrl = kakaolocList.get(0).get("image_url").asText();
            if(imageUrl.split("type").length == 2) {
            	imageUrl = "/hulzzuk/resources/images/logList/no_image.jpg";
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
	 }
	 
	 
	 public LocationVO convertJsonToLocationVO(JsonNode jsonNode, LocationEnum locationEnum) {
		 LocationVO vo = new LocationVO();
	        vo.setLocId(jsonNode.get("id").asText());
	        vo.setPlaceName(jsonNode.get("place_name").asText());
	       String imageUrl = convertJsonToLocationImage(jsonNode.get("place_name").asText());
	        vo.setImgPath(imageUrl);
	        if(!(jsonNode.get("road_address_name").asText().isBlank())) {
	        	vo.setAddressName(jsonNode.get("road_address_name").asText());
	        }else {
	        	vo.setAddressName(jsonNode.get("address_name").asText());
	        }
	        vo.setPhone(jsonNode.get("phone").asText());
	        vo.setX(jsonNode.get("x").asDouble());
	        vo.setY(jsonNode.get("y").asDouble());
	        vo.setCategory(jsonNode.get("category_group_code").asText());
	        if(locationEnum == LocationEnum.ACCO) {
	        	vo.setPlaceUrl(jsonNode.get("place_url").asText());
	        }
			if(locationEnum == LocationEnum.REST) {
				vo.setRestMenu(jsonNode.get("place_url").asText());
			}
	        return vo;
	 }
}
