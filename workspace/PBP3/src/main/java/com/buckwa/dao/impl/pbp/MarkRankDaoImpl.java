package com.buckwa.dao.impl.pbp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.MarkRankDao;
import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.MarkRankRound;
import com.buckwa.domain.pbp.MarkRankWrapper;

@Repository("markRankDao")
public class MarkRankDaoImpl implements MarkRankDao {
	private static Logger logger = LoggerFactory.getLogger(MarkRankDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
 
	@Override
	public MarkRankWrapper getByAcademicYear( String getByAcademicYear) {		 		
		String sql =" select *  from mark_rank where academic_year ='"+getByAcademicYear+"'" ;  
		
		logger.info("  getByAcademicYear sql:"+sql);
		
		MarkRankWrapper markRankWrapper = new MarkRankWrapper();	 				
		List<MarkRank> markRankList  = this.jdbcTemplate.query(sql,	new MarkRankMapper() );				
		markRankWrapper.setMarkRankList(markRankList);
	 
		return markRankWrapper;
	}
	
	@Override
	public MarkRankWrapper getByAcademicYearRound( String getByAcademicYear,String employeeType,String round) {		 		
		String sql =" select *  from mark_rank where academic_year ='"+getByAcademicYear+"' and employee_type='"+employeeType+"' and round ='"+round+"'" ;  
		
		logger.info("  getByAcademicYearRound sql:"+sql);
		
		MarkRankWrapper markRankWrapper = new MarkRankWrapper();	 				
		List<MarkRank> markRankList  = this.jdbcTemplate.query(sql,	new MarkRankMapper() );				
		markRankWrapper.setMarkRankList(markRankList);
	 
		return markRankWrapper;
	}
	
	@Override
	public MarkRankWrapper getByRound( String getByAcademicYear) {	
		
		
		List<MarkRankRound>  markRankRoundList = new ArrayList();
		MarkRankRound markRankRound0_1 = new MarkRankRound();
		MarkRankRound markRankRound0_2 = new MarkRankRound();
		MarkRankRound markRankRound1_1 = new MarkRankRound();
		
		String sql0_1 =" select *  from mark_rank where academic_year ='"+getByAcademicYear+"' and employee_type='0' and round ='1'" ;  
		String sql0_2 =" select *  from mark_rank where academic_year ='"+getByAcademicYear+"' and employee_type='0' and round ='2'" ;  
		String sql1_1 =" select *  from mark_rank where academic_year ='"+getByAcademicYear+"' and employee_type='1' and round ='1'" ;  
		
		logger.info("  sql0_1 sql:"+sql0_1);
		logger.info("  sql0_2 sql:"+sql0_2);
		logger.info("  sql1_1 sql:"+sql1_1);
		
		List<MarkRank> markRankList0_1  = this.jdbcTemplate.query(sql0_1,	new MarkRankMapper() );	
		List<MarkRank> markRankList0_2  = this.jdbcTemplate.query(sql0_2,	new MarkRankMapper() );	
		List<MarkRank> markRankList1_1  = this.jdbcTemplate.query(sql1_1,	new MarkRankMapper() );	
		
		
		markRankRound0_1.setMarkRankList(markRankList0_1);
		markRankRound0_1.setEmployeeType("0");
		markRankRound0_1.setRound("1");
		
		
		markRankRound0_2.setMarkRankList(markRankList0_2);
		markRankRound0_2.setEmployeeType("0");
		markRankRound0_2.setRound("2");
		
		
		markRankRound1_1.setMarkRankList(markRankList1_1);
		markRankRound1_1.setEmployeeType("1");
		markRankRound1_1.setRound("1");
		
		
		// Comment 2 Round
		//markRankRoundList.add(markRankRound0_1);
		//markRankRoundList.add(markRankRound0_2);
		markRankRoundList.add(markRankRound1_1);
		
		MarkRankWrapper markRankWrapper = new MarkRankWrapper();	 				
		markRankWrapper.setMarkRankRoundList(markRankRoundList);
	 
		return markRankWrapper;
	}
	
	@Override
	public boolean isExist(MarkRank markRank) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalRole  from mark_rank t  where t.mark_from="+markRank.getMarkFrom()+" or t.mark_to="+markRank.getMarkTo() ;
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
	public void create(MarkRank markRank) {
		logger.info(" #RoleDaoImpl.create # ");		
		final MarkRank finalmarkRank = markRank;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into mark_rank (mark_from, mark_to,academic_year) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setInt(1,finalmarkRank.getMarkFrom());
				ps.setInt(2,finalmarkRank.getMarkTo());
				ps.setString(3,finalmarkRank.getAcademicYear());		 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	
	@Override
	public void edit(MarkRankWrapper markRankWrapper) {
		logger.info("  edit # ");		
		final MarkRankWrapper finalMarkRankWrapper = markRankWrapper;
		
		List<MarkRank> markRankList = finalMarkRankWrapper.getMarkRankList();
		
		for(final MarkRank markRankTmp:markRankList){			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  update  mark_rank set mark_from =? ,mark_to=?,academic_year=?,salary_level=? where mark_rank_id=? and academic_year='"+finalMarkRankWrapper.getAcademicYear()+"'" +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setInt(1,markRankTmp.getMarkFrom());
					ps.setInt(2,markRankTmp.getMarkTo());
					ps.setString(3,finalMarkRankWrapper.getAcademicYear());	
					ps.setInt(4,markRankTmp.getSalaryLevel());
					ps.setLong(5, markRankTmp.getMarkRankId());
					return ps;  
					}
				} );			
			
		} 
 
	}
	
	@Override
	public void editRound(MarkRankWrapper markRankWrapper) {
		logger.info("  edit # ");		
		final MarkRankWrapper finalMarkRankWrapper = markRankWrapper;
		
		List<MarkRank> markRankList = finalMarkRankWrapper.getMarkRankList();
		
		for(final MarkRank markRankTmp:markRankList){			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  update  mark_rank set mark_from =? ,mark_to=?,academic_year=?,salary_level=? where mark_rank_id=? and academic_year='"+finalMarkRankWrapper.getAcademicYear()+"' and employee_type='"+finalMarkRankWrapper.getEmployeeType()+"' and round='"+finalMarkRankWrapper.getRound()+"' " +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setInt(1,markRankTmp.getMarkFrom());
					ps.setInt(2,markRankTmp.getMarkTo());
					ps.setString(3,finalMarkRankWrapper.getAcademicYear());	
					ps.setInt(4,markRankTmp.getSalaryLevel());
					ps.setLong(5, markRankTmp.getMarkRankId());
					return ps;  
					}
				} );			
			
		} 
 
	}
	
	
	
	
	@Override
	public void delete(String  markRankId) {
		logger.info(" #RoleDaoImpl.delete # ");		
	        String deleteSQL ="delete from  mark_rank where mark_rank_id ="+markRankId;
	        logger.info(" #### delete:"+deleteSQL);
			this.jdbcTemplate.update(deleteSQL);	
		 
	 
 
	}
	
	
	
	@Override
	public void addNew(MarkRank markRank) {
		logger.info(" #RoleDaoImpl.addNew # ");		
		final MarkRank finalmarkRank = markRank;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into mark_rank (mark_from, mark_to, academic_year) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setInt(1,0);
				ps.setInt(2,0);
				ps.setString(3,finalmarkRank.getAcademicYear());		 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	 
	@Override
	public void addNewRound(MarkRank markRank) {
		logger.info(" #RoleDaoImpl.addNewRound # ");		
		final MarkRank finalmarkRank = markRank;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into mark_rank (mark_from, mark_to, academic_year,employee_type,round) values (?, ?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setInt(1,0);
				ps.setInt(2,0);
				ps.setString(3,finalmarkRank.getAcademicYear());	
				ps.setString(4, finalmarkRank.getEmployeeType());
				ps.setString(5, finalmarkRank.getRound());
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	} 
 
	
	private class MarkRankMapper implements RowMapper<MarkRank> {   						
        @Override
		public MarkRank mapRow(ResultSet rs, int rowNum) throws SQLException {
        	MarkRank domain = new MarkRank(); 
        	domain.setMarkRankId(rs.getLong("mark_rank_id"));
			domain.setMarkFrom(rs.getInt("mark_from"));		
			domain.setMarkTo(rs.getInt("mark_to"));	
			domain.setSalaryLevel(rs.getInt("salary_level"));			 
			domain.setAcademicYear(rs.getString("academic_year"));
		return domain;
    }
	}
	
	
 
}
