package com.hulzzuk.log.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.log.model.vo.PageCriteria;

@Repository("LogDao")
public class LogDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<LogVO> getLogList(PageCriteria criteria){
		return sqlSessionTemplate.selectList("logsMapper.getLogList", criteria);
	}
}
