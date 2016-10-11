package com.buckwa.dao.impl.pam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.TeacherTeachDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Leave;
import com.buckwa.domain.pam.Subject;
import com.buckwa.domain.pam.TeachTable;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("teacherTeachDao")
public class TeacherTeachDaoImpl implements TeacherTeachDao {
	
	private static Logger logger = LoggerFactory.getLogger(TeacherTeachDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<TeachTable> getTeachTableList(Long userId,Long yearId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append("SELECT tt.teach_table_id,s.name_th as subjectName,s.name_en as subjectNameEn,tt.teach_day,tt.teach_time,tt.teach_time2,sm.start_date,sm.end_date,0 as degree,sm.semester_id,p.year_id \n");
		sql.append("FROM pam_teacher_teach p \n");
		sql.append("inner join pam_teach_table tt on tt.subject_code = p.subject_code and tt.faculty_id = p.faculty_id and p.dept_id=tt.dept_id and p.curr_id=tt.curr_id and p.curr2_id = tt.curr2_id and p.class = tt.class and p.section = tt.section and p.lect_or_prac=tt.lect_or_prac and p.program=tt.program \n");
		sql.append("inner join year y on y.year_id = tt.year_id \n");
		sql.append("inner join semester sm on sm.year_id=y.year_id and tt.semester_id=sm.semester_id \n");
		sql.append("inner join pam_teacher te on te.code = p.teacher_id and te.year_id="+yearId+" and te.semester_id = sm.semester_id \n");
		sql.append("inner join pam_subject s on s.code = tt.subject_code and s.year_id="+yearId+" and s.semester_id = sm.semester_id \n");
		sql.append("where p.year_id="+yearId+" and te.user_id="+userId+" and (sm.name=1 or sm.name=2) \n");
		sql.append(" union all \n");
		sql.append("SELECT tt.teach_table_id,s.name_th as subjectName,s.name_en as subjectNameEn,tt.teach_day,tt.teach_time,tt.teach_time2,sm.start_date,sm.end_date,1 as degree,sm.semester_id,p.year_id \n");
		sql.append("FROM pam_master_teacher_teach p \n");
		sql.append("inner join pam_master_teach_table tt on tt.subject_code = p.subject_code and tt.faculty_id = p.faculty_id and p.dept_id=tt.dept_id and p.curr_id=tt.curr_id and p.branch_id = tt.branch_id and p.class = tt.class and p.section = tt.section and p.lect_or_prac=tt.lect_or_prac and p.admis_type=tt.admis_type and p.term_admis=tt.term_admis  \n");
		sql.append("inner join year y on y.year_id = tt.year_id \n");
		sql.append("inner join semester sm on sm.year_id=y.year_id and tt.semester_id=sm.semester_id \n");
		sql.append("inner join pam_teacher te on te.code = p.teacher_id and te.year_id="+yearId+" and te.semester_id = sm.semester_id \n");
		sql.append("inner join pam_master_subject s on s.code = tt.subject_code and s.year_id="+yearId+" and s.semester_id = sm.semester_id \n");
		sql.append("where p.year_id="+yearId+" and te.user_id="+userId+" and (sm.name=1 or sm.name=2) \n");	
		sql.append(") A \n");
		List<TeachTable> objList = null;
		try {
			objList = this.jdbcTemplate.query(sql.toString(),new RowMapper<TeachTable>() {
					public TeachTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						TeachTable domain = new TeachTable();
						domain.setTeachTableId(rs.getLong("teach_table_id"));
						if(org.apache.commons.lang.StringUtils.isNotEmpty(rs.getString("subjectName")))
							domain.setSubjectName(rs.getString("subjectName"));
						else
							domain.setSubjectName(rs.getString("subjectNameEn"));
						domain.setTeachDay(rs.getInt("teach_day"));
						domain.setTeachTime(rs.getTime("teach_time"));
						domain.setTeachTime2(rs.getTime("teach_time2"));
						domain.setStartDate(rs.getDate("start_date"));
						domain.setEndDate(rs.getDate("end_date"));
						domain.setDegree(rs.getInt("degree"));
						domain.setSemesterId(rs.getLong("semester_id"));
						domain.setYearId(rs.getLong("year_id"));
						return domain;
					}
				} );			
			return objList;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public TeachTable getTeachTable(Long teachTableId){
		StringBuffer sb = new StringBuffer();
		sb.append("select t.*,s.code,s.name_th as subjectName FROM pam_teach_table t inner join pam_subject s on s.code = t.subject_code where t.teach_table_id="+teachTableId);
		TeachTable obj = this.jdbcTemplate.queryForObject(sb.toString(),new TeachTableMapper() );				
		return obj;
		
	
	}
	
	@Override
	public TeachTable getTeachTable(Long teachTableId,String degree,Long yearId,Long semesterId){
		StringBuffer sb = new StringBuffer();
		if("0".equals(degree)){
			sb.append("select t.*,s.code,s.name_th as subjectName,s.name_en as subjectNameEn FROM pam_teach_table t " +
					"inner join pam_subject s on s.code = t.subject_code where t.teach_table_id="+teachTableId+" and s.year_id="+yearId+" and s.semester_id="+semesterId+"");
			TeachTable obj = this.jdbcTemplate.queryForObject(sb.toString(),new TeachTableMapper() );			
			return obj;
		}else if("1".equals(degree)){
			sb.append("select t.*,s.code,s.name_th as subjectName,s.name_en as subjectNameEn FROM pam_master_teach_table t " +
					"inner join pam_master_subject s on s.code = t.subject_code where t.teach_table_id="+teachTableId+" and s.year_id="+yearId+" and s.semester_id="+semesterId+"");
			TeachTable obj = this.jdbcTemplate.queryForObject(sb.toString(),new TeachTableMasterMapper() );			
			return obj;
		}
			
		
		return null;
	
	}
	
	private class TeachTableMapper implements RowMapper<TeachTable> {
		@Override
		public TeachTable mapRow(ResultSet rs, int rowNum) throws SQLException {
			TeachTable domain = new TeachTable();
			domain.setTeachTableId(rs.getLong("teach_table_id"));
			if(org.apache.commons.lang.StringUtils.isNotEmpty(rs.getString("subjectName")))
				domain.setSubjectName(rs.getString("subjectName"));
			else
				domain.setSubjectName(rs.getString("subjectNameEn"));
			domain.setTeachDay(rs.getInt("teach_day"));
			domain.setTeachTime(rs.getTime("teach_time"));
			domain.setTeachTime2(rs.getTime("teach_time2"));
			domain.setSubjectCode(rs.getString("code"));
			domain.setTimeTeach(BuckWaUtils.getTimeToEnd(domain.getTeachTime(),domain.getTeachTime2()));
			domain.setRoom(rs.getString("room"));
			domain.setBuilding(rs.getString("building"));
			domain.setRoomNo(rs.getString("room_no"));
			domain.setBuildingNo(rs.getString("building_no"));
			return domain;
		}
	}

	private class TeachTableMasterMapper implements RowMapper<TeachTable> {
		@Override
		public TeachTable mapRow(ResultSet rs, int rowNum) throws SQLException {
			TeachTable domain = new TeachTable();
			domain.setTeachTableId(rs.getLong("teach_table_id"));
			if(org.apache.commons.lang.StringUtils.isNotEmpty(rs.getString("subjectName")))
				domain.setSubjectName(rs.getString("subjectName"));
			else
				domain.setSubjectName(rs.getString("subjectNameEn"));
			domain.setTeachDay(rs.getInt("teach_day"));
			domain.setTeachTime(rs.getTime("teach_time"));
			domain.setTeachTime2(rs.getTime("teach_time2"));
			domain.setSubjectCode(rs.getString("code"));
			domain.setTimeTeach(BuckWaUtils.getTimeToEnd(domain.getTeachTime(),domain.getTeachTime2()));
			domain.setRoom(rs.getString("room"));
			domain.setBuilding(rs.getString("building"));
			//domain.setRoomNo(rs.getString("room_no"));
			//domain.setBuildingNo(rs.getString("building_no"));
			return domain;
		}
	}
}

