package com.buckwa.service.impl.admin;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.MenuDao;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.admin.MenuService;
import com.buckwa.util.BuckWaConstants;


@Service("menuService") 
public class MenuServiceImpl implements MenuService {
	private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	@Autowired
	private MenuDao menuDao;
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Menu> menuList = menuDao.getAll();			
			response.addResponse("menuList",menuList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Menu menu = (Menu)request.get("menu");					
			boolean isMenuNameExist = menuDao.isExist(menu.getCode(),menu.getName());
			if(isMenuNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				menuDao.create(menu);
			}

		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Menu menu = (Menu)request.get("menu");
			boolean isMenuNameExist = menuDao.isExistForUpdate(menu.getCode(),menu.getName(),menu.getMenuId());
			if(isMenuNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				menuDao.update(menu);	
				response.setSuccessCode("S002");	
			}
						
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}


	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			 
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = menuDao.getAllByOffset(pagingBean);		
			
			
			List<Menu> menuList = returnBean.getCurrentPageItem();
			rearrangeOrder(menuList);
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}


	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" MenuServiceImpl.getMenuById");
			String id = (String)request.get("menuId");			
			Menu menu = menuDao.getById(id);						
			if(menu!=null){
				response.addResponse("menu",menu);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id = (String)request.get("menuId");
	 
			menuDao.deleteById(id);	
			 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse moveDown(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			Menu menu = (Menu)request.get("menuMove");	 
			menuDao.moveDown(menu);				 			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse moveUp(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			Menu menu = (Menu)request.get("menuMove");	 
			menuDao.moveUp(menu);	 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	@Override
	public BuckWaResponse list(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private void rearrangeOrder(List<Menu> menuList){		
		try{
			String orderNo ="";
			String topOrderNo ="";
			String downOrderNo="";			
			int loop = 0;
			
			Object []menuArray = menuList.toArray();
			for(int i=0;i<menuArray.length;i++){
				Menu topMenu =null;
				Menu currentMenu = (Menu)menuArray[i];
				Menu downMenu =null;
				
				if(loop==0){
					downMenu = (Menu)menuArray[i+1];
					currentMenu.setDownOrder(downMenu.getOrderNo());
					currentMenu.setDownMenuId(downMenu.getMenuId());
				}else if(loop==menuArray.length-1){
					topMenu = (Menu)menuArray[i-1];
					currentMenu.setTopOrder(topMenu.getOrderNo());
					currentMenu.setTopMenuId(topMenu.getMenuId());
				}else {
					downMenu = (Menu)menuArray[i+1];
					topMenu = (Menu)menuArray[i-1];
					currentMenu.setDownOrder(downMenu.getOrderNo());
					currentMenu.setTopOrder(topMenu.getOrderNo());	
					currentMenu.setDownMenuId(downMenu.getMenuId());
					currentMenu.setTopMenuId(topMenu.getMenuId());
				}
				
				loop++;
			}
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
}
