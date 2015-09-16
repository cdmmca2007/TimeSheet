package com.dlab.ts.jobs;

import com.dlab.ts.service.NotificationService;
import com.dlab.ts.service.TimesheetService;
import java.util.Calendar;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class TimesheetTask {

    final static Logger LOG = LoggerFactory.getLogger(TimesheetTask.class);
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TimesheetService timesheetService;
    
//    public void setNotificationService(NotificationService notificationService){
//        this.notificationService = notificationService;
//    }
    /**
     * This method is used create timesheet of all users it will check for
     * timesheet created or not if not created it will create.
     */
    public void createTimeSheet(JobExecutionContext context) {
        LOG.info("Start create timesheet"+notificationService);
        Date date =context.getFireTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        timesheetService.generateWeeklyTimesheet();
    }

    /**
     * This method is used to send alert mail to submit timesheet on thursday 10
     * AM to all and friday 11 am for pending
     *
     */
    public void tsSubmissionAlert(JobExecutionContext context) {
         LOG.info("Start submission alert");
         Date date =context.getFireTime();
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         notificationService.sendTSSubmissionNotificationToAll(cal);
    }

    /**
     * on friday 11 for approve
     */
    public void tsApproveAlert(JobExecutionContext context) {
        LOG.info("Start approved alert");
        Date date =context.getFireTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(cal.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY)
        notificationService.sendTsApproveNotificationToAll(cal);
    }

    /**
     * friday 5 pm for auto approve
     */
    public void tsAutoApprove(JobExecutionContext context) {
        LOG.info("Start auto approve alert");
        LOG.info("Start approved alert");
        Date date =context.getFireTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
         timesheetService.autoApprove();
    }
}
