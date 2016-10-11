package com.buckwa.service.impl.pam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.dao.intf.pam.ImportProfileDao;
import com.buckwa.dao.intf.pam.ImportTimeTableDao;
import com.buckwa.dao.intf.userregistration.UserRegistrationDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;
import com.buckwa.domain.pam.TimeTable;
import com.buckwa.service.intf.pam.ImportFileService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.PAMConstants;
import com.buckwa.util.school.SchoolUtil;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

@Service("importProfileService")
 
public class ImportProfileServiceImpl implements ImportFileService {
	
	private static Logger logger = LoggerFactory.getLogger(ImportProfileServiceImpl.class);
	
	@Autowired
	private ImportProfileDao importProfileDao;
	
	@Autowired
	private UserRegistrationDao userRegistrationDao;
	
	@Autowired
	private ImportTimeTableDao importTimeTableDao;
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	
	public void readProfileWorkbook(Workbook workbook, HttpServletRequest httpRequest) {
		
		if (null != workbook) {
			
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("sheet name: " + sheet.getSheetName());
			logger.info("last row number: " + (sheet.getLastRowNum()+1));
			logger.info("sheet size: " + (sheet.getLastRowNum()+1));
			
			String academicYear = schoolUtil.getCurrentAcademicYear();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", BuckWaDateUtils.thaiLocale);
			
			List<LovDetail> lovSexList           = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_SEX);
			List<LovDetail> lovEmployeeTypeList  = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EMPLOYEE_TYPE);
			List<LovDetail> lovPositionList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_POSITION);
			List<LovDetail> lovWorkLineList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORK_LINE);
			List<LovDetail> lovFacultyList       = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_FACULTY);
			List<LovDetail> lovInsigniaList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_INSIGNIA);
			List<LovDetail> lovMarriedStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_MARRIED_STATUS);
			List<LovDetail> lovEducationList     = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EDUCATION);
			List<LovDetail> lovWorkingStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORKING_STATUS);
			String employeeTypeGovOfficeCode = "1"; // EmployeeType GovOfficer
			String workLineAcademicCode      = "1"; // Academic
			
			for (Row rows : sheet) {
				
				Person person = new Person();
				
				for (Cell cell : rows) {
					
					if (cell.getRowIndex() == 0 ) {
						continue;
					}
					
					int row = cell.getRowIndex();
					int col = cell.getColumnIndex();
					
					// A = 0
					// EmployeeId
					if (col == 0) {
						String value = "";
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getRichStringCellValue().getString().trim();
						}
						else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							value = String.valueOf((int) cell.getNumericCellValue());
						}
						else {
							RejectCell(row, col);
						}
						person.setEmployeeId(value);
					}
					
					// B = 1
					// CitizenId
					else if (col == 1) {
						String value = "";
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getRichStringCellValue().getString().trim();
						}
						else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							value = String.valueOf((int) cell.getNumericCellValue());
						}
						else {
							RejectCell(row, col);
						}
						person.setCitizenId(value);
					}
					
					// C = 2
					// ThaiName & ThaiSurname
					else if (col == 2) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							String[] values = value.split(" ");
							for (int i = 0; i < values.length; i++) {
								if (i == 0) {
									values[i] = BuckWaUtils.removeTitleFromThaiName(values[i]);
									person.setThaiName(values[i]);
								}
								else if (i > 0 && StringUtils.hasText(values[i])) {
									if (null == person.getThaiSurname()) {
										person.setThaiSurname("");
									}
									person.setThaiSurname(person.getThaiSurname() + " " + values[i]);
								}
							}
							if (null != person.getThaiSurname()) {
								person.setThaiSurname(person.getThaiSurname().trim());
							}
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// D = 3
					// Sex
					else if (col == 3) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setSex(value);
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// E = 4
					// E-Mail
					else if (col==4) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setEmail(value);
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// F = 5
					// Birth Date
					// TODO
					else if (col == 5){// Date
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							try {
								Date value = sdf.parse(cell.getRichStringCellValue().getString().trim());
								person.setBirthdate(value);
							} catch (ParseException e) {
								e.printStackTrace();
								logger.error(e.getMessage());
							}
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// G = 6
					// Rate No
					else if (col == 6) {
						String value = "";
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getRichStringCellValue().getString().trim();
						}
						else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							value = String.valueOf((int) cell.getNumericCellValue());
						}
						else {
							RejectCell(row, col);
						}
						person.setRateNo(value);
					}
					
					// H = 7
					// Employee Type
					else if (col == 7) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setEmployeeType(getLovCode(lovEmployeeTypeList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// I = 8
					// Position
					else if (col == 8) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setPosition(getLovCode(lovPositionList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// J = 9
					// Work Line
					else if (col==9) {
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							person.setWorkLine(getLovCode(lovWorkLineList, value));
						}else{
							RejectCell(row, col);
						}
					}
					
					// K = 10
					// Level
					else if (col==10) {
						String value = "";
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getRichStringCellValue().getString().trim();
						}
						else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
							value = String.valueOf((int) cell.getNumericCellValue());
						}
						else {
							RejectCell(row, col);
						}
						person.setLevel(value);
					}
					
					// L = 11
					// Salary
					else if (col==11) {// BigDecimal
						if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							BigDecimal value = new BigDecimal(cell.getNumericCellValue());
							person.setSalary(value);
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// M = 12
					// Faculty
					else if (col == 12) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setFaculty(getLovCode(lovFacultyList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// N = 13
					// Institution
					
					// O = 14
					// Province
					
					// P = 15
					// Assign Date
					// TODO
					else if(col==15){// Date
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							try {
								Date value = sdf.parse(cell.getRichStringCellValue().getString().trim());
								person.setAssignDate(value);
							} catch (ParseException e) {
								e.printStackTrace();
								logger.error(e.getMessage());
							}
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// Q = 16
					// Working Date
					// TODO
					else if(col==16){// Date
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							try {
								Date value = sdf.parse(cell.getRichStringCellValue().getString().trim());
								person.setWorkingDate(value);
							} catch (ParseException e) {
								e.printStackTrace();
								logger.error(e.getMessage());
							}
						}
						else{
							RejectCell(row, col);
						}
					}
					
					// R = 17
					// Work Telephone No.
					else if (col == 17) {
						String value = "";
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getRichStringCellValue().getString().trim();
						}
						else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							value = Integer.toString((int) cell.getNumericCellValue());
						}
						else {
							RejectCell(row, col);
						}
						person.setWorkTelNo(value);
					}
					
					// S = 18
					// Working Status
					else if (col == 18) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setWorkingStatus(getLovCode(lovWorkingStatusList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// T = 19
					// Married Status
					else if (col == 19) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setMarriedStatus(getLovCode(lovMarriedStatusList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// U = 20
					// Max Insignia
					else if (col == 20) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setMaxInsignia(getLovCode(lovInsigniaList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// V = 21
					// Max Education
					else if (col == 21) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setMaxEducation(getLovCode(lovEducationList, value));
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// AO = 40
					// Picture
					else if (col == 40) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							if (value == null || "".equals(value)) {
								value = PAMConstants.PROFILE_PICTURE_NO_PICTURE_JPG;
							}
							person.setPicture(value);
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// Faculty
					else if (col == 41) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setFacultyDesc(value);
						}
						else {
							RejectCell(row, col);
						}
					}
					
					// Department 
					else if (col == 42) {
						
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setDepartmentDesc(value);
						}
						else {
							RejectCell(row, col);
						}
						
		
					}
					
				}// End for (Cell cell : rows)
				
				if(rows.getRowNum()>0){
					
					// Verify PersonType
					if (employeeTypeGovOfficeCode.equals(person.getEmployeeType())
						|| workLineAcademicCode.equals(person.getWorkLine())) {
						person.setPersonType(PAMConstants.PERSON_TYPE_CODE_GOV_OFFICER);
					}
					else {
						person.setPersonType(PAMConstants.PERSON_TYPE_CODE_OFFICER);
					}
					
					try {
						
						person.setCreateBy(httpRequest.getUserPrincipal().getName());
						person.setUpdateBy(httpRequest.getUserPrincipal().getName());
						
						String username = person.getEmail();
						logger.info("Email&User to Register > "+username);
						
						int userExist = importProfileDao.findByUsername(username);
						
						if(userExist == 0){
							// ######## To Inset To talble Person  ########
							Long personKey = importProfileDao.create(person);
							// ##### To Register User #####
							userRegistrationDao.createUserFromUpload(username.trim());
							
							logger.info("Key > "+personKey);
						
						}
						else{
							logger.info(" ## User name :"+username+" Aready exist , So Update !!!");
							
							importProfileDao.update(person);
						}
						
						
						// ################ Academic Person #########
						
						int academicUserExist = importProfileDao.findAcademicPersonByUsername(username,academicYear);
						person.setAcademicYear(academicYear);
						
						if(academicUserExist == 0){
							// ######## To Inset To talble Person  ########
							Long personKey = importProfileDao.createAcademicPerson(person);

						
						}
						else{
							logger.info(" ## User name :"+username+" Aready exist , So Update !!!");
							
							importProfileDao.updateAcademicPerson(person);
						}						
						
						
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// End if(rows.getRowNum()>0)
			}// End for (Row rows : sheet)

		}
		else {
			logger.info("No Worksheet to read");
		}

	}

	
	
	public void readPBPProfileWorkbook(Workbook workbook, HttpServletRequest httpRequest) {
		

		
		if (null != workbook) {
			
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("sheet name: " + sheet.getSheetName());
			logger.info("last row number: " + (sheet.getLastRowNum()+1));
			logger.info("sheet size: " + (sheet.getLastRowNum()+1));
			
			String academicYear = schoolUtil.getCurrentAcademicYear();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", BuckWaDateUtils.thaiLocale);
 
			String employeeTypeGovOfficeCode = "1"; // EmployeeType GovOfficer
			String workLineAcademicCode      = "1"; // Academic
			/*
			1. �ѧ�Ѵ	: facultyDesc :person.faculty_desc :������ǡ�����ʵ��
			2. ��ǹ�ҹ: departmentDesc :person.department_desc	: �Ң��Ԫ����ǡ����ä��Ҥ�
			3. ������:employeeType :person.employee_type:����Ҫ���
			4. �ӹ�˹��:titleName :person.title_name:���
			5. ����:firstName :person.thai_name:��
			6. ���ʡ��:lastName	 :person.thai_surname:����Ѳ��ѭ��
			7. �Ţ����ѵ��:rateNo :person.rate_no:107
			8. ���˹觧ҹ:academicRank :person.academic_rank:�ͧ��ʵ�Ҩ����
			9. �زԡ���֡��:maxEducation :person.max_education:�.�͡
			10. Email :person.email:1@kmitl.ac.th
				 */
			for (Row rows : sheet) {				
				Person person = new Person();				
				for (Cell cell : rows) {					
					if (cell.getRowIndex() == 0 ) {
						continue;
					}
					if (cell.getRowIndex() == 1 ) {
						continue;
					}					
					int row = cell.getRowIndex();
					int col = cell.getColumnIndex();		
					
					
					// 1:  �ѧ�Ѵ	: facultyDesc :person.faculty_desc :������ǡ�����ʵ��
					if (col == 1) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setFacultyDesc(value);
						} else {
							RejectCell(row, col);
						}
					}					
					
					// 2: ��ǹ�ҹ: departmentDesc :person.department_desc	: �Ң��Ԫ����ǡ����ä��Ҥ�
					if (col == 2) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setDepartmentDesc(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
					
					// 3: ������:employeeType :person.employee_type:����Ҫ���
					if (col == 3) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setEmployeeType(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
					
					
					// 4:�ӹ�˹��:titleName :person.title_name:���
					if (col == 4) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setTitleName(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
										
					
					// 5:����:thaiName :person.thai_name:��
					if (col == 5) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setThaiName(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
										
					
					
					// 6:���ʡ��:thaiName	 :person.thai_surname:����Ѳ��ѭ��
					if (col ==6) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setThaiSurname(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
										
					
					
					// 7:�Ţ����ѵ��:rateNo :person.rate_no:107
					if (col == 7) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setRateNo(value);
						} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							String value = Integer.toString((int) cell.getNumericCellValue());
							person.setRateNo(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
										
					
					
					// 8:���˹觧ҹ:academicRank :person.academic_rank:�ͧ��ʵ�Ҩ����
					if (col == 8) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setAcademicRank(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
										
					// 9:�زԡ���֡��:maxEducation :person.max_education:�.�͡
					if (col ==9) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setMaxEducation(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
											
					
					// 10:Email :person.email:1@kmitl.ac.th
					if (col == 10) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							person.setEmail(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
						
	
				}// End for (Cell cell : rows)
				
				if(rows.getRowNum()>0){		 
					
					try {
						
						person.setCreateBy(httpRequest.getUserPrincipal().getName());
						person.setUpdateBy(httpRequest.getUserPrincipal().getName());
						
						String username = person.getEmail();
						logger.info("Email&User to Register > "+username);
						
						int userExist = importProfileDao.findByUsername(username);
						
						if(userExist == 0){
							// ######## To Inset To talble Person  ########
							//Long personKey = importProfileDao.create(person);
							Long personKey = importProfileDao.createPBP(person);
							// ##### To Register User #####
							userRegistrationDao.createUserFromUpload(username.trim());
							
							logger.info("Key > "+personKey);
						
						}
						else{
							logger.info(" ## User name :"+username+" Aready exist , So Update !!!");
							
							importProfileDao.updatePBP(person);
						}
 
						
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// End if(rows.getRowNum()>0)
			}// End for (Row rows : sheet)

		}
		else {
			logger.info("No Worksheet to read");
		}

	}

	
	
	public void readStudentInSecWorkbook(Workbook workbook, HttpServletRequest httpRequest) {
		

		
		if (null != workbook) {
			
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("sheet name: " + sheet.getSheetName());
			logger.info("last row number: " + (sheet.getLastRowNum()+1));
			logger.info("sheet size: " + (sheet.getLastRowNum()+1));
			
			String academicYear = schoolUtil.getCurrentAcademicYear();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", BuckWaDateUtils.thaiLocale);
 
			String employeeTypeGovOfficeCode = "1"; // EmployeeType GovOfficer
			String workLineAcademicCode      = "1"; // Academic
	 
			for (Row rows : sheet) {				
				Section domain = new Section();				
				for (Cell cell : rows) {					
					if (cell.getRowIndex() == 0 ) {
						continue;
					}
					if (cell.getRowIndex() == 1 ) {
						continue;
					}					
					int row = cell.getRowIndex();
					int col = cell.getColumnIndex();		
					
			 
					if (col == 0) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setSubjectId(value);
						} else {
							RejectCell(row, col);
						}
					}	
					
					if (col == 1) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setAcademicYear(value);
						} else {
							RejectCell(row, col);
						}
					}					
					
					
	 
					if (col == 2) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setSemester(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
					
					if (col == 3) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setSectionNumber(value);
						}
						else {
							RejectCell(row, col);
						}
					}				 
						
					if (col == 4) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setTotalStudent(value);
						}
						else {
							RejectCell(row, col);
						}
					}		
	
				}// End for (Cell cell : rows)
				
				if(rows.getRowNum()>0){		 
					
					try { 
						
 
							   
				 importProfileDao.updateTotalStudent(domain);
							 
				 
	 
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// End if(rows.getRowNum()>0)
			}// End for (Row rows : sheet)

		}
		else {
			logger.info("No Worksheet to read");
		}

	}
	
	
	public void readStaffWorkbook(Workbook workbook, HttpServletRequest httpRequest) {
		

		
		if (null != workbook) {
			
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("sheet name: " + sheet.getSheetName());
			logger.info("last row number: " + (sheet.getLastRowNum()+1));
			logger.info("sheet size: " + (sheet.getLastRowNum()+1));
			
			String academicYear = schoolUtil.getCurrentAcademicYear();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", BuckWaDateUtils.thaiLocale);
 
			String employeeTypeGovOfficeCode = "1"; // EmployeeType GovOfficer
			String workLineAcademicCode      = "1"; // Academic
	 
			for (Row rows : sheet) {				
				Staff domain = new Staff();				
				for (Cell cell : rows) {					
					if (cell.getRowIndex() == 0 ) {
						continue;
					}
					if (cell.getRowIndex() == 1 ) {
						continue;
					}					
					int row = cell.getRowIndex();
					int col = cell.getColumnIndex();		
					
			 
					if (col == 0) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setStaffCode(value);
						} else {
							RejectCell(row, col);
						}
					}					
					
	 
					if (col == 2) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String value = cell.getRichStringCellValue().getString().trim();
							domain.setFullName(value);
						}
						else {
							RejectCell(row, col);
						}
					}	
					
					 
						
	
				}// End for (Cell cell : rows)
				
				if(rows.getRowNum()>0){		 
					
					try {
						
						domain.setCreateBy(httpRequest.getUserPrincipal().getName());
						domain.setUpdateBy(httpRequest.getUserPrincipal().getName());
						
						
						String staffCode = domain.getStaffCode();
						logger.info("  staffCode > "+staffCode);
						
						int userExist = importProfileDao.findByStaffCode(staffCode);
						
						if(userExist == 0){
							   
							 Long personKey = importProfileDao.createStaff(domain);
							 
				 
						//	logger.info("Key > "+personKey);
						
						}
						else{
							logger.info(" ## Staff Code  :"+staffCode+" Aready exist , So Update !!!");
							 
							
							importProfileDao.updateStaff(domain);
						}
 
						
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// End if(rows.getRowNum()>0)
			}// End for (Row rows : sheet)

		}
		else {
			logger.info("No Worksheet to read");
		}

	}
	
	@Override
	public void readTimeTableWorkbook(Workbook workbook, HttpServletRequest httpRequest) {
	if (null != workbook) {
			
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("sheet name: " + sheet.getSheetName());
			logger.info("last row number: " + (sheet.getLastRowNum()+1));
			logger.info("sheet size: " + (sheet.getLastRowNum()+1));
			
			for (Row rows : sheet) {
				
				TimeTable timeTable = new TimeTable();
				
				for (Cell cell : rows) {
					
					if (cell.getRowIndex() == 0 ) {
						continue;
					}
					
					int row = cell.getRowIndex();
					int col = cell.getColumnIndex();
					
					if(col==0){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setFacultyId(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setFacultyId(value);;
						}else{
							RejectCell(row, col);
						}
					}else if(col==1){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setDeptId(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setDeptId(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==2 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setCurrId(Integer.parseInt(value));
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							Integer value = (int) cell.getNumericCellValue();
							timeTable.setCurrId(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==3 ){
						if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setCurrId2(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==4 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setSubjectId(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setSubjectId(value);
						}else{
							RejectCell(row, col);
						}
						
					}else if(col==5 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setSemester(Integer.parseInt(value));
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							Integer value = (int) cell.getNumericCellValue();
							timeTable.setSemester(value);
						}else{
							RejectCell(row, col);
						}
						
						
					}else if(col==6 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setYear(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setYear(value);
						}else{
							RejectCell(row, col);
						}
						
					}else if(col==7 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setLevel(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setLevel(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==8 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setProgram(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==9){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setSectionCd(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setSectionCd(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==10){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setTeacherId(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setTeacherId(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==11){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setLectOrPrac(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==12){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setPriority(Integer.parseInt(value));
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							Integer value = (int) cell.getNumericCellValue();
							timeTable.setPriority(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==13){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setTimeStamp(value);
						}else{
							RejectCell(row, col);
						}
					}
							
				}
				
				if(rows.getRowNum()>0){
					logger.info("TIME TABLE > "+BeanUtils.getBeanString(timeTable));
					try {
						timeTable.setCreateBy(httpRequest.getUserPrincipal().getName());
						timeTable.setUpdateBy(httpRequest.getUserPrincipal().getName());
						
						if(importTimeTableDao.getCount(timeTable) == 0){
							importTimeTableDao.create(timeTable);
						}
						timeTable.setTimeTableCd(importTimeTableDao.getTimeTableId(timeTable));
						if( importTimeTableDao.checkSection(timeTable) == 0){
							importTimeTableDao.addSection(timeTable);
						}
						timeTable.setSectionId(importTimeTableDao.getSectionId(timeTable));
						if(importTimeTableDao.checkStaff(timeTable) == 0){
							importTimeTableDao.addstaff(timeTable);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		else {
			logger.info("No Worksheet to read");
		}
		importTimeTableDao.updateStaff();

	}

	
	@Override
	public void readTimeTableDescWorkbook(Workbook workbook, HttpServletRequest httpRequest) {
	if (null != workbook) {
			
			Sheet sheet = workbook.getSheetAt(0);
			logger.info("sheet name: " + sheet.getSheetName());
			logger.info("last row number: " + (sheet.getLastRowNum()+1));
			logger.info("sheet size: " + (sheet.getLastRowNum()+1));
			
			for (Row rows : sheet) {
				
				TimeTable timeTable = new TimeTable();
				
				for (Cell cell : rows) {
					
					if (cell.getRowIndex() == 0 ) {
						continue;
					}
					
					int row = cell.getRowIndex();
					int col = cell.getColumnIndex();
					
					if(col==0){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setSubjectId(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setSubjectId(value);;
						}else{
							RejectCell(row, col);
						}
					}else if(col==1){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setEngName(value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							String value = Integer.toString((int) cell.getNumericCellValue());
							timeTable.setEngName(value);
						}else{
							RejectCell(row, col);
						}
					}else if(col==2 ){
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							String value = cell.getRichStringCellValue().getString().trim();
							timeTable.setThaiName( value);
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							Integer value = (int) cell.getNumericCellValue();
							timeTable.setThaiName(value+"");
						}else{
							RejectCell(row, col);
						}
					 
					}
							
				}
				
				if(rows.getRowNum()>0){
					logger.info("TIME TABLE > "+BeanUtils.getBeanString(timeTable));
					try {
						timeTable.setCreateBy(httpRequest.getUserPrincipal().getName());
						timeTable.setUpdateBy(httpRequest.getUserPrincipal().getName());
						
						 
							importTimeTableDao.updateDesc(timeTable);
			 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		else {
			logger.info("No Worksheet to read");
		}
		importTimeTableDao.updateStaff();

	}

	
	
	public void RejectCell(int row , int column) {
		logger.info("Reject >> Row : " +row+" <=> Column : "+column+" >> Cell value is not a correct ! ");
	}
	
	public BuckWaResponse importTimeTableSql(BuckWaRequest request){
		logger.info("VacationLeaveServiceImpl.flowProcess");
		BuckWaResponse response = new BuckWaResponse();
		try {
			List<String> fileList = (List) request.get("fileList");
			boolean result = importTimeTableDao.executeSqlScript(fileList);
			if(result)
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
			else{
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E001");
			}
				
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
	
	private String getLovCode(List<LovDetail> lovDetailList, String name) {
		String code = "0";
		for (LovDetail lovDetail : lovDetailList) {
			if (lovDetail.getName().equals(name)) {
				code = lovDetail.getCode();
				break;
			}
		}
		return code;
	}
	
}
