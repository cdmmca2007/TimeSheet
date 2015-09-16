package com.dlab.ts.service.impl;

import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.dao.TimesheetDao;
import com.dlab.ts.service.MailService;
import com.dlab.ts.service.TimesheetService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Service("timesheetService")
public class TimesheetServiceImpl implements TimesheetService{
	final static Logger logger = LoggerFactory.getLogger(TimesheetServiceImpl.class);
	@Autowired
	private TimesheetDao timesheetDao;
        @Autowired UserSessionBean userSessionBean;
        @Autowired 
        MailService mailService;        
   public ServiceResult addTimeSheet(String tsId, int status,List<Map<String,Object>> rows){
        return null;
    }
   
    @Override
    @Transactional
    public ServiceResult updateUserTimeSheet(String tsId, int status,Map<String,Object> rows){
       ServiceResult res=new ServiceResult();
        try{       
            if(timesheetDao.updateTimeSheet(tsId, status, rows)>0){
                 res.setSuccess(true);
                 if(status==4 || status==5 || status==6){
                     List rs=this.timesheetDao.getUsersDetailsFromTSIDForEmailNotice(tsId);
                     Map<String,Object> obj=new HashMap<String,Object>();
                     if(status==4 || status==5 || status==6){
                     for(int i=0;i<rs.size();i++){
                        Map record = (Map) rs.get(i);
                        obj.put("dateweek",record.get("dateweek").toString());
                        obj.put("ccname",record.get("empname").toString());
                        obj.put("ccemailid",record.get("empemailid").toString());
                        obj.put("toname",record.get("mgrname").toString());
                        obj.put("toemailid",record.get("mgremailid").toString());
                        if(status==4)
                        obj.put("status","Submitted");
                        if(status==5)
                        obj.put("status","Rejected");
                        if(status==6)
                        obj.put("status","Approved");
                        
                      }
                     }
                     this.mailService.sendTSSubmitNotificationToMgr(obj,status);
                 }
            }
        }
        catch(Exception ex){
            res.setErrorCode(501);
            ex.printStackTrace();
            logger.error("Exception in Method:updateUserTimeSheet",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult getAllTimesheets(String managerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public ServiceResult getUsersTimesheets(Map<String,Object> param) {
        logger.info("param"+param);
       ServiceResult res=new ServiceResult();
        try{   
            param.put("userId", userSessionBean.getUserId());
            res.setData(this.timesheetDao.getUsersTimesheets(param));
            res.setSuccess(true);
        }catch(Exception ex){
            logger.error("inside usertiesheet", ex);
            res.setErrorCode(501);
            logger.error("Exception in Method:getUsersTimesheets",ex);
        } 
        return res;
    }
    @Override
    @Transactional
    public ServiceResult getManagerTimesheets(Map<String,Object> param) {
       logger.info("param"+param);
       ServiceResult res=new ServiceResult();
        try{
            if(userSessionBean.getRoleId()==1){//If User is Admin the
                                               //then return timesheet All usr
                res.setData(this.timesheetDao.getAllUsersTimesheetsForAdmin(param));
                res.setSuccess(true);
            } else {//IF user is Manager then 
                    //then return timesheet of all emoplyee working under this mgr id.
                param.put("userId", userSessionBean.getUserId());
                res.setData(this.timesheetDao.getManagersTimesheets(param));
                res.setSuccess(true);
            }
           
        }catch(Exception ex){
            logger.error("inside usertiesheet", ex);
            res.setErrorCode(501);
            logger.error("Exception in Method:getManagerTimesheets",ex);
        } 
        return res;
    }
    
    @Override
    @Transactional
    public ServiceResult getTimesheet(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional
    public ServiceResult getTimesheetLog(String tsid) {
        ServiceResult res=new ServiceResult();
        try{
        res.setData(this.timesheetDao.getTimesheetLog(tsid));
         res.setSuccess(true);
        }catch(Exception ex){
            res.setErrorCode(501);
            logger.error("Exception in Method:getTimesheetLog",ex);
        } 
        return res;
    }

    @Override
    @Transactional
    public ServiceResult generateWeeklyTimesheet() {
      ServiceResult res=new ServiceResult();  
      try {
        if(this.timesheetDao.generateWeeklyTimesheet()>0){
            res.setSuccess(true);
        }}catch(Exception ex){
            res.setErrorCode(501);
            logger.error("Exception in Method:generateWeeklyTimesheet",ex);
        }
      
     return res;  
    }

    @Override
    @Transactional
    public ServiceResult getTSForAcctntOrForUser(String id, String userId) {
        ///sendTSSubmissionNotificationToAll(5);
        ServiceResult res=new ServiceResult();
        try{
        res.setData(this.timesheetDao.getTSForAcctntOrForUser(id,userId));
         res.setSuccess(true);
        }catch(Exception ex){
            res.setErrorCode(501);
            logger.error("Exception in Method:getTSForAcctntOrForUser",ex);
        } 
        return res;
    }

    @Override
    public void autoApprove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
