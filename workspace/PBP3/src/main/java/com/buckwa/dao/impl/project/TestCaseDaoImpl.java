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

import com.buckwa.dao.intf.project.TestCaseDao;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.TestCase;
import com.buckwa.domain.project.TestCaseDetail;
import com.buckwa.domain.project.TestWrapper;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectUtil;
import com.buckwa.web.controller.admin.RoleManagementController;


@Repository("testCaseDao")
public class TestCaseDaoImpl implements TestCaseDao {
	private static Logger logger = LoggerFactory.getLogger(TestCaseDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@Override
	public List<TestCase> getAllByModuleId(String moduleId) {
		logger.info(" #getAllByModuleId moduleId: "+moduleId);
		List<TestCase> returnList = new ArrayList<TestCase>(); 
		try{
			String sql ="";
			if(StringUtils.hasText(moduleId)){
				sql ="  select * from project_testcase r  where r.module_id="+moduleId ;	
			}else{
				sql ="  select * from project_testcase r  ";	
			}			 
			returnList = this.jdbcTemplate.query(	sql,new TestCaseMapper());
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	@Override
	public List<TestWrapper> getAllByProjectId(String projectId) {
		logger.info(" #getAllByProjectId projectId: "+projectId);
		List<TestWrapper> returnList = new ArrayList<TestWrapper>(); 
		try{
			List<TestCase> testCaseList = new ArrayList();  
			List<DetailDesign> detailDesignList = new ArrayList(); 
			String sqlDetailDesign ="";
			if(StringUtils.hasText(projectId)){
				sqlDetailDesign ="  select * from project_detail_design  r  where r.project_id="+projectId ;	
			} 			
			logger.info("  #### getAllByProjectId  sqlDetailDesign sql:"+sqlDetailDesign);
			detailDesignList = this.jdbcTemplate.query(	sqlDetailDesign,new DetailDesignMapper());	 			
			if(detailDesignList!=null&&detailDesignList.size()>0){	 
				for(DetailDesign detailTmp:detailDesignList){	
					TestWrapper testWrapper = new TestWrapper();
					testWrapper.setDetailDesign(detailTmp);
					String moduleId = detailTmp.getModuleId()+"";
					String detailDesignId = detailTmp.getDetailDesignId()+"";
					logger.info(" ## Get Testcase by Module Id :"+moduleId);
					String sqlTestCase  ="  select * from project_testcase r  where r.detail_design_id="+detailDesignId +" and r.module_id="+moduleId+" and r.project_id="+projectId ;	
					logger.info("  sqlTestCase:"+sqlTestCase);
					testCaseList = this.jdbcTemplate.query(	sqlTestCase,new TestCaseMapper());	 
					testWrapper.setTestCaseList(testCaseList);
					returnList.add(testWrapper);
				}
			}			 
			
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}	
	 
	@Override
	public List<TestWrapper> getAllWrapByModuleId(String moduleId) {
		logger.info(" #getAllWrapByModuleId moduleId: "+moduleId);
		List<TestWrapper> returnList = new ArrayList<TestWrapper>(); 
		try{
			List<TestCase> testCaseList = new ArrayList();  
			List<DetailDesign> detailDesignList = new ArrayList(); 
			String sqlDetailDesign ="";
			if(StringUtils.hasText(moduleId)){
				sqlDetailDesign ="  select * from project_detail_design  r  where r.module_id="+moduleId ;	
			} 			
			logger.info("  #### getAllWrapByModuleId  sqlDetailDesign sql:"+sqlDetailDesign);
			detailDesignList = this.jdbcTemplate.query(	sqlDetailDesign,new DetailDesignMapper());	 			
			if(detailDesignList!=null&&detailDesignList.size()>0){	 
				for(DetailDesign detailTmp:detailDesignList){	
					TestWrapper testWrapper = new TestWrapper();
					testWrapper.setDetailDesign(detailTmp);
					moduleId = detailTmp.getModuleId()+"";
					String detailDesignId = detailTmp.getDetailDesignId()+"";
					logger.info(" ## Get Testcase by Module Id :"+moduleId);
					String sqlTestCase  ="  select * from project_testcase r  where r.detail_design_id="+detailDesignId +" and r.module_id="+moduleId ;	
					logger.info("  sqlTestCase:"+sqlTestCase);
					testCaseList = this.jdbcTemplate.query(	sqlTestCase,new TestCaseMapper());	 
					testWrapper.setTestCaseList(testCaseList);
					returnList.add(testWrapper);
				}
			}			 
			
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}	
	 
	
	public TestCase getById(String id) {
		logger.info(" #getById id: "+id);
		 TestCase  returnVal = null; 		 
		try{
			String sql ="";
			if(StringUtils.hasText(id)){
				sql ="  select * from project_testcase r  where r.testcase_id="+id ;	
				 returnVal = this.jdbcTemplate.queryForObject(	sql,new TestCaseMapper());
			} 		
			List<TestCaseDetail> testCaseDetailList = new ArrayList();
			if(returnVal!=null){				
				// Get TestCase Detail 				
				sql ="  select * from project_testcase_detail r  where r.testcase_id="+id ;	
				testCaseDetailList = this.jdbcTemplate.query(	sql,new TestCaseDetailMapper());				
				returnVal.setTestcaseDetailList(testCaseDetailList);
			}
		 
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnVal;
	}
		
	
	 
	private class TestCaseMapper implements RowMapper<TestCase> {   						
        @Override
		public TestCase mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TestCase domain = new TestCase();
        	domain.setTestcaseId(rs.getLong("testcase_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setSummary(rs.getString("summary")); 
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setModuleId(rs.getLong("module_id")); 
    
		return domain;
		} 
    } 
	
	@Override
	public TestCase prepareTestCaseByDetailDesign(String moduleId,String id) {		 
		TestCase testcase = new TestCase();
		
		Module module = this.jdbcTemplate.queryForObject(
				"select *  from project_module where module_id = ?",
				new Object[]{moduleId},new ModuleMapper());	
		
		
		DetailDesign detailDesign = this.jdbcTemplate.queryForObject(
				"select *  from project_detail_design where detail_design_id = ?",
				new Object[]{id},new DetailDesignMapper());	
				
		testcase.setModuleId(module.getModuleId());
		testcase.setModuleName(module.getName());
		testcase.setDetailDesignId(detailDesign.getDetailDesignId());
		testcase.setName(detailDesign.getName());
		
		logger.info(" prepareTestCaseByDetailDesign:"+BeanUtils.getBeanString(testcase));
		return testcase;
	}		
	
	
	
	@Override
	public void  create(TestCase testcase) {
		logger.info(" #testcaseDaoImpl.create  testcase# "+ BeanUtils.getBeanString(testcase));		
		final String code = projectUtil.getTestCaseNo(testcase.getProjectId());		
		final TestCase finaltestcase = testcase;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_testcase (code, name,summary, module_id,project_id,detail_design_id ) values (?, ?,?,?,? ,? )" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,code);
				ps.setString(2,finaltestcase.getName());
				ps.setString(3,finaltestcase.getSummary()); 
				ps.setLong(4,finaltestcase.getModuleId());
				ps.setLong(5,finaltestcase.getProjectId());
				ps.setLong(6,finaltestcase.getDetailDesignId());
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}
	
	
	private class TestCaseDetailMapper implements RowMapper<TestCaseDetail> {   						
        @Override
		public TestCaseDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TestCaseDetail domain = new TestCaseDetail();
        	domain.setTestcaseDetailId(rs.getLong("testcase_detail_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));        	 
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setModuleId(rs.getLong("module_id")); 
    
		return domain;
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
