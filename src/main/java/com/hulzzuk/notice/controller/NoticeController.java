package com.hulzzuk.notice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hulzzuk.notice.model.service.NoticeService;
import com.hulzzuk.notice.model.vo.NoticeVO;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.util.Map;

@Controller
@RequestMapping("notice")
public class NoticeController {

	// 로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class); // org.slf4j 임포트 하기

	@Autowired
	private NoticeService noticeService;
	
	@ResponseBody
	@RequestMapping(value = "select.do")
	public ModelAndView getNoticeById(@RequestParam(name = "noticeId") long noticeId, ModelAndView mv) {

	return noticeService.getNoticeById(noticeId, mv, false);
	}

	@ResponseBody
	@RequestMapping(value = "page.do")
	public ModelAndView getNoticeList(ModelAndView mv,
									  @RequestParam(name = "keyword", required = false) String keyword,
									  @RequestParam(name = "page", required = false) String  page,
									  @RequestParam(name = "limit", required = false) String limit) {
		return noticeService.getNoticeList(keyword, page, limit, mv);
	}

	@RequestMapping(value = "moveCreate.do")
	public ModelAndView moveCreate(ModelAndView mv) {
		mv.setViewName("notice/noticeCreate");
		return mv;
	}

	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createNotice(@RequestBody NoticeVO noticeVO) {

		return noticeService.createNotice(noticeVO);
	}

	@RequestMapping(value = "moveUpdate.do")
	public ModelAndView moveUpdate(ModelAndView mv,
								   @RequestParam(name = "noticeId") long noticeId) {

		return noticeService.getNoticeById(noticeId, mv, true);
	}

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public ModelAndView updateNotice(ModelAndView mv, NoticeVO noticeVO) {
		return noticeService.updateNotice(noticeVO, mv);
	}

	@RequestMapping(value = "delete.do")
	public ModelAndView deleteNotice(ModelAndView mv,@RequestParam(name = "noticeId") long noticeId) {
		return noticeService.deleteNotice(noticeId, mv);
	}
}
