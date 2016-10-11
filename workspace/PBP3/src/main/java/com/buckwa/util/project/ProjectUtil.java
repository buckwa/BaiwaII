package com.buckwa.util.project;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.project.ProjectCommonService;
import com.buckwa.util.BuckWaConstants;

@Service("projectUtil")
public class ProjectUtil {
	private static Logger logger = LoggerFactory.getLogger(ProjectUtil.class);
	@Autowired
	private ProjectCommonService projectCommonService;
	
    public synchronized  String getBusinessRuleNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestBusinessRuleNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getLatestVendorCode: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "BR1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "BR"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    public synchronized  String getActorNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestActorNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getLatestVendorCode: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "AC1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "AC"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    
	
    public synchronized  String getUseCaseNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestUseCaseNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getLatestVendorCode: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "UC1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "UC"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    
    public synchronized   String getDetailDesignNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestDetailDesignNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getLatestVendorCode: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "DS1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "DS"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    
    
    public  synchronized  String getTestCaseNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestTestCaseNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getTestCaseNo: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "TC1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "TC"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    
    
    public synchronized  String getUtilNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestUtilNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getUtilNo: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "U1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "U"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    
    public synchronized  String getMessageNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestMessageNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getMessageNo: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "M1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "M"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }   
    public  synchronized  String getModuleNo(Long projectId)  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    		request.put("projectId", projectId);
    		BuckWaResponse response  = projectCommonService.getLatestModuleNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getModuleNo: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "M1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "M"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }	    
    
    
    public  Long getProjectIdFromSession(HttpServletRequest httpRequest)  {
    	Long returnValue = null;
    	try{
    		String projectIdstr = httpRequest.getSession().getAttribute("projectId")+"";
    		if(projectIdstr!=null){
    			returnValue= new Long(projectIdstr);
    		} 
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }
    
    public  Long getVisionIdFromSession(HttpServletRequest httpRequest)  {
    	Long returnValue = null;
    	try{
    		String projectIdstr = httpRequest.getSession().getAttribute("visionId")+"";
    		if(projectIdstr!=null){
    			returnValue= new Long(projectIdstr);
    		} 
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }
    
    
    public  String getProjectNameFromSession(HttpServletRequest httpRequest)  {
    	String returnValue = null;
    	try{
    		returnValue = httpRequest.getSession().getAttribute("projectName")+"";
     
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }
    
    
    public  Long getUsecaseIdFromSession(HttpServletRequest httpRequest)  {
    	Long returnValue = null;
    	try{
    		String projectIdstr = httpRequest.getSession().getAttribute("usecaseId")+"";
    		if(projectIdstr!=null){
    			returnValue= new Long(projectIdstr);
    		} 
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }
    
    
    
    public  String getWebboardCategoryTypeFromSession(HttpServletRequest httpRequest)  {
    	String returnValue = "";
    	try{
    		returnValue = (String)httpRequest.getSession().getAttribute("webboardCategoryType"); 
    		logger.info(" ###  getWebboardCategoryTypeFromSession returnValue:"+returnValue);
    	}catch(Exception ex){
    		ex.printStackTrace(); 
    	}    	
    	return returnValue;
    }       
    
    
    public  Long getVisionFromSession(HttpServletRequest httpRequest)  {
    	Long returnValue = null;
    	try{
    		String projectIdstr = httpRequest.getSession().getAttribute("visionId")+"";
    		if(projectIdstr!=null){
    			returnValue= new Long(projectIdstr);
    		} 
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }
    
    public  Long  getModuleIdFromSession(HttpServletRequest httpRequest)  {
    	Long returnValue = null;
    	try{
    		 
    		 String projectIdstr = httpRequest.getSession().getAttribute("moduleId")+"";
     		if(projectIdstr!=null){
    			returnValue= new Long(projectIdstr);
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }
    public  Long  getDetailDesignIdFromSession(HttpServletRequest httpRequest)  {
    	Long returnValue = null;
    	try{
    		 
    		 String projectIdstr = httpRequest.getSession().getAttribute("detailDesignId")+"";
     		if(projectIdstr!=null){
    			returnValue= new Long(projectIdstr);
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnValue;
    }  
    
    
    public synchronized  String getLabNo()  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();
    	 
    		BuckWaResponse response  = projectCommonService.getLatestLabNo(request);	
    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getLatestVendorCode: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "LB1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "LB"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }
    public synchronized  String getLabCategoryNo()  {
    	String returnStr = "";
    	try{
    		BuckWaRequest request = new BuckWaRequest();    	 
    		BuckWaResponse response  = projectCommonService.getLabCategoryNo(request);	    		
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				String tmpStr = (String)response.getResObj("returnValue");
                 logger.info(" getLatestVendorCode: "+tmpStr);
				if(tmpStr==null){
					returnStr =  "LC1";
				}else{ 					
					int currentCode = Integer.parseInt(tmpStr);
					int returnInt = currentCode+1;
					returnStr = "LC"+returnInt+"";
				} 
			}else {				 
				 throw new Exception();
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		 
    	}    	
    	return returnStr;
    }   
    
}
