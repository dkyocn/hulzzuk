package com.hulzzuk.notice.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.notice.model.vo.NoticeVO;

@Repository("noticeDao")
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public NoticeVO getNoticeById(long id) {
		return sqlSessionTemplate.selectOne("noticeMapper.getNoticeById",id);
	}

}
