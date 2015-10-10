/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.service.impl;

import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.dao.IssueDao;
import com.dlab.ts.service.IssueService;
import com.dlab.ts.service.MailService;
import static com.dlab.ts.service.impl.UserServiceImpl.logger;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LENOVO
 */
@Service("issueServiceImplmentation")
public class IssueServiceImplmentation implements IssueService{
    
    @Autowired
    UserSessionBean userSessionBean;
    @Autowired
    IssueDao   issueDao; 
    @Autowired 
    MailService mailService;

    @Override
    public ServiceResult addBug(Map<String, Object> issue) {
        ServiceResult res=new ServiceResult();
        try{
            String uid = UUID.randomUUID().toString();
            issue.put("bugid", uid);
            issue.put("createdOn",new Date());
            issue.put("createdBy",userSessionBean.getUserId());
            issue.put("modifiedBy",userSessionBean.getUserId());
            issue.put("emailId",userSessionBean.getEmailId());
            issue.put("fname",userSessionBean.getfName());
            if(this.issueDao.addIssue(issue)==0){
              res.setSuccess(false);
            }else{
                  res.setSuccess(true);
                  this.mailService.sendNewBugNotification(issue);
            }
        }
        catch(Exception ex){
            res.setErrorCode(501);
            ex.printStackTrace();
            logger.error("Exception in Method:addBug",ex);
        }
        return res;
    }
    
}
