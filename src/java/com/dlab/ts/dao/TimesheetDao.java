/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.dao;

import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.dao.AbstractSimpleDao;
import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("timesheetDao")
public class TimesheetDao extends AbstractSimpleDao implements InitializingBean {
    final static Logger logger = LoggerFactory.getLogger(ProjectDao.class);
    @Autowired
    UserSessionBean userSessionBean;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Inside TimesheetDao Dao");
        System.out.println("Generic Dao:" + this.jdbcTemplate);
        System.out.println("sqlQueries:" + this.sqlQueries);
    }

    public int addTimeSheet(String tsId, int status, List<Map<String, Object>> rows) {
        return 1;
    }

    public int updateTimeSheet(String tsId, int status, Map<String, Object> timeheet) {
        System.out.println("Param" + timeheet);
        String query1 = this.sqlQueries.getProperty("UPDATE_TIMESHEET");
        System.out.println("query1:" + query1);
        this.jdbcTemplate.update(query1, new Object[]{status, tsId});

        int newStatus = (Integer) timeheet.get("newStatus");
        int oldStatus = (Integer) timeheet.get("oldStatus");
        String vId = (String) timeheet.get("vId");
        int ver = (Integer) timeheet.get("version");
        List param = (List) timeheet.get("timesheet");
        if (oldStatus == 4 || oldStatus == 0 || oldStatus==2 || oldStatus==3) { //approve, reject,withdraw,new save, frm withdraw to save
            vId = addTimeSheetLog(ver,tsId,oldStatus,newStatus,timeheet.get("comment"));
            
            if (oldStatus == 0 || oldStatus==2 || oldStatus==3) { //new save, submit// save, submit frm withdraw// from revert back to save or submit
                // add
                String query2 = this.sqlQueries.getProperty("ADD_TIMESHEET_DETAIL");
                System.out.println("query2:" + query2);
                for (int i = 0; i < param.size(); i++) {
                     Map<String, Object> p =(Map)param.get(i);
                     p.put("tsid",vId);
                     System.out.println("param.get(i):" + p);
                    this.jdbcTemplate.update(query2, p);
                }
            }
        } else if (oldStatus == 1) { //save again, submit
            String query3 = this.sqlQueries.getProperty("UPDATE_TIMESHEET_LOG");
            System.out.println("query3:" + query3);
            this.jdbcTemplate.update(query3, new Object[]{ver, userSessionBean.getUserId(),
                        System.currentTimeMillis(), oldStatus, newStatus, timeheet.get("comment"), vId});

            //delete and add
            this.jdbcTemplate.update(this.sqlQueries.getProperty("DEL_TIMESHEET_DETAIL"), vId);
            String query2 = this.sqlQueries.getProperty("ADD_TIMESHEET_DETAIL");
            System.out.println("query2:" + query2);
            for (int i = 0; i < param.size(); i++) {
                   Map<String, Object> p =(Map)param.get(i);
                    p.put("tsid",vId);
                    System.out.println("param.get(i):" + p);
                    this.jdbcTemplate.update(query2, p);
            }
        }
            
        
        return 1;
    }
     public List getManagersTimesheets(Map<String,Object> param) {
        String query = this.sqlQueries.getProperty("GET_MGR_TIMESHEET");
       
        if(param.containsKey("status") && !param.get("status").equals("")){
            query = query+" and status=:status";
        }
        if(param.containsKey("fromdate") && !param.get("fromdate").equals("")){
            query = query+" and fromdate>=:fromdate";
        }
        if(param.containsKey("todate") && !param.get("todate").equals("")){
            query = query+" and todate<=:todate";
        }
         System.out.println("query:" + query);
        return this.jdbcTemplate.queryForList(query, param);
    }
     
    public List getUsersTimesheets(Map<String,Object> param) {
        
        String query = this.sqlQueries.getProperty("GET_USER_TIMESHEET");
       
        if(param.containsKey("status") && !param.get("status").equals("")){
            query = query+" and status=:status";
        }
        if(param.containsKey("fromdate") && !param.get("fromdate").equals("")){
            query = query+" and fromdate>=:fromdate";
        }
        if(param.containsKey("todate") && !param.get("todate").equals("")){
            query = query+" and todate<=:todate";
        }
         System.out.println("query:" + query);
        return this.jdbcTemplate.queryForList(query, param);
    }

    public List getTimesheetLog(String tsid) {
        String query = this.sqlQueries.getProperty("GET_TIMESHEET_LOG");
        System.out.println("query:" + query);
        List logs = this.jdbcTemplate.queryForList(query, tsid);
        String query1 = this.sqlQueries.getProperty("GET_TIMESHEET_DETAIL");
        for (int i = 0; i < logs.size(); i++) {
            Map record = (Map) logs.get(i);
            String id = (String) record.get("vId");
            List rows = this.jdbcTemplate.queryForList(query1, id);
            record.put("rows", rows);
        }
        return logs;

    }

    public int generateWeeklyTimesheet() {
     String empquery=this.sqlQueries.getProperty("GET_EMP_LIST");
     String insrttsquery=this.sqlQueries.getProperty("INSRT_TIMESHEET");
     int tot=0;
     Map<String, Object> model=new HashMap<String, Object>();
     
     Long startdate, enddate;
    
     Calendar cal = Calendar.getInstance();
     cal.setTime(new Date());
     int day=cal.getFirstDayOfWeek();
     cal.set(Calendar.DAY_OF_WEEK,day+1);
     cal.set(Calendar.HOUR_OF_DAY,12);
     cal.set(Calendar.MINUTE,15);
     cal.set(Calendar.SECOND,0);
     cal.set(Calendar.MILLISECOND,0);
     
     startdate=cal.getTimeInMillis();
     cal.add(Calendar.MINUTE,6*24*60-2); 
     enddate=cal.getTimeInMillis();
     System.out.print(startdate+"-----"+enddate);
     model.put("fromdate",startdate);
     model.put("todate",enddate);
     List rs=this.jdbcTemplate.queryForList(empquery,model);
     for(int i=0;i<rs.size();i++){
       Map<String, String> rec=(Map<String, String>) rs.get(i);
       Map<String, Object> param=new HashMap<String, Object>(); 
       String tsid=UUID.randomUUID().toString();
       param.put("id",tsid);
       param.put("userId",rec.get("userid"));
       param.put("fromDate",startdate);
       param.put("toDate",enddate);
       param.put("status",0);
       param.put("createdOn",new Date());
       param.put("createdBy","ADMIN");
       param.put("modifiedBy","ADMIN");
       if(this.jdbcTemplate.update(insrttsquery, param)==1){
          tot++;
       }
     }   
     return 0;  
    }

    
    public List getTSForAcctntOrForUser(String id, String userId) {
        String query =null;
        Map<String, String> model=new HashMap<String, String>();
        if(id!=null && userId==null){
       	   query = this.sqlQueries.getProperty("GET_ACCOUNTANT_REPORT");
           String[] parts = id.split("-");
           model.put("fromdate",parts[0]);
           model.put("todate", parts[1]);
        }   
        else{
           query = this.sqlQueries.getProperty("GET_TS_REP_FOR_USER_FOR_WEEK"); 
           String[] parts = id.split("-");
           model.put("fromdate",parts[0]);
           model.put("todate", parts[1]);
           model.put("userid", userId);
        }   
	System.out.println("query:"+query);
        
        return this.jdbcTemplate.queryForList(query,model);
    }

    public List getAllUsersTimesheetsForAdmin(Map<String, Object> param) {
        String query = this.sqlQueries.getProperty("GET_ADM_TIMESHEET");
       
        if(param.containsKey("status") && !param.get("status").equals("")){
            query = query+" and status=:status";
        }
        if(param.containsKey("fromdate") && !param.get("fromdate").equals("")){
            query = query+" and fromdate>=:fromdate";
        }
        if(param.containsKey("todate") && !param.get("todate").equals("")){
            query = query+" and todate<=:todate";
        }
         System.out.println("query:" + query);
        return this.jdbcTemplate.queryForList(query, param);
       
    }
   public List<Map<String,Object>> getUsersDetailsFromTSIDForEmailNotice(String tsid){
       Map<String,Object> obj=new HashMap<String,Object>();
       String query = this.sqlQueries.getProperty("GET_DETAILS_FROM_TSID");
       obj.put("tsid",tsid);
       return this.jdbcTemplate.queryForList(query, obj);
      
      } 


    private String addTimeSheetLog(int ver, String tsId, int oldStatus, int newStatus, Object comment) {
        String query3 = this.sqlQueries.getProperty("ADD_TIMESHEET_LOG");
        logger.debug("query3:" + query3);
        ver++;
        String vId = UUID.randomUUID().toString();
        this.jdbcTemplate.update(query3, new Object[]{vId, ver, tsId, userSessionBean.getUserId(),
                    System.currentTimeMillis(), oldStatus, newStatus, comment});
        return vId;
    }

    
}
