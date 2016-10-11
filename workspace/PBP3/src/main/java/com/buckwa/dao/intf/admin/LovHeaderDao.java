package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.LovHeader;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.PAMConstants;

public interface LovHeaderDao {
	
 
	public void update(LovHeader lovHeader);
	public void create(LovHeader lovHeader);
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public List<LovHeader> getAll();
	public LovHeader getById(String lovHeaderId);
	public void deleteById(String lovHeaderId); 
	public boolean isAlreadyUsege(String lovHeaderId);
	public boolean isExist(String lovHeaderName,String name);
	public boolean isExistForUpdate(String code,String name,Long id);
 
	
	
	// Detail
	public void createLOVDetail(LovDetail lovDetail);	
	public boolean isDetailExist(String lovHeaderName,String name,Long id);	
	public boolean isDetailExistForUpdate(String code,String name,Long id);
	public void deleteDetailById(String lovId); 
	public LovDetail getDetailById(String id); 
	
	
	public void updateDetail(LovHeader lovHeader);
	
	public List<LovDetail> getDetailListByCode(String code);
	
 
	public List<LovDetail> 	getLovAcademicRankList( ); 
	public List<LovDetail> getLovMaxEducationList( );
}
