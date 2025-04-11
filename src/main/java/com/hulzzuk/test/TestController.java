package com.hulzzuk.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "test.do", method = RequestMethod.GET)
    public String test(Model model) {
        try {
            String result = testService.connectTest();
            model.addAttribute("dbResult", result);
            model.addAttribute("message", "DB 연결 성공!");
        } catch (Exception e) {
            model.addAttribute("dbResult", null);
            model.addAttribute("message", "DB 연결 실패: " + e.getMessage());
        }
        return "test"; // test.jsp로 이동
    }
}
