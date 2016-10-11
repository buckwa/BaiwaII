package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import com.buckwa.dao.intf.pam.ImportTimeTableDao;
import com.buckwa.domain.pam.TimeTable;

/**
 *
@Author : Kroekpong Sakulchai
@Create : Aug 10, 2012 2:10:21 AM
 *
 **/

@Repository("importTimeTableDao")

public class ImportTimeTableDaoImpl implements ImportTimeTableDao{

	private static Logger logger = LoggerFactory.getLogger(ImportTimeTableDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long create(final TimeTable timetable) {
		
		final StringBuilder sb = new StringBuilder();
		sb.append("	INSERT INTO pbp.time_table ");
		sb.append("		( faculty_id, dept_id, curr_id, curr2_id, ");
		sb.append("			subject_id, semester, YEAR, class, program, ");
		sb.append("			lect_or_prac,priority, time_stamp)");
		sb.append("		VALUES( ?, ?, ?, ?, ");
		sb.append("			?, ?, ?, ?, ?, ");
		sb.append("			?, ?, ?");
		sb.append("			)");

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
				ps.setString( 1 , timetable.getFacultyId() );
				ps.setString( 2 , timetable.getDeptId());
				ps.setInt( 3 , timetable.getCurrId());
				ps.setString( 4 , timetable.getCurrId2());
				ps.setString( 5 , timetable.getSubjectId());
				logger.info(" ## Semester Insert:"+timetable.getSemester());
				ps.setInt( 6 , 1);
				ps.setString( 7 , timetable.getYear());
				ps.setString( 8 , timetable.getLevel());
				ps.setString( 9 , timetable.getProgram());
				ps.setString( 10 , timetable.getLectOrPrac());
				ps.setInt( 11 , timetable.getPriority());
				ps.setString( 12 , timetable.getTimeStamp());
				
				return ps;  
			}
			
		};
//		
		logger.info("TIME 2 >"+timetable);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
//		Long key = keyHolder.getKey().longValue();
		return (long) 1;
	
	}
	
	@Override
	public void updateDesc(TimeTable timetable) {
		String sql =" UPDATE time_table set eng_name='"+timetable.getEngName()+"' , thai_name='"+timetable.getThaiName()+"' where subject_id="+timetable.getSubjectId();
		this.jdbcTemplate.update(sql);
	}
	
	
	@Override
	public boolean executeSqlScript(List<String> fileList){ 
		logger.info(" #ImportTimeTableDaoImpl.executeSqlScript # ");
		try{
//			SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(this.jdbcTemplate.getDataSource());
//			if(fileList!=null && fileList.size()>0){
//				Resource resourceDrop = new ClassPathResource("drop_table.xls");
//				EncodedResource encodeResource = new EncodedResource(resourceDrop,"utf-8");
//				encodeResource.getEncoding();
//				resourceDrop.getInputStream();
//				SimpleJdbcTestUtils.executeSqlScript(simpleJdbcTemplate, resourceDrop, false);
//				for(String file : fileList){
//					Resource resource = new FileSystemResource(file);
//					EncodedResource encodeResourceFile = new EncodedResource(resource,"utf-8");
//					encodeResourceFile.getEncoding();
//					resource.getInputStream();
//					SimpleJdbcTestUtils.executeSqlScript(simpleJdbcTemplate, encodeResourceFile, false);
//				}
//				return true;
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Long getCount(TimeTable timetable) {	 
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   SELECT COUNT(1) as countSubjectId FROM time_table WHERE subject_id = "+ timetable.getSubjectId()+" and semester="+timetable.getSemester()+ " and year="+timetable.getYear()); 
		logger.info(" SQL Count :"+sqltotalsb);
		List<Long> count = this.jdbcTemplate.query(
				sqltotalsb.toString(),
				new RowMapper<Long>() {
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return Long.parseLong(rs.getString("countSubjectId"));
				}
				});
		logger.info(count +": count");
		return count.get(0);
	}

	
	@Override
	public Long addSection(final TimeTable timetable) {
		
		final StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO pbp.section( time_table_id, section_cd,semester, academic_year,staff_flag)VALUES(?,?,?,?,'N')");

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
				ps.setInt( 1 , timetable.getTimeTableCd() );
				ps.setString( 2 , timetable.getSectionCd());
				ps.setInt( 3 , timetable.getSemester());
				ps.setString( 4 , timetable.getYear());
				return ps;  
			}
			
		};
//		
		logger.info("TIME 2 >"+timetable);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
//		Long key = keyHolder.getKey().longValue();
		return (long) 1;
	
	}

	@Override
	public Long addstaff(final TimeTable timetable) {
		
		final StringBuilder sb = new StringBuilder();
		sb.append("	INSERT INTO pbp.staff_mapping (section_id, teacher_id)VALUES(?, ?) ");

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
				ps.setInt( 1 , timetable.getSectionId());
				ps.setString( 2 , timetable.getTeacherId());
				return ps;  
			}
			
		};
//		
		logger.info("TIME 2 >"+timetable);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
//		Long key = keyHolder.getKey().longValue();
		return (long) 1;
	
	}

	@Override
	public Integer getTimeTableId(TimeTable timetable) {	 
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("	SELECT time_table_cd ");
		sqltotalsb.append("	FROM time_table ");
		sqltotalsb.append("	WHERE subject_id = '"+ timetable.getSubjectId()+"'");
		
		List<Integer> timeTable = this.jdbcTemplate.query(
				sqltotalsb.toString(),
				new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("time_table_cd");
				}
				});
		logger.info(timeTable +": timeTable");
		return timeTable.get(0);
	}

	@Override
	public Integer getSectionId(TimeTable timetable) {	 
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("	SELECT section_id FROM section ");
		sqltotalsb.append("	WHERE time_table_id = "+timetable.getTimeTableCd());
		sqltotalsb.append("	AND section_cd ="+ timetable.getSectionCd());
		
		List<Integer> timeTable = this.jdbcTemplate.query(
				sqltotalsb.toString(),
				new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("section_id");
				}
				});
		logger.info(timeTable +": timeTable");
		return timeTable.get(0);
	}
	
	@Override
	public Integer checkStaff(TimeTable timetable) {	 
		StringBuffer sqltotalsb = new StringBuffer();
		
		sqltotalsb.append("	SELECT COUNT(1) as checkStaff FROM staff_mapping");
		sqltotalsb.append("	WHERE section_id = '"+timetable.getSectionId()+"'");
		sqltotalsb.append("	AND teacher_id = '"+timetable.getTeacherId()+"'");
		
		
		List<Integer> timeTable = this.jdbcTemplate.query(
				sqltotalsb.toString(),
				new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("checkStaff");
					}
				});
		logger.info(timeTable +": timeTable");
		return timeTable.get(0);
	}

	@Override
	public Long checkSection(TimeTable timetable) {	 
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append(" SELECT COUNT(1) AS checkSection ");
		sqltotalsb.append(" FROM section ");
		sqltotalsb.append(" WHERE time_table_id = (");
		sqltotalsb.append("			SELECT time_table_cd ");
		sqltotalsb.append("			FROM time_table ");
		sqltotalsb.append("			WHERE subject_id = '"+ timetable.getSubjectId()+"' and year="+timetable.getYear()+" and semester ="+timetable.getSectionCd());
		sqltotalsb.append("		)");
		sqltotalsb.append(" AND section_cd = "+timetable.getSectionCd());
		
		logger.info(" SQL Select Count:"+sqltotalsb.toString());
		List<Long> count = this.jdbcTemplate.query(
				sqltotalsb.toString(),
				new RowMapper<Long>() {
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return Long.parseLong(rs.getString("checkSection"));
				}
				});
		logger.info(count +": count");
		return count.get(0);
	}
	
	
	
	@Override
	public void updateStaff() {
		String sql =" UPDATE section a INNER JOIN  staff_mapping b ON a.section_id = b.section_id SET a.staff_flag = 'Y'  WHERE a.section_id IN ( SELECT section_id  FROM staff_mapping  GROUP BY section_id  HAVING COUNT(1) > 1 ) OR (b.teacher_id IN (SELECT CODE FROM staff WHERE UPPER(full_name) LIKE '%STAFF%')) ";
		this.jdbcTemplate.update(sql);
	}
}
