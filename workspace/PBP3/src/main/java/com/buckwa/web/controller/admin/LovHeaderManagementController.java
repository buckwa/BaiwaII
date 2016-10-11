package com.buckwa.web.controller.admin;

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

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.LovHeader;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.LovDetailValidator;
import com.buckwa.domain.validator.admin.LovHeaderValidator;
import com.buckwa.service.intf.admin.LovHeaderService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/lovHeader")
@SessionAttributes(types = LovHeader.class)
public class LovHeaderManagementController {
	private static Logger logger = LoggerFactory.getLogger(LovHeaderManagementController.class);
	@Autowired
	private LovHeaderService lovHeaderService;
	
	@ModelAttribute("lovHeader") 
	public LovHeader lovHeader() { 
		logger.info(" Call @ModelAttribute LovHeader ");
		return new LovHeader(); 
	} 	
	
	@RequestMapping("init.htm")
	public ModelAndView init( HttpServletRequest httpRequest ) {
		logger.info(" # LovHeaderManagementController.init ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("lovHeaderList");
		
		try{	
			 		 	 	
			PagingBean bean = new PagingBean(); 
			mav.addObject("pagingBean", bean);	 
		 
			int offset = 0;
			bean.setOffset(offset);	 
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("lovHeader", new LovHeader());	
			request.put("pagingBean", bean);	
			bean.put("lovHeader", new LovHeader());			 
			BuckWaResponse response = lovHeaderService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("lovHeaderCreate");
		try{	
		LovHeader lovHeader = new LovHeader();
		lovHeader.setStatus(BuckWaConstants.ENABLE);
		mav.addObject("lovHeader", lovHeader);	
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createLovHeader(@ModelAttribute LovHeader lovHeader, BindingResult result) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new LovHeaderValidator().validate(lovHeader, result);			
			if (result.hasErrors()) {				
				mav.setViewName("lovHeaderCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("lovHeader", lovHeader);
				BuckWaResponse response = lovHeaderService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("lovHeader", new LovHeader());
					mav.addObject("successCode", response.getSuccessCode()); 
					
					PagingBean bean = new PagingBean(); 
					mav.addObject("pagingBean", bean);	
					mav.addObject("lovHeader", lovHeader);	
					int offset = 0;
					bean.setOffset(offset);					 
					request = new BuckWaRequest();		
					mav.addObject("lovHeader", new LovHeader());	
					request.put("pagingBean", bean);	
					bean.put("lovHeader", new LovHeader());			 
					response = lovHeaderService.getByOffset(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						logger.info(" Success ");
						PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
						bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
						bean.setTotalItems(beanReturn.getTotalItems());
						mav.addObject("pagingBean", bean);				
					}else {
						logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
						mav.addObject("errorCode", response.getErrorCode()); 
					}					
				 
					mav.setViewName("lovHeaderList");					
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("lovHeaderCreate");
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
		logger.info(" # LovHeaderManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("lovHeaderList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("lovHeader", new LovHeader());	
			request.put("pagingBean", bean);	
			bean.put("lovHeader", new LovHeader());			 
			BuckWaResponse response = lovHeaderService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute LovHeader lovHeader,@ModelAttribute PagingBean bean) {
		logger.info(" # LovHeaderManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("lovHeaderList");
		try{			
			//PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);			
			//logger.info(" PagingBean:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("lovHeader", lovHeader);
		 
			BuckWaResponse response = lovHeaderService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				mav.addObject("doSearch","true");
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	@RequestMapping(value="view.htm", method = RequestMethod.GET)
	public ModelAndView initView(@RequestParam("lovHeaderId") String lovHeaderId) {
		logger.info(" # LovHeaderManagementController.initView lovHeaderId:"+lovHeaderId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("lovHeaderId", lovHeaderId);	
		BuckWaResponse response = lovHeaderService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			LovHeader lovHeader = (LovHeader)response.getResObj("lovHeader");
			logger.info(" lovHeader return :"+BeanUtils.getBeanString(lovHeader));
			mav.addObject("lovHeader", lovHeader);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("lovHeaderView");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("lovHeaderId") String lovHeaderId) {
		logger.info(" # LovHeaderManagementController.initEdit lovHeaderId:"+lovHeaderId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("lovHeaderId", lovHeaderId);	
		BuckWaResponse response = lovHeaderService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			LovHeader lovHeader = (LovHeader)response.getResObj("lovHeader");
			logger.info(" lovHeader return :"+BeanUtils.getBeanString(lovHeader));
			mav.addObject("lovHeader", lovHeader);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("lovHeaderEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute LovHeader lovHeader, BindingResult result) {		
		logger.info(" # LovHeaderManagementController.submitEdit lovHeader:"+BeanUtils.getBeanString(lovHeader));
		ModelAndView mav = new ModelAndView();
		try{
			logger.info(" lovHeader:"+BeanUtils.getBeanString(lovHeader));
			new LovHeaderValidator().validate(lovHeader, result);
			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("lovHeaderEdit");
			}else {
	
				logger.info("  Validate Success , Do create LovHeader ");
				BuckWaRequest request = new BuckWaRequest();
				request.put("lovHeader", lovHeader);
				BuckWaResponse response = lovHeaderService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("lovHeader", new LovHeader());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("lovHeaderEdit");					
					
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("lovHeaderEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	
	
	@RequestMapping(value="manage.htm", method = RequestMethod.GET)
	public ModelAndView initManage(@RequestParam("lovHeaderId") String lovHeaderId) {
		logger.info(" # initManage lovHeaderId:"+lovHeaderId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("lovHeaderId", lovHeaderId);	
		BuckWaResponse response = lovHeaderService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			LovHeader lovHeader = (LovHeader)response.getResObj("lovHeader");
			logger.info(" lovHeader return :"+BeanUtils.getBeanString(lovHeader));
			mav.addObject("lovHeader", lovHeader);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("lovHeaderManage");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="manage.htm", method = RequestMethod.POST)
	public ModelAndView submitManage(@ModelAttribute LovHeader lovHeader, BindingResult result) {		
		logger.info(" # LovHeaderManagementController.submitManage lovHeader:"+BeanUtils.getBeanString(lovHeader));
		ModelAndView mav = new ModelAndView();
		try{
			logger.info(" lovHeader:"+BeanUtils.getBeanString(lovHeader));
			new LovHeaderValidator().validate(lovHeader, result);
			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("lovHeaderManage");
			}else {
	
				logger.info("  Validate Success , Do create LovHeader ");
				BuckWaRequest request = new BuckWaRequest();
				request.put("lovHeader", lovHeader);
				BuckWaResponse response = lovHeaderService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("lovHeader", new LovHeader());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("lovHeaderManage");					
					
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("lovHeaderManage");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("lovHeaderId") String lovHeaderId,HttpServletRequest httpRequest,@ModelAttribute LovHeader lovHeader,@ModelAttribute PagingBean bean) {
		logger.info(" # LovHeaderManagementController.delete lovHeaderId:"+lovHeaderId);
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("lovHeaderList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("lovHeaderId", lovHeaderId);	
			BuckWaResponse response = lovHeaderService.deleteById(request);
			
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
			bean.put("lovHeader", lovHeader);		
			response = lovHeaderService.getByOffset(request);
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
	
	
	
	@RequestMapping(value="addNewLOV.htm")
	public ModelAndView addNewLOV(@ModelAttribute LovHeader lovHeader, BindingResult result) {	
		ModelAndView mav = new ModelAndView();
		logger.info(" ########## addNewLOV :"+BeanUtils.getBeanString(lovHeader));
		mav.setViewName("lovHeaderManage");
		try{						
			LovDetail lovDetail = new LovDetail();
			lovDetail.setCode(lovHeader.getDetailCode());
			lovDetail.setName(lovHeader.getDetailName());
			lovDetail.setHeaderId(lovHeader.getLovHeaderId());
			lovDetail.setStatus("1");
			new LovDetailValidator().validate(lovDetail, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("lovDetail", lovDetail); 
				BuckWaResponse response = lovHeaderService.createLOVDetail(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					 lovHeader = (LovHeader)response.getResObj("lovHeader");
					mav.addObject("lovHeader", lovHeader);
					mav.addObject("successCode", response.getSuccessCode()); 
					lovHeader.setDetailCode("");
					lovHeader.setDetailName("");
					mav.setViewName("lovHeaderManage");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("lovHeaderManage");
				}		
				
			
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
	@RequestMapping(value="deleteDetail.htm", method = RequestMethod.GET)
	public ModelAndView deleteDetail(@RequestParam("lovHeaderId") String lovHeaderId,@RequestParam("lovDetailId") String lovDetailId,HttpServletRequest httpRequest) {
		logger.info(" # deleteDetail lovHeaderId:"+lovHeaderId);
		ModelAndView mav = new ModelAndView();		
		try{
			PagingBean bean = new PagingBean(); 
			mav.setViewName("lovHeaderManage");
			BuckWaRequest request = new BuckWaRequest();
			request.put("lovDetailId", lovDetailId);	
			BuckWaResponse response = lovHeaderService.deleteDetailById(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			 request = new BuckWaRequest();
			request.put("lovHeaderId", lovHeaderId);	
			 response = lovHeaderService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				logger.info("  Success");
				LovHeader lovHeader = (LovHeader)response.getResObj("lovHeader");
				logger.info(" lovHeader return :"+BeanUtils.getBeanString(lovHeader));
				mav.addObject("lovHeader", lovHeader);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}	
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return mav;
	}
	
	
	
	@RequestMapping(value="editDetail.htm", method = RequestMethod.GET)
	public ModelAndView initEditDetail(@ModelAttribute LovHeader lovHeader,@RequestParam("lovDetailId") String lovDetailId) {
		logger.info(" # initEditDetail lovDetailId:"+lovDetailId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
 
		request.put("lovDetailId", lovDetailId);	
		BuckWaResponse response = lovHeaderService.getDetailById(request);
	 
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			LovDetail lovDetail = (LovDetail)response.getResObj("lovDetail");
			lovHeader.setDetailCode(lovDetail.getCode());
			lovHeader.setDetailName(lovDetail.getName()); 
			mav.addObject("lovHeader", lovHeader);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		 
		mav.setViewName("lovDetailEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="editDetail.htm", method = RequestMethod.POST)
	public ModelAndView editDetail(@ModelAttribute LovHeader lovHeader ) {
		 
		ModelAndView mav = new ModelAndView();
		try{	
		mav.setViewName("lovDetailEdit");
		BuckWaRequest request = new BuckWaRequest();
 
		request.put("lovHeader", lovHeader);	
		BuckWaResponse response = lovHeaderService.updateDetail(request);  
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			mav.addObject("successCode",response.getSuccessCode()); 
			 request = new BuckWaRequest();
			request.put("lovHeaderId", lovHeader.getLovHeaderId());	
			 response = lovHeaderService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				logger.info("  Success");
				 lovHeader = (LovHeader)response.getResObj("lovHeader");
				logger.info(" lovHeader return :"+BeanUtils.getBeanString(lovHeader));					
				mav.addObject("lovHeader", lovHeader);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}	
			mav.setViewName("lovHeaderManage");			
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
 
}