package com.buckwa.web.controller.pam.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.domain.pam.Unit;
import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.domain.validator.pam.KpiTemplateValidator;
import com.buckwa.service.intf.pam.KpiCategoryService;
import com.buckwa.service.intf.pam.KpiTemplateService;
import com.buckwa.service.intf.pam.UnitService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.PAMConstants;

@Controller
@RequestMapping("/admin/kpiTemplate")
@SessionAttributes(types = KpiTemplate.class)
public class KpiTemplateController {	
	private static Logger logger = LoggerFactory.getLogger(KpiTemplateController.class);	
	@Autowired
	private KpiTemplateService kpiTemplateTreeService;		
 		
	@Autowired
	private KpiCategoryService kpitCategoryService;	
	
	@Autowired
	private UnitService unitService;	
 
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	 
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("kpiTemplateList");  
		KpiTemplate kpiTemplate= new KpiTemplate();	
		try{ 
 
			BuckWaResponse response =kpitCategoryService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<KpiCategory> kpiCategoryList = (List<KpiCategory>)response.getResObj("kpiCategoryList");					 
				mav.addObject("kpiCategoryList", kpiCategoryList);
				httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList);
				logger.info(" Start  categoryId in session:"+httpRequest.getSession().getAttribute("categoryId"));
			if(httpRequest.getSession().getAttribute("categoryId")!=null ){
				request.put("categoryId", (String)httpRequest.getSession().getAttribute("categoryId")); 
			}else{
				if(kpiCategoryList!=null&&kpiCategoryList.size()>0){
					KpiCategory catetory = kpiCategoryList.get(0); 
					request.put("categoryId", catetory.getKpiCategoryId()); 
				}			 
			}
			
			
			List<Unit> unitList = null;
			response =unitService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){					 
				unitList =(List<Unit>)response.getResObj("unitList");	
				httpRequest.getSession().setAttribute("unitList", unitList); 
			} 
			
				
			response =kpiTemplateTreeService.getTreeByCategoryId(request) ;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				KpiTemplateTree kpiTemplateTree = (KpiTemplateTree)response.getResObj("kpiTemplateTree");	
				rearangeOrder(kpiTemplateTree);
				setUnitandMarkType(kpiTemplateTree,unitList);
				mav.addObject("kpiTemplateTree", kpiTemplateTree);
			}  
			 

			} 
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="getTemplateByGroupId.htm", method = RequestMethod.GET)
	public ModelAndView getTemplateByGroupId(HttpServletRequest httpRequest,@RequestParam("groupId") String groupId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	 
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("kpiTemplateList");  
		KpiTemplate kpiTemplate= new KpiTemplate();	
		try{
 
			BuckWaResponse response =kpitCategoryService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<KpiCategory> kpiCategoryList = (List<KpiCategory>)response.getResObj("kpiCategoryList");					 
				mav.addObject("kpiCategoryList", kpiCategoryList); 
				request.put("categoryId", groupId);
				httpRequest.getSession().setAttribute("categoryId", groupId);
			    response =kpiTemplateTreeService.getTreeByCategoryId(request) ;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				KpiTemplateTree kpiTemplateTree = (KpiTemplateTree)response.getResObj("kpiTemplateTree");	
				rearangeOrder(kpiTemplateTree);
 
				setUnitandMarkType(kpiTemplateTree,(List<Unit>)httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTemplateTree", kpiTemplateTree);
			}  
			} 
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="initAddNode.htm", method = RequestMethod.GET)
	public ModelAndView initAddNode(HttpServletRequest httpRequest,@RequestParam("kpiTemplateId") String kpiTemplateId) {
		logger.info(" Start  kpiTemplateId"+kpiTemplateId);
		ModelAndView mav = new ModelAndView();	 
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		 
		mav.setViewName("addNode");  
		KpiTemplate kpiTemplate= new KpiTemplate();	
		try{
 
			kpiTemplate.setParentId(new Long(kpiTemplateId));
			kpiTemplate.setMarkType(PAMConstants.MARK_TYPE_0);
			mav.addObject("kpiTemplate", kpiTemplate);
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="addNode.htm", method = RequestMethod.POST)
	public ModelAndView addNode(HttpServletRequest httpRequest,@ModelAttribute KpiTemplate kpiTemplate, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiTemplateList");  
		try{			 
			
			new KpiTemplateValidator().validate(kpiTemplate, result);			
			if (result.hasErrors()) {				
				mav.setViewName("addNode");
			}else {	
				BuckWaRequest request = new BuckWaRequest(); 
				request.put("kpiTemplate", kpiTemplate); 
				BuckWaResponse response =kpiTemplateTreeService.create(request); 
				
				
				response =kpitCategoryService.getAll();
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					List<KpiCategory> kpiCategoryList = (List<KpiCategory>)response.getResObj("kpiCategoryList");					 
					mav.addObject("kpiCategoryList", kpiCategoryList);
					httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList);
					logger.info(" Start  categoryId session "+httpRequest.getSession().getAttribute("categoryId"));
				if(httpRequest.getSession().getAttribute("categoryId")!=null ){
					request.put("categoryId", (String)httpRequest.getSession().getAttribute("categoryId")); 
				}else{
					if(kpiCategoryList!=null&&kpiCategoryList.size()>0){
						KpiCategory catetory = kpiCategoryList.get(0); 
						request.put("categoryId", catetory.getKpiCategoryId()); 
					}			 
				}
					
				response =kpiTemplateTreeService.getTreeByCategoryId(request) ;
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					KpiTemplateTree kpiTemplateTree = (KpiTemplateTree)response.getResObj("kpiTemplateTree");	
					rearangeOrder(kpiTemplateTree);
					setUnitandMarkType(kpiTemplateTree,(List<Unit>)httpRequest.getSession().getAttribute("unitList"));
					mav.addObject("kpiTemplateTree", kpiTemplateTree);
				}  
				} 
			
			}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}

 
	
	@RequestMapping(value="initEditNode.htm", method = RequestMethod.GET)
	public ModelAndView initEditNode(HttpServletRequest httpRequest,@RequestParam("kpiTemplateId") String kpiTemplateId) {
		logger.info(" Start  kpiTemplateId"+kpiTemplateId);
		ModelAndView mav = new ModelAndView();	 
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		mav.setViewName("editNode");  
		KpiTemplate kpiTemplate= new KpiTemplate();	
		try{ 
			kpiTemplate.setKpiTemplateId(new Long(kpiTemplateId));
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiTemplate", kpiTemplate); 
			BuckWaResponse response =kpiTemplateTreeService.getById(request);
			 
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
			 kpiTemplate = (KpiTemplate)response.getResObj("kpiTemplate");	  
			 kpiTemplate.setKpiTemplateId(new Long(kpiTemplateId));
			mav.addObject("kpiTemplate", kpiTemplate);
			} else{
				mav.setViewName("editNode");  	
			}	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="editNode.htm", method = RequestMethod.POST)
	public ModelAndView editNode(HttpServletRequest httpRequest,@ModelAttribute KpiTemplate kpiTemplate, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiTemplateList");  
		try{			 
			new KpiTemplateValidator().validate(kpiTemplate, result);			
			if (result.hasErrors()) {				
				mav.setViewName("editNode");
			}else {	
				BuckWaRequest request = new BuckWaRequest(); 
				request.put("kpiTemplate", kpiTemplate); 
				BuckWaResponse response =kpiTemplateTreeService.update(request); 
				
				
				
				response =kpitCategoryService.getAll();
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					List<KpiCategory> kpiCategoryList = (List<KpiCategory>)response.getResObj("kpiCategoryList");					 
					mav.addObject("kpiCategoryList", kpiCategoryList);
					httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList);
					
				if(httpRequest.getSession().getAttribute("categoryId")!=null ){
					request.put("categoryId", (String)httpRequest.getSession().getAttribute("categoryId")); 
				}else{
					if(kpiCategoryList!=null&&kpiCategoryList.size()>0){
						KpiCategory catetory = kpiCategoryList.get(0); 
						request.put("categoryId", catetory.getKpiCategoryId()); 
					}			 
				}
					
				response =kpiTemplateTreeService.getTreeByCategoryId(request) ;
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					KpiTemplateTree kpiTemplateTree = (KpiTemplateTree)response.getResObj("kpiTemplateTree");	
					rearangeOrder(kpiTemplateTree);
					setUnitandMarkType(kpiTemplateTree,(List<Unit>)httpRequest.getSession().getAttribute("unitList"));
					mav.addObject("kpiTemplateTree", kpiTemplateTree);
				}  
				} 
			}
				
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}

	@RequestMapping(value="deleteNode.htm", method = RequestMethod.GET)
	public ModelAndView deleteNode(HttpServletRequest httpRequest,@RequestParam("kpiTemplateId") String kpiTemplateId) {
		logger.info(" Start  kpiTemplateId"+kpiTemplateId);
		ModelAndView mav = new ModelAndView();	 
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		mav.setViewName("kpiTemplateList");  
		KpiTemplate kpiTemplate= new KpiTemplate();	
		try{ 
			BuckWaRequest request = new BuckWaRequest(); 
			kpiTemplate.setKpiTemplateId(new Long(kpiTemplateId));
			request.put("kpiTemplate", kpiTemplate); 
			BuckWaResponse response =kpiTemplateTreeService.remove(request);

			response =kpitCategoryService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<KpiCategory> kpiCategoryList = (List<KpiCategory>)response.getResObj("kpiCategoryList");					 
				mav.addObject("kpiCategoryList", kpiCategoryList);
				httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList);
				
			if(httpRequest.getSession().getAttribute("categoryId")!=null ){
				request.put("categoryId", (String)httpRequest.getSession().getAttribute("categoryId")); 
			}else{
				if(kpiCategoryList!=null&&kpiCategoryList.size()>0){
					KpiCategory catetory = kpiCategoryList.get(0); 
					request.put("categoryId", catetory.getKpiCategoryId()); 
				}			 
			}
				
			response =kpiTemplateTreeService.getTreeByCategoryId(request) ;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				KpiTemplateTree kpiTemplateTree = (KpiTemplateTree)response.getResObj("kpiTemplateTree");	
				rearangeOrder(kpiTemplateTree);
				setUnitandMarkType(kpiTemplateTree,(List<Unit>)httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTemplateTree", kpiTemplateTree);
			}  
			} 
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
 
	public void addChild(Node<KpiTemplate> newNode,Long kpiTemplateId, KpiTemplateTree tree) {
		
		 //  List<Kpi> children = getByParentId(taskElement.getData().getKpiId());
		logger.info(" ### Start Recursive #####");
		saveOrUpdate(tree,kpiTemplateId,newNode);
		getRecursive( tree.getRootElement());
		logger.info(" ### End Recursive #####");
		
		
			List  children = tree.getRootElement().getChildren();
			logger.info(" ## addChild root Name:"+tree.getRootElement().getData().getName());
	        List<Node<KpiTemplate>> childElements = new ArrayList<Node<KpiTemplate>>();
	        for (Iterator<Node> it = children.iterator(); it.hasNext();) {
	        	Node<KpiTemplate> nodtTmp = it.next();
	        	
	        	logger.info(" ## kpiTemplateId:"+nodtTmp.getData().getKpiTemplateId());
	        	if(kpiTemplateId==nodtTmp.getData().getKpiTemplateId()){
	        		logger.info(" ### Found Matching KPI #####");
	        	}
	            //Node<Kpi> childElement = new Node<Kpi>(newNode);
	           // childElements.add(childElement);
	           // getRecursive(childElement, tree);
	        }
	      //  taskElement.setChildren(childElements);

	}
	
	
	   public void saveOrUpdate(KpiTemplateTree kpiTemplateTree,Long nodeId,Node<KpiTemplate> newNode) {
	        List<Node<KpiTemplate>> tasks = kpiTemplateTree.toList();
	        // save the tree in reverse order, starting from the leaf nodes
	        // and going up to the root of the tree.
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<KpiTemplate> taskElement = tasks.get(i);
	            KpiTemplate task = taskElement.getData();
	           // saveOrUpdate(task);
	            Long parentId = task.getKpiTemplateId();
	            for (Iterator<Node<KpiTemplate>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<KpiTemplate> childElement = it.next();
	                KpiTemplate childTask = childElement.getData();
	                logger.info(" ######## saveOrUpdate childTask:"+childTask.getKpiTemplateId());
	                if(nodeId.intValue()==childTask.getKpiTemplateId().intValue()){
	                	  logger.info(" ########  Found Matching KPI Id:"+nodeId);
	                	  childElement.getChildren().add(newNode);
	                }
	                childTask.setParentId(parentId);
	              //  taskDao.saveOrUpdate(childTask);
	            }
	        }
	    }
	
 
	public void getRecursive( Node<KpiTemplate> node) {
			List  children =node.getChildren();
	        List<Node<KpiTemplate>> childElements = new ArrayList<Node<KpiTemplate>>();
	        for (Iterator<Node> it = children.iterator(); it.hasNext();) {
	        	Node<KpiTemplate> nodtTmp = it.next();
	        	logger.info(" ## kpiTemplateId:"+nodtTmp.getData().getKpiTemplateId());
	 
	           // Node<KpiTemplate> childElement = new Node<KpiTemplate>(childKpiTemplate);
	           // childElements.add(childElement);
	           getRecursive( nodtTmp);
	        }
	       // taskElement.setChildren(childElements);

	}
	
	
	public void addChild( Node<KpiTemplate> rootNode,Long nodeId,KpiTemplate kpiTemplateNew) {
		List  children =rootNode.getChildren();
        List<Node<KpiTemplate>> childElements = new ArrayList<Node<KpiTemplate>>();
        for (Iterator<Node> it = children.iterator(); it.hasNext();) {
        	Node<KpiTemplate> nodtTmp = it.next();
        	logger.info(" ## kpiTemplateId:"+nodtTmp.getData().getKpiTemplateId());
 
           // Node<KpiTemplate> childElement = new Node<KpiTemplate>(childKpiTemplate);
           // childElements.add(childElement);
           getRecursive( nodtTmp);
        }
       // taskElement.setChildren(childElements);

}

	
	   public void rearangeOrder(KpiTemplateTree kpiTemplateTree ) {
	        List<Node<KpiTemplate>> tasks = kpiTemplateTree.toList();
	        // save the tree in reverse order, starting from the leaf nodes
	        // and going up to the root of the tree.
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<KpiTemplate> taskElement = tasks.get(i);
	            KpiTemplate task = taskElement.getData();
	           
	            Long parentId = task.getKpiTemplateId();
	            int childOrder=1;
	            for (Iterator<Node<KpiTemplate>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<KpiTemplate> childElement = it.next();
	                KpiTemplate childTask = childElement.getData();
	                logger.info(" ######## saveOrUpdate childTask:"+childTask.getKpiTemplateId());
	                childTask.setChildOrder(childOrder);
	                childTask.setParentId(parentId); 
	                childOrder++;
	            }
	        }
	    }
	   public void setUnitandMarkType(KpiTemplateTree kpiTemplateTree,List<Unit> unitList) {
	        List<Node<KpiTemplate>> tasks = kpiTemplateTree.toList();	      
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<KpiTemplate> taskElement = tasks.get(i);
	            KpiTemplate task = taskElement.getData(); 
	           // Long parentId = task.getKpiTemplateId();
	           // int childOrder=1;
	            for (Iterator<Node<KpiTemplate>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<KpiTemplate> childElement = it.next();
	               int numberofchild =  childElement.getNumberOfChildren();
	                KpiTemplate childTask = childElement.getData(); 
	                String markType =childTask.getMarkType();
	                 
	                if(numberofchild>0){
	                	childTask.setMarkTypeDesc("");
	                }else{
	                	
	                if(PAMConstants.MARK_TYPE_0.equals(markType)){
	                	childTask.setMarkTypeDesc("\u0e04\u0e30\u0e41\u0e19\u0e19");
	                }else if(PAMConstants.MARK_TYPE_1.equals(markType)) { 
		                Long unitId =childTask.getUnitId();
		                if(unitId!=null){
		                	for(Unit unittmp :unitList){
		                		Long unitIdtmp = unittmp.getUnitId();
		                		if(unitId.intValue()==unitIdtmp.intValue()){
		                			childTask.setMarkTypeDesc("\u0e04\u0e30\u0e41\u0e19\u0e19/"+unittmp.getName());
		                			break;
		                		}
		                	}

		                }
	                }else{
	                	//childTask.setMarkTypeDesc("N/A");
	                	childTask.setMarkTypeDesc("");
	                }
	                }

	            }
	        }
	    }

}
