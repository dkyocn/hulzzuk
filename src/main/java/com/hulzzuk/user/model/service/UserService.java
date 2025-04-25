package com.hulzzuk.user.model.service;

import org.springframework.web.servlet.ModelAndView;

public interface UserService {
	ModelAndView selectUser(String userId);
}
