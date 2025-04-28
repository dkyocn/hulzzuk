package com.hulzzuk.log.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.service.LogService;
import com.hulzzuk.log.model.vo.LogVO;
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/page.do")
    public String getLogPage(@RequestParam(name="page", defaultValue="1") int page, Model model) {
        int amount = 15; // 한 페이지당 15개
        int start = (page - 1) * amount;

        int totalCount = logService.getLogCount(); // 총 데이터 수
        List<LogVO> logList = logService.getLogPage(start, amount);

        model.addAttribute("logs", logList);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("amount", amount);

        return "logs/log";  // /WEB-INF/views/logs/log.jsp
    }
    
    
    
}