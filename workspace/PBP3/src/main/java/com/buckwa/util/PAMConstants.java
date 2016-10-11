package com.buckwa.util;

import java.io.Serializable;
import java.util.ResourceBundle;

public class PAMConstants implements Serializable{
	
	public static final ResourceBundle rbApp     = ResourceBundle.getBundle("application");
	public static final ResourceBundle rbMessage = ResourceBundle.getBundle("messages", BuckWaDateUtils.thaiLocale);
	
	public static String MARK_TYPE_0 ="0";
	public static String MARK_TYPE_1 ="1";

	
	public static String LEC_OR_PRAC = "LEC_OR_PRAC";
	public static String DEGREE_LEVEL = "DEGREE_LEVEL";
	public static String THAI_SHORT_DATE  = "THAI_SHORT_DATE";
	
	// Lov Code
	public static final String LOV_CODE_SEX            = "SEX";
	public static final String LOV_CODE_EMPLOYEE_TYPE  = "EMPLOYEE_TYPE";
	public static final String LOV_CODE_POSITION       = "POSITION";
	public static final String LOV_CODE_WORK_LINE      = "WORK_LINE";
	public static final String LOV_CODE_FACULTY        = "FACULTY";
	public static final String LOV_CODE_INSIGNIA       = "INSIGNIA";
	public static final String LOV_CODE_MARRIED_STATUS = "MARRIED_STATUS";
	public static final String LOV_CODE_EDUCATION      = "EDUCATION ";
	public static final String LOV_CODE_WORKING_STATUS = "WORKING_STATUS ";
	
	public static final String LOV_MAX_EDCUATION = "MAX_EDCUATION ";
	public static final String LOV_ACADEMIC_RANK = "LOV_ACADEMIC_RANK ";
	
	// Person Type
	public static final String PERSON_TYPE_CODE_GOV_OFFICER = "0";
	public static final String PERSON_TYPE_CODE_OFFICER     = "1";
	
	// Profile picture - NoPicture.jpg
	public static final String PROFILE_PICTURE_NO_PICTURE_JPG = "NoPicture.jpg";
	
	// Title
	public static final String TITLE_MR  = rbMessage.getString("label.title.mr");
	public static final String TITLE_MS  = rbMessage.getString("label.title.ms");
	public static final String TITLE_MRS = rbMessage.getString("label.title.mrs");
	
}
