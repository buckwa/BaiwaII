package com.buckwa.dao.impl.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

import com.buckwa.dao.intf.project.UseCaseDao;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.UseCase;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectConstant;
import com.buckwa.util.project.ProjectUtil;

@Repository("useCaseDao")
public class UseCaseDaoImpl implements UseCaseDao {
	private static Logger logger = LoggerFactory.getLogger(UseCaseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@Autowired
    private PathUtil pathUtil;

	public List<UseCase> getAllUseCaseByProjectId(String projectId) {
		logger.info(" Start");
		List<UseCase> returnList = new ArrayList<UseCase>(); 
		try{
			String sql ="  select * from project_usecase r where r.project_id="+projectId ;	
			logger.info(" sql:"+sql);
			returnList = this.jdbcTemplate.query(	sql,new UseCaseMapper());
			getDetailDesignList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	public List<UseCase> getAllUseCaseByModuleId(String moduleId) {
		logger.info(" moduleId: "+moduleId);
		List<UseCase> returnList = new ArrayList<UseCase>(); 
		try{
			String sql ="  select * from project_usecase r  where r.module_id="+moduleId ;	
			returnList = this.jdbcTemplate.query(	sql,new UseCaseMapper());
			getDetailDesignList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	private void getDetailDesignList (List<UseCase> returnListIn){
		
		try{
			for(UseCase useCaseTmp:returnListIn){
				String sql ="  select * from project_detail_design r  where r.usecase_id="+useCaseTmp.getUsecaseId() ;	
				//logger.info(" ## sql get DetailDesign: "+sql);
				List<DetailDesign> detailDesignList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
				useCaseTmp.setDetailDesignList(detailDesignList);
			}
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		
	}
	
	
	 
	@Override
	public void create(UseCase useCase) {
		logger.info(" #UseCaseDaoImpl.create # ");		
		final String code = projectUtil.getUseCaseNo(useCase.getProjectId());		
		final UseCase finalUseCase = useCase;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_usecase (code, name,summary,actor,basic_flow,alternate_flow,module_id,project_id,update_date) values (?, ?,?,?,?,?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,code);
				ps.setString(2,finalUseCase.getName());
				ps.setString(3,finalUseCase.getSummary());
				ps.setString(4,finalUseCase.getActor());
				ps.setString(5,finalUseCase.getBasicFlow());
				ps.setString(6,finalUseCase.getAlternateFlow());
				ps.setLong(7,finalUseCase.getModuleId());
				ps.setLong(8,finalUseCase.getProjectId());
				ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));		
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}
	
 
	@Override
	public void update(UseCase useCase) {
		logger.info(" #UseCaseDaoImpl.update useCase: "+BeanUtils.getBeanString(useCase));
		this.jdbcTemplate.update(
				"update  project_usecase set name=? ,summary=?,actor=?,basic_flow=?,alternate_flow=? ,update_date=? where usecase_id=? ",
				useCase.getName(),useCase.getSummary(),useCase.getActor(),useCase.getBasicFlow(),useCase.getAlternateFlow()  ,new Timestamp(System.currentTimeMillis()),useCase.getUsecaseId());
 
	}
 


	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		UseCase useCase = (UseCase)pagingBean.get("useCase");		
		List<UseCase> returnList = new ArrayList<UseCase>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_usecase  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(useCase.getCode())){
			sqltotalsb.append(" and r.code like  '%"+useCase.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(useCase.getName())){
			sqltotalsb.append(" and r.name like  '%"+useCase.getName().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_usecase r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(useCase.getCode())){
			sb.append(" and r.code like  '%"+useCase.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(useCase.getName())){
			sb.append(" and r.name like  '%"+useCase.getName().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(	sql,new UseCaseMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public UseCase getUseCaseById(String useCaseId) {		 
		UseCase useCase = this.jdbcTemplate.queryForObject(
				"select *  from project_usecase where usecase_id = ?",
				new Object[]{useCaseId},new UseCaseMapper());	 		
		
	   String sqlImage = "select *  from image_path where image_code ='"+useCaseId+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_USE_CASE+"'";
		logger.info(" sqlImage :"+sqlImage);
		if(useCase!=null){			
			List<ImagePath>	imagePathList = this.jdbcTemplate.query(sqlImage ,new ImagePathMapper());				
			//logger.info("  ### Found imagePathList :"+imagePathList);
			useCase.setFilePathList(imagePathList);
			List<UseCase> returnList = new ArrayList();
			returnList.add(useCase);
			getDetailDesignList(returnList);
			}			
		return useCase;
	}	

	
	@Override
	public void deleteUseCaseById(String useCaseId) {	
		String delsql =" delete from  project_usecase where usecase_id ="+useCaseId;
		logger.info("  ###### delete sql:"+delsql);
		this.jdbcTemplate.update(delsql);	
		 	
	}
	
 
	@Override
	public boolean isUseCaseAlreadyUsege(String useCaseId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwauseCase useCase inner join buckwagroupuseCase groupuseCase " +
					"on (useCase.useCase_id = groupuseCase.useCase_id)	where useCase.useCase_id ="+useCaseId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
 
	private class UseCaseMapper implements RowMapper<UseCase> {   						
        @Override
		public UseCase mapRow(ResultSet rs, int rowNum) throws SQLException {
        	UseCase domain = new UseCase();
        	domain.setUsecaseId(rs.getLong("usecase_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setSummary(rs.getString("summary"));
        	domain.setActor(rs.getString("actor"));
        	domain.setBasicFlow(rs.getString("basic_flow"));
        	domain.setAlternateFlow(rs.getString("alternate_flow"));
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setModuleId(rs.getLong("module_id")); 
         	domain.setCreateDate(rs.getTimestamp("create_date"));
         	domain.setCreateBy(rs.getString("create_by"));
         	domain.setUpdateDate(rs.getTimestamp("update_date"));
         	domain.setUpdateBy(rs.getString("update_by"));
		return domain;
		} 
    } 
	
	@Override
	public void updateFilePath(UseCase usecase) { 
		logger.info(" #### In updateFilePath usecase:"+BeanUtils.getBeanString(usecase));		
		// Step 1 Remove 	
		String sqlDelImage =" delete from image_path where image_code='"+usecase.getUsecaseId()+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_USE_CASE+"'";
		
		logger.info("  sqlDelImage:"+sqlDelImage);
 		this.jdbcTemplate.update(sqlDelImage );			
		List<ImagePath> imagePathList = usecase.getFilePathList();		
		for(ImagePath imagePathTmp :imagePathList){			
		 this.jdbcTemplate.update(
					"insert into image_path (image_code, image_path,image_type ,file_name) values (?, ?,? ,?)",
					usecase.getUsecaseId(), imagePathTmp.getImagePath(),ProjectConstant.ARTIFACE_TYPE_USE_CASE,imagePathTmp.getFileName());	
			
		}  	
 	}	
	
	private class ImagePathMapper implements RowMapper<ImagePath> {   						
        @Override
		public ImagePath mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ImagePath domain = new ImagePath();
        	domain.setImagePathId(rs.getLong("image_path_id")); 
        	domain.setImageCode(rs.getString("image_code"));
        	domain.setImagePath(rs.getString("image_path"));
         	domain.setImageType(rs.getString("image_type"));
         	domain.setFileName(rs.getString("file_name"));
         	domain.setFullImagePath(pathUtil.getFullPathByRelativePath(rs.getString("image_path")));
         	;
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
 
		return domain;
		} 
    } 
}
