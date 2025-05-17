package com.hulzzuk.recomment.controller;

import com.hulzzuk.comment.model.service.CommentService;
import com.hulzzuk.comment.model.vo.CommentVO;
import com.hulzzuk.recomment.model.service.RecommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("recomm")
public class RecommentController {


    @Autowired
    private RecommentService recommentService;


    // 생성
    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createRecomment(HttpSession session,
                                               @RequestParam(name = "id") Long id,
                                               @RequestParam(name = "content") String content) {
        return recommentService.createRecomment(session,id,content);
    }

    // 삭제
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, Object> deleteRecomment(HttpSession session,
                                              @RequestParam(name = "id") Long id) {
        return recommentService.deleteComment(session, id);
    }
}
