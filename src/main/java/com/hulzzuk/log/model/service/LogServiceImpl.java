package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.log.model.vo.PageCriteria;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;  // LogDao 객체 주입
	
	@Override
	public List<LogVO> getLogList(int start, int amount) {
	    PageCriteria criteria = new PageCriteria(start, amount);
	    return logDao.getLogList(criteria);
	}
}
