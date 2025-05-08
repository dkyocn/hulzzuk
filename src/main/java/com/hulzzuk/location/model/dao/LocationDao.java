package com.hulzzuk.location.model.dao;

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
	
<<<<<<< HEAD
	// 검색 페이지 리스트 조회
=======
>>>>>>> 1efd3d1c217c70ee61e1e9e19cbbee3fb98f8cc9
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
<<<<<<< HEAD
=======

    public List<LocationVO> getLocationList(LocationEnum locationEnum, String keyword) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("locationEnum", locationEnum);

        // 각 LocationEnum에 맞는 조회 쿼리 실행
        return switch (locationEnum) {
            case ACCO -> sqlSessionTemplate.selectList("accoMapper.getAccoList", map);
            case REST -> sqlSessionTemplate.selectList("restMapper.getRestList", map);
            case ATTR -> sqlSessionTemplate.selectList("attrMapper.getAttrList", map);
            default -> sqlSessionTemplate.selectList("attrMapper.getAttrList", map);
        };
    }
>>>>>>> 1efd3d1c217c70ee61e1e9e19cbbee3fb98f8cc9
}
