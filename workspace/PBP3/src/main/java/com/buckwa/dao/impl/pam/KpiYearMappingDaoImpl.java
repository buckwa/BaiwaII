package com.buckwa.dao.impl.pam;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

import com.buckwa.dao.intf.pam.KpiCategoryDao;
import com.buckwa.dao.intf.pam.KpiYearMappingDao;
import com.buckwa.dao.intf.pam.YearDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.util.BeanUtils;

 
@Repository("kpiYearMappingDao")
public class KpiYearMappingDaoImpl implements KpiYearMappingDao {
	private static Logger logger = LoggerFactory.getLogger(KpiYearMappingDaoImpl.class);

	@Autowired
	private YearDao yearDao;
	
	@Autowired
	private KpiCategoryDao kpiCategoryDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<KpiYearMapping> getAll() {
		List<KpiYearMapping> returnList = new ArrayList<KpiYearMapping>();
		String sql = " SELECT ksm.semester_id, ksm.kpicategory_id, ksm.year_id FROM kpicategory_semester_mapping ksm ";
		logger.info(" sql:" + sql);
		returnList = this.jdbcTemplate.query(sql, new KpiYearMappingMapper());
		return returnList;
	}

	@Override
	public void update(KpiYearMapping kpiYearMapping) {
		logger.info("  kpiYearMapping: " + kpiYearMapping);
		this.jdbcTemplate.update("UPDATE kpi_semester_mapping ksm SET SCORE = ? " +
				"WHERE kpi_id = ? AND semester_id = ? AND year_id = ?",
				kpiYearMapping.getScore(), 
				kpiYearMapping.getKpiId(),
				kpiYearMapping.getSemesterId(),
				kpiYearMapping.getYearId()
		);

	}
	
	@Override
	public void updateStatus(KpiYearMapping kpiYearMapping) {
		logger.info("  kpiYearMapping: " + kpiYearMapping);
		this.jdbcTemplate.update("UPDATE kpi_semester_mapping ksm SET STATUS = ? WHERE kpi_id = ? AND semester_id = ? AND year_id = ? AND STATUS <> ? ",
				kpiYearMapping.getStatus(), 
				kpiYearMapping.getKpiId(),
				kpiYearMapping.getSemesterId(),
				kpiYearMapping.getYearId(),
				kpiYearMapping.getStatus()
		);
		
	}

	@Override
	public Long create(KpiYearMapping kpiYearMapping) {
		logger.info("  kpiYearMapping : "+BeanUtils.getBeanString(kpiYearMapping));
		final KpiYearMapping finalKpiYearMapping = kpiYearMapping;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("INSERT INTO kpi_semester_mapping(id,semester_id,kpi_id,year_id,score,status) VALUES ( NULL,?,?,?,?,'A')", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalKpiYearMapping.getSemesterId());
				ps.setLong(2, finalKpiYearMapping.getKpiId());
				ps.setLong(3, finalKpiYearMapping.getYearId());
				ps.setLong(4, finalKpiYearMapping.getScore() == null? 0 : finalKpiYearMapping.getScore());
				return ps;
			}
		}, keyHolder);
		Long returnid = keyHolder.getKey().longValue();

		return returnid;
	}
	
	
	public void initialMappingByYear(List<KpiYearMapping> yearMappingList){		
		for(KpiYearMapping tmpobj:yearMappingList){
			createKpiYearMapping(tmpobj);
		}
		
	}

	
	@Override
	public Long createKpiYearMapping(KpiYearMapping kpiYearMapping) {
		logger.info("   ");

		
		
		
		
		final KpiYearMapping finalKpiYearMapping = kpiYearMapping;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(" INSERT INTO kpi_year_mapping(kpi_id,year_id,status,category_id) VALUES ( ?,?,'A',?)", 
						Statement.RETURN_GENERATED_KEYS);				 
				ps.setLong(1, finalKpiYearMapping.getKpiId());
				ps.setLong(2, finalKpiYearMapping.getYearId());
				ps.setLong(3, finalKpiYearMapping.getCategoryId());
				return ps;
			}
		}, keyHolder);
		Long returnid = keyHolder.getKey().longValue();
		
		return returnid;
 
	}
	
	

	public PagingBean getByOffset(PagingBean pagingBean) {
		KpiYearMapping kpiYearMapping = (KpiYearMapping) pagingBean.get("kpiYearMapping");
		List<KpiYearMapping> returnList = new ArrayList<KpiYearMapping>();
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from kpi_year_mapping  r \n");
		sqltotalsb.append(" where 1=1 ");
		if ( kpiYearMapping.getYearId()!=null&&kpiYearMapping.getYearId()!=0) {
			sqltotalsb.append(" and r.year_id =  "	+ kpiYearMapping.getYearId());
		}
		if ( kpiYearMapping.getCategoryId()!=null&&kpiYearMapping.getCategoryId()!=0) {
			sqltotalsb.append(" and r.category_id =  "	+ kpiYearMapping.getCategoryId());
		}
		int rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);
		StringBuffer sb = new StringBuffer();
		//sb.append(" select  *  from kpi_year_mapping r \n");
		
		sb.append(" select  r.* ,y.name as yname,k.name as kname from kpi_year_mapping r\n");
		sb.append(" left join year y on (r.year_id=y.year_id)\n");
		sb.append(" left join kpicategory k on (r.category_id=k.kpicategory_id)\n");
		 
		sb.append(" where 1=1 ");
		
		if ( kpiYearMapping.getYearId()!=null&&kpiYearMapping.getYearId()!=0) {
			sb.append(" and r.year_id =  "	+ kpiYearMapping.getYearId());
		}
		if ( kpiYearMapping.getCategoryId()!=null&&kpiYearMapping.getCategoryId()!=0) {
			sb.append(" and r.category_id =  "	+ kpiYearMapping.getCategoryId());
		}		
		
		sb.append(" LIMIT " + pagingBean.getLimitItemFrom() + ","			+ pagingBean.getLimitItemTo());
		String sql = sb.toString();
		logger.info(" sql:" + sql);
		returnList = this.jdbcTemplate.query(sql, new KpiYearMappingMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}

	@Override
	public KpiYearMapping getById(String id) {
		String sql = " select *  from kpicategory where kpicategory_id = " + id;
		KpiYearMapping kpiyearMapping = this.jdbcTemplate.queryForObject(sql,new KpiYearMappingMapper());

		return kpiyearMapping;
	}

	@Override
	public void deleteById(String id) {
		this.jdbcTemplate				.update(" delete from  kpicategory where kpicategory_id =" + id);
	}
	
	 
	

	@Override
	public boolean isAlreadyUsege(String id) {
		boolean returnValue = false;
		try {
			String sqltmp = "select count(*) as totalusage from kpicategory kpicategory inner join aumphur aumphur "
					+ "on (kpicategory.kpicategory_id = aumphur.kpicategory_id)	where kpicategory.kpicategory_id ="
					+ id;
			logger.info(" sql:" + sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if (found != null && found.intValue() > 0) {
				returnValue = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public boolean isExist(String name) {
		boolean returnValue = false;
		try {
			String sqltmp = "select count(*) as total  from kpicategory t  where t.name='"
					+ StringEscapeUtils.escapeSql(name) + "'";
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if (found != null && found.intValue() > 0) {
				returnValue = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public boolean isExistForCreate(KpiYearMapping kpiYearMapping) {
		boolean returnValue = false;
		try {
			String sqltmp = "select count(*) as total  from kpi_year_mapping  t  where t.year_id="+kpiYearMapping.getYearId()+" and t.category_id="+kpiYearMapping.getCategoryId();
			
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if (found != null && found.intValue() > 0) {
				returnValue = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	
	
	@Override
	public boolean isExistForUpdate(String name, Long id) {
		boolean returnValue = false;
		try {
			String sqltmp = "select count(*) as total  from kpicategory t  where t.name='"
					+ StringEscapeUtils.escapeSql(name) + "'  and t.kpicategory_id !=" + id;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if (found != null && found.intValue() > 0) {
				returnValue = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public List<KpiYearMapping> getBySemester(final String semester_id) {
		List<KpiYearMapping> returnList = new ArrayList<KpiYearMapping>();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ksm.semester_id, ");
		sql.append(" ksm.kpi_id, ");
		sql.append(" ksm.score kpi_score, ");
		sql.append(" ksm.year_id, ");
		sql.append(" k.name kpi_name, ");
		sql.append(" k.code kpi_code");
		sql.append(" FROM kpi_semester_mapping ksm  ");
		sql.append(" INNER JOIN kpi k ON k.kpi_id = ksm.kpi_id");
		sql.append(" WHERE ksm.semester_id = ? ");
		sql.append(" AND ksm.status = 'A' ");
		logger.info(" sql:" + sql.toString());

		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(
						sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, semester_id);
				return ps;
			}
		};

		returnList = this.jdbcTemplate.query(psc, new KpiYearMappingMapper());
		return returnList;
	}

	private class KpiYearMappingMapper implements RowMapper<KpiYearMapping> {
		@Override
		public KpiYearMapping mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			KpiYearMapping domain = new KpiYearMapping();
			domain.setKpiYearMappingId(rs.getLong("kpi_year_mapping_id"));
			domain.setKpiId(rs.getLong("kpi_id"));
			domain.setStatus(rs.getString("status"));
			domain.setYearId(rs.getLong("year_id"));
			domain.setCategoryId(rs.getLong("category_id"));
			domain.setYearName(rs.getString("yname"));
			domain.setCategoryName(rs.getString("kname"));
			return domain;
		}

	}

}
