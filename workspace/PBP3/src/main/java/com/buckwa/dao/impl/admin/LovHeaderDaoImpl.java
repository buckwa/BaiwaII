package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.LovHeader;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("lovHeaderDao")
public class LovHeaderDaoImpl implements LovHeaderDao {
	private static Logger logger = LoggerFactory.getLogger(LovHeaderDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<LovHeader> getAll() {
		List<LovHeader> returnList = new ArrayList<LovHeader>(); 
		String sql ="  select * from lov_header r  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<LovHeader>() {
				public LovHeader mapRow(ResultSet rs, int rowNum) throws SQLException {
					LovHeader domain = new LovHeader(); 					
					domain.setLovHeaderId(rs.getLong("lov_header_id"));					 
				return domain;
				}
				});

	return returnList;
	}

	@Override
	public void update(LovHeader lovHeader) {
		logger.info(" #LovHeaderDaoImpl.update lovHeader: "+BeanUtils.getBeanString(lovHeader));
		this.jdbcTemplate.update(
				"update  lov_header set code=?,name=?,status=? where lov_header_id=? ",
				lovHeader.getCode(), lovHeader.getName(),lovHeader.getStatus() ,lovHeader.getLovHeaderId());

	}
	
	@Override
	public void updateDetail(LovHeader lovHeader) {
		logger.info(" #updateDetail lovHeader: "+BeanUtils.getBeanString(lovHeader));
		this.jdbcTemplate.update(
				"update  lov_detail set code=?,name=? where lov_id=? ",
				lovHeader.getDetailCode(), lovHeader.getDetailName(),lovHeader.getLovDetailId());

	}
	
	

	@Override
	public void create(LovHeader lovHeader) {
		logger.info(" #LovHeaderDaoImpl.create # ");		
	try{ 
		this.jdbcTemplate.update(
				"insert into lov_header (code, name,status) values (?, ?,? )",
				lovHeader.getCode() ,lovHeader.getName(),lovHeader.getStatus() );			
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	}

	@Override
	public void createLOVDetail(LovDetail lovDetail) {
		logger.info(" #LovHeaderDaoImpl.create # ");		
	try{ 
		this.jdbcTemplate.update(
				"insert into lov_detail (code, name,status,lov_header_id) values (?, ?,? ,?)",
				lovDetail.getCode() ,lovDetail.getName(),lovDetail.getStatus() ,lovDetail.getHeaderId());			
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	}
	
	
	public PagingBean getAllByOffset(PagingBean pagingBean) {
		logger.info("  ## getAllByOffset ");
		
		List<LovHeader> returnList = new ArrayList<LovHeader>();		
		LovHeader lovHeader = (LovHeader)pagingBean.get("lovHeader");	
		StringBuffer totalSql  = new StringBuffer();
	 
		totalSql.append("  select count(*) as total_item  from  lov_header t where 1=1 ") ;		
		if(StringUtils.hasText(lovHeader.getCode())){
			totalSql.append(" and t.code like  '%"+StringEscapeUtils.escapeSql(lovHeader.getCode().trim())+"%'");
		}
		if(StringUtils.hasText(lovHeader.getName())){
			totalSql.append(" and t.name like  '%"+StringEscapeUtils.escapeSql(lovHeader.getName().trim())+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(totalSql.toString(),Integer.class); 
		
		
		pagingBean.setTotalItems(rows_totalItem);			
	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from lov_header r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(lovHeader.getCode())){
			sb.append(" and r.code like  '%"+StringEscapeUtils.escapeSql(lovHeader.getCode().trim())+"%'");
		}
		if(StringUtils.hasText(lovHeader.getName())){
			sb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(lovHeader.getName().trim())+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<LovHeader>() {
				public LovHeader mapRow(ResultSet rs, int rowNum) throws SQLException {
					LovHeader lovHeader = new LovHeader();				 
					lovHeader.setLovHeaderId(rs.getLong("lov_header_id"));
					lovHeader.setCode(rs.getString("code"));
					lovHeader.setName(rs.getString("name")); 
					lovHeader.setStatus(rs.getString("status"));	 
				return lovHeader;
				}
				});
		pagingBean.setCurrentPageItem(returnList);
		return pagingBean;
	}
	
	

	@Override
	public LovHeader getById(String lovHeaderId) {		 
		LovHeader lovHeader = this.jdbcTemplate.queryForObject(
				"select *  from lov_header where lov_header_id = ?",
				new Object[]{lovHeaderId},
				new RowMapper<LovHeader>() {
				public LovHeader mapRow(ResultSet rs, int rowNum) throws SQLException {
					LovHeader lovHeader = new LovHeader();
					lovHeader.setLovHeaderId(rs.getLong("lov_header_id"));
					lovHeader.setCode(rs.getString("code"));
					lovHeader.setName(rs.getString("name")); 
					lovHeader.setStatus(rs.getString("status"));	 
				return lovHeader;
				}
				});
		
		if(lovHeader!=null){ 
			List <LovDetail> lovDetailList = this.jdbcTemplate.query(
					"select *  from lov_detail where lov_header_id = ?",
					new Object[]{lovHeaderId},
					new RowMapper<LovDetail>() {
					public LovDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						LovDetail lovHeader = new LovDetail();
						lovHeader.setLovId(rs.getLong("lov_id"));
						lovHeader.setCode(rs.getString("code"));
						lovHeader.setName(rs.getString("name")); 
						lovHeader.setStatus(rs.getString("status"));	 
					return lovHeader;
					}
					});	 
			
			lovHeader.setDetailList(lovDetailList);
		} 
		
		
		
		return lovHeader;
	}	
	
	
	@Override
	public LovDetail getDetailById(String lovId) {		 
	 
		LovDetail returnValue = null;
	 
		returnValue = this.jdbcTemplate.queryForObject(
					"select *  from lov_detail where lov_id = ?",
					new Object[]{lovId},
					new RowMapper<LovDetail>() {
					public LovDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						LovDetail domain = new LovDetail();
						domain.setLovId(rs.getLong("lov_id"));
						domain.setCode(rs.getString("code"));
						domain.setName(rs.getString("name")); 
						domain.setStatus(rs.getString("status"));	 
					return domain;
					}
					});	  
		 
		
		
		
		return returnValue;
	}	


	
	
	@Override
	public void deleteById(String lovHeaderId) {	 
		this.jdbcTemplate.update(" delete from  lov_header where lov_header_id ="+lovHeaderId);		
	}
	
	@Override
	public void deleteDetailById(String id) {	 
		this.jdbcTemplate.update(" delete from  lov_detail where lov_id ="+id);		
	}
	
	
	
 
	@Override
	public boolean isAlreadyUsege(String lovHeaderId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwalovHeader lovHeader inner join buckwagrouplovHeader grouplovHeader " +
					"on (lovHeader.lovHeader_id = grouplovHeader.lovHeader_id)	where lovHeader.lovHeader_id ="+lovHeaderId;
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
	public boolean isExist(String code,String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalLovHeader  from lov_header lovHeader  where lovHeader.code='"+StringEscapeUtils.escapeSql(code)+"' or  lovHeader.name='"+StringEscapeUtils.escapeSql(name)+"'";
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
	public boolean isExistForUpdate(String code,String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalLovHeader  from lov_header t  where (t.code='"+StringEscapeUtils.escapeSql(code)+"' or t.name='"+StringEscapeUtils.escapeSql(name)+"') and t.lov_header_id !="+id;
			logger.info("  isExistForUpdate sql:"+sqltmp);
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
	public boolean isDetailExist(String code,String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalLOV  from lov_detail lov  where (lov.code='"+StringEscapeUtils.escapeSql(code)+"' or lov.name='"+StringEscapeUtils.escapeSql(name)+"') and lov.lov_id !="+id;
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
	public boolean isDetailExistForUpdate(String code,String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalLovHeader  from lov_detail t  where (t.code='"+StringEscapeUtils.escapeSql(code)+"' or t.name='"+StringEscapeUtils.escapeSql(name)+"') and t.lov_detail_id !="+id;
			logger.info("  isExistForUpdate sql:"+sqltmp);
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
	public List<LovDetail> getDetailListByCode(String code) {
		String sql = " SELECT d.code, d.name " +
			" FROM lov_header h " +
			"   INNER JOIN lov_detail d ON h.lov_header_id = d.lov_header_id " +
			" WHERE 1=1 " +
			"   AND d.status = 1 " +
			"   AND h.code = ? ";
		
		List<LovDetail> lovDetailList = this.jdbcTemplate.query(sql, lovDetailMapper, new Object[] {code});
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql);
			logger.info("criteria: " + code);
			if (null != lovDetailList) {
				logger.info("result.size(): " + lovDetailList.size());
			}
		}
		
		return lovDetailList;
	}	
	
	
	public List<LovDetail> 	getLovAcademicRankList( ) {
		String sql = "select distinct academic_rank as code  from person_pbp ";
			
			List<LovDetail> lovDetailList = this.jdbcTemplate.query(sql, lovDetailPBPMapper );
			
			if (logger.isDebugEnabled()) {
				logger.info("sql: " + sql);
			 
				if (null != lovDetailList) {
					logger.info("result.size(): " + lovDetailList.size());
				}
			}
			
			return lovDetailList;
		}	 
	public List<LovDetail> getLovMaxEducationList( ) {
		String sql = "select distinct max_education as code  from person_pbp ";
		
		List<LovDetail> lovDetailList = this.jdbcTemplate.query(sql, lovDetailPBPMapper );
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql);
		 
			if (null != lovDetailList) {
				logger.info("result.size(): " + lovDetailList.size());
			}
		}
		
		return lovDetailList;
		}	
	
	
	private static final LovDetailMapper lovDetailMapper = new LovDetailMapper();
	private static class LovDetailMapper implements RowMapper<LovDetail> {
		@Override
		public LovDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			LovDetail domain = new LovDetail();
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("name"));
			return domain;
		}
	}
	
	private static final LovDetailPBPMapper lovDetailPBPMapper = new LovDetailPBPMapper();
	private static class LovDetailPBPMapper implements RowMapper<LovDetail> {
		@Override
		public LovDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			LovDetail domain = new LovDetail();
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("code"));
			return domain;
		}
	}
	
}
