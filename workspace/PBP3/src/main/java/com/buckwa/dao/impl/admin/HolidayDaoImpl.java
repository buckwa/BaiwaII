package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.HolidayDao;
import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.admin.HolidayCriteria;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaDateUtils;


@Repository("holidayDao")
public class HolidayDaoImpl implements HolidayDao{
	
	private static Logger logger = LoggerFactory.getLogger(HolidayDaoImpl.class);
	
//	private String DATE_PATTERN = "\\\\d{2}\\\\/\\\\d{2}\\\\/\\\\d{4}";
	private String DATE_PATTERN = "\\d{2}\\/\\d{2}\\/\\d{4}";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Holiday getHoliday(Holiday holiday) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void update(Holiday holiday) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #update holiday: "+BeanUtils.getBeanString(holiday));
		}
		
		final StringBuffer QUERY_UPDATE = new StringBuffer()
					.append(" update holiday set ")
					.append(" 	year_id = ?, holiday_date = ?, holiday_name = ?, holiday_desc = ?, enable = ? ")
					.append(" 	, updated_date = current_timestamp, update_by = ? ")
					.append(" where holiday_id = ? ");
		
		this.jdbcTemplate.update(QUERY_UPDATE.toString(),
				holiday.getYearId(),
				BuckWaDateUtils.parseDate(holiday.getHolidayDate()),
				holiday.getHolidayName(), 
				holiday.getHolidayDesc(),
				holiday.isEnable(),
				"hardCode-Update",
				holiday.getHolidayId()
		);
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #update holiday: "+BeanUtils.getBeanString(holiday));
		}
	}


	@Override
	public void create(Holiday holiday) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #Create holiday ");
			
			logger.info("year_id: "+holiday.getYearId());
			logger.info("holiday_date: "+holiday.getHolidayDate());
			logger.info("holiday_name: "+holiday.getHolidayName());
			logger.info("holiday_Desc: "+holiday.getHolidayDesc());
			logger.info("isEnable: "+holiday.isEnable());
		}
		
		final StringBuffer QUERY_CREATE = new StringBuffer()
			.append(" INSERT INTO holiday ")
			.append(" (year_id, holiday_date, holiday_name, holiday_desc, enable, created_date, create_by) ")
			.append(" VALUES ")
			.append(" ( ?     ,      ?      ,     ?       ,     ?       ,    ?  , current_timestamp,     ?    ) ");
		
		int holidayId = this.jdbcTemplate.update(QUERY_CREATE.toString(),
				new Object[]{
					holiday.getYearId(),
					BuckWaDateUtils.parseDate(holiday.getHolidayDate()),
					holiday.getHolidayName(),
					holiday.getHolidayDesc(),
					holiday.isEnable(),
					"hardCode-Create"
				}
		);	
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #Create holidayId: "+holidayId);
		}
	}

	@Override
	public List<Holiday> getAllHoliday() {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #getAll holiday");
		}

		List<Holiday> returnList = new ArrayList<Holiday>(); 
		final StringBuffer sql = new StringBuffer()
			.append(" select ")
			.append(" 	holiday_id, year_id, holiday_date, holiday_name, holiday_desc, enable ")
			.append(" from holiday ");	
		returnList = this.jdbcTemplate.query(sql.toString(),
			new RowMapper<Holiday>() {
				public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
					Holiday domain = new Holiday(); 					
					domain.setHolidayId(rs.getString("holiday_id"));		
					domain.setYearId(rs.getString("year_id"));		
					domain.setHolidayDate(rs.getString("holiday_date"));
					domain.setHolidayDate(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getDate("holiday_date")));
					domain.setHolidayDesc(rs.getString("holiday_desc"));
					domain.setEnable(rs.getBoolean("enable"));
					return domain;
				}
			}
		);
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #getAll holiday");
		}

		return returnList;
	}
	
	@Override
	public Holiday getHolidayById(String id) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #getHolidayById holidayId: "+id);
		}

		final StringBuffer query = new StringBuffer()
				.append(" SELECT ")
				.append(" 	holiday_id, year_id, holiday_date, holiday_name, holiday_desc, enable ")
				.append(" 	, status, created_date, create_by, updated_date, update_by ")
				.append(" FROM holiday ")
				.append(" WHERE holiday_id = ? ");
		
		Holiday holiday = this.jdbcTemplate.queryForObject(query.toString(),
			new Object[]{id},
			new RowMapper<Holiday>() {
				public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
					Holiday domain = new Holiday();
					domain.setHolidayId(rs.getString("holiday_id"));
					domain.setYearId(rs.getString("year_id"));
					domain.setHolidayDate(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getDate("holiday_date")));
					domain.setHolidayName(rs.getString("holiday_name"));		
					domain.setHolidayDesc(rs.getString("holiday_desc"));		
					domain.setEnable(rs.getBoolean("enable"));	
					domain.setStatus(rs.getString("status"));	
					domain.setCreateDate(rs.getTimestamp("created_date"));	
					domain.setCreateBy(rs.getString("create_by"));	
					domain.setUpdateDate(rs.getTimestamp("updated_date"));	
					domain.setUpdateBy(rs.getString("update_by"));	
					return domain;
				}
			}
		);
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #getHolidayById holidayId: "+id);
		}
		
		return holiday;
	}	

	
	
	@Override
	public PagingBean getAllHolidayByOffset(PagingBean pagingBean) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #getAllHoliday ");
		}
		
		/* used for search*/
		HolidayCriteria holiday = (HolidayCriteria)pagingBean.get("holiday");
		
		/* used for collect data */
 		List<Holiday> returnList = new ArrayList<Holiday>();	
 		
 		/* Query */
 		StringBuffer sqltotalIsb = new StringBuffer();
 		sqltotalIsb.append(" select count(*) as total_item  from  holiday  where 1=? \n");	
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select ")
		  .append(" 	holiday_id, holiday_date, holiday_name, holiday_desc, enable from holiday  where  1=? \n");	
		
		/* param */
		List<Object> params = new ArrayList<Object>();
 		params.add(1);
 		
 		//TODO: if got a time can change this to dynamic filter
 		if(StringUtils.hasText(holiday.getYearId())){
			sqltotalIsb.append(" and year_id = ? ");
			sb.append(" and year_id = ? ");
			params.add(holiday.getYearId());
		}
		if(StringUtils.hasText(holiday.getHolidayName())){
			sqltotalIsb.append(" and holiday_name like ? ");
			sb.append(" and holiday_name like ? ");
			params.add("%"+holiday.getHolidayName()+"%");
		}
		
		/* for Filter - min & max Date*/
		if(holiday.getIsDurationDate()!=null && holiday.getIsDurationDate().equalsIgnoreCase("true")){
			if(StringUtils.hasText(holiday.getMinDate())
					&&holiday.getMinDate().toString().matches(this.DATE_PATTERN)){
				sqltotalIsb.append(" and holiday_date >= ? ");
				sb.append(" and holiday_date >= ? ");
				params.add(BuckWaDateUtils.utilDateToSqlDate(BuckWaDateUtils.parseDate(holiday.getMinDate())));
			}
			
			if(StringUtils.hasText(holiday.getMaxDate())
					&&holiday.getMaxDate().toString().matches(this.DATE_PATTERN)){
				sqltotalIsb.append(" and holiday_date <= ? ");
				sb.append(" and holiday_date <= ? ");
				params.add(BuckWaDateUtils.utilDateToSqlDate(BuckWaDateUtils.parseDate(holiday.getMaxDate())));
			}
		}else{
			if(StringUtils.hasText(holiday.getHolidayDate())
					&&holiday.getHolidayDate().toString().matches(this.DATE_PATTERN)){
				sqltotalIsb.append(" and holiday_date = ? ");
				sb.append(" and holiday_date = ? ");
				params.add(BuckWaDateUtils.utilDateToSqlDate(BuckWaDateUtils.parseDate(holiday.getHolidayDate())));
			}
		}
		
		
		/* set order by */
		sb.append(" order by holiday_date ");
		
		if(logger.isInfoEnabled()){
			logger.info("Chk Query & param");
			logger.info("count: "+sqltotalIsb.toString());
			logger.info("query: "+sb.toString());
			
			logger.info("params(cnt): "+params.size());
			logger.info("params: "+params.toString());
			logger.info("params: "+params.toArray());
		}
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalIsb.toString()
				,params.toArray(),Integer.class
		); 
		
		pagingBean.setTotalItems(rows_totalItem);	
		
		/* setPaging follow by Size of Data */
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
	 
		returnList = this.jdbcTemplate.query(sb.toString()
			,params.toArray()
			,new RowMapper<Holiday>() {
				public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
					Holiday domain = new Holiday();					
					domain.setHolidayId(rs.getString("holiday_id"));
					domain.setHolidayDate(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getDate("holiday_date")));
					domain.setHolidayName(rs.getString("holiday_name"));	
					domain.setHolidayDesc(rs.getString("holiday_desc"));
					domain.setEnable(rs.getBoolean("enable"));			
					return domain;
				}
			}
		);
		
		pagingBean.setCurrentPageItem(returnList);

		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #getAllHoliday ");
		}
		
		return pagingBean;
	}
	
	
	@Override
	public PagingBean getAllHolidayByOffsetYear(PagingBean pagingBean) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #getAllHolidayByOffsetYear ");
		}
		
		Holiday holiday = (Holiday)pagingBean.get("holiday");
		
 		List<Holiday> returnList = new ArrayList<Holiday>();	
 		
 		/* Query */
 		StringBuffer sqltotalIsb = new StringBuffer();
 		sqltotalIsb.append(" select count(*) as total_item  from  holiday  where 1=? and year_id=? \n");	
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select ")
		  .append(" 	holiday_id, holiday_date, holiday_name, holiday_desc, enable from holiday  where  1=?  and year_id=? \n");	
		
		/* param */
		List<Object> params = new ArrayList<Object>();
 		params.add(1);
 		
 		//TODO: if got a time can change this to dynamic filter
 		if(StringUtils.hasText(holiday.getYearId())){
			params.add(holiday.getYearId());
		}
 		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalIsb.toString()
				,params.toArray(),Integer.class
		); 
		
		pagingBean.setTotalItems(rows_totalItem);	
		
		/* setPaging follow by Size of Data */
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
	 
		returnList = this.jdbcTemplate.query(sb.toString()
			,params.toArray()
			,new RowMapper<Holiday>() {
				public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
					Holiday domain = new Holiday();					
					domain.setHolidayId(rs.getString("holiday_id"));
					domain.setHolidayDate(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getDate("holiday_date")));
					domain.setHolidayName(rs.getString("holiday_name"));	
					domain.setHolidayDesc(rs.getString("holiday_desc"));
					domain.setEnable(rs.getBoolean("enable"));			
					return domain;
				}
			}
		);
		
		pagingBean.setCurrentPageItem(returnList);

		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #getAllHoliday ");
		}
		
		return pagingBean;
	}
	
	@Override
	public void deleteHolidayById(String holidayId) {	
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #deleteHolidayById ");
		}
		
		final StringBuffer query = new StringBuffer();
		query.append(" delete from holiday where holiday_id = ? ");
	
		this.jdbcTemplate.update(query.toString(), holidayId);
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #deleteHolidayById ");
		}
	}

	@Override
	public String getHolidayIdFromUniqueFields(Holiday holiday) {
		
		final String yearId = holiday.getYearId();
		final String holidayDate = holiday.getHolidayDate();
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #getHolidayIdFromUniqueFields{ year: "+yearId+", date: "+holidayDate+"} ");
		}

		final StringBuffer sql = new StringBuffer()
			.append(" select holiday_id form holiday ")
			.append(" where year_id = ? and holiday_date = ? ");
		
		String returnstr = "";
		
		returnstr = this.jdbcTemplate.queryForObject(sql.toString(),
			new Object[]{
				yearId
				,holidayDate
			},
			new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("holiday_id")	;	
					return str;
				}
			}
		);	
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #getHolidayIdFromUniqueFields{ year: "+yearId+", date: "+holidayDate+"} ");
		}
		
		return returnstr;
	}
	
	@Override
	public boolean isHolidayExist(Holiday holiday) {
		final String yearId = holiday.getYearId();
		final String holidayDate = holiday.getHolidayDate();
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #isHolidayExist{ year: "+yearId+", date: "+holidayDate+"} ");
		}
		
		boolean isExist = false;
		try{
			final StringBuffer sql= new StringBuffer()
				.append(" select count(*) ")
				.append(" from holiday ")
				.append(" where year_id = ? and	holiday_date = ? ");
			if(logger.isInfoEnabled()){
				logger.info(" sql: "+sql.toString());
			}
			
			
			
			Long found = this.jdbcTemplate.queryForObject(sql.toString(),
						new Object[]{ yearId, BuckWaDateUtils.utilDateToSqlDate(BuckWaDateUtils.parseDate(holidayDate))
						 }	
					,Long.class);
			if(found!=null && found.intValue()>0){
				isExist = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #isHolidayExist{ year: "+yearId+", date: "+holidayDate+"} ");
		}
		
		return isExist;
	}	
	
	@Override
	public List<Holiday> getHolidayByYear(String holidayId){
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", before #getHolidayByYear holiday");
		}

		List<Holiday> returnList = new ArrayList<Holiday>(); 
		final StringBuffer sql = new StringBuffer();
		sql.append(" select holiday_id, year_id, holiday_date, holiday_name, holiday_desc, enable from holiday where year_id="+holidayId);
		returnList = this.jdbcTemplate.query(sql.toString(),
			new RowMapper<Holiday>() {
				public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
					Holiday domain = new Holiday(); 					
					domain.setHolidayId(rs.getString("holiday_id"));		
					domain.setYearId(rs.getString("year_id"));		
					domain.setHolidayDate(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getDate("holiday_date")));
					domain.setHolidayName(rs.getString("holiday_name"));
					domain.setHolidayDesc(rs.getString("holiday_desc"));
					domain.setEnable(rs.getBoolean("enable"));
					return domain;
				}
			}
		);
		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString() + ", after #getHolidayByYear holiday");
		}

		return returnList;
	}
	
}
