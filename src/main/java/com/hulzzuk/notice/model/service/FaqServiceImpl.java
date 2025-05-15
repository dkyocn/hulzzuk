package com.hulzzuk.notice.model.service;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.notice.enummeration.Category;
import com.hulzzuk.notice.model.dao.FaqDao;
import com.hulzzuk.notice.model.dao.NoticeDao;
import com.hulzzuk.notice.model.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("faqService")
public class FaqServiceImpl implements FaqService {

    @Autowired
    private NoticeDao faqDao;

    @Override
    public ModelAndView getFaqById(long faqId, ModelAndView mv) {
        NoticeVO faqVO = faqDao.getNoticeById(faqId);

        if(faqVO == null) {
            throw new IllegalArgumentException(ErrorCode.FAQ_NOT_FOUND.getMessage());
        }

        mv.addObject("faq", faqVO);
        mv.setViewName("faq/faqUpdate");

        return mv;
    }

    @Override
    public ModelAndView getFaqList(String keyword, String page, String slimit, ModelAndView mv) {
        // 페이징 처리
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }

        // 한 페이지에 출력할 목록 갯수 기본 10개로 지정함
        int limit = 10;
        if (slimit != null) {
            limit = Integer.parseInt(slimit);
        }
        // 검색결과가 적용된 총 목록 갯수 조회해서, 총 페이지 수 계산함
        int listCount = faqDao.getNoticeListCount(keyword, Category.FAQ);
        // 페이지 관련 항목들 계산 처리
        Paging paging = new Paging(null, listCount, limit, currentPage, "page.do");
        paging.calculate();

        // 마이바티스 매퍼에서 사용되는 메소드는 Object 1개만 전달할 수 있음

        // 서비스 모델로 페이징 적용된 목록 조회 요청하고 결과받기
        List<NoticeVO> faqPage = faqDao.getNoticeList(keyword, paging,Category.FAQ);
        // ModelAndView : Model + View
        mv.addObject("keyword", keyword);
        mv.addObject("faqList", faqPage);
        mv.addObject("paging", paging);

        mv.setViewName("faq/faqList");


        return mv;
    }

    @Override
    public Map<String, Object> createFaq(NoticeVO faqVO) {
        HashMap<String, Object> map = new HashMap<>();

        faqDao.createNotice(faqVO);

        if(faqVO.getNoticeId() == 0) {
            throw new IllegalArgumentException(ErrorCode.FAQ_INSERT_ERROR.getMessage());
        }

        map.put("successYN", true);

        return map;
    }

    @Override
    public ModelAndView updateFaq(NoticeVO faqVO, ModelAndView mv) {
        faqVO.setCategory(Category.FAQ);
        if(faqDao.updateNotice(faqVO) == 0) {
            throw new IllegalArgumentException(ErrorCode.FAQ_UPDATE_ERROR.getMessage());
        }

        mv.setViewName("redirect:/faq/page.do?page=1");
        return mv;
    }

    @Override
    public ModelAndView deleteFaq(long faqId, ModelAndView mv) {
        if(faqDao.deleteNotice(faqId) == 0) {
            throw new IllegalArgumentException(ErrorCode.FAQ_DELETE_ERROR.getMessage());
        }


        mv.setViewName("redirect:/faq/page.do?page=1");
        return mv;
    }
}
