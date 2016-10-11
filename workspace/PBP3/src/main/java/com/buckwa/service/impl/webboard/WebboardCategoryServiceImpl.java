package com.buckwa.service.impl.webboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.webboard.WebboardCategoryDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.Category;
import com.buckwa.domain.util.Unit;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.webboard.WebboardCategoryService;
import com.buckwa.util.BuckWaConstants;


@Service("webboardCategoryService") 
public class WebboardCategoryServiceImpl implements WebboardCategoryService {
	private static Logger logger = LoggerFactory.getLogger(WebboardCategoryServiceImpl.class);
	@Autowired
	private WebboardCategoryDao webboardCategoryDao;
	
	 
	
	
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Category> categoryList = webboardCategoryDao.getAll();			
			response.addResponse("categoryList",categoryList);				
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
			Category category = (Category)request.get("category");					
			boolean isCategoryNameExist = webboardCategoryDao.isExist(category.getName());
			logger.info(" isCategoryNameExist:"+isCategoryNameExist);
			if(isCategoryNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				webboardCategoryDao.create(category);
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
			Category category = (Category)request.get("category");
			boolean isCategoryNameExist = webboardCategoryDao.isExistForUpdate( category.getName(),category.getCategoryId());
			if(isCategoryNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				webboardCategoryDao.update(category);	
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
			PagingBean returnBean = webboardCategoryDao.getAllByOffset(pagingBean);	 
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
			logger.info(" CategoryServiceImpl.getCategoryById");
			String id =  request.get("categoryId")+"";			
			Category category = webboardCategoryDao.getById(id);						
			if(category!=null){
				response.addResponse("category",category);
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
			String id =  request.get("categoryId")+"";
			webboardCategoryDao.deleteById(id);	
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
	
	@Override
	public List<Category> getAllCategory() {
		List returnList = new ArrayList();
		try{		
			returnList = webboardCategoryDao.getAll();				
		}catch(Exception ex){
			ex.printStackTrace();		
		}
		return returnList;
	}
 
}
