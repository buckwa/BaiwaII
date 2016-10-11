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

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.KpiTreeDao;
import com.buckwa.domain.pam.MarkLevel;
import com.buckwa.domain.pam.MarkLevelDetail;
import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
import com.buckwa.domain.pam.nodetree.KpiTree;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.util.BeanUtils;

@Repository("kpiTreeDao")
public class KpiTreeDaoImpl implements KpiTreeDao {
	
	private static Logger logger = LoggerFactory.getLogger(KpiTreeDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public Long create(Kpi kpi) {
		logger.info("   ###  Step 1 Create KpiTree   ");		
		
 
		
		final Kpi finalKpi = kpi;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +		
			/*
						"  insert into kpi_tree (name, code,parent_id,mark) values (?, ?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalKpi.getName());
				ps.setString(2,finalKpi.getCode());		
				ps.setLong(3,finalKpi.getParentId());	
				ps.setLong(4,finalKpi.getMark()==null?new Long(0):finalKpi.getMark().longValue());
				*/
				"  insert into kpi_tree (name, code,parent_id,mark_type,unit_id,mark,weight,weight_total) values (?, ?,?,?,?,?,?,?)" +
			 "", Statement.RETURN_GENERATED_KEYS);   
		ps.setString(1,finalKpi.getName());
		ps.setString(2,finalKpi.getCode());		
		ps.setLong(3,finalKpi.getParentId());	
		ps.setString(4,finalKpi.getMarkType());	
		ps.setLong(5,finalKpi.getUnitId()==null?new Long(0):finalKpi.getUnitId());	
		ps.setLong(6,finalKpi.getMark()==null?new Long(0):finalKpi.getMark().intValue());	
		ps.setBigDecimal(7,new BigDecimal(1.00));
		ps.setBigDecimal(8,new BigDecimal(1.00));
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();
		
		
		
		logger.info("   ###  Step 2 Create Mark Level and set to Kpi   ");	
		
		
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
		
		 
		String sqlupdate="  UPDATE kpi_tree SET mark_level_id ="+markLevelkeyHolder.getKey().longValue()+ " where kpi_tree_id="+kpi.getKpiId();
		logger.info(" ## sql: "+sqlupdate);
		//sql.append( sqlupdate); 
		
		int success = this.jdbcTemplate.update(sqlupdate);
		
		
		
	   return returnid;
	}
	
	

	@Override
	public Long update(Kpi kpi) {
		logger.info("  update kpi_tree: "+BeanUtils.getBeanString(kpi));
		/*
		this.jdbcTemplate.update(
				"update  kpi_tree set name=?,code=? ,mark=?  where kpi_tree_id=? ",
				kpi.getName(), kpi.getCode(),kpi.getMark(),kpi.getKpiId()); 
		*/
		this.jdbcTemplate.update(
				" update  kpi_tree set name=?,code=?,mark_type=?,unit_id=? ,mark=?,weight=?,weight_total=?  where kpi_tree_id=? ",
				kpi.getName(), kpi.getCode(),kpi.getMarkType(),kpi.getUnitId(),kpi.getMark(),kpi.getWeight(),kpi.getWeightTotal(),kpi.getKpiId()); 
		return kpi.getKpiId();
	}
	
 

	@Override
	public void remove(Kpi kpi) {
		logger.info("  kpi: "+BeanUtils.getBeanString(kpi));
		this.jdbcTemplate.update(" delete from  kpi_tree where kpi_tree_id ="+kpi.getKpiId());	
	}
 	
	
	@Override
	public Kpi getById(Long kpiId) {
		String sql =" select *  from kpi_tree where kpi_tree_id = "+kpiId;
		Kpi kpi = this.jdbcTemplate.queryForObject(sql,	new KpiMapper() );	 		
		return kpi;
	}
	
 
	@Override
	public List<Kpi> getByParentId(Long kpiId) {
		List<Kpi> returnList = new ArrayList<Kpi>();
         String sql ="select * from kpi_tree where parent_id="+kpiId;
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Kpi>() {
				public Kpi mapRow(ResultSet rs, int rowNum) throws SQLException {
					Kpi kpi = new Kpi();				 
					kpi.setKpiId(rs.getLong("kpi_tree_id"));
					kpi.setName(rs.getString("name"));
					kpi.setCode(rs.getString("code"));	
					kpi.setParentId(rs.getLong("parent_id"));
					kpi.setMark(rs.getBigDecimal("mark"));
					logger.info(" getByParentId mark: "+rs.getBigDecimal("mark"));
					kpi.setMarkType(rs.getString("mark_type"));
					kpi.setUnitId(rs.getLong("unit_id"));
					kpi.setWeight(rs.getBigDecimal("weight"));
					kpi.setTarget(rs.getBigDecimal("target")==null?new BigDecimal(0.00):rs.getBigDecimal("target"));
					kpi.setWeightTotal(rs.getBigDecimal("weight_total"));
					
					// Get MarkLevel 
					kpi.setMarkLevel(	getMarkLevel(rs.getLong("mark_level_id"),rs.getLong("kpi_tree_id")));
				return kpi;
				}
				}); 
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
	
	

	@Override
	  public void createTree(KpiTree taskTree) {
	        List<Node<Kpi>> tasks =taskTree.toList(); 
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<Kpi> taskElement = tasks.get(i);
	            Kpi task = taskElement.getData();
	            Long parentId  = create(task);	           
	            for (Iterator<Node<Kpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<Kpi> childElement = it.next();
	                Kpi childKpi = childElement.getData();
	                childKpi.setParentId(parentId);
	                create(childKpi);
	            }
	        }
	    }	
 
	
	
	@Override
	  public void editLevel1(KpiTree taskTree) {
	        List<Node<Kpi>> tasks =taskTree.toList(); 
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<Kpi> taskElement = tasks.get(i);
	            Kpi task = taskElement.getData();
	            Long parentId  = update(task);	           
	            for (Iterator<Node<Kpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<Kpi> childElement = it.next();
	                Kpi childKpi = childElement.getData();
	                childKpi.setParentId(parentId);
	                update(childKpi);
	            }
	        }
	    }	
	
	
 
	public void recursiveCreateTemplate(Node<KpiTemplate> taskElement ,KpiTemplate rootElement) {  
	    KpiTemplate parentElement = taskElement.getData(); 
	    logger.info(" parentElement:"+BeanUtils.getBeanString(parentElement));
        Long yearId = rootElement.getYearId();
        Long categoryId = rootElement.getCategoryId(); 
        BigDecimal markBig = rootElement.getMark();
        
        parentElement.setYearId(yearId);
        parentElement.setCategoryId(categoryId);
        //parentElement.setMark(markBig);
        
	    Long parentId  = createTemplate(parentElement);	        
	    logger.info(" old  ParentId:"+parentElement.getKpiTemplateId()+ " new parent Id:"+parentId);
		   int childSize = taskElement.getChildren().size(); 
	        for (int i=0;i<childSize;i++) {
	        	Node<KpiTemplate> childKpi = (Node<KpiTemplate>)taskElement.getChildren().get(i); 
	        	childKpi.getData().setParentId(parentId);
	        	recursiveCreateTemplate(childKpi,rootElement);
	        }
	   

	}
	
	
	@Override
	  public Long createTreeByTemplate(KpiTemplateTree taskTree) {
		    Long idReturn = null;	
		    KpiTemplate rootElement = taskTree.getRootElement().getData();
	        Long yearId = rootElement.getYearId();
	        Long categoryId = rootElement.getCategoryId(); 
	        Long kpiTemplateId = rootElement.getKpiTemplateId();
	        
	        /*
	        
	        List<Node<KpiTemplate>> tasks =taskTree.toList(); 
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<KpiTemplate> taskElement = tasks.get(i);
	            KpiTemplate task = taskElement.getData();
	            task.setYearId(yearId);
	            task.setCategoryId(categoryId);	            
	            Long parentId  = createTemplate(task);	      
	           
	            for (Iterator<Node<KpiTemplate>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<KpiTemplate> childElement = it.next();
	                KpiTemplate childKpi = childElement.getData();
	                childKpi.setYearId(yearId);
	                childKpi.setCategoryId(categoryId);
	                childKpi.setParentId(parentId);
	                createTemplate(childKpi);
	            }
	          
	            
	        }
	          */
	        
	        recursiveCreateTemplate(taskTree.getRootElement(),rootElement);
	        
	        logger.info(" createTreeByTemplate yearId:"+yearId+" categoryId:"+categoryId);	        
			String sql =" select *  from kpi_tree where year_id = "+yearId +" and group_id="+categoryId+" and parent_id=0";			
			Kpi kpi = this.jdbcTemplate.queryForObject(sql,	new KpiMapper() );				
			logger.info(" ########### new Root kpi:"+BeanUtils.getBeanString(kpi));			
			idReturn = kpi.getKpiId();
	        
	        return idReturn;
	    }	
 
	
	public Long createTemplate(KpiTemplate kpi) {
		
		
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
		
		
		
		
		final KpiTemplate finalKpi = kpi;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into kpi_tree (name, code,parent_id,year_id,group_id,unit_id,mark,mark_type,weight,weight_total,mark_level_id) values (?,?,?,?,?,?,?,?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalKpi.getName());
				ps.setString(2,finalKpi.getCode());		
				ps.setLong(3,finalKpi.getParentId());	
				ps.setLong(4,finalKpi.getYearId());	
				ps.setLong(5,finalKpi.getCategoryId());	
				ps.setLong(6,finalKpi.getUnitId());			
				ps.setLong(7,finalKpi.getMark()==null?new Long(0):finalKpi.getMark().longValue());	
				ps.setString(8,finalKpi.getMarkType());	
				ps.setBigDecimal(9,new BigDecimal(0));
				ps.setBigDecimal(10,new BigDecimal(0));
				ps.setLong(11,markLevelkeyHolder.getKey().longValue());	
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();
	   return returnid;
	}
	
	
 
	
	@Override
	  public void updateTree(KpiTree taskTree) {
	        List<Node<Kpi>> tasks =taskTree.toList(); 
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<Kpi> taskElement = tasks.get(i);
	            Kpi task = taskElement.getData();
	            Long parentId  = update(task);	           
	            for (Iterator<Node<Kpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<Kpi> childElement = it.next();
	                Kpi childKpi = childElement.getData();
	                childKpi.setParentId(parentId);
	                update(childKpi);
	            }
	        }
	    }	
	
	
	@Override
	  public KpiTree getTreeById(Long id) {
	        KpiTree kpiTree = new KpiTree();
	        Node rootElement = new Node<Kpi>(getById(id));
	        getRecursive(rootElement, kpiTree);
	        kpiTree.setRootElement(rootElement);
	        return kpiTree;
	    }
	

	@Override
	  public KpiTree getTemplateByYearAndGroupId(String yearId,String groupId) {
		
	 
			String sqltmp = "select t.kpi_id from kpi_year_mapping  t  where t.year_id="+ yearId + " and t.category_id=" + groupId;

			logger.info(" ##   sql\n:" + sqltmp);
			try {
				String kpiIdStr = this.jdbcTemplate	.queryForObject(sqltmp, String.class); 
		        KpiTree kpiTree = new KpiTree();
		        Node rootElement = new Node<Kpi>(getById(new Long(kpiIdStr)));
		        getRecursive(rootElement, kpiTree);
		        kpiTree.setRootElement(rootElement);
		         
		        return kpiTree;
			} catch (Exception  e) {
				e.printStackTrace();
			}
			return null;
	    }
	
	

	@Override
	public void getRecursive(Node<Kpi> taskElement, KpiTree tree) {
		   List<Kpi> children = getByParentId(taskElement.getData().getKpiId());
	        List<Node<Kpi>> childElements = new ArrayList<Node<Kpi>>();
	        for (Iterator<Kpi> it = children.iterator(); it.hasNext();) {
	        	Kpi childKpi = it.next();
	        	logger.info(" ## kpiId:"+childKpi.getKpiId());
	            Node<Kpi> childElement = new Node<Kpi>(childKpi);
	            childElements.add(childElement);
	            getRecursive(childElement, tree);
	        }
	        taskElement.setChildren(childElements);

	}
	
	 @Override
	public KpiTree getAllTree(){
		  KpiTree root = getTreeById (new Long(14));
		  
		  return root;
		  
	}
	
	 
	@Override
	public KpiTree getNodeTreeByYearandEmpType(String yearid, String empTypeId) {

		KpiTree kpiTree = new KpiTree();
		String sqltmp = "select t.kpi_year_mapping_id  from kpi_year_mapping  t  where t.year_id="
				+ yearid + " and t.category_id=" + empTypeId;

		logger.info(" ## getNodeTreeByYearandEmpType sql\n:" + sqltmp);
		String kpiIdStr = this.jdbcTemplate
				.queryForObject(sqltmp, String.class);

		if (kpiIdStr != null) {
			kpiTree = getTreeById(new Long(kpiIdStr));

		}

		return kpiTree;

	}
	
	@Override
	public KpiTree getNodeTreeByYear(String yearid) {

		KpiTree kpiTree = new KpiTree();
		String sqltmp = "select t.kpi_year_mapping_id  from kpi_year_mapping  t  where t.year_id="+ yearid;

		logger.info(" ## getNodeTreeByYearandEmpType sql\n:" + sqltmp);
		String kpiIdStr = this.jdbcTemplate.queryForObject(sqltmp, String.class);

		if (kpiIdStr != null) {
			kpiTree = getTreeById(new Long(kpiIdStr));

		}

		return kpiTree;

	}
	
	
	private class KpiMapper implements RowMapper<Kpi> {   						
        @Override
		public Kpi mapRow(ResultSet rs, int rowNum) throws SQLException {
			Kpi domain = new Kpi(); 					
			domain.setKpiId(rs.getLong("kpi_tree_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
			domain.setParentId(rs.getLong("parent_id"));
			domain.setMark(rs.getBigDecimal("mark"));
			logger.info("kpiId:"+rs.getLong("kpi_tree_id")+" mark:"+rs.getBigDecimal("mark"));
			domain.setMarkType(rs.getString("mark_type"));
			domain.setUnitId(rs.getLong("unit_id"));
			domain.setWeight(rs.getBigDecimal("weight"));
			domain.setWeightTotal(rs.getBigDecimal("weight_total"));
		return domain;
    }
        
	} 
	
	@Override
	public void updateWeight(final String kpi,  final String weight) {
		
		StringBuffer sql = new StringBuffer();
		String sqlupdate=" UPDATE kpi_tree SET weight = "+weight+" WHERE kpi_tree_id = "+kpi;
		logger.info(" ## sql: "+sqlupdate);
		//sql.append( sqlupdate); 
		
		int success = this.jdbcTemplate.update(sqlupdate);
		
		
	}
	
	
	@Override
	public void updateTarget(final String kpi,  final String weight) {
		
		StringBuffer sql = new StringBuffer();
		String sqlupdate=" UPDATE kpi_tree SET target = "+weight+" WHERE kpi_tree_id = "+kpi;
		logger.info(" ## sql: "+sqlupdate);
		//sql.append( sqlupdate); 
		
		int success = this.jdbcTemplate.update(sqlupdate);
		
		
	}
	
	
	@Override
	public void updateLevel(String levelId, String mark,String level) {
		
		StringBuffer sql = new StringBuffer();
		String sqlupdate=" UPDATE mark_level_detail  SET mark = "+mark+" WHERE mark_level_detail_id = "+levelId;
		logger.info(" ## updateLevel  sql: "+sqlupdate);
		//sql.append( sqlupdate); 
		
		int success = this.jdbcTemplate.update(sqlupdate);
		
		
	}
	
	

}
