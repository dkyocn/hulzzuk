package com.hulzzuk.location.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.location.model.vo.LocationVO;

@Repository("locationDao")
public class LocationDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// 숙소명 찾기
	public String getAccoName(String locId) {
	    return sqlSessionTemplate.selectOne("accoMapper.getAccoName", locId);
	}

	// 맛집명 찾기
	public String getRestName(String locId) {
	    return sqlSessionTemplate.selectOne("restMapper.getRestName", locId);
	}

	// 즐길거리명 찾기
	public String getAttrName(String locId) {
	    return sqlSessionTemplate.selectOne("attrMapper.getAttrName", locId);
	}
	
	// 숙소 찾기
	public LocationVO getAccoById(String locId) {
		return sqlSessionTemplate.selectOne("accoMapper.getAccoById", locId);
	}
	// 맛집 찾기
	public LocationVO getRestById(String locId) {
		return sqlSessionTemplate.selectOne("restMapper.getRestById", locId);
	}
	// 즐길거리 찾기
	public LocationVO getAttrById(String locId) {
		return sqlSessionTemplate.selectOne("attrMapper.getAttrById", locId);
	}
	
	// 검색 페이지 리스트 조회
	public List<LocationVO> getLocationPage(LocationEnum locationEnum, String keyword, Paging paging, SortEnum sortEnum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("locationEnum", locationEnum);
        map.put("keyword", keyword);
        map.put("paging", paging);
        map.put("sortEnum", sortEnum != null ? sortEnum.name() : null);

        // 각 LocationEnum에 맞는 조회 쿼리 실행
        switch (locationEnum) {
            case ACCO:
                return sqlSessionTemplate.selectList("accoMapper.getAccoPage", map);
            case REST:
                return sqlSessionTemplate.selectList("restMapper.getRestPage", map);
            case ATTR:
                return sqlSessionTemplate.selectList("attrMapper.getAttrPage", map);
            default:
                return null;
        }
    }

	// 리스트 갯수 조회
    public int getLocationListCount(LocationEnum locationEnum, String keyword, SortEnum sortEnum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("sortEnum", sortEnum != null ? sortEnum.name() : null);
        map.put("locationEnum", locationEnum);
        
        // 각 LocationEnum에 맞는 카운트 조회
        switch (locationEnum) {
        case ACCO:
        	 return sqlSessionTemplate.selectOne("accoMapper.getAccoListCount", map);		
        case REST:
        	return sqlSessionTemplate.selectOne("restMapper.getRestListCount", map);		 
        case ATTR:
        	return sqlSessionTemplate.selectOne("attrMapper.getAttrListCount", map);
        default:
        	return 0;
        }
    }
    
    public List<LocationVO> getLocationList(LocationEnum locationEnum, String keyword) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("locationEnum", locationEnum);

        List<LocationVO> locationVOList = new ArrayList<>();
        // 각 LocationEnum에 맞는 조회 쿼리 실행
        switch (locationEnum) {
            case ACCO:
                locationVOList = sqlSessionTemplate.selectList("accoMapper.getAccoList", map);
                locationVOList.forEach(locationVO -> {
                    locationVO.setLocationEnum(LocationEnum.ACCO);
                });
                return locationVOList;
            case REST:
                locationVOList = sqlSessionTemplate.selectList("restMapper.getRestList", map);
                locationVOList.forEach(locationVO -> {
                    locationVO.setLocationEnum(LocationEnum.REST);
                });
                return locationVOList;
            case ATTR:
                locationVOList = sqlSessionTemplate.selectList("attrMapper.getAttrList", map);
                locationVOList.forEach(locationVO -> {
                    locationVO.setLocationEnum(LocationEnum.ATTR);
                });
                return locationVOList;
            case ALL : 
                List<LocationVO> attrList = sqlSessionTemplate.selectList("attrMapper.getAttrList", map);
                attrList.forEach(locationVO -> {
                    locationVO.setLocationEnum(LocationEnum.ATTR);
                });
                
                List<LocationVO> restList = sqlSessionTemplate.selectList("restMapper.getRestList", map);
                restList.forEach(locationVO -> {
                    locationVO.setLocationEnum(LocationEnum.REST);
                });
                
                List<LocationVO> accoList = sqlSessionTemplate.selectList("accoMapper.getAccoList", map);
                accoList.forEach(locationVO -> {
                    locationVO.setLocationEnum(LocationEnum.ACCO);
                });
                
                locationVOList = accoList;
                locationVOList.addAll(restList);
                locationVOList.addAll(attrList);
                return locationVOList;
        }

        return locationVOList;
    }

    // 숙소 추가
	public int  insertAcco(LocationVO acco) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("locId", acco.getLocId());
		map.put("placeName", acco.getPlaceName());
		map.put("addressName", acco.getAddressName());
		map.put("phone", acco.getPhone());
		map.put("x", acco.getX());
		map.put("y", acco.getY());
		map.put("category", acco.getCategory());
		map.put("placeUrl", acco.getPlaceUrl());
		map.put("imgPath", acco.getImgPath());
		return sqlSessionTemplate.insert("accoMapper.insertAcco", map);
	}
	
    // 맛집 추가
	public int insertRest(LocationVO rest) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("locId", rest.getLocId());
		map.put("placeName", rest.getPlaceName());
		map.put("addressName", rest.getAddressName());
		map.put("phone", rest.getPhone());
		map.put("x", rest.getX());
		map.put("y", rest.getY());
		map.put("category", rest.getCategory());
		map.put("restMenu", rest.getPlaceUrl());
		map.put("imgPath", rest.getImgPath());
		return sqlSessionTemplate.insert("restMapper.insertRest", map);
	}    
	
    // 즐길거리 추가
	public int insertAttr(LocationVO attr) {
		HashMap<String, Object> map = new HashMap<>();
        map.put("locId", attr.getLocId());
        map.put("placeName", attr.getPlaceName());
        map.put("addressName", attr.getAddressName());
        map.put("phone", attr.getPhone());
        map.put("x", attr.getX());
        map.put("y", attr.getY());
        map.put("category", attr.getCategory());
        map.put("imgPath", attr.getImgPath());
        return sqlSessionTemplate.insert("attrMapper.insertAttr", map);
	}
}
