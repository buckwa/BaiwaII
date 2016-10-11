package com.buckwa.framework.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
 
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

 
import com.buckwa.util.BeanUtils;

@Service("smsService") 
public class SMSService {
	private static Logger logger = LoggerFactory.getLogger(SMSService.class);
	
 
	private String smsURL;		
	
 
	private String smsUserName;		
	
 
	private String smsPassword;			

 
	private String senderName;	
	 
	
	public SMSResult sendSMS (SMSDomain smsDomain){	
		SMSResult returnResult = new SMSResult();
		logger.info(" smsDomain :"+BeanUtils.getBeanString(smsDomain));
		try{			
			URL url = new URL(smsURL);
	        URLConnection urlConnection = url.openConnection();
	        urlConnection.setDoOutput(true);
	        
	        String data = "method=send" +
	        		"&username="+smsUserName+
	        		"&password="+smsPassword+
	        		"&from="+senderName+
	        		"&to="+senderName+
	        		"&message="+smsDomain.getSmsBody() ;
	        
	        logger.info(" smsURL :"+smsURL+data);
	        
	        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        String line = null;
	      //  logger.info(" URL:"+data);
	        while ((line = br.readLine()) != null) {
	           //logger.info(" Result: "+line + "\n");
	        }
	        wr.close();
	        br.close();			
	        returnResult.setStatus(SMSConstant.SUCCESS);
		}catch(Exception ex){
			returnResult.setStatus(SMSConstant.FAIL);
			ex.printStackTrace();
		}		
		return returnResult;
	}
	
	 
	public List<SMSResult> sendSMSList (List<SMSDomain> sendList){
		List<SMSResult> returnList = new ArrayList();
		try{
			for(SMSDomain tmpDomain:sendList){	
				SMSResult smsResult = new SMSResult();
				try{					
					smsResult =sendSMS(tmpDomain);
					smsResult.setStatus(SMSConstant.FAIL);
				}catch(Exception ex1){
					smsResult.setStatus(SMSConstant.FAIL);
					ex1.printStackTrace();
				}
				smsResult.setSmsDomain(tmpDomain);
				returnList.add(smsResult);
			}			
		}catch (Exception ex){
			ex.printStackTrace();
		}		
		return returnList;		
	}
}
