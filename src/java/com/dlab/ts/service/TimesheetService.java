package com.dlab.ts.service;

import com.dlab.spring.web.util.ServiceResult;
import java.util.Map;

public interface TimesheetService {

    public ServiceResult getAllTimesheets(String managerId);
    public ServiceResult getUsersTimesheets(Map<String,Object> param);
    public ServiceResult getTimesheetLog(String tsid);
    public ServiceResult getTimesheet(String id);

    public ServiceResult updateUserTimeSheet(String id, int status,Map<String, Object> param);
    public ServiceResult generateWeeklyTimesheet();
    public ServiceResult getTSForAcctntOrForUser(String id, String userId);
    public ServiceResult getManagerTimesheets(Map<String, Object> param);

    public void autoApprove();
 
}
