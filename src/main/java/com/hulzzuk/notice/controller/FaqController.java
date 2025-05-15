package com.hulzzuk.notice.controller;

import com.hulzzuk.notice.model.service.FaqService;
import com.hulzzuk.notice.model.vo.NoticeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("faq")
public class FaqController {

    // 로그 객체 생성
    private static final Logger logger = LoggerFactory.getLogger(FaqController.class); // org.slf4j 임포트 하기

    @Autowired
    private FaqService faqService;

    @ResponseBody
    @RequestMapping(value = "page.do")
    public ModelAndView getFaqList(ModelAndView mv,
                                      @RequestParam(name = "keyword", required = false) String keyword,
                                      @RequestParam(name = "page", required = false) String  page,
                                      @RequestParam(name = "limit", required = false) String limit) {
        return faqService.getFaqList(keyword, page, limit, mv);
    }

    @RequestMapping(value = "moveCreate.do")
    public ModelAndView moveCreate(ModelAndView mv) {
        mv.setViewName("faq/faqCreate");
        return mv;
    }

    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createFaq(@RequestBody NoticeVO faqVO) {

        return faqService.createFaq(faqVO);
    }

    @RequestMapping(value = "moveUpdate.do")
    public ModelAndView moveUpdate(ModelAndView mv,
                                   @RequestParam(name = "faqId") long faqId) {

        return faqService.getFaqById(faqId, mv);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public ModelAndView updateFaq(ModelAndView mv, NoticeVO faqVO) {
        return faqService.updateFaq(faqVO, mv);
    }

    @RequestMapping(value = "delete.do")
    public ModelAndView deleteFaq(ModelAndView mv,@RequestParam(name = "faqId") long faqId) {
        return faqService.deleteFaq(faqId, mv);
    }
}
