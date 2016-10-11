package com.buckwa.dao.impl.admin;

import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.admin.InitialSystemDao;

@Repository("initialSystemDao")
public class InitialSystemDaoImal implements InitialSystemDao {


	@Override
	public boolean initialSystem() throws Exception {
		boolean result = false;
		try{
			//Resource resource = new ClassPathResource("initialSystem.sql"); 
			//SimpleJdbcTestUtils.executeSqlScript(simpleJdbcTemplate, resource, true); 
			result =true;
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}
	
	
	 
}
