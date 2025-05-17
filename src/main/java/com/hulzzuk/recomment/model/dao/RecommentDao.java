package com.hulzzuk.recomment.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.recomment.model.vo.RecommentVO;


@Repository("recommentDao")
public class RecommentDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<RecommentVO> getVocRecomment(Long commentId){
		return sqlSessionTemplate.selectList("recommentMapper.getVocRecomment", commentId);
	}
}
