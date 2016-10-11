package com.buckwa.dao.impl.pbp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.PBPWorkTypeDao;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.ReportPbp;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckwaNumberUtil;
import com.buckwa.util.school.SchoolUtil;

@Repository("pBPWorkTypeDao")
public class PBPWorkTypeDaoImpl implements PBPWorkTypeDao {
	private static Logger logger = LoggerFactory.getLogger(PBPWorkTypeDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private GenerateCodeUtil  generateCodeUtil;
//	@Override
//	public PBPWorkTypeWrapper getByAcademicYear( String getByAcademicYear) {		 		
//		String sql =" select *  from pbp_work_type where academic_year ='"+getByAcademicYear+"'" ;  
//		
//		logger.info("  getByAcademicYear sql:"+sql);
//		
//		PBPWorkTypeWrapper pBPWorkTypeWrapper = new PBPWorkTypeWrapper();	 	
//		
//		try{
//		List<PBPWorkType> pBPWorkTypeList  = this.jdbcTemplate.query(sql,	new PBPWorkTypeMapper() );	
//		
//		
//
//		for(PBPWorkType tmp:pBPWorkTypeList){
//			String sqlSub =" select *  from pbp_work_type_sub  where work_type_id ="+tmp.getWorkTypeId(); 
//			logger.info(" sqlSub:"+sqlSub);
//			List<PBPWorkTypeSub> pBPWorkTypeSubList  =null;
//			
//			try{
//				pBPWorkTypeSubList = this.jdbcTemplate.query(sqlSub,	new PBPWorkTypeSubMapper() );	
//				
//				if(pBPWorkTypeSubList!=null&&pBPWorkTypeSubList.size()>0){
//					
//					for(PBPWorkTypeSub subTmp:pBPWorkTypeSubList){
//						
//						// Get KPI User Mapping belong to sub
//						
//					}
//					
//					
//					tmp.setpBPWorkTypeSubList(pBPWorkTypeSubList);
//				}else{
//					
//					String sqlMap =" select *  from academic_kpi_user_mapping  where work_type_code ="+tmp.getCode()+" and academic_year='"+tmp.getAcademicYear()+"'"; 
//					
//			 
//					List<AcademicKPIUserMapping>	academicKPIUserMappingList = this.jdbcTemplate.query(sqlMap,	new AcademicKPIUserMappingMapper() );	
//					
//					if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){
//						
//						for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){
//							
//							//AcademicKPIAttributeValueMapper
//							
//						}
//						
//					}
//					
//					tmp.setAcademicKPIUserMappingList(academicKPIUserMappingList);
//				}
//				
//				
//			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
//				ex.printStackTrace();
//			} 
//		}
//		
//		pBPWorkTypeWrapper.setpBPWorkTypeList(pBPWorkTypeList);
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return pBPWorkTypeWrapper;
//	}
	
	
	
	@Override
	public PBPWorkTypeWrapper getByAcademicYearFacultyCode( String getByAcademicYear,String facultyCode) {		 		
		String sql =" select *  from pbp_work_type where academic_year ='"+getByAcademicYear+"' and faculty_code='"+facultyCode+"' order by code" ;   
		logger.info("  getByAcademicYearAndFactulty sql:"+sql); 
		PBPWorkTypeWrapper pBPWorkTypeWrapper = new PBPWorkTypeWrapper();	 	 
		try{
		List<PBPWorkType> pBPWorkTypeList  = this.jdbcTemplate.query(sql,	new PBPWorkTypeMapper() );	 
		pBPWorkTypeWrapper.setpBPWorkTypeList(pBPWorkTypeList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return pBPWorkTypeWrapper;
	}

	@Override
	public List<RadarPlotReport> getRadarPlotPersonMark( String username,String academicYear,String round) {			
		List<RadarPlotReport> radarPlotList = new ArrayList();
		
		String sql =" select *  from report_person where academic_year ='"+academicYear+"' and round="+round+" and username='"+username+"'" ;   
		//String sql =" ";
		logger.info("  getRadarPlotPersonMark sql:"+sql); 
		  	 
		try{
			
			List<ReportPbp> reportPbpList  = this.jdbcTemplate.query(sql,	new ReportPbpMapper() );
			
			ReportPbp reportPbp  =null;
			if(reportPbpList==null||reportPbpList.size()==0){
				reportPbp = new ReportPbp();
				reportPbp.setMark_1("0.00");
				reportPbp.setE_mark_1("0.00");
				reportPbp.setWork_type_name1("0.00");
				
				reportPbp.setMark_2("0.00");
				reportPbp.setE_mark_2("0.00");
				reportPbp.setWork_type_name2("0.00");
				
				
				reportPbp.setMark_3("0.00");
				reportPbp.setE_mark_3("0.00");
				reportPbp.setWork_type_name3("0.00");
				
				
				reportPbp.setMark_4("0.00");
				reportPbp.setE_mark_4("0.00");
				reportPbp.setWork_type_name4("0.00");
				
				
				reportPbp.setMark_5("0.00");
				reportPbp.setE_mark_5("0.00");
				reportPbp.setWork_type_name5("0.00");
				
				reportPbp.setMark_total("0.00");
			}else{
				reportPbp = reportPbpList.get(0);
			}
			
			RadarPlotReport r1 = new RadarPlotReport();
			r1.setAxisName(reportPbp.getWork_type_name1());
			r1.setAxisValue(reportPbp.getMark_1());
			r1.setAxisName2(reportPbp.getWork_type_name1());
			r1.setAxisValue2(reportPbp.getE_mark_1());
			
			RadarPlotReport r2 = new RadarPlotReport();
			r2.setAxisName(reportPbp.getWork_type_name2());
			r2.setAxisValue(reportPbp.getMark_2());
			r2.setAxisName2(reportPbp.getWork_type_name2());
			r2.setAxisValue2(reportPbp.getE_mark_2());
			
			RadarPlotReport r3 = new RadarPlotReport();
			r3.setAxisName(reportPbp.getWork_type_name3());
			r3.setAxisValue(reportPbp.getMark_3());
			r3.setAxisName2(reportPbp.getWork_type_name3());
			r3.setAxisValue2(reportPbp.getE_mark_3());
			
			RadarPlotReport r4 = new RadarPlotReport();
			r4.setAxisName(reportPbp.getWork_type_name4());
			r4.setAxisValue(reportPbp.getMark_4());
			r4.setAxisName2(reportPbp.getWork_type_name4());
			r4.setAxisValue2(reportPbp.getE_mark_4());
			
			RadarPlotReport r5 = new RadarPlotReport();
			r5.setAxisName(reportPbp.getWork_type_name5());
			r5.setAxisValue(reportPbp.getMark_5());
			r5.setAxisName2(reportPbp.getWork_type_name5());
			r5.setAxisValue2(reportPbp.getE_mark_5());
			
			radarPlotList.add(r1);
			radarPlotList.add(r2);
			radarPlotList.add(r3);
			radarPlotList.add(r4);
			radarPlotList.add(r5);
			 
		
			 
		}catch(Exception ex){
			
			 
			
			ex.printStackTrace();
		}
		return radarPlotList;
	}
	
	@Override
	public List<RadarPlotReport> getRadarPlotPersonMarkByYear( String username,String academicYear) {			
		List<RadarPlotReport> radarPlotList = new ArrayList();
		
		String sql =" select *  from report_person where academic_year ='"+academicYear+"'   and username='"+username+"' and round is not null " ;   
		//String sql =" ";
		logger.info("  getRadarPlotPersonMark sql:"+sql); 
		  	 
		try{
			
			List<ReportPbp> reportPbpList  = this.jdbcTemplate.query(sql,	new ReportPbpMapper() );	
			
			
			if(reportPbpList!=null&&reportPbpList.size()>0){
				
				String worktypeName1 ="";
				String worktypeName2 ="";
				String worktypeName3 ="";
				String worktypeName4 ="";
				String worktypeName5 ="";
				
				BigDecimal totalMark1 = new BigDecimal(0.00);
				BigDecimal totalMark1_E = new BigDecimal(0.00);
				
				BigDecimal totalMark2 = new BigDecimal(0.00);
				BigDecimal totalMark2_E = new BigDecimal(0.00);
				
				BigDecimal totalMark3 = new BigDecimal(0.00);
				BigDecimal totalMark3_E = new BigDecimal(0.00);
				
				BigDecimal totalMark4 = new BigDecimal(0.00);
				BigDecimal totalMark4_E = new BigDecimal(0.00);
				
				BigDecimal totalMark5 = new BigDecimal(0.00);
				BigDecimal totalMark5_E = new BigDecimal(0.00);
				
		 
				
				
				for(ReportPbp tmp:reportPbpList){
					worktypeName1 = tmp.getWork_type_name1();
					worktypeName2 = tmp.getWork_type_name2();
					worktypeName3 = tmp.getWork_type_name3();
					worktypeName4 = tmp.getWork_type_name4();
					worktypeName5 = tmp.getWork_type_name5();
					
					
					totalMark1 = totalMark1.add(new BigDecimal(tmp.getMark_1()));
					totalMark2 = totalMark2.add(new BigDecimal(tmp.getMark_2()));
					totalMark3 = totalMark3.add(new BigDecimal(tmp.getMark_3()));
					totalMark4 = totalMark4.add(new BigDecimal(tmp.getMark_4()));
					totalMark5 = totalMark5.add(new BigDecimal(tmp.getMark_5()));
					
					
					
					totalMark1_E = totalMark1_E.add(new BigDecimal(tmp.getE_mark_1()));
					totalMark2_E = totalMark2_E.add(new BigDecimal(tmp.getE_mark_2()));
					totalMark3_E = totalMark3_E.add(new BigDecimal(tmp.getE_mark_3()));
					totalMark4_E = totalMark4_E.add(new BigDecimal(tmp.getE_mark_4()));
					totalMark5_E = totalMark5_E.add(new BigDecimal(tmp.getE_mark_5()));
					
					
				}
				
				 
				
				RadarPlotReport r1 = new RadarPlotReport();
				r1.setAxisName(worktypeName1);
				r1.setAxisValue(totalMark1+"");
				r1.setAxisName2(worktypeName1);
				r1.setAxisValue2(totalMark1_E+"");
				
				RadarPlotReport r2 = new RadarPlotReport();
				r2.setAxisName(worktypeName2);
				r2.setAxisValue(totalMark2+"");
				r2.setAxisName2(worktypeName2);
				r2.setAxisValue2(totalMark2_E+"");
				
				RadarPlotReport r3 = new RadarPlotReport();
				r3.setAxisName(worktypeName3);
				r3.setAxisValue(totalMark3+"");
				r3.setAxisName2(worktypeName3);
				r3.setAxisValue2(totalMark3_E+"");
				
				RadarPlotReport r4 = new RadarPlotReport();
				r4.setAxisName(worktypeName4);
				r4.setAxisValue(totalMark4+"");
				r4.setAxisName2(worktypeName4);
				r4.setAxisValue2(totalMark4_E+"");
				
				RadarPlotReport r5 = new RadarPlotReport();
				r5.setAxisName(worktypeName5);
				r5.setAxisValue(totalMark5+"");
				r5.setAxisName2(worktypeName5);
				r5.setAxisValue2(totalMark5_E+"");
				
				radarPlotList.add(r1);
				radarPlotList.add(r2);
				radarPlotList.add(r3);
				radarPlotList.add(r4);
				radarPlotList.add(r5);				
				
				
			}
			
			
			

			 
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return radarPlotList;
	}
	
	 
	private PBPWorkTypeWrapper  getExcistPersonMark( String username,String academicYear,String round) {	
		
		PBPWorkTypeWrapper pbpWorkTypeWrapper = new PBPWorkTypeWrapper();
		pbpWorkTypeWrapper.setUserName(username);
		pbpWorkTypeWrapper.setAcademicYear(academicYear);
		pbpWorkTypeWrapper.setRound(round);
		List<RadarPlotReport> radarPlotList = new ArrayList();
		
		String sql =" select *  from report_person where academic_year ='"+academicYear+"' and round="+round+" and username='"+username+"'" ;   
		//String sql =" ";
		logger.info("  getRadarPlotPersonMark sql:"+sql); 
		  	 
		try{
			
			ReportPbp reportPbp  = this.jdbcTemplate.queryForObject(sql,	new ReportPbpMapper() );	
			
			RadarPlotReport r1 = new RadarPlotReport();
			r1.setAxisName(reportPbp.getWork_type_name1());
			r1.setAxisValue(reportPbp.getMark_1());
			r1.setAxisName2(reportPbp.getWork_type_name1());
			r1.setAxisValue2(reportPbp.getE_mark_1());
			
			RadarPlotReport r2 = new RadarPlotReport();
			r2.setAxisName(reportPbp.getWork_type_name2());
			r2.setAxisValue(reportPbp.getMark_2());
			r2.setAxisName2(reportPbp.getWork_type_name2());
			r2.setAxisValue2(reportPbp.getE_mark_2());
			
			RadarPlotReport r3 = new RadarPlotReport();
			r3.setAxisName(reportPbp.getWork_type_name3());
			r3.setAxisValue(reportPbp.getMark_3());
			r3.setAxisName2(reportPbp.getWork_type_name3());
			r3.setAxisValue2(reportPbp.getE_mark_3());
			
			RadarPlotReport r4 = new RadarPlotReport();
			r4.setAxisName(reportPbp.getWork_type_name4());
			r4.setAxisValue(reportPbp.getMark_4());
			r4.setAxisName2(reportPbp.getWork_type_name4());
			r4.setAxisValue2(reportPbp.getE_mark_4());
			
			RadarPlotReport r5 = new RadarPlotReport();
			r5.setAxisName(reportPbp.getWork_type_name5());
			r5.setAxisValue(reportPbp.getMark_5());
			r5.setAxisName2(reportPbp.getWork_type_name5());
			r5.setAxisValue2(reportPbp.getE_mark_5());
			
			radarPlotList.add(r1);
			radarPlotList.add(r2);
			radarPlotList.add(r3);
			radarPlotList.add(r4);
			radarPlotList.add(r5);
			
			pbpWorkTypeWrapper.setTotalMark(new BigDecimal(reportPbp.getMark_total()));
			pbpWorkTypeWrapper.setTotalMark_E(new BigDecimal(reportPbp.getE_mark_total()));
			
			pbpWorkTypeWrapper.setRadarPlotReportList(radarPlotList);
			 
			pbpWorkTypeWrapper.setIsFirstLogin("N");
		}catch(Exception ex){
			
			pbpWorkTypeWrapper.setIsFirstLogin("Y");
			
			
			ex.printStackTrace();
		}
		return pbpWorkTypeWrapper;
	}
	
	
	@Override
	public List<RadarPlotReport> getRadarPlotPersonMarkE( String username,String academicYear,String round) {			
		List<RadarPlotReport> radarPlotList = new ArrayList();
		
		String sql =" select *  from report_person where academic_year ='"+academicYear+"' and round="+round+" and username='"+username+"'" ;   
		//String sql =" ";
		logger.info("  getRadarPlotPersonMark sql:"+sql); 
		  	 
		try{
			
			ReportPbp reportPbp  = this.jdbcTemplate.queryForObject(sql,	new ReportPbpMapper() );	
			
			RadarPlotReport r1 = new RadarPlotReport();
			r1.setAxisName(reportPbp.getWork_type_name1());
			r1.setAxisValue(reportPbp.getE_mark_1());
			
			RadarPlotReport r2 = new RadarPlotReport();
			r2.setAxisName(reportPbp.getWork_type_name2());
			r2.setAxisValue(reportPbp.getE_mark_2());
			
			RadarPlotReport r3 = new RadarPlotReport();
			r3.setAxisName(reportPbp.getWork_type_name3());
			r3.setAxisValue(reportPbp.getE_mark_3());
			
			RadarPlotReport r4 = new RadarPlotReport();
			r4.setAxisName(reportPbp.getWork_type_name4());
			r4.setAxisValue(reportPbp.getE_mark_4());
			
			RadarPlotReport r5 = new RadarPlotReport();
			r5.setAxisName(reportPbp.getWork_type_name5());
			r5.setAxisValue(reportPbp.getE_mark_5());
			
			radarPlotList.add(r1);
			radarPlotList.add(r2);
			radarPlotList.add(r3);
			radarPlotList.add(r4);
			radarPlotList.add(r5);
			 
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return radarPlotList;
	}
	
	
		
	
	@Override
	public PBPWorkTypeWrapper getExsistCalculateByAcademicYear( String academicYear,String userName,String round,String employeeType,String facultyCode) {
		logger.info("  ########### Start Call  userName :"+userName+" academicYear:"+academicYear+" round:"+round+" employeeType:"+employeeType+" facultyCode:"+facultyCode);
		PBPWorkTypeWrapper pbpWorkTypeWrapper = new PBPWorkTypeWrapper();
		
		pbpWorkTypeWrapper =getExcistPersonMark(userName, academicYear, round);
	 
		
		
		return pbpWorkTypeWrapper;
	}
	
	@Override
	public String  getCurretnEvalulate( String academicYear,String employeeTypeNo ) {
		logger.info("  ########### academicYear :"+academicYear +"   employeeTypeNO:"+employeeTypeNo );		
		
		String returnEvalRound ="1";
		
		
		String evalType = "1";
	 
		if(employeeTypeNo.equalsIgnoreCase("2")){
			evalType ="2";
	 
		} 
 
		String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+evalType+"'"   ;  
 
		 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
		
 
		 long startTime =0l;
		 long endTime =0l;
 
 
				 startTime = academicYearEvaluateRound.getRound1StartDate().getTime(); 
				 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
	 
	 
				// startTime2 = academicYearEvaluateRound.getRound2StartDate().getTime(); 
				// endTime2 = academicYearEvaluateRound.getRound2EndDate().getTime();
			 
				 Long currentTime = System.currentTimeMillis();
				 if(currentTime>=startTime&&currentTime<endTime){
					 returnEvalRound ="1";
				 }else{
					 returnEvalRound ="2";
				 }
		 
 
	 
		return returnEvalRound;
	} 
	
	public PBPWorkTypeWrapper getCalculateByAcademicYear( String academicYear,String userName,String round,String employeeTypeNo,String facultyCode) {
		logger.info("  ###########  :"+userName +"   employeeTypeNO:"+employeeTypeNo );		
		String evalType = "1";
		
		//if(employeeType.equalsIgnoreCase("ข้าราชการ")||employeeType.equalsIgnoreCase("2")){
		if(employeeTypeNo.equalsIgnoreCase("2")){
			evalType ="2";
	 
		} 
		
		// ######## Start academic_year, academic_evaluate_round
		String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+evalType+"'"   ;  
		//logger.info(" sqlRound:"+sqlRound);
		 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
		
		// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
		 
		 long startTime =0l;
		 long endTime =0l;
		 
		 Timestamp startTimeStamp = null;
		 Timestamp endTimeStamp = null;
		 
		 //if(employeeType.equalsIgnoreCase("ข้าราชการ")||employeeType.equalsIgnoreCase("2")){
			if(employeeTypeNo.equalsIgnoreCase("1")){
			 if("1".equalsIgnoreCase(round)){
				 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
				 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
				 
				 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
				 endTimeStamp = academicYearEvaluateRound.getRound1EndDate();
			 }else{
				 startTime = academicYearEvaluateRound.getRound2StartDate().getTime();
				 startTimeStamp = academicYearEvaluateRound.getRound2StartDate();
				 
				 endTime = academicYearEvaluateRound.getRound2EndDate().getTime();
				 endTimeStamp = academicYearEvaluateRound.getRound2EndDate();
			 }
			 
		 }else{
			 
			 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
			 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
			 
			 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
			 endTimeStamp = academicYearEvaluateRound.getRound1EndDate(); 
			 
		 }
				 
			 logger.info(" Calculate Date Range :"+startTimeStamp+" - "+endTimeStamp);
		 
		 
		
		String sql =" select *  from pbp_work_type where academic_year ='"+academicYear+"' and faculty_code='"+facultyCode+"'" ;  
		
		//logger.info(" ### [1] pbp_work_type #### getByAcademicYear sql:"+sql);
		
		PBPWorkTypeWrapper pBPWorkTypeWrapper = new PBPWorkTypeWrapper();	 	
		//String academicYear,String userName,String round,String employeeType,String facultyCode
		
		pBPWorkTypeWrapper.setAcademicYear(academicYear);
		pBPWorkTypeWrapper.setUserName(userName);
		pBPWorkTypeWrapper.setRound(round);
		pBPWorkTypeWrapper.setEmployeeType(employeeTypeNo);
		pBPWorkTypeWrapper.setFacultyCode(facultyCode);
		
		pBPWorkTypeWrapper.setStartRoundDate(new Date(startTimeStamp.getTime()));
		pBPWorkTypeWrapper.setEndRoundDate(new Date(endTimeStamp.getTime()));
		
		try{
			
		///**### [1] pbp_work_type ----*/	
		List<PBPWorkType> pBPWorkTypeList  = this.jdbcTemplate.query(sql,	new PBPWorkTypeMapper() );	 
		for(PBPWorkType tmp:pBPWorkTypeList){

			try{

					
					String sqlMap =" select *  from academic_kpi_user_mapping  where work_type_code ="+tmp.getCode()+" " +
							" and academic_year='"+tmp.getAcademicYear()+"' and user_name='"+userName+"' and create_date BETWEEN '"+startTimeStamp+"' AND '"+endTimeStamp+"'"; 
					logger.info("### [2] academic_kpi_user_mapping ###  sqlMap:"+sqlMap);
					List<AcademicKPIUserMapping> academicKPIUserMappingList = this.jdbcTemplate.query(sqlMap,	new AcademicKPIUserMappingMapper() );	
					
					if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){
						
						//logger.info(" Found academic_kpi_user_mapping for worktype code:"+tmp.getCode()+"  username :"+userName+" academicYear:"+academicYear+" list below ");
						for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){
							
							try{
								
								String sqlkpi =" select *  from academic_kpi where academic_kpi_id ="+mappingTmp.getAcademicKPIId() ; 
								//logger.info(" ### sqlkpi:"+sqlkpi);
								 AcademicKPI  academicKPI  = this.jdbcTemplate.queryForObject(sqlkpi,	new AcademicKPIMapper() );
								 mappingTmp.setAcademicKPI(academicKPI);
								 
								 	
							 	//logger.info(" ### [4] academic_kpi_attribute_value --####");
								String sqlAttributeValue =" select *  from academic_kpi_attribute_value where kpi_user_mapping_id ="+mappingTmp.getKpiUserMappingId() ; 
								//logger.info(" sqlAttributeValue:"+sqlAttributeValue); 
								List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
								try{
									academicKPIAttributeValueList = this.jdbcTemplate.query(sqlAttributeValue,	new AcademicKPIAttributeValueMapper() );
									//logger.info(" Found Attribute list for Usermaping id :"+mappingTmp.getKpiUserMappingId()+" size:"+ academicKPIAttributeValueList.size());
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 
								mappingTmp.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
								 
								//logger.info(" ### [5] academic_kpi_attribute --####");	
								String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+mappingTmp.getAcademicKPIId()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
								List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
								try{
									academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 									
								
								for(AcademicKPIAttributeValue attributeValueTmp: academicKPIAttributeValueList){
									//logger.info("  Start set is calculate  attributeName  :"+attributeValueTmp.getName() ); 
									for(AcademicKPIAttribute attributeTmp:academicKPIAttributeList){
										String isCalculate = attributeTmp.getIsCalculate();
										//String isValidateNumber= attributeTmp.getIsCalculate();
										String attributeName = attributeTmp.getName();
										
										if(attributeName!=null){
											String attributeValueName = attributeValueTmp.getName();
											if(attributeName.equalsIgnoreCase(attributeValueName)){
												if("Y".equalsIgnoreCase(isCalculate)){
													attributeValueTmp.setIsCalculate("Y");
												}
											}
										}else{
											logger.info(" Found Attribute Name null");
										}
									}
								}
								mappingTmp.setAcademicKPIAttributeList(academicKPIAttributeList);
								 
							}catch (org.springframework.dao.EmptyResultDataAccessException ex){
								ex.printStackTrace();
							} 
							
							//AcademicKPIAttributeValueMapper
							
						}
						
					}
					
					tmp.setAcademicKPIUserMappingList(academicKPIUserMappingList);
			//	} 
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
		}
		
		
		// Callculate 
		
		logger.info("  ##########  ##########   ##########  Start Calculate   ##########  ##########  ##########" );
	 

		BigDecimal totalMark = new BigDecimal(0.00);
		BigDecimal totalMarkCompareBase = new BigDecimal(0.00);
		BigDecimal totalPercentMark = new BigDecimal(0.00);
		BigDecimal totalPercentMarkComareBase = new BigDecimal(0.00);
		
		BigDecimal totalMark_E = new BigDecimal(0.00);
		BigDecimal totalMarkCompareBase_E = new BigDecimal(0.00);
		BigDecimal totalPercentMark_E = new BigDecimal(0.00);
		BigDecimal totalPercentMarkComareBase_E = new BigDecimal(0.00);
		
		for(PBPWorkType tmp:pBPWorkTypeList){
			BigDecimal maxMark =  tmp.getMaxHour();
			BigDecimal limitBase = tmp.getLimitBase();
			
			BigDecimal minHour =tmp.getMinHour();
			BigDecimal maxHour =tmp.getMaxHour();
			
			BigDecimal totalInWorkType = new BigDecimal(0.00);
			BigDecimal totalInWorkType_E = new BigDecimal(0.00);
			
			List<AcademicKPIUserMapping> academicKPIUserMappingList =  tmp.getAcademicKPIUserMappingList();
			if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){	
				
				for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){					
					BigDecimal totalMappingTmp = new BigDecimal(0.0);
					BigDecimal totalMappingTmp_E = new BigDecimal(0.0);
					//logger.info(" Start With Total Mark:"+totalMappingTmp);
					AcademicKPI kpiTmp = mappingTmp.getAcademicKPI();					
					String mappingStatus = mappingTmp.getStatus();		
					
					//logger.info(" Start KPIMapping ID:"+mappingTmp.getKpiUserMappingId() );
					
					if("APPROVED".equalsIgnoreCase(mappingStatus)){ 	
						String calResultStr =""; 
						List<AcademicKPIAttributeValue>  academicKPIAttributeValueList  =mappingTmp.getAcademicKPIAttributeValueList();						
						//logger.info("   ########## Mapping id:"+mappingTmp.getKpiUserMappingId()+" Statatus  Approve , So Start Calculate " );
						//logger.info(" KPI Mark:"+kpiTmp.getMark()+" Multiply Value:"+kpiTmp.getMultiplyValue());    
						BigDecimal multiplyValueBig = new BigDecimal(0.00);						
						try{
							multiplyValueBig = new BigDecimal(kpiTmp.getMultiplyValue());
							multiplyValueBig.setScale(2);
						}catch(Exception ex){
							multiplyValueBig = new BigDecimal(1).setScale(2);
						}
						
						calResultStr= " ("+multiplyValueBig+" ตัวคุณ )"+" X ("+kpiTmp.getMark()+" คะแนน KPI )";						
						if(kpiTmp.getMultiplyValue()!=null&&multiplyValueBig.doubleValue()>0){							
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()).multiply(multiplyValueBig).setScale(2); 							 
							//logger.info("  Found multiply value :"+kpiTmp.getMultiplyValue()+"  So multiply by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}else{									 	
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()); 							
							//logger.info("  No multiply value :"+kpiTmp.getMultiplyValue()+"  So Add by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}					
					//	logger.info("  Start is calculate attribute size :"+academicKPIAttributeValueList.size());
								for(AcademicKPIAttributeValue attributeValueTmp: academicKPIAttributeValueList){ 
										String isCalculate = attributeValueTmp.getIsCalculate(); 
										String attributeValueName = attributeValueTmp.getName();										
										if("Y".equalsIgnoreCase(isCalculate)){	 
										String attributeValueValue = attributeValueTmp.getValue(); 
										BigDecimal tmpBeforCall = totalMappingTmp;
										if(attributeValueName.indexOf("สัดส่วน(%)")!=-1){ 
											logger.info(" สัดส่วน"+attributeValueValue);
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2,RoundingMode.HALF_UP)).divide(new BigDecimal(100));											
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +"  So :"+tmpBeforCall+"*"+attributeValueValue+"/100 = "+totalMappingTmp);
											calResultStr=calResultStr+ " X (" +attributeValueName+" "+attributeValueValue+")";
										}else{ 
											logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +" So :"+tmpBeforCall+"*"+attributeValueValue+"  = "+totalMappingTmp);
 											try{
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2,RoundingMode.HALF_UP));											
											}catch(Exception ex){
												
												//logger.info("  Exception #############   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +" So :"+tmpBeforCall+"*"+attributeValueValue+"  = "+totalMappingTmp);
												ex.printStackTrace();
											}
											calResultStr=calResultStr+ " X (" +attributeValueValue+" "+attributeValueName+")";
										}
 
								}
								
								
							}
								//logger.info("  Call Str: "+calResultStr);
								logger.info(" ID: "+mappingTmp.getKpiUserMappingId()+":APPROVED --> "+calResultStr+" = "+totalMappingTmp);
								mappingTmp.setCalResultStr(calResultStr+" = "+totalMappingTmp.setScale(2,RoundingMode.HALF_UP) );	
								updateCallResultStr(mappingTmp);
								
								//BigDecimal totalPercentInMapping = totalMappingTmp.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
								mappingTmp.setTotalInMapping(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));   
								//mappingTmp.setTotalPercentInMapping(totalPercentInMapping.setScale(2));
								totalInWorkType =totalInWorkType.add(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
								totalInWorkType_E =totalInWorkType_E.add(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
						
						//logger.info("   Mark after other Attribute :"+totalMappingTmp);
					} else{
						//logger.info("   ##########  Mapping id:"+mappingTmp.getKpiUserMappingId()+"  Statatus Not Approve , So Calculate for Temporary Mark" );
						
 						
						
						String calResultStr =""; 
						List<AcademicKPIAttributeValue>  academicKPIAttributeValueList  =mappingTmp.getAcademicKPIAttributeValueList();
						
						//logger.info("   ########## Mapping id:"+mappingTmp.getKpiUserMappingId()+" Statatus  Approve , So Start Calculate " );
						//logger.info(" KPI Mark:"+kpiTmp.getMark()+" Multiply Value:"+kpiTmp.getMultiplyValue());    
						BigDecimal multiplyValueBig = new BigDecimal(0.00);						
						try{
							multiplyValueBig = new BigDecimal(kpiTmp.getMultiplyValue());
							multiplyValueBig.setScale(2);
						}catch(Exception ex){
							multiplyValueBig = new BigDecimal(1).setScale(2);
						}
						
						calResultStr= " ("+multiplyValueBig+" ตัวคุณ )"+" X ("+kpiTmp.getMark()+" คะแนน KPI )";						
						if(kpiTmp.getMultiplyValue()!=null&&multiplyValueBig.doubleValue()>0){							
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()).multiply(multiplyValueBig).setScale(2,RoundingMode.HALF_UP); 
						//	logger.info("  Found multiply value :"+kpiTmp.getMultiplyValue()+"  So multiply by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}else{									 	
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()); 
						//	logger.info("  No multiply value :"+kpiTmp.getMultiplyValue()+"  So Add by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}					
						//logger.info("  Start is calculate attribute size :"+academicKPIAttributeValueList.size());
								for(AcademicKPIAttributeValue attributeValueTmp: academicKPIAttributeValueList){ 
										String isCalculate = attributeValueTmp.getIsCalculate(); 
										String attributeValueName = attributeValueTmp.getName();										
										if("Y".equalsIgnoreCase(isCalculate)){	 
										String attributeValueValue = attributeValueTmp.getValue(); 
										BigDecimal tmpBeforCall = totalMappingTmp;
										if(attributeValueName.indexOf("สัดส่วน(%)")!=-1){ 
										//	logger.info(" สัดส่วน"+attributeValueValue);
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2,RoundingMode.HALF_UP)).divide(new BigDecimal(100));
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +"  So :"+tmpBeforCall+"*"+attributeValueValue+"/100 = "+totalMappingTmp);
											calResultStr=calResultStr+ " X (" +attributeValueName+" "+attributeValueValue+")";
										}else{ 
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +" So :"+tmpBeforCall+"*"+attributeValueValue+"  = "+totalMappingTmp);
 											try{
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue.trim()).setScale(2,RoundingMode.HALF_UP));
											}catch(Exception ex){
												System.out.println(" Number Formate Exception attributeName:"+attributeValueName+" value:"+attributeValueValue);
												ex.printStackTrace();
											}
											calResultStr=calResultStr+ " X (" +attributeValueValue+" "+attributeValueName+")";
										}
 
								}
								
								
							}
								//logger.info("  Call Str: "+calResultStr);
								logger.info(" ID: "+mappingTmp.getKpiUserMappingId()+":NOT APPROVED --> "+calResultStr+" = "+totalMappingTmp);
								mappingTmp.setCalResultStr(calResultStr+" = "+totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
								updateCallResultStr(mappingTmp);
														
								//BigDecimal totalPercentInMapping = totalMappingTmp.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
								mappingTmp.setTotalInMapping(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));   
								//mappingTmp.setTotalPercentInMapping(totalPercentInMapping.setScale(2));
								totalInWorkType_E =totalInWorkType_E.add(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));
						
						
						
					}
					
				 
					//BigDecimal totalPercentInMapping = totalMappingTmp.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
					//mappingTmp.setTotalInMapping(totalMappingTmp.setScale(2));   
					//mappingTmp.setTotalPercentInMapping(totalPercentInMapping.setScale(2));
					//totalInWorkType =totalInWorkType.add(totalMappingTmp.setScale(2));
				} 				
			} 
			
		//	BigDecimal totalInPercentWorkType = totalInWorkType.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
			//logger.info(" getMarkDepartmetn totalInPercentWorkType :"+totalInWorkType );
			tmp.setTotalInWorkType(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
			tmp.setTotalInWorkType_E(totalInWorkType_E.setScale(2,RoundingMode.HALF_UP));
			
			logger.info(tmp.getName()+"   "+totalInWorkType  );
			//logger.info(" ## totalInWorkType name:"+tmp.getName()+"   "+totalInWorkType  );
			
			//tmp.setTotalInPercentWorkType(totalInPercentWorkType.setScale(2));
			if(totalInWorkType.compareTo(minHour)==-1){
			//	tmp.setTotalInPercentCompareBaseWorkType(totalInPercentWorkType.setScale(2));
				tmp.setTotalInWorkTypeCompareBase(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
				tmp.setCompareBaseStatus("UNDER");
			}else  if(totalInWorkType.compareTo(maxHour)==1){
				//tmp.setTotalInPercentCompareBaseWorkType ( tmp.getMaxPercent() .setScale(2));
				tmp.setTotalInWorkTypeCompareBase( tmp.getMinHour() .setScale(2,RoundingMode.HALF_UP));
				tmp.setCompareBaseStatus("OVER");
			}else {
			//	tmp.setTotalInPercentCompareBaseWorkType(totalInPercentWorkType.setScale(2));
				tmp.setTotalInWorkTypeCompareBase(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
				tmp.setCompareBaseStatus("NORMAL");
			}
			
			tmp.setTotalInWorkTypeCompareBase_E(totalInWorkType_E.setScale(2));
			
			totalPercentMark =totalPercentMark.add(tmp.getTotalInPercentWorkType().setScale(2,RoundingMode.HALF_UP));
			totalPercentMarkComareBase =totalPercentMarkComareBase.add(tmp.getTotalInPercentCompareBaseWorkType().setScale(2,RoundingMode.HALF_UP));
			totalMark = totalMark.add(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
			totalMarkCompareBase =totalMarkCompareBase.add(tmp.getTotalInWorkTypeCompareBase().setScale(2,RoundingMode.HALF_UP));
			
			
			totalPercentMark_E =totalPercentMark_E.add(tmp.getTotalInPercentWorkType_E().setScale(2));
			totalPercentMarkComareBase_E =totalPercentMarkComareBase_E.add(tmp.getTotalInPercentCompareBaseWorkType_E().setScale(2,RoundingMode.HALF_UP));
			totalMark_E = totalMark_E.add(totalInWorkType_E.setScale(2,RoundingMode.HALF_UP));
			totalMarkCompareBase_E =totalMarkCompareBase_E.add(tmp.getTotalInWorkTypeCompareBase_E().setScale(2,RoundingMode.HALF_UP));
			
			//logger.info(" total Mark:"+totalMark);
		} 
		
			//BigDecimal totalPercentMark = totalMark.multiply(new BigDecimal(100)).divide(maxMark,2,RoundingMode.HALF_UP); 
			logger.info(" totalPercentMark  :"+totalPercentMark);
			pBPWorkTypeWrapper.setTotalMark(totalMark.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalMarkCompareBase(totalMarkCompareBase);
			pBPWorkTypeWrapper.setTotalPercentMark(totalPercentMark.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalPercentMarkCompareBase(totalPercentMarkComareBase.setScale(2,RoundingMode.HALF_UP));
			
			
			pBPWorkTypeWrapper.setTotalMark_E(totalMark_E.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalMarkCompareBase_E(totalMarkCompareBase_E);
			pBPWorkTypeWrapper.setTotalPercentMark_E(totalPercentMark_E.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalPercentMarkCompareBase_E(totalPercentMarkComareBase_E.setScale(2,RoundingMode.HALF_UP));
			
			// 1- Because mark_rank keep 0,1
			// int employeeTypeGetSalary = Integer.parseInt(employeeType) -1;
			//pBPWorkTypeWrapper.setIncreaseSalaryRate(getIncreaseSalaryRate(totalPercentMarkComareBase,academicYear,employeeTypeGetSalary+"",round));
			pBPWorkTypeWrapper.setpBPWorkTypeList(pBPWorkTypeList);
			
			
			updateReportPerson(pBPWorkTypeWrapper);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return pBPWorkTypeWrapper;
	} 
	
	
	
	
	
	@Override
	public PBPWorkTypeWrapper getCalculateByAcademicYearAllYear( String academicYear,String userName ,String employeeTypeNo,String facultyCode) {
		logger.info("  ###########  :"+userName +"   employeeTypeNO:"+employeeTypeNo );
		
		
		// Get Start ,End Date 
		
		String evalType = "1";
		
		//if(employeeType.equalsIgnoreCase("ข้าราชการ")||employeeType.equalsIgnoreCase("2")){
		if(employeeTypeNo.equalsIgnoreCase("2")){
			evalType ="2";
	 
		} 
		
		// ######## Start academic_year, academic_evaluate_round
		String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+evalType+"'"   ;  
		//logger.info(" sqlRound:"+sqlRound);
		 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
		
		 //logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
		 
		 long startTime =0l;
		 long endTime =0l;
		 
		 Timestamp startTimeStamp = null;
		 Timestamp endTimeStamp = null;
		 
		 //if(employeeType.equalsIgnoreCase("ข้าราชการ")||employeeType.equalsIgnoreCase("2")){
			if(employeeTypeNo.equalsIgnoreCase("1")){
			 
				 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
				 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
				 
		 
				 endTime = academicYearEvaluateRound.getRound2EndDate().getTime();
				 endTimeStamp = academicYearEvaluateRound.getRound2EndDate();
		 
			 
		 }else{
			 
			 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
			 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
			 
			 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
			 endTimeStamp = academicYearEvaluateRound.getRound1EndDate(); 
			 
		 }
				 
		 logger.info(" Calculate Date Range :"+startTimeStamp+" - "+endTimeStamp);
		 
		 
		 
		 
		
		String sql =" select *  from pbp_work_type where academic_year ='"+academicYear+"' and faculty_code='"+facultyCode+"'" ;  
		
		//logger.info(" ### [1] pbp_work_type #### getByAcademicYear sql:"+sql);
		
		PBPWorkTypeWrapper pBPWorkTypeWrapper = new PBPWorkTypeWrapper();	 	
		//String academicYear,String userName,String round,String employeeType,String facultyCode
		
		pBPWorkTypeWrapper.setAcademicYear(academicYear);
		pBPWorkTypeWrapper.setUserName(userName);
		 
		pBPWorkTypeWrapper.setEmployeeType(employeeTypeNo);
		pBPWorkTypeWrapper.setFacultyCode(facultyCode);
		
		pBPWorkTypeWrapper.setStartRoundDate(new Date(startTimeStamp.getTime()));
		pBPWorkTypeWrapper.setEndRoundDate(new Date(endTimeStamp.getTime()));
		
		try{
			
		///**### [1] pbp_work_type ----*/	
		List<PBPWorkType> pBPWorkTypeList  = this.jdbcTemplate.query(sql,	new PBPWorkTypeMapper() );	 
		for(PBPWorkType tmp:pBPWorkTypeList){
 
			try{
 
					
					String sqlMap =" select *  from academic_kpi_user_mapping  where work_type_code ="+tmp.getCode()+" " +
							" and academic_year='"+tmp.getAcademicYear()+"' and user_name='"+userName+"' and create_date BETWEEN '"+startTimeStamp+"' AND '"+endTimeStamp+"'"; 
					//logger.info("### [2] academic_kpi_user_mapping ###  sqlMap:"+sqlMap);
					List<AcademicKPIUserMapping> academicKPIUserMappingList = this.jdbcTemplate.query(sqlMap,	new AcademicKPIUserMappingMapper() );	
					
					if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){
						
						logger.info("  worktype code:"+tmp.getCode()+" list below ");
						for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){
							
							try{
								
								String sqlkpi =" select *  from academic_kpi where academic_kpi_id ="+mappingTmp.getAcademicKPIId() ; 
								//logger.info(" ### sqlkpi:"+sqlkpi);
								 AcademicKPI  academicKPI  = this.jdbcTemplate.queryForObject(sqlkpi,	new AcademicKPIMapper() );
								 mappingTmp.setAcademicKPI(academicKPI);
								 
								 	
							 	//logger.info(" ### [4] academic_kpi_attribute_value --####");
								String sqlAttributeValue =" select *  from academic_kpi_attribute_value where kpi_user_mapping_id ="+mappingTmp.getKpiUserMappingId() ; 
								List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
								try{
									academicKPIAttributeValueList = this.jdbcTemplate.query(sqlAttributeValue,	new AcademicKPIAttributeValueMapper() );
									//System.out.print( mappingTmp.getKpiUserMappingId()+":"+ academicKPIAttributeValueList.size()+",");
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 
								mappingTmp.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
								 
								//logger.info(" ### [5] academic_kpi_attribute --####");	
								String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+mappingTmp.getAcademicKPIId()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
								List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
								try{
									academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 									
								
								for(AcademicKPIAttributeValue attributeValueTmp: academicKPIAttributeValueList){
									//logger.info("  Start set is calculate  attributeName  :"+attributeValueTmp.getName() ); 
									for(AcademicKPIAttribute attributeTmp:academicKPIAttributeList){
										String isCalculate = attributeTmp.getIsCalculate();
										//String isValidateNumber= attributeTmp.getIsCalculate();
										String attributeName = attributeTmp.getName();
										
										if(attributeName!=null){
											String attributeValueName = attributeValueTmp.getName();
											if(attributeName.equalsIgnoreCase(attributeValueName)){
												if("Y".equalsIgnoreCase(isCalculate)){
													attributeValueTmp.setIsCalculate("Y");
												}
											}
										}else{
											logger.info(" Found Attribute Name null");
										}
									}
								}
								mappingTmp.setAcademicKPIAttributeList(academicKPIAttributeList);
								 
							}catch (org.springframework.dao.EmptyResultDataAccessException ex){
								ex.printStackTrace();
							} 
	 
							
						}
						
					}
					
					tmp.setAcademicKPIUserMappingList(academicKPIUserMappingList);
			//	} 
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
		}
		
		
		// Callculate 
		
//		logger.info("  ##########  ##########   ##########  Start Calculate   ##########  ##########  ##########" );
	 

		BigDecimal totalMark = new BigDecimal(0.00);
		BigDecimal totalMarkCompareBase = new BigDecimal(0.00);
		BigDecimal totalPercentMark = new BigDecimal(0.00);
		BigDecimal totalPercentMarkComareBase = new BigDecimal(0.00);
		
		BigDecimal totalMark_E = new BigDecimal(0.00);
		BigDecimal totalMarkCompareBase_E = new BigDecimal(0.00);
		BigDecimal totalPercentMark_E = new BigDecimal(0.00);
		BigDecimal totalPercentMarkComareBase_E = new BigDecimal(0.00);
		
		for(PBPWorkType tmp:pBPWorkTypeList){
			BigDecimal maxMark =  tmp.getMaxHour();
			BigDecimal limitBase = tmp.getLimitBase();
			
			BigDecimal minHour =tmp.getMinHour();
			BigDecimal maxHour =tmp.getMaxHour();
			
			BigDecimal totalInWorkType = new BigDecimal(0.00);
			BigDecimal totalInWorkType_E = new BigDecimal(0.00);
			
			List<AcademicKPIUserMapping> academicKPIUserMappingList =  tmp.getAcademicKPIUserMappingList();
			if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){	
				
				for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){					
					BigDecimal totalMappingTmp = new BigDecimal(0.0);
					BigDecimal totalMappingTmp_E = new BigDecimal(0.0);
					//logger.info(" Start With Total Mark:"+totalMappingTmp);
					AcademicKPI kpiTmp = mappingTmp.getAcademicKPI();					
					String mappingStatus = mappingTmp.getStatus();		
					
					
					
					if("APPROVED".equalsIgnoreCase(mappingStatus)){ 	
						String calResultStr =""; 
						List<AcademicKPIAttributeValue>  academicKPIAttributeValueList  =mappingTmp.getAcademicKPIAttributeValueList();						
						//logger.info("   ########## Mapping id:"+mappingTmp.getKpiUserMappingId()+" Statatus  Approve , So Start Calculate " );
						//logger.info(" KPI Mark:"+kpiTmp.getMark()+" Multiply Value:"+kpiTmp.getMultiplyValue());    
						BigDecimal multiplyValueBig = new BigDecimal(0.00);						
						try{
							multiplyValueBig = new BigDecimal(kpiTmp.getMultiplyValue());
							multiplyValueBig.setScale(2);
						}catch(Exception ex){
							multiplyValueBig = new BigDecimal(1).setScale(2);
						}
						
						calResultStr= " ("+multiplyValueBig+" ตัวคุณ )"+" X ("+kpiTmp.getMark()+" คะแนน KPI )";						
						if(kpiTmp.getMultiplyValue()!=null&&multiplyValueBig.doubleValue()>0){							
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()).multiply(multiplyValueBig).setScale(2); 							 
							//logger.info("  Found multiply value :"+kpiTmp.getMultiplyValue()+"  So multiply by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}else{									 	
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()); 							
						//	logger.info("  No multiply value :"+kpiTmp.getMultiplyValue()+"  So Add by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}					
						//logger.info("  Start is calculate attribute size :"+academicKPIAttributeValueList.size());
								for(AcademicKPIAttributeValue attributeValueTmp: academicKPIAttributeValueList){ 
										String isCalculate = attributeValueTmp.getIsCalculate(); 
										String attributeValueName = attributeValueTmp.getName();										
										if("Y".equalsIgnoreCase(isCalculate)){	 
										String attributeValueValue = attributeValueTmp.getValue(); 
										BigDecimal tmpBeforCall = totalMappingTmp;
										if(attributeValueName.indexOf("สัดส่วน(%)")!=-1){ 
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2,RoundingMode.HALF_UP)).divide(new BigDecimal(100));											
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +"  So :"+tmpBeforCall+"*"+attributeValueValue+"/100 = "+totalMappingTmp);
											calResultStr=calResultStr+ " X (" +attributeValueName+" "+attributeValueValue+")";
										}else{ 
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +" So :"+tmpBeforCall+"*"+attributeValueValue+"  = "+totalMappingTmp);
 											try{
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2));											
											}catch(Exception ex){
												
												logger.info("  Exception #############   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +" So :"+tmpBeforCall+"*"+attributeValueValue+"  = "+totalMappingTmp);
												ex.printStackTrace();
											}
											calResultStr=calResultStr+ " X (" +attributeValueValue+" "+attributeValueName+")";
										}
 
								}
								
								
							}
								//logger.info("  Call Str: "+calResultStr);
								logger.info(mappingTmp.getKpiUserMappingId()+":APPROVED --> "+calResultStr+" = "+totalMappingTmp);
								mappingTmp.setCalResultStr(calResultStr+" = "+totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
								updateCallResultStr(mappingTmp);
								
								//BigDecimal totalPercentInMapping = totalMappingTmp.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
								mappingTmp.setTotalInMapping(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));   
								//mappingTmp.setTotalPercentInMapping(totalPercentInMapping.setScale(2));
								totalInWorkType =totalInWorkType.add(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
								totalInWorkType_E =totalInWorkType_E.add(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
						
						//logger.info("   Mark after other Attribute :"+totalMappingTmp);
					} else{
						//logger.info("   ##########  Mapping id:"+mappingTmp.getKpiUserMappingId()+"  Statatus Not Approve , So Calculate for Temporary Mark" );
						
 						
						
						String calResultStr =""; 
						List<AcademicKPIAttributeValue>  academicKPIAttributeValueList  =mappingTmp.getAcademicKPIAttributeValueList();
						
						//logger.info("   ########## Mapping id:"+mappingTmp.getKpiUserMappingId()+" Statatus  Approve , So Start Calculate " );
						//logger.info(" KPI Mark:"+kpiTmp.getMark()+" Multiply Value:"+kpiTmp.getMultiplyValue());    
						BigDecimal multiplyValueBig = new BigDecimal(0.00);						
						try{
							multiplyValueBig = new BigDecimal(kpiTmp.getMultiplyValue());
							multiplyValueBig.setScale(2);
						}catch(Exception ex){
							multiplyValueBig = new BigDecimal(1).setScale(2);
						}
						
						calResultStr= " ("+multiplyValueBig+" ตัวคุณ )"+" X ("+kpiTmp.getMark()+" คะแนน KPI )";						
						if(kpiTmp.getMultiplyValue()!=null&&multiplyValueBig.doubleValue()>0){							
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()).multiply(multiplyValueBig).setScale(2,RoundingMode.HALF_UP); 
							//logger.info("  Found multiply value :"+kpiTmp.getMultiplyValue()+"  So multiply by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}else{									 	
							totalMappingTmp =totalMappingTmp.add(kpiTmp.getMark()); 
							//logger.info("  No multiply value :"+kpiTmp.getMultiplyValue()+"  So Add by :"+kpiTmp.getMultiplyValue()+" = "+totalMappingTmp);
						}					
						//logger.info("  Start is calculate attribute size :"+academicKPIAttributeValueList.size());
								for(AcademicKPIAttributeValue attributeValueTmp: academicKPIAttributeValueList){ 
										String isCalculate = attributeValueTmp.getIsCalculate(); 
										String attributeValueName = attributeValueTmp.getName();										
										if("Y".equalsIgnoreCase(isCalculate)){	 
										String attributeValueValue = attributeValueTmp.getValue(); 
										BigDecimal tmpBeforCall = totalMappingTmp;
										if(attributeValueName.indexOf("สัดส่วน(%)")!=-1){ 
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2,RoundingMode.HALF_UP)).divide(new BigDecimal(100));
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +"  So :"+tmpBeforCall+"*"+attributeValueValue+"/100 = "+totalMappingTmp);
											calResultStr=calResultStr+ " X (" +attributeValueName+" "+attributeValueValue+")";
										}else{ 
											//logger.info("   Attribute Name:"+attributeValueName+"   Attribute value:"+attributeValueValue+ " isCal:"+isCalculate +" So :"+tmpBeforCall+"*"+attributeValueValue+"  = "+totalMappingTmp);
 											try{
											totalMappingTmp =totalMappingTmp.multiply(new BigDecimal(attributeValueValue).setScale(2));
											}catch(Exception ex){
												ex.printStackTrace();
											}
											calResultStr=calResultStr+ " X (" +attributeValueValue+" "+attributeValueName+")";
										}
 
								}
								
								
							}
								//logger.info("  Call Str: "+calResultStr+" = "+totalMappingTmp);
								logger.info(mappingTmp.getKpiUserMappingId()+":NOT APPROVED --> "+calResultStr+" = "+totalMappingTmp);
								mappingTmp.setCalResultStr(calResultStr+" = "+totalMappingTmp.setScale(2,RoundingMode.HALF_UP));	
								updateCallResultStr(mappingTmp);
														
								//BigDecimal totalPercentInMapping = totalMappingTmp.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
								mappingTmp.setTotalInMapping(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));   
								//mappingTmp.setTotalPercentInMapping(totalPercentInMapping.setScale(2));
								totalInWorkType_E =totalInWorkType_E.add(totalMappingTmp.setScale(2,RoundingMode.HALF_UP));
						
						
						
					}
					
				 
					//BigDecimal totalPercentInMapping = totalMappingTmp.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
					//mappingTmp.setTotalInMapping(totalMappingTmp.setScale(2));   
					//mappingTmp.setTotalPercentInMapping(totalPercentInMapping.setScale(2));
					//totalInWorkType =totalInWorkType.add(totalMappingTmp.setScale(2));
				} 				
			} 
			
		//	BigDecimal totalInPercentWorkType = totalInWorkType.multiply(new BigDecimal(100)).divide( limitBase ,2,RoundingMode.HALF_UP); 
			//logger.info(" getMarkDepartmetn totalInPercentWorkType :"+totalInWorkType );
			tmp.setTotalInWorkType(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
			tmp.setTotalInWorkType_E(totalInWorkType_E.setScale(2,RoundingMode.HALF_UP));
			
			logger.info(tmp.getName()+"   "+totalInWorkType  );
			
			//tmp.setTotalInPercentWorkType(totalInPercentWorkType.setScale(2));
			if(totalInWorkType.compareTo(minHour)==-1){
			//	tmp.setTotalInPercentCompareBaseWorkType(totalInPercentWorkType.setScale(2));
				tmp.setTotalInWorkTypeCompareBase(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
				tmp.setCompareBaseStatus("UNDER");
			}else  if(totalInWorkType.compareTo(maxHour)==1){
				//tmp.setTotalInPercentCompareBaseWorkType ( tmp.getMaxPercent() .setScale(2));
				tmp.setTotalInWorkTypeCompareBase( tmp.getMinHour() .setScale(2,RoundingMode.HALF_UP));
				tmp.setCompareBaseStatus("OVER");
			}else {
			//	tmp.setTotalInPercentCompareBaseWorkType(totalInPercentWorkType.setScale(2));
				tmp.setTotalInWorkTypeCompareBase(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
				tmp.setCompareBaseStatus("NORMAL");
			}
			
			tmp.setTotalInWorkTypeCompareBase_E(totalInWorkType_E.setScale(2));
			
			totalPercentMark =totalPercentMark.add(tmp.getTotalInPercentWorkType().setScale(2,RoundingMode.HALF_UP));
			totalPercentMarkComareBase =totalPercentMarkComareBase.add(tmp.getTotalInPercentCompareBaseWorkType().setScale(2,RoundingMode.HALF_UP));
			totalMark = totalMark.add(totalInWorkType.setScale(2,RoundingMode.HALF_UP));
			totalMarkCompareBase =totalMarkCompareBase.add(tmp.getTotalInWorkTypeCompareBase().setScale(2,RoundingMode.HALF_UP));
			
			
			totalPercentMark_E =totalPercentMark_E.add(tmp.getTotalInPercentWorkType_E().setScale(2));
			totalPercentMarkComareBase_E =totalPercentMarkComareBase_E.add(tmp.getTotalInPercentCompareBaseWorkType_E().setScale(2,RoundingMode.HALF_UP));
			totalMark_E = totalMark_E.add(totalInWorkType_E.setScale(2,RoundingMode.HALF_UP));
			totalMarkCompareBase_E =totalMarkCompareBase_E.add(tmp.getTotalInWorkTypeCompareBase_E().setScale(2,RoundingMode.HALF_UP));
			
			//logger.info(" total Mark:"+totalMark);
		} 
		
			//BigDecimal totalPercentMark = totalMark.multiply(new BigDecimal(100)).divide(maxMark,2,RoundingMode.HALF_UP); 
			//logger.info(" totalPercentMark  :"+totalPercentMark);
			pBPWorkTypeWrapper.setTotalMark(totalMark.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalMarkCompareBase(totalMarkCompareBase);
			pBPWorkTypeWrapper.setTotalPercentMark(totalPercentMark.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalPercentMarkCompareBase(totalPercentMarkComareBase.setScale(2,RoundingMode.HALF_UP));
			
			
			pBPWorkTypeWrapper.setTotalMark_E(totalMark_E.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalMarkCompareBase_E(totalMarkCompareBase_E);
			pBPWorkTypeWrapper.setTotalPercentMark_E(totalPercentMark_E.setScale(2,RoundingMode.HALF_UP));
			pBPWorkTypeWrapper.setTotalPercentMarkCompareBase_E(totalPercentMarkComareBase_E.setScale(2,RoundingMode.HALF_UP));
			
			// 1- Because mark_rank keep 0,1
			// int employeeTypeGetSalary = Integer.parseInt(employeeType) -1;
			//pBPWorkTypeWrapper.setIncreaseSalaryRate(getIncreaseSalaryRate(totalPercentMarkComareBase,academicYear,employeeTypeGetSalary+"",round));
			pBPWorkTypeWrapper.setpBPWorkTypeList(pBPWorkTypeList);
			
			
			//updateReportPerson(pBPWorkTypeWrapper);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return pBPWorkTypeWrapper;
	} 
	
	
	public int updateReportPerson ( PBPWorkTypeWrapper pBPWorkTypeWrapper){
		int returnValue = 0;
		try{
			final PBPWorkTypeWrapper finalTmp = pBPWorkTypeWrapper;
			
			final String academicYear = finalTmp.getAcademicYear();
			final String round = finalTmp.getRound();
			final String userName = finalTmp.getUserName();
			
	        String deletePersonReport ="delete from  report_person where academic_year ="+academicYear+" and round ="+round+" and userName='"+userName+"'"; 
	        logger.info("  deletePersonReport sql:"+deletePersonReport);
			this.jdbcTemplate.update(deletePersonReport);
			
			
			List<PBPWorkType> workTypeList =finalTmp.getpBPWorkTypeList();
			
			String workTypeCode1 = "";
			String workTypeName1 = "";
			String workTypeCode2 = "";
			String workTypeName2 = "";
			String workTypeCode3 = "";
			String workTypeName3 = "";
			String workTypeCode4 = "";
			String workTypeName4 = "";
			String workTypeCode5 = "";
			String workTypeName5 = "";
	 
			
			 BigDecimal mark_1 = new BigDecimal(0.00);
			 BigDecimal mark_2 = new BigDecimal(0.00);
			 BigDecimal mark_3 = new BigDecimal(0.00);
			 BigDecimal mark_4 = new BigDecimal(0.00);
			 BigDecimal mark_5 = new BigDecimal(0.00);
			
			
			 BigDecimal mark_E_1 = new BigDecimal(0.00);
			 BigDecimal mark_E_2 = new BigDecimal(0.00);
			 BigDecimal mark_E_3 = new BigDecimal(0.00);
			 BigDecimal mark_E_4 = new BigDecimal(0.00);
			 BigDecimal mark_E_5 = new BigDecimal(0.00);		
			
			
			for(PBPWorkType workTypeTmp:workTypeList){
				
				String workTypeCode  = workTypeTmp.getCode();
				String workTypeName = workTypeTmp.getName();
				BigDecimal mark = workTypeTmp.getTotalInWorkType();
				BigDecimal e_mark = workTypeTmp.getTotalInWorkType_E();
				
				
				if("1".equals(workTypeCode)){
					mark_1 = mark;
					mark_E_1 = e_mark;
					workTypeCode1 = workTypeCode;
					workTypeName1= workTypeName;
				}else if("2".equals(workTypeCode)){
					mark_2 = mark;
					mark_E_2 = e_mark;
					workTypeCode2 = workTypeCode;
					workTypeName2= workTypeName;
				}else if("3".equals(workTypeCode)){
					mark_3 = mark;
					mark_E_3 = e_mark;
					workTypeCode3 = workTypeCode;
					workTypeName3= workTypeName;
				}else if("4".equals(workTypeCode)){
					mark_4 = mark;
					mark_E_4 = e_mark;
					workTypeCode4 = workTypeCode;
					workTypeName4= workTypeName;
				}else if("5".equals(workTypeCode)){
					mark_5 = mark;
					mark_E_5 = e_mark;
					workTypeCode5 = workTypeCode;
					workTypeName5= workTypeName;
				}
				
				
			
				
			}
			
			
			/*
DROP TABLE IF EXISTS `pbp2`.`report_person`;
CREATE TABLE  `pbp2`.`report_person` (
  `report_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `academic_year` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `first_last_name` varchar(45) DEFAULT NULL,
  `mark_1` varchar(45) DEFAULT NULL,
  `mark_2` varchar(45) DEFAULT NULL,
  `mark_3` varchar(45) DEFAULT NULL,
  `mark_4` varchar(45) DEFAULT NULL,
  `mark_5` varchar(45) DEFAULT NULL,
  `work_type_name1` varchar(450) DEFAULT NULL,
  `work_type_code1` varchar(45) DEFAULT NULL,
  `work_type_name2` varchar(450) DEFAULT NULL,
  `work_type_name3` varchar(450) DEFAULT NULL,
  `work_type_name4` varchar(450) DEFAULT NULL,
  `work_type_name5` varchar(450) DEFAULT NULL,
  `work_type_code2` varchar(45) DEFAULT NULL,
  `work_type_code3` varchar(45) DEFAULT NULL,
  `work_type_code4` varchar(45) DEFAULT NULL,
  `work_type_code5` varchar(45) DEFAULT NULL,
  `faculty_code` varchar(45) DEFAULT NULL,
  `faculty_name` varchar(45) DEFAULT NULL,
  `department_code` varchar(45) DEFAULT NULL,
  `department_name` varchar(45) DEFAULT NULL,
  `mark_total` varchar(45) DEFAULT NULL,
  `person_type_code` varchar(45) DEFAULT NULL,
  `person_type_name` varchar(45) DEFAULT NULL,
  `round` varchar(45) DEFAULT NULL,
  `e_mark_1` varchar(45) DEFAULT NULL,
  `e_mark_2` varchar(45) DEFAULT NULL,
  `e_mark_3` varchar(45) DEFAULT NULL,
  `e_mark_4` varchar(45) DEFAULT NULL,
  `e_mark_5` varchar(45) DEFAULT NULL,
  `e_mark_total` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
			 */
			final BigDecimal m1  = mark_1;
			final BigDecimal m2  = mark_2;
			final BigDecimal m3  = mark_3;
			final BigDecimal m4  = mark_4;
			final BigDecimal m5  = mark_5;
			
			final String wtCode1  = workTypeCode1;
			final String wtCode2  = workTypeCode2;
			final String wtCode3  = workTypeCode3;
			final String wtCode4  = workTypeCode4;
			final String wtCode5  = workTypeCode5;
			
			final String wotName1  = workTypeName1;
			final String wotName2  = workTypeName2;
			final String wotName3  = workTypeName3;
			final String wotName4  = workTypeName4;
			final String wotName5  = workTypeName5;
			
			final BigDecimal mE_1  = mark_E_1;
			final BigDecimal mE_2  = mark_E_2;
			final BigDecimal mE_3  = mark_E_3;
			final BigDecimal mE_4  = mark_E_4;
			final BigDecimal mE_5  = mark_E_5;
			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  insert into report_person ("
							+ "academic_year, "
							+ "username,"
							+ "round,"
							+ "mark_1,"
							+ "mark_2,"
							+ "mark_3,"
							+ "mark_4,"
							+ "mark_5,"
							+ "mark_total,"
							+ "work_type_code1,"
							+ "work_type_name1,"
							+ "work_type_code2,"
							+ "work_type_name2,"
							+ "work_type_code3,"
							+ "work_type_name3,"
							+ "work_type_code4,"
							+ "work_type_name4,"
							+ "work_type_code5,"
							+ "work_type_name5,"							
							+ "e_mark_1,"
							+ "e_mark_2,"
							+ "e_mark_3,"
							+ "e_mark_4,"
							+ "e_mark_5,"
							+ "e_mark_total"
						 
							+ ")   values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
						 "", Statement.RETURN_GENERATED_KEYS);   
					
					
					ps.setString(1,academicYear);
					ps.setString(2,userName);
					ps.setString(3,round);
					ps.setString(4,m1+"");
					ps.setString(5,m2+"");
					ps.setString(6,m3+"");
					ps.setString(7,m4+"");
					ps.setString(8,m5+"");
					ps.setString(9,finalTmp.getTotalMark()+"");
					ps.setString(10,wtCode1);
					ps.setString(11,wotName1);
					ps.setString(12,wtCode2);
					ps.setString(13,wotName2);
					ps.setString(14,wtCode3);
					ps.setString(15,wotName3);
					ps.setString(16,wtCode4);
					ps.setString(17,wotName4);
					ps.setString(18,wtCode5);
					ps.setString(19,wotName5);
					ps.setString(20,mE_1+"");
					ps.setString(21,mE_2+"");
					ps.setString(22,mE_3+"");
					ps.setString(23,mE_4+"");
					ps.setString(24,mE_5+"");
					ps.setString(25,finalTmp.getTotalMark_E().toString());
					 
					 
					return ps;  
					}
				} ); 			 
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}	
	
	
	
	private int updateCallResultStr ( AcademicKPIUserMapping mappingTmp){
		int returnValue = 0;
		try{
			final AcademicKPIUserMapping finalTmp = mappingTmp;
		 
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  update  academic_kpi_user_mapping set cal_result_str =?   where kpi_user_mapping_id=?  " +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,finalTmp.getCalResultStr());			 
					ps.setLong(2, finalTmp.getKpiUserMappingId());
				 
				
					return ps;  
					}
				} );			 
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	 
	private int getIncreaseSalaryRate (BigDecimal totalPercentMark,String academicYear,String employeeType,String round){
		int returnValue = 0;
		try{
			
			  
			String sql =" select *  from mark_rank where academic_year ='"+academicYear+"' and employee_type='"+employeeType+"' and round ='"+round+"'" ;  
			
			logger.info("  getIncreaseSalaryRate sql:"+sql);
			
		//	MarkRankWrapper markRankWrapper = new MarkRankWrapper();	 				
			List<MarkRank> markRankList  = this.jdbcTemplate.query(sql,	new MarkRankMapper() );	
			
			for(MarkRank markRankTmp:markRankList){
				
				int markFrom =markRankTmp.getMarkFrom();
				int markTo =markRankTmp.getMarkTo();
				int mark =totalPercentMark.intValue();
				logger.info("  markFrom:"+markFrom+"  markTo:"+markTo+" mark:"+mark);
				boolean isBetween = BuckwaNumberUtil.isBetween(markFrom, markTo, mark);
				
				logger.info("  isBetween:"+isBetween);
				if(isBetween){
					returnValue = markRankTmp.getSalaryLevel();
					break;
					
				}
				
				
			}
			 
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	
	@Override
	public PBPWorkType  getById( String id) {		 		
		String sql =" select *  from pbp_work_type where work_type_id="+id ;  
		
		logger.info("  getByAcademicYear sql:"+sql);
		 	 				
		 PBPWorkType pBPWorkType   = this.jdbcTemplate.queryForObject (sql,	new PBPWorkTypeMapper() );				
			if(pBPWorkType!=null){
				
				
				String sqlSub =" select *  from pbp_work_type_sub  where work_type_id ="+id; 
				logger.info(" sqlSub:"+sqlSub);
				List<PBPWorkTypeSub> pBPWorkTypeSubList  =null;
				
				try{
					pBPWorkTypeSubList = this.jdbcTemplate.query(sqlSub,	new PBPWorkTypeSubMapper() );	
					pBPWorkType.setpBPWorkTypeSubList(pBPWorkTypeSubList);
					
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				} 
				
			}
	 
		return pBPWorkType;
	}
	@Override
	public PBPWorkType  getByCodeAcademicFacultyCode( String code, String academicYear,String facultyCode) {		 		
		String sql =" select *  from pbp_work_type where code="+code+" and academic_year='"+academicYear+"' and faculty_code='"+facultyCode+"'" ;  
		
		logger.info("  getByAcademicYear sql:"+sql);
		 	 				
		 PBPWorkType pBPWorkType   = this.jdbcTemplate.queryForObject (sql,	new PBPWorkTypeMapper() );				
			if(pBPWorkType!=null){
				
				
				String sqlSub =" select *  from pbp_work_type_sub  where work_type_id ="+pBPWorkType.getWorkTypeId(); 
				logger.info(" sqlSub:"+sqlSub);
				List<PBPWorkTypeSub> pBPWorkTypeSubList  =null;
				
				try{
					pBPWorkTypeSubList = this.jdbcTemplate.query(sqlSub,	new PBPWorkTypeSubMapper() );	
					pBPWorkType.setpBPWorkTypeSubList(pBPWorkTypeSubList);
					
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				} 
				
			}
	 
		return pBPWorkType;
	}
	
	
	
	@Override
	public PBPWorkType  getSub( String id) {		 		
		String sql =" select *  from pbp_work_type where work_type_id ="+id; 
		logger.info(" sql:"+sql);
		PBPWorkType  pBPWorkType   =null;
		
		try{
			pBPWorkType  = this.jdbcTemplate.queryForObject(sql,	new PBPWorkTypeMapper() );	
		}catch (Exception ex){
			ex.printStackTrace();
		} 
		
		if(pBPWorkType!=null){
			
			
			String sqlSub =" select *  from pbp_work_type_sub  where work_type_id ="+id; 
			logger.info(" sqlSub:"+sqlSub);
			List<PBPWorkTypeSub> pBPWorkTypeSubList  =null;
			
			try{
				pBPWorkTypeSubList = this.jdbcTemplate.query(sqlSub,	new PBPWorkTypeSubMapper() );	
				pBPWorkType.setpBPWorkTypeSubList(pBPWorkTypeSubList);
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
			
		}
	 
		return pBPWorkType ;
	}
	
	
	
	@Override
	public boolean isExist(PBPWorkType pBPWorkType) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalRole  from pbp_work_type t  where t.name='"+pBPWorkType.getName()+"' " ;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	
	@Override
	public void create(PBPWorkType pBPWorkType) {
		logger.info("  create # ");		
		final PBPWorkType finalpBPWorkType = pBPWorkType;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into mark_rank (mark_from, mark_to,academic_year) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
			//	ps.setInt(1,finalpBPWorkType.getMarkFrom());
				//ps.setInt(2,finalpBPWorkType.getMarkTo());
				ps.setString(3,finalpBPWorkType.getAcademicYear());		 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	
	@Override
	public void edit(PBPWorkTypeWrapper pBPWorkTypeWrapper) {
		logger.info("  edit # ");		
		final PBPWorkTypeWrapper finalPBPWorkTypeWrapper = pBPWorkTypeWrapper;
		
		List<PBPWorkType> pBPWorkTypeList = finalPBPWorkTypeWrapper.getpBPWorkTypeList();
		
		
		for(final PBPWorkType pBPWorkTypeTmp:pBPWorkTypeList){		
			logger.info("  xxx:"+BeanUtils.getBeanString(pBPWorkTypeTmp));
			logger.info("  pBPWorkTypeTmp id: "+pBPWorkTypeTmp.getWorkTypeId()+"academicYear:"+pBPWorkTypeTmp.getAcademicYear()+"facultyCode:"+finalPBPWorkTypeWrapper.getFacultyCodeSelect()+"  name:"+pBPWorkTypeTmp.getName()+" limit:"+pBPWorkTypeTmp.getLimitBase()+" min_hour:"+pBPWorkTypeTmp.getMinHour()+" worktypeId:"+pBPWorkTypeTmp.getWorkTypeId());
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  update  pbp_work_type set name =? ,min_percent=?,min_hour=?,max_percent=? ,max_hour=?,limit_base=?,min_hour_cal=? , max_hour_cal=?  where work_type_id=? " +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,pBPWorkTypeTmp.getName());
					ps.setBigDecimal(2,pBPWorkTypeTmp.getMinPercent());
					ps.setBigDecimal(3,pBPWorkTypeTmp.getMinHour());
					ps.setBigDecimal(4,pBPWorkTypeTmp.getMaxPercent());
					ps.setBigDecimal(5,pBPWorkTypeTmp.getMaxHour());
					//ps.setBigDecimal(6,pBPWorkTypeTmp.getLimitBase());
					
					ps.setBigDecimal(6,pBPWorkTypeTmp.getMaxHour());
					ps.setBoolean(7,pBPWorkTypeTmp.isMinHourCal());
					ps.setBoolean(8,pBPWorkTypeTmp.isMaxHourCal());
					ps.setLong(9, pBPWorkTypeTmp.getWorkTypeId());
				 
					
				
					return ps;  
					}
				} );			
			
		} 
 
	}
	
	
	
	
	@Override
	public void delete(String  pBPWorkTypeId) {
		logger.info("pBPWorkTypeId: "+pBPWorkTypeId);		
	        String deleteSQL ="delete from  pbp_work_type where work_type_id ="+pBPWorkTypeId;
	        String deleteSubSQL ="delete from  pbp_work_type_sub where work_type_id ="+pBPWorkTypeId;
	        logger.info(" #### delete:"+deleteSQL);
	        logger.info(" #### deleteSubSQL:"+deleteSubSQL);
			this.jdbcTemplate.update(deleteSQL);	
			this.jdbcTemplate.update(deleteSQL);
	 
 
	}
	
	
	
	@Override
	public void addNew(PBPWorkType pBPWorkType) {
		logger.info(" .addNew # ");		
		final String academicYear = schoolUtil.getCurrentAcademicYear();
		final int nexCode = generateCodeUtil.getNextAcademicUnitCode(academicYear);
		final PBPWorkType finalpBPWorkType = pBPWorkType;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into pbp_work_type (min_percent,min_hour,max_percent,max_hour, academic_year,code,limit_base) values (?, ?,?,?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setInt(1,0);
				ps.setInt(2,0);
				ps.setInt(3,0);
				ps.setInt(4,0);
				ps.setString(5,finalpBPWorkType.getAcademicYear());		
				ps.setInt(6,nexCode);
				ps.setInt(7, 0);
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	@Override
	public void addNewSub(PBPWorkTypeSub pBPWorkTypeSub) {
		logger.info(" .addNew # ");		
		final String academicYear = schoolUtil.getCurrentAcademicYear();
		final int nexCode = generateCodeUtil.getNextWorkTypeSubCode(pBPWorkTypeSub.getWorkTypeId()+"");
		final PBPWorkTypeSub finalpBPWorkTypeSub = pBPWorkTypeSub;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into pbp_work_type_sub (code,work_type_id,academic_year ) values (? ,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
			 	
				ps.setInt(1,nexCode);
				ps.setLong(2, finalpBPWorkTypeSub.getWorkTypeId());
				ps.setString(3, academicYear);
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	} 
	
	@Override
	public void editSub(PBPWorkType pBPWorkType) {
		logger.info("  edit # ");		
		final PBPWorkType finalPBPWorkTypeWrapper = pBPWorkType;
		
		List<PBPWorkTypeSub> pBPWorkTypeSubList = finalPBPWorkTypeWrapper.getpBPWorkTypeSubList();
		
		for(final PBPWorkTypeSub pBPWorkTypeTmp:pBPWorkTypeSubList){			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  update  pbp_work_type_sub set name =?   where work_type_sub_id=?  "+
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,pBPWorkTypeTmp.getName());
					 
					ps.setLong(2, pBPWorkTypeTmp.getWorkTypeSubId());
				 
				
					return ps;  
					}
				} );			
			
		} 
 
	}
	
	
	
	
	@Override
	public void deleteSub(String  pBPWorkTypeSubId) {
		logger.info("pBPWorkTypeId: "+pBPWorkTypeSubId);		
	        String deleteSQL ="delete from  pbp_work_type_sub where work_type_sub_id ="+pBPWorkTypeSubId;
	        logger.info(" #### delete:"+deleteSQL);
			this.jdbcTemplate.update(deleteSQL);	
		 
	 
 
	}
	
	
	private class PBPWorkTypeMapper implements RowMapper<PBPWorkType> {   						
        @Override
		public PBPWorkType mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PBPWorkType domain = new PBPWorkType(); 
        	domain.setWorkTypeId(rs.getLong("work_type_id"));
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("name"));
			domain.setDescription(rs.getString("description"));
			domain.setMinPercent(rs.getBigDecimal("min_percent"));
			domain.setMinHour(rs.getBigDecimal("min_hour"));
			domain.setMaxPercent(rs.getBigDecimal("max_percent"));
			domain.setMaxHour(rs.getBigDecimal("max_hour"));
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setLimitBase(rs.getBigDecimal("limit_base"));
			domain.setFacultyCode(rs.getString("faculty_code"));
			 
			domain.setMinHourCal(rs.getBoolean("min_hour_cal"));
			domain.setMaxHourCal(rs.getBoolean("max_hour_cal"));
		 
		return domain;
    }
	}
	
	
	private class PBPWorkTypeSubMapper implements RowMapper<PBPWorkTypeSub> {   						
        @Override
		public PBPWorkTypeSub mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PBPWorkTypeSub domain = new PBPWorkTypeSub(); 
        	domain.setWorkTypeSubId(rs.getLong("work_type_sub_id"));
        	domain.setWorkTypeId(rs.getLong("work_type_id"));
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("name"));
	 
			domain.setAcademicYear(rs.getString("academic_year"));
		 
		return domain;
    }
	}
	
	private class AcademicKPIUserMappingMapper implements RowMapper<AcademicKPIUserMapping> {   						
        @Override
		public AcademicKPIUserMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIUserMapping domain = new AcademicKPIUserMapping(); 
        	
        	
        	domain.setKpiUserMappingId(rs.getLong("kpi_user_mapping_id"));
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		 
			domain.setWorkTypeCode(rs.getString("work_type_code"));  
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setAcademicKPICode(rs.getString("academic_kpi_code"));
			domain.setStatus(rs.getString("status"));
			domain.setFromSource(rs.getString("from_source"));
		 
		return domain;
    }
	}
	 
 
	
	private class AcademicKPIAttributeValueMapper implements RowMapper<AcademicKPIAttributeValue> {   						
        @Override
		public AcademicKPIAttributeValue mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttributeValue domain = new AcademicKPIAttributeValue(); 
        	 
        	domain.setAcademicKPIMappingId(rs.getLong("kpi_user_mapping_id"));
			domain.setName(rs.getString("name"));		
			domain.setValue(rs.getString("value"));	 
		 
			return domain;
    }
	}
	
	private class AcademicKPIMapper implements RowMapper<AcademicKPI> {   						
        @Override
		public AcademicKPI mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPI domain = new AcademicKPI(); 
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setWorkTypeCode(rs.getString("work_type_code"));
			domain.setDescription(rs.getString("description"));	
			domain.setStatus(rs.getString("status"));	
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setMark(rs.getBigDecimal("mark"));
			domain.setUnitCode(rs.getString("unit_code"));
			domain.setMultiplyValue(rs.getString("rule_code"));
			domain.setUnitDesc(schoolUtil.getUnitDescMyCode(rs.getString("unit_code"), rs.getString("academic_year")));
		 
		return domain;
    }
	}
	
	private class AcademicKPIAttributeMapper implements RowMapper<AcademicKPIAttribute> {   						
        @Override
		public AcademicKPIAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttribute domain = new AcademicKPIAttribute(); 
        	domain.setAcademicKPIAtributeId(rs.getLong("kpi_attribute_id"));
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setAcademicKPICode(rs.getString("academic_kpi_code"));
			domain.setIsCalculate(rs.getString("is_calculate")); 
			domain.setIsValidateNumber(rs.getString("is_validate_number"));
			domain.setAcademicYear(rs.getString("academic_year"));
		    domain.setMandatory(rs.getString("mandatory"));
		 
		return domain;
    }
	}
	
	
	private class MarkRankMapper implements RowMapper<MarkRank> {   						
        @Override
		public MarkRank mapRow(ResultSet rs, int rowNum) throws SQLException {
        	MarkRank domain = new MarkRank(); 
        	domain.setMarkRankId(rs.getLong("mark_rank_id"));
			domain.setMarkFrom(rs.getInt("mark_from"));		
			domain.setMarkTo(rs.getInt("mark_to"));	
			domain.setSalaryLevel(rs.getInt("salary_level"));			 
			domain.setAcademicYear(rs.getString("academic_year"));
		return domain;
    }
	}
	
	private class AcademicYearEvaluateRoundMapper implements RowMapper<AcademicYearEvaluateRound> {   						
        @Override
		public AcademicYearEvaluateRound mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicYearEvaluateRound domain = new AcademicYearEvaluateRound(); 
        	domain.setRoundId(rs.getLong("round_id"));
			domain.setRound1StartDate(rs.getTimestamp("round1_start_date"));		
			domain.setRound1EndDate(rs.getTimestamp("round1_end_date"));
			domain.setRound1StartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round1_start_date")));
			domain.setRound1EndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round1_end_date")));
			
			domain.setRound1Status(rs.getString("round1_status"));
			
			domain.setRound2StartDate(rs.getTimestamp("round2_start_date"));
			domain.setRound2EndDate(rs.getTimestamp("round2_End_date"));
			domain.setRound2StartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round2_start_date")));
			domain.setRound2EndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round2_End_date")));
			domain.setRound2Status(rs.getString("round2_status"));
			
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setEvaluateType(rs.getString("evaluate_type"));
			domain.setEvaluateTypeDesc(rs.getString("evaluate_type_desc"));
			
		return domain;
    }
	}
	
	private class ReportPbpMapper implements RowMapper<ReportPbp> {  					
        @Override
		public ReportPbp mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ReportPbp domain = new ReportPbp(); 
        	domain.setAcademic_year(rs.getString("academic_year"));
        	domain.setUsername(rs.getString("username"));
        	domain.setRound(rs.getString("round"));
        	domain.setMark_1(rs.getString("mark_1"));
        	domain.setMark_2(rs.getString("mark_2"));
        	domain.setMark_3(rs.getString("mark_3"));
        	domain.setMark_4(rs.getString("mark_4"));
        	domain.setMark_5(rs.getString("mark_5"));
        	domain.setMark_total(rs.getString("mark_total"));
        	domain.setWork_type_code1(rs.getString("work_type_code1"));
        	domain.setWork_type_name1(rs.getString("work_type_name1"));
        	domain.setWork_type_code2(rs.getString("work_type_code2"));
        	domain.setWork_type_name2(rs.getString("work_type_name2"));
        	domain.setWork_type_code3(rs.getString("work_type_code3"));
        	domain.setWork_type_name3(rs.getString("work_type_name3"));
        	domain.setWork_type_code4(rs.getString("work_type_code4"));
        	domain.setWork_type_name4(rs.getString("work_type_name4"));
        	domain.setWork_type_code5(rs.getString("work_type_code5"));
        	domain.setWork_type_name5(rs.getString("work_type_name5"));						
        	domain.setE_mark_1(rs.getString("e_mark_1"));
        	domain.setE_mark_2(rs.getString("e_mark_2"));
        	domain.setE_mark_3(rs.getString("e_mark_3"));
        	domain.setE_mark_4(rs.getString("e_mark_4"));
        	domain.setE_mark_5(rs.getString("e_mark_5"));
        	domain.setE_mark_total(rs.getString("e_mark_total"));
		 
		return domain;
    }
	}
}
