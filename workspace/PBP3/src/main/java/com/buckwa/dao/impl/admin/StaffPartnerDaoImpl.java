package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.admin.StaffPartnerDao;
import com.buckwa.domain.admin.StaffRatio;
import com.buckwa.domain.admin.SubjectOfStaff;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;
import com.buckwa.util.BeanUtils;

@Repository("staffPartnerDao")
public class StaffPartnerDaoImpl implements StaffPartnerDao{

	private static Logger logger = LoggerFactory.getLogger(StaffPartnerDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String getallSQL = "SELECT "+
			"t.subject_id ,t.lect_or_prac as subjectName,st.semester term, st.* "+
			"FROM time_table t  "+
			"LEFT JOIN section st "+
			"ON t.time_table_cd = st.time_table_id "+
			"WHERE st.staff_flag='Y' ";
	
	@Override
	public List<SubjectOfStaff> getAllList(Section section) {
		String limit = " limit 0,50 ";
		String sql = getallSQL;
//				AND st.academic_year='2556' -- year ปีการศักษา
//				AND st.semester='1' -- เทอม
//				AND t.subject_id='05300123' -- วิชา
		if(BeanUtils.isNotEmpty(section.getSubjectId())){
			sql +=" AND t.subject_id='" + section.getSubjectId() +"' ";
		}
		if(BeanUtils.isNotEmpty(section.getAcademicYear())){
			sql += " AND st.academic_year='"+section.getAcademicYear() +"' ";
		}
		if(BeanUtils.isNotEmpty(section.getSemester())){
			sql += " AND st.semester="+section.getSemester();
		}
		
		logger.info("SQL : " + sql + limit);
		
		List<SubjectOfStaff> rs = jdbcTemplate.query(sql,new Object[]{}, new SubjectByStaff());
	 
		return rs;
	}
	
	private static String findSectionById = "select "+
			"t.subject_id,s.semester term ,t.lect_or_prac as subjectName,s.*  "+
			"from section s  "+
			"LEFT JOIN time_table t "+
			"ON s.time_table_id=t.time_table_cd "+
			"WHERE  s.section_id=?";
	
	public SubjectOfStaff findSectionById( String sectionId ){
		SubjectOfStaff subjectOfStaff = null;
		
		subjectOfStaff = jdbcTemplate.queryForObject(findSectionById,new Object[]{sectionId}, new SubjectByStaff());
		
		return subjectOfStaff;
	}
	
	public class SubjectByStaff implements RowMapper<SubjectOfStaff>{

		@Override
		public SubjectOfStaff mapRow(ResultSet r, int arg1)throws SQLException {
			SubjectOfStaff sf = new SubjectOfStaff();
			sf.setSubjectId( r.getString("subject_id"));
			sf.setSubjectName( r.getString("subjectName"));
			sf.setYear(r.getString("academic_year"));
			sf.setTerm( r.getString("term"));
			sf.setSectionId(r.getString("section_id"));
			sf.setTimeTableId( r.getString("time_table_id"));
			sf.setSectionCd( r.getString("section_cd"));
			sf.setStaffFlage( r.getString("staff_flag"));
			return sf;
		}
		
	}

	private static String getStaffRatioById = "SELECT "+
			"* "+
			"FROM staff_ratio sr "+
			"WHERE sr.section_id=?";
	@Override
	public List<StaffRatio> getStaffRatioById(String sectionId) {
		
		List<StaffRatio> res = jdbcTemplate.query(getStaffRatioById, new Object[]{sectionId} , new StaffRatioMapping());
		return res;
	}
	
	public class StaffRatioMapping implements RowMapper<StaffRatio>{

		@Override
		public StaffRatio mapRow(ResultSet r, int arg1) throws SQLException {
			StaffRatio staffRatio = new StaffRatio();
				staffRatio.setSection_id(r.getString("staff_ratio"));
				staffRatio.setStaff_code(r.getString("staff_code"));
				staffRatio.setStaff_name(r.getString("staff_name"));
				staffRatio.setStaff_ratio(r.getInt("staff_ratio"));
			return staffRatio;
		}
		
	}

	private static String findStaff = "SELECT * "+
			"FROM staff  s";
	@Override
	public Staff findStaff(String firstname, String lastname) {
		logger.info("firstname " + firstname);
		logger.info("lastname " + lastname);
		String sql =findStaff + " WHERE s.full_name like '" +firstname+"%"+lastname+"'";
		logger.info("sql : " + sql);
		Staff rs = null;
		try{
			rs = jdbcTemplate.queryForObject(sql, new StaffMapping());	
		}catch(Exception e){}
		
		return rs;
	}

	private static String addStaffRatio = "insert into `staff_ratio`(`section_id`,`staff_code`,`staff_name`,`staff_ratio`)values(?,?,?,?)";
	@Override
	public Staff addStaffRatio(Staff staff, Integer ratio) {
		jdbcTemplate.update(addStaffRatio, staff.getSectionId(),staff.getStaffCode(),staff.getFullName(),ratio);
		return staff;
	}
	
	public class StaffMapping implements RowMapper<Staff>{

		@Override
		public Staff mapRow(ResultSet r, int arg1) throws SQLException {
			Staff staff = new Staff();
			staff.setStaffId(r.getLong("staff_id"));
			staff.setStaffCode(r.getString("code"));
			staff.setFullName(r.getString("full_name"));
			return staff;
		}
		
	}

	@Override
	public Integer maxRatio(String section_id) {
		String sql = "SELECT SUM(sr.staff_ratio) FROM staff_ratio sr WHERE sr.section_id="+section_id;
		int r = jdbcTemplate.queryForObject(sql,Integer.class);
		return r;
	}

	private static String deleteStaff = "delete from `staff_ratio` where `section_id`=? and `staff_code`=?";
	@Override
	public void deleteStaff(String sectionId, String staff_code) {
		jdbcTemplate.update(deleteStaff,sectionId,staff_code);
	}
	
	private static String getStaffByName = "select * from staff s where 1=1";
	@Override
	public List<Staff> getStaffByName(String firstname, String lastName) {
		String sql = getStaffByName;
		
		if(BeanUtils.isNotEmpty(firstname)){
			sql += " AND s.full_name like '%" + firstname  + "%'";
		}
		if(BeanUtils.isNotEmpty(lastName)){
			sql += " AND s.full_name like '%" + lastName  + "%'";
		}
		
		sql += " limit 0,5 ";
		
		logger.info( "SQL : " + sql);
		
		List<Staff> res = jdbcTemplate.query(sql, new StaffMapping());
		return res;
	}

}
