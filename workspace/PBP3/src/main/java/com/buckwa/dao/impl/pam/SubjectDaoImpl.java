package com.buckwa.dao.impl.pam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.SubjectDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Subject;

@Repository("subjectDao")
public class SubjectDaoImpl implements SubjectDao {
	private static Logger logger = LoggerFactory.getLogger(SubjectDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Subject> getAll(Long yearId,Long semesterId) {		 		
		StringBuffer sb = new StringBuffer();
		List<Subject> returnList = new ArrayList<Subject>();
		
		sb.append("select * from ( \n");
		sb.append("select DISTINCT C.teacher_id,C.name as teacher_tname,A.name_th as subject_name_th, \n");
		sb.append("A.name_en as subject_name_en,A.credit,A.lect_hr,A.prac_hr,A.self_hr,A.detail,A.code as subject_code \n");
		sb.append("from pam_teacher_teach B inner join \n");
		sb.append("( \n");
		sb.append("select DISTINCT ptt.subject_code,ptt.teacher_id,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,s.code from pam_subject s \n");
		sb.append("inner join pam_teacher_teach ptt on ptt.subject_code = s.code \n");
		sb.append("where ptt.year_id="+yearId+" and ptt.semester_id="+semesterId+" \n");
		sb.append(") A on A.subject_code = B.subject_code \n");
		sb.append("inner join pam_teacher C on C.code = A.teacher_id  ");
		sb.append("where B.year_id="+yearId+" and b.semester_id="+semesterId);
		
		sb.append(" union all ");
		
		sb.append("select DISTINCT C.teacher_id,C.name as teacher_tname,A.name_th as subject_name_th, ");
		sb.append("A.name_en as subject_name_en,A.credit,A.lect_hr,A.prac_hr,A.self_hr,A.detail,A.code as subject_code ");
		sb.append("from pam_master_teacher_teach B inner join ");
		sb.append("( ");
		sb.append("select DISTINCT ptt.subject_code,ptt.teacher_id,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,s.code from pam_master_subject s ");
		sb.append("inner join pam_master_teacher_teach ptt on ptt.subject_code = s.code ");
		sb.append("where ptt.year_id="+yearId+" and ptt.semester_id="+semesterId+" ");
		sb.append(") A on A.subject_code = B.subject_code ");
		sb.append("inner join pam_teacher C on C.code = A.teacher_id  ");
		sb.append("where B.year_id="+yearId+" and b.semester_id="+semesterId);
		
		sb.append(") AA "); 
		

		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new SubjectMapper() );	
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return returnList;
	}	
	
	@Override
	public List<Subject> getAll(Long yearId,Long semesterId,String teacherList) {		 		
		StringBuffer sb = new StringBuffer();
		List<Subject> returnList = new ArrayList<Subject>();
		
		if(StringUtils.isNotBlank(teacherList)){
			
			sb.append("select * from ( \n");
			sb.append("select DISTINCT C.teacher_id,C.name as teacher_tname,A.name_th as subject_name_th,  \n");
			sb.append("A.name_en as subject_name_en,A.credit,A.lect_hr,A.prac_hr,A.self_hr,A.detail,A.code as subject_code  \n");
			sb.append("from pam_teacher_teach B inner join  \n");
			sb.append("(  \n");
			sb.append("select DISTINCT ptt.subject_code,ptt.teacher_id,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,s.code from pam_subject s  \n");
			sb.append("inner join pam_teacher_teach ptt on ptt.subject_code = s.code  \n");
			sb.append("where ptt.year_id="+yearId+" and ptt.semester_id="+semesterId);
			sb.append(") A on A.subject_code = B.subject_code  \n");
			sb.append("inner join pam_teacher C on C.code = A.teacher_id  \n");
			sb.append("where B.year_id="+yearId+" and B.semester_id="+semesterId+" and C.teacher_id in ("+teacherList+") \n");
			
			sb.append(" union all ");
			
			sb.append("select DISTINCT C.teacher_id,C.name as teacher_tname,A.name_th as subject_name_th,  \n");
			sb.append("A.name_en as subject_name_en,A.credit,A.lect_hr,A.prac_hr,A.self_hr,A.detail,A.code as subject_code  \n");
			sb.append("from pam_master_teacher_teach B inner join  \n");
			sb.append("(  \n");
			sb.append("select DISTINCT ptt.subject_code,ptt.teacher_id,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,s.code from pam_master_subject s  \n");
			sb.append("inner join pam_master_teacher_teach ptt on ptt.subject_code = s.code  \n");
			sb.append("where ptt.year_id="+yearId+" and ptt.semester_id="+semesterId);
			sb.append(") A on A.subject_code = B.subject_code \n ");
			sb.append("inner join pam_teacher C on C.code = A.teacher_id  \n");
			sb.append("where B.year_id="+yearId+" and B.semester_id="+semesterId+" and C.teacher_id in ("+teacherList+") \n");
			
			sb.append(") AA");
			
			
			try {
				returnList = this.jdbcTemplate.query(sb.toString(),new SubjectMapper() );	
			} catch (EmptyResultDataAccessException e) {
				e.printStackTrace();
			}
		}
		
		
		return returnList;
	}	
	
	@Override
	public PagingBean getAllSubjectByOffset(PagingBean pagingBean){
		logger.info("getAllSubjectByOffset");
		Subject subject = (Subject) pagingBean.get("subject");
		List<Subject> returnList = new ArrayList<Subject>();
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("select * from ( ");
		sqltotalsb.append("SELECT distinct s.subject_id,s.year_id,s.semester_id,s.code,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,0 as degree ");
		sqltotalsb.append("FROM pam_teacher_teach p inner join pam_subject s on s.code=p.subject_code ");
		sqltotalsb.append("inner join pam_teacher t on t.code=p.teacher_id ");
		sqltotalsb.append(" where 1=1 and s.status='A' and t.user_id = "+subject.getUserId());
		if (subject.getYearId()!=null&&subject.getYearId().intValue()!=0) {
			sqltotalsb.append(" and s.year_id ="+subject.getYearId());
		}
		
		if (subject.getSemesterId()!=null&&subject.getSemesterId().intValue()!=0) {
			sqltotalsb.append(" and s.semester_id ="+subject.getSemesterId());
		}
		sqltotalsb.append(" union all ");
		sqltotalsb.append("SELECT distinct s.subject_id,s.year_id,s.semester_id,s.code,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,1 as degree ");
		sqltotalsb.append("FROM pam_master_teacher_teach p inner join pam_master_subject s on s.code=p.subject_code ");
		sqltotalsb.append("inner join pam_teacher t on t.code=p.teacher_id ");
		sqltotalsb.append(" where 1=1 and s.status='A' and t.user_id = "+subject.getUserId());
		if (subject.getYearId()!=null&&subject.getYearId().intValue()!=0) {
			sqltotalsb.append(" and s.year_id ="+subject.getYearId());
		}
		if (subject.getSemesterId()!=null&&subject.getSemesterId().intValue()!=0) {
			sqltotalsb.append(" and s.semester_id ="+subject.getSemesterId());
		}
		sqltotalsb.append(") Data ");
		
		
		int rows_totalItem = jdbcTemplate.queryForObject("select count(*) as total_item from ("+sqltotalsb.toString()+") A ",Integer.class);
		pagingBean.setTotalItems(rows_totalItem);
		StringBuffer sb = new StringBuffer();
		sb.append("select * from( ");
		sb.append("SELECT distinct s.subject_id,s.year_id,s.semester_id,s.code,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,y.name as yearName,sm.name as  semesterName,0 as degree ");
		sb.append("FROM pam_teacher_teach p inner join pam_subject s on s.code=p.subject_code ");
		sb.append("inner join pam_teacher t on t.code=p.teacher_id ");
		sb.append("inner join year y on y.year_id = s.year_id inner join semester sm on sm.semester_id = s.semester_id");
		sb.append(" where 1=1 and s.status='A' and t.user_id = "+subject.getUserId()) ;
		if (subject.getYearId()!=null&&subject.getYearId().intValue()!=0) {
			sb.append(" and s.year_id ="+subject.getYearId());
		}
		
		if (subject.getSemesterId()!=null&&subject.getSemesterId().intValue()!=0) {
			sb.append(" and s.semester_id ="+subject.getSemesterId());
		}
		sb.append(" union all ");
		sb.append("SELECT distinct s.subject_id,s.year_id,s.semester_id,s.code,s.name_th,s.name_en,s.credit,s.lect_hr,s.prac_hr,s.self_hr,s.detail,y.name as yearName,sm.name as  semesterName,1 as degree ");
		sb.append("FROM pam_master_teacher_teach p inner join pam_master_subject s on s.code=p.subject_code ");
		sb.append("inner join pam_teacher t on t.code=p.teacher_id ");
		sb.append("inner join year y on y.year_id = s.year_id inner join semester sm on sm.semester_id = s.semester_id");
		sb.append(" where 1=1 and s.status='A' and t.user_id = "+subject.getUserId());
		if (subject.getYearId()!=null&&subject.getYearId().intValue()!=0) {
			sb.append(" and s.year_id ="+subject.getYearId());
		}
		
		if (subject.getSemesterId()!=null&&subject.getSemesterId().intValue()!=0) {
			sb.append(" and s.semester_id ="+subject.getSemesterId());
		}
		sb.append(") Data ");
		
		

		sb.append(" order by Data.name_th ");
		sb.append(" LIMIT " + pagingBean.getLimitItemFrom() + ","
				+ pagingBean.getLimitItemTo());
		String sql = sb.toString();
		returnList = this.jdbcTemplate.query(sql, new SubjectMapperOffset());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Subject getSubjectById(Long subjectId,String degree){
		StringBuffer sb = new StringBuffer();
		if("0".equals(degree))
			sb.append("  select * from pam_subject where status='A' and subject_id = "+subjectId);
		else
			sb.append("  select * from pam_master_subject where status='A' and subject_id = "+subjectId);
		Subject obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(), new RowMapper<Subject>() {
				public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
					Subject domain = new Subject();
					domain.setNameTh(rs.getString("name_th"));
					domain.setNameEn(rs.getString("name_en"));
					domain.setCredit(rs.getInt("credit"));
					domain.setLecHr(rs.getInt("lect_hr"));
					domain.setPrcHr(rs.getInt("prac_hr"));
					domain.setSelfHr(rs.getInt("self_hr"));
					domain.setDetails(rs.getString("detail"));
					domain.setSubjectId(rs.getLong("subject_id"));
					domain.setCode(rs.getString("code"));
					if("0".equals("degree"))
						domain.setDegree("degree_bachelor");
					else
						domain.setDegree("degree_master");
					return domain;
				}
			});			
		} catch (EmptyResultDataAccessException e) {
			//logger.error(e);
		}
		return obj;
	}
	
	private class SubjectMapper implements RowMapper<Subject> {
		@Override
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			Subject domain = new Subject();
			domain.setTeacherId(rs.getLong("teacher_id"));
			domain.setTeachName(rs.getString("teacher_tname"));
			domain.setNameTh(rs.getString("subject_name_th"));
			domain.setNameEn(rs.getString("subject_name_en"));
			domain.setCredit(rs.getInt("credit"));
			domain.setLecHr(rs.getInt("lect_hr"));
			domain.setPrcHr(rs.getInt("prac_hr"));
			domain.setSelfHr(rs.getInt("self_hr"));
			domain.setDetails(rs.getString("detail"));
			domain.setCode(rs.getString("subject_code"));
			return domain;
		}
	}
	
	private class SubjectMapperOffset implements RowMapper<Subject> {
		@Override
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			Subject domain = new Subject();
			domain.setNameTh(rs.getString("name_th"));
			domain.setNameEn(rs.getString("name_en"));
			domain.setCredit(rs.getInt("credit"));
			domain.setLecHr(rs.getInt("lect_hr"));
			domain.setPrcHr(rs.getInt("prac_hr"));
			domain.setSelfHr(rs.getInt("self_hr"));
			domain.setDetails(rs.getString("detail"));
			domain.setSubjectId(rs.getLong("subject_id"));
			domain.setCode(rs.getString("code"));
			domain.setYear(rs.getString("yearName"));
			domain.setSemester(rs.getString("semesterName"));
			domain.setDegreeType(rs.getInt("degree"));
			if(domain.getDegreeType()==0)
				domain.setDegree("degree_bachelor");
			else
				domain.setDegree("degree_master");
			return domain;
		}
	}
	
	
	@Override
	public List<Subject> getAllSubjectByUserId(Subject subject) {
		
		PagingBean pagingBean = new PagingBean();
		pagingBean.put("subject", subject);
		pagingBean = getAllSubjectByOffset(pagingBean);
		
		if (pagingBean.getTotalItems() > pagingBean.getMaxPageItems()) {
			pagingBean.setOffset(0);
			pagingBean.setMaxPageItems(pagingBean.getTotalItems());
			pagingBean = getAllSubjectByOffset(pagingBean);
		}
		
		List<Subject> subjectList = pagingBean.getCurrentPageItem();
		
		if (logger.isDebugEnabled()) {
			logger.info("subject.userId: " + subject.getUserId());
			logger.info("subject.yearId: " + subject.getYearId());
			logger.info("subject.semesterId: " + subject.getSemesterId());
			logger.info("subjectList.size(): " + subjectList.size());
		}
		
		return subjectList;
	}
}
