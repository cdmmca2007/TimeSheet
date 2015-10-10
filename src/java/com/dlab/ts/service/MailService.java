/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.service;


import com.dlab.ts.model.User;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author Kamlesh
 */
public interface MailService {
    int CREATE_USER_TPL = 1, CHANGE_PASSWORD_BY_ADMIN =2,STUDENT_ABSENT_TPL =3,
    TIMESHEET_SUBMIT_NOTIFICATION=201,
    TIMESHEET_ARROVAL_NOTIFICATION=202,TIMESHEET_REJECTED_NOTIFICATION=203,
    TS_SUB_NOTIC_TO_ALL=204,TS_SUB_NOTIC_TO_DEFALUTER=205,TS_APPROVE_ALERT_MGR=301 , ADD_BUG_NOTIFICATION=1001;  
    
    
    void sendCreateUserNotification(Map<String, Object> obj);
    void sendAdminChangePasswordNotification(Map<String, Object> obj);
    void sendTSSubmitNotificationToMgr(Map<String, Object> obj,int status);
    public void sendLinkOnForgotPassword(Connection conn,String name,String emailid,String pass);
    void sendTSSubmissionNotificationToAll(String[] obj,Calendar cal);

    public void sendTsApproveNotificationToAll(String[] obj, Calendar cal);

    public void sendNewBugNotification(Map<String, Object> sue);
}
