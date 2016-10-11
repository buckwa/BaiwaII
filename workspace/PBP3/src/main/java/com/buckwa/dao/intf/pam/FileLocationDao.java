package com.buckwa.dao.intf.pam;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

public interface FileLocationDao {
	
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(FileLocation  fileLocation) ;
	public void deleteFile(String fileCode);
	public FileLocation findByFileCode(String fileCode);
	
	public Long createPBPAttachFile(AcademicKPIAttachFile  academicKPIAttachFile) ;
	
	
	public void deletePBPAttachFile(String atachFileId);
	
	public AcademicKPIAttachFile findPBPAttachFile(String fileCode);
	
	public boolean updatePBPPersonPicture(String personId,String picturePath);
	
	
}
