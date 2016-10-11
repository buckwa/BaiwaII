package com.buckwa.dao.intf.util;

import java.util.List;

import com.buckwa.domain.mail.BuckWaMail;
import com.buckwa.domain.util.BuckwaSMS;

public interface EmailUtilDao {
	
	public void requestSendMail (List<BuckWaMail> mailList);
	 

}
