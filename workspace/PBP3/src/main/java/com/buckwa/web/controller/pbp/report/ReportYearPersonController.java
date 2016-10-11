package com.buckwa.web.controller.pbp.report;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.MaternityLeave;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.PAMConstants;

import eu.bitwalker.useragentutils.UserAgent;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
 

@Controller
@RequestMapping("/report")
@SessionAttributes(types = MaternityLeave.class)
public class ReportYearPersonController{
	
	private static Logger logger = LoggerFactory.getLogger(ReportYearPersonController.class);	
	
	@Autowired
	private PersonProfileService personProfileService;
	
	@Autowired
	private PBPWorkTypeService pbpWorkTypeService;
	
	@RequestMapping(value="/printReportYear.htm", method = RequestMethod.GET)
	public void printReportYear(HttpServletRequest httpRequest, HttpServletResponse httpResponse,@RequestParam("round") String round,@RequestHeader("User-Agent") String userAgent) {
//		Person person = null;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			System.out.println(" #### User Agent :"+userAgent);
			UserAgent userAgentObj = UserAgent.parseUserAgentString(httpRequest.getHeader("User-Agent"));
			
			String browserType = userAgentObj.getBrowser().getName();
			System.out.println("browserType:"+browserType);
			//System.out.println("browserType:"+userAgentObj.getBrowser().getName() + " " + userAgentObj.getBrowserVersion());
			
			
			httpResponse.setContentType("application/force-download");		
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = null;
			
			String year = httpRequest.getParameter("year");
			//String round = httpRequest.getParameter("round");
			System.out.println(" #### Round :"+round);
		 	 
			try{				
				Integer.parseInt(round);
				
			}catch(Exception ex){
				round ="1";
				
			}
	 
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			logger.info("username :" + user.getUsername());
			logger.info("year :" + year);
			


			request.put("username", user.getUsername());
			request.put("academicYear", year);
			

			Person person = new Person();
			response = personProfileService.getByUsername(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
			}
			
			
			String facultyCode = person.getFacultyCode();
			 
			request.put("userName",BuckWaUtils.getUserNameFromContext());
			request.put("round",round);
			request.put("employeeType",person.getEmployeeTypeNo());
			request.put("facultyCode",facultyCode);
			
			String sumPoint1 = "0";
			String sumPoint2 = "0";
			String sumPoint3 = "0";
			String sumPoint4 = "0";
			String sumPoint5 = "0";
			String sumPointAll = "0";
			
			Date todayDate = new Date();
			String reportFrom = "-";
			String reportTo = "-";
			
			response = pbpWorkTypeService.getCalculateByAcademicYear(request);
			List<PersonReport>  reportList = new ArrayList<ReportYearPersonController.PersonReport>();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
				pBPWorkTypeWrapper.setAcademicYear(year);
				person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper); 
				reportList =getPersonReportList(pBPWorkTypeWrapper);
				sumPoint1 = getSumPoint(reportList,"1");
				sumPoint2 = getSumPoint(reportList,"2");
				sumPoint3 = getSumPoint(reportList,"3");
				sumPoint4 = getSumPoint(reportList,"4");
				sumPoint5 = getSumPoint(reportList,"5");
				
				sumPointAll = getSumPoint(reportList,"ALL");
				
				Date startRoundDate = pBPWorkTypeWrapper.getStartRoundDate();
				Date endRoundDate = pBPWorkTypeWrapper.getEndRoundDate();
				
				
				
				// Add For Specific case
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date endRoundDateHardCode = sdf.parse("31/07/2016");
				
		 
				
				if(null!= startRoundDate){
					reportFrom = new SimpleDateFormat("d").format(startRoundDate)
						+ " "+new SimpleDateFormat("MMMMM", new Locale("th", "TH")).format(startRoundDate)+" "
						+ new SimpleDateFormat("yyyy", new Locale("th", "TH")).format(startRoundDate);
				}
				
//				if(null!= endRoundDate){
//					reportTo = new SimpleDateFormat("d").format(endRoundDate)
//						+ " "+new SimpleDateFormat("MMMMM", new Locale("th", "TH")).format(endRoundDate)+" "
//						+ new SimpleDateFormat("yyyy", new Locale("th", "TH")).format(endRoundDate);
//				}
				

				if(null!= endRoundDateHardCode){
				reportTo = new SimpleDateFormat("d").format(endRoundDateHardCode)
					+ " "+new SimpleDateFormat("MMMMM", new Locale("th", "TH")).format(endRoundDateHardCode)+" "
					+ new SimpleDateFormat("yyyy", new Locale("th", "TH")).format(endRoundDateHardCode);
			}
				
				
			}	
			
			String reportDate = new SimpleDateFormat("d").format(todayDate)
					+" "+new SimpleDateFormat("MMMMM", new Locale("th", "TH")).format(todayDate)
					+ " พ.ศ. " + new SimpleDateFormat("yyyy", new Locale("th", "TH")).format(todayDate);
					
			
			String age = "-";
			if(null!=person.getBirthdate()){
				age = todayDate.getYear() - person.getBirthdate().getYear() + "";
			}
			params.put("reportDate", reportDate);
			params.put("fullName", person.getThaiName()+" "+person.getThaiSurname());
			params.put("fromDate", reportFrom);
			params.put("toDate", reportTo);
			params.put("position", person.getAcademicRank());
			params.put("salaryNo", person.getRateNo());
			params.put("unit", person.getFacultyDesc());
			params.put("degree", person.getMaxEducation());
			params.put("age",  age);
			
			String empType ="พนักงาน";
			if("ข้าราชการ".equals(person.getEmployeeType())){
				empType ="ข้าราชการ";
			}
			params.put("employeeType", empType);
			
			logger.info(" employeeTypeซ" + empType);

			if(null!=person.getWorkingDate()){
				String startDayStr = new SimpleDateFormat("d", new Locale("th", "TH")).format(person.getWorkingDate());
				params.put("startWorkDay", startDayStr);
				
				String startMonthStr = new SimpleDateFormat("MMMMM", new Locale("th", "TH")).format(person.getWorkingDate());
			//	params.put("startWorkMonth",person.getWorkingDate().getMonth());
				params.put("startWorkMonth",startMonthStr);
				
				String worintDateStr =  new SimpleDateFormat("yyyy", new Locale("th", "TH")).format(person.getWorkingDate());
				//params.put("startWorkYear", person.getWorkingDate().getYear());
				params.put("startWorkYear",worintDateStr);
				 
// 				int wyear = todayDate.getYear() - person.getWorkingDate().getYear();
//				int wmont = Math.abs(todayDate.getMonth() - person.getWorkingDate().getMonth());
//				
//				System.out.println(" total year:"+todayDate.getYear()+"-"+person.getWorkingDate().getYear()+"="+wyear);
//				System.out.println(" total month:"+todayDate.getMonth()+"-"+person.getWorkingDate().getMonth()+"="+wmont);
//				wyear = wyear<0? 0:wyear;
//				wmont = wmont<0? 0:wmont;
				
				long[] dt = differenceBetweenDates(person.getWorkingDate(), new Date());
				
				params.put("sumWorkYear", dt[2]);
				params.put("sumWorkMonth", dt[1]);
			}else{
				params.put("startWorkDay","-");
				params.put("startWorkMonth","-");
				params.put("startWorkYear", "-");
				params.put("sumWorkYear", "-");
				params.put("sumWorkMonth", "-");
			}
			
			params.put("moreWorkReport", "");
			
			
			//-- sumpoint ---
			params.put("sumPoint1", sumPoint1);
			params.put("sumPoint2", sumPoint2);
			params.put("sumPoint3", sumPoint3);
			params.put("sumPoint4", sumPoint4);
			params.put("sumPoint5", sumPoint5);
			
			params.put("sumPointAll", sumPointAll);
			
			String reportPath = PAMConstants.rbApp.getString("report.path");	
			logger.info("reportPath :" + reportPath);
			String reportFile =  reportPath+"person_yearly_report.jasper";	
			logger.info("reportFile :" + reportFile);
			String subReportFileName = reportPath+"person_yearly_report_detail.jrxml";	
			logger.info("subReportFileName :" + subReportFileName);
			params.put("SUBREPORT_DIR", reportPath);
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile, params, new JRBeanCollectionDataSource(reportList));
			JasperReport reportDetail = JasperCompileManager.compileReport(subReportFileName);
			
			params.put("subreportParameter", reportDetail);
			
			String fileNamex = person.getThaiName()+"_"+person.getThaiSurname()+"_"+year+"_"+round+".pdf";
			String fileName =URLEncoder.encode(fileNamex,"UTF-8");
			
			httpResponse.setContentType("application/pdf; charset=UTF-8");
			httpResponse.setCharacterEncoding("UTF-8");
	        if(browserType.contains("IE")||browserType.contains("Chrome"))
	        	httpResponse.setHeader("Content-Disposition","attachment; filename="+fileName);
	        if(browserType.contains("Firefox")){
	        	httpResponse.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+fileName);
	        }
 
	 
			JasperExportManager.exportReportToPdfStream(jasperPrint,  httpResponse.getOutputStream()); 
			
			
			logger.info("Output File Name :" + fileName);
//			ServletOutputStream outputStream = httpResponse.getOutputStream();
			//httpResponse.setHeader("Content-Disposition", "attachment; filename="+user.getUsername()+"_"+year+"_"+round+".pdf");
				
			
//			outputStream.close();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private String getSumPoint(List<PersonReport> reportList, String type){
		BigDecimal sum = BigDecimal.ZERO;
		for (PersonReport personReport : reportList) {
			String worktypeCode = personReport.getWorkGroup();
			if(type.equals(worktypeCode)){		
				return personReport.getSumPoint();
			}
			sum = sum.add(new BigDecimal(personReport.getSumPoint()));
		}
		return sum.toString();
	}
	


	private List<PersonReport> getPersonReportList(PBPWorkTypeWrapper pBPWorkTypeWrapper){
		 
			List<PBPWorkType> workTypeList =pBPWorkTypeWrapper.getpBPWorkTypeList();
			List<PersonReport> personReportList = new ArrayList();
			
			for(PBPWorkType  tmp:workTypeList){
				
				String worktypeCode =tmp.getCode();
				String worktypeName = tmp.getName();
				BigDecimal totalInworktype =tmp.getTotalInWorkType();
				
					PersonReport personReport = new PersonReport();						
					List<Map> workList = new ArrayList<Map>();				
					personReport.setWorkGroup(worktypeCode);
					personReport.setTitle(worktypeName);
					personReport.setSumPoint(totalInworktype+"");												
					List<AcademicKPIUserMapping> mappingList =tmp.getAcademicKPIUserMappingList();	
					int run = 1;
					for(AcademicKPIUserMapping mappingTmp:mappingList){			
						List<AcademicKPIAttributeValue> attributeList =	mappingTmp.getAcademicKPIAttributeValueList();
						AcademicKPIAttributeValue attributeTmp =attributeList.get(0);
						if("APPROVED".equals(mappingTmp.getStatus())){
							BigDecimal totalMark =mappingTmp.getTotalInMapping();;							
							System.out.println(" VAlue:"+attributeTmp.getValue()+ " Mark:"+totalMark);							
							Map<String, Object> workMap = new HashMap<String, Object>();
							workMap.put("work", run+". "+attributeTmp.getValue());
							workMap.put("point", totalMark);
							workList.add(workMap);		
							run++;
						}
					}						
					personReport.setWorkList(workList);
					personReportList.add(personReport);
			}
		 
		 return personReportList;
		
	}
	
	public static class PersonReport{
		private String workGroup;
		private String title;
		private String sumPoint;
		private List<Map> workList;
		
		public String getWorkGroup() {
			return workGroup;
		}
		public void setWorkGroup(String workGroup) {
			this.workGroup = workGroup;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getSumPoint() {
			return sumPoint;
		}
		public void setSumPoint(String sumPoint) {
			this.sumPoint = sumPoint;
		}
		public List<Map> getWorkList() {
			return workList;
		}
		public void setWorkList(List<Map> workList) {
			this.workList = workList;
		} 
	}
	
	public static long[] differenceBetweenDates(Date fromDate, Date toDate) {
	    Calendar startDate = Calendar.getInstance();
	    startDate.setTime(fromDate);
	    long years = 0;
	    long months = 0;
	    long days = 0;
	    Calendar endDate = Calendar.getInstance();
	    endDate.setTime(toDate);
	    Calendar tmpdate = Calendar.getInstance();
	    tmpdate.setTime(startDate.getTime());

	    tmpdate.add(Calendar.YEAR, 1);
	    while (tmpdate.compareTo(endDate) <= 0) {
	        startDate.add(Calendar.YEAR, 1);
	        tmpdate.add(Calendar.YEAR, 1);
	        years++;
	    }
	    tmpdate.setTime(startDate.getTime());
	    tmpdate.add(Calendar.MONTH, 1);
	    while (tmpdate.compareTo(endDate) <= 0) {
	        startDate.add(Calendar.MONTH, 1);
	        tmpdate.add(Calendar.MONTH, 1);
	        months++;
	    }
	    tmpdate.setTime(startDate.getTime());
	    tmpdate.add(Calendar.DATE, 1);
	    while (tmpdate.compareTo(endDate) <= 0) {
	        startDate.add(Calendar.DATE, 1);
	        tmpdate.add(Calendar.DATE, 1);
	        days++;
	    }
	    return new long[]{days, months, years};
	}
	
	public static void main(String[] args) {
		
		
		try {
			Date d2 = DateUtils.parseDate("25/11/1996", new String[] {"dd/MM/yyyy"});
//			Date d1 = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
			long[] dt = differenceBetweenDates(d2, new Date());
			System.out.println(dt[0]);
			System.out.println(dt[1]);
			System.out.println(dt[2]);
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}

