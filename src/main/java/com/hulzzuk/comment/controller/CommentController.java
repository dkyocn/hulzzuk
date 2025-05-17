package com.hulzzuk.comment.controller;

import com.hulzzuk.comment.model.vo.CommentVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hulzzuk.comment.model.service.CommentService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("comment")
public class CommentController {
	@Autowired
	private CommentService commentService;


	// 생성
	@RequestMapping(value = "create.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createComment(HttpSession session,
											 @RequestParam(name = "type") String type, // VOC, LOG
											 @RequestParam(name = "id") Long id,
											 @RequestParam(name = "content") String content) {

			return commentService.createComment(session, type, id, content);
		}


	
	
	// 삭제
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteComment(HttpSession session,
											  @RequestParam(name = "id") Long id) {
		return commentService.deleteComment(session, id);
	}
}
