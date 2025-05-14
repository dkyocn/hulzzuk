package com.hulzzuk.love.model.service;

import java.util.Map;


import com.hulzzuk.location.model.enumeration.LocationEnum;

import jakarta.servlet.http.HttpSession;

public interface LoveService {
	Map<String, Object> insertLove(HttpSession session, LocationEnum locationEnum, String locId);
	
	Map<String, Object> deleteLove(long loveId);
}
