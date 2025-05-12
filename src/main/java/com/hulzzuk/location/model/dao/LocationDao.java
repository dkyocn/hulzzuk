package com.hulzzuk.location.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

}
