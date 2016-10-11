package com.buckwa.web.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buckwa.dao.impl.uitl.ExcelReader;
import com.buckwa.dao.impl.uitl.ExcelReader.ExcelRowMapper;
import com.buckwa.dao.impl.uitl.RejectSubjectUtil;
import com.buckwa.dao.intf.pbp.AcademicYearDao;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.util.BuckWaDateUtils;

@Controller
@RequestMapping("/initialAcademicYear")
public class InitialAcademicYearController implements InitializingBean , BeanFactoryAware{
	private static Logger logger = LoggerFactory.getLogger(InitialAcademicYearController.class);
 
		
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RejectSubjectUtil rejectSubjectUtil;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private AcademicYearDao academicYearDao;
	
	  public void setBeanFactory(BeanFactory context) {
 
		  logger.info(" ##### InitialAcademicYearController ##### ");
			 
		  }
	
	
	public void afterPropertiesSet() throws Exception { 
		
		logger.info("############# Start InitialAcademicYearController #############"); 
		String currentAcademicYearStr ="";
		try{
			
			String currentAcademicYearSQL =" SELECT name FROM academic_year where year_status='Y' ";								 
			  String academicYear = (String)this.jdbcTemplate.queryForObject(	currentAcademicYearSQL , String.class);
			  currentAcademicYearStr =academicYear;
			academicYearUtil.setAcademicYear(academicYear);
			logger.info(" #############  AcademicYear:"+academicYear); 
			
			 List<AcademicYear> academicYearList =new ArrayList();
			 
				String sql =" select *  from academic_year ";  
				logger.info(" sqlxx:"+sql);
				academicYearList  = this.jdbcTemplate.query(sql,	new AcademicYearMapper() );		
				
				for(AcademicYear ayeartmp:academicYearList){
					
					String sqlSemester =" select *  from semester where academic_year='"+ayeartmp.getName()+"'"; 
					logger.info(" sqlSemester:"+sql);
					List<Semester> semesterList = this.jdbcTemplate.query(sqlSemester,	new SemesterMapper() );	
					
					ayeartmp.setSemesterList(semesterList);
				}
		 
			
			academicYearUtil.setAcademicYearList(academicYearList);
		}catch(Exception ex){
			logger.info(ex.toString());;
		}
	
		
		logger.info("#############  Load Faculty Dropdown #############"); 
		try{
			
			String facultySQL =" SELECT name,code FROM faculty where academic_year='"+currentAcademicYearStr+"' ";								 
			 
			
			 List<Faculty> facultyList =new ArrayList();
		 
				//logger.info(" facultySQL:"+facultySQL);
				//facultyList  = this.jdbcTemplate.query(facultySQL,	new FacultyMapper() );	 
			//academicYearUtil.setFacultyList(facultyList);
		}catch(Exception ex){
			logger.info(ex.toString());;
		}
		
		
		logger.info("#############  Load Reject Subject  #############"); 
		try{
			
			final Map<String,String> data = new HashMap<>();
			final Pattern r = Pattern.compile("^[0-9]+$");
			//ExcelReader excelReader = new ExcelReader("D:\\Project\\KMITL\\doc\\eject_subject.xls", 4,1);
			
			ExcelReader excelReader = new ExcelReader("/project/pbp/xlsx/eject_subject.xls", 4,1);
			
			List<String> rs = (List<String>) excelReader.readExcel(new ExcelRowMapper<String>() {

				@Override
				public String mapper(List<Object> rs, int rowIndex) {
					
					if(rs.size() >= 2){
						String val = "";
						if(rs.get(1) instanceof Double){
							val = String.valueOf(((Double)rs.get(1)).intValue());
						}else{
							val= String.valueOf(rs.get(1));
						}
						Matcher m = r.matcher(val);
						boolean find = m.find();
						System.out.println(val + " " + find);
						if(find){
							data.put(val, val);
							return val;
						}
						return null;
					}
					return null;
				}
			});
			System.out.println("SIZE:" +  rs.size());
			
			rejectSubjectUtil.setRejectMap(data);
		}catch(Exception ex){
			logger.info(ex.toString());;
		}
		
		
		logger.info("#############  Load ChumPorn Reg Id Mapping  #############"); 
 
		
		try {
			
			final Map<String, String> regMap = new HashMap<String, String>();
			//TODO change C:\\Users\\Developer\\Downloads\\Reg_ID_Mapping.xlsx , [InputStream Or String Path]
			//ExcelReader excelReader = new ExcelReader("D:\\Project\\pbp\\xlsx\\Reg_ID_Mapping.xlsx", 3);
			ExcelReader excelReader = new ExcelReader("/project/pbp/xlsx/Reg_ID_Mapping.xlsx", 3);
			excelReader.readExcel(new ExcelRowMapper<String>(){
				private String  getValue(Object value){
					String v = null;
					if(value == null){
						v = null;
					}else if(value instanceof Double){
						v = ""+ ((Double)value).longValue();
					}else{
						v = value.toString();
					}
					return  v;
				}
				@Override
				public String mapper(List rs, int rowIndex) {
					String columnB = getValue(rs.get(1));
					String columnC = getValue(rs.get(2));

					if(StringUtils.isEmpty(columnB)) return null;
					System.out.println(columnB + " : " + columnC);
					regMap.put(columnB, columnC);
					return columnB;
				}
				
			});
			
			//TODO USE regMap
			System.out.println("RegId SIZE : " + regMap.size());
			
			rejectSubjectUtil.setChumRegIdMappingMap(regMap);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		
	}
	
	private class SemesterMapper implements RowMapper<Semester> {
        @Override
		public Semester mapRow(ResultSet rs, int rowNum) throws SQLException {
			Semester domain = new Semester();
			domain.setSemesterId(rs.getLong("semester_id"));
			domain.setName(rs.getString("name"));
			domain.setStartDate(rs.getTimestamp("start_date"));
			domain.setEndDate(rs.getTimestamp("end_date"));
			domain.setYearId(rs.getLong("year_id"));
			return domain;
	    }
	}
	
	private class AcademicYearMapper implements RowMapper<AcademicYear> {   						
        @Override
		public AcademicYear mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicYear domain = new AcademicYear(); 
        	domain.setAcademicYearId(rs.getLong("academic_year_id"));
			domain.setName(rs.getString("name"));		
			domain.setStartDate(rs.getTimestamp("start_date"));
			domain.setEndDate(rs.getTimestamp("end_date"));	
			domain.setStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("start_date")));
			domain.setEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("end_date")));
		return domain;
    }
	}
	private class FacultyMapper implements RowMapper<Faculty> {   						
        @Override
		public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Faculty domain = new Faculty(); 
        	domain.setName(rs.getString("name"));
        	domain.setCode(rs.getString("code"));
		 
		return domain;
    }
	}
}