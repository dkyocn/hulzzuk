package com.hulzzuk.location.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.location.model.vo.LocationVO;

@Repository("locationDao")
public class LocationDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public LocationVO getAccoById(String locId) {
		return sqlSessionTemplate.selectOne("accoMapper.getLocationById", locId);
	}
	
	public LocationVO getRestById(String locId) {
		return sqlSessionTemplate.selectOne("restMapper.getLocationById", locId);
	}
	
	public LocationVO getAttrById(String locId) {
		return sqlSessionTemplate.selectOne("attrSMapper.getLocationById", locId);
	}
	

}
