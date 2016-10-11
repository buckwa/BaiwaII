package com.buckwa.dao.impl.pbp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.AcademicUnitDao;
import com.buckwa.domain.pbp.AcademicUnit;
import com.buckwa.domain.pbp.AcademicUnitWrapper;
import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.MarkRankWrapper;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.school.SchoolUtil;

@Repository("academicUnitDao")
public class AcademicUnitDaoImpl implements AcademicUnitDao {
	private static Logger logger = LoggerFactory.getLogger(AcademicUnitDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private GenerateCodeUtil  generateCodeUtil;
 
	@Override
	public AcademicUnitWrapper getByAcademicYear( String getByAcademicYear) {		 		
		//String sql =" select *  from academic_unit where academic_year ='"+getByAcademicYear+"'" ; 
		String sql =" select *  from academic_unit " ; 
		logger.info(" sql:"+sql);
		List<AcademicUnit> academicUnitList  =null;
		
		try{
			academicUnitList = this.jdbcTemplate.query(sql,	new AcademicUnitMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		} 
		AcademicUnitWrapper academicUnitWrapper = new AcademicUnitWrapper();  
		academicUnitWrapper.setAcademicUnitList(academicUnitList);
		return academicUnitWrapper;
	}
	
	
	@Override
	public boolean isNameExist(AcademicUnit academicUnit) {
		 boolean returnValue = false;
		try{
			//String sqltmp = "select count(*) as totalRole  from academic_unit t  where t.name='"+StringEscapeUtils.escapeSql(academicUnit.getName())+"' and academic_year='"+academicUnit.getAcademicYear()+"'";
			String sqltmp = "select count(*) as totalRole  from academic_unit t  where t.name='"+StringEscapeUtils.escapeSql(academicUnit.getName())+"'";

			logger.info(" isNameExist sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isExistForUpdate(AcademicUnit academicUnit) {
		 boolean returnValue = false;
		try{
			//String sqltmp = "select count(*) as totalRole  from academic_unit t  where t.name='"+StringEscapeUtils.escapeSql(academicUnit.getName())+"'  and academic_year='"+academicUnit.getAcademicYear()+"' and academic_unit_id !="+academicUnit.getAcademicUnitId();
			String sqltmp = "select count(*) as totalRole  from academic_unit t  where t.name='"+StringEscapeUtils.escapeSql(academicUnit.getName())+"  and academic_unit_id !="+academicUnit.getAcademicUnitId();
			
			logger.info(" isExistForUpdate sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	
	
	
	@Override
	public void create(AcademicUnit academicUnit) {
		logger.info(" # .create # ");		
		final AcademicUnit finalacademicUnit = academicUnit;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into academic_unit (name, code,academic_year,enable) values (?, ?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalacademicUnit.getName());
				ps.setString(2,finalacademicUnit.getCode());
				ps.setString(3,finalacademicUnit.getAcademicYear());
				ps.setBoolean(4,true);		 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	 
	@Override
	public void update(AcademicUnit academicUnit) {
		logger.info("  update academicUnit: "+BeanUtils.getBeanString(academicUnit));
		this.jdbcTemplate.update(
				"update  academic_unit set name=?  where academicUnit=? ",
				academicUnit.getName(), academicUnit.getAcademicUnitId()); 
		
	}
	
	@Override
	public void edit(AcademicUnitWrapper academicUnitWrapper) {
		logger.info("  edit # ");		
		final AcademicUnitWrapper finalWrapper = academicUnitWrapper;
		
		List<AcademicUnit> unitList = finalWrapper.getAcademicUnitList();
		
		for(final AcademicUnit academicUnitTmp:unitList){			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  update  academic_unit set name =?   where academic_unit_id=? " +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,academicUnitTmp.getName());
				 ;
					ps.setLong(2, academicUnitTmp.getAcademicUnitId());
					return ps;  
					}
				} );			
			
		} 
 
	}
	
	
	
	@Override
	public AcademicUnit getById(String id) {		 		
		String sql =" select *  from academic_unit where academic_unit_id = "+id;
		AcademicUnit domain = this.jdbcTemplate.queryForObject(sql,	new AcademicUnitMapper() );				
	 			
		return domain;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete  from academic_unit where academic_unit_id = "+id);
		 	
	}	
	
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwarole role inner join buckwagrouprole grouprole " +
					"on (role.role_id = grouprole.role_id)	where role.role_id ="+id;
			
			logger.info(" sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	
	@Override
	public void addNew(AcademicUnit academicUnitk) {
		logger.info(" # .addNew # ");	
		final String academicYear = schoolUtil.getCurrentAcademicYear();
		final int nexCode = generateCodeUtil.getNextAcademicUnitCode(academicYear);
		final AcademicUnit finalDomain = academicUnitk;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into academic_unit (code ,academic_year) values (?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setInt(1,nexCode);
				ps.setString(2,academicYear);	 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	
	
	private class AcademicUnitMapper implements RowMapper<AcademicUnit> {   						
        @Override
		public AcademicUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicUnit domain = new AcademicUnit(); 
        	domain.setAcademicUnitId(rs.getLong("academic_unit_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setDescription(rs.getString("description"));	
			domain.setStatus(rs.getString("status"));	
			domain.setAcademicYear(rs.getString("academic_year"));
		return domain;
    }
	}
	
	
 
}
