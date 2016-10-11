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
import com.buckwa.domain.admin.address.Province;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.address.AumphurValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.address.AumphurService;
import com.buckwa.service.intf.admin.address.ProvinceService;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/address/aumphur")
@SessionAttributes(types = Aumphur.class)
public class AumphurController {	
	private static Logger logger = LoggerFactory.getLogger(AumphurController.class);	
	@Autowired
	private AumphurService aumphurService;	
	
	@Autowired
	private ProvinceService provinceService;		
	
	@Autowired
	private CommonService commonService;		
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		PagingBean bean = new PagingBean();
		mav.addObject("aumphur", new Aumphur());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("aumphurList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("aumphur", new Aumphur());	
		request.put("pagingBean", bean);	
		bean.put("aumphur", new Aumphur());			 
		BuckWaResponse response = aumphurService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			logger.info(" Success ");
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			mav.addObject("pagingBean", bean);				
		}else {	 
			mav.addObject("errorCode", response.getErrorCode()); 
		}		
		
		response = provinceService.getAll();
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			List<Province> provinceList = (List<Province>)response.getResObj("provinceList");
			mav.addObject("provinceList", provinceList);	
			httpRequest.getSession().setAttribute("provinceList",provinceList);
		}
		
		return mav;
	}	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("aumphurCreate");
		Aumphur aumphur = new Aumphur();
		 
		mav.addObject("aumphur", aumphur);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createAumphur(@ModelAttribute Aumphur aumphur, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{			
			new AumphurValidator().validate(aumphur, result);			
			if (result.hasErrors()) {				
				mav.setViewName("aumphurCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("aumphur", aumphur);
				BuckWaResponse response = aumphurService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("aumphur", new Aumphur());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("aumphurCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("aumphurCreate");
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
		mav.setViewName("aumphurList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("aumphur", new Aumphur());	
			request.put("pagingBean", bean);	
			bean.put("aumphur", new Aumphur());			 
			BuckWaResponse response = aumphurService.getByOffset(request);
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Aumphur aumphur,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("aumphurList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("aumphur", aumphur);			
			BuckWaResponse response = aumphurService.getByOffset(request);
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
	public ModelAndView initEdit(@RequestParam("aumphurId") String aumphurId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		BuckWaRequest request = new BuckWaRequest();
		request.put("aumphurId", aumphurId);	
		BuckWaResponse response = aumphurService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Aumphur aumphur = (Aumphur)response.getResObj("aumphur");			
			 
			mav.addObject("aumphur", aumphur);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("aumphurEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Aumphur aumphur, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{		
			new AumphurValidator().validate(aumphur, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("aumphurEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("aumphur", aumphur);
				BuckWaResponse response = aumphurService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Aumphur newAumphur =  new Aumphur();
					 
					mav.addObject("aumphur",aumphur);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("aumphurEdit");						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("aumphurEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("aumphurId") String aumphurId,HttpServletRequest httpRequest,@ModelAttribute Aumphur aumphur,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("aumphurList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("aumphurId", aumphurId);	
			BuckWaResponse response = aumphurService.deleteById(request);
			
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
			bean.put("aumphur", aumphur);		
			response = aumphurService.getByOffset(request);
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
