package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.WorkPersonMappingFileDao;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.WorkPersonMappingFile;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 24, 2012 12:54:37 AM
 */
@Repository("workPersonMappingFileDao")
public class WorkPersonMappingFileDaoImpl implements WorkPersonMappingFileDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkPersonMappingFileDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	private static final String SQL_CREATE =
		" INSERT INTO work_person_mapping_file ( " +
		"   work_person_mapping_file_id, " +
		"   work_person_id, " +
		"   work_person_attr_id, " +
		"   file_id, " +
		"   status, " +
		"   create_by, " +
		"   update_by, " +
		"   created_date, " +
		"   updated_date " +
		" ) " +
		" VALUES ( " +
		"   NULL, ?, ?, ?, 'A', ?, ?, " +
		"   CURRENT_TIMESTAMP, " +
		"   CURRENT_TIMESTAMP " +
		" ) ";
	
	@Override
	public void create(final WorkPersonMappingFile domain) {
		logger.info(" #WorkPersonMappingFileDaoImpl.create # ");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, domain.getWorkPersonId());
				ps.setLong(2, domain.getWorkPersonAttrId());
				ps.setLong(3, domain.getFileId());
				ps.setString(4, "");
				ps.setString(5, "");
				return ps;
			}
		}, keyHolder);
		Long key = keyHolder.getKey().longValue();
		
		logger.info("key: " + key);
	}
	
	
	private static final String SQL_DELETE_FILE_LOCATION = 
		" UPDATE file_location " +
		"   SET status = 'I' " +
		" WHERE status = 'A' " +
		"   AND file_id = ? ";
	
	private static final String SQL_DELETE_WORK_PERSON_MAPPING_FILE = 
		" UPDATE work_person_mapping_file " +
		"   SET status = 'I' " +
		" WHERE status = 'A' " +
		"   AND work_person_id = ? " +
		"   AND file_id = ? ";
	
	@Override
	public void delete(WorkPersonMappingFile workPersonMappingFile) {
		this.jdbcTemplate.update(SQL_DELETE_FILE_LOCATION, new Object[] {workPersonMappingFile.getFileId()});
		this.jdbcTemplate.update(SQL_DELETE_WORK_PERSON_MAPPING_FILE, new Object[] {
			workPersonMappingFile.getWorkPersonId(),
			workPersonMappingFile.getFileId()
		});
	}
	
	
	private static final String SQL_GET_FILE_LOCATION_LIST = 
		" SELECT fl.*, map.work_person_attr_id, attr.label" +
		" FROM file_location AS fl " +
		" INNER JOIN  work_person_mapping_file map " +
		"   ON fl.file_id = map.file_id " +
		" INNER JOIN  work_person_attr attr " +
		"   ON attr.work_person_attr_id = map.work_person_attr_id " +
		" WHERE map.status='A' " +
		"   AND fl.status='A' " +
		"   AND map.work_person_id=? ";
	
	@Override
	public List<FileLocation> getFileLocationListByWorkPersonId(Long workPersonId) {
		List<FileLocation> resultList = this.jdbcTemplate.query(SQL_GET_FILE_LOCATION_LIST, new Object[] {
			workPersonId
		}, fileLocationMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + SQL_GET_FILE_LOCATION_LIST);
			logger.info("criteria: " + workPersonId);
			if (null != resultList) {
				logger.info("resultList.size(): " + resultList.size());
			}
		}
		
		return resultList;
	}
	
	
	private static final FileLocationMapper fileLocationMapper = new FileLocationMapper();
	private static class FileLocationMapper implements RowMapper<FileLocation> {
		@Override
		public FileLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
			FileLocation fileLocation = new FileLocation();
			fileLocation.setFileId(rs.getLong("file_id"));
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
			fileLocation.setWorkPersonAttrId(rs.getLong("work_person_attr_id"));
			fileLocation.setLabel(rs.getString("label"));
			return fileLocation;
		}
	}

}
