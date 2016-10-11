package com.buckwa.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.dao.intf.pam.ImportProfileDao;
import com.buckwa.dao.intf.userregistration.UserRegistrationDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.pam.Person;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;

@Controller
@RequestMapping("/uploadMgr")
public class UploadUserMgr {
	private static Logger logger = LoggerFactory.getLogger(UploadUserMgr.class);

	@Autowired
	private ImportProfileDao importProfileDao;
	@Autowired
	private UserRegistrationDao userRegistrationDao;
	
 

	private String userName;

	private ArrayList<Person> listSuccessPerson;

	private ArrayList<Person> listErrorPerson;

	private ArrayList<User> listSuccessLogin;

	private ArrayList<User> listErrorLogin;
	
	private ArrayList<User> sucessList;
	
	private ArrayList<User> failList;

	//@Value("#{appProperties['file.teacherByFacName']}") 
	private String fileByFecPath;

	//@Value("#{appProperties['file.teacherByAll']}") 
	private String fileByAllPath;

	public void readPBPProfileWorkbook(Workbook loginData, Workbook informationData) throws Exception {
//		Person person = new Person();
		Sheet loginsheet = loginData.getSheetAt(0);
		logger.info("******************** LOGIN ****************************");
		logger.info("LOGIN sheet name: " + loginsheet.getSheetName());
		logger.info("LOGIN last row number: " + (loginsheet.getLastRowNum()+1));
		logger.info("LOGIN sheet size: " + (loginsheet.getLastRowNum()+1));
		logger.info("*******************************************************");
		Sheet infomationsheet = informationData.getSheetAt(0);
		logger.info("*********************INFORMATION****************************");
		logger.info("INFORMATION sheet name: " + infomationsheet.getSheetName());
		logger.info("INFORMATION last row number: " + (infomationsheet.getLastRowNum()+1));
		logger.info("INFORMATION sheet size: " + (infomationsheet.getLastRowNum()+1));
		logger.info("*******************************************************");

		int validateLoginSize = loginsheet.getRow(0).getLastCellNum() + 1;
		int validateInfoSize = infomationsheet.getRow(0).getLastCellNum() + 1;
		
		logger.info("*********************Validate************************");
		logger.info("Validate***validateLoginSize  : " + validateLoginSize);
		logger.info("Validate***validateInfoSize  : " + validateInfoSize);
		if(validateLoginSize <= 7){
			String s = "\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48,\u0E2D\u0E31\u0E15\u0E23\u0E32\u0009,\u0E23\u0E2B\u0E31\u0E2A,\u0E2A\u0E33\u0E19\u0E31\u0E01\u0E17\u0E30\u0E40\u0E1A\u0E35\u0E22\u0E19\u0009,\u0E40\u0E25\u0E02\u0E1B\u0E0A\u0E0A.,\u0009\u0E0A\u0E37\u0E48\u0E2D (\u0E20\u0E32\u0E29\u0E32\u0E44\u0E17\u0E22)\u0009,\u0E2D\u0E35\u0E40\u0E21\u0E25\u0E4C,\u0009\u0E04\u0E13\u0E30\u0009\u0E2A\u0E32\u0E02\u0E32";
			throw new Exception("not XSLX column : " + s);
		}
		if(validateInfoSize <= 20){
			String s = "\u0E25\u0E33\u0E14\u0E31\u0E1A\u0E17\u0E35\u0E48,\u0009\u0E2A\u0E31\u0E07\u0E01\u0E31\u0E14,\u0009\u0E2A\u0E48\u0E27\u0E19\u0E07\u0E32\u0E19,\u0009\u0E2A\u0E32\u0E22\u0E07\u0E32\u0E19,\u0009\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0009,\u0E23\u0E32\u0E22\u0E0A\u0E37\u0E48\u0E2D-\u0E19\u0E32\u0E21\u0E2A\u0E01\u0E38\u0E25\u0009\u0009,\u0009\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48,\u0E2D\u0E31\u0E15\u0E23\u0E32,\u0009\u0E15\u0E33\u0E41\u0E2B\u0E19\u0E48\u0E07\u0E07\u0E32\u0E19,\u0009\u0E27\u0E38\u0E12\u0E34\u0E01\u0E32\u0E23\u0E28\u0E36\u0E01\u0E29\u0E32,\u0009\u0E40\u0E23\u0E34\u0E48\u0E21\u0E17\u0E33\u0E07\u0E32\u0E19,\u0009\u0E27\u0E31\u0E19\u0E40\u0E01\u0E34\u0E14,\u0009\u0E2A\u0E16\u0E32\u0E19\u0E20\u0E32\u0E1E\u0E17\u0E33\u0E07\u0E32\u0E19,\u0009\u0E1A\u0E31\u0E15\u0E23\u0E1B\u0E23\u0E30\u0E0A\u0E32\u0E0A\u0E19\u0009,\u0E27\u0E31\u0E19\u0E40\u0E01\u0E29\u0E35\u0E22\u0E13\u0E2D\u0E32\u0E22\u0E38,\u0009\u0E2B\u0E21\u0E32\u0E22\u0E40\u0E2B\u0E15\u0E38\u0009\u0E40\u0E04\u0E23\u0E37\u0E48\u0E2D\u0E07\u0E23\u0E32\u0E0A\u0E2F\u0E2A\u0E39\u0E07\u0E2A\u0E38\u0E14,\u0009   \u0E40\u0E1B\u0E47\u0E19/\u0E44\u0E21\u0E48\u0E40\u0E1B\u0E47\u0E19        \u0E2A\u0E21\u0E32\u0E0A\u0E34\u0E01, \u0E01.\u0E1A.\u0E02.\u0009,\u0E41\u0E2A\u0E14\u0E07\u0E40\u0E08\u0E15\u0E19\u0E32\u0E40\u0E1B\u0E25\u0E35\u0E48\u0E22\u0E19\u0E2A\u0E16\u0E32\u0E19\u0E20\u0E32\u0E1E\n";
			throw new Exception("not XSLX column : " + s);
		}
		
		logger.info("*******************************************************");
		
		readLoginData ( loginsheet);
		readPersonData(infomationsheet);
		insertData();
		
	}
	
	public void readPersonData(Sheet infomationsheet){
		logger.info("*************************readPersonData LOAD ***********************");
		int max = infomationsheet.getLastRowNum() + 1;
		listSuccessPerson = new ArrayList<Person>();
		listErrorPerson = new ArrayList<Person>();
		logger.info("## readPersonData max : " + max);
		for( int i = 1 ; i < max ; i++){
			Row  r =  infomationsheet.getRow(i);
			logger.info("## readPersonData ROW : " + (i+1));
			Person person = new Person();
			person.setFacultyDesc(getCellValue(r.getCell(1), 1));
			//person.setRegId(getCellValue(r.getCell(1), 1));
			person.setDepartmentDesc(getCellValue(r.getCell(2), 2));
			person.setEmployeeType(getCellValue(r.getCell(4), 4));
			
			person.setTitleName(getCellValue(r.getCell(5), 5));
			person.setThaiName(getCellValue(r.getCell(6), 6));
			person.setThaiSurname(getCellValue(r.getCell(7), 7));
			
			person.setRateNo(getCellValue(r.getCell(8), 8));
			person.setAcademicRank(getCellValue(r.getCell(9), 9));
			person.setMaxEducation(getCellValue(r.getCell(10), 10));
			
			person.setEmail("email");
			
			person.setPicture(null);
			person.setCitizenId(getCellValue(r.getCell(14), 17));
			person.setCreateBy(userName );
			person.setUpdateBy(userName);
			
			// working_date,birth_date  Column: 11,12
			System.out.println(" working Date Str:"+getCellValue(r.getCell(11), 11));
			person.setWorkingDate(BuckWaDateUtils.convertStr_MMddyyy_TH_to_Date(getCellValue(r.getCell(11), 11)));
			person.setBirthdate(BuckWaDateUtils.convertStr_MMddyyy_TH_to_Date(getCellValue(r.getCell(12), 12)));
			
			
			if(BeanUtils.isNotEmpty( person.getCitizenId())){
				listSuccessPerson.add(person);
			}else{
				listErrorPerson.add(person);
			}
			
			
		}
	}
	
	public void readLoginData (Sheet loginsheet){
		logger.info("*************************readLoginData LOAD ***********************");
		int max = loginsheet.getLastRowNum() + 1;
		listSuccessLogin = new ArrayList<User>();
		listErrorLogin = new ArrayList<User>();
		for( int i = 2 ; i < max ; i++){
			Row  r =  loginsheet.getRow(i);
			logger.info("## readLoginData ROW : " + (i+1)+"  of max row :"+max);
			User user = new User();
//						String value = getCellValue(r.getCell(ci), ci);
//						logger.info(" ### : " + value);
			
//			username, password,first_name,last_name,email,enable,secure_code
			user.setUsername(getCellValue(r.getCell(4), 4));
			user.setPassword("5f4dcc3b5aa765d61d8327deb882cf99");
			String name = getCellValue(r.getCell(3), 3);
			
		 
			
			StringTokenizer st = new StringTokenizer(name);
		 
		     user.setFirst_name(st.nextToken());
		     user.setLast_name(st.nextToken());
		     
		    	
		     
		 
			
			//user.setFirst_name(name.split(" ")[0]);
			//logger.info("## name 0: " + name.split(" ")[0]);
		//	user.setLast_name(name.split(" ")[1]);
			user.setEmail(getCellValue(r.getCell(4), 4));
			user.setEnabled(true);
			user.setIcNumber(getCellValue(r.getCell(2), 2));
			user.setRegId(getCellValue(r.getCell(1), 1));
			if(BeanUtils.isNotEmpty( user.getUsername()) && BeanUtils.isNotEmpty(user.getIcNumber())){
				listSuccessLogin.add(user);
			}else{
				user.setStatusRecord("Username OR IC number empty");
				listErrorLogin.add(user);
			}
			 
		}
//		ps.setString(1, person.getFacultyDesc()  );
//		ps.setString( 2 , person.getDepartmentDesc());
//		ps.setString( 3 , person.getEmployeeType());
//		ps.setString( 4 , person.getTitleName());
//		ps.setString( 5 , person.getThaiName());
//		ps.setString( 6 , person.getThaiSurname());
//		ps.setString( 7 , person.getRateNo());
//		 
//		ps.setString( 8 , person.getAcademicRank());
//		ps.setString( 9 , person.getMaxEducation());
//		ps.setString( 10 , person.getEmail());
//		ps.setString( 11, person.getPicture());
//		ps.setString( 12, person.getCreateBy());
//		ps.setString( 13, person.getUpdateBy());
	}
	
	public String getCellValue(Cell c ,int index){
		String v = "";
		try{
			
			switch (c.getCellType()) {
			
			case Cell.CELL_TYPE_STRING:
				logger.info("## CELL : " + index + " : " + c.getStringCellValue());
				v = c.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				

				    if (HSSFDateUtil.isCellDateFormatted(c)) { 
				           v=BuckWaDateUtils.get_MMddyyyy_thai_from_date(c.getDateCellValue()); 
				    }else{
				    	//c.setCellType(Cell.CELL_TYPE_STRING);
				    	logger.info("## CELL ปปปปป: " + index + " : " + c.getNumericCellValue()+" after read:"+v);
				    	Double numbervalue = c.getNumericCellValue();
				    	v = String.valueOf( (numbervalue==null)? "" : numbervalue.longValue()) ;
				    }
				 
				    logger.info("## CELL : " + index + " : " + c.getNumericCellValue()+" after read:"+v);
				
				break;
			default:
//				logger.info("## CELL OTHER: " + index + " : " + c.getStringCellValue());
				if(  DateUtil.isCellDateFormatted(c)){
					Date d = c.getDateCellValue();
					v = BuckWaDateUtils.get_MMddyyyy_thai_from_date(d);
					System.out.println(" Date Value :"+d);
				}
				break;
			}
			

			
			
			
			
		}catch(Exception e){
			logger.info("## CELL ERROR " );
			return null;
		}
		
		return v;
	}
	
	@RequestMapping(value = "process.htm", method = RequestMethod.GET)
	public ModelAndView loadFile(){
		long lastTime = System.currentTimeMillis();
		FileInputStream file = null,file2 = null;
		try {
			userName = BuckWaUtils.getUserNameFromContext();
			logger.info("***************** path ***********************");
			logger.info("fileByFecPath : " + fileByFecPath);
			logger.info("fileByAllPath : " + fileByAllPath);
			logger.info("**********************************************");
			
			file = new FileInputStream(new File(fileByFecPath));
			file2 = new FileInputStream(new File(fileByAllPath));
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook1 = new XSSFWorkbook (file);
			XSSFWorkbook workbook2 = new XSSFWorkbook (file2);
			
			readPBPProfileWorkbook(workbook1,workbook2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				file.close();
				file2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       
		logger.info("init");
		ModelAndView mav = new ModelAndView();
		
		if(listErrorLogin !=null && !listErrorLogin.isEmpty()){
			failList.addAll(listErrorLogin);
		}
		
		mav.addObject("okList", sucessList);
		mav.addObject("failList", failList);
		mav.setViewName("useruploadview");
		mav.addObject(BuckWaConstants.SUCCESS_CODE, BuckWaConstants.MSGCODE_IMPORT_SUCESS);

		mav.addObject("processTime", System.currentTimeMillis() - lastTime);
		return mav;

	}

	public void setFileByFecPath(String fileByFecPath) {
		this.fileByFecPath = fileByFecPath;
	}

	public void setFileByAllPath(String fileByAllPath) {
		this.fileByAllPath = fileByAllPath;
	}

	public void insertData(){
		sucessList = new ArrayList<User>();
		failList = new ArrayList<User>();
		logger.info("*************** INSERT DATA *********************");
		for(User u : listSuccessLogin){
		//	logger.info("*************** INSERT LOOP *********************");
		//	logger.info("*************** USERNAME  : " + u.getUsername());
			//find detail
			Person person = this.findPersonData( u.getIcNumber(),u.getRegId());
			
			if( person != null){
				person.setEmail( u.getEmail());
			}
			
			try{
				boolean isUserSuccess = false;
				User isExistUser = userRegistrationDao.getUserByUsernameFromBuckwa(u.getUsername());
				if(isExistUser == null){
					u.setEnabled(true);
					logger.info("## userRegistrationDao.create");
					u.setGroups(new String[]{"2"});
					u.setEnabled(true);
					u.setPassword("password");
					userRegistrationDao.create(u);
					u.setStatusRecord("CREATE");
					isUserSuccess = true;
				}else{
//					logger.info("## userRegistrationDao.update2");
					userRegistrationDao.updateUserRegister(u);
					u.setStatusRecord("UPDATE");
					isUserSuccess = true;
				}
				 
				boolean isPersonSucess = true;
				if(person != null){
					isPersonSucess = false;
					if( importProfileDao.isExistPerson( u.getUsername()) == false){
						//logger.info("## importProfileDao.createPBP");
						importProfileDao.createPBP(person);
						isPersonSucess = true;
					}else{
						//logger.info("## importProfileDao.updatePBP");
						//importProfileDao.updatePBP(person);
						importProfileDao.updatePBPNew(person);
						isPersonSucess = true;
					}
				}
				
				if(isUserSuccess && isPersonSucess){
					sucessList.add(u);
				}else{
					failList.add(u);
				}
			}catch(Exception e){
				logger.info("ERROR " + e.getMessage());
				failList.add(u);
				e.printStackTrace();
			}
			
		}
	}
	
	public Person findPersonData(String icNumber,String regId){
		Person person = null;
		
		if(icNumber == null) return person;
		
		for(Person p : listSuccessPerson){
			logger.info(" IC:"+icNumber+":"+p.getCitizenId());
			if(icNumber.equals( p.getCitizenId())){
				logger.info("findPersonData : "+p.getUsername() + icNumber + " : " + p.getCitizenId()+" RegId:"+regId);
				p.setRegId(regId);
				return p;
			}
		}
		
		logger.info("findPersonData : " + icNumber + " : NOT FOUND");
		return person; 
	}
}
