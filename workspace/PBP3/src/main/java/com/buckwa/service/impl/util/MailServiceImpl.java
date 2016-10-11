package com.buckwa.service.impl.util;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.util.EmailUtilDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.mail.BuckWaMail;
import com.buckwa.service.intf.util.MailService;
import com.buckwa.util.BeanUtils;
 
 
@Service("mailService") 
public class MailServiceImpl implements MailService{
	private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
//	
//	@Autowired
//	private MailSender mailSender;
//	
//	
//	@Autowired
//	private EmailUtilDao utilDao;
//	
// 
//	private String emailURL;
//
//	
//	@Override
//	public boolean sendMail(BuckWaMail mail) {
//		logger.info("  sendMail()  mail:"+BeanUtils.getBeanString(mail));
//		try{
//		SimpleMailMessage message = new SimpleMailMessage();
//		logger.info(" secureCode:"+mail.getUserSecureCode()+" username:"+mail.getSendToName());
//		String url = emailURL+"userregistration/enableUser.htm?code="+mail.getUserSecureCode()+"&userName="+mail.getSendTo();		 
//	 		 
//		 
//		message.setTo(mail.getSendTo());
//		message.setFrom("chawean@gmail.com"); 
//		message.setSubject("User Registration Confirmation ");
//		StringBuffer sb= new StringBuffer();
//		sb.append(" Dear "+mail.getSendToName()+",\n\n");
//		sb.append(" Please visit the link below for activate you account. \n\n" );
//		sb.append(url+" \n\n" );
//		sb.append(" Thank you.  \n" );
//		sb.append(" Buckwa Admin. \n" );
//		message.setText(sb.toString());
//		logger.info(" mailSender:"+mailSender);
//		 
//		mailSender.send(message);
//		logger.info(" Send mail Success");
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return false;
//	}
//
//	@Override
//	public boolean confirmeNewUser(User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean confirmeResetPassword(User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean sendMailForgotPASS(BuckWaMail mail,String newPass) {		
//		logger.info("  sendMailForgotPASS : "+BeanUtils.getBeanString(mail));
//		SimpleMailMessage message = new SimpleMailMessage(); 
//		message.setTo(mail.getSendTo());
//		message.setSubject("Your New Password ");
//		StringBuffer sb= new StringBuffer();
//		sb.append(" Dear "+mail.getSendToName()+",\n\n");
//		sb.append(" Your password has been changed to:\n" );
//		sb.append(newPass+"\n\n\n" );
//		sb.append(" Sincerely,  \n" );
//		sb.append(" Carp Adnin. \n" );
//		message.setText(sb.toString());
//		mailSender.send(message);
//		logger.info(" Send mail Success");
//		return false;
//	}
//	
//	@Override
//	public boolean requestSendMail(List<BuckWaMail> mailList) {		 
//		logger.info(" requestSendMail ");
//		boolean returnval = false;		
//		try{ 
//			utilDao.requestSendMail(mailList);
//			returnval = true;
//		}catch(Exception ex){
//			ex.printStackTrace();
//		} 
//		return returnval;
//	}
//	
	
}
