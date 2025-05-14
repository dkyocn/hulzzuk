package com.hulzzuk.notice.model.service;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.notice.enummeration.Category;
import com.hulzzuk.plan.model.vo.PlanVO;
import com.hulzzuk.user.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.notice.model.dao.NoticeDao;
import com.hulzzuk.notice.model.vo.NoticeVO;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao noticeDao;

	/**
	 * 공지사항 단일 조회하는 메서드
	 * @param noticeId 조회하려는 공지사항 id
	 * @param mv 페이지를 만들어줄 ModelAndView
	 * @return 완성된 ModelAndView
	 */
	@Override
	public ModelAndView getNoticeById(long noticeId, ModelAndView mv,  boolean moveYN) {

		NoticeVO noticeVO = noticeDao.getNoticeById(noticeId);

		if(noticeVO == null) {
			throw new IllegalArgumentException(ErrorCode.NOTICE_NOT_FOUND.getMessage());
		}

		if(moveYN) {
			mv.addObject("notice", noticeVO);
			mv.setViewName("notice/noticeUpdate");
		} else {
			mv.addObject("notice", noticeVO);
			mv.setViewName("notice/noticeDetail");
		}

		return mv;
	}

	@Override
	public ModelAndView getNoticeList(String keyword, String page, String slimit, ModelAndView mv) {
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
			int listCount = noticeDao.getNoticeListCount(keyword, Category.NOTICE);
			// 페이지 관련 항목들 계산 처리
			Paging paging = new Paging(null, listCount, limit, currentPage, "page.do");
			paging.calculate();

			// 마이바티스 매퍼에서 사용되는 메소드는 Object 1개만 전달할 수 있음

			// 서비스 모델로 페이징 적용된 목록 조회 요청하고 결과받기
			List<NoticeVO> noticePage = noticeDao.getNoticeList(keyword, paging,Category.NOTICE);
			// ModelAndView : Model + View
			mv.addObject("keyword", keyword);
			mv.addObject("noticeList", noticePage);
			mv.addObject("paging", paging);

			mv.setViewName("notice/noticeList");


		return mv;
	}

	@Override
	public Map<String, Object> createNotice(NoticeVO noticeVO) {

		HashMap<String, Object> map = new HashMap<>();

		noticeDao.createNotice(noticeVO);

		if(noticeVO.getNoticeId() == 0) {
			throw new IllegalArgumentException(ErrorCode.NOTICE_INSERT_ERROR.getMessage());
		}

		map.put("successYN", true);

		return map;
	}

	@Override
	public ModelAndView updateNotice(NoticeVO noticeVO, ModelAndView mv) {
		noticeVO.setCategory(Category.NOTICE);
		if(noticeDao.updateNotice(noticeVO) == 0) {
			throw new IllegalArgumentException(ErrorCode.NOTICE_UPDATE_ERROR.getMessage());
		}

		mv.setViewName("redirect:/notice/select.do?noticeId="+noticeVO.getNoticeId());
		return mv;
	}

	@Override
	public ModelAndView deleteNotice(long id, ModelAndView mv) {

		if(noticeDao.deleteNotice(id) == 0) {
			throw new IllegalArgumentException(ErrorCode.NOTICE_DELETE_ERROR.getMessage());
		}


		mv.setViewName("redirect:/notice/page.do?page=1");
		return mv;
	}

}
