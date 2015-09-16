package com.dlab.ts.dao;

import com.dlab.spring.web.dao.AbstractNamedDao;
import com.dlab.spring.web.dao.AbstractSimpleDao;
import com.dlab.ts.controller.ProjectController;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository("projectDao")
public class ProjectDao extends AbstractSimpleDao implements InitializingBean{
	final static Logger logger = LoggerFactory.getLogger(ProjectDao.class);	
	@Override
	public void afterPropertiesSet() throws Exception {
            System.out.println("Inside Project Dao");
            System.out.println("Generic Dao:"+ this.jdbcTemplate);
            System.out.println("sqlQueries:"+ this.sqlQueries);
	}
	

    public Map<String,Object> addProject(Map<String,Object> project) {
        logger.debug("Paaram:"+project);
        
        String projectid=UUID.randomUUID().toString();
        String query = this.sqlQueries.getProperty("ADD_PROJ"); 
        project.put("projectId", projectid);
        logger.debug("query:"+query);      
        //logger.error("",ex);
        if(this.jdbcTemplate.update(query, project) > 0){
            String proj_mgr = this.sqlQueries.getProperty("ADD_PROJ_MEMBER");
            project.put("userId", project.get("projectManager"));
            project.put("resourceId", UUID.randomUUID().toString());
            if(this.jdbcTemplate.update(proj_mgr, project)>0)
            {
              project.put("result",1);  
            }else{
              project.put("result",0);    
            }
                
              ////Put Audit Trial Code 
              //// Send an email to PM for project allocation
              //// Map<String,String> pm_emailid=getUserEmailId(project.get("projectmanagerid").toString());

        }
        
        return project;
    }

    public Map<String,Object> updateProject(Map<String, Object> model) {
         logger.debug("Paaram:"+model);
         String query = this.sqlQueries.getProperty("EDIT_PROJ");  
         logger.debug("query:"+query);      
         if(this.jdbcTemplate.update(query, model) >  0){
         }
         return model;
    }

    public int deleteProject(Map<String, Object> model) {
        String query = this.sqlQueries.getProperty("DEL_PROJ");
        return this.jdbcTemplate.update(query, model);
    }

    public Object getProject(String id) {
        String query = this.sqlQueries.getProperty("GET_PROJECT");
        return this.jdbcTemplate.queryForList(query, new Object[]{id});
    }

    public List getAllProject(String userid,int roleid) {
        
       	String query = this.sqlQueries.getProperty("GET_PROJECTS");
        if(roleid==1){
	System.out.println("query:"+query);
        return this.jdbcTemplate.queryForList(query, new HashMap());
        }else{
        query=query+" AND p.projectmanagerid=:projectmanagerid";    
	System.out.println("query:"+query);
        Map<String, Object> model=new HashMap();
        model.put("projectmanagerid", userid);
        return this.jdbcTemplate.queryForList(query,model);
        }
    }

    public List getUserProject(String userId) {
        String query = this.sqlQueries.getProperty("GET_USER_PROJECTS");
        System.out.println("query:"+query);
	Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("endDate", System.currentTimeMillis());
        System.out.println("param:"+param.toString());
        return this.jdbcTemplate.queryForList(query,param);
    }
    public List getMangersProject(String userId) {
        String query = this.sqlQueries.getProperty("GET_MANAGERS_PROJECTS");
        logger.debug("query:"+query);
	Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("endDate", System.currentTimeMillis());
        logger.debug("param:"+param.toString());
        return this.jdbcTemplate.queryForList(query,param);
    }

    public List getProjectMember(String projId) {
        String query = this.sqlQueries.getProperty("GET_PROJ_MEMBER");
        System.out.println("query:"+query);
        return this.jdbcTemplate.queryForList(query,projId); 
    }

    public List addProjectMember(String projId, String[] members) {
//        Map<String,Object> projectmemeber = new HashMap<String,Object>();
//        String projectid=UUID.randomUUID().toString();
//        String query = this.sqlQueries.getProperty("ADD_PROJ");  
//        project.put("projectid",projectid);
//        project.put("createdby","");//need to set userid who is currently logged in
//        project.put("createdon",new Date());//need to set userid who is currently logged in
//        project.put("modifiedby","");//need to set userid who is currently logged in
        
//        if(this.jdbcTemplate.update(query, project) 
//        ADD_PROJ_MEMBER
        return null;
    }

    public int deleteProjectMember(HashMap<String,Object> member) {
        String query    = this.sqlQueries.getProperty("DEL_PROJ_RESOURCE");  
        int flag=0;
        if(this.jdbcTemplate.update(query, member) > 0){
               flag=1;
        }
        return flag;  
    }

    public List getProjectActivity(String projId) {
        String query = this.sqlQueries.getProperty("GET_PROJ_ACTIVITY");
        System.out.println("query:"+query);
	Map<String, Object> param = new HashMap<String, Object>();
        param.put("projectId", projId);
        System.out.println("param:"+param.toString());
        return this.jdbcTemplate.queryForList(query,param);
    }

    public Map<String,Object> addProjectActivity(Map<String, Object> activity) {
        String query =new String();
        logger.debug("Paaram:"+activity);
        if(Integer.parseInt(activity.get("action").toString()) > 0){
           query = this.sqlQueries.getProperty("EDIT_PROJ_ACT");  
           activity.put("preexistence",0);
        }   
        else{   
           String chkpreExistnce=this.sqlQueries.getProperty("CHK_PRE_PROJ_ACT"); 
           List obj=this.jdbcTemplate.queryForList(chkpreExistnce, activity);
           if(obj.size()==0){
           query = this.sqlQueries.getProperty("ADD_PROJ_ACT"); 
           activity.put("preexistence",0);
           }
           else
           {
             activity.put("preexistence",1);
             return activity;
           }    
        }   
        logger.debug("query:"+query);      
        if(this.jdbcTemplate.update(query, activity) > 0){
               ////Put Audit Trial Code 
        }
        return activity;  
    }

    public int deleteProjectActivity(HashMap<String,Object> activity) {
        String query    = this.sqlQueries.getProperty("DEL_PROJ_ACT");  
        String chkquery = this.sqlQueries.getProperty("CHK_PROJ_ACT");  
        int flag=0;
        List obj=this.jdbcTemplate.queryForList(chkquery, activity);
        if(obj.size()==0 ){
        if(this.jdbcTemplate.update(query, activity) > 0){
               flag=1;
        }
        }else{
           flag=2;
        }
        return flag;  

    }

    public List getProjectManager(String projId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Map<String, String> getUserEmailId(String userid) {
 
        Map<String,String> pm_email_id=new HashMap<String,String>();
        String query= this.sqlQueries.getProperty("USER_EMAIL_ID");  
        pm_email_id.put("userid", userid);
        //ResultSet rs=this.jdbcTemplate.query(query,pm_email_id);
        return pm_email_id;
    }

    public Map<String, Object> addProjectMember(Map<String, Object> model) {
        
        if(Integer.parseInt(model.get("action").toString()) > 0)
        {
        String proj_memeber_query = this.sqlQueries.getProperty("EDIT_PROJ_MEM_DETIAL");
        if(this.jdbcTemplate.update(proj_memeber_query, model)>0){
                 model.put("result",1);
        }
        }   
        else{
        String proj_memeber_query = this.sqlQueries.getProperty("ADD_PROJ_MEM_DETIAL");
        model.put("resourceId",UUID.randomUUID().toString());
        if(this.jdbcTemplate.update(proj_memeber_query, model)>0)
          model.put("result",1);
        }
        return model;
    }

    public List getProjecActivityReport(String projId) {
       	String query = this.sqlQueries.getProperty("GET_PROJECTS_ACTIVITY_REPORT");
	System.out.println("query:"+query);
        Map model=new HashMap();
        model.put("projectid", projId);
        return this.jdbcTemplate.queryForList(query, model);
    }

    public PMUReport projectPMUReportData(String projectid) {
        PMUReport obj=new PMUReport();
        String projdetquery=this.sqlQueries.getProperty("GET_PROJECT_DETAIL");
        String projactquery=this.sqlQueries.getProperty("GET_ACTIVITY_DETAILS");
        String listofdate  =this.sqlQueries.getProperty("GET_DATE_LIST");
        String activitysumdel =this.sqlQueries.getProperty("GET_SUM_DETAILS");
        long p_startfrom=0,p_enddate=0;
        Map projectdet=new HashMap();
        Map datelist=new HashMap();
        
        List<Map<String, Object>> proj=this.jdbcTemplate.queryForList(projdetquery,new Object[]{projectid});
        for (int i = 0; i < proj.size(); i++) {
            Map record = (Map) proj.get(i);
            projectdet.put("projectname",record.get("projectname").toString());
            projectdet.put("projectcode",record.get("projectcode").toString());
            projectdet.put("clientname",record.get("clientname").toString());
            projectdet.put("startdate",record.get("startdate").toString());
            p_startfrom=Long.parseLong(record.get("startdate").toString());
            projectdet.put("enddate",record.get("enddate").toString());
            p_enddate  =Long.parseLong(record.get("enddate").toString());
        }        
        List<Map<String, Object>> dl=this.jdbcTemplate.queryForList(listofdate,new Object[]{p_startfrom,p_enddate});
        int tot_week=0;
        for (int i = 0; i < dl.size(); i++) {
            Map record = (Map) dl.get(i);
            datelist.put("date_"+i,record.get("fromdate").toString());
            tot_week=i;
        } 
        List<Map<String, Object>> alrow=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> al=this.jdbcTemplate.queryForList(projactquery,new Object[]{projectid});
        for (int i = 0; i < al.size(); i++) {
            Map activitydet=new HashMap();        
            Map record = (Map) al.get(i);
            activitydet.put("activityname",record.get("activity").toString());
            activitydet.put("allotedhour",record.get("tot_hour").toString());
            String activityid=record.get("activityid").toString();
            List<Map<String, Object>> asd=this.jdbcTemplate.queryForList(activitysumdel,new Object[]{projectid,activityid,p_startfrom,p_enddate});
            
            for (int j = 0; j < asd.size(); j++) {
            Map rec = (Map) asd.get(j);
            activitydet.put("date_"+j,rec.get("tot_hour")!=null?rec.get("tot_hour").toString():0);            
            } 
            alrow.add(activitydet);
        } 
        obj.setProjdet(projectdet);
        obj.setDatelist(datelist);
        obj.setActivitysumlist(alrow);
        return obj;   
    }

    public Map<String, Object> addActivityActualProgress(Map<String, Object> activity) {
        String query =new String();
        logger.debug("Paaram:"+activity);
        activity.put("result", 0);
        query=this.sqlQueries.getProperty("INSERT_ACTUAL_ACTIVTY_PROGRESS");   
        logger.debug("query:"+query);   
        activity.put("id",UUID.randomUUID().toString());
        if(this.jdbcTemplate.update(query, activity) > 0){
          query=this.sqlQueries.getProperty("UPDATE_ACTIVTY_PROGRESS");     
          if(this.jdbcTemplate.update(query,activity) > 0)
            activity.put("result", 1);
            return activity;  
        }
        return activity;  
    }

    public ProgressReport projectProcessReportData(String projectid) {
        ProgressReport progrep=new ProgressReport();
        String projdetquery=this.sqlQueries.getProperty("GET_PROJECT_DETAIL");
        String projactquery=this.sqlQueries.getProperty("GET_PRGRESS_REPORT");
        Map projectdet=new HashMap();
        long p_startfrom=0,p_enddate=0;
        List<Map<String, Object>> proj=this.jdbcTemplate.queryForList(projdetquery,new Object[]{projectid});
        for (int i = 0; i < proj.size(); i++) {
            Map record = (Map) proj.get(i);
            projectdet.put("projectname",record.get("projectname").toString());
            projectdet.put("projectcode",record.get("projectcode").toString());
            projectdet.put("clientname",record.get("clientname").toString());
            projectdet.put("startdate",record.get("startdate").toString());
            p_startfrom=Long.parseLong(record.get("startdate").toString());
            projectdet.put("enddate",record.get("enddate").toString());
            p_enddate  =Long.parseLong(record.get("enddate").toString());
        }
        List<Map<String, Object>> alrow=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> al=this.jdbcTemplate.queryForList(projactquery,new Object[]{projectid,projectid,projectid});
        for (int i = 0; i < al.size(); i++) {
            Map activitydet=new HashMap();        
            Map record = (Map) al.get(i);
            activitydet.put("activityname",record.get("activityname").toString());
            activitydet.put("activitycode",record.get("activitycode").toString());
            activitydet.put("actualplanhour",record.get("actualplanhour").toString());
            activitydet.put("progpertsbooking",record.get("progpertsbooking").toString());
            activitydet.put("prog_per_boking_percent",record.get("prog_per_boking_percent").toString());
            activitydet.put("actualprogress",record.get("actualprogress").toString());
            activitydet.put("productivity",record.get("productivity").toString());
            alrow.add(activitydet);
        }
        progrep.setProjdet(projectdet);
        progrep.setActivitysumlist(al);
        return progrep;
    }
   
}
