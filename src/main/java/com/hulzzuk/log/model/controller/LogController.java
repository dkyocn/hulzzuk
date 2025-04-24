package com.hulzzuk.log.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hulzzuk.log.model.service.LogService;
import com.hulzzuk.log.model.vo.LogVO;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	// 로그리스트 조회
	@RequestMapping("/list")
	@ResponseBody
	public List<LogVO> getLogList(int start, int amount) {
		return logService.getLogList(start, amount);
	}

}
