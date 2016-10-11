package com.buckwa.web.controller.webboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.UploadItem;
import com.buckwa.domain.webboard.Topic;

@Controller
@RequestMapping("/webboard")
@SessionAttributes(types = UploadItem.class)
public class WebboardController {
  
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView initWebboard() {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initWebboard");
		try {
			mav.addObject("topic", new Topic());
			mav.addObject("pagingBean", bean);
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}


	
}
