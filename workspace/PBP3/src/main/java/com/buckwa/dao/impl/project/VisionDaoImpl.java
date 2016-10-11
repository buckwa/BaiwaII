package com.buckwa.dao.impl.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.project.VisionDao;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.project.Vision;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectConstant;

@Repository("visionDao")
public class VisionDaoImpl implements VisionDao {
	private static Logger logger = LoggerFactory.getLogger(VisionDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
    private PathUtil pathUtil;
	 
 
	@Override
	public void update(Vision vision) {
		logger.info(" #UseCaseDaoImpl.update Vision: "+BeanUtils.getBeanString(vision));
		this.jdbcTemplate.update(
				"update  project_vision set summary=? ,detail=? ,reference=?,update_date=? ,update_by=? where vision_id=? ",
				 vision.getSummary(),vision.getDetail(),vision.getReference(),new java.sql.Timestamp(System.currentTimeMillis()),vision.getUpdateBy(),vision.getVisionId());
 
	}
  
	@Override
	public Vision getVisionByProjectId(String projectId) {	
		logger.info("  ### getVisionByProjectId :"+projectId);
		Vision vision =  null;
		try{
			vision = this.jdbcTemplate.queryForObject(
					"select *  from project_vision where project_id = ?",
					new Object[]{projectId},new VisionMapper());	
			
			if(vision!=null){
				
			List<ImagePath>	imagePathList = this.jdbcTemplate.query(
						"select *  from image_path where image_code = ? and image_type ='"+ProjectConstant.ARTIFACE_TYPE_VISION+"'",
						new Object[]{projectId},new ImagePathMapper());	
			
			logger.info("  ### Found imagePathList :"+imagePathList);
			vision.setImagePathList(imagePathList);
			
			
			List<ImagePath>	filePathList = this.jdbcTemplate.query(
					"select *  from image_path where image_code = ? and image_type ='"+ProjectConstant.ARTIFACE_TYPE_VISION_REFERENCE+"'",
					new Object[]{projectId},new ImagePathMapper());	
		
		logger.info("  ### Found filePathList :"+filePathList);
		vision.setFilePathList(filePathList);			
			}
			
		}catch(Exception ex){
			ex.printStackTrace(); 
		} 
		return vision;
	}	
 
	
	@Override
	public void updateImagePath(Vision vision) { 
		logger.info(" #### In updateImagePath vision:"+BeanUtils.getBeanString(vision));
		
		// Step 1 Remove 	
		String sqlDelImage =" delete from image_path where image_code='"+vision.getProjectId()+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_VISION+"'";
		
		logger.info("  sqlDelImage:"+sqlDelImage);
 		this.jdbcTemplate.update(sqlDelImage );			
		List<ImagePath> imagePathList = vision.getImagePathList();		
		for(ImagePath imagePathTmp :imagePathList){			
		 this.jdbcTemplate.update(
					"insert into image_path (image_code, image_path,image_type,file_name ) values (?, ?,? ,?)",
					vision.getProjectId(), imagePathTmp.getImagePath(),ProjectConstant.ARTIFACE_TYPE_VISION,imagePathTmp.getFileName());	
			
		}  	
 	}
	@Override
	public void updateRefenceFilePath(Vision vision) { 
		logger.info(" #### In updateRefenceFilePath vision:"+BeanUtils.getBeanString(vision));
		
		// Step 1 Remove 	
		String sqlDelImage =" delete from image_path where image_code='"+vision.getProjectId()+"' and image_type ='"+ProjectConstant.ARTIFACE_TYPE_VISION_REFERENCE+"'";
		
		logger.info("  sqlDelImage:"+sqlDelImage);
 		this.jdbcTemplate.update(sqlDelImage );			
		List<ImagePath> imagePathList = vision.getFilePathList();		
		for(ImagePath imagePathTmp :imagePathList){			
		 this.jdbcTemplate.update(
					"insert into image_path (image_code, image_path,image_type,file_name ) values (?, ?,?,? )",
					vision.getProjectId(), imagePathTmp.getImagePath(),ProjectConstant.ARTIFACE_TYPE_VISION_REFERENCE,imagePathTmp.getFileName());	
			
		}  	
 	}
	
	@Override
	public void removeImagePath(ImagePath imagePath) { 
 		String sqlDeleteImage =" delete from  image_path  where image_path_id="+imagePath.getImagePathId();
		logger.info(" ## Remove imagepath sql:"+sqlDeleteImage);
 		this.jdbcTemplate.update(sqlDeleteImage);					 
 	}
	
	
	@Override
	public void removeFilePath(ImagePath imagePath) { 
 		String sqlDeleteImage =" delete from  image_path  where image_path_id="+imagePath.getImagePathId();
		logger.info(" ## Remove imagepath sql:"+sqlDeleteImage);
 		this.jdbcTemplate.update(sqlDeleteImage);					 
 	}
	
	
	
	private class VisionMapper implements RowMapper<Vision> {   						
        @Override
		public Vision mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Vision domain = new Vision();
        	domain.setProjectId(rs.getLong("project_id")); 
        	domain.setVisionId(rs.getLong("vision_id")); 
        	domain.setSummary(rs.getString("summary"));
         	domain.setDetail(rs.getString("detail")); 
         	domain.setReference(rs.getString("reference")); 
         	
         	domain.setCreateDate(rs.getTimestamp("create_date"));
         	domain.setCreateBy(rs.getString("create_by"));
         	domain.setUpdateDate(rs.getTimestamp("update_date"));
         	domain.setUpdateBy(rs.getString("update_by"));
         	
		return domain;
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
	
}
