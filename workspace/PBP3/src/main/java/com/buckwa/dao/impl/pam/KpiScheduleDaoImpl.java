package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.KpiScheduleDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiSchedule;
import com.buckwa.util.MySqlUtil;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 30, 2012 1:19:47 AM
 */
@Repository("kpiScheduleDao")
public class KpiScheduleDaoImpl implements KpiScheduleDao {
	
	private static Logger logger = LoggerFactory.getLogger(KpiScheduleDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	private static final String SQL_GET_ALL = 
		" SELECT kpi_schedule_id, " +
		"   year_name, " +
		"   first_schedule_teacher_upload_start_date, " +
		"   first_schedule_teacher_upload_end_date, " +
		"   first_schedule_teacher_evaluate_start_date, " +
		"   first_schedule_teacher_evaluate_end_date, " +
		"   second_schedule_teacher_upload_start_date, " +
		"   second_schedule_teacher_upload_end_date, " +
		"   second_schedule_teacher_evaluate_start_date, " +
		"   second_schedule_teacher_evaluate_end_date, " +
		"   first_schedule_staff_upload_start_date, " +
		"   first_schedule_staff_upload_end_date, " +
		"   first_schedule_staff_evaluate_start_date, " +
		"   first_schedule_staff_evaluate_end_date, " +
		"   status " +
		" FROM kpi_schedule " +
		" WHERE status = 'A' ";
	
	@Override
	public PagingBean getByOffset(PagingBean pagingBean) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new LinkedList<Object>();
		
		KpiSchedule kpiSchedule = (KpiSchedule) pagingBean.get("kpiSchedule");
		
		sql.append(SQL_GET_ALL);
		
		if (StringUtils.hasText(kpiSchedule.getYearName())) {
			sql.append(" AND year_name LIKE ? ");
			params.add("%" + kpiSchedule.getYearName() + "%");
		}
		int rows_totalItem = jdbcTemplate.queryForObject(MySqlUtil.genTotalCountSql(sql.toString()), params.toArray(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);
		
		logger.info(" sql:"+sql);
		
		List<KpiSchedule> returnList = this.jdbcTemplate.query(MySqlUtil.genPagingSql(sql.toString(), pagingBean), kpiScheduleMapper, params.toArray());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	private static final String SQL_CREATE =
		" INSERT INTO kpi_schedule ( " +
		"   kpi_schedule_id, " +
		"   year_name, " +
		"   first_schedule_teacher_upload_start_date, " +
		"   first_schedule_teacher_upload_end_date, " +
		"   first_schedule_teacher_evaluate_start_date, " +
		"   first_schedule_teacher_evaluate_end_date, " +
		"   second_schedule_teacher_upload_start_date, " +
		"   second_schedule_teacher_upload_end_date, " +
		"   second_schedule_teacher_evaluate_start_date, " +
		"   second_schedule_teacher_evaluate_end_date, " +
		"   first_schedule_staff_upload_start_date, " +
		"   first_schedule_staff_upload_end_date, " +
		"   first_schedule_staff_evaluate_start_date, " +
		"   first_schedule_staff_evaluate_end_date, " +
		"   status, " +
		"   created_by, " +
		"   created_date, " +
		"   updated_by, " +
		"   updated_date " +
		" ) " +
		" VALUES ( " +
		"   NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
		"   'A', ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP " +
		" ) ";
	
	@Override
	public Long create(final KpiSchedule domain) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, domain.getYearName());
				ps.setDate(2, new Date(domain.getTeacherUploadStartDate1().getTime()));
				ps.setDate(3, new Date(domain.getTeacherUploadEndDate1().getTime()));
				ps.setDate(4, new Date(domain.getTeacherEvaluateStartDate1().getTime()));
				ps.setDate(5, new Date(domain.getTeacherEvaluateEndDate1().getTime()));
				ps.setDate(6, new Date(domain.getTeacherUploadStartDate2().getTime()));
				ps.setDate(7, new Date(domain.getTeacherUploadEndDate2().getTime()));
				ps.setDate(8, new Date(domain.getTeacherEvaluateStartDate2().getTime()));
				ps.setDate(9, new Date(domain.getTeacherEvaluateEndDate2().getTime()));
				ps.setDate(10, new Date(domain.getStaffUploadStartDate().getTime()));
				ps.setDate(11, new Date(domain.getStaffUploadEndDate().getTime()));
				ps.setDate(12, new Date(domain.getStaffEvaluateStartDate().getTime()));
				ps.setDate(13, new Date(domain.getStaffEvaluateEndDate().getTime()));
				ps.setString(14, domain.getCreateBy());
				ps.setString(15, domain.getCreateBy());
				return ps;
			}
		}, keyHolder);
		Long returnid = keyHolder.getKey().longValue();
		
	   return returnid;
	}
	
	
	private static final String SQL_UPDATE =
		" UPDATE kpi_schedule SET " +
		"   first_schedule_teacher_upload_start_date = ?, " +
		"   first_schedule_teacher_upload_end_date = ?, " +
		"   first_schedule_teacher_evaluate_start_date = ?, " +
		"   first_schedule_teacher_evaluate_end_date = ?, " +
		"   second_schedule_teacher_upload_start_date = ?, " +
		"   second_schedule_teacher_upload_end_date = ?, " +
		"   second_schedule_teacher_evaluate_start_date = ?, " +
		"   second_schedule_teacher_evaluate_end_date = ?, " +
		"   first_schedule_staff_upload_start_date = ?, " +
		"   first_schedule_staff_upload_end_date = ?, " +
		"   first_schedule_staff_evaluate_start_date = ?, " +
		"   first_schedule_staff_evaluate_end_date = ?, " +
		"   updated_by = ?, " +
		"   updated_date = CURRENT_TIMESTAMP " +
		" where year_name = ? ";
	
	@Override
	public void update(KpiSchedule domain) {
		this.jdbcTemplate.update(SQL_UPDATE, new Object[] {
			domain.getTeacherUploadStartDate1(),
			domain.getTeacherUploadEndDate1(),
			domain.getTeacherEvaluateStartDate1(),
			domain.getTeacherEvaluateEndDate1(),
			domain.getTeacherUploadStartDate2(),
			domain.getTeacherUploadEndDate2(),
			domain.getTeacherEvaluateStartDate2(),
			domain.getTeacherEvaluateEndDate2(),
			domain.getStaffUploadStartDate(),
			domain.getStaffUploadEndDate(),
			domain.getStaffEvaluateStartDate(),
			domain.getStaffEvaluateEndDate(),
			domain.getUpdateBy(),
			domain.getYearName()
		});
	}
	
	
	@Override
	public KpiSchedule getById(Long kpiScheduleId) {
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_ALL);
		sql.append("  AND kpi_schedule_id = ? ");
		
		KpiSchedule kpiSchedule = this.jdbcTemplate.queryForObject(sql.toString(), kpiScheduleMapper, new Object[] {
			kpiScheduleId
		});
		
		if (logger.isDebugEnabled()) {
			logger.info("kpiSchedule: " + kpiSchedule.toString());
		}
		
		return kpiSchedule;
	}
	
	
	private static final String SQL_DELETE =
		" UPDATE kpi_schedule SET " +
		"   status = 'I', " +
		"   updated_by = ?, " +
		"   updated_date = CURRENT_TIMESTAMP " +
		" where kpi_schedule_id = ? ";
	
	@Override
	public void deleteById(Long kpiScheduleId, String username) {
		this.jdbcTemplate.update(SQL_DELETE, new Object[] {
			username,
			kpiScheduleId
		});
	}
	
	private static final String SQL_IS_EXIST =
		" SELECT COUNT(1) AS TOTAL " +
		" FROM kpi_schedule " +
		" WHERE status = 'A' " +
		"   AND year_name = ?";
	
	@Override
	public boolean isExist(String yearName) {
		boolean returnValue = false;
		try{
			Long found = this.jdbcTemplate.queryForObject(SQL_IS_EXIST, new Object[] {yearName},Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	@Override
	public KpiSchedule getByYearName(String yearName) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_ALL);
		sql.append("  AND year_name = ? ");
		logger.info(" yearName:"+yearName);
		logger.info(" sql:"+sql.toString());
		KpiSchedule kpiSchedule = this.jdbcTemplate.queryForObject(sql.toString(), kpiScheduleMapper, new Object[] {
			yearName
		});
		
		if (logger.isDebugEnabled()) {
			logger.info("kpiSchedule: " + kpiSchedule.toString());
		}
		
		return kpiSchedule;
	}
	
	
	
	
	private static final KpiScheduleMapper kpiScheduleMapper = new KpiScheduleMapper();
	private static class KpiScheduleMapper implements RowMapper<KpiSchedule> {
		@Override
		public KpiSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
			KpiSchedule domain = new KpiSchedule();
			domain.setKpiScheduleId(rs.getLong("kpi_schedule_id"));
			domain.setYearName(rs.getString("year_name"));
			domain.setTeacherUploadStartDate1(rs.getDate("first_schedule_teacher_upload_start_date"));
			domain.setTeacherUploadEndDate1(rs.getDate("first_schedule_teacher_upload_end_date"));
			domain.setTeacherEvaluateStartDate1(rs.getDate("first_schedule_teacher_evaluate_start_date"));
			domain.setTeacherEvaluateEndDate1(rs.getDate("first_schedule_teacher_evaluate_end_date"));
			domain.setTeacherUploadStartDate2(rs.getDate("second_schedule_teacher_upload_start_date"));
			domain.setTeacherUploadEndDate2(rs.getDate("second_schedule_teacher_upload_end_date"));
			domain.setTeacherEvaluateStartDate2(rs.getDate("second_schedule_teacher_evaluate_start_date"));
			domain.setTeacherEvaluateEndDate2(rs.getDate("second_schedule_teacher_evaluate_end_date"));
			domain.setStaffUploadStartDate(rs.getDate("first_schedule_staff_upload_start_date"));
			domain.setStaffUploadEndDate(rs.getDate("first_schedule_staff_upload_end_date"));
			domain.setStaffEvaluateStartDate(rs.getDate("first_schedule_staff_evaluate_start_date"));
			domain.setStaffEvaluateEndDate(rs.getDate("first_schedule_staff_evaluate_end_date"));
			return domain;
		}
	}

}
