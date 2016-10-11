package com.buckwa.dao.impl.project;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.project.ProjectCommonDao;
import com.buckwa.web.controller.admin.RoleManagementController;



@Repository("projectCommonDao")
public class ProjectCommonDaoImpl implements ProjectCommonDao{
	private static Logger logger = LoggerFactory.getLogger(ProjectCommonDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	@Override
	public String getLatestBusinessRuleNo(){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_business_rule p WHERE p.business_rule_id = (SELECT MAX(business_rule_id) FROM project_business_rule)",  String.class);
			 returnStr = returnStr.replaceAll("BR", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
	
	@Override
	public String getLatestBusinessRuleNoByProjectId(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_business_rule p WHERE p.project_id ="+projectId+" and  p.business_rule_id = (SELECT MAX(business_rule_id) FROM project_business_rule t where t.project_id="+projectId+")",  String.class);
			 returnStr = returnStr.replaceAll("BR", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}	
	
	@Override
	public String getLatestActorNoByProjectId(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_actor p WHERE p.project_id ="+projectId+" and  p.actor_id = (SELECT MAX(actor_id) FROM project_actor t where t.project_id="+projectId+")",  String.class);
			 returnStr = returnStr.replaceAll("BR", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}	
	
	
	
	
	@Override
	public String getLatestUseCaseNo(String projectId){
		String returnStr ="1";
		 try{
			// String sql ="SELECT code FROM project_usecase p WHERE p.project_id ="+projectId+"  and  p.usecase_id = (SELECT MAX(usecase_id) FROM project_usecase)";
			
			StringBuffer sb= new StringBuffer();
			sb.append("  SELECT code FROM project_usecase p \n");
			
			sb.append("  WHERE p.project_id ="+projectId+" and \n");
			sb.append("   p.usecase_id = (SELECT MAX(usecase_id) \n");
			sb.append("  FROM project_usecase pu where  pu.project_id="+projectId+")\n");

			 logger.info(" ## getLatestUseCaseNo sql:"+sb.toString());
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject(sb.toString(),  String.class);
			 returnStr = returnStr.replaceAll("UC", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
	

	
	@Override
	public String getLatestModuleNo(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_module p WHERE p.project_id ="+projectId+"  and  p.module_id = (SELECT MAX(module_id) FROM project_module t where t.project_id="+projectId+")",  String.class);
			 returnStr = returnStr.replaceAll("M", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
	
	
	@Override
	public String getLatestUtilNo(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_util p WHERE p.project_id ="+projectId+"  and  p.util_id = (SELECT MAX(util_id) FROM project_util t where t.project_id="+projectId+")",  String.class);
			 returnStr = returnStr.replaceAll("U", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
		
	
	@Override
	public String getLatestMessageNo(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_message p WHERE p.project_id ="+projectId+"  and  p.message_id = (SELECT MAX(message_id) FROM project_message)",  String.class);
			 returnStr = returnStr.replaceAll("M", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
	
	
	@Override
	public String getLatestDetailDesignNo(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_detail_design p WHERE p.project_id ="+projectId+"  and p.detail_design_id = (SELECT MAX(detail_design_id) FROM project_detail_design t where t.project_id="+projectId+")",  String.class);
			 returnStr = returnStr.replaceAll("DS", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
	
	@Override
	public String getLatestTestCaseNo(String projectId){
		String returnStr ="1";
		 try{
			 returnStr  = (String) this.jdbcTemplate
			    .queryForObject("SELECT code FROM project_testcase p WHERE p.project_id ="+projectId+"  and p.testcase_id = (SELECT MAX(testcase_id) FROM project_testcase t where t.project_id="+projectId+")",  String.class);
			 returnStr = returnStr.replaceAll("TC", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}
	
	 
	@Override
	public String getLatestLabNo(){
		String returnStr ="1";
		 try{ 
			 returnStr  = (String) this.jdbcTemplate
					    .queryForObject("SELECT code FROM project_lab p WHERE  p.lab_id = (SELECT MAX(lab_id) FROM project_lab  ",  String.class);
					 returnStr = returnStr.replaceAll("LB", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}	
	 
	@Override
	public String getLabCategoryNo(){
		String returnStr ="1";
		 try{ 
			 returnStr  = (String) this.jdbcTemplate
					 .queryForObject("SELECT code FROM project_lab_category p WHERE  p.lab_category_id = (SELECT MAX(lab_category_id) FROM project_lab_category  ",  String.class);
					 returnStr = returnStr.replaceAll("LC", "");
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally{
			 if((returnStr == null) || ("".equalsIgnoreCase(returnStr)))returnStr ="1";
		 }
		return returnStr;
	}	
	 
	
	
}
