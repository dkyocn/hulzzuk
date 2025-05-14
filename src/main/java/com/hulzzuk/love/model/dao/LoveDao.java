package com.hulzzuk.love.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.hulzzuk.love.model.vo.LoveVO;

@Repository("LoveDao")
public class LoveDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int insertAccoLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertAccoLove", love);
	}
	
	public int insertRestLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertRestLove", love);
	}
	
	public int insertAttrLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertAttrLove", love);
	}
	
	public int insertLogLove(LoveVO love) {
	    return sqlSessionTemplate.insert("loveMapper.insertLogLove", love);
	}

    public int deleteLove(LoveVO love) {
        return sqlSessionTemplate.delete("loveMapper.deleteLove", love);
    }
}
