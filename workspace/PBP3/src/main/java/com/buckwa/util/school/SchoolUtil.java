package com.buckwa.util.school;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.service.intf.pbp.SchoolUtilDao;

@Service("schoolUtil") 
public class SchoolUtil {
	
	@Autowired
	private SchoolUtilDao schoolUtilDao;
	
	private static Map<String, String> UNIT_NAP;
	
	
	private static Logger logger = LoggerFactory.getLogger(SchoolUtil.class);
	public static String getYearLevelFromStudentCode(String studentCode,String academicYear ){
		String returnValue ="0";
		try{
			
			int academicYearInt =Integer.parseInt(academicYear);
			int studentCodetoYearInt = Integer.parseInt(getYearCodeFromStudentCode(studentCode))+2500;
			//logger.info(" academicYearInt:"+academicYearInt +"  studentCodetoYearInt:"+studentCodetoYearInt);
			if(academicYearInt==studentCodetoYearInt){
				returnValue = "1";
			}else if(academicYearInt>studentCodetoYearInt){
				int yearLevel = academicYearInt-studentCodetoYearInt+1;
				returnValue= yearLevel+"";
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		} 
		return returnValue;
		
	}
	
	public  String getCourseNameByCourseId(Long courseId ){
		String returnValue ="";
		try{
			
			returnValue= schoolUtilDao.getCourseNameByCourseId(courseId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public  String getUserNameFromRegId(String regId,String academicYear ){
		String returnValue ="";
		try{
			
			returnValue= schoolUtilDao.getUserNameFromRegId(regId,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public  String getFacultyCodeFromRegId(String regId,String academicYear ){
		String returnValue ="";
		try{
			
			returnValue= schoolUtilDao.getFacultyCodeFromRegId(regId,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public  String getRegIdFromUserName(String userName ,String academicYear){
		String returnValue ="";
		try{
			
			returnValue= schoolUtilDao.getRegIdFromUserName(userName,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public static String getYearLevelFromStudentCodeTwoDigit(int studentCode,int academicYear ){
		String returnValue ="0";
		try{
 
			if(academicYear==studentCode){
				returnValue = "1";
			}else if(academicYear>studentCode){
				int yearLevel = academicYear-studentCode+1;
				returnValue= yearLevel+"";
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	
	public static String getYearCodeFromStudentCode(String studentCode){
		String returnValue ="";
		try{
			
			if(studentCode!=null&&studentCode.length()==8){
				
				returnValue = studentCode.substring(0,2);
				//logger.info(" studentCode:"+ studentCode + " after substring:"+returnValue);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	
	public   String getFacultyCodeByFacultyName(String name,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getFacultyCodeByFacultyName(name,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getFacultyCodeByDepartmentName(String name,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getFacultyCodeByDepartmentName(name,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getDepartmentCodeByDepartmentName(String name,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getDepartmentCodeByDepartmentName(name,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getDepartmentByUserName(String userName,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getDepartmentByUserName(userName,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getDepartmentByHeadUserName(String userName,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getDepartmentByHeadUserName(userName,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getHeadUserNamebyDepartmentDesc(String facultyDesc ,String departmentDesc,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getHeadUserNamebyDepartmentDesc(facultyDesc,departmentDesc,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getFacutyByUserName(String userName,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getFacutyByUserName(userName,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public   String getFacutyByDeanUserName(String userName,String academicYear){
		String returnValue ="";
		try{
			return schoolUtilDao.getFacutyByDeanUserName(userName,academicYear);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
 
	
	public String getCurrentAcademicYear(){ 
		return schoolUtilDao.getCurrentAcademicYear();
		
	}
	public  String getUnitDescMyCode(String code ,String academicYear){
		String returnValue ="";
		try{
			
//			returnValue= schoolUtilDao.getUnitDescMyCode(code,academicYear);
			
			returnValue= getUNIT_NAP().get(code);
			
			//logger.info("> UnitCode  : "+code+", UnitDesc  : "+returnValue);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
		
	}
	
	public String getShortPositionDesc(String fullDesc,String maxEducation){
		String returnValue ="";
		try{
			
			if (StringUtils.isBlank(fullDesc)) {
				returnValue="";
			}else{
				if(fullDesc.trim().equalsIgnoreCase("�ͧ��ʵ�Ҩ����")){
					returnValue="��.";
				}else if(fullDesc.trim().equalsIgnoreCase("�Ҩ����")){
					returnValue="�.";
				}else if(fullDesc.trim().equalsIgnoreCase("��������ʵ�Ҩ����")){
					returnValue="��.";
				}else if(fullDesc.trim().equalsIgnoreCase("��ʵ�Ҩ����")){
					returnValue="�.";
				}
				
				if (!StringUtils.isBlank(maxEducation)) {
					if(maxEducation.equalsIgnoreCase("��ԭ���͡")){
						returnValue = returnValue+"��.";
					}
					
				}
			} 
			// ทดสอบ
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
	}

	public Map<String, String> getUNIT_NAP() {
		if(null==UNIT_NAP){
			List<Map<String, Object>> mapList = schoolUtilDao.getAllMapUnitDesc();
			Map<String, String> map = new HashMap<>();
			for (Map<String, Object> m : mapList) {
				map.put(m.get("code")+"", (String)m.get("name"));
			}
			UNIT_NAP = map;
		}
		return UNIT_NAP;
	}
}
