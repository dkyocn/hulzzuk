package com.hulzzuk.log.model.service;

import java.util.List;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogVO;

public interface LogService {

    List<LogVO> getLogList(Paging paging);
    

    int getLogCount();

    LogVO getLogById(long id);

    void createLog(LogVO logVo);

    //void createLogPlace(LogPlaceVO logPlaceVo);

    void updateLog(LogVO logVo);

  //  void updateLogPlace(LogPlaceVO logPlaceVo);

    void deleteLog(long id);
    
    List<LogVO> getLogPage(int start, int amount);
}