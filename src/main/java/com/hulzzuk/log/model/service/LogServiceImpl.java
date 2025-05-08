package com.hulzzuk.log.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.dao.LogDao;
import com.hulzzuk.log.model.vo.LogVO;
import com.hulzzuk.plan.model.dao.PlanDao;
import com.hulzzuk.plan.model.vo.PlanVO;


@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public List<LogVO> getLogList(Paging paging) {
        return logDao.getLogList(paging);
    }

    @Override
    public int getLogCount() {
        return logDao.getLogCount();
    }

    @Override
    public LogVO getLogById(long id) {
        return logDao.getLogById(id);
    }

    @Override
    public List<PlanVO> selectPlanIdList(String userId) {
        return logDao.selectPlanIdList(userId);
    }
    @Override
    public void createLog(LogVO logVo) {
    	logDao.createLog(logVo);
    }

  
//    @Override
//    public void createLogPlace(LogPlaceVO logPlaceVo) {
//        LogDao.createLogPlace(logPlaceVo);
//    }

    @Override
    public void updateLog(LogVO logVo) {
    	logDao.updateLog(logVo);
    }

//    @Override
//    public void updateLogPlace(LogPlaceVO logPlaceVo) {
//        LogDao.updateLogPlace(logPlaceVo);
//    }

    @Override
    public void deleteLog(long id) {
    	logDao.deleteLog(id);
    }
    @Override
    public List<LogVO> getLogPage(int start, int amount) {
        return logDao.getLogPage(start, amount);
    }

	@Override
	public PlanVO getPlanById(Long planId) {
		return logDao.getPlanById(planId);
	}
}
