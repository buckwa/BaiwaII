package com.buckwa.dao.impl.pbp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.AcademicKPIUserMappingDao;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.webboard.Message;
import com.buckwa.util.school.SchoolUtil;

@Repository("academicKPIUserMappingDao")
public class AcademicKPIUserMappingDaoImpl implements AcademicKPIUserMappingDao {
	private static Logger logger = LoggerFactory.getLogger(AcademicKPIUserMappingDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
 
 
	@Override
	public AcademicKPIUserMappingWrapper getById( String id) {		 		
		
		AcademicKPIUserMappingWrapper  academicKPIUserMappingWrapper = new AcademicKPIUserMappingWrapper();
		
		
		String sqlMap =" select *  from academic_kpi_user_mapping  where kpi_user_mapping_id ="+id;
		 AcademicKPIUserMapping 	mappingTmp  = null;
		 logger.info(" sqlMap:"+sqlMap);
		
		try{
			mappingTmp  = this.jdbcTemplate.queryForObject(sqlMap,	new AcademicKPIUserMappingMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		} 
		
		if(mappingTmp!=null){ 
				
				String sqlkpi =" select *  from academic_kpi where academic_kpi_id ="+mappingTmp.getAcademicKPIId() ; 
				logger.info(" sqlkpi:"+sqlkpi);
				 
				
				try{
					 AcademicKPI  academicKPI  = this.jdbcTemplate.queryForObject(sqlkpi,	new AcademicKPIMapper() );
					 mappingTmp.setAcademicKPI(academicKPI);
					 
					 
						
						
						
						//String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+mappingTmp.getAcademicKPICode()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
						String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+mappingTmp.getAcademicKPIId()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
						List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList<AcademicKPIAttribute>();
						try{
							logger.info(" sqlAttribute:"+sqlAttribute);
							academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
							
						}catch (org.springframework.dao.EmptyResultDataAccessException ex){
							ex.printStackTrace();
						} 									
						
						mappingTmp.setAcademicKPIAttributeList(academicKPIAttributeList);
						
						
						Map<String,String> isValidateNoMap = new HashMap();
						for(AcademicKPIAttribute attTmp:academicKPIAttributeList){
							String attName =attTmp.getName();
							String validateNo =attTmp.getIsValidateNumber();
							
							isValidateNoMap.put(attName, validateNo);
						}
											 
					 
					 
					 
						String sqlAttributeValue =" SELECT a.* ,b.ratio AS ratio FROM academic_kpi_attribute_value a LEFT JOIN academic_kpi_user_mapping b ON a.kpi_user_mapping_id = b.kpi_user_mapping_id WHERE a.kpi_user_mapping_id ="+id; 
						List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList<AcademicKPIAttributeValue>();
						try{
							logger.info(" sqlAttributeValue:"+sqlAttributeValue);
							academicKPIAttributeValueList = this.jdbcTemplate.query(sqlAttributeValue,	new AcademicKPIAttributeValueMapper() );
							
							for(AcademicKPIAttributeValue valueTmp:academicKPIAttributeValueList){
								String valueName= valueTmp.getName();
								String isValidateNoValue =  isValidateNoMap.get(valueName);
								valueTmp.setIsValidateNumber(isValidateNoValue);
								
							}
							
						}catch (org.springframework.dao.EmptyResultDataAccessException ex){
							ex.printStackTrace();
						} 
						
						mappingTmp.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);

						
						// Get Image
						
						String sqlAttachFile  =" select *  from academic_kpi_attach_file  where kpi_user_mapping_id ="+mappingTmp.getKpiUserMappingId() ; 
						 List<AcademicKPIAttachFile> academicKPIAttachFileList = new ArrayList<AcademicKPIAttachFile>();
						try{
							logger.info(" sqlAttachFile:"+sqlAttribute);
							academicKPIAttachFileList = this.jdbcTemplate.query(sqlAttachFile,	new AcademicKPIAttachFileMapper() );
							
						}catch (org.springframework.dao.EmptyResultDataAccessException ex){
							ex.printStackTrace();
						} 									
						
						mappingTmp.setAcademicKPIAttachFileList(academicKPIAttachFileList);
						
						
						// Get Message
						
						StringBuffer sbmessage = new StringBuffer();
						sbmessage.append(" select   \n");
						sbmessage.append(" * from webboard_message r  \n");
						sbmessage.append(" where 1=1 and topic_id="+mappingTmp.getKpiUserMappingId());	
				 
						String sql =sbmessage.toString();		
						//logger.info(" sql sbmessage:"+sql);			
						List <Message> messageList = this.jdbcTemplate.query(
								sql,
								new RowMapper<Message>() {
								public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
									Message domain = new Message();				 
									domain.setMessageId(rs.getLong("message_id"));
									domain.setTopicId(rs.getLong("topic_id"));
									domain.setMessageDetail(rs.getString("detail"));
									domain.setCreateBy(rs.getString("create_by"));
									domain.setStatus(rs.getString("status"));
									domain.setCreateDate(rs.getTimestamp("create_date"));
								return domain;
								}
								}); 
						mappingTmp.setMessageList(messageList);
						
						
						
						String sqlworktype =" select *  from pbp_work_type where code ="+academicKPI.getWorkTypeCode() ;  				 
					 
						List<PBPWorkType> pBPWorkTypeList  = this.jdbcTemplate.query(sqlworktype,	new PBPWorkTypeMapper() );	
						academicKPIUserMappingWrapper.setpBPWorkType(pBPWorkTypeList.get(0));
					 
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				} 
				
				
				
			  
			
		}
		
	
		
		
		
		academicKPIUserMappingWrapper.setAcademicKPIUserMapping(mappingTmp);
		
	 
		return academicKPIUserMappingWrapper;
	}
	
	
	
	@Override
	public void approve( String id,String approveBy) {		 		
		String sql =" update   academic_kpi_user_mapping set status='APPROVED',approve_by ='"+approveBy+"' , approve_date_time =CURRENT_TIMESTAMP  where kpi_user_mapping_id ="+id+"" ; 
		logger.info(" sql:"+sql);
		this.jdbcTemplate.update(sql); 
	 
	  
	}
	
	@Override
	public void update( AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper) {		 
		
		
		List<AcademicKPIAttributeValue> academicKPIAttributeValueList =academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPIAttributeValueList();
		for(final AcademicKPIAttributeValue tmp:academicKPIAttributeValueList){
			String sql =" update   academic_kpi_attribute_value set value='"+tmp.getValue()+"' where name ='"+tmp.getName()+"' and kpi_user_mapping_id ="+tmp.getAcademicKPIMappingId()+"" ; 
			logger.info(" sql update :"+sql);
			this.jdbcTemplate.update(sql); 			
		}

	 
	  
	}
	
	@Override
	public void changeKPI( AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper) {		 
		
		
		List<AcademicKPIAttributeValue> academicKPIAttributeValueList =academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPIAttributeValueList();
		for(final AcademicKPIAttributeValue tmp:academicKPIAttributeValueList){
			//String sql =" update   academic_kpi_user_mapping set academic_kpi_code='"+academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPI().getCode()+"'"
			String sql =" update   academic_kpi_user_mapping set academic_kpi_id='"+academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPI().getAcademicKPIId()+"'"
					+ ", academic_kpi_id="+academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPI().getAcademicKPIId()+"  "
							+ "where   kpi_user_mapping_id ="+tmp.getAcademicKPIMappingId()+"" ; 
			logger.info(" sql update :"+sql);
			this.jdbcTemplate.update(sql); 			
		}

	 
	  
	}
	
 
	
	@Override
	public void delete( String id) {		 		
		String sql =" delete from   academic_kpi_user_mapping   where kpi_user_mapping_id ="+id+"" ; 
		String sqlAttributeValue =" delete from   academic_kpi_attribute_value   where kpi_user_mapping_id ="+id+"" ; 
		String sqlAttachFile =" delete from   academic_kpi_attach_file   where academic_kpi_user_id ="+id+"" ; 

		logger.info(" sql:"+sql);
		logger.info(" sqlAttributeValue:"+sqlAttributeValue);
		logger.info(" sqlAttachFile:"+sqlAttachFile);
		this.jdbcTemplate.update(sql); 
		this.jdbcTemplate.update(sqlAttributeValue); 
		this.jdbcTemplate.update(sqlAttachFile); 
	  
	}
	
	
	@Override
	public void unApprove( String id) {		 		
		String sql =" update academic_kpi_user_mapping set status ='CREATE' where kpi_user_mapping_id ="+id+"" ; 
	 
 
		logger.info(" sql:"+sql);
 
		this.jdbcTemplate.update(sql); 
	  
	}
	
	
	
	private class AcademicKPIUserMappingMapper implements RowMapper<AcademicKPIUserMapping> {   						
        @Override
		public AcademicKPIUserMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIUserMapping domain = new AcademicKPIUserMapping(); 
        	
        	
        	domain.setKpiUserMappingId(rs.getLong("kpi_user_mapping_id"));
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		 
			domain.setWorkTypeCode(rs.getString("work_type_code"));  
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setAcademicKPICode(rs.getString("academic_kpi_code"));
			domain.setStatus(rs.getString("status"));
			domain.setRatio(rs.getInt("ratio"));
			domain.setCalResultStr(rs.getString("cal_result_str"));
			domain.setUserName(rs.getString("user_name"));
			//logger.info(rs.getInt("ratio"));
		 
		return domain;
    }
	}
	 
 
	
	private class AcademicKPIAttributeValueMapper implements RowMapper<AcademicKPIAttributeValue> {   						
        @Override
		public AcademicKPIAttributeValue mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttributeValue domain = new AcademicKPIAttributeValue(); 
        	 
        	domain.setAcademicKPIMappingId(rs.getLong("kpi_user_mapping_id"));
			domain.setName(rs.getString("name"));		
			domain.setValue(rs.getString("value"));	 
			domain.setRatio(rs.getInt("ratio"));	 
		 
			return domain;
    }
	}
	
	private class AcademicKPIMapper implements RowMapper<AcademicKPI> {   						
        @Override
		public AcademicKPI mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPI domain = new AcademicKPI(); 
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setWorkTypeCode(rs.getString("work_type_code"));
			domain.setDescription(rs.getString("description"));	
			domain.setStatus(rs.getString("status"));	
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setMark(rs.getBigDecimal("mark"));
			domain.setUnitCode(rs.getString("unit_code"));
			domain.setUnitDesc(schoolUtil.getUnitDescMyCode(rs.getString("unit_code"), rs.getString("academic_year")));
			domain.setFromRegis(rs.getString("from_reg"));
		 
		return domain;
    }
	}
	
	private class AcademicKPIAttributeMapper implements RowMapper<AcademicKPIAttribute> {   						
        @Override
		public AcademicKPIAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttribute domain = new AcademicKPIAttribute(); 
        	domain.setAcademicKPIAtributeId(rs.getLong("kpi_attribute_id"));
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setAcademicKPICode(rs.getString("academic_kpi_code"));
			domain.setIsCalculate(rs.getString("is_calculate")); 
			domain.setIsValidateNumber(rs.getString("is_validate_number"));
			domain.setAcademicYear(rs.getString("academic_year"));
		    domain.setMandatory(rs.getString("mandatory"));
		   
		 
		return domain;
    }
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
 
	private class PBPWorkTypeMapper implements RowMapper<PBPWorkType> {   						
        @Override
		public PBPWorkType mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PBPWorkType domain = new PBPWorkType(); 
        	domain.setWorkTypeId(rs.getLong("work_type_id"));
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("name"));
			domain.setDescription(rs.getString("description"));
			domain.setMinPercent(rs.getBigDecimal("min_percent"));
			domain.setMinHour(rs.getBigDecimal("min_hour"));
			domain.setMaxPercent(rs.getBigDecimal("max_percent"));
			domain.setMaxHour(rs.getBigDecimal("max_hour"));
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setLimitBase(rs.getBigDecimal("limit_base"));
		 
		return domain;
    }
	}
}
