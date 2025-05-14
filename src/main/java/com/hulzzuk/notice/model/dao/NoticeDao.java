package com.hulzzuk.notice.model.dao;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.notice.enummeration.Category;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.notice.model.vo.NoticeVO;

import java.util.HashMap;
import java.util.List;

@Repository("noticeDao")
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public NoticeVO getNoticeById(long id) {
		return sqlSessionTemplate.selectOne("noticeMapper.getNoticeById",id);
	}

	public List<NoticeVO> getNoticeList(String keyword, Paging paging, Category category) {
		HashMap<String, Object> map = new HashMap<>();

		map.put("category", category);
		map.put("keyword", keyword);
		map.put("paging", paging);
		return sqlSessionTemplate.selectList("noticeMapper.getNoticeList",map);
	}

	public int getNoticeListCount(String keyword, Category category) {
		HashMap<String, Object> map = new HashMap<>();

		map.put("category", category);
		map.put("keyword", keyword);

		return sqlSessionTemplate.selectOne("noticeMapper.getNoticeListCount",map);
	}

	public void createNotice(NoticeVO noticeVO) {
		sqlSessionTemplate.insert("noticeMapper.createNotice",noticeVO);
	}

	public int updateNotice(NoticeVO noticeVO) {
		return sqlSessionTemplate.update("noticeMapper.updateNotice",noticeVO);
	}

	public int deleteNotice(long id) {
		return sqlSessionTemplate.delete("noticeMapper.deleteNotice",id);
	}
}
