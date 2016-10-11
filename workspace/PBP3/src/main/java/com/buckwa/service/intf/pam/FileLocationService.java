package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

public interface FileLocationService {
	
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public Long create(FileLocation  fileLocation) ;
	public BuckWaResponse deleteFile(BuckWaRequest request);
	public FileLocation findByFileCode(String fileCode);
	public boolean checkFileNameServerExist(String fileName,String fromTable);
	
	public Long createPBPAttachFile(AcademicKPIAttachFile  academicKPIAttachFile) ;
	
	public BuckWaResponse deletePBPAttachFile(BuckWaRequest request);
	
	public AcademicKPIAttachFile   findPBPAttachFile(String id);
	
	public boolean updatePBPPersonPicture(String personId,String picturePath);
	
	 
}
