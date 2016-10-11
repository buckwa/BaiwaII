package com.buckwa.web.controller.pam.admin;

import java.math.BigDecimal;
import java.util.Iterator;
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

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.Unit;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTree;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.domain.validator.pam.KpiTreeValidator;
import com.buckwa.domain.validator.pam.KpiYearMappingValidator;
import com.buckwa.service.intf.pam.KpiCategoryService;
import com.buckwa.service.intf.pam.KpiTreeService;
import com.buckwa.service.intf.pam.KpiYearMappingService;
import com.buckwa.service.intf.pam.UnitService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.PAMConstants;

@Controller
@RequestMapping("/admin/marklevel")
@SessionAttributes(types = KpiYearMapping.class)
public class KpiMarklevelController {
	private static Logger logger = LoggerFactory.getLogger(KpiMarklevelController.class);

	@Autowired
	private KpiCategoryService kpiCategoryService;

	@Autowired
	private KpiYearMappingService kpiYearMappingService;

	@Autowired
	private YearService yearService;

	@Autowired
	private KpiTreeService kpiTreeService;

	@Autowired
	private UnitService unitService;

	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start initList() ");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();

		BuckWaRequest request = new BuckWaRequest();
		mav.addObject("pagingBean", bean);
		mav.setViewName("kpilevelList");

		KpiYearMapping kpiYearMapping = new KpiYearMapping();
		mav.addObject("kpiYearMapping", kpiYearMapping);
		try {
			BuckWaResponse response = yearService.getAll();
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				List<Year> yearList = (List<Year>) response.getResObj("yearList");

				if (yearList != null && yearList.size() > 0) {
					mav.addObject("yearList", yearList);
					httpRequest.getSession().setAttribute("yearList", yearList);
					response = kpiCategoryService.getAll();
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						List<KpiCategory> kpiCategoryList = (List<KpiCategory>) response.getResObj("kpiCategoryList");
						if (kpiCategoryList != null && kpiCategoryList.size() > 0) {
							mav.addObject("kpiCategoryList", kpiCategoryList);
							httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList);

							// Create New if not Exist
						}

					}

				}
			}

			// Validate and check is Tree Create belong year,category
			List<Unit> unitList = null;
			response = unitService.getAll();
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				unitList = (List<Unit>) response.getResObj("unitList");
				httpRequest.getSession().setAttribute("unitList", unitList);
			}

			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);

			request = new BuckWaRequest();
			mav.addObject("kpiYearMapping", new KpiYearMapping());
			request.put("pagingBean", bean);
			bean.put("kpiYearMapping", new KpiYearMapping());
			response = kpiYearMappingService.getByOffset(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);
			} else {
				mav.addObject("errorCode", response.getErrorCode());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	
	
	@RequestMapping(value = "initCreateNewMapping.htm", method = RequestMethod.GET)
	public ModelAndView initCreateNewMapping(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();

		BuckWaRequest request = new BuckWaRequest();
		mav.addObject("pagingBean", bean);
		mav.setViewName("createKpiyearList");

		KpiYearMapping kpiYearMapping = new KpiYearMapping();
		//kpiYearMapping.setYearId(new Long(2012));
		mav.addObject("kpiYearMapping", kpiYearMapping);
		try {
			BuckWaResponse response = yearService.getAll();
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				List<Year> yearList = (List<Year>) response.getResObj("yearList"); 
				if (yearList != null && yearList.size() > 0) {
					mav.addObject("yearList", yearList);
					httpRequest.getSession().setAttribute("yearList", yearList);
					response = kpiCategoryService.getAll();
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						List<KpiCategory> kpiCategoryList = (List<KpiCategory>) response.getResObj("kpiCategoryList");
						if (kpiCategoryList != null && kpiCategoryList.size() > 0) {
							mav.addObject("kpiCategoryList", kpiCategoryList);
							httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList); 
						} 
					}

				}
			}

 

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	

	@RequestMapping(value = "createNewMapping.htm", method = RequestMethod.POST)
	public ModelAndView createNewMapping(HttpServletRequest httpRequest, KpiYearMapping kpiYearMapping,  BindingResult result) {	 
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("kpiyearList");
		PagingBean bean = new PagingBean(); 
		mav.addObject("pagingBean", bean);
		try {
			Kpi kpi = new Kpi();
			BuckWaRequest request = new BuckWaRequest();
	        
			new KpiYearMappingValidator().validate(kpiYearMapping, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("createKpiyearList");
			}else {

			request.put("kpiYearMapping", kpiYearMapping);
			BuckWaResponse response = kpiYearMappingService.createNewMapping(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
				bean.setOffset(offset);

				request = new BuckWaRequest();
				mav.addObject("kpiYearMapping", new KpiYearMapping());
				request.put("pagingBean", bean);
				bean.put("kpiYearMapping", new KpiYearMapping());
				response = kpiYearMappingService.getByOffset(request);

				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					logger.info(" Success ");
					PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
					bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
					bean.setTotalItems(beanReturn.getTotalItems());
					mav.addObject("pagingBean", bean);
				} else {
					mav.addObject("errorCode", response.getErrorCode());
				}

			} else {
				mav.addObject("errorCode", response.getErrorCode());
				mav.setViewName("createKpiyearList");
				return mav;
			}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "getByRootId.htm", method = RequestMethod.GET)
	public ModelAndView getTemplateByGroupId(HttpServletRequest httpRequest, @RequestParam("kpiId") String kpiId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("viewYearKpi");
		KpiTemplate kpi = new KpiTemplate();
		try {
			request.put("kpiId", kpiId);
			mav.addObject("kpi", new Kpi());
			BuckWaResponse response = kpiTreeService.getTreeById(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);

				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "kpilevelLevel1.htm", method = RequestMethod.GET)
	public ModelAndView kpilevelLevel1(HttpServletRequest httpRequest, @RequestParam("yearId") String yearId, @RequestParam("groupId") String groupId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("kpilevelLevel1");
		KpiTemplate kpi = new KpiTemplate();
		try {
			httpRequest.getSession().setAttribute("yearId", yearId);
			httpRequest.getSession().setAttribute("groupId", groupId);
			kpi.setYearId(new Long(yearId));
			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", kpi);
			BuckWaResponse response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);

				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
				httpRequest.getSession().setAttribute("kpiTree", kpiTree);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	@RequestMapping(value = "editweightLevel1.htm", method = RequestMethod.POST)
	public ModelAndView editweightLevel1(HttpServletRequest httpRequest ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("kpilevelLevel1");
		KpiTemplate kpi = new KpiTemplate();
		try {
			String yearId = (String)httpRequest.getSession().getAttribute("yearId");
			String groupId = (String)httpRequest.getSession().getAttribute("groupId");
			KpiTree kpiTree= (KpiTree)httpRequest.getSession().getAttribute("kpiTree");
			
			logger.info("  kpiTree update :"+kpiTree);
			// Save 
			request.put("kpiTree", kpiTree);
			kpiTreeService.editLevel1(request);
			 
			kpi.setYearId(new Long(yearId));
			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", kpi);
			BuckWaResponse response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);

				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	
	@RequestMapping(value = "initEditkpilevelLevel1.htm", method = RequestMethod.GET)
	public ModelAndView initEditkpilevelLevel1(HttpServletRequest httpRequest, @RequestParam("yearId") String yearId, @RequestParam("groupId") String groupId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("initEditkpilevelLevel1");
		KpiTemplate kpi = new KpiTemplate();
		try {
			httpRequest.getSession().setAttribute("yearId", yearId);
			httpRequest.getSession().setAttribute("groupId", groupId);
			kpi.setYearId(new Long(yearId));
			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", kpi);
			BuckWaResponse response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);

				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "kpilevelLevel2.htm", method = RequestMethod.GET)
	public ModelAndView kpilevelLevel2(HttpServletRequest httpRequest, @RequestParam("yearId") String yearId, @RequestParam("groupId") String groupId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request1 = new BuckWaRequest();
		mav.setViewName("kpilevelLevel2");
		KpiTemplate kpi = new KpiTemplate();
		try {
			httpRequest.getSession().setAttribute("yearId", yearId);
			httpRequest.getSession().setAttribute("groupId", groupId);
			
			kpi.setYearId(new Long(yearId));
			request1.put("yearId",yearId);
			request1.put("groupId", groupId);
			mav.addObject("kpi", kpi);
			
	 
			BuckWaResponse response = kpiTreeService.getTemplateByYearAndGroupId(request1);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);

				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	@RequestMapping(value = "initEditkpilevelLevel2.htm", method = RequestMethod.GET)
	public ModelAndView initEditkpilevelLevel2(HttpServletRequest httpRequest, @RequestParam("yearId") String yearId, @RequestParam("groupId") String groupId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("initEditkpilevelLevel2");
		KpiTemplate kpi = new KpiTemplate();
		try {
			httpRequest.getSession().setAttribute("yearId", yearId);
			httpRequest.getSession().setAttribute("groupId", groupId);
			kpi.setYearId(new Long(yearId));
			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", kpi);
			BuckWaResponse response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);

				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "create.htm", method = RequestMethod.GET)
	public ModelAndView create(HttpServletRequest httpRequest, @RequestParam("kpiCategoryId") String kpiCategoryId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("kpiyearCreate");
		try {
			Kpi kpi = new Kpi();
			BuckWaRequest request = new BuckWaRequest();
			request.put("kpiCategoryId", kpiCategoryId);
			BuckWaResponse response = kpiCategoryService.getById(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiCategory kpiCategory = (KpiCategory) response.getResObj("kpiCategory");
				// kpi.setKpiCategoryId(kpiCategory.getKpiCategoryId());
				// kpi.setCategoryName(kpiCategory.getName());
				logger.info("## kpi:" + BeanUtils.getBeanString(kpi));
			} else {
				mav.addObject("errorCode", response.getErrorCode());
				return mav;
			}
			mav.addObject("kpi", kpi);

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest, @ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("kpilevelList");
		try {
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);
			// logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();
			mav.addObject("kpiYearMapping", new KpiYearMapping());
			request.put("pagingBean", bean);
			bean.put("kpiYearMapping", new KpiYearMapping());
			BuckWaResponse response = kpiYearMappingService.getByOffset(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);
			} else {
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest, @ModelAttribute KpiYearMapping kpiYearMapping, @ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("kpilevelList");
		try {
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);
			bean.put("kpiYearMapping", kpiYearMapping);
			BuckWaResponse response = kpiYearMappingService.getByOffset(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);
				mav.addObject("doSearch", "true");
			} else {
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	public void rearangeOrder(KpiTree kpiTree) {
		if (kpiTree != null) {
			List<Node<Kpi>> tasks = kpiTree.toList();
			// save the tree in reverse order, starting from the leaf nodes
			// and going up to the root of the tree.
			int numberOfNodes = tasks.size();
			
			logger.info(" ##  rearangeOrder number of child :"+numberOfNodes);
			if(numberOfNodes==0){
				Node<Kpi> taskElement = tasks.get(0);
				taskElement.getData().setIsLeave("Y");
			}else{
				Node<Kpi> taskElement = tasks.get(0);
				taskElement.getData().setIsLeave("N");
			}
			for (int i = numberOfNodes - 1; i >= 0; i--) {
				Node<Kpi> taskElement = tasks.get(i);
				Kpi task = taskElement.getData();
				// saveOrUpdate(task);
				Long parentId = task.getKpiId();
				int childOrder = 1;
				for (Iterator<Node<Kpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
					Node<Kpi> childElement = it.next();
					Kpi childTask = childElement.getData();
					logger.info(" ######## saveOrUpdate childTask:" + childTask.getKpiId());
					childTask.setChildOrder(childOrder);
					childTask.setParentId(parentId);
					// taskDao.saveOrUpdate(childTask);
					
					int numberOfNodes1 = childElement.getChildren().size();;
					
					logger.info(" ##  rearangeOrder number of child :"+numberOfNodes1);
					if(numberOfNodes1==0){
					 
						childElement.getData().setIsLeave("Y");
					}else{
					 
						childElement.getData().setIsLeave("N");
					}
	
					childOrder++;
				}
			}
			
			
		}
	}

	public void setUnitandMarkType(KpiTree kpiTree, List<Unit> unitList) {
		logger.info(" ### setUnitandMarkType kpiTree:"+kpiTree);
		List<Node<Kpi>> tasks = kpiTree.toList();
		int numberOfNodes = tasks.size();
		for (int i = numberOfNodes - 1; i >= 0; i--) {
			Node<Kpi> taskElement = tasks.get(i);
			Kpi task = taskElement.getData();
			// Long parentId = task.getKpiId();
			// int childOrder=1;
			for (Iterator<Node<Kpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
				Node<Kpi> childElement = it.next();
				int numberofchild = childElement.getNumberOfChildren();
				Kpi childTask = childElement.getData();
				String markType = childTask.getMarkType();

				if (numberofchild > 0) {

					childTask.setMarkTypeDesc("");
				} else {
					if (PAMConstants.MARK_TYPE_0.equals(markType)) {
						childTask.setMarkTypeDesc("\u0e04\u0e30\u0e41\u0e19\u0e19");
					} else if (PAMConstants.MARK_TYPE_1.equals(markType)) {
						Long unitId = childTask.getUnitId();
						if (unitId != null) {
							for (Unit unittmp : unitList) {
								Long unitIdtmp = unittmp.getUnitId();
								if (unitId.intValue() == unitIdtmp.intValue()) {
									childTask.setMarkTypeDesc("\u0e04\u0e30\u0e41\u0e19\u0e19/" + unittmp.getName());
									break;
								}
							}
						}
					} else {
						childTask.setMarkTypeDesc("N/A");
					}
				}
			}
		}
	}

	@RequestMapping(value = "initAddNode.htm", method = RequestMethod.GET)
	public ModelAndView initAddNode(HttpServletRequest httpRequest, @RequestParam("kpiId") String kpiId) {
		logger.info(" Start  kpiId" + kpiId);
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiyearAddNode");
		Kpi kpi = new Kpi();
		try {

			kpi.setParentId(new Long(kpiId));
			kpi.setMarkType(PAMConstants.MARK_TYPE_0);
			mav.addObject("kpi", kpi);
			httpRequest.getSession().setAttribute("parentId", kpiId);
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "addNode.htm", method = RequestMethod.POST)
	public ModelAndView addNode(HttpServletRequest httpRequest, @ModelAttribute Kpi kpi, BindingResult result) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewYearKpi");
		try {
			
			new KpiTreeValidator().validate(kpi, result);			
			if (result.hasErrors()) {				
				mav.setViewName("kpiyearAddNode");
			}else {	
			
			BuckWaRequest request = new BuckWaRequest();
			String parentKpiid = (String) httpRequest.getSession().getAttribute("parentId");
			kpi.setParentId(new Long(parentKpiid));
			request.put("kpi", kpi);
			logger.info(" addNode kpi:" + BeanUtils.getBeanString(kpi));
			BuckWaResponse response = kpiTreeService.create(request);

			String yearId = (String) httpRequest.getSession().getAttribute("yearId");
			String groupId = (String) httpRequest.getSession().getAttribute("groupId");

			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", new Kpi());
			response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);
				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "initEditNode.htm", method = RequestMethod.GET)
	public ModelAndView initEditNode(HttpServletRequest httpRequest, @RequestParam("kpiId") String kpiId) {
		logger.info(" Start  kpiId" + kpiId);
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiyearEditNode");
		Kpi kpi = new Kpi();
		try {
			kpi.setKpiId(new Long(kpiId));
			BuckWaRequest request = new BuckWaRequest();
			request.put("kpi", kpi);
			BuckWaResponse response = kpiTreeService.getById(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				kpi = (Kpi) response.getResObj("kpi");
				kpi.setKpiTemplateId(new Long(kpiId));
				kpi.setKpiId(new Long(kpiId));
				mav.addObject("kpi", kpi);
				httpRequest.getSession().setAttribute("kpi", kpi);
			} else {
				mav.setViewName("kpiyearEditNode");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "editNode.htm", method = RequestMethod.POST)
	public ModelAndView editNode(HttpServletRequest httpRequest, @ModelAttribute Kpi kpi, BindingResult result) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewYearKpi");
		try {
			
			new KpiTreeValidator().validate(kpi, result);			
			if (result.hasErrors()) {				
				logger.info(" ### Found Error ");
				mav.setViewName("kpiyearEditNode");
			}else {	
			BuckWaRequest request = new BuckWaRequest();
			request.put("kpi", kpi);
			BuckWaResponse response = kpiTreeService.update(request);

			String yearId = (String) httpRequest.getSession().getAttribute("yearId");
			String groupId = (String) httpRequest.getSession().getAttribute("groupId");

			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", new Kpi());
			response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
				rearangeOrder(kpiTree);
				setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
				mav.addObject("kpiTree", kpiTree);
			}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "deleteNode.htm", method = RequestMethod.GET)
	public ModelAndView deleteNode(HttpServletRequest httpRequest, @RequestParam("kpiId") String kpiId) {
		logger.info(" Start  kpiId" + kpiId);
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewYearKpi");
		Kpi kpi = new Kpi();
		try {
			BuckWaRequest request = new BuckWaRequest();
			kpi.setKpiId(new Long(kpiId));
			request.put("kpi", kpi);
			BuckWaResponse response = kpiTreeService.remove(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				mav.addObject("successCode", response.getSuccessCode()); 
				String yearId = (String) httpRequest.getSession().getAttribute("yearId");
				String groupId = (String) httpRequest.getSession().getAttribute("groupId");

				request.put("yearId", yearId);
				request.put("groupId", groupId);
				mav.addObject("kpi", new Kpi());
				response = kpiTreeService.getTemplateByYearAndGroupId(request);
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
					rearangeOrder(kpiTree);
					setUnitandMarkType(kpiTree, (List<Unit>) httpRequest.getSession().getAttribute("unitList"));
					mav.addObject("kpiTree", kpiTree);
					
				}
				
			}
		 
		



		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	@RequestMapping(value="updateMarkLevel.htm", method = RequestMethod.GET)
	public ModelAndView updateMarkLevel(HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		
		String levelId = httpRequest.getParameter("levelId");
		String mark = httpRequest.getParameter("mark");
		String level = httpRequest.getParameter("level");
		
		logger.info(" ## updateMarkLevel levelId:"+levelId+" mark:"+mark+" level:"+level);
		//String yearId =httpRequest.getParameter("yearId");
		//String groupId =httpRequest.getParameter("groupId");
		
		 
		String yearId = (String)httpRequest.getSession().getAttribute("yearId");
		String groupId  = (String)httpRequest.getSession().getAttribute("groupId");
		
 
		
		try{	
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			request.put("levelId", levelId);
			request.put("mark",  mark );
			request.put("level",  level );
			//response = personMappingEvaluateService.updateEvaluateKpiEstimateScore(request);
			//response = kpiTreeService.updateWeight(request);
			response = kpiTreeService.updateLevel(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				personMappingEvaluateService.endEvaluateSession(request);
			}
			
			//Redirect and ReloadData
			//return initList(personId, httpRequest, BuckWaConstants.ACTION_EDIT, bean);
			return kpilevelLevel2(httpRequest,yearId,groupId);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}

}
