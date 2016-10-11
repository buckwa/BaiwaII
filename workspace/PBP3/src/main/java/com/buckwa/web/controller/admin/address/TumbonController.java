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

import com.buckwa.domain.admin.address.Aumphur;
import com.buckwa.domain.admin.address.Tumbon;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.address.TumbonValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.address.AumphurService;
import com.buckwa.service.intf.admin.address.TumbonService;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/address/tumbon")
@SessionAttributes(types = Tumbon.class)
public class TumbonController {	
	private static Logger logger = LoggerFactory.getLogger(TumbonController.class);	
	@Autowired
	private TumbonService tumbonService;	
	
	@Autowired
	private AumphurService aumphurService;	
	@Autowired
	private CommonService commonService;		
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		PagingBean bean = new PagingBean();
		mav.addObject("tumbon", new Tumbon());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("tumbonList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("tumbon", new Tumbon());	
		request.put("pagingBean", bean);	
		bean.put("tumbon", new Tumbon());			 
		BuckWaResponse response = tumbonService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			logger.info(" Success ");
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			mav.addObject("pagingBean", bean);				
		}else {	 
			mav.addObject("errorCode", response.getErrorCode()); 
		}			
		response = aumphurService.getAll();
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			List<Aumphur> aumphurList = (List<Aumphur>)response.getResObj("aumphurList");
			mav.addObject("aumphurList", aumphurList);	
			httpRequest.getSession().setAttribute("aumphurList",aumphurList);
		}
		
		return mav;
	}	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tumbonCreate");
		Tumbon tumbon = new Tumbon();
		 
		mav.addObject("tumbon", tumbon);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createTumbon(@ModelAttribute Tumbon tumbon, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{			
			new TumbonValidator().validate(tumbon, result);			
			if (result.hasErrors()) {				
				mav.setViewName("tumbonCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("tumbon", tumbon);
				BuckWaResponse response = tumbonService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("tumbon", new Tumbon());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("tumbonCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("tumbonCreate");
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
		mav.setViewName("tumbonList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("tumbon", new Tumbon());	
			request.put("pagingBean", bean);	
			bean.put("tumbon", new Tumbon());			 
			BuckWaResponse response = tumbonService.getByOffset(request);
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Tumbon tumbon,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("tumbonList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("tumbon", tumbon);			
			BuckWaResponse response = tumbonService.getByOffset(request);
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
	public ModelAndView initEdit(@RequestParam("tumbonId") String tumbonId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		BuckWaRequest request = new BuckWaRequest();
		request.put("tumbonId", tumbonId);	
		BuckWaResponse response = tumbonService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Tumbon tumbon = (Tumbon)response.getResObj("tumbon");			
			 
			mav.addObject("tumbon", tumbon);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("tumbonEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Tumbon tumbon, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{		
			new TumbonValidator().validate(tumbon, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("tumbonEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("tumbon", tumbon);
				BuckWaResponse response = tumbonService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Tumbon newTumbon =  new Tumbon();
					 
					mav.addObject("tumbon",tumbon);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("tumbonEdit");						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("tumbonEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("tumbonId") String tumbonId,HttpServletRequest httpRequest,@ModelAttribute Tumbon tumbon,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("tumbonList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("tumbonId", tumbonId);	
			BuckWaResponse response = tumbonService.deleteById(request);
			
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
			bean.put("tumbon", tumbon);		
			response = tumbonService.getByOffset(request);
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
