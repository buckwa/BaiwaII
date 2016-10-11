package com.buckwa.dao.impl.project;

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
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.project.ModuleDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.UseCase;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectUtil;

@Repository("moduleDao")
public class ModuleDaoImpl implements ModuleDao {
	private static Logger logger = LoggerFactory.getLogger(ModuleDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;

	public List<Module> getAllModule() {
		List<Module> returnList = new ArrayList<Module>(); 
		String sql ="  select * from project_module r  " ;	
		returnList = this.jdbcTemplate.query(	sql, new ModuleMapper());

	return returnList;
	}
	@Override
	public List<Module> getAllModuleByProjectId(String projectId) {
		List<Module> returnList = new ArrayList<Module>(); 
		String sql ="  select * from project_module r where project_id="+projectId ;	
		returnList = this.jdbcTemplate.query(	sql, new ModuleMapper());

	return returnList;
	}
	@Override
	public List<Module> getAllModuleByProjectIdCountUseCase(String projectId) {
		List<Module> returnList = new ArrayList<Module>(); 
		StringBuffer sb = new StringBuffer();
		sb.append(" select parent.*, count(child.usecase_id) no_of_child  \n");
		sb.append("  from   project_module parent\n");
		sb.append(" left join project_usecase child   on child.module_id = parent.module_id \n");
		sb.append("  where parent.project_id="+projectId +" \n");
		sb.append("  group by parent.module_id \n");
		sb.append("  order by parent.module_id  \n");
 
		returnList = this.jdbcTemplate.query(	sb.toString(), new ModuleCountMapper());
		
		

	return returnList;
	}	
	
	@Override
	public List<Module> getAllModuleByProjectIdCountDetailDesign(String projectId) {
		logger.info(" #DetailDesignDaoImpl.getAllDetailDesign # ");
		List<Module> returnList = new ArrayList<Module>(); 
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" select parent.*, count(child.detail_design_id) no_of_child  \n");
			sb.append("  from   project_module parent\n");
			sb.append(" left join project_detail_design child   on child.module_id = parent.module_id \n");
			sb.append("  where parent.project_id="+projectId +" \n");
			sb.append("  group by parent.module_id \n");
			sb.append("  order by parent.module_id  \n");			
			 	
			returnList = this.jdbcTemplate.query(	sb.toString(),new ModuleCountMapper());
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
 
	@Override
	public List<Module> getAllModuleByProjectIdCountTestcase(String projectId) {
		logger.info(" #DetailDesignDaoImpl.getAllModuleByProjectIdCountTescase# ");
		List<Module> returnList = new ArrayList<Module>(); 
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" select parent.*, count(child.testcase_id) no_of_child  \n");
			sb.append("  from   project_module parent\n");
			sb.append(" left join project_testcase child   on child.module_id = parent.module_id \n");
			sb.append("  where parent.project_id="+projectId +" \n");
			sb.append("  group by parent.module_id \n");
			sb.append("  order by parent.module_id  \n");			
			 	
			logger.info(" ## getAllModuleByProjectIdCountTestcase sql:"+sb.toString());
			returnList = this.jdbcTemplate.query(	sb.toString(),new ModuleCountMapper());
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	
	@Override
	public void update(Module module) {
		logger.info(" #ModuleDaoImpl.update module: "+BeanUtils.getBeanString(module));
		this.jdbcTemplate.update(
				"update  project_module set name=?   where module_id=? ",
				module.getName()  ,module.getModuleId());
 
	}
 
	@Override
	public void create(Module module) {
		logger.info(" #ModuleDaoImpl.create # ");
		
		final String businessCode = projectUtil.getModuleNo(module.getProjectId());
		
		final Module finalModule = module;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_module (code, name,project_id) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,businessCode);
				ps.setString(2,finalModule.getName());
				ps.setLong(3,finalModule.getProjectId());		 			
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		Module module = (Module)pagingBean.get("module");		
		List<Module> returnList = new ArrayList<Module>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_module  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(module.getCode())){
			sqltotalsb.append(" and r.code like  '%"+module.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(module.getName())){
			sqltotalsb.append(" and r.name like  '%"+module.getName().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_module r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(module.getCode())){
			sb.append(" and r.code like  '%"+module.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(module.getName())){
			sb.append(" and r.name like  '%"+module.getName().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(sql,new ModuleMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public Module getModuleById(String moduleId,String projectId) {	
		logger.info(" ### getModuleById moduleId:"+moduleId);
		
		Module module  = null;
		
		if (!StringUtils.hasLength(moduleId)) {
			String sql= "select *  from project_module where project_id = ?";
			List<Module> moduleList  = this.jdbcTemplate.query(	sql,	new Object[]{projectId},new ModuleMapper());
			if(moduleList!=null&&moduleList.size()>0){
				module = moduleList.get(0);
			}
		}else{
			String sql= "select *  from project_module where module_id = ?";
			module = this.jdbcTemplate.queryForObject(	sql,	new Object[]{moduleId},new ModuleMapper());
		} 
		if(module!=null){			
			String sql ="  select * from project_usecase r  where r.module_id="+module.getModuleId() ;	
			List<UseCase> useCaseReturnList = this.jdbcTemplate.query(	sql,new UseCaseMapper());
			module.setUseCaseList(useCaseReturnList);
			
			for(UseCase ucTmp:useCaseReturnList){
				String usecaseId = ucTmp.getUsecaseId()+"";
				
				// Get Detail Design belong to usecaseId 
				
				String sqlDetailDesing ="  select * from project_detail_design r  where r.module_id="+module.getModuleId() ;	
				
				
				
				
			}
		}
		 
		return module;
	}
	
	@Override
	public Module getModuleandDetailDesignById(String moduleId,String projectId) {			
		logger.info(" ### getModuleandDetailDesignById moduleId:"+moduleId);
		Module module  = null;		
		if (!StringUtils.hasLength(moduleId)) {
			String sql= "select *  from project_module where project_id = ?";
			List<Module> moduleList  = this.jdbcTemplate.query(	sql,	new Object[]{projectId},new ModuleMapper());
			if(moduleList!=null&&moduleList.size()>0){
				module = moduleList.get(0);
			}
		}else{
			String sql= "select *  from project_module where module_id = ?";
			module = this.jdbcTemplate.queryForObject(	sql,	new Object[]{moduleId},new ModuleMapper());
		} 
		
		if(module!=null){
			
			String sql ="  select * from project_detail_design r  where r.module_id="+module.getModuleId() ;	
			List<DetailDesign> returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			module.setDetailDesignList(returnList);
		}
		
		return module;
	}	

	@Override
	public Module getModuleandTestCaseById(String moduleId,String projectId) {			
		logger.info(" ### getModuleandDetailDesignById moduleId:"+moduleId);
		Module module  = null;		
		if (!StringUtils.hasLength(moduleId)) {
			String sql= "select *  from project_module where project_id = ?";
			List<Module> moduleList  = this.jdbcTemplate.query(	sql,	new Object[]{projectId},new ModuleMapper());
			if(moduleList!=null&&moduleList.size()>0){
				module = moduleList.get(0);
			}
		}else{
			String sql= "select *  from project_module where module_id = ?";
			module = this.jdbcTemplate.queryForObject(	sql,	new Object[]{moduleId},new ModuleMapper());
		} 
		
		if(module!=null){
			
			String sql ="  select * from project_detail_design r  where r.module_id="+module.getModuleId() ;	
			List<DetailDesign> returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			module.setDetailDesignList(returnList);
		}
		
		return module;
	}	

	
	@Override
	public void deleteModuleById(String moduleId) {	 
		this.jdbcTemplate.update(" delete from  project_module where module_id ="+moduleId);	
		this.jdbcTemplate.update(" delete from  project_usecase where module_id ="+moduleId); 
		 	
	}
	
 
	@Override
	public boolean isModuleAlreadyUsege(String moduleId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from project_module module inner join buckwagroupmodule groupmodule " +
					"on (module.module_id = groupmodule.module_id)	where module.module_id ="+moduleId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	private class ModuleMapper implements RowMapper<Module> {   						
        @Override
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Module domain = new Module();
        	domain.setModuleId(rs.getLong("module_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setDetail(rs.getString("detail"));        	 
        	domain.setProjectId(rs.getLong("project_id"));        	
		return domain;
		} 
    } 
	
	private class ModuleCountMapper implements RowMapper<Module> {   						
        @Override
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Module domain = new Module();
        	domain.setModuleId(rs.getLong("module_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setDetail(rs.getString("detail"));        	 
        	domain.setProjectId(rs.getLong("project_id"));       
        	domain.setChildCount(rs.getLong("no_of_child"));  
		return domain;
		} 
    } 
	
	private class UseCaseMapper implements RowMapper<UseCase> {   						
        @Override
		public UseCase mapRow(ResultSet rs, int rowNum) throws SQLException {
        	UseCase domain = new UseCase();
        	domain.setUsecaseId(rs.getLong("usecase_id"));
        	logger.info(" ### rs usecase_id:"+rs.getLong("usecase_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setSummary(rs.getString("summary"));
        	domain.setActor(rs.getString("actor"));
        	domain.setBasicFlow(rs.getString("basic_flow"));
        	domain.setAlternateFlow(rs.getString("alternate_flow"));
        	//domain.setAlternateFlow("test \n test1 <br> test3 ");
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setModuleId(rs.getLong("module_id")); 
		return domain;
		} 
    } 
	
	
	private class DetailDesignMapper implements RowMapper<DetailDesign> {   						
        @Override
		public DetailDesign mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DetailDesign domain = new DetailDesign();
        	domain.setDetailDesignId(rs.getLong("detail_design_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setSummary(rs.getString("summary")); 
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setModuleId(rs.getLong("module_id")); 
        	domain.setStep(rs.getString("step")); 
        	domain.setDataPart(rs.getString("data_part")); 
		return domain;
		} 
    } 
	
}
