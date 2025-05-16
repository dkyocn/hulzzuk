package com.hulzzuk;

import com.hulzzuk.location.model.service.LocationService;
import com.hulzzuk.location.model.vo.LocationVO;
import com.hulzzuk.log.model.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private LogService logService;

	@Autowired
	private LocationService locationService;

	@RequestMapping("main.do")
	public ModelAndView forwardMainView(ModelAndView modelAndView) {

		ModelAndView top3LocList = locationService.getTop3LocList(modelAndView);

		modelAndView.addObject(top3LocList);
		modelAndView.setViewName("common/main");

		return modelAndView;

	}

}
