package com.buckwa.util;

import com.buckwa.domain.common.PagingBean;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 12, 2012 10:38:21 PM
 */
public class MySqlUtil {
	
	public static String genTotalCountSql(String originalSql) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(1) ");
		sql.append(" FROM ( ");
		sql.append(originalSql);
		sql.append(" ) buckwa ");
		return sql.toString();
	}
	
	
	public static String genPagingSql(String originalSql, PagingBean pagingBean) {
		StringBuilder sql = new StringBuilder(originalSql);
		sql.append(" LIMIT ");
		sql.append(pagingBean.getLimitItemFrom());
		sql.append(",");
		//sql.append(String.valueOf(pagingBean.getLimitItemTo()));
		sql.append(String.valueOf(pagingBean.getMaxPageItems()));
		return sql.toString();
	}
	
}
