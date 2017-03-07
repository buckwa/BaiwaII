package com.buckwa.dao.impl.pbp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

 
import com.buckwa.dao.intf.pbp.AcademicKPIDao;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.HeadApproveSummary;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolConstants;
import com.buckwa.util.school.SchoolUtil;

@Repository("headApproveSummaryDao")
public class HeadApproveSummaryDao  {
	private static Logger logger = LoggerFactory.getLogger(HeadApproveSummaryDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	public void HeadApproveInsert(AcademicKPIUserMapping domain, int academicKPIId) {
		logger.info("  ########## HeadApproveInsert : "+BeanUtils.getBeanString(domain));
		
		final HeadApproveSummary HeadApp = new HeadApproveSummary(); 
		
		HeadApp.setAcademic_year(domain.getAcademicYear());//มี
		HeadApp.setFac_code(domain.getFac_code());//มี
		HeadApp.setFac_name(domain.getFac_name());
		HeadApp.setDep_code(domain.getDep_code());
		HeadApp.setDep_name(domain.getDep_name());
		HeadApp.setKpi_id(domain.getAcademicKPIId().intValue());//มี
		HeadApp.setKpi_name(domain.getName());
		HeadApp.setKpi_unit("");//ไม่มี
		HeadApp.setKpi_user_mapping_id(academicKPIId);//มี
		HeadApp.setUser_name(domain.getUserName());//มี
		HeadApp.setFull_name(domain.getThaiName());//มี
		HeadApp.setIs_approve(domain.getStatus());//มี
		HeadApp.setIs_from_reg("N"); 
		HeadApp.setWork_name(domain.getWork_name());//มี
		HeadApp.setWork_type_code(domain.getWorkTypeCode());//มี
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement(
						"  INSERT INTO head_approve_summary  " +						
						"  (academic_year,  " +
						"  fac_code, " +
						"  fac_name, " +
						"  dep_code, " +
						"  dep_name, " +
						"  kpi_id,   " +
						"  kpi_name, " +
						"  kpi_unit,  " +
						"  kpi_user_mapping_id,  " +
						"  user_name,  " +
						"  full_name,  " +
						"  is_approve,  " +
						"  is_from_reg,   " +
						"  work_name,  " +
						"  work_type_code)  " +
						"  VALUES (?, ?, ?, ?, ?, ?,?, ?,?, ?, ?,?, ?, ? , ?)  " +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1, HeadApp.getAcademic_year());
				ps.setString(2, HeadApp.getFac_code()); 
				ps.setString(3, HeadApp.getFac_name());
				ps.setString(4, HeadApp.getDep_code());
				ps.setString(5, HeadApp.getDep_name());
				ps.setLong(6, HeadApp.getKpi_id());
				ps.setString(7, HeadApp.getKpi_name());
				ps.setString(8, HeadApp.getKpi_unit());
				ps.setLong(9, HeadApp.getKpi_user_mapping_id());
				ps.setString(10, HeadApp.getUser_name());
				ps.setString(11, HeadApp.getFull_name());
				ps.setString(12, HeadApp.getIs_approve());
				ps.setString(13, HeadApp.getIs_from_reg());
				ps.setString(14, HeadApp.getWork_name());
				ps.setString(15, HeadApp.getWork_type_code());
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();	
		logger.info("  ########## Key : "+returnid);
			
	}

}
