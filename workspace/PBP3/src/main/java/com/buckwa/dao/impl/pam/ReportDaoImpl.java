package com.buckwa.dao.impl.pam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.ReportDao;
import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.ReportPersonByFaculty;
import com.buckwa.domain.pam.ReportPersonSummary;
import com.buckwa.domain.pam.SummaryLeave;

/*
@Author : Taechapon Himarat (Su)
@Create : Oct 18, 2012 10:53:43 PM
 */
@Repository("reportDao")
public class ReportDaoImpl implements ReportDao {
	
	private static Logger logger = LoggerFactory.getLogger(ReportDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	
	private static final String SQL_SUMMARY_LEAVE =
		" SELECT a.leavetype_code, " +
		"   SUM(a.amount) AS total, " +
		"   COUNT(a.amount) AS amount " +
		" FROM leave_summary a " +
		" INNER JOIN leave_type b ON a.leavetype_code = b.code " +
		" WHERE a.STATUS = 'A' " +
		"   AND a.userid = ? " +
		"   AND (a.created_date >= ? AND a.created_date <= ?) " +
		" GROUP BY a.leavetype_code ";
	
	@Override
	public List<SummaryLeave> getSummaryLeaveByUserIdAndSemester(Long userId, Date fromDate, Date toDate) {
		logger.info("- start");
		
		List<SummaryLeave> resultList = this.jdbcTemplate.query(SQL_SUMMARY_LEAVE, summaryLeaveMapper, new Object[] {
			userId,
			fromDate,
			toDate
		});
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + SQL_SUMMARY_LEAVE);
			logger.info("userId:" + userId);
			logger.info("fromDate:" + fromDate);
			logger.info("toDate:" + toDate);
			if (null != resultList) {
				logger.info("resultList.size(): " + resultList);
			}
		}
		
		return resultList;
	}
	
	private static final SummaryLeaveMapper summaryLeaveMapper = new SummaryLeaveMapper();
	private static class SummaryLeaveMapper implements RowMapper<SummaryLeave> {
		@Override
		public SummaryLeave mapRow(ResultSet rs, int rowNum) throws SQLException {
			SummaryLeave domain = new SummaryLeave();
			domain.setLeaveTypeCode(rs.getString("leavetype_code"));
			domain.setTotal(rs.getInt("total"));
			domain.setAmount(rs.getInt("amount"));
			return domain;
		}
	}

	
	// --------------------------------------------------
	
	
	private static final String SQL_REPORT_PERSON_SUMMARY =
		" SELECT " +
		"   detail.code AS code, " +
		"   detail.name AS employee_type, " +
		"   SUM(CASE WHEN (p.work_line = 1 AND p.working_status = 2) THEN 1 ELSE 0 END) AS teacher_working, " +
		"   SUM(CASE WHEN (p.work_line = 1 AND p.working_status = 3) THEN 1 ELSE 0 END) AS teacher_leave, " +
		"   SUM(CASE WHEN (p.work_line = 2 AND p.position = 6) THEN 1 ELSE 0 END) AS research, " +
		"   SUM(CASE WHEN (p.work_line = 2 AND p.position != 6) THEN 1 ELSE 0 END) AS support " +
		" FROM person p " +
		"   INNER JOIN lov_detail AS detail " +
		"     ON detail.code = p.employee_type " +
		"   INNER JOIN lov_header AS header " +
		"     ON header.lov_header_id = detail.lov_header_id " +
		"     AND header.lov_header_id = 2 " +
		" GROUP BY detail.name ";
	
	@Override
	public List<ReportPersonSummary> getPersonSummaryReport() {
		logger.info("- start");
		
		List<ReportPersonSummary> resultList = this.jdbcTemplate.query(SQL_REPORT_PERSON_SUMMARY, personSummaryMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + SQL_REPORT_PERSON_SUMMARY);
			if (null != resultList) {
				logger.info("resultList.size(): " + resultList.size());
			}
		}
		
		return resultList;
	}
	
	private static final PersonSummaryMapper personSummaryMapper = new PersonSummaryMapper();
	private static class PersonSummaryMapper implements RowMapper<ReportPersonSummary> {
		@Override
		public ReportPersonSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReportPersonSummary domain = new ReportPersonSummary();
			domain.setCode(rs.getString("code"));
			domain.setEmployeeType(rs.getString("employee_type"));
			domain.setTeacherWorking(rs.getInt("teacher_working"));
			domain.setTeacherLeave(rs.getInt("teacher_leave"));
			domain.setTeacherTotal(
				domain.getTeacherWorking() +
				domain.getTeacherLeave()
			);
			domain.setResearch(rs.getInt("research"));
			domain.setSupport(rs.getInt("support"));
			domain.setPersonTotal(
				domain.getTeacherTotal() +
				domain.getResearch() + 
				domain.getSupport()
			);
			return domain;
		}
	}
	
	
	// --------------------------------------------------
	
	
	private static final String SQL_REPORT_PERSON_BY_FACULTY =
		" SELECT " +
		"   detail.code AS code, " +
		"   detail.name AS faculty, " +
		"   SUM(CASE WHEN p.position = 1 THEN 1 ELSE 0 END) AS teacher, " +
		"   SUM(CASE WHEN p.position = 2 THEN 1 ELSE 0 END) AS professor_assistant, " +
		"   SUM(CASE WHEN p.position = 3 THEN 1 ELSE 0 END) AS associate_professor, " +
		"   SUM(CASE WHEN p.position = 4 THEN 1 ELSE 0 END) AS professor, " +
		"   SUM(CASE WHEN p.max_education = 5 THEN 1 ELSE 0 END) AS bachelor_degree, " +
		"   SUM(CASE WHEN p.max_education = 4 THEN 1 ELSE 0 END) AS master_degree, " +
		"   SUM(CASE WHEN p.max_education = 2 THEN 1 ELSE 0 END) AS doctor_degree " +
		" FROM person p " +
		"   INNER JOIN lov_detail AS detail " +
		"     ON detail.code = p.faculty " +
		"   INNER JOIN lov_header AS header " +
		"     ON header.lov_header_id = detail.lov_header_id " +
		"     AND header.lov_header_id = 5 " +
		" GROUP BY detail.name ";
	
	@Override
	public List<ReportPersonByFaculty> getPersonByFacultyReport() {
		logger.info("- start");
		
		List<ReportPersonByFaculty> resultList = this.jdbcTemplate.query(SQL_REPORT_PERSON_BY_FACULTY, personByFacultyMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + SQL_REPORT_PERSON_BY_FACULTY);
			if (null != resultList) {
				logger.info("resultList.size(): " + resultList.size());
			}
		}
		
		return resultList;
	}
	
	private static final PersonByFacultyMapper personByFacultyMapper = new PersonByFacultyMapper();
	private static class PersonByFacultyMapper implements RowMapper<ReportPersonByFaculty> {
		@Override
		public ReportPersonByFaculty mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReportPersonByFaculty domain = new ReportPersonByFaculty();
			domain.setCode(rs.getString("code"));
			domain.setFaculty(rs.getString("faculty"));
			domain.setPositionTeacher(rs.getInt("teacher"));
			domain.setPositionProfessorAssistant(rs.getInt("professor_assistant"));
			domain.setPositionAssociateProfessor(rs.getInt("associate_professor"));
			domain.setPositionProfessor(rs.getInt("professor"));
			domain.setPositionTotal(
				domain.getPositionTeacher() +
				domain.getPositionProfessorAssistant() +
				domain.getPositionAssociateProfessor() +
				domain.getPositionProfessor()
			);
			domain.setEducationBachelorDegree(rs.getInt("bachelor_degree"));
			domain.setEducationMasterDegree(rs.getInt("master_degree"));
			domain.setEducationDoctorDegree(rs.getInt("doctor_degree"));
			domain.setEducationTotal(
				domain.getEducationBachelorDegree() +
				domain.getEducationMasterDegree() +
				domain.getEducationDoctorDegree()
			);
			return domain;
		}
	}
	
	
	// --------------------------------------------------
	
	
	private static final String SQL_GET_KPI_SUMMARY_SCORE_PART_1 =
		" SELECT kt.kpi_tree_id, " +
		"   kt.name, " +
		"   kt.mark, " +
		"   wpattr.value, " +
		"   wpattr.flag_cal " +		
//		"   COUNT(wpattr.value) AS count, " +
//		"   SUM(CASE WHEN wpattr.flag_cal = 1 THEN kt.mark*wpattr.value ELSE kt.mark*1 END) AS summary_score " +
		" FROM work_person_kpi wp, " +
		"   kpi_tree kt, " +
		"   work_person_attr wpattr " +
		" WHERE wp.kpi_id = kt.kpi_tree_id " +
		" 	AND wp.kpi_id <> 0 " +
		" 	AND wp.work_person_attr_id IN ( " +
		" 	  SELECT work_person_attr_id " +
		" 	  FROM work_person_attr " +
		" 	  WHERE work_person_id IN ( " +
		" 	    SELECT work_person_id " +
		" 	    FROM work_person " +
		" 	    WHERE user_id IN ( ";
	
	private static final String SQL_GET_KPI_SUMMARY_SCORE_PART_2 =
		" 	    ) " +
		"     ) " +
		" 	) " +
		" 	AND wpattr.work_person_attr_id = wp.work_person_attr_id " +
		" 	AND kt.year_id = ? ";
//		" GROUP BY kt.kpi_tree_id ";
	
	@Override
	public List<KpiPersonEvaluateMapping> getKpiSummaryScoreList(String yearId) {
		logger.info("- start");
		
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_KPI_SUMMARY_SCORE_PART_1);
		sql.append(" SELECT user_id ");
		sql.append(" FROM buckwauser ");
		sql.append(SQL_GET_KPI_SUMMARY_SCORE_PART_2);
		
		List<KpiPersonEvaluateMapping> resultList = this.jdbcTemplate.query(sql.toString(), new Object[] {
			yearId
		},
		kpiPersonValuateMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql.toString());
			logger.info("yearId: " + yearId);
			if (null != resultList) {
				logger.info("resultList.size(): " + resultList.size());
			}
		}
		
		return resultList;
	}
	
	
	@Override
	public List<KpiPersonEvaluateMapping> getKpiSummaryScoreList(String yearId, List<String> userIdList) {
		logger.info("- start");
		
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_KPI_SUMMARY_SCORE_PART_1);
		if (userIdList != null && userIdList.size() > 0) {
			for (int i = 0; i < userIdList.size(); i++) {
				sql.append("?,");
			}
			if (userIdList != null && !userIdList.isEmpty()) {
				sql.deleteCharAt(sql.length() - 1);
			}
			userIdList.add(yearId);
		}
		else {
			sql.append("NULL");
			userIdList = new ArrayList<String>();
			userIdList.add(yearId);
		}
		sql.append(SQL_GET_KPI_SUMMARY_SCORE_PART_2);
		
		List<KpiPersonEvaluateMapping> resultList = this.jdbcTemplate.query(sql.toString(), userIdList.toArray(),
			kpiPersonValuateMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql.toString());
			if (null != resultList) {
				logger.info("resultList.size(): " + resultList.size());
			}
		}
		
		return resultList;
	}
	
	
	private static final KpiPersonEvaluateMapper kpiPersonValuateMapper = new KpiPersonEvaluateMapper();
	private static class KpiPersonEvaluateMapper implements RowMapper<KpiPersonEvaluateMapping> {   						
		@Override
		public  KpiPersonEvaluateMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
			KpiPersonEvaluateMapping domain = new  KpiPersonEvaluateMapping(); 		
			domain.setKpiId(rs.getLong("kpi_tree_id"));
			domain.setKpiMarkScore(rs.getBigDecimal("mark"));
			domain.setFlag_cal(rs.getBigDecimal("flag_cal"));
			domain.setCal_value(rs.getString("value"));
			return domain;
		}
	}

}
