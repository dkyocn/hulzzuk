package com.hulzzuk.notice.model.service;

import com.hulzzuk.common.enumeration.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.notice.model.dao.NoticeDao;
import com.hulzzuk.notice.model.vo.NoticeVO;
import org.springframework.web.servlet.ModelAndView;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao noticeDao;

	/**
	 * 공지사항 단일 조회하는 메서드
	 * @param id 조회하려는 공지사항 id
	 * @param mv 페이지를 만들어줄 ModelAndView
	 * @return 완성된 ModelAndView
	 */
	@Override
	public ModelAndView getNoticeById(long id, ModelAndView mv) {

		NoticeVO noticeVO = noticeDao.getNoticeById(id);

		if(noticeVO == null) {
			throw new IllegalArgumentException(ErrorCode.NOTICE_NOT_FOUND.getMessage());
		}

		mv.addObject("notice", noticeVO);
		mv.setViewName("notice/noticeDetail");
		return mv;
	}

}
