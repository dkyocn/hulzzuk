package com.hulzzuk.notice.model.service;

import org.springframework.web.servlet.ModelAndView;

public interface NoticeService {

	ModelAndView getNoticeById(long id, ModelAndView mv);
}
