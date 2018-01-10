package com.buckwa.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.buckwa.domain.UserSession;
import com.buckwa.domain.pbp.PagingMessage;
import com.buckwa.domain.webboard.Message;
import com.buckwa.util.BuckWaConstants;

import baiwa.util.UserLoginUtil;

@Repository("personDetailDao")
public class PersonDetialDao {
	private static Logger logger = LoggerFactory.getLogger(PersonDetialDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public UserSession getUsername(String username) {
		// TODO Wongwithit Dav
		String sql = "SELECT p.* , b.* ,p.faculty AS faculty_code FROM person p  INNER JOIN buckwauser b ON p.email = b.USERNAME   WHERE  username = '"
				+ username + "' and enable =1 ";

		logger.info(" loadUserByUsername:" + sql);

		// UserSession userSession = null;
		UserSession userSession = (UserSession) jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper(UserSession.class));

		return userSession;
	}

	// @SuppressWarnings("unchecked")
	public List<Message> getMessageByUser(String Username, String year) {
		List<Message> messageList = new ArrayList<>();
		String sql = " SELECT a.message_id ,a.header ,a.detail,a.topic_id,a.create_by,a.create_date,a.status "
				+ " FROM (SELECT * FROM webboard_message GROUP BY topic_id ) a  INNER JOIN academic_kpi_user_mapping  b ON a.topic_id = b.kpi_user_mapping_id "
				+ " INNER JOIN person_pbp c ON  b.user_name = c.email " + " WHERE  c.email = '" + Username
				+ "' AND a.detail != ''   AND c.academic_year = '"+year+"' ORDER BY a.message_id DESC  ";

		logger.info(" GetMessageByUsername:" + sql);

		// messageList = jdbcTemplate.query(sql, new
		// BeanPropertyRowMapper(Message.class));
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			Message message = new Message();
			// message.setCode((String)row.get("message_id"));
			// message.setDescription((String)row.get("detail"));
			message.setTopicId((Long) row.get("topic_id"));
			message.setMessageId((Long) row.get("message_id"));
			message.setMessageDetail((String) row.get("detail"));
			message.setName((String) row.get("header"));
			message.setCreateBy((String) row.get("create_by"));
			message.setCreateDate((Timestamp) row.get("create_date"));
			message.setStatusMessage((Boolean) row.get("status"));

			messageList.add(message);
		}

		return messageList;

	}

	public List<Message> getMessageByKPIId(String Username, String KpiId, String year) {

		List<Message> messageList = new ArrayList<>();
		String sql = "SELECT a.topic_id , a.message_id, a.detail,a.header ,a.create_by, a.create_date,a.status "
				+ " FROM webboard_message a  INNER JOIN academic_kpi_user_mapping  b "
				+ " ON a.topic_id = b.kpi_user_mapping_id " + " INNER JOIN person_pbp c ON  b.user_name = c.email "
				// + " WHERE c.email = '"+Username+"' AND topic_id = '"+KpiId+
				// "' ORDER BY a.message_id ASC ";
				+ " WHERE  topic_id = '" + KpiId + "'  AND a.detail != ''  AND c.academic_year = '"+year+"' ORDER BY a.message_id ASC  ";

		logger.info(" GetMessageByKPIID:" + sql);

		// messageList = jdbcTemplate.query(sql, new
		// BeanPropertyRowMapper(Message.class));
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			Message message = new Message();
			// message.setCode((String)row.get("message_id"));
			// message.setDescription((String)row.get("detail"));
			message.setTopicId((Long) row.get("topic_id"));
			message.setMessageId((Long) row.get("message_id"));
			message.setMessageDetail((String) row.get("detail"));
			message.setName((String) row.get("header"));
			message.setCreateBy((String) row.get("create_by"));
			message.setCreateDate((Timestamp) row.get("create_date"));
			message.setStatusMessage((Boolean) row.get("status"));

			messageList.add(message);
		}

		return messageList;

	}

	
	public List<Message> getMessageByHead(String Username, String Department, String year) {

		List<Message> messageList = new ArrayList<>();
		String Departmentid = UserLoginUtil.getCurrentDepartmentCode();
		int count = countMessage(Department,year);
		String sql = " SELECT c.topic_id , c.message_id, c.detail,c.header ,c.create_by, c.create_date , c.status "
				+ " FROM person_pbp a LEFT JOIN academic_kpi_user_mapping b "
				+ " ON b.user_name =  a.email INNER JOIN  (SELECT * FROM webboard_message GROUP BY topic_id ) c "
				+ " ON c.topic_id = b.kpi_user_mapping_id WHERE a.department_desc = '" + Department + "' "
				+ " AND c.detail != ''  AND a.academic_year = '"+year+"'and c.create_date between '2016-09-01 00:00:00' and  '2017-09-02 00:00:00' ORDER BY  DATE(c.create_date) DESC LIMIT 0,? ";
		if (count > 20) {
			count = 20;
		}
		
		logger.info(" GetMessageByHead:" + sql + " and count :" + count + "and department Id :" + Departmentid);

		// messageList = jdbcTemplate.query(sql, new
		// BeanPropertyRowMapper(Message.class));
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { count });
		for (Map<String, Object> row : rows) {
			Message message = new Message();
			// message.setCode((String)row.get("message_id"));
			// message.setDescription((String)row.get("detail"));
			message.setTopicId((Long) row.get("topic_id"));
			message.setMessageId((Long) row.get("message_id"));
			message.setMessageDetail((String) row.get("detail"));
			message.setName((String) row.get("header"));
			message.setCreateBy((String) row.get("create_by"));
			message.setCreateDate((Timestamp) row.get("create_date"));
			message.setStatusMessage((Boolean) row.get("status"));

			messageList.add(message);
		}

		return messageList;

	}

	public int countMessage(String criteria, String year) {
		Boolean role = UserLoginUtil.isRole(BuckWaConstants.ROLE_HEAD);
		String sql = "";
		if (role) {
			sql = " SELECT COUNT(*) FROM person_pbp a LEFT JOIN academic_kpi_user_mapping b "
					+ " ON b.user_name =  a.email INNER JOIN  (SELECT * FROM webboard_message GROUP BY topic_id ) c "
					+ " ON c.topic_id = b.kpi_user_mapping_id " + " WHERE a.department_desc = '" + criteria
					+ " ' AND c.status = '1'  AND c.detail != '' AND a.academic_year = '"+year+"'  and c.create_date between '2016-09-01 00:00:00' and  '2017-09-02 00:00:00' ";
		 
		} else {
			sql = " SELECT COUNT(*)  FROM (SELECT * FROM webboard_message GROUP BY topic_id ) a  INNER JOIN academic_kpi_user_mapping  b ON a.topic_id = b.kpi_user_mapping_id "
					+ " INNER JOIN person_pbp c ON  b.user_name = c.email " + " WHERE  c.email = '" + criteria
					+ "' AND a.detail != '' AND c.academic_year = '"+year+"' and a.create_date between '2016-09-01 00:00:00' and  '2017-09-02 00:00:00' ";
		}
		logger.info(" CountMessage:" + sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);

		return count;
	}

	public List<Message> getMessageByHeadAll(PagingMessage request, String year) {

		List<Message> messageList = new ArrayList<>();
		// String Departmentid = UserLoginUtil.getCurrentDepartmentCode();
		int count = countMessage(request.getDepartmentName(),year);
		String sql = " SELECT c.topic_id , c.message_id, c.detail,c.header ,c.create_by, c.create_date ,c.status "
				+ " FROM person_pbp a LEFT JOIN academic_kpi_user_mapping b " + " ON b.user_name =  a.email "
				+ " INNER JOIN  (SELECT * FROM webboard_message GROUP BY topic_id ) c "
				+ " ON c.topic_id = b.kpi_user_mapping_id " + " WHERE a.department_desc = ? AND c.detail != ''  AND a.academic_year = '"+year+"' "
				+ " ORDER BY  DATE(c.create_date) DESC   ";

		logger.info(" GetMessageByHeadAll:" + sql + "and department Id :" + request.getDepartmentName());
		System.out.println("Start Page and EndPage :" + request.getPageStrart() + "," + request.getPageEnd());

		// messageList = jdbcTemplate.query(sql, new
		// BeanPropertyRowMapper(Message.class));
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,
				new Object[] { request.getDepartmentName() });
		for (Map<String, Object> row : rows) {
			Message message = new Message();
//			 message.setCode((String)row.get("message_id"));
//			 message.setDescription((String)row.get("detail"));
			message.setTopicId((Long) row.get("topic_id"));
			message.setMessageId((Long) row.get("message_id"));
			message.setMessageDetail((String) row.get("detail"));
			message.setName((String) row.get("header"));
			message.setCreateBy((String) row.get("create_by"));
			message.setCreateDate((Timestamp) row.get("create_date"));
			message.setStatusMessage((Boolean) row.get("status"));

			messageList.add(message);
		}

		return messageList;

	}

	public void updateFlagMessage(String messageID) {
		String sql = " UPDATE webboard_message  a SET a.status = '0' " + " WHERE a.message_id = ? ";
		jdbcTemplate.update(sql, messageID);

	}
}
