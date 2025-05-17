package com.hulzzuk.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hulzzuk.comment.model.service.CommentService;

@Controller
@RequestMapping("comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
}
