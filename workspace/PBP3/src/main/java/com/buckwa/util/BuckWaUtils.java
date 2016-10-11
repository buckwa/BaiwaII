package com.buckwa.util;

import java.security.MessageDigest;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.pam.Leave;
import com.buckwa.domain.pam.LeaveTotal;
import com.buckwa.domain.pam.Person;

public class BuckWaUtils {
	private static Logger logger = LoggerFactory.getLogger(BuckWaUtils.class);
	private static final DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
	private static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

	public static String getUserNameFromContext() throws BuckWaException {

		String returnValue = "";
		try {
			BuckWaUser user = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 
			returnValue = user.getUsername();
		} catch (Exception ex) {
			ex.printStackTrace();
			// User Not Found
			throw new BuckWaException("E003", "");

		}

		return returnValue;
	}
	
	public static String getFullNameFromContext() throws BuckWaException {

		String returnValue = "";
		try {
			BuckWaUser user = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 
			returnValue = user.getFirstLastName();
		} catch (Exception ex) {
			ex.printStackTrace();
			// User Not Found
			throw new BuckWaException("E003", "");

		}

		return returnValue;
	}

	public static String getUserIdFromContext() throws BuckWaException {

		String returnValue = "";
		try {
			BuckWaUser user = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			returnValue = user.getUserId() + "";

		} catch (Exception ex) {
			ex.printStackTrace();
			// User Not Found
			throw new BuckWaException("E003", "");

		}

		return returnValue;
	}

	public static BuckWaUser getUserFromContext() throws BuckWaException {

		BuckWaUser userReturn = null;
		try {
			userReturn = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		} catch (Exception ex) {
			// ex.printStackTrace();
			// User Not Found
			// throw new BuckWaException("E003","");

		}

		return userReturn;
	}
	
	public static String getFacultyCodeFromUserContext() throws BuckWaException {

		String facultyCode = null;
		try {
			BuckWaUser buckwaUser = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Person person =buckwaUser.getPersonProfile();
			facultyCode = person.getFacultyCode();
			person.getFacultyDesc();
		 	//logger.info("   ########### facultyDesc:"+person.getFacultyDesc());
		} catch (Exception ex) {
			 ex.printStackTrace();
			// User Not Found
			// throw new BuckWaException("E003","");

		}

		return facultyCode;
	}
	
	public static String getFacultyDescFromUserContext() throws BuckWaException {

		String returnStr = null;
		try {
			BuckWaUser buckwaUser = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Person person =buckwaUser.getPersonProfile();
			//facultyCode = person.getFacultyCode();
			returnStr =person.getFacultyDesc();
		 	//logger.info("   ########### facultyDesc:"+person.getFacultyDesc());
		} catch (Exception ex) {
			 ex.printStackTrace();
			// User Not Found
			// throw new BuckWaException("E003","");

		}

		return returnStr;
	}
	

	public static boolean ifAnyGROUP(String groupName) {
		boolean returnResult = false;
		try {
			BuckWaUser user = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> grantedAutority = user.getAuthorities();
			for (GrantedAuthority tmp : grantedAutority) {
				//logger.info("  xxxxxx:" + tmp.getAuthority());
				if (BuckWaConstants.ROLE_STORE_ADMIN.equals(tmp.getAuthority())) {
					//logger.info("  Found Match ing :" + tmp.getAuthority());
					return true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnResult;
	}

	public static boolean isRole(String roleName) {
		boolean returnResult = false;
		try {
			BuckWaUser user = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> grantedAutority = user.getAuthorities();
			for (GrantedAuthority tmp : grantedAutority) {
				if (roleName.equals(tmp.getAuthority())) {
					//logger.info(" Found Match ing :" + tmp.getAuthority());
					return true;
				}
			}
		} catch (Exception ex) {
			//logger.info(" No User Session !!!!! ");
			//ex.printStackTrace();
		}
		return returnResult;
	}

	public static String getGroupIdFromGroupName(String groupName, List<Group> groupList) {
		String returnstr = "";
		try {
			for (Group tmp : groupList) {
				if (groupName.equalsIgnoreCase(tmp.getGroupName())) {
					return tmp.getGroupId();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnstr;
	}

	public static String str2DigitsStr(String str) {
		double d;
		try {
			d = Double.parseDouble(replaceNull(str));
		} catch (Exception e) {
			return "0.00";
		}
		return df.format(d);
	}

	public static String replaceNull(String s) {
		return (s == null || s.equals("null")) ? "" : s;
	}

	public static String getSecureCode(String userName) {
		String returnResult = "";
		try {

			Random randomDate = new Random(10000);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, randomDate.nextInt());
			Random randomTime = new Random(cal.getTimeInMillis());
			long randomlong = randomTime.nextLong();
			String strbeforeEncode = userName + randomlong;

			returnResult = strbeforeEncode;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnResult;

	}

	public static synchronized String encrypt(String str) {
		String returnpassEncode = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hashedPwd = md.digest();
			// String passwordEncode = HexUtils.toHexString(hashedPwd);
			String passwordEncode = toHexString(hashedPwd);

			returnpassEncode = passwordEncode;
			/*
			 * String kryptPwd = new String(string);
			 * logger.info(" kryptPwd:"+kryptPwd); Cipher ecipher =
			 * Cipher.getInstance("DES"); logger.info(" ecipher:"+ecipher); //
			 * Encode the string into bytes using utf-8 byte[] utf8 =
			 * str.getBytes("UTF-8"); // Encrypt byte[] enc =
			 * ecipher.doFinal(utf8); // Encode bytes to base64 to get a string
			 * return new sun.misc.BASE64Encoder().encode(enc);
			 * 
			 * 
			 * 
			 * 
			 * } catch (javax.crypto.BadPaddingException e) {
			 * e.printStackTrace(); } catch (IllegalBlockSizeException e) {
			 * e.printStackTrace(); } catch (UnsupportedEncodingException e) {
			 * e.printStackTrace();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnpassEncode;
	}

	public static boolean isValidPassword(String password) {
		boolean returnResult = false;
		try {
			Pattern pattern;
			Matcher matcher;
			// String PASSWORD_PATTERN =
			// "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,10})";
			String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Za-z]).{6,10})";
			pattern = Pattern.compile(PASSWORD_PATTERN);
			matcher = pattern.matcher(password);
			returnResult = matcher.matches();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnResult;
	}

	public static String generatePASS(int passlength) {
		StringBuffer sb = new StringBuffer();

		sb.append("1");
		for (int x = 0; x < passlength - 1; x++) {
			char tmpStr = (char) ((int) (Math.random() * 26) + 97);
			sb.append(tmpStr);
		}
		//logger.info(" pass:" + sb.toString());
		return sb.toString();
	}

	public static String getUserFileNameByOriginalFileName(String originalFileName, String imageType) {
		String returnVal = "";
		try {
			String originalFileExtension = FileUtils.getFileExtension(originalFileName);
			returnVal = imageType + BuckWaUtils.getUserIdFromContext() + "." + originalFileExtension;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnVal;
	}

	public static String getPasswordEncode(String passPlainText) {
		String returnEncodeTxt = "";
		try {
			// StandardPasswordEncoder encoder = new
			// StandardPasswordEncoder("secret");
			// String result = encoder.encode("myPassword");
			// assertTrue(encoder.matches("myPassword", result));

			// PasswordEncoder encoder = PasswordEn
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnEncodeTxt;
	}

	public static String toHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(toHex(bytes[i] >> 4));
			sb.append(toHex(bytes[i]));
		}

		return sb.toString();
	}

	private static char toHex(int nibble) {
		final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		return hexDigit[nibble & 0xF];
	}

	public static String generateDocNo(Long docNo) {
		int docNoL = 0;
		if (docNo != null) {
			docNoL = String.valueOf(docNo).length();
		}
		StringBuffer docTemp = new StringBuffer();
		do {
			docTemp.append("0");
			docNoL++;
		} while (docNoL <= 9);

		docTemp.append(String.valueOf(docNo));
		return docTemp.toString();
	}

	public static Leave setFlowActionButton(Leave leave, boolean isOfficer) {
		if (!isOfficer) {
			if (leave.getFlowStatus().equals(BuckWaConstants.L_INPROCESS)) {
				if (leave.getOwnerId() == leave.getCurrentUserId())
					leave.setCancelAction("true");
			}
		} else {
			if (leave.getFlowStatus().equals(BuckWaConstants.L_INPROCESS)) {
				if (leave.getOwnerId() == leave.getCurrentUserId())
					leave.setCancelAction("true");
				leave.setApproveAction("true");
			}
		}

		// leave can cancel when status is approve
		if (leave.getIsCancel() == 0 && leave.getFlowStatus().equals(BuckWaConstants.L_APPROVE)
				&& leave.getOwnerId() == leave.getCurrentUserId()) {
			leave.setCancalLeaveAction("true");
		}

		return leave;
	}

	public static String getFullName(String firstName, String lastName) {
		String fullName = "";
		if (firstName != null) {
			fullName = firstName.concat(" ").concat(lastName != null ? lastName : "");
		}
		return fullName.trim();

	}

	public static String getTimeToEnd(Time timeFrom, Time timeEnd) {
		String fullTime = "";
		if (timeFrom != null) {
			fullTime = BuckWaDateUtils.convertTime(timeFrom);
			if (timeEnd != null)
				fullTime = fullTime.concat("-").concat(BuckWaDateUtils.convertTime(timeEnd));
		}
		return fullTime.trim();

	}

	public static LeaveTotal countDay(Date fromDate, Date toDate) {
		int dayTotal = 0;
		LeaveTotal leaveTotal = new LeaveTotal();
		if (fromDate != null && toDate != null) {
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(fromDate);
			startCal.set(Calendar.HOUR_OF_DAY, 0);
			startCal.set(Calendar.MINUTE, 0);
			startCal.set(Calendar.SECOND, 0);

			Calendar endCal = Calendar.getInstance();
			endCal.setTime(toDate);
			endCal.set(Calendar.HOUR_OF_DAY, 0);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);

			Calendar tempCal = Calendar.getInstance();
			if (startCal.before(endCal) || startCal.equals(endCal)) {
				int year = 0;
				int month = 0;
				int day = 0;
				do {
					if (day == 30) {
						month++;
						day = 0;
					}
					if (month == 12) {
						year++;
						month = 0;
					}

					if (startCal.get(Calendar.DAY_OF_WEEK) != 1 && startCal.get(Calendar.DAY_OF_WEEK) != 7) {
						day++;
						dayTotal++;
					}
					startCal.add(Calendar.DATE, 1);
				} while (startCal.before(endCal) || startCal.equals(endCal));
				leaveTotal.setDay(day);
				leaveTotal.setMonth(month);
				leaveTotal.setYear(year);
				leaveTotal.setDayTotal(dayTotal);
			}
		}
		return leaveTotal;
	}

	public static LeaveTotal countDay(Date fromDate, Date toDate, List<Holiday> holidays) {
		int dayTotal = 0;
		LeaveTotal leaveTotal = new LeaveTotal();
		if (fromDate != null && toDate != null) {
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(fromDate);
			startCal.set(Calendar.HOUR_OF_DAY, 0);
			startCal.set(Calendar.MINUTE, 0);
			startCal.set(Calendar.SECOND, 0);

			Calendar endCal = Calendar.getInstance();
			endCal.setTime(toDate);
			endCal.set(Calendar.HOUR_OF_DAY, 0);
			endCal.set(Calendar.MINUTE, 0);
			endCal.set(Calendar.SECOND, 0);

			Calendar tempCal = Calendar.getInstance();
			if (startCal.before(endCal) || startCal.equals(endCal)) {
				int year = 0;
				int month = 0;
				int day = 0;
				boolean flag = false;
				do {
					flag = false;
					if (day == 30) {
						month++;
						day = 0;
					}
					if (month == 12) {
						year++;
						month = 0;
					}

					if (startCal.get(Calendar.DAY_OF_WEEK) != 1 && startCal.get(Calendar.DAY_OF_WEEK) != 7) {
						if (holidays != null && holidays.size() > 0) {
							for (Holiday holiday : holidays) {
								if (holiday.isEnable()) {
									Calendar holidayD = Calendar.getInstance();
									holidayD.setTime(BuckWaDateUtils.parseDate(holiday.getHolidayDate()));
									holidayD.set(Calendar.HOUR_OF_DAY, 0);
									holidayD.set(Calendar.MINUTE, 0);
									holidayD.set(Calendar.SECOND, 0);
									if (holidayD.equals(startCal)) {
										flag = true;
										break;
									}
								}

							}
						}
						if (!flag) {
							day++;
							dayTotal++;
						}
					}
					startCal.add(Calendar.DATE, 1);
				} while (startCal.before(endCal) || startCal.equals(endCal));
				leaveTotal.setDay(day);
				leaveTotal.setMonth(month);
				leaveTotal.setYear(year);
				leaveTotal.setDayTotal(dayTotal);
			}
		}
		return leaveTotal;
	}

	public static long countAllDay(Date fromDate, Date toDate) {
		long day = 0;
		if (fromDate != null && toDate != null) {
			day = (toDate.getTime() - fromDate.getTime()) / MILLISECONDS_IN_DAY;
		}
		return day;
	}

	// public static Calendar getCalendarByDayOfWeek(int dayOfWeek){
	// Calendar startCal = Calendar.getInstance();
	// startCal.setTime(obj.getStartDate());
	// startCal.set(Calendar.HOUR_OF_DAY, 0);
	// startCal.set(Calendar.MINUTE, 0);
	// startCal.set(Calendar.SECOND, 0);
	// }
	
	public static String removeTitleFromThaiName(String thaiName) {
		
		if (thaiName != null && !"".equals(thaiName)) {
			if (thaiName.length() >= 6) {
				if (PAMConstants.TITLE_MS.equals(thaiName.substring(0, PAMConstants.TITLE_MS.length()))) {
					thaiName = thaiName.substring(PAMConstants.TITLE_MS.length());
				}
			}
			if (thaiName.length() >= 3) {
				if (PAMConstants.TITLE_MRS.equals(thaiName.substring(0, PAMConstants.TITLE_MRS.length()))) {
					thaiName = thaiName.substring(PAMConstants.TITLE_MRS.length());
				}
				else if (PAMConstants.TITLE_MR.equals(thaiName.substring(0, PAMConstants.TITLE_MR.length()))) {
					thaiName = thaiName.substring(PAMConstants.TITLE_MR.length());
				}
			}
		}
		
		return thaiName;
	}
	
	public static String getBrowserNameFromUserAgent(String userAgent){
		
		String returnType="";
		
		// #### User Agent :Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36
		//
		return returnType;
	}
	
	public static void main(String arg[]) {

		logger.info(" encrypt of chawean is :" + encrypt("password"));

	}
}
