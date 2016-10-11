package com.buckwa.dao.impl.pam;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.PersonEvaluateMappingDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.MarkLevel;
import com.buckwa.domain.pam.MarkLevelDetail;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.PersonEvaluateMapping;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.nodetree.EvaluateKpi;
import com.buckwa.domain.pam.nodetree.EvaluateKpiTree;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaUtils;

/**
 *
@Author : Kroekpong Sakulchai
@Create : Aug 23, 2012 7:30:26 PM
 *
 **/

@Repository("PersonMappingEvaluateDao")
public class PersonEvaluateMappingDaoImpl implements PersonEvaluateMappingDao{
	
	private static Logger logger = LoggerFactory.getLogger(PersonEvaluateMappingDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Override
//	public String getEvaluateStatusByWorkLineCode(String worklineCode) { // FIXME input year_id & semester_id
//		
//		logger.info("Strat DAO >> getEvaluateStatusByWorkLineCode()");
//
//		String evaluateStatus = "W";
//		
//		String year_id = "2012";
//		String semester_id = "4"; // FIXME
//				
//			try{
//				String sql = " SELECT * FROM evaluate_person_mapping WHERE person_id = " +
//						" (SELECT person_id FROM workline_person_mapping WHERE workline_code ='"+StringEscapeUtils.escapeSql(worklineCode.trim())+"')  AND  year_id ="+year_id+" AND semeter_id="+semester_id;
//				logger.info(" sql:"+sql);
//				List<PersonEvaluateMapping> evaluateMapper = this.jdbcTemplate.query(sql, new PersonCheckEvaluateMapper());
//				if(evaluateMapper.size()>0){
//					evaluateStatus = evaluateMapper.get(0).getEvaluateStatus();
//				}
//			}catch(Exception ex){
//				ex.printStackTrace();
//			}			
//			
//			logger.info(" Evaluate Status :"+evaluateStatus);
//		return evaluateStatus.trim();
//	}
	
//	@Override
//	public String getEvaluateStatusByPersonIdYearIdSemesterId(String personId ,String yearId, String semesterId) { 
//		
//		logger.info("Strat DAO >> getEvaluateStatusByPersonIdYearIdSemesterId()");
//		
//		String evaluateStatus = "W";
//		
//		try{
//			String sql = " SELECT * FROM evaluate_person_mapping WHERE person_id ="+personId+" AND  year_id ="+yearId+" AND semeter_id="+semesterId;
//			logger.info(" sql:"+sql);
//			List<PersonEvaluateMapping> evaluateMapper = this.jdbcTemplate.query(sql, new PersonCheckEvaluateMapper());
//			if(evaluateMapper.size()>0){
//				evaluateStatus = evaluateMapper.get(0).getEvaluateStatus();
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}			
//		
//		logger.info(" Evaluate Status :"+evaluateStatus);
//		return evaluateStatus.trim();
//	}
	
	@Override
	public String getEvaluateStatusByPersonIdYearIdByTerm(String personId ,String yearId, String semesterId) { 
		
		logger.info("Strat DAO >> getEvaluateStatusByPersonIdYearIdSemesterId()");
		
		String evaluateStatus = "W";
		
		try{
			String sql = " SELECT * FROM evaluate_person_mapping WHERE person_id ="+personId+" AND  year_id ="+yearId+" AND semeter_id="+semesterId;
			logger.info(" sql:"+sql);
			List<PersonEvaluateMapping> evaluateMapper = this.jdbcTemplate.query(sql, new PersonCheckEvaluateMapper());
			if(evaluateMapper.size()>0){
				evaluateStatus = evaluateMapper.get(0).getEvaluateStatus();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		
		logger.info(" Evaluate Status :"+evaluateStatus);
		return evaluateStatus.trim();
	}
	
	
	private static final String SQL_CREATE =
			" INSERT INTO evaluate_person_mapping ( " +
			"   person_id, " +
			"   year_id , " +
			"   semeter_id , " +
			"   evaluate_name, " +
			"   evaluate_status, " +
			"   evaluate_by_person_id, " +
			"   status, " +
			"   create_by, " +
			"   create_date, " +
			"   update_by, " +
			"   update_date " +
			" ) " +
			" VALUES ( " +
			"   ?, ?, ?, ?, 'S', ?, " +
			"   'A', ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP) ";
	
	private static final String SQL_UPDATE_STATUS_PROCESS =
			" UPDATE evaluate_person_mapping " +
			" SET evaluate_status = 'P', " +
			"     update_by = ?, " +
			"     update_date = CURRENT_TIMESTAMP " +
			" WHERE person_id = ? ";
		
	@Override
	public int createOrUpdatePersonEvaluate(final PersonEvaluateMapping evaluateMapping) {
		
		logger.info("Strat DAO >> createOrUpdatePersonEvaluate()");
		
		String sql = "SELECT *  FROM evaluate_person_mapping  WHERE person_id = '"+StringEscapeUtils.escapeSql(evaluateMapping.getPerson().getPersonId().toString().trim())+"'" +
				" AND year_id ="+evaluateMapping.getYear().getYearId()+" AND semeter_id ="+evaluateMapping.getSemester().getSemesterId();
		
		logger.info(" sql:"+sql);
		
		List<PersonEvaluateMapping> evaluateMapper = this.jdbcTemplate.query(sql, new PersonCheckEvaluateMapper());

		KeyHolder keyHolder = new GeneratedKeyHolder(); 	
		
		Integer returnid = new Integer(0);
		
		if(evaluateMapper.size() == 0){ 
			logger.info("Strat DAO >> DO CREATE ");
			
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, evaluateMapping.getPerson().getPersonId());
					ps.setLong(2, evaluateMapping.getYear().getYearId());
					ps.setLong(3, evaluateMapping.getSemester().getSemesterId());
					ps.setString(4, evaluateMapping.getEvaluateName());
					ps.setString(5, evaluateMapping.getEvaluateByPersonId());
					ps.setString(6, evaluateMapping.getCreateBy());
					ps.setString(7, evaluateMapping.getCreateBy());
					return ps;
				}
			}, keyHolder);
			
			 returnid =  keyHolder.getKey().intValue();
		}
		
//		else {
//			logger.info("Strat DAO >> DO UPDATE ");
//			
//			jdbcTemplate.update(new PreparedStatementCreator() {
//				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
//					PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_STATUS_PROCESS );
//					ps.setString(1, evaluateMapping.getCreateBy());
//					ps.setLong(2, evaluateMapping.getPerson().getPersonId());
//					return ps;
//				}
//			}, keyHolder );
//		}
//		
		return returnid;
	}
	
	
	private static final String SQL_UPDATE_STATUS_SUCCESS =
			" UPDATE evaluate_person_mapping " +
					" SET evaluate_status = 'S', " +
					"     update_date = CURRENT_TIMESTAMP " +
					" WHERE person_id = ? ";
	
	@Override
	public void endEvaluateSession(final String personId) {
		
		logger.info("Strat DAO >> endEvaluateSession()");

			try{
				
				jdbcTemplate.update(new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
						PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_STATUS_SUCCESS);
						ps.setString(1, personId.trim());
						return ps;
					}
				});
			}catch(Exception ex){
				ex.printStackTrace();
			}			
			
	}
	
	
	@Override
	public BigDecimal getPersonEvaluateTotalScore(final String personId) {
		
		BigDecimal markScore = new BigDecimal(0);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  SUM(mark) FROM work_person_kpi wp , kpi_tree kt  "); 
		sql.append(" WHERE wp.kpi_id=kt.kpi_tree_id AND  kpi_id <> 0 "); 
		sql.append(" AND wp.work_person_attr_id IN (                 "); 
		sql.append(" SELECT  work_person_attr_id                     "); 
		sql.append(" FROM work_person_attr WHERE work_person_id IN (	");
		sql.append(" SELECT work_person_id FROM work_person          "); 
		sql.append(" WHERE user_id = (SELECT user_id FROM buckwauser "); 
		sql.append(" WHERE USERNAME = (SELECT email FROM person      "); 
		sql.append(" WHERE person_id = " +personId.trim()+	" ))))");
		
		
		Long score = jdbcTemplate.queryForObject(sql.toString(),Long.class); 
		markScore = new BigDecimal(score);
		
		return markScore;
	}
	
	@Override
	public List<KpiPersonEvaluateMapping> getKPIPersonEvaluate(final String personId , final int yearId , final int semesterId) {
		
		List<KpiPersonEvaluateMapping> evaluateMappings = new ArrayList<KpiPersonEvaluateMapping>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * , (SELECT wpa.VALUE FROM work_person_attr wpa WHERE wpa.work_person_attr_id=wp.work_person_attr_id) VALUE  "); 
		sql.append(" FROM work_person_kpi wp JOIN kpi_tree kt ON kt.kpi_tree_id = wp.kpi_id "); 
		sql.append(" join work_person wps on wps.work_person_id = wp.work_person_id   "); 
		sql.append(" WHERE wp.kpi_id <> 0  AND wp.work_person_attr_id IN  "); 
		sql.append(" (SELECT  work_person_attr_id FROM work_person_attr WHERE work_person_id IN 	");
		sql.append(" (SELECT work_person_id FROM work_person WHERE user_id =  "); 
		sql.append(" (SELECT user_id FROM buckwauser  WHERE USERNAME =  "); 
		sql.append(" (SELECT email FROM person         "); 
		sql.append(" WHERE person_id = " +personId.trim()+	" ))))");
//		sql.append(" AND wps.year_id = " +yearId);
		sql.append(" AND (SELECT year_id FROM YEAR WHERE  start_date <= wp.created_date AND wp.created_date <= end_date)=(SELECT year_id FROM YEAR WHERE  start_date <= NOW() AND NOW() <= end_date) ");
//		sql.append(" AND wps.semester_id = " +semesterId);
		
		evaluateMappings = this.jdbcTemplate.query(sql.toString(), new KPIPersonEvaluateMapper());
		
		
//		int  rows_totalItem = jdbcTemplate.queryForObject(sql.toString()); 
		
//		score = rows_totalItem*100; //FIXME
		logger.info(" sql:"+sql);
		
		return evaluateMappings;
	}
	
	@Override
	public List<KpiPersonEvaluateMapping> getEvaluateKpiEstimateScore(final String personEvaluateId , final int evaluateTerm) {
		
		List<KpiPersonEvaluateMapping> evaluateMappings = new ArrayList<KpiPersonEvaluateMapping>();
		
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT * FROM evaluate_kpi_tree WHERE person_map_evaluate_id = (SELECT person_map_evaluate_id FROM evaluate_person_mapping WHERE person_id ="+StringEscapeUtils.escapeSql(personEvaluateId.trim())+" ) AND evaluate_term ="+evaluateTerm); 
		
		evaluateMappings = this.jdbcTemplate.query(sql.toString(), new EstimateKPIEvaluateMapper());
		
		logger.info(" sql:"+sql);
		
		return evaluateMappings;
	}
	
	@Override
	public void updateEvaluateKpiEstimateScore(final String evaluateKpiId,  final BigDecimal estimateScore) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE evaluate_kpi_tree SET estimate_score = "+estimateScore+" WHERE evaluate_kpi_tree_id = "+StringEscapeUtils.escapeSql(evaluateKpiId)); 
		
		int success = this.jdbcTemplate.update(sql.toString());
		
		logger.info(" sql:"+sql);
		
	}
	
	@Override
	public PagingBean getAllEvaluatePersonByOffset(PagingBean pagingBean) {
		
		logger.info("Strat DAO >> getAllEvaluatePersonByWorkLineCode()");

		
		List<PersonEvaluateMapping> personEvaluateMappingList = new ArrayList<PersonEvaluateMapping>();
		
		PersonEvaluateMapping personEvaluateMapping =  (PersonEvaluateMapping)pagingBean.get("personEvaluateMapping");
		
		String worklineCode = personEvaluateMapping.getPerson().getWorklineCode();
		String yearId = "";
		String semesterId = "";
		String evaluateStatus = personEvaluateMapping.getEvaluateStatus();
		
		if(null!=personEvaluateMapping.getYear().getYearId()){
			yearId=Long.toString(personEvaluateMapping.getYear().getYearId());
		}
		if(null!=personEvaluateMapping.getSemester().getSemesterId()){
			semesterId=Long.toString(personEvaluateMapping.getSemester().getSemesterId());
		}
		
		if(null == evaluateStatus) evaluateStatus = "S";
				
			try{
				
				StringBuffer sb = new StringBuffer();
				
				sb.append("  SELECT * FROM person ps ,evaluate_person_mapping pem  WHERE ps.person_id=pem.person_id   ");
				sb.append("  AND ps.status = 'A'   AND pem.status = 'A' ");
				
				if(StringUtils.hasText(evaluateStatus)){
					sb.append("  AND pem.evaluate_status = '"+StringEscapeUtils.escapeSql(evaluateStatus)+"' ");
				}
				if(StringUtils.hasText(worklineCode)){
					sb.append(" AND ps.workline_code = '"+StringEscapeUtils.escapeSql(worklineCode)+"' ");
				}
				if(StringUtils.hasText(yearId)){
					sb.append("  AND pem.year_id = '"+StringEscapeUtils.escapeSql(yearId)+"' ");
				}
				if(StringUtils.hasText(semesterId)){
					sb.append("  AND pem.semeter_id = '"+StringEscapeUtils.escapeSql(semesterId)+"' ");
				}
				
				sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
				
				
				// Count all result
//				Integer  rows_totalItem = jdbcTemplate.queryForObject(sb.toString()); 
//				pagingBean.setTotalItems(rows_totalItem);	
				
				
				logger.info(" sql:"+sb.toString());				
				
				personEvaluateMappingList = this.jdbcTemplate.query(sb.toString(), new PersonCheckEvaluateMapper());
				
				pagingBean.setTotalItems(personEvaluateMappingList.size());
				
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}			
			
			logger.info(" Evaluate MappingList :"+personEvaluateMappingList);
			
			pagingBean.setCurrentPageItem(personEvaluateMappingList);
		
		return pagingBean;
	}
	
	@Override
	public EvaluateKpiTree getKPIEvaluateByYearAndGroupId(String yearId,String groupId) {
	
 
		String sqltmp = "select t.kpi_id from kpi_year_mapping  t  where t.year_id="+ yearId + " and t.category_id=" + groupId;

		logger.info(" ## getKPIEvaluateByYearAndGroupId sql\n:" + sqltmp);
		try {
			String kpiIdStr = this.jdbcTemplate	.queryForObject(sqltmp, String.class); 
			EvaluateKpiTree kpiTree = new EvaluateKpiTree();
	        Node<EvaluateKpi> rootElement = new Node<EvaluateKpi>(getById(new Long(kpiIdStr)));
	        getRecursive(rootElement, kpiTree);
	        kpiTree.setRootElement(rootElement);
	        return kpiTree;
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
    }
	
	@Override
	public EvaluateKpi getById(Long kpiId) {
		String sql =" select *  from kpi_tree where kpi_tree_id = "+kpiId;
		EvaluateKpi kpi = this.jdbcTemplate.queryForObject(sql,	new EvaluateKpiMapper() );	 		
		return kpi;
	}
	
	@Override
	public void getRecursive(Node<EvaluateKpi> taskElement, EvaluateKpiTree evaluateKpiTree) {
		   List<EvaluateKpi> children = getByParentId(taskElement.getData().getKpiId());
	        List<Node<EvaluateKpi>> childElements = new ArrayList<Node<EvaluateKpi>>();
	        for (Iterator<EvaluateKpi> it = children.iterator(); it.hasNext();) {
	        	EvaluateKpi childKpi = it.next();
	        	logger.info(" ## kpiId:"+childKpi.getKpiId());
	            Node<EvaluateKpi> childElement = new Node<EvaluateKpi>(childKpi);
	            childElements.add(childElement);
	            getRecursive(childElement, evaluateKpiTree);
	        }
	        taskElement.setChildren(childElements);

	}
	
	@Override
	public List<EvaluateKpi> getByParentId(Long kpiId) {
		List<EvaluateKpi> returnList = new ArrayList<EvaluateKpi>();
         String sql ="select * from kpi_tree where parent_id="+kpiId;
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(sql,new EvaluateKpiMapper()); 
		return returnList;
	}

	
	private MarkLevel getMarkLevel(Long levelId,Long kpiTreeId){
		
		// Step 1 Get Mark Level
		try{
		String sql =" select *  from mark_level  where mark_level_id = "+levelId;
		String sqldetail =" select *  from mark_level_detail  where mark_level_id = "+levelId;
		
		logger.info(" ### getMarkLevel sql:"+sql);
		MarkLevel markLevel = this.jdbcTemplate.queryForObject(sql,	new MarkLevelMapper() );	 		
		
		// Step 2 Get MarkLevelDetail List
		logger.info("  ### getMarkLevel  markLevel"+markLevel);
		
		List<MarkLevelDetail> returnList = this.jdbcTemplate.query(
				sqldetail,
				new RowMapper<MarkLevelDetail>() {
				public MarkLevelDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
					MarkLevelDetail  domain = new MarkLevelDetail();				 
					domain.setMarkLevelDetailId(rs.getLong("mark_level_detail_id"));
					domain.setMarkLevelId(rs.getLong("mark_level_id"));
					domain.setLevel(rs.getInt("level"));
					domain.setMark(rs.getBigDecimal("mark"));
				return domain;
				}
				}); 
		
		markLevel.setMarkLevelDetailList(returnList);
		logger.info("  ### getMarkLevel  returnList"+returnList);
		return markLevel;
		
		}catch(org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
			
			logger.info(" ### Empty !!! , Create new mark leve");
			
			// Create initial Mark Level 
			final KeyHolder markLevelkeyHolder = new GeneratedKeyHolder();		
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(" INSERT INTO mark_level (create_date ) VALUES ( ? )", 
							Statement.RETURN_GENERATED_KEYS);				 
					ps.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));			 
					return ps;
				}
			}, markLevelkeyHolder); 
			logger.info(" ### Empty !!! , Create new mark leve markLevelkeyHolder:"+markLevelkeyHolder);
			KeyHolder markLevelkeyHolderDetail = new GeneratedKeyHolder();	 
			for(int i=0;i<5;i++){
				final int myFinalVariable =i;
				jdbcTemplate.update(new PreparedStatementCreator( ) {
					 
					public PreparedStatement createPreparedStatement(Connection connection ) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(" INSERT INTO mark_level_detail (mark_level_id,level,mark,create_date ) VALUES ( ?,?,?,? )", 
								Statement.RETURN_GENERATED_KEYS);				
						ps.setLong(1, markLevelkeyHolder.getKey().longValue());
						ps.setInt(2,myFinalVariable);
						ps.setBigDecimal(3,new BigDecimal(0.00));
						ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));			 
						return ps;
					}
				},markLevelkeyHolderDetail);			
				
			}
			
			String sql =" select *  from mark_level  where mark_level_id = "+markLevelkeyHolder.getKey().longValue();
			String sqldetail =" select *  from mark_level_detail  where mark_level_id = "+markLevelkeyHolder.getKey().longValue(); 
			logger.info(" ### getMarkLevel sql:"+sql);
			MarkLevel markLevel = this.jdbcTemplate.queryForObject(sql,	new MarkLevelMapper() );	 		 
			// Step 2 Get MarkLevelDetail List
			logger.info("  ### getMarkLevel  markLevel e"+markLevel); 
			List<MarkLevelDetail> returnList = this.jdbcTemplate.query(
					sqldetail,
					new RowMapper<MarkLevelDetail>() {
					public MarkLevelDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						MarkLevelDetail  domain = new MarkLevelDetail();				 
						domain.setMarkLevelDetailId(rs.getLong("mark_level_detail_id"));
						domain.setMarkLevelId(rs.getLong("mark_level_id"));
						domain.setLevel(rs.getInt("level"));
						domain.setMark(rs.getBigDecimal("mark"));
					return domain;
					}
					}); 
			
			markLevel.setMarkLevelDetailList(returnList);
			
			 
			String sqlupdate="  UPDATE kpi_tree SET mark_level_id ="+markLevelkeyHolder.getKey().longValue()+ " where kpi_tree_id="+kpiTreeId;
			logger.info(" ## sql: "+sqlupdate);
			//sql.append( sqlupdate); 
			
			int success = this.jdbcTemplate.update(sqlupdate);
			
			logger.info("  ### getMarkLevel  returnList"+returnList);
			return markLevel;
			
		}
	}
	
	private class MarkLevelMapper implements RowMapper<MarkLevel> {
		@Override
		public MarkLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
			MarkLevel domain = new MarkLevel();
			domain.setMarkLevelId(rs.getLong("mark_level_id"));

			return domain;
		}

	}
	
	private class EvaluateKpiMapper implements RowMapper<EvaluateKpi> {   						
        @Override
		public EvaluateKpi mapRow(ResultSet rs, int rowNum) throws SQLException {
        	EvaluateKpi domain = new EvaluateKpi(); 					
			domain.setKpiId(rs.getLong("kpi_tree_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
			domain.setParentId(rs.getLong("parent_id"));
			domain.setMark(rs.getBigDecimal("mark"));
			domain.setMarkType(rs.getString("mark_type"));
			domain.setUnitId(rs.getLong("unit_id"));
			domain.setWeight(rs.getBigDecimal("weight"));
			domain.setWeightTotal(rs.getBigDecimal("weight_total"));
			domain.setTarget(rs.getBigDecimal("target"));
			
			// Get MarkLevel 
			domain.setMarkLevel(	getMarkLevel(rs.getLong("mark_level_id"),rs.getLong("kpi_tree_id")));
			return domain;
	    }
        
	} 
	
	private class PersonCheckEvaluateMapper implements RowMapper<PersonEvaluateMapping> {   						
		@Override
		public  PersonEvaluateMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
			PersonEvaluateMapping domain = new  PersonEvaluateMapping(); 					
			domain.setEvaluateStatus(rs.getString("evaluate_status"));		
			domain.setEvaluateByPersonId(rs.getString("evaluate_by_person_id"));		
			domain.setEvaluateName(rs.getString("evaluate_name"));		
			
			Semester semester = new Semester();
			semester.setSemesterId(rs.getLong("semeter_id"));
			semester.setYearId(rs.getLong("year_id"));
			domain.setSemester(semester);
			
			Person person = new Person();
			person.setPersonId(rs.getLong("person_id"));
			domain.setPerson(person);
			
			return domain;
		}
	}
	
	private class KPIPersonEvaluateMapper implements RowMapper<KpiPersonEvaluateMapping> {   						
		@Override
		public  KpiPersonEvaluateMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
			KpiPersonEvaluateMapping domain = new  KpiPersonEvaluateMapping(); 		
			domain.setKpiId(rs.getLong("kpi_id"));
			domain.setKpiMarkScore(rs.getBigDecimal("mark"));
			domain.setFlag_cal(rs.getBigDecimal("flag_cal"));
			domain.setCal_value(rs.getString("value"));
			
			return domain;
		}
	}
	
	private class EstimateKPIEvaluateMapper implements RowMapper<KpiPersonEvaluateMapping> {   						
		@Override
		public  KpiPersonEvaluateMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
			KpiPersonEvaluateMapping domain = new  KpiPersonEvaluateMapping(); 		
			domain.setKpiId(rs.getLong("kpi_tree_id"));
			domain.setKpiMarkScore(rs.getBigDecimal("mark"));
			domain.setPersonMapEvaluateId(rs.getString("person_map_evaluate_id"));
			domain.setEvaluateKpiId(rs.getLong("evaluate_kpi_tree_id"));
			domain.setEstimateScore(rs.getBigDecimal("estimate_score"));
			domain.setDefaultScore(rs.getBigDecimal("default_score"));
			
			return domain;
		}
	}

	@Override
	public void createKpiYearMapping(KpiYearMapping kpiYearMapping) {
				
		
	}


	@Override
	public Long createEvaluateKpiTree(EvaluateKpiTree taskTree) {
		Long idReturn = null;	
		EvaluateKpi rootElement = taskTree.getRootElement().getData();
        Long yearId = rootElement.getYearId();
        Long categoryId = rootElement.getCategoryId(); 
        Long evaluateKpiId = rootElement.getKpiId();
        logger.info(" yearId:"+yearId+" categoryId:"+categoryId+" Root evaluateKpiTemplateId:"+evaluateKpiId);
        
        // Create Evaluate Kpi Tree
        recursiveCreateTemplate(taskTree.getRootElement(),rootElement);
        
        logger.info(" createTreeByTemplate yearId:"+yearId+" categoryId:"+categoryId);	        
        
//		String sql =" select *  from evaluate_kpi_tree where year_id = "+yearId +" and group_id="+categoryId+" and parent_id=0";			
//		Kpi kpi = this.jdbcTemplate.queryForObject(sql,	new EvaluateKpiMapper() );				
//		logger.info(" ########### new Root kpi:"+BeanUtils.getBeanString(kpi));			
//		idReturn = kpi.getKpiId();
        
        return idReturn;
	}
	
	public void recursiveCreateTemplate(Node<EvaluateKpi> taskElement ,EvaluateKpi rootElement) {  
		EvaluateKpi parentElement = taskElement.getData(); 
	    logger.info(" parentElement:"+BeanUtils.getBeanString(parentElement));
        Long yearId = rootElement.getYearId();
        Long categoryId = rootElement.getCategoryId(); 
        String personEvaluateId = rootElement.getPersonMapEvaluateId(); 
        parentElement.setYearId(yearId);
        parentElement.setCategoryId(categoryId);
        parentElement.setPersonMapEvaluateId(personEvaluateId);
        // Create Evaluate Tree
	    Long parentId  = createTemplate(parentElement);
	    logger.info(" old  ParentId:"+parentElement.getKpiId()+ " new parent Id:"+parentId);
		   int childSize = taskElement.getChildren().size(); 
	        for (int i=0;i<childSize;i++) {
	        	Node<EvaluateKpi> childKpi = (Node<EvaluateKpi>)taskElement.getChildren().get(i); 
	        	childKpi.getData().setParentId(parentId);
	        	recursiveCreateTemplate(childKpi,rootElement);
	        }
	   

	}
	
	public Long createTemplate(EvaluateKpi kpi) {
		final EvaluateKpi finalKpi = kpi;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement(" insert into evaluate_kpi_tree (name, code,kpi_tree_id,parent_id,year_id,group_id,unit_id,mark,mark_type, person_map_evaluate_id) " +
						" values (?,?,?,?,?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalKpi.getName());
				ps.setString(2,finalKpi.getCode());		
				ps.setLong(3,finalKpi.getKpiId());	
				ps.setLong(4,finalKpi.getParentId());	
				ps.setLong(5,finalKpi.getYearId());	
				ps.setLong(6,finalKpi.getCategoryId());	
				ps.setLong(7,finalKpi.getUnitId());			
				ps.setLong(8,finalKpi.getUnitId()==null?null:100);	
				ps.setString(9,finalKpi.getMarkType());	
				ps.setString(10,finalKpi.getPersonMapEvaluateId());	
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();
	   return returnid;
	}


	
	@Override
	public Semester getPersonEvaluateYearAndSemester(String personId) {
		
		Semester semester = new Semester();
		
		List<PersonEvaluateMapping> personEvaluateMappingList = new ArrayList<PersonEvaluateMapping>();
		
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT * FROM evaluate_person_mapping WHERE person_id ="+StringEscapeUtils.escapeSql(personId)); 
		
		
		personEvaluateMappingList = this.jdbcTemplate.query(sql.toString(), new PersonCheckEvaluateMapper());
		
		if(personEvaluateMappingList.size()>0)
		semester = personEvaluateMappingList.get(0).getSemester();
		
//		semester.setSemesterId(Long.parseLong((String) map.get("person_id")));
//		semester.setYearId(Long.parseLong((String) map.get("year_id")));
		
		logger.info(" sql:"+sql);
		
		return semester;
	}

	@Override
	public List<EstimateUser> getUnderEstimateUserListByUserId(Long userId) {		 		
		logger.info(" #EstimateUserDaoImpl.estimateUserList # ");
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT p.person_id,p.thai_name, p.employee_type, p.thai_surname,  A.user_id, A.estimate_group_id ");
		sql.append("  FROM person p                                                                               ");
		sql.append("  INNER JOIN buckwauser b                                                                     ");
		sql.append("  ON p.email = b.USERNAME                                                                     ");
		sql.append("                                                                                              ");
		sql.append("  INNER JOIN    (   		                                                                  ");
		sql.append("  SELECT eg.estimate_group_id,eg.name,eu.user_id FROM estimate_group eg                       ");
		sql.append("  INNER JOIN estimate_user eu ON eg.estimate_group_id = eu.estimate_group_id                  ");
		sql.append("  ) A                                                                                         ");
		sql.append("  ON A.user_id= b.user_id                                                                     ");
		sql.append("   INNER JOIN    (                                                                            ");
		sql.append("  SELECT ebs.estimate_group_id, ebs.user_id FROM estimate_by_user ebs                         ");
		sql.append(" WHERE ebs.user_id =" + userId + "           ");
		sql.append("  ) EC ON A.estimate_group_id = EC.estimate_group_id                                          ");
		
		logger.info(" sql:"+sql);
		
		List<EstimateUser> resultList = null;
		try {
			resultList = this.jdbcTemplate.query(sql.toString() ,new RowMapper<EstimateUser>() {
				public EstimateUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					EstimateUser domain = new EstimateUser();
					domain.setEstimateGroupId(rs.getLong("estimate_group_id"));
					domain.setUserId(rs.getLong("user_id"));
					Person person = new Person();
					person.setPersonId(rs.getLong("person_id"));
					person.setEmployeeType(rs.getString("employee_type"));
					person.setFullName(BuckWaUtils.getFullName(rs.getString("thai_name"), rs.getString("thai_surname")));
					domain.setPerson(person);
					return domain;
				}
				});			
		} catch (EmptyResultDataAccessException e) {

		}
		return resultList;
	}	
	

}
