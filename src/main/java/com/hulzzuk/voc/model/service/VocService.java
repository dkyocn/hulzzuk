package com.hulzzuk.voc.model.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.common.enumeration.SortEnum;
import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.voc.model.enumeration.VocEnum;
import com.hulzzuk.voc.model.vo.VocVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("vocService")
public interface VocService {
	// 리스트 갯수 조회
	int getVocListCount(String keyword, VocEnum vocEnum);
	// 리스트 조회
	ModelAndView getVocList(VocEnum vocEnum, String keyword, String page, String limit, ModelAndView mv);
	// user 닉네임 조회
	 HashMap<String, String> getUserNicks(VocEnum vocEnum, String keyword);
	// 상세페이지 => 리뷰 1개 조회
	ModelAndView getVocById(long vocId, ModelAndView mv, HttpSession session);
	// 리뷰 생성
	ModelAndView createVoc(ModelAndView mv, HttpServletRequest request, VocVO vocVO);
	// 리뷰 수정
	ModelAndView updateVoc(ModelAndView mv, VocVO vocVO);
	// 리뷰 삭제
	ModelAndView deleteVoc(long vocId, ModelAndView mv);
	// 리뷰 1개 조회
	VocVO getOneVoc(long vocId);
}
