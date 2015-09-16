/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.dao;

import com.dlab.spring.web.dao.AbstractNamedDao;
import com.dlab.spring.web.dao.AbstractSimpleDao;
import com.dlab.ts.model.Email;
import com.kjava.base.ReadableException;
//import com.mysql.jdbc.Connection;
import com.kjava.base.db.DaoUtil;
import com.kjava.base.util.ExtJsonUtil;
import com.kjava.base.util.JSONUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.*;
//import java.util.HashSet;
//import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 *
 * @author cd
 */
@Repository("notificationDao")
public class NotificationDao extends AbstractSimpleDao implements InitializingBean{
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(NotificationDao.class);
     
//     public void setDataSource(DataSource dataSource) {
//         this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
//     }
//     public void setSqlQueries(Properties sqlQueries) {
//         this.sqlQueries = sqlQueries;
//     }
    @Override
     public void afterPropertiesSet() throws Exception {
            System.out.println("Inside Notification Dao");
            System.out.println("Generic Dao:"+ this.jdbcTemplate);
            System.out.println("sqlQueries:"+ this.sqlQueries);
     }
    public List<Map<String,Object>> sendTSSubmissionNotificationToAll(Calendar cal) {
        logger.info("Calendar.DAY_OF_WEEK::"+cal.get(Calendar.DAY_OF_WEEK));
        if(cal.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY){
            /*
            If its friday then send notification to all irrespective of 
            * whether emp has submitted the timesheet or not 
            */
            String query = this.sqlQueries.getProperty("GET_USERS");
            return this.jdbcTemplate.queryForList(query,new HashMap());
        }
        else
        {
            /*
            If its not friday then send notification to them who has not 
            * submitted the timesheet.
            */
           String query = this.sqlQueries.getProperty("GET_EMAIL_ID_OFTS_DEFAULTER");
           return this.jdbcTemplate.queryForList(query,new HashMap()); 
        }    
     
    }

    public List<Map<String, Object>> sendTsApproveNotificationToAll(Calendar cal) {
            logger.info("Calendar.DAY_OF_WEEK::"+cal.get(Calendar.DAY_OF_WEEK));
            String query = this.sqlQueries.getProperty("GET_USERS");
            query=query+" where u.roleid=2";
            return this.jdbcTemplate.queryForList(query,new HashMap());
    }
}
