/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.service;


import com.dlab.spring.web.util.ServiceResult;
import java.util.Calendar;

/**
 *
 * @author Kamlesh
 */
public interface NotificationService{
    public void sendTSSubmissionNotificationToAll(Calendar cal);
    public void sendTsApproveNotificationToAll(Calendar cal);
}
