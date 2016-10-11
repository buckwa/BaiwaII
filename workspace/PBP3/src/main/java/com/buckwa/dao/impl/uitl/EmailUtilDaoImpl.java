package com.buckwa.dao.impl.uitl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.util.EmailUtilDao;
import com.buckwa.domain.mail.BuckWaMail;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.util.BuckwaSMS;


@Repository("emailUtilDao")
public class EmailUtilDaoImpl implements  EmailUtilDao {

	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void requestSendMail(List<BuckWaMail> mailList) {

     try{
    	 
    	 for(BuckWaMail mailtmp: mailList){
    		 
    			final BuckWaMail finalDomain = mailtmp;
    			KeyHolder keyHolder = new GeneratedKeyHolder();      			
    			jdbcTemplate.update(new PreparedStatementCreator() {  
    				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
    					PreparedStatement ps = connection.prepareStatement("" +						
    							"  insert into buckwa_email (send_to,message) values (?,?) " +
    						 "", Statement.RETURN_GENERATED_KEYS);   
    					ps.setString(1,finalDomain.getSendTo());
    					ps.setString(2,finalDomain.getMessage());		 	 			
    					return ps;  
    					}
    				}, 	keyHolder);
    	 	
    			Long returnid =  keyHolder.getKey().longValue();	 
    		 
    	 }
    	 
     }catch(Exception ex){
    	 ex.printStackTrace();
     }
		
	}
 
 
}
