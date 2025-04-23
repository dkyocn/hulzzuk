package com.hulzzuk.notice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hulzzuk.notice.model.service.NoticeService;
import com.hulzzuk.notice.model.vo.NoticeVO;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("notice")
public class NoticeController {

	// 로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class); // org.slf4j 임포트 하기

	@Autowired
	private NoticeService noticeService;
	
	@ResponseBody
	@RequestMapping(value = "select.do", method = RequestMethod.GET, produces = "application/json; charset:UTF-8")
	public ModelAndView getNoticeById(@RequestParam(name = "noticeId") long noticeId, ModelAndView mv) {

	return noticeService.getNoticeById(noticeId, mv);
	}
}
