package com.buckwa.service.impl.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.buckwa.domain.util.BuckwaSMS;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.util.SMSService;

 
public class SMSServiceImpl  implements SMSService{
	private static Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);
	@Value("#{appProperties['smsGatewayURL']}")
	private String smsGatewayURL;
	 
      @Override
	public boolean sendSMS(List<BuckwaSMS> smsList) {    	  
    	  boolean returnResult = false;
    	  int loop = 0;
    	  try{    		      		
    		  for(BuckwaSMS smstmp : smsList ){
    			  loop ++;
    			  sendSMS(smstmp);    			  
    		  } 
    		  returnResult = true;
    		  logger.info(" Send SMS Success loop:"+loop);
    	  }catch(Exception ex) {
    		 logger.info(" Send SMS fail loop:"+loop);
    		  ex.printStackTrace();
    	  }   	  		
		return returnResult;
	}

	private boolean sendSMS (BuckwaSMS smsDomain){
    	  boolean returnResult = false;
    	  try{    		  
              String requestUrl  = smsGatewayURL +
              "username=" + URLEncoder.encode(smsDomain.getUsername(), "UTF-8") +
              "&password=" + URLEncoder.encode(smsDomain.getPassword(), "UTF-8") +
              "&recipient=" + URLEncoder.encode(smsDomain.getRecipient(), "UTF-8") +
              "&messagetype=SMS:TEXT" +
              "&messagedata=" + URLEncoder.encode(smsDomain.getMessage(), "UTF-8") +
              "&originator=" + URLEncoder.encode(smsDomain.getSender(), "UTF-8") +
              "&serviceprovider=GSMModem1" +
              "&responseformat=html";
              logger.info(" sendSMS  request :"+requestUrl);
              URL url = new URL(requestUrl);
              HttpURLConnection uc = (HttpURLConnection)url.openConnection();
              logger.info(" sendSMS response message:"+uc.getResponseMessage());
              uc.disconnect();  		  
    	  }catch(Exception ex){    		 
    		  ex.printStackTrace();
    	  }
    	  return returnResult; 	     	  
	 
	}  
	
}
