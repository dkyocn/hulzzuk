package com.hulzzuk.voc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.voc.model.enumeration.VocEnum;
import com.hulzzuk.voc.model.service.VocService;
import com.hulzzuk.voc.model.vo.VocVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("voc")
public class VocController {
	
	@Autowired
	private VocService vocService;
	
	// 리스트 조회
	@RequestMapping(value="page.do")
	public ModelAndView getVocList(ModelAndView mv, 
										@RequestParam(name = "keyword", required = false) String keyword,
										@RequestParam(name = "vocEnum") VocEnum vocEnum,
										@RequestParam(name = "page", required = false) String  page,
                                        @RequestParam(name = "limit", required = false) String limit) {
		return vocService.getVocList(vocEnum, keyword, page, limit, mv);
	}
	
	// 상세페이지 => 리뷰 1개 조회
	@RequestMapping(value = "select.do")
	public ModelAndView getVocById(@RequestParam(name = "vocId") long vocId, ModelAndView mv, HttpSession session) {

	return vocService.getVocById(vocId, mv, session);
	}
	
	// 리뷰 생성
	@RequestMapping(value = "create.do")
	public ModelAndView createVoc(ModelAndView mv) {
		mv.setViewName("voc/vocCreate");
        
        return mv;
	}
	
	// 등록 처리
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	public ModelAndView createVocPost(ModelAndView mv, HttpServletRequest request, VocVO vocVO) {
	    return vocService.createVoc(mv, request, vocVO);
	}
	
	// 리뷰 수정
	@RequestMapping(value = "update.do")
	public ModelAndView updateVoc(ModelAndView mv, @RequestParam(name = "vocId") long vocId) {
		VocVO vocVO = vocService.getOneVoc(vocId); // 이건 서비스에서 조회
	    mv.addObject("vocVO", vocVO); // JSP에서 쓸 수 있도록 전달
		mv.setViewName("voc/vocUpdate");
        return mv;
	}
	
	// 수정 처리
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public ModelAndView updateVoc(ModelAndView mv, VocVO vocVO) {
		return vocService.updateVoc(mv, vocVO);
	}
	// 리뷰 삭제
	@RequestMapping(value = "delete.do")
	public ModelAndView deleteVoc(ModelAndView mv,
										@RequestParam(name = "vocId") long vocId) {
		return vocService.deleteVoc(vocId, mv);
	}
	
}
