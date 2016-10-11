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

import com.buckwa.dao.intf.project.DetailDesignDao;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.project.TestCase;
import com.buckwa.domain.project.UseCase;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectConstant;
import com.buckwa.util.project.ProjectUtil;

@Repository("detailDesignDao")
public class DetailDesignDaoImpl implements DetailDesignDao {
	private static Logger logger = LoggerFactory.getLogger(DetailDesignDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;

	public List<DetailDesign> getAllByProjectId(String projectId) {
		logger.info(" #DetailDesignDaoImpl.getAllDetailDesign # ");
		List<DetailDesign> returnList = new ArrayList<DetailDesign>(); 
		try{
			String sql ="  select * from project_detail_design r where r.project_id="+projectId ;	
			returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			getTestCaseList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	public List<DetailDesign> getAllDetailDesignProjectId(String projectId) {
		logger.info(" #DetailDesignDaoImpl.getAllDetailDesign # ");
		List<DetailDesign> returnList = new ArrayList<DetailDesign>(); 
		try{
			String sql ="  select * from project_detail_design r  " ;	
			returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			getTestCaseList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	private void getTestCaseList (List<DetailDesign> returnListIn){		
		
			for(DetailDesign domainTmp:returnListIn){	
				
				try{ 
					
				// Get Use Case
			 String sqlusecase ="  select *  from project_usecase u inner join project_detail_design d on(u.usecase_id=d.usecase_id)	 where d.detail_design_id="+domainTmp.getDetailDesignId();		
			 List<UseCase> useCaseList = this.jdbcTemplate.query(	sqlusecase,new ShortUseCaseMapper());	
			  if(useCaseList!=null&&useCaseList.size()>0){
				  domainTmp.setUsecase(useCaseList.get(0));
			  }
					
				// Get Test Case List
				String sql ="  select * from project_testcase r  where r.detail_design_id="+domainTmp.getDetailDesignId() ;	
				logger.info(" ## sql get getTestCaseList: "+sql);
				List<TestCase> testCaseList = this.jdbcTemplate.query(	sql,new TestCaseMapper());
				domainTmp.setTestCaseList(testCaseList);
				
				
				
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}	 
	}
	
	
	
	public List<DetailDesign> getAllDetailDesignByModuleId(String moduleId) {
		logger.info(" #DetailDesignDaoImpl.getAllDetailDesignByModuleIde moduleId: "+moduleId);
		List<DetailDesign> returnList = new ArrayList<DetailDesign>(); 
		try{
			String sql ="  select * from project_detail_design r  where r.module_id="+moduleId ;	
			returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			getTestCaseList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	 
	@Override
	public void create(DetailDesign detailDesign) {
		logger.info(" #DetailDesignDaoImpl.create # ");		
		final String code = projectUtil.getDetailDesignNo(detailDesign.getProjectId());		
		final DetailDesign finalDetailDesign = detailDesign;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_detail_design (code, name,summary, module_id,project_id,step,data_part,usecase_id) values (?,?, ?,?,?,?,?,? )" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,code);
				ps.setString(2,finalDetailDesign.getName());
				ps.setString(3,finalDetailDesign.getSummary()); 
				ps.setLong(4,finalDetailDesign.getModuleId());
				ps.setLong(5,finalDetailDesign.getProjectId());
				ps.setString(6, finalDetailDesign.getStep())	;
				ps.setString(7, finalDetailDesign.getDataPart())	;
				ps.setLong(8, finalDetailDesign.getUsecaseId())	;
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}
	
 
	@Override
	public void update(DetailDesign detailDesign) {
		logger.info(" #DetailDesignDaoImpl.update detailDesign: "+BeanUtils.getBeanString(detailDesign));
		this.jdbcTemplate.update(
				"update  project_detail_design set name=? ,summary=? ,step=?,data_part=? where detail_design_id=? ",
				detailDesign.getName(),detailDesign.getSummary(),detailDesign.getStep(),detailDesign.getDataPart()  ,detailDesign.getDetailDesignId());
 
	}
 


	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		DetailDesign detailDesign = (DetailDesign)pagingBean.get("detailDesign");		
		List<DetailDesign> returnList = new ArrayList<DetailDesign>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_detail_design  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(detailDesign.getCode())){
			sqltotalsb.append(" and r.code like  '%"+detailDesign.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(detailDesign.getName())){
			sqltotalsb.append(" and r.name like  '%"+detailDesign.getName().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_detail_design r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(detailDesign.getCode())){
			sb.append(" and r.code like  '%"+detailDesign.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(detailDesign.getName())){
			sb.append(" and r.name like  '%"+detailDesign.getName().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public DetailDesign getDetailDesignById(String detailDesignId) {		 
		DetailDesign detailDesign = this.jdbcTemplate.queryForObject(
				"select *  from project_detail_design where detail_design_id = ?",
				new Object[]{detailDesignId},new DetailDesignMapper());		
		List<DetailDesign>  returnList = new ArrayList();
		returnList.add(detailDesign);
		getTestCaseList(returnList);
		return detailDesign;
	}	

	@Override
	public DetailDesign prepareDetailDesignByUseCase(String moduleId,String usecaseId) {		 
		DetailDesign detailDesign = new DetailDesign();
		
		String sqlModule= " select *  from project_module m	 inner join project_usecase u on(m.module_id=u.module_id)	 where u.usecase_id=?";

		Module module = this.jdbcTemplate.queryForObject(sqlModule,	new Object[]{usecaseId},new ModuleMapper());	 
		
		UseCase usecase = this.jdbcTemplate.queryForObject(
				"select *  from project_usecase where usecase_id = ?",
				new Object[]{usecaseId},new UseCaseMapper());	
				
		detailDesign.setModuleId(module.getModuleId());
		detailDesign.setModuleName(module.getName());
		detailDesign.setUsecaseId(usecase.getUsecaseId());
		detailDesign.setUsecaseName(usecase.getName());
		detailDesign.setName(usecase.getName());
		return detailDesign;
	}	
	
	
	public void updateFilePath(DetailDesign detailDesign) { 
		logger.info(" #### In updateFilePath detailDesign:"+BeanUtils.getBeanString(detailDesign));		
		// Step 1 Remove 	
		String sqlDelImage =" delete from image_path where image_code='"+detailDesign.getDetailDesignId()+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN+"'";
		
		logger.info("  sqlDelImage:"+sqlDelImage);
		this.jdbcTemplate.update(sqlDelImage );			
		List<ImagePath> imagePathList = detailDesign.getFilePathList();		
		for(ImagePath imagePathTmp :imagePathList){			
		 this.jdbcTemplate.update(
					"insert into image_path (image_code, image_path,image_type ,file_name) values (?, ?,? ,?)",
					detailDesign.getDetailDesignId(), imagePathTmp.getImagePath(),ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN,imagePathTmp.getFileName());	
			
		}  	
	}	
	
	
	
	@Override
	public void deleteDetailDesignById(String detailDesignId) {	
		String delsql =" delete from  project_detail_design where detail_design_id ="+detailDesignId;
		logger.info("  ###### delete sql:"+delsql);
		this.jdbcTemplate.update(delsql);	
		 	
	}
	
	@Override
	public Project getProjectByProjectId(String projectId) {		 
		logger.info(" ### getProjectByProjectId projectId:"+projectId);
		Project project = this.jdbcTemplate.queryForObject(
				"select *  from project where project_id = ?",
				new Object[]{projectId},new ProjectMapper());	 
		if(project!=null){			
			String sql ="  select * from project_detail_design r  where r.project_id="+project.getProjectId() ;	
		    logger.info("  ### getProjectByProjectId  sql:"+sql);
			List<DetailDesign> returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			project.setDetailDesignList(returnList);
		}
		
		
		return project;
	}	
	
	
 
	@Override
	public boolean isDetailDesignAlreadyUsege(String detailDesignId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwadetailDesign detailDesign inner join buckwagroupdetailDesign groupdetailDesign " +
					"on (detailDesign.detailDesign_id = groupdetailDesign.detailDesign_id)	where detailDesign.detailDesign_id ="+detailDesignId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
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
         	domain.setCreateDate(rs.getTimestamp("create_date"));
         	domain.setCreateBy(rs.getString("create_by"));
         	domain.setUpdateDate(rs.getTimestamp("update_date"));
         	domain.setUpdateBy(rs.getString("update_by"));
		return domain;
		} 
    } 
	
	
	private class ProjectMapper implements RowMapper<Project> {   						
        @Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Project domain = new Project();
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setProjectName(rs.getString("project_name"));        	 
		return domain;
		} 
    } 
	
	
	@Override
	public void uploadScreen(DetailDesign detailDesign) { 
		logger.info(" #### In uploadScreen detailDesign:"+BeanUtils.getBeanString(detailDesign));
		
		// Step 1 Remove 	
		String sqlDelImage =" delete from image_path where image_code='"+detailDesign.getProjectId()+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN+"'";
		
		logger.info("  sqlDelImage:"+sqlDelImage);
 		this.jdbcTemplate.update(sqlDelImage );			
		List<ImagePath> imagePathList = detailDesign.getScreenImagePathList();	
		for(ImagePath imagePathTmp :imagePathList){			
		 this.jdbcTemplate.update(
					"insert into image_path (image_code, image_path,image_type ) values (?, ?,? )",
					detailDesign.getProjectId(), imagePathTmp.getImagePath(),ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN);	
			
		}  	
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
         	domain.setCreateDate(rs.getTimestamp("create_date"));
         	domain.setCreateBy(rs.getString("create_by"));
         	domain.setUpdateDate(rs.getTimestamp("update_date"));
         	domain.setUpdateBy(rs.getString("update_by"));
		return domain;
		} 
    } 
	
	
	private class TestCaseMapper implements RowMapper<TestCase> {   						
        @Override
		public TestCase mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TestCase domain = new TestCase();
        	domain.setTestcaseId(rs.getLong("testcase_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name")); 
    
		return domain;
		} 
    } 
	
	
	private class ShortUseCaseMapper implements RowMapper<UseCase> {   						
        @Override
		public UseCase mapRow(ResultSet rs, int rowNum) throws SQLException {
        	UseCase domain = new UseCase();
        	domain.setUsecaseId(rs.getLong("usecase_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
 
		return domain;
		} 
    } 
	 
}
