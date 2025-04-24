package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.log.model.vo.LogVO;

public interface LogService {
	List<LogVO> getLogList(int start, int amount);

}
