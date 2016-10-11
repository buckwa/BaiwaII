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

import com.buckwa.dao.intf.project.LabDao;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Lab;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectConstant;
import com.buckwa.util.project.ProjectUtil;

@Repository("labDao")
public class LabDaoImpl implements LabDao {
	private static Logger logger = LoggerFactory.getLogger(LabDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@Autowired
    private PathUtil pathUtil;

	public List<Lab> getAllLabByProjectId(String projectId) {
		logger.info(" #LabDaoImpl.getAllLab # ");
		List<Lab> returnList = new ArrayList<Lab>(); 
		try{
			String sql ="  select * from project_lab r where r.project_id="+projectId ;	
			returnList = this.jdbcTemplate.query(	sql,new LabMapper());
			getDetailDesignList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	public List<Lab> getAllLabByLabCategoryId(String moduleId) {
		logger.info(" #LabDaoImpl.getAllLabByLabCategoryIde moduleId: "+moduleId);
		List<Lab> returnList = new ArrayList<Lab>(); 
		try{
			String sql ="  select * from project_lab r  where r.module_id="+moduleId ;	
			returnList = this.jdbcTemplate.query(	sql,new LabMapper());
			getDetailDesignList(returnList);
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	private void getDetailDesignList (List<Lab> returnListIn){
		
		try{
			for(Lab labTmp:returnListIn){
				String sql ="  select * from project_detail_design r  where r.lab_id="+labTmp.getLabId() ;	
				logger.info(" ## sql get DetailDesign: "+sql);
				List<DetailDesign> detailDesignList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
				labTmp.setDetailDesignList(detailDesignList);
			}
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		
	}
	
	
	 
	@Override
	public void create(Lab lab) {
		logger.info(" #LabDaoImpl.create # ");		
		final String code = projectUtil.getLabNo( );		
		final Lab finalLab = lab;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_lab (code, name,summary,actor,basic_flow,alternate_flow,lab_category_id) values ( ?,?,?,?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,code);
				ps.setString(2,finalLab.getName());
				ps.setString(3,finalLab.getSummary());
				ps.setString(4,finalLab.getActor());
				ps.setString(5,finalLab.getBasicFlow());
				ps.setString(6,finalLab.getAlternateFlow());
				ps.setLong(7,finalLab.getLabCategoryId());
				 
				 			
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}
	
 
	@Override
	public void update(Lab lab) {
		logger.info(" #LabDaoImpl.update lab: "+BeanUtils.getBeanString(lab));
		this.jdbcTemplate.update(
				"update  project_lab set name=? ,summary=?,actor=?,basic_flow=?,alternate_flow=?  where lab_id=? ",
				lab.getName(),lab.getSummary(),lab.getActor(),lab.getBasicFlow(),lab.getAlternateFlow()  ,lab.getLabId());
 
	}
 


	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		Lab lab = (Lab)pagingBean.get("lab");		
		List<Lab> returnList = new ArrayList<Lab>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_lab  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(lab.getCode())){
			sqltotalsb.append(" and r.code like  '%"+lab.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(lab.getName())){
			sqltotalsb.append(" and r.name like  '%"+lab.getName().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_lab r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(lab.getCode())){
			sb.append(" and r.code like  '%"+lab.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(lab.getName())){
			sb.append(" and r.name like  '%"+lab.getName().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(	sql,new LabMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public Lab getLabById(String labId) {		 
		Lab lab = this.jdbcTemplate.queryForObject(
				"select *  from project_lab where lab_id = ?",
				new Object[]{labId},new LabMapper());	 		
		
	   String sqlImage = "select *  from image_path where image_code ='"+labId+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_USE_CASE+"'";
		logger.info(" sqlImage :"+sqlImage);
		if(lab!=null){			
			List<ImagePath>	imagePathList = this.jdbcTemplate.query(sqlImage ,new ImagePathMapper());				
			logger.info("  ### Found imagePathList :"+imagePathList);
			lab.setFilePathList(imagePathList);
			List<Lab> returnList = new ArrayList();
			returnList.add(lab);
			getDetailDesignList(returnList);
			}			
		return lab;
	}	

	
	@Override
	public void deleteLabById(String labId) {	
		String delsql =" delete from  project_lab where lab_id ="+labId;
		logger.info("  ###### delete sql:"+delsql);
		this.jdbcTemplate.update(delsql);	
		 	
	}
	
 
	@Override
	public boolean isLabAlreadyUsege(String labId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwalab lab inner join buckwagrouplab grouplab " +
					"on (lab.lab_id = grouplab.lab_id)	where lab.lab_id ="+labId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
 
	private class LabMapper implements RowMapper<Lab> {   						
        @Override
		public Lab mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Lab domain = new Lab();
        	domain.setLabId(rs.getLong("lab_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setSummary(rs.getString("summary"));
        	domain.setActor(rs.getString("actor"));
        	domain.setBasicFlow(rs.getString("basic_flow"));
        	domain.setAlternateFlow(rs.getString("alternate_flow"));
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setLabCategoryId(rs.getLong("lab_category_id")); 
         	domain.setCreateDate(rs.getTimestamp("create_date"));
         	domain.setCreateBy(rs.getString("create_by"));
         	domain.setUpdateDate(rs.getTimestamp("update_date"));
         	domain.setUpdateBy(rs.getString("update_by"));
		return domain;
		} 
    } 
	
	@Override
	public void updateFilePath(Lab lab) { 
		logger.info(" #### In updateFilePath lab:"+BeanUtils.getBeanString(lab));		
		// Step 1 Remove 	
		String sqlDelImage =" delete from image_path where image_code='"+lab.getLabId()+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_USE_CASE+"'";
		
		logger.info("  sqlDelImage:"+sqlDelImage);
 		this.jdbcTemplate.update(sqlDelImage );			
		List<ImagePath> imagePathList = lab.getFilePathList();		
		for(ImagePath imagePathTmp :imagePathList){			
		 this.jdbcTemplate.update(
					"insert into image_path (image_code, image_path,image_type ,file_name) values (?, ?,? ,?)",
					lab.getLabId(), imagePathTmp.getImagePath(),ProjectConstant.ARTIFACE_TYPE_USE_CASE,imagePathTmp.getFileName());	
			
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
