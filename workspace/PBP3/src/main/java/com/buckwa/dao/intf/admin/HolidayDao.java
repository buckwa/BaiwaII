package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.common.PagingBean;

public interface HolidayDao {	

	public Holiday getHoliday(Holiday holiday);
	public void update(Holiday holiday);
	public void create(Holiday holiday);
	
	public List<Holiday> getAllHoliday();
	
	public Holiday getHolidayById(String holidayId);
	public PagingBean getAllHolidayByOffset(PagingBean pagingBean);
	
	public void deleteHolidayById(String holidayId) ;
	
	public String getHolidayIdFromUniqueFields(Holiday holiday);
	
	public boolean isHolidayExist(Holiday holiday);
	
	public List<Holiday> getHolidayByYear(String holidayId);
	
	public PagingBean getAllHolidayByOffsetYear(PagingBean pagingBean);
}
