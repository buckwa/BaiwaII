package com.buckwa.service.impl.pam;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.ClassRoomProcessDao;
import com.buckwa.dao.intf.pam.FileLocationDao;
import com.buckwa.dao.intf.pam.WorkPersonAttrDao;
import com.buckwa.dao.intf.pam.WorkPersonDao;
import com.buckwa.dao.intf.pam.WorkPersonKpiDao;
import com.buckwa.dao.intf.pam.WorkPersonMappingFileDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.ClassRoomProcess;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.Teacher;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.domain.pam.WorkPersonAttr;
import com.buckwa.domain.pam.WorkPersonKpi;
import com.buckwa.domain.pam.WorkPersonMappingFile;
import com.buckwa.service.intf.pam.WorkPersonService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.FileUtils;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("workPersonService")
public class WorkPersonServiceImpl implements WorkPersonService {

	private static Logger logger = LoggerFactory.getLogger(WorkPersonServiceImpl.class);

	@Autowired
	private WorkPersonDao workPersonDao;
	
	@Autowired
	private WorkPersonKpiDao workPersonKpiDao;
	
	@Autowired
	private WorkPersonAttrDao workPersonAttrDao;
	
	@Autowired
	private FileLocationDao fileLocationDao;
	
	@Autowired
	private WorkPersonMappingFileDao workPersonMappingFileDao;
	
	@Autowired
	private ClassRoomProcessDao classRoomProcessDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		logger.info("WorkPersonServiceImpl.create");
		BuckWaResponse response = new BuckWaResponse();
		try {
			WorkPerson workPerson = (WorkPerson)request.get("workPerson");
			workPerson = workPersonDao.create(workPerson);
			
			//work person attribute
			if(workPerson.getWorkPersonAttrList()!=null&&workPerson.getWorkPersonAttrList().size()>0){
				for(WorkPersonAttr workPersonAttr : workPerson.getWorkPersonAttrList()){
					workPersonAttr.setWorkPersonId(workPerson.getWorkPersonId());
					workPersonAttr = workPersonAttrDao.create(workPersonAttr);
					if(workPersonAttr.getWorkPersonAttrId()!=null&&StringUtils.isNotBlank(workPersonAttr.getValue())){
						WorkPersonKpi workPersonKpi = new WorkPersonKpi();
						workPersonKpi.setKpiId(workPersonAttr.getKpiId());
						workPersonKpi.setWorkPersonId(workPersonAttr.getWorkPersonId());
						workPersonKpi.setFlagCalculate(workPersonAttr.getFlagCalculate());
						workPersonKpi.setCreateBy(workPersonAttr.getCreateBy());
						workPersonKpi.setWorkPersonAttrId(workPersonAttr.getWorkPersonAttrId());
						workPersonKpiDao.create(workPersonKpi);
					}
				}
			}
			
			//attach file
			if(workPerson.getFileLocationList()!=null&&workPerson.getFileLocationList().size()>0){
				for (FileLocation fileLocation : workPerson.getFileLocationList()) {
					
					// create folder
					String workPersonIdDicPath = new File(fileLocation.getFilePath()).getParentFile().getAbsolutePath() + File.separator + workPerson.getWorkPersonId();
					FileUtils.createDirectoryIfNotExist(workPersonIdDicPath);

					// copy file & delete original file
					File oldFile = new File(fileLocation.getFilePath() + fileLocation.getFileName() + fileLocation.getFileExtension());
					File newFile = new File(workPersonIdDicPath + File.separator + fileLocation.getFileName() + fileLocation.getFileExtension());
					FileUtils.copyFile(oldFile, newFile);
					FileUtils.forceDelete(oldFile);
					
					// add file location to db
					fileLocation.setFilePath(fileLocation.getFilePath().replace("temp", String.valueOf(workPerson.getWorkPersonId())));
					Long fileLocationId = fileLocationDao.create(fileLocation);
					
					
					if(workPerson.getWorkPersonAttrList()!=null&&workPerson.getWorkPersonAttrList().size()>0){
						for(WorkPersonAttr workPersonAttr : workPerson.getWorkPersonAttrList()){
							if (fileLocation.getLabel().equals(workPersonAttr.getLabel())) {
								fileLocation.setWorkPersonAttrId(workPersonAttr.getWorkPersonAttrId());
								break;
							}
						}
						// mapping work_person & file
						WorkPersonMappingFile mapping = new WorkPersonMappingFile();
						mapping.setWorkPersonId(workPerson.getWorkPersonId());
						mapping.setWorkPersonAttrId(fileLocation.getWorkPersonAttrId());
						mapping.setFileId(fileLocationId);
						workPersonMappingFileDao.create(mapping);
					}
				}
			}
			
			
			response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
		} catch (DuplicateKeyException dx) {
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse createClassRoom(BuckWaRequest request){
		logger.info("WorkPersonServiceImpl.create");
		BuckWaResponse response = new BuckWaResponse();
		try {
			
			ClassRoomProcess classRoomProcess = (ClassRoomProcess)request.get("classRoomProcess");
			classRoomProcessDao.update(classRoomProcess);
			
			List<WorkPerson> workPersonOldList = workPersonDao.getByClassRoomYearSemester("1",String.valueOf(classRoomProcess.getYearId()),String.valueOf(classRoomProcess.getSemesterId()));
			
			
			
			List<Teacher> teacherList = (List)request.get("teacherList");
			if(teacherList!=null&&teacherList.size()>0){
				for(Teacher teacher : teacherList){
					classRoomProcessDao.updateTeacher(teacher.getName(), teacher.getUserId());
				}
			}
			
			List<WorkPerson> workPersonList = (List)request.get("workPersonList");
			if(workPersonList!=null&&workPersonList.size()>0){
				if(workPersonOldList!=null && workPersonOldList.size()>0){
					for(WorkPerson workPersonOld : workPersonOldList){
						workPersonKpiDao.deleteByWorkPersonId(String.valueOf(workPersonOld.getWorkPersonId()));
						workPersonAttrDao.deleteByWorkPersonId(String.valueOf(workPersonOld.getWorkPersonId()));
						workPersonDao.delete(String.valueOf(workPersonOld.getWorkPersonId()));
					}
				}
				for(WorkPerson workPerson : workPersonList){
					workPerson = workPersonDao.create(workPerson);
					
					//work person attribute
					if(workPerson.getWorkPersonAttrList()!=null&&workPerson.getWorkPersonAttrList().size()>0){
						for(WorkPersonAttr workPersonAttr : workPerson.getWorkPersonAttrList()){
							workPersonAttr.setWorkPersonId(workPerson.getWorkPersonId());
							workPersonAttr = workPersonAttrDao.create(workPersonAttr);
							if(workPersonAttr.getWorkPersonAttrId()!=null&&StringUtils.isNotBlank(workPersonAttr.getValue())){
								WorkPersonKpi workPersonKpi = new WorkPersonKpi();
								workPersonKpi.setKpiId(workPersonAttr.getKpiId());
								workPersonKpi.setWorkPersonId(workPersonAttr.getWorkPersonId());
								workPersonKpi.setCreateBy(workPersonAttr.getCreateBy());
								workPersonKpi.setWorkPersonAttrId(workPersonAttr.getWorkPersonAttrId());
								workPersonKpiDao.create(workPersonKpi);
							}
						}
					}
				}
			}
			response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
		} catch (DuplicateKeyException dx) {
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse mappingUserClassRoom(BuckWaRequest request){
		logger.info("WorkPersonServiceImpl.create");
		BuckWaResponse response = new BuckWaResponse();
		try {
			
			ClassRoomProcess classRoomProcess = (ClassRoomProcess)request.get("classRoomProcess");
			
			List<WorkPerson> workPersonOldList = workPersonDao.getByClassRoomYearSemester("1",String.valueOf(classRoomProcess.getYearId()),String.valueOf(classRoomProcess.getSemesterId()),classRoomProcess.getUser1List());
			
			if(workPersonOldList!=null && workPersonOldList.size()>0){
				for(WorkPerson workPersonOld : workPersonOldList){
					workPersonKpiDao.deleteByWorkPersonId(String.valueOf(workPersonOld.getWorkPersonId()));
					workPersonAttrDao.deleteByWorkPersonId(String.valueOf(workPersonOld.getWorkPersonId()));
					workPersonDao.delete(String.valueOf(workPersonOld.getWorkPersonId()));
				}
			}
			
			List<Teacher> teacherList = (List)request.get("teacherList");
			if(teacherList!=null&&teacherList.size()>0){
				for(Teacher teacher : teacherList){
					classRoomProcessDao.updateTeacher(teacher.getName(), teacher.getUserId());
				}
			}
			
			List<WorkPerson> workPersonList = (List)request.get("workPersonList");
			if(workPersonList!=null&&workPersonList.size()>0){
				for(WorkPerson workPerson : workPersonList){
					workPerson = workPersonDao.create(workPerson);
					
					//work person attribute
					if(workPerson.getWorkPersonAttrList()!=null&&workPerson.getWorkPersonAttrList().size()>0){
						for(WorkPersonAttr workPersonAttr : workPerson.getWorkPersonAttrList()){
							workPersonAttr.setWorkPersonId(workPerson.getWorkPersonId());
							workPersonAttr = workPersonAttrDao.create(workPersonAttr);
							if(workPersonAttr.getWorkPersonAttrId()!=null&&StringUtils.isNotBlank(workPersonAttr.getValue())){
								WorkPersonKpi workPersonKpi = new WorkPersonKpi();
								workPersonKpi.setKpiId(workPersonAttr.getKpiId());
								workPersonKpi.setWorkPersonId(workPersonAttr.getWorkPersonId());
								workPersonKpi.setCreateBy(workPersonAttr.getCreateBy());
								workPersonKpi.setWorkPersonAttrId(workPersonAttr.getWorkPersonAttrId());
								workPersonKpiDao.create(workPersonKpi);
							}
						}
					}
				}
			}
			response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
		} catch (DuplicateKeyException dx) {
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		logger.info("WorkPersonServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workPersonId =  request.get("workPersonId")+"";			
			WorkPerson obj = workPersonDao.getById(workPersonId);						
			if(obj!=null){
				List<WorkPersonKpi> workPersonKpis = workPersonKpiDao.getByWorkPersonId(workPersonId);
				obj.setWorkPersonKpiList(workPersonKpis);
				
				List<FileLocation> fileLocationList = workPersonMappingFileDao.getFileLocationListByWorkPersonId(Long.valueOf(workPersonId));
				obj.setFileLocationList(fileLocationList);
				
				response.addResponse("workPerson",obj);
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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		logger.info("WorkPersonServiceImpl.update");
		BuckWaResponse response = new BuckWaResponse();
		try{	
			WorkPerson workPerson = (WorkPerson)request.get("workPerson");
			workPersonKpiDao.deleteByWorkPersonId(String.valueOf(workPerson.getWorkPersonId()));
			workPersonAttrDao.deleteByWorkPersonId(String.valueOf(workPerson.getWorkPersonId()));
			workPersonDao.update(workPerson);

			if(workPerson.getWorkPersonAttrList()!=null&&workPerson.getWorkPersonAttrList().size()>0){
				for(WorkPersonAttr workPersonAttr : workPerson.getWorkPersonAttrList()){
					workPersonAttr.setWorkPersonId(workPerson.getWorkPersonId());
					workPersonAttr = workPersonAttrDao.create(workPersonAttr);
					if(workPersonAttr.getWorkPersonAttrId()!=null&&StringUtils.isNotBlank(workPersonAttr.getValue())){
						WorkPersonKpi workPersonKpi = new WorkPersonKpi();
						workPersonKpi.setKpiId(workPersonAttr.getKpiId());
						workPersonKpi.setWorkPersonId(workPersonAttr.getWorkPersonId());
						workPersonKpi.setCreateBy(workPersonAttr.getCreateBy());
						workPersonKpi.setWorkPersonAttrId(workPersonAttr.getWorkPersonAttrId());
						workPersonKpi.setFlagCalculate(workPersonAttr.getFlagCalculate());
						workPersonKpiDao.create(workPersonKpi);
					}
				}
			}
			
			//attach file
			for (FileLocation fileLocation : workPerson.getFileLocationList()) {
				
				// create folder
				String workPersonIdDicPath = new File(fileLocation.getFilePath()).getParentFile().getAbsolutePath() + File.separator + workPerson.getWorkPersonId();
				FileUtils.createDirectoryIfNotExist(workPersonIdDicPath);
				
				WorkPersonMappingFile mapping = new WorkPersonMappingFile();
				if (null != fileLocation.getFileId()) {
					mapping.setWorkPersonId(workPerson.getWorkPersonId());
					mapping.setFileId(fileLocation.getFileId());
					workPersonMappingFileDao.delete(mapping);
				}
				
				// add file location to db
				Long fileLocationId = fileLocationDao.create(fileLocation);
				
				for(WorkPersonAttr workPersonAttr : workPerson.getWorkPersonAttrList()){
					if (fileLocation.getLabel().equals(workPersonAttr.getLabel())) {
						fileLocation.setWorkPersonAttrId(workPersonAttr.getWorkPersonAttrId());
						break;
					}
				}
				// mapping work_person & file
				mapping.setWorkPersonId(workPerson.getWorkPersonId());
				mapping.setWorkPersonAttrId(fileLocation.getWorkPersonAttrId());
				mapping.setFileId(fileLocationId);
				workPersonMappingFileDao.create(mapping);
			}
			
			response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse delete(BuckWaRequest request) {
		logger.info("WorkPersonServiceImpl.delete");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workPersonId = (String)request.get("workPersonId");
			workPersonKpiDao.deleteByWorkPersonId(workPersonId);
			workPersonAttrDao.deleteByWorkPersonId(workPersonId);
			workPersonDao.delete(workPersonId);
			response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS);
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
			logger.info("");
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = workPersonDao.getAllByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse checkNameAlready(BuckWaRequest request){
		logger.info("WorkPersonServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workPersonId =  request.get("workPersonId")+"";		
			String name =  request.get("name")+"";
			String userid =  request.get("userid")+"";
			boolean obj = workPersonDao.checkNameAlready(workPersonId,userid,name);						
			if(obj)
				response.setStatus(BuckWaConstants.FAIL);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getWorkPersonAttrById(BuckWaRequest request){
		logger.info("WorkPersonServiceImpl.getWorkPersonAttrById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workPersonId =  request.get("workPersonId")+"";			
			List<WorkPersonAttr> obj = workPersonAttrDao.getByWorkPersonId(workPersonId);	
			response.addResponse("WorkPersonAttrList",obj);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse getByWorkTemplate(BuckWaRequest request) {
		logger.info("WorkPersonServiceImpl.getByWorkTemplate");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			WorkPerson workPerson = (WorkPerson) request.get("workPerson");
			List<WorkPerson> workPersonList = workPersonDao.getAllByWorkTemplate(workPerson);			
			response.addResponse("workPersonList", workPersonList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}

	
}
