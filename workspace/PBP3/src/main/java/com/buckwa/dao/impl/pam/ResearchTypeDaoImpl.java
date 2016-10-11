package com.buckwa.dao.impl.pam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.ResearchTypeDao;
import com.buckwa.domain.pam.ResearchType;

@Repository("researchTypeDao")
public class ResearchTypeDaoImpl implements ResearchTypeDao {

	private static Logger logger = LoggerFactory.getLogger(ResearchTypeDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ResearchType> getAll() {
		List<ResearchType> returnList = new ArrayList<ResearchType>();
		String sql = "  select r.researchtype_id , r.code, r.name from research_type r  ";
		logger.info(" sql:" + sql);
		returnList = this.jdbcTemplate.query(sql, new ResearchTypeMapper());
		return returnList;
	}

	private class ResearchTypeMapper implements RowMapper<ResearchType> {
		@Override
		public ResearchType mapRow(ResultSet rs, int rowNum) throws SQLException {
			ResearchType domain = new ResearchType();
			domain.setResearchTypeId(rs.getLong("researchtype_id"));
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("name"));
			return domain;
		}
	}
}
