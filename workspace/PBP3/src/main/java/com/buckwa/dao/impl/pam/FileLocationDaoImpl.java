package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.FileLocationDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 6, 2012 13:37:14 PM
 *
 **/

@Repository("fileLocationDao")

public class FileLocationDaoImpl implements FileLocationDao {

	private static Logger logger = LoggerFactory.getLogger(FileLocationDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_GET_COUNT_BY_FROM_TABLE = 
			" SELECT COUNT(*) AS total_item  FROM file_location WHERE status = 'A' AND  from_table = ";
	
	private static final String SQL_GET_ALL_FILE_LOCATION_BY_FILE_TABLE = 
			" SELECT *  FROM file_location WHERE status = 'A' AND from_table = ";
	
	@Override
	public PagingBean getByOffset(PagingBean pagingBean) {
		
		List<FileLocation> returnList = new ArrayList<FileLocation>();
		
		String fromTable = (String)pagingBean.get("fromTable");
		String fileName =  (String)pagingBean.get("fileName");
		
		String sqltotal = SQL_GET_COUNT_BY_FROM_TABLE +"'"+fromTable+"' " ;
		
		// For Search by File Name
		if(StringUtils.hasText(fileName)){
			sqltotal += " AND file_name like  '%"+StringEscapeUtils.escapeSql(fileName.trim())+"%' " ;
		}
		
		logger.info(" sqltotal :"+sqltotal);

		// For check total items
		int  rows_totalItem = jdbcTemplate.queryForObject( sqltotal,Integer.class ); 
		pagingBean.setTotalItems(rows_totalItem);
		
		// Select as limit
		StringBuffer sb = new StringBuffer();
		sb.append(SQL_GET_ALL_FILE_LOCATION_BY_FILE_TABLE+"'"+fromTable+"'"); 
		// For Search by File Name
		if(StringUtils.hasText(fileName)){
			sb.append(" AND file_name like  '%"+StringEscapeUtils.escapeSql(fileName.trim())+"%' ");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		
		logger.info(" sql :"+sb.toString());	
		
		returnList = this.jdbcTemplate.query(sb.toString() , fileLocationMapper);
		
		logger.info("### File List >>> "+returnList);	
		
		pagingBean.setCurrentPageItem(returnList);
		
		return pagingBean;
	}
	
	public Long create(final FileLocation  fileLocation) {
		
		final StringBuilder sb = new StringBuilder();
			sb.append( " INSERT INTO file_location  ");
			sb.append( " 	(file_id,                   ");
			sb.append( " 	file_path,                  ");
			sb.append( " 	from_table,                 ");
			sb.append( " 	file_code,                  ");
			sb.append( " 	file_name,                  ");
			sb.append( " 	file_extension,              ");
			sb.append( " 	file_type,                  ");
			sb.append( " 	file_desc,                  ");
			sb.append( " 	file_size,                  ");
			sb.append( " 	topiclv1_id,                ");
			sb.append( " 	status,                     ");
			sb.append( " 	create_by,                  ");
			sb.append( " 	create_date,                ");
			sb.append( " 	update_by,                  ");
			sb.append( " 	update_date	)               ");
			sb.append( " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'A', ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP ) " );
			
			logger.info("FileLocation >> "+fileLocation);
			logger.info("sql : "+sb.toString());
			
			PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
					PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
					ps.setString( 1 , fileLocation.getFilePath());
					ps.setString( 2 , fileLocation.getFromTable());
					ps.setString( 3 , fileLocation.getFileCode());
					ps.setString( 4 , fileLocation.getFileName());
					ps.setString( 5 , fileLocation.getFileExtension());
					ps.setString( 6 , fileLocation.getFileType());
					ps.setString( 7 , fileLocation.getFileDesc());
					ps.setString( 8 , fileLocation.getFileSize());
					ps.setString( 9 , fileLocation.getTopiclv1Id());
					ps.setString( 10 , fileLocation.getCreateBy());
					ps.setString( 11 , fileLocation.getUpdateBy());
					return ps;  
				}
			};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
		Long key = keyHolder.getKey().longValue();
		
		return key;
	}
	
	
	public Long createPBPAttachFile(final AcademicKPIAttachFile  academicKPIAttachFile) {
		/*
		DROP TABLE IF EXISTS `pbp`.`academic_kpi_attach_file`;
		CREATE TABLE  `pbp`.`academic_kpi_attach_file` (
		  `attach_file_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
		  `file_name` varchar(200) DEFAULT NULL,
		  `file_path` varchar(450) DEFAULT NULL,
		  `academic_kpi_user_id` int(10) unsigned DEFAULT NULL,
		  `kpi_user_mapping_id` int(10) unsigned DEFAULT NULL,
		  PRIMARY KEY (`attach_file_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		*/
		final StringBuilder sb = new StringBuilder();
			sb.append( " INSERT INTO academic_kpi_attach_file  ");
			sb.append( " 	(full_path_name,                   ");
		 
			sb.append( " 	kpi_user_mapping_id ,file_name ,create_date,create_by         )        ");
			 
		 
			sb.append( " VALUES (  ?, ?,?, CURRENT_TIMESTAMP,?  ) " );
			
		 
			logger.info("sql : "+sb.toString());
			
			PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
					PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
					ps.setString( 1 ,academicKPIAttachFile.getFullFilePathName());
					ps.setString( 2 , academicKPIAttachFile.getKpiUserMappingId());
					ps.setString( 3 , academicKPIAttachFile.getFileName());
					ps.setString( 4 , academicKPIAttachFile.getCreateBy()); 
				 
					return ps;  
				}
			};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
		Long key = keyHolder.getKey().longValue();
		
		return key;
	}
	
	
	public boolean updatePBPPersonPicture(String personId,String picturePath) {
 
		String sql = " update person_pbp set picture = '"+picturePath+"' where person_id = "+personId+" ";
		
		logger.info("sql : "+sql);
		
		this.jdbcTemplate.update(sql);
		
		return true;
	}
	
	
	
	
	@Override
	public FileLocation findByFileCode(String fileCode) {
		
		FileLocation fileLocation = new FileLocation();
		
		String sql = " select * from file_location where status='A' and file_code ='"+fileCode+"' ";
		
		logger.info("sql : "+sql);
		
		fileLocation = (this.jdbcTemplate.query(sql, fileLocationMapper)).get(0);
		
		logger.info("result >> "+fileLocation);
		
		return fileLocation;
	}
	
	
	@Override
	public AcademicKPIAttachFile findPBPAttachFile(String fileCode) {
		
		AcademicKPIAttachFile academicKPIAttachFile = new AcademicKPIAttachFile();
		
		String sql = " select * from academic_kpi_attach_file where  attach_file_id ="+fileCode+" ";
		
		logger.info("sql : "+sql);
		
		academicKPIAttachFile = (this.jdbcTemplate.query(sql, new AcademicKPIAttachFileMapper())).get(0);
		
		logger.info("result >> "+academicKPIAttachFile);
		
		return academicKPIAttachFile;
	}
	
	
	private class AcademicKPIAttachFileMapper implements RowMapper<AcademicKPIAttachFile> {   						
        @Override
		public AcademicKPIAttachFile mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttachFile domain = new AcademicKPIAttachFile(); 
        	domain.setAttachFileId(rs.getLong("attach_file_id"));
        	domain.setKpiUserMappingId(rs.getString("kpi_user_mapping_id"));
        	domain.setFullFilePathName(rs.getString("full_path_name"));
        	domain.setFileName(rs.getString("file_name")); 
		 
		return domain;
    }
	}
	
	
	@Override
	public void deleteFile(String fileCode) {
		
//		String fileName = (file.trim()).substring(0,file.lastIndexOf("."));
//		String fileType = (file.trim()).substring(file.lastIndexOf("."));
		
		String _fileCode = fileCode.trim();
//		String sql = " update file_location set status = 'I' where file_name = '"+fileName+"' and file_type ='"+fileType+"' ";
		String sql = " update file_location set status = 'I' where file_code = '"+_fileCode+"' ";
		
		logger.info("sql : "+sql);
		
		this.jdbcTemplate.update(sql);
	}
	
	
	@Override
	public void deletePBPAttachFile(String attach_file_id) {
		
 
	 
//		String sql = " update file_location set status = 'I' where file_name = '"+fileName+"' and file_type ='"+fileType+"' ";
		String sql = " delete from  academic_kpi_attach_file   where attach_file_id = "+attach_file_id+" ";
		
		logger.info("sql : "+sql);
		
		this.jdbcTemplate.update(sql);
	}
	
	
	
	
	private static final FileLocationMapper fileLocationMapper = new FileLocationMapper();
	private static class FileLocationMapper implements RowMapper<FileLocation> {
		@Override
		public FileLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			FileLocation fileLocation = new FileLocation();
			fileLocation.setFilePath(rs.getString("file_path"));
			fileLocation.setFromTable(rs.getString("from_table"));
			fileLocation.setFileCode(rs.getString("file_code"));
			fileLocation.setFileName(rs.getString("file_name"));
			fileLocation.setFileExtension(rs.getString("file_extension"));
			fileLocation.setFileType(rs.getString("file_type"));
			fileLocation.setFileDesc(rs.getString("file_desc"));
			fileLocation.setFileSize(rs.getString("file_size"));
			fileLocation.setTopiclv1Id(rs.getString("topiclv1_id"));
			fileLocation.setStatus(rs.getString("status"));
			fileLocation.setCreateBy(rs.getString("create_by"));
			fileLocation.setCreateDate(rs.getTimestamp("create_date"));
			fileLocation.setUpdateBy(rs.getString("update_by"));
			fileLocation.setUpdateDate(rs.getTimestamp("update_date"));
			
			return fileLocation;
		}
	}
	
	
	
}
