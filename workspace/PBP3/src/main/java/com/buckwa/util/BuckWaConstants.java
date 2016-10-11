package com.buckwa.util;

import java.io.Serializable;

public class BuckWaConstants implements Serializable{
	
	public static int SUCCESS =0;
	public static int FAIL =1;
	
	
	public static String SUCCESS_CODE ="successCode";
	public static String ERROR_CODE ="errorCode";
	public static String PAGE_SELECT ="PAGE_SELECT";
	public static String PERSON_INIT="PERSON_INIT";
	public static String ADMIN_INIT="ADMIN_INIT";
	public static String ROLE_INIT="ROLE_INIT";
	public static String LEAVE_INIT="LEAVE_INIT";
	public static String EVALUATE_INIT="EVALUATE_INIT";
	public static String REPORT_INIT="REPORT_INIT";

	//## Role
	public static String ROLE_ADMIN="ROLE_ADMIN";
	public static String ROLE_USER="ROLE_USER";
	public static String ROLE_STORE_ADMIN="ROLE_STORE_ADMIN";
	public static String ROLE_STORE_USER="ROLE_STORE_USER";
	public static String ROLE_OFFICER="ROLE_OFFICER";

	//## Group
	public static String GROUP_ADMIN="GROUP_ADMIN";
	public static String GROUP_USER="GROUP_USER";
	public static String GROUP_STORE_ADMIN="GROUP_STORE_ADMIN";
	public static String GROUP_STORE_USER="GROUP_STORE_USER";
	
	public static String ENABLE ="0";
	public static String DISABLE ="1";
	
	public static String ENABLE_TXT ="ENABLE";
	public static String DISABLE_TXT ="DISABLE";
	
	public static String STATUS_Y ="Y";
	public static String STATUS_N ="N";
	
	public static String ACTIVE="A";
	public static String INACTIVE="I";
	
	public static boolean ACTIVE_BOOLEAN=true;
	public static boolean INACTIVE_BOOLEAN=false;
	
	
	public static String IS_FOUND ="isFound";
	
	public static String NOT_FOUND ="N";
	public static String FOUND="Y";
	
	
	public static String ERROR_E001="E001";
	public static String ERROR_E002="E002";
	
	public static String NO_DATA_FOUND ="E014";
	
	// Image Type
	public static String USER_IMAGE_TYPE_SIGNATURE ="SINGNATURE";
	
	//Leave Status
	public static String L_INPROCESS = "LP001";
	public static String L_APPROVE = "LP002";
	public static String L_REJECT = "LP003";
	public static String L_CANCEL = "LP004";
	public static String L_CANCEL_LEAVE = "LP005";
	
	//Leave Type 
	public static String LEAVE_RESEARCH = "L001";
	public static String LEAVE_VACATION = "L002";
	public static String LEAVE_SICK = "L003";
	public static String LEAVE_PERSONAL = "L004";
	public static String LEAVE_MATERNITY = "L005";
	public static String LEAVE_MONKHOOD = "L006";
	public static String LEAVE_CANCEL = "L007";
	public static String EMPTY ="EMPTY";
	
	//Research Type
	public static String L_RESEARCH_TYPE_R001="R001";
	public static String L_RESEARCH_TYPE_R002="R002";
	public static String L_RESEARCH_TYPE_R003="R003";
	public static String L_RESEARCH_TYPE_R004="R004";
	public static String L_RESEARCH_TYPE_R005="R005";
	
	//Leave MSG
	public static String L_CREATE_COMPLETE="L_COMPLETE";
	public static String L_REJECT_COMPLETE="L_REJECT";
	public static String L_CANCEL_COMPLETE="L_CANCEL";
	public static String L_APPROVE_COMPLETE="L_APPROVE";
	public static String L_NOT_EVER="L_NOT_EVER";
	public static String L_EVER="L_EVER";
	
	// --> Import/Export
	public static String BROWSER_MSIE ="MSIE"; // For Encode file name when download
	
	public static String PROCESS_SEARCH="S";
	public static String PROCESS_IMPORT="I";
	public static String PROCESS_EXPORT = "E";
	public static String PROCESS_DELETE = "D";
	
	public static String MSGCODE_IMPORT_SUCESS = "S001";
	public static String MSGCODE_EDIT_SUCESS = "S002";
	public static String MSGCODE_DELETE_SUCESS="S004";
	public static String MSGCODE_SELECT_FILE="E012";
	public static String MSGCODE_FILE_TOO_LARGE="E013";
	public static String MSGCODE_FILE_NAME_EXIST="E016";
	public static String MSGCODE_FILE_EXTENSION_INVALID="E019";
	public static String MSGCODE_FILE_EXTENSION_INVALID_JPG="E020";
	
	
	
	public static String PERSON_CODE = "PC";
	public static String TIMETABLE_CODE = "TC";
	public static String WORKPERSON_CODE = "WC";

	public static String PERSON_TABLE = "person";
	public static String TIMETABLE_TABLE = "timetable";
	public static String WORKPERSON_TABLE = "work_person";

	public static String PATH_UPLOAD_PROFILE = "profile/";
	public static String PATH_UPLOAD_TIMETABLE= "timetable/";
	public static String PATH_UPLOAD_WORKPERSON= "workperson/";
	public static String PATH_UPLOAD_WORKPERSON_TEMP= PATH_UPLOAD_WORKPERSON + "temp/";
	
	// --> Evaluate <KP>
	public static String EVALUATE_WAIT="W";
	public static String EVALUATE_PROCESS="P";
	public static String EVALUATE_SUCCESS="S";
	
	public static Integer LIMIT_KPI_NAME_LEVEL_1= 25;
	public static Integer LIMIT_KPI_NAME_LEVEL_2= 30;
	public static Integer LIMIT_KPI_NAME_LEVEL_3= 25;
	public static Integer LIMIT_KPI_NAME_SCORE = 15;
	
	public static String ACTION_EDIT="E";
	public static String ACTION_VIEW="V";
	
	public static int MAX_LEFT_PADDING=5;
	public static String LEFT_PADDING_CHAR="0";
	
	public static final String EVALUATE_TYPE_TEACHER   = "123456";
	public static final String EVALUATE_TYPE_STAFF = "0";
	public static final String EVALUATE_TYPE_EXT_STAFF = "3";
	
	public static final String EVALUATE_REPORT_NAME = "\u0E41\u0E1A\u0E1A\u0E1B\u0E23\u0E30\u0E40\u0E21\u0E34\u0E19\u0E1C\u0E25\u0E01\u0E32\u0E23\u0E1B\u0E0F\u0E34\u0E1A\u0E31\u0E15\u0E34\u0E23\u0E32\u0E0A\u0E01\u0E32\u0E23";
	public static final String EVALUATE_REPORT_TEMPLATE = "report/evaluate_template.jasper";
	
	public static final String EVALUATE_TEACHER_ID   = "11";
	public static final String EVALUATE_STAFF_ID = "12";
	
	public static final String EVALUATE_TERM_1 = "1";
	public static final String EVALUATE_TERM_2 = "2";
	
	public static final String EVALUATE_ALLOWED = "Y";
	public static final String EVALUATE_NOT_ALLOWED = "N";
	
	public static final String GENDER_MALE   = "M";
	public static final String GENDER_FEMALE = "F";
	
	
	//Time table 
	public static final String SUBJECT_CODE   = "\u0e23\u0e2b\u0e31\u0e2a\u0e27\u0e34\u0e0a\u0e32";
	public static final String TH_NAME   = "\u0e0a\u0e37\u0e48\u0e2d\u0e27\u0e34\u0e0a\u0e32\u0e20\u0e32\u0e29\u0e32\u0e44\u0e17\u0e22";
	public static final String EN_NAME   = "\u0e0a\u0e37\u0e48\u0e2d\u0e27\u0e34\u0e0a\u0e32\u0e20\u0e32\u0e29\u0e32\u0e2d\u0e31\u0e07\u0e01\u0e24\u0e29";
	public static final String CREDIT   = "\u0e2b\u0e19\u0e48\u0e27\u0e22\u0e01\u0e34\u0e15";
	public static final String LECT_HR   = "\u0e08\u0e33\u0e19\u0e27\u0e19\u0e0a\u0e31\u0e48\u0e27\u0e42\u0e21\u0e07\u0e17\u0e24\u0e29\u0e0e\u0e35";
	public static final String PRAC_HR   = "\u0e08\u0e33\u0e19\u0e27\u0e19\u0e0a\u0e31\u0e48\u0e27\u0e42\u0e21\u0e07\u0e1b\u0e0f\u0e34\u0e1a\u0e31\u0e15\u0e34";
	public static final String SELF_HR   = "\u0e08\u0e33\u0e19\u0e27\u0e19\u0e0a\u0e31\u0e48\u0e27\u0e42\u0e21\u0e07\u0e28\u0e36\u0e01\u0e29\u0e32\u0e14\u0e49\u0e27\u0e22\u0e15\u0e19\u0e40\u0e2d\u0e07";
	public static final String DETAILS   = "\u0e23\u0e32\u0e22\u0e25\u0e30\u0e40\u0e2d\u0e35\u0e22\u0e14\u0e27\u0e34\u0e0a\u0e32";
	

}
