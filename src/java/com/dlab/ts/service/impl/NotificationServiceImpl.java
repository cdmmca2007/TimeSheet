/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.service.impl;

import com.dlab.ts.service.MailService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.dlab.ts.dao.NotificationDao;
import com.dlab.ts.service.NotificationService;
import java.util.Calendar;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kamlesh
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    final static org.slf4j.Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    @Autowired
    private NotificationDao notificationDao;
//    private @Autowired
//    JavaMailSender mailSender;
//    private @Autowired
//    SimpleMailMessage simpleMailMessage;
//    private @Autowired
//    EmailDAO emailDAO;
    @Autowired
    MailService mailService;

    @Override
    @Transactional
    public void sendTSSubmissionNotificationToAll(Calendar cal) {
        try {
            List<Map<String, Object>> rows = this.notificationDao.sendTSSubmissionNotificationToAll(cal);
            LOG.info("Getting Users for notification ::"+rows.toString());
            String[] obj = new String[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Map record = (Map) rows.get(i);
                obj[i] = record.get("emailId").toString();
            }
            LOG.info("List of email ids::"+obj.toString());
            this.mailService.sendTSSubmissionNotificationToAll(obj, cal);
        } catch (Exception ex) {
            LOG.error("Exception in Method:sendTSSubmissionNotificationToAll", ex);
        }
    }

    @Override
    public void sendTsApproveNotificationToAll(Calendar cal) {
        try {
            List<Map<String, Object>> rows = this.notificationDao.sendTsApproveNotificationToAll(cal);
            LOG.info("Getting Users for notification ::"+rows.toString());
            String[] obj = new String[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Map record = (Map) rows.get(i);
                obj[i] = record.get("emailId").toString();
            }
            LOG.info("List of email ids::"+obj.toString());
            this.mailService.sendTsApproveNotificationToAll(obj, cal);
        } catch (Exception ex) {
            LOG.error("Exception in Method:sendTsApproveNotificationToAll", ex);
        }
    }
}
