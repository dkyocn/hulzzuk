package com.hulzzuk.comment.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hulzzuk.common.enumeration.ErrorCode;
import com.hulzzuk.user.model.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.comment.model.dao.CommentDao;
import com.hulzzuk.comment.model.vo.CommentVO;

@Service("commentService")
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;

	// voc 댓글 조회
	@Override
	public List<CommentVO> getVocComment(Long vocId) {
	    // DAO에서 댓글 리스트 가져오기
	    List<CommentVO> commentList = commentDao.getVocComment(vocId);
	    return commentList;
	}
	
	// voc 댓글 생성
	
	// voc 댓글 삭제

	@Override
	public Map<String, Object> createComment(HttpSession session, String type, long id, String content) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		HashMap<String, Object> map = new HashMap<>();

		if(loginUser != null){
			CommentVO commentVO = new CommentVO();
			switch (type){
				case "VOC":
					commentVO = new CommentVO(content,null,id, loginUser.getUserId());
					break;
				case "LOG":
					commentVO = new CommentVO(content,id,null, loginUser.getUserId());
					break;
			}

			// 생성
			if (commentDao.createComment(commentVO) == 0 ) {
				throw new RuntimeException(ErrorCode.COMMENT_INSERT_ERROR.getMessage());
			}

			map.put("status","success");
			map.put("comment",commentVO);
		}

		return map;
	}

	@Override
	public Map<String, Object> deleteComment(HttpSession session, long id) {
		UserVO loginUser = (UserVO) session.getAttribute("loginUser");

		HashMap<String, Object> map = new HashMap<>();

		if(loginUser != null){
			CommentVO commentVO = commentDao.getCommentById(id);
			if(commentVO != null && commentVO.getUserId().equals(loginUser.getUserId())){
				if(commentDao.deleteComment(id) == 0) {
					throw new RuntimeException(ErrorCode.COMMENT_DELETE_ERROR.getMessage());
				}
				map.put("status","success");
			} else {
				map.put("status","error");
			}
		}
		return map;
	}

	@Override
	public List<CommentVO> getLogComment(Long logId) {
		return commentDao.getLogComment(logId); 
	
	}

}
