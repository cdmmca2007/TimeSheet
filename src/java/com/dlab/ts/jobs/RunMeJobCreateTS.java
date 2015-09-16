package com.dlab.ts.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RunMeJobCreateTS extends QuartzJobBean {

    final static Logger LOG = LoggerFactory.getLogger(RunMeJobCreateTS.class);
    private TimesheetTask timesheetTask;

    public void setTimesheetTask(TimesheetTask timesheetTask) {
        LOG.debug("Inside setTimesheetTask ::" + timesheetTask);
        this.timesheetTask = timesheetTask;
    }

    /**
     * This method is a job called by spring automatically on every day 12:05 AM
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        LOG.info("Create Time sheet Job scheduler started ::"+context);
        timesheetTask.createTimeSheet(context);
        LOG.info("Create Time sheet Job scheduler finished ::");
    }
}