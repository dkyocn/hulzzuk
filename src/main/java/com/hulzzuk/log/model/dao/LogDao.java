package com.hulzzuk.log.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hulzzuk.common.vo.Paging;
import com.hulzzuk.log.model.vo.LogVO;

@Repository
public class LogDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public List<LogVO> getLogList(Paging paging) {
        return sqlSession.selectList("logMapper.getLogList", paging);
    }

    public int getLogCount() {
        return sqlSession.selectOne("logMapper.getLogCount");
    }

    public LogVO getLogById(long id) {
        return sqlSession.selectOne("logMapper.getLogById", id);
    }

    public void createLog(LogVO logVo) {
        sqlSession.insert("logMapper.createLog", logVo);
    }

//    public void createLogPlace(LogPlaceVO logPlaceVo) {
//        sqlSession.insert("logMapper.createLogPlace", logPlaceVo);
//    }

    public void updateLog(LogVO logVo) {
        sqlSession.update("logMapper.updateLog", logVo);
    }

//    public void updateLogPlace(LogPlaceVO logPlaceVo) {
//        sqlSession.update("logMapper.updateLogPlace", logPlaceVo);
//    }

    public void deleteLog(long id) {
        sqlSession.delete("logMapper.deleteLog", id);
    }
    
    public List<LogVO> getLogPage(int start, int amount) {
        Map<String, Object> param = new HashMap<>();
        param.put("start", start);
        param.put("amount", amount);

        return sqlSession.selectList("logMapper.getLogList", param);
    }

    
}
