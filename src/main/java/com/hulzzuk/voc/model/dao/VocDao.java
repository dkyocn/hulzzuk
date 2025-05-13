package com.hulzzuk.voc.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.voc.model.enumeration.VocEnum;
import com.hulzzuk.voc.model.vo.VocVO;

@Repository("vocDao")
public class VocDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 리스트 갯수 조회
	public int getVocListCount(String keyword, VocEnum vocEnum) {
		HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("vocEnum", vocEnum != null ? vocEnum.name() : null);
        return sqlSessionTemplate.selectOne("vocMapper.getVocListCount", map);	
	}
	
	// 리스트 조회
	public List<VocVO> getVocList(VocEnum vocEnum, String keyword, Paging paging) {
		HashMap<String, Object> map = new HashMap<>();
        map.put("vocEnum", vocEnum != null ? vocEnum.name() : null);
        map.put("keyword", keyword);
        map.put("paging", paging);

        return sqlSessionTemplate.selectList("vocMapper.getVocList", map);
        // 각 LocationEnum에 맞는 조회 쿼리 실행
	}

	// 상세페이지 => 리뷰 1개 조회
		public VocVO getVocById(long vocId) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("vocId", vocId);
			return sqlSessionTemplate.selectOne("vocMapper.getVocById", map);
		}
		
		// 리뷰 생성
		public int createVoc(VocVO vocVO) {
			return sqlSessionTemplate.insert("vocMapper.createVoc", vocVO);
		}
		
		// 리뷰 수정
		public int updateVoc(VocVO vocVO) {
			return sqlSessionTemplate.update("vocMapper.updateVoc", vocVO);
		}
		
		// 리뷰 삭제
		public int deleteVoc(long vocId) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("vocId", vocId);

			return sqlSessionTemplate.delete("vocMapper.deleteVoc", map);
		}
}
