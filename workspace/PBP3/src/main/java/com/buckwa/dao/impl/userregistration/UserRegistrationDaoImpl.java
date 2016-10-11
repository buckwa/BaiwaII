package com.buckwa.dao.impl.userregistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.userregistration.UserRegistrationDao;
import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.Address;
import com.buckwa.service.intf.CommonService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaException;
import com.buckwa.util.BuckWaUtils;


@Repository("userRegistrationDao")
public class UserRegistrationDaoImpl implements UserRegistrationDao {
	
	private static Logger logger = LoggerFactory.getLogger(UserRegistrationDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CommonService commonService;	

	
	@Override
	public void create(User user) throws Exception{		
		// Step 1 . Insert User
		 
			String sqlIsUserExist = " select count(*) as total_item  from  buckwauser where username= '"+user.getUsername()+"'" ;		 
			int  rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class); 

			if(rows_totalItem>0){
				throw new DuplicateKeyException( "");
			}else{
				
				/*
				
				final Address address = user.getAddress();			
			 
				KeyHolder keyHolder = new GeneratedKeyHolder(); 
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into address ( " +
								" 		  no" +
								" 		  ,soi" +
								" 		  ,road" +						
								" 		  ,aumpur" +
								" 		  ,province" +
								" 		  ,tel_no" +
								" 		  ,fax_no" +
								" 		  ,zip_code" +
								" 		  ,latitude" +
								" 		  ,longitude" +
								" 		  ,tumbon" +
								") " +
								" 		  values(?, ?,?,?,?,?,?,?,?,?,? )" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,address.getNo());
						ps.setString(2,address.getSoi());
						ps.setString(3,address.getRoad());
						ps.setString(4,address.getAumpur()	);			 
						ps.setString(5,address.getProvince());
						ps.setString(6,address.getTelNo());
						ps.setString(7,address.getFaxNo());
						ps.setString(8,address.getZipCode());	
						ps.setString(9,address.getLatitude());	
						ps.setString(10,address.getLongitude());	
						ps.setString(11,address.getTumbon());
						return ps;  
						}
					}, 	keyHolder);
				
				Long addressId =  keyHolder.getKey().longValue();
				
				*/
				int xx =this.jdbcTemplate.update(
						"insert into buckwauser (username, password,first_name,last_name,email,enable,secure_code) values (?, ?,?,?,?,?,? )",
						user.getUsername(), BuckWaUtils.encrypt(user.getPassword()),user.getFirst_name(),user.getLast_name(),user.getEmail(),user.isEnabled(),user.getSecureCode() );				
				String []groupIdArray = user.getGroups();	
				logger.info(" groupIdArray:"+groupIdArray);
				if(groupIdArray!=null&&groupIdArray.length>0){			
					//Step 2.  Create User - Group Mapping			
					for(int i=0;i<groupIdArray.length;i++){
						String groupIdStr = groupIdArray[i];	
						if(StringUtils.hasLength(groupIdStr)){
							  this.jdbcTemplate.update(
										"insert into buckwausergroup (username, group_id) values (?, ?)",
										user.getUsername(), groupIdStr);					
						}					
					}			
				}					
		 	}
 

	}
	
	
	
	@Override
	public void createUserFromUpload(String userName) throws Exception{	 
			String sqlIsUserExist = " select count(*) as total_item  from  buckwauser where username= '"+userName+"'" ;		 
			int  rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class);  
			if(rows_totalItem>0){
				//throw new DuplicateKeyException( "");
				logger.info(" ## User name :"+userName+" Aready exist , So Skip !!!");
			}else{ 
				int xx =this.jdbcTemplate.update(
						"insert into buckwauser (username, password ,enable) values (?, ?,?  )",
						userName, BuckWaUtils.encrypt("password"), 1  );	
				
				List <Group>allGroup = commonService.getAllGroup();
			 
				String []groupIdArray = new String[]{BuckWaUtils.getGroupIdFromGroupName(BuckWaConstants.GROUP_USER,allGroup) };
				logger.info(" groupIdArray:"+groupIdArray);
				if(groupIdArray!=null&&groupIdArray.length>0){			
					//Step 2.  Create User - Group Mapping			
					for(int i=0;i<groupIdArray.length;i++){
						String groupIdStr = groupIdArray[i];	
						if(StringUtils.hasLength(groupIdStr)){
							  this.jdbcTemplate.update(
										"insert into buckwausergroup (username, group_id) values (?, ?)",
										userName, groupIdStr);					
						}					
					}			
				}					
		 	} 
	}
	
	
	@Override
	public void enableUser(User user) throws BuckWaException{	 
		
 		int recordUpdate =this.jdbcTemplate.update(	" update  buckwauser set enable =1 where username=? and secure_code=? ",	
 				  user.getUsername() ,user.getSecureCode());	
 		
 		logger.info("enableUser  recordUpdate :"+recordUpdate );
 		if(!(recordUpdate>0)){ 			
 			throw new BuckWaException("E014", "");
 		}
 	}	
	
	
	
	@Override
	public User getUserByUsername(String userName) { 
		
		String sql = " select * from buckwauser where username ='"+userName+"'";		
		
		RowMapper<User> mapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User domain = new User();						 
			domain.setUsername(rs.getString("username"));
			domain.setPassword(rs.getString("password"));
			domain.setEnabled(rs.getBoolean("enable"))	;
			domain.setFirst_name(rs.getString("first_name"));
			domain.setLast_name(rs.getString("last_name"));
			domain.setAddress1(rs.getString("address1"));
			domain.setAddress2(rs.getString("address2"));
			domain.setTel_no(rs.getString("tel_no"));
			domain.setEmail(rs.getString("email"));
			domain.setAddressId(rs.getLong("address_id"));
			domain.setSignatureImagePath(rs.getString("signature_image_path"))	;	
		return domain;
		}
		};	 
		
		
		User returnUser  =  (User) jdbcTemplate.queryForObject(sql, mapper);			
		if(returnUser.getAddressId()!=null){
			Address tmpAddress = getByAddressById(returnUser.getAddressId());
			returnUser.setAddress(tmpAddress);
		}
		
		if(returnUser!=null){
			String sqlGroup = " select bsg.group_id from buckwausergroup bsg  where bsg.username ='"+userName+"'";
			logger.info(" sqlGroup:"+sqlGroup);		
			List<String> groupStrList = this.jdbcTemplate.query(
					sqlGroup,
					new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
						String groupstr = rs.getString("group_id")	;	
					return groupstr;
					}
					});		
			returnUser.setGroups(groupStrList.toArray(new String[0]));
		}
 
		return returnUser;
	}
	
	
	
	@Override
	public void update(User domain) {	
		
		// Update   
		final Address address = domain.getAddress();
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  update address set " +
						" 		  no =?" +
						" 		  ,soi =?" +
						" 		  ,road =?" +						
						" 		  ,aumpur =?" +
						" 		  ,province =?" +
						" 		  ,tel_no =?" +
						" 		  ,fax_no =?" +
						" 		  ,zip_code =?" +
						" 		  ,latitude =?" +
						" 		  ,longitude =?" +
						" 		  ,tumbon =? where address_id ="+address.getAddressId()+""						 
					  , Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,address.getNo());
				ps.setString(2,address.getSoi());
				ps.setString(3,address.getRoad());
				ps.setString(4,address.getAumpur()	);			 
				ps.setString(5,address.getProvince());
				ps.setString(6,address.getTelNo());
				ps.setString(7,address.getFaxNo());
				ps.setString(8,address.getZipCode());	
				ps.setString(9,address.getLatitude());	
				ps.setString(10,address.getLongitude());	
				ps.setString(11,address.getTumbon());
				return ps;  
				}
			}, 	keyHolder);
 				
		this.jdbcTemplate.update(
				"update  user set first_name=?, last_name=? "+
				" where username=? " ,
				domain.getFirst_name()
				,domain.getLast_name()
				,domain.getUsername()	
				 );

	}	
	
	public void updateUserRegister(User user) {	
		String sql = "UPDATE buckwauser u SET u.USERNAME=?,u.PASSWORD=?,u.first_name=?,u.last_name=?,u.email=? WHERE u.USERNAME=?";
		jdbcTemplate.update(sql, user.getUsername(),user.getPassword(),user.getFirst_name(),user.getLast_name(),user.getEmail(),user.getUsername());
	}
	
	private Address getByAddressById(Long addressId) {	
		Address addressReturn  = new Address();
		try{
			String sql ="select *  from address where address_id = ?";
			addressReturn = this.jdbcTemplate.queryForObject(	sql,new Object[]{addressId},new AddressMapper() );
		}catch(Exception ex){
			ex.printStackTrace();
		} 
		return addressReturn;
	}
	
	private class AddressMapper implements RowMapper<Address> {   						
        @Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Address domain = new Address();
        	domain.setNo(rs.getString("no"));
        	domain.setSoi(rs.getString("soi"));
        	domain.setRoad(rs.getString("road"));
        	domain.setTumbon(rs.getString("tumbon"));
        	domain.setAumpur(rs.getString("aumpur"));
        	domain.setProvince(rs.getString("province"));
        	domain.setZipCode(rs.getString("zip_code"));     
			domain.setFaxNo(rs.getString("fax_no"));
			domain.setLatitude(rs.getString("latitude"));
			domain.setLongitude(rs.getString("longitude"));
			domain.setCreateBy(rs.getString("create_by"));
			domain.setCreateDate(rs.getTimestamp("create_date"));
			domain.setUpdateBy(rs.getString("update_by"));
			domain.setUpdateDate(rs.getTimestamp("update_date"));
			domain.setTelNo(rs.getString("tel_no"));
		return domain;
		} 
    }

	@Override
	public String isEmailAreadyRegistered(String email) {
		// TODO Auto-generated method stub
		String returnVal = null;
		try{
 				String sql = " SELECT first_name FROM buckwauser  where username ='"+email+"'";
				logger.info(" isEmailAreadyRegistered:"+sql);		
				List<String> groupStrList = this.jdbcTemplate.query(
						sql,
						new RowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
							String groupstr = rs.getString("first_name")	;	
						return groupstr;
						}
						});		
				 
		 if(groupStrList!=null&&groupStrList.size()>0){
			 logger.info(" Found Email :"+email);
			 returnVal = groupStrList.get(0);
			 
		 }else{
			 logger.info(" Not Found Email :"+email);
		 }
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return returnVal;
	} 
	
	
	@Override
	public User getUserByUsernameFromBuckwa(String userName) { 
		
		String sql = " select * from buckwauser where username ='"+userName+"'";		
//		logger.info("##SQL : " + sql);
		RowMapper<User> mapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User domain = new User();						 
			domain.setUsername(rs.getString("username"));
			domain.setPassword(rs.getString("password"));
			domain.setEnabled(rs.getBoolean("enable"))	;
			domain.setFirst_name(rs.getString("first_name"));
			domain.setLast_name(rs.getString("last_name"));
			domain.setAddress1(rs.getString("address1"));
			domain.setAddress2(rs.getString("address2"));
			domain.setTel_no(rs.getString("tel_no"));
			domain.setEmail(rs.getString("email"));
			domain.setAddressId(rs.getLong("address_id"));
			domain.setSignatureImagePath(rs.getString("signature_image_path"))	;	
		return domain;
		}
		};	 
		
		User returnUser = null;
		try{
			returnUser  =  (User) jdbcTemplate.queryForObject(sql, mapper);	
		}catch(Exception e){		
		}
		return returnUser;
	}
	

}
