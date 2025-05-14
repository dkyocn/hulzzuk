package com.hulzzuk.notice.model.service;

import com.hulzzuk.notice.model.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface NoticeService {

	// 공지사항 단일 조회
	ModelAndView getNoticeById(long id, ModelAndView mv, boolean moveYN);

	// 공지사항 페이지 조회
	ModelAndView getNoticeList(String keyword, String page, String limit, ModelAndView modelAndView);

	// 공지사항 생성
	Map<String, Object> createNotice(NoticeVO noticeVO);

	// 공지사항 수정
	ModelAndView updateNotice(NoticeVO noticeVO, ModelAndView mv);

	// 공지사항 삭제
	ModelAndView deleteNotice(long id, ModelAndView mv);
}
