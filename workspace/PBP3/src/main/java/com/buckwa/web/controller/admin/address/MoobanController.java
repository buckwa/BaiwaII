package com.buckwa.web.controller.admin.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.address.Mooban;
import com.buckwa.domain.admin.address.Tumbon;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.address.MoobanValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.address.MoobanService;
import com.buckwa.service.intf.admin.address.TumbonService;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/address/mooban")
@SessionAttributes(types = Mooban.class)
public class MoobanController {	
	private static Logger logger = LoggerFactory.getLogger(MoobanController.class);	
	@Autowired
	private MoobanService moobanService;	
	
	@Autowired
	private TumbonService tumbonService;	
	@Autowired
	private CommonService commonService;		
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		PagingBean bean = new PagingBean();
		mav.addObject("mooban", new Mooban());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("moobanList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("mooban", new Mooban());	
		request.put("pagingBean", bean);	
		bean.put("mooban", new Mooban());			 
		BuckWaResponse response = moobanService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			logger.info(" Success ");
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			mav.addObject("pagingBean", bean);				
		}else {	 
			mav.addObject("errorCode", response.getErrorCode()); 
		}				
		
		response = tumbonService.getAll();
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			List<Tumbon> tumbonList = (List<Tumbon>)response.getResObj("tumbonList");
			mav.addObject("tumbonList", tumbonList);	
			httpRequest.getSession().setAttribute("tumbonList",tumbonList);
		}
		return mav;
	}	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("moobanCreate");
		Mooban mooban = new Mooban();
		 
		mav.addObject("mooban", mooban);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createMooban(@ModelAttribute Mooban mooban, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{			
			new MoobanValidator().validate(mooban, result);			
			if (result.hasErrors()) {				
				mav.setViewName("MoobanCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("mooban", mooban);
				BuckWaResponse response = moobanService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("mooban", new Mooban());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("moobanCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("moobanCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("moobanList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("mooban", new Mooban());	
			request.put("pagingBean", bean);	
			bean.put("mooban", new Mooban());			 
			BuckWaResponse response = moobanService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Mooban mooban,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("moobanList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("mooban", mooban);			
			BuckWaResponse response = moobanService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				mav.addObject("doSearch","true");
			}else {			
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("moobanId") String moobanId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		BuckWaRequest request = new BuckWaRequest();
		request.put("moobanId", moobanId);	
		BuckWaResponse response = moobanService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Mooban mooban = (Mooban)response.getResObj("mooban");			
			 
			mav.addObject("mooban", mooban);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("moobanEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Mooban mooban, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{		
			new MoobanValidator().validate(mooban, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("moobanEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("mooban", mooban);
				BuckWaResponse response = moobanService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Mooban newMooban =  new Mooban();
					 
					mav.addObject("mooban",mooban);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("moobanEdit");						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("moobanEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("moobanId") String moobanId,HttpServletRequest httpRequest,@ModelAttribute Mooban mooban,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("moobanList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("moobanId", moobanId);	
			BuckWaResponse response = moobanService.deleteById(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("mooban", mooban);		
			response = moobanService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {			 
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return mav;
	}	
	
}
