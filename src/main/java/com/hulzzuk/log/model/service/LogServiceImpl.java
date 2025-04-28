package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.vo.LogVO;


@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao LogDao;

    @Override
    public List<LogVO> getLogList(Paging paging) {
        return LogDao.getLogList(paging);
    }

    @Override
    public int getLogCount() {
        return LogDao.getLogCount();
    }

    @Override
    public LogVO getLogById(long id) {
        return LogDao.getLogById(id);
    }

    @Override
    public void createLog(LogVO logVo) {
    	LogDao.createLog(logVo);
    }

//    @Override
//    public void createLogPlace(LogPlaceVO logPlaceVo) {
//        LogDao.createLogPlace(logPlaceVo);
//    }

    @Override
    public void updateLog(LogVO logVo) {
    	LogDao.updateLog(logVo);
    }

//    @Override
//    public void updateLogPlace(LogPlaceVO logPlaceVo) {
//        LogDao.updateLogPlace(logPlaceVo);
//    }

    @Override
    public void deleteLog(long id) {
    	LogDao.deleteLog(id);
    }
    @Override
    public List<LogVO> getLogPage(int start, int amount) {
        return LogDao.getLogPage(start, amount);
    }
}
