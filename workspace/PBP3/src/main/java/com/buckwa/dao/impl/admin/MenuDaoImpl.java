package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.MenuDao;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("menuDao")
public class MenuDaoImpl implements MenuDao {
	private static Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Menu> getAll() {
		List<Menu> returnList = new ArrayList<Menu>(); 
		String sql ="  select * from buckwamenu r  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Menu>() {
				public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
					Menu domain = new Menu(); 					
					domain.setMenuId(rs.getLong("menu_id"));					 
				return domain;
				}
				});

	return returnList;
	}
	
	
	


	@Override
	public void update(Menu menu) {
		logger.info(" #MenuDaoImpl.update menu: "+BeanUtils.getBeanString(menu));
		this.jdbcTemplate.update(
				"update  buckwamenu set code=?,description=?,url=? ,name=? ,status=? where menu_id=? ",
				menu.getCode(), menu.getDesc(),menu.getUrl(),menu.getName(),menu.getStatus() ,menu.getMenuId());

	}

	@Override
	public void create(Menu menu) {
		logger.info(" #MenuDaoImpl.create # ");
		
	try{
		
		String sqlMax = " select max(order_no) as maxorderno from buckwamenu  ";
		
		logger.info("  sqlMax : "+sqlMax);
		String returnstr = this.jdbcTemplate.queryForObject(
				sqlMax,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("maxorderno")	;	
					return str;
				}
				});	
		
		logger.info("  max value : "+returnstr);
		int orderInt = 0;
		if(returnstr!=null){
			orderInt = Integer.parseInt(returnstr);
			orderInt++;
		}		
		
		this.jdbcTemplate.update(
				"insert into buckwamenu (code, description,url,name,status,order_no) values (?, ?,?,?,?,?)",
				menu.getCode(), menu.getDesc(),menu.getUrl(),menu.getName(),menu.getStatus(),orderInt);			
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	}

	
	public PagingBean getAllByOffset(PagingBean pagingBean) {
		logger.info("");
		
		List<Menu> returnList = new ArrayList<Menu>();		
		Menu menu = (Menu)pagingBean.get("menu");	
		StringBuffer totalSql  = new StringBuffer();
	 
		totalSql.append("  select count(*) as total_item  from  buckwamenu t where 1=1 ") ;		
		if(StringUtils.hasText(menu.getCode())){
			totalSql.append(" and t.code like  '%"+StringEscapeUtils.escapeSql(menu.getCode().trim())+"%'");
		}
		if(StringUtils.hasText(menu.getDesc())){
			totalSql.append(" and t.description like  '%"+StringEscapeUtils.escapeSql(menu.getDesc().trim())+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(totalSql.toString(),Integer.class); 
		
		
		pagingBean.setTotalItems(rows_totalItem);			
	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from buckwamenu r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(menu.getCode())){
			sb.append(" and r.code like  '%"+StringEscapeUtils.escapeSql(menu.getCode().trim())+"%'");
		}
		if(StringUtils.hasText(menu.getDesc())){
			sb.append(" and r.description like  '%"+StringEscapeUtils.escapeSql(menu.getDesc().trim())+"%'");
		}
		
		sb.append(" order by order_no ");
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
	 
 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Menu>() {
				public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
					Menu menu = new Menu();				 
					menu.setMenuId(rs.getLong("menu_id"));
					menu.setCode(rs.getString("code"));
					menu.setName(rs.getString("name"));
					menu.setDesc(rs.getString("description"));	
					menu.setUrl(rs.getString("url"));	
					menu.setStatus(rs.getString("status"));	
					menu.setOrderNo(rs.getString("order_no"));	
				return menu;
				}
				});
		pagingBean.setCurrentPageItem(returnList);
		return pagingBean;
	}
	
	

	@Override
	public Menu getById(String menuId) {		 
		Menu menu = this.jdbcTemplate.queryForObject(
				"select *  from buckwamenu where menu_id = ?",
				new Object[]{menuId},
				new RowMapper<Menu>() {
				public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
					Menu menu = new Menu();
					menu.setMenuId(rs.getLong("menu_id"));
					menu.setCode(rs.getString("code"));
					menu.setName(rs.getString("name"));
					menu.setDesc(rs.getString("description"));	
					menu.setUrl(rs.getString("url"));
					menu.setStatus(rs.getString("status"));	
					menu.setOrderNo(rs.getString("order_no"));	
				return menu;
				}
				});
			
		return menu;
	}	

	
	@Override
	public void deleteById(String menuId) {	 
		this.jdbcTemplate.update(" delete from  buckwamenu where menu_id ="+menuId);		
	}


	
	@Override
	public void moveDown(Menu menu) {	 
		
		int topOrder = (menu.getTopOrder()==null||menu.getTopOrder().trim().length()==0)?0:Integer.parseInt(menu.getTopOrder());
		int currentOrder = (menu.getOrderNo()==null||menu.getOrderNo().trim().length()==0)?0:Integer.parseInt(menu.getOrderNo());
		int downOrder =(menu.getDownOrder()==null||menu.getDownOrder().trim().length()==0)?0:Integer.parseInt(menu.getDownOrder());  
		
		int newcurrentOrder =currentOrder+1;;
		logger.info("  currentOrder:"+currentOrder);
		
		if(newcurrentOrder<downOrder){
			
		}else{
			this.jdbcTemplate.update(" update buckwamenu set order_no ="+currentOrder+" where menu_id ="+menu.getDownMenuId());	
		} 
		logger.info(" set current order to:"+newcurrentOrder);
		this.jdbcTemplate.update(" update buckwamenu set order_no ="+newcurrentOrder+" where menu_id ="+menu.getMenuId());	
	}

	
	@Override
	public void moveUp(Menu menu) {	 
		int topOrder = (menu.getTopOrder()==null||menu.getTopOrder().trim().length()==0)?0:Integer.parseInt(menu.getTopOrder());
		int currentOrder = (menu.getOrderNo()==null||menu.getOrderNo().trim().length()==0)?0:Integer.parseInt(menu.getOrderNo());
		int downOrder =(menu.getDownOrder()==null||menu.getDownOrder().trim().length()==0)?0:Integer.parseInt(menu.getDownOrder()); 
		
		
		if(currentOrder==0){
			logger.info("  currentOrder==0 Do nothing ");
		}else{
			int newcurrentOrder =currentOrder-1;;		
			
			if(newcurrentOrder>topOrder){
				
			}else{
				this.jdbcTemplate.update(" update buckwamenu set order_no ="+currentOrder+" where menu_id ="+menu.getTopMenuId());	
			}
			this.jdbcTemplate.update(" update buckwamenu set order_no ="+newcurrentOrder+" where menu_id ="+menu.getMenuId());				
					
		}

	}


	@Override
	public String getIdFromMenuName(String menuName) {		
		String returnstr = "";
		String sqlMenuId = " select menu_id form buckwamenu where menu_name ='"+StringEscapeUtils.escapeSql(menuName)+"'";
		 
		
		logger.info(" sqlMenuId:"+sqlMenuId);		
		returnstr = this.jdbcTemplate.queryForObject(
				menuName,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("menu_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isAlreadyUsege(String menuId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwamenu menu inner join buckwagroupmenu groupmenu " +
					"on (menu.menu_id = groupmenu.menu_id)	where menu.menu_id ="+menuId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isExist(String code,String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalMenu  from buckwamenu menu  where menu.code='"+StringEscapeUtils.escapeSql(code)+"' or  menu.name='"+StringEscapeUtils.escapeSql(name)+"'";
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
		
	@Override
	public boolean isExistForUpdate(String code,String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalMenu  from buckwamenu t  where (t.code='"+StringEscapeUtils.escapeSql(code)+"' or t.name='"+StringEscapeUtils.escapeSql(name)+"') and t.menu_id !="+id;
			logger.info("  isExistForUpdate sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
}
