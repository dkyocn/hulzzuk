package com.hulzzuk.notice.model.service;

import com.hulzzuk.notice.model.vo.NoticeVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface FaqService {

    ModelAndView getFaqById(long faqId, ModelAndView mv);

    ModelAndView getFaqList(String keyword, String  page, String limit, ModelAndView mv);

    Map<String, Object> createFaq(NoticeVO faqVO);

    ModelAndView updateFaq(NoticeVO faqVO, ModelAndView mv);

    ModelAndView deleteFaq(long faqId, ModelAndView mv);


}
