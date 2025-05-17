package com.hulzzuk.voc.model.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.comment.model.service.CommentService;
import com.hulzzuk.comment.model.vo.CommentVO;
import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.recomment.model.service.RecommentService;
import com.hulzzuk.recomment.model.vo.RecommentVO;
import com.hulzzuk.user.model.dao.UserDao;
import com.hulzzuk.user.model.vo.UserVO;
import com.hulzzuk.voc.model.dao.VocDao;
import com.hulzzuk.voc.model.enumeration.VocEnum;
import com.hulzzuk.voc.model.vo.VocVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("vocService")
public class VocServiceImpl implements VocService{
	
	private static final Logger logger = LoggerFactory.getLogger(VocServiceImpl.class);

	@Autowired
	private VocDao vocDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CommentService commentService;
	@Autowired
	private RecommentService recommentService;
	
	// 리스트 갯수 조회
	@Override
	public int getVocListCount(String keyword, VocEnum vocEnum) {
		return vocDao.getVocListCount(keyword, vocEnum);
    }
	
	// 리뷰 1개 조회
	@Override
	public VocVO getOneVoc(long vocId) {
		return vocDao.getVocById(vocId);
	}
	
	// user 닉네임 조회
	@Override
	public HashMap<String, String> getUserNicks(VocEnum vocEnum, String keyword) {
		 List<VocVO> vocList = vocDao.getVocList(vocEnum, keyword, null);
        
	       HashMap<String, String> userNicks  = new HashMap<>();
	        for(VocVO vocVO : vocList  ) {
	        	UserVO user = userDao.selectUser(vocVO.getUserId());
	        	userNicks.put(vocVO.getUserId(), user.getUserNick());
	        }
	        return userNicks;
		}
	
	// 리스트 조회
	@Override
	public ModelAndView getVocList(VocEnum vocEnum, String keyword, String page, String limit, ModelAndView mv) {
		// 페이징 처리
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }
        
        int pageLimit = 10; // 기본 10개
        if (limit != null) {
            pageLimit = Integer.parseInt(limit);
        }
    	// 총 목록 갯수 조회해서. 총 페이지 수 계산함
        int listCount = getVocListCount(keyword, vocEnum);
        // 페이지 관련 항목들 계산 처리
        Paging paging = new Paging(keyword, listCount, pageLimit, currentPage, "page.do");
        paging.calculate();

        List<VocVO> vocList = vocDao.getVocList(vocEnum, keyword, paging);
        mv.addObject("vocList", vocList);
        mv.addObject("paging", paging);
        mv.addObject("userNicks", getUserNicks(vocEnum, keyword));
		  mv.addObject("vocEnum", vocEnum);
		  mv.addObject("keyword",keyword);
		  mv.setViewName("voc/vocList");
        
        return mv;
	}
	
	// 상세페이지 => 리뷰 1개 조회
	@Override
	public ModelAndView getVocById(long vocId, ModelAndView mv, HttpSession session) {
		VocVO vocVO = vocDao.getVocById(vocId); 
		String loginUserId = (String) session.getAttribute("authUserId");
		
		UserVO userVo = userDao.selectUser(vocVO.getUserId());		
		// 2. 댓글 목록 + 개수 조회 (댓글 서비스 호출)
		List<CommentVO> commentList = commentService.getVocComment(vocId);
		
		HashMap<String, String> userNicks = new HashMap<>();
		HashMap<String, String> recouserNicks = new HashMap<>();
		
		HashMap<Long, List<RecommentVO>> recommentMap = new HashMap<>();
		
		for(CommentVO commentVO : commentList) {
			userNicks.put(commentVO.getUserId(), userDao.selectUser(commentVO.getUserId()).getUserNick());
			
			List<RecommentVO> tempRecomments = recommentService.getVocRecomment(commentVO.getCommentId());
			// commentid 댓글에 대한 대댓글 조회 -> 대댓글 useriD를 가지고 닉네임 조회
			if(tempRecomments != null) {
				recommentMap.put(commentVO.getCommentId(), tempRecomments);
				for(RecommentVO reco : tempRecomments) {
	                    UserVO recoUser = userDao.selectUser(reco.getUserId());
	                    recouserNicks.put(reco.getUserId(), recoUser.getUserNick());
	                }
					/*
					 * recommentList = recommentService.getVocRecomment(commentVO.getCommentId());
					 * recouserNicks.put(, userDao.selectUser(commentVO.getUserId()).getUserNick());
					 */
				}
		}
		
		
		
		mv.addObject("loginUserId", loginUserId);
		mv.addObject("vocVO", vocVO);
		mv.addObject("commentList", commentList);
		mv.addObject("recommentMap", recommentMap);
		mv.addObject("userNicks",userNicks);
		mv.addObject("recouserNicks",recouserNicks);
		
		mv.setViewName("voc/vocDetailView");
		
		return mv; 
	}
	
	// 리뷰 생성
	@Override
	public ModelAndView createVoc(ModelAndView mv, HttpServletRequest request, VocVO vocVO) {
		int successYN = 0;
		vocVO.setUserId(((UserVO)request.getSession().getAttribute("loginUser")).getUserId());
		vocVO.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
		vocVO.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
		successYN =  vocDao.createVoc(vocVO);
		if(successYN == 0) {
		    throw new  IllegalArgumentException("저장 안되었음요");
		    }else {
		    	mv.setViewName("redirect:/voc/page.do?vocEnum=ALL&page=1");
		    }
		    return mv;
	}

	// 리뷰 수정
	@Override
	public ModelAndView updateVoc(ModelAndView mv, VocVO vocVO) {
		int successYN = 0;
		vocVO.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
		successYN = vocDao.updateVoc(vocVO);
		if (successYN == 0) {
			throw new IllegalArgumentException("저장 안되었음요");
		} else {
			mv.setViewName("redirect:/voc/select.do?vocId=" + vocVO.getVocId()); // 목록 페이지 경로로 리다이렉트
		}
		return mv;
	}
	
	// 리뷰 삭제
	@Override
	public ModelAndView deleteVoc(long vocId, ModelAndView mv) {
		int result = vocDao.deleteVoc(vocId);
		if(result <=  0) {
			throw new IllegalArgumentException(ErrorCode.REVIEW_NOT_FOUND.getMessage());
		}
		mv.setViewName("redirect:/voc/page.do?vocEnum=ALL&page=1");  // 목록 페이지 경로로 리다이렉트
	    return mv;
	}

}
