/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.constants;

/**
 *
 * @author Kamlesh the admin
 */
public interface URLMap {
   
    String VERIFY_USERS = "/verifyUser.do";
    String USERS_FULL_DETAILS = "/user/fulldetais";
    String USER = "/user";
    String ADMIN_CHANGE_PASSWORD = "/admchngpadwrd";
    String CHANGE_PASSWORD = "/chngpadwrd";
    String EDIT_USER = "/edtusr";
    String DISABLE_USER= "/disableusr";
    String DELETE_USER= "/user";
    String GET_SEC_QUESTION="/user/getquest.do";
    
    String GET_AUDITTRAIL = "/user/auditTrail.do";
    String GET_ROLES = "/user/getRoles.do";
    String SIGN_OUT = "/signOut.do";
   
    //Project
    String PROJECT = "/project";
    String USER_PROJECT="/userproject";
    String EDIT_PROJECT="/editProj";
    String PROJECT_MEMBER="/member";
    String EDIT_PROJECT_MEMBER="/editmember";
    String ADD_PROJECT_ACTIVITY="/activity";
    String EDIT_PROJECT_ACTIVITY="/editactivity";
    String ACTUAL_PROGRESS="/actualprogress";
    String PROJECT_MANAGER="/projectmanager";
    String PROJECT_ACTIVITY="/projectactivity";
    String PROJECT_ACTIVITY_REPORT="/projactvtyrep";
    //Timesheet
    String TIMESHEET = "/timesheet";
    String TIMESHEET_GETALL = "/timesheet/getall";
    String TIMESHEET_GENERATE="/timesheet/add";
    String GETALL_TIMESHEET_REPORT="/timesheet/mainreport";
    String MGR_TIMESHEET_GETALL = "manager/timesheet/getall";
      
    String ADD_EMAIL=""  ;
    String GET_EMAIL=""  ;
    String EXCEL="/download"  ; 
    String EXCEL_REPORT_1="/downloadPMU";
    String EXCEL_REPORT_3="/downloadPR";
      
}

