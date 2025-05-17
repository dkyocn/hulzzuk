package com.hulzzuk.recomment.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hulzzuk.comment.model.vo.CommentVO;
import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.user.model.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hulzzuk.recomment.model.dao.RecommentDao;
import com.hulzzuk.recomment.model.vo.RecommentVO;

@Service("recommentService")
public class RecommentServiceImpl implements RecommentService{
	
	@Autowired
	private RecommentDao recommentDao;
	
	// voc 댓글 조회
			@Override
			public List<RecommentVO> getVocRecomment(Long commentId) {
			    // DAO에서 댓글 리스트 가져오기
			    List<RecommentVO> recommentList = recommentDao.getVocRecomment(commentId);
			    return recommentList;
			}

	@Override
	public Map<String, Object> createRecomment(HttpSession session, Long id, String content) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		HashMap<String, Object> map = new HashMap<>();

		if(loginUser != null){
			RecommentVO recommentVO = new RecommentVO(content, loginUser.getUserId(), id);

			// 생성
			if (recommentDao.createRecomment(recommentVO) == 0 ) {
				throw new RuntimeException(ErrorCode.RECOMMENT_INSERT_ERROR.getMessage());
			}

			map.put("status","success");
			map.put("comment",recommentVO);
		}

		return map;
	}

	@Override
	public Map<String, Object> deleteComment(HttpSession session, Long id) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		HashMap<String, Object> map = new HashMap<>();

		if(loginUser != null){
			RecommentVO recommentVO = recommentDao.getRecommentById(id);
			if(recommentVO != null && recommentVO.getUserId().equals(loginUser.getUserId())){
				if(recommentDao.deleteRecomment(id) == 0) {
					throw new RuntimeException(ErrorCode.RECOMMENT_DELETE_ERROR.getMessage());
				}
				map.put("status","success");
			} else {
				map.put("status","error");
			}
		}
		return map;
	}

}
