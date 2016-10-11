package com.buckwa.service.impl.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.HolidayDao;
import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.admin.HolidayCriteria;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.service.intf.admin.HolidayService;
import com.buckwa.util.BuckWaConstants;

@Service("holidayService") 
public class HolidayServiceImpl implements HolidayService {	
	
	private static Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);
	
	@Autowired
	private HolidayDao holidayDao;
 
	@Override
	public BuckWaResponse getAll() {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-getAll-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Holiday> holidayList = holidayDao.getAllHoliday();			
			response.addResponse("holidayList",holidayList);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
//		response.setSuccessCode("S001");
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-create-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{
			HolidayCriteria holidayCriteria = (HolidayCriteria)request.get("holidayCriteria");
			boolean isExist = false;
			
			if(holidayCriteria!=null
				&& holidayCriteria.getHolidayName().length()>1
				){
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				List<Date> listDuration = new ArrayList<Date>();
				
				if(holidayCriteria.getIsDurationDate().equalsIgnoreCase("true")
					&&holidayCriteria.getMinDate()!=null && holidayCriteria.getMinDate().length()>0
					&&holidayCriteria.getMaxDate()!=null && holidayCriteria.getMaxDate().length()>0){
					if(logger.isInfoEnabled()){
						logger.info("- duration Date -");
						logger.info("- minDate: "+holidayCriteria.getMinDate());
						logger.info("- maxDate: "+holidayCriteria.getMaxDate());
					}
					/* BEGIN: try from 
					 * http://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java
					 */
					Date  startDate = (Date)formatter.parse(holidayCriteria.getMinDate()); 
					Date  endDate = (Date)formatter.parse(holidayCriteria.getMaxDate());
					long interval = 24*1000 * 60 * 60; // 1 Day in millis
					long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
					long curTime = startDate.getTime();
					while (curTime <= endTime) {
						listDuration.add(new Date(curTime));
					    curTime += interval;
					}

					/* END: try */
				}else if(!holidayCriteria.getIsDurationDate().equalsIgnoreCase("true")
						&& holidayCriteria.getHolidayDate()!=null && holidayCriteria.getHolidayDate().length()>0){
					if(logger.isInfoEnabled()){
						logger.info("- singleDate: "+ holidayCriteria.getHolidayDate());
					}
					listDuration.add((Date)formatter.parse(holidayCriteria.getHolidayDate()));
				}else{
					throw new Exception("Something Abnormal!");
				}
				
				if(logger.isInfoEnabled()){
					for(int i=0;i<listDuration.size();i++){
					    Date lDate =(Date)listDuration.get(i);
					    String ds = formatter.format(lDate);    
					    logger.info(" Date is ..." + ds);
					}
				}
				
				for(int i=0; i<listDuration.size(); i++){
					Holiday holiday = new Holiday();
					
					holiday.setYearId(holidayCriteria.getYearId());
					holiday.setHolidayDate(formatter.format(listDuration.get(i)));
					holiday.setHolidayName(holidayCriteria.getHolidayName());
					holiday.setHolidayDesc(holidayCriteria.getHolidayDesc());
					
					isExist = holidayDao.isHolidayExist(holiday);

					if(isExist){
						response.setErrorCode("E002");	
						response.setStatus(BuckWaConstants.FAIL);
						break;
					}
				}
				
				if(!isExist){
					
					for(int i=0; i<listDuration.size(); i++){
						Holiday holiday = new Holiday();
						
						holiday.setYearId(holidayCriteria.getYearId());
						holiday.setHolidayDate(formatter.format(listDuration.get(i)));
						holiday.setHolidayName(holidayCriteria.getHolidayName());
						holiday.setHolidayDesc(holidayCriteria.getHolidayDesc());
						holiday.setEnable(holidayCriteria.isEnable());
						
						holidayDao.create(holiday);
					}
					
					response.setSuccessCode("S001");	
				}
				
			}else{
				response.setErrorCode("E017");
				response.setStatus(BuckWaConstants.FAIL);
			}
					
		}catch(DuplicateKeyException dx){
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			if(ex.getMessage().equals("Something Abnormal!")){
				response.setErrorCode("E017");	
			}else{
				response.setErrorCode("E001");	
			}
			response.setStatus(BuckWaConstants.FAIL);
					
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-update-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{	 
			Holiday holiday = (Holiday)request.get("holiday");
			
			response.setStatus(BuckWaConstants.FAIL);
			if(holiday.getHolidayName().length() <1){
				response.setErrorCode("E017");
			}else{
				Holiday oldHoliday = holidayDao.getHolidayById(holiday.getHolidayId());
				boolean isExist = holidayDao.isHolidayExist(holiday);
				boolean isSameYear = oldHoliday.getYearId().equals(holiday.getYearId()); 
				boolean isSameDate = oldHoliday.getHolidayDate().equals(holiday.getHolidayDate()); 
				boolean isSameDesc = oldHoliday.getHolidayDesc().equals(holiday.getHolidayDesc()); 
				boolean isSameName = oldHoliday.getHolidayName().equals(holiday.getHolidayName()); 
				boolean isSameEnable = oldHoliday.isEnable()==holiday.isEnable();
				
				boolean isDirty = !(isSameYear&&isSameDate&&isSameDesc&&isSameEnable&&isSameName);

				if(!isExist||isDirty){
					holidayDao.update(holiday);
					response.setSuccessCode("S002");
					response.setStatus(BuckWaConstants.SUCCESS);
				}else{
					response.setErrorCode("E002");
				}		
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
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-getByOffset-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{		
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = holidayDao.getAllHolidayByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
//		response.setSuccessCode("S001");
		return response;
	}
	
	@Override
	public BuckWaResponse getByOffsetYear(BuckWaRequest request) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-getByOffset-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{		
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = holidayDao.getAllHolidayByOffsetYear(pagingBean);	
			response.addResponse("pagingBean",returnBean);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
//		response.setSuccessCode("S001");
		return response;
	}


	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-getById-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String holidayId = (String)request.get("holidayId");			
			Holiday holiday = holidayDao.getHolidayById(holidayId);						
			response.addResponse("holiday",holiday);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
//		response.setSuccessCode("S001");
		return response;
	}
	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-deleteById-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id =  request.get("holidayId").toString();

			// Check Is using
			Holiday targetHoliday = holidayDao.getHolidayById(id);
			if(targetHoliday==null){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				holidayDao.deleteHolidayById(id);	
			}			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("D001");
		return response;
	}
	
	public BuckWaResponse getHolidayByYear(BuckWaRequest request){
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-getHolidayByYear-");
		}
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String holidayId =  request.get("holidayId").toString();
			List<Holiday> holidayList = holidayDao.getHolidayByYear(holidayId);	
			response.addResponse("holidayList",holidayList);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
}
