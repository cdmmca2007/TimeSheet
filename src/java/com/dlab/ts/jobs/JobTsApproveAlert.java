package com.dlab.ts.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobTsApproveAlert extends QuartzJobBean {

    final static Logger LOG = LoggerFactory.getLogger(JobTsApproveAlert.class);
    private TimesheetTask timesheetTask;

    public void setTimesheetTask(TimesheetTask timesheetTask) {
        LOG.debug("Inside setTimesheetTask ::" + timesheetTask);
        this.timesheetTask = timesheetTask;
    }

    /**
     * This method is a job called by spring automatically on friday 11 AM
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        LOG.info("Create Time sheet Job scheduler started ::");
        timesheetTask.tsApproveAlert(context);
        LOG.info("Create Time sheet Job scheduler finished ::");
    }
}