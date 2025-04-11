package com.hulzzuk.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestDao dao;
	
	public String connectTest() {
        return dao.connectTest();
    }

}
