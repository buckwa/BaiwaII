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

import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.MenuValidator;
import com.buckwa.service.intf.admin.MenuService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/menu")
@SessionAttributes(types = Menu.class)
public class MenuManagementController {
	private static Logger logger = LoggerFactory.getLogger(MenuManagementController.class);
	@Autowired
	private MenuService menuService;
	
	@ModelAttribute("menu") 
	public Menu menu() { 
		logger.info(" Call @ModelAttribute Menu ");
		return new Menu(); 
	} 	
	
	@RequestMapping("init.htm")
	public ModelAndView init(@ModelAttribute("menu") Menu menu) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("menuList");
		
		try{	
		menu = new Menu();		 	 	
		PagingBean bean = new PagingBean(); 
		mav.addObject("pagingBean", bean);	
		mav.addObject("menu", menu);	
		
	 
		int offset = 0;
		bean.setOffset(offset);	
		//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("menu", new Menu());	
		request.put("pagingBean", bean);	
		bean.put("menu", new Menu());			 
		BuckWaResponse response = menuService.getByOffset(request);
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
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("menuCreate");
		try{	
		Menu menu = new Menu();
		menu.setStatus(BuckWaConstants.ENABLE);
		mav.addObject("menu", menu);	
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createMenu(@ModelAttribute Menu menu, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{			
			new MenuValidator().validate(menu, result);			
			if (result.hasErrors()) {				
				mav.setViewName("menuCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("menu", menu);
				BuckWaResponse response = menuService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("menu", new Menu());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("menuCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("menuCreate");
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
		logger.info(" # MenuManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("menuList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("menu", new Menu());	
			request.put("pagingBean", bean);	
			bean.put("menu", new Menu());			 
			BuckWaResponse response = menuService.getByOffset(request);
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Menu menu,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("menuList");
		try{			
			//PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);			
			//logger.info(" PagingBean:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("menu", menu);
		 
			BuckWaResponse response = menuService.getByOffset(request);
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
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("menuId") String menuId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("menuId", menuId);	
		BuckWaResponse response = menuService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Menu menu = (Menu)response.getResObj("menu");
			logger.info(" menu return :"+BeanUtils.getBeanString(menu));
			mav.addObject("menu", menu);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("menuEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Menu menu, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
			logger.info(" menu:"+BeanUtils.getBeanString(menu));
			new MenuValidator().validate(menu, result);
			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("menuEdit");
			}else {
	
				logger.info("  Validate Success , Do create Menu ");
				BuckWaRequest request = new BuckWaRequest();
				request.put("menu", menu);
				BuckWaResponse response = menuService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("menu", new Menu());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("menuEdit");					
					
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("menuEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("menuId") String menuId,HttpServletRequest httpRequest,@ModelAttribute Menu menu,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("menuList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("menuId", menuId);	
			BuckWaResponse response = menuService.deleteById(request);
			
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
			bean.put("menu", menu);		
			response = menuService.getByOffset(request);
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
	
	
	@RequestMapping(value="moveDown.htm", method = RequestMethod.GET)
	public ModelAndView moveDown(@RequestParam("menuId") String menuId,HttpServletRequest httpRequest,
			@ModelAttribute Menu menu,
			@RequestParam("topOrder") String topOrder,
			@RequestParam("downOrder") String downOrder,
			@RequestParam("topMenuId") String topMenuId,
			@RequestParam("downMenuId") String downMenuId,
			@RequestParam("orderNo") String orderNo,
			@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("menuList");
			BuckWaRequest request = new BuckWaRequest();
			Menu menuMove = new Menu();
			menuMove.setMenuId(new Long(menuId));
			menuMove.setDownOrder(downOrder);
			menuMove.setTopOrder(topOrder);
			menuMove.setOrderNo(orderNo);
			menuMove.setTopMenuId(new Long((topMenuId==null||topMenuId.trim().length()==0)?"0":topMenuId));
			menuMove.setDownMenuId(new Long((downMenuId==null||downMenuId.trim().length()==0)?"0":downMenuId));
		 	request.put("menuMove", menuMove);
			logger.info(" moveDown menuMove:"+BeanUtils.getBeanString(menuMove));
			BuckWaResponse response = menuService.moveDown(request);
			
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
			bean.put("menu", menu);		
			response = menuService.getByOffset(request);
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
	@RequestMapping(value="moveUp.htm", method = RequestMethod.GET)
	public ModelAndView moveUp(@RequestParam("menuId") String menuId,HttpServletRequest httpRequest,
			@ModelAttribute Menu menu,
			@RequestParam("topOrder") String topOrder,
			@RequestParam("downOrder") String downOrder,
			@RequestParam("topMenuId") String topMenuId,
			@RequestParam("downMenuId") String downMenuId,
			@RequestParam("orderNo") String orderNo,
			@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("menuList");
			BuckWaRequest request = new BuckWaRequest();
			Menu menuMove = new Menu();
			menuMove.setMenuId(new Long(menuId));
			menuMove.setDownOrder(downOrder);
			menuMove.setTopOrder(topOrder);
			menuMove.setOrderNo(orderNo);
			menuMove.setTopMenuId(new Long((topMenuId==null||topMenuId.trim().length()==0)?"0":topMenuId));
			menuMove.setDownMenuId(new Long((downMenuId==null||downMenuId.trim().length()==0)?"0":downMenuId));
		 	request.put("menuMove", menuMove);
		 	
		 	logger.info(" moveUp menuMove:"+BeanUtils.getBeanString(menuMove));
			BuckWaResponse response = menuService.moveUp(request);
			
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
			bean.put("menu", menu);		
			response = menuService.getByOffset(request);
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