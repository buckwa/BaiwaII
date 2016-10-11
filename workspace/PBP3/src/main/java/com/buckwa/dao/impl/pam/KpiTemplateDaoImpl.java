package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.KpiCategoryDao;
import com.buckwa.dao.intf.pam.KpiTemplateDao;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.util.BeanUtils;

@Repository("kpiTemplateDao")
public class KpiTemplateDaoImpl implements KpiTemplateDao {
	
	private static Logger logger = LoggerFactory.getLogger(KpiTemplateDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KpiCategoryDao kpiCategoryDao;
	
	@Override
	public Long create(KpiTemplate kpiTemplate) {
		logger.info("   ");		
		final KpiTemplate finalKpi = kpiTemplate;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into kpi_tree_template (name, code,parent_id,mark_type,unit_id,mark) values (?, ?,?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalKpi.getName());
				ps.setString(2,finalKpi.getCode());		
				ps.setLong(3,finalKpi.getParentId());	
				ps.setString(4,finalKpi.getMarkType());	
				ps.setLong(5,finalKpi.getUnitId()==null?new Long(0):finalKpi.getUnitId());	
				ps.setLong(6,finalKpi.getMark()==null?new Long(0):finalKpi.getMark().intValue());	
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();
	   return returnid;
	}
	
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Long update(KpiTemplate kpi) {
		logger.info("  kpi: "+BeanUtils.getBeanString(kpi));
		 
		this.jdbcTemplate.update(
				"update  kpi_tree_template set name=?,code=?,mark_type=?,unit_id=? ,mark=?  where kpi_tree_template_id=? ",
				kpi.getName(), kpi.getCode(),kpi.getMarkType(),kpi.getUnitId(),kpi.getMark(),kpi.getKpiTemplateId()); 
		return kpi.getKpiTemplateId();
	}
	

	@Override
	public void remove(KpiTemplate kpi) {
		logger.info("  kpi: "+BeanUtils.getBeanString(kpi));
		this.jdbcTemplate.update(" delete from  kpi_tree_template where kpi_tree_template_id ="+kpi.getKpiTemplateId());	
	}
 	
	
	@Override
	public KpiTemplate getById(Long kpiId) {
		
		logger.info("  Create From kpi template Id:"+kpiId) ;
		String sql =" select *  from kpi_tree_template where kpi_tree_template_id = "+kpiId;
		logger.info(" getById  sel:"+sql) ;
		KpiTemplate kpi = this.jdbcTemplate.queryForObject(sql,	new KpiTemplateMapper() );	 		
		return kpi;
	}
	
 
	@Override
	public List<KpiTemplate> getByParentId(Long kpiId) {
		List<KpiTemplate> returnList = new ArrayList<KpiTemplate>();
         String sql ="select * from kpi_tree_template where parent_id="+kpiId;
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<KpiTemplate>() {
				public KpiTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
					KpiTemplate kpi = new KpiTemplate();				 
					kpi.setKpiTemplateId(rs.getLong("kpi_tree_template_id"));					 
					kpi.setName(rs.getString("name"));
					kpi.setCode(rs.getString("code"));	
					kpi.setParentId(rs.getLong("parent_id"));					 
					kpi.setMarkType(rs.getString("mark_type"));
					kpi.setUnitId(rs.getLong("unit_id"));	
					kpi.setCategoryId(rs.getLong("group_id"));
					kpi.setMark(rs.getBigDecimal("mark"));
				return kpi;
				}
				}); 
		
		//logger.info(" Fond child size:"+returnList==null?"0":returnList.size());
		return returnList;
	}
	

	@Override
	  public void createTree(KpiTemplateTree taskTree) {
	        List<Node<KpiTemplate>> tasks =taskTree.toList(); 
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<KpiTemplate> taskElement = tasks.get(i);
	            KpiTemplate task = taskElement.getData();
	            Long parentId  = create(task);	           
	            for (Iterator<Node<KpiTemplate>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<KpiTemplate> childElement = it.next();
	                KpiTemplate childKpi = childElement.getData();
	                childKpi.setParentId(parentId);
	                create(childKpi);
	            }
	        }
	    }	
	
 
	
	@Override
	  public void updateTree(KpiTemplateTree taskTree) {
	        List<Node<KpiTemplate>> tasks =taskTree.toList(); 
	        int numberOfNodes = tasks.size();
	        for (int i = numberOfNodes - 1; i >= 0; i--) {
	            Node<KpiTemplate> taskElement = tasks.get(i);
	            KpiTemplate task = taskElement.getData();
	            Long parentId  = update(task);	           
	            for (Iterator<Node<KpiTemplate>> it = taskElement.getChildren().iterator(); it.hasNext();) {
	                Node<KpiTemplate> childElement = it.next();
	                KpiTemplate childKpi = childElement.getData();
	                childKpi.setParentId(parentId);
	                update(childKpi);
	            }
	        }
	    }	
	
	
	
	
	@Override
	  public KpiTemplateTree getTreeById(Long id) {		
			logger.info(" ### Start getTreeById  id:"+id);
			KpiTemplateTree kpiTemplateTree = new KpiTemplateTree();
	        Node rootElement = new Node<KpiTemplate>(getById(id));
	        getRecursive(rootElement, kpiTemplateTree);
	        kpiTemplateTree.setRootElement(rootElement);
	        
	        return kpiTemplateTree;
	    }
	

	@Override
	public void getRecursive(Node<KpiTemplate> taskElement, KpiTemplateTree tree) {
	   // logger.info("  ParentId:"+taskElement.getData().getKpiTemplateId());
		   List<KpiTemplate> children = getByParentId(taskElement.getData().getKpiTemplateId());
		//   logger.info(" Found children size:"+children==null?"":children.size());
	        List<Node<KpiTemplate>> childElements = new ArrayList<Node<KpiTemplate>>();
	        for (Iterator<KpiTemplate> it = children.iterator(); it.hasNext();) {
	        	KpiTemplate childKpi = it.next(); 
	            Node<KpiTemplate> childElement = new Node<KpiTemplate>(childKpi);
	            childElements.add(childElement);
	            getRecursive(childElement, tree);
	        }
	        taskElement.setChildren(childElements);

	}
	
	 @Override
	public KpiTemplateTree getAllTree(){
		 KpiTemplateTree root = getTreeById (new Long(14));		  
		  return root; 
	}
	 
	 
	 @Override
	public KpiTemplateTree getTreeByRootId(String rootId){
		 KpiTemplateTree root = getTreeById (new Long(rootId));		  
		  return root; 
	}
 
	 
	 @Override
	public KpiTemplateTree getTreeByCategoryId(final String catetoryId){
		 
		 logger.info(" catetoryId :"+catetoryId);
		 String kpiIdStr =  null;
		 KeyHolder keyHolder = new GeneratedKeyHolder(); 
		    try{
				String sql =" select kpi_tree_template_id  from kpi_tree_template where group_id = "+catetoryId;
				logger.info(" ## getTreeByCategoryId sql\n:"+sql);
			    kpiIdStr = this.jdbcTemplate.queryForObject(sql,	String.class );	 		    	
		    }catch(Exception ex){
		    	logger.info("  No Found Tree , Create New ");
		     
		    }
		    logger.info(" ## getTreeByCategoryId Found kpiIdStr:"+kpiIdStr);
		    if(kpiIdStr==null){		    	//
		    	final KpiCategory catTmp = kpiCategoryDao.getById(catetoryId);	    			    	
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into kpi_tree_template (name, group_id) values (?, ?)" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,catTmp.getName());
					 
						ps.setLong(2,new Long(catetoryId));	
						return ps;  
						}
					}, 	keyHolder); 
				 kpiIdStr =  keyHolder.getKey().longValue()+"";
		    }
		   
		    logger.info("  Found kpiId for load Tree:"+kpiIdStr);
		    KpiTemplateTree root = getTreeById (new Long(kpiIdStr));		  
		  return root; 
	}
 
	 
		
		@Override
		public void initialMappingByYear(List<KpiTemplateTree> kpitreeList) {
			
			 
			for(KpiTemplateTree kpitreeTmp:kpitreeList){
				
				createTree(kpitreeTmp);
				
			}
			
			
	 
		}
	 
	 
	
	private class KpiTemplateMapper implements RowMapper<KpiTemplate> {   						
        @Override
		public KpiTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        	KpiTemplate domain = new KpiTemplate(); 					
			domain.setKpiTemplateId(rs.getLong("kpi_tree_template_id"));		 
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
			domain.setParentId(rs.getLong("parent_id"));
			domain.setMarkType(rs.getString("mark_type"));
			domain.setUnitId(rs.getLong("unit_id"));	
			domain.setCategoryId(rs.getLong("group_id"));
			domain.setMark(rs.getBigDecimal("mark"));
		return domain;
    }
        
	} 

}
