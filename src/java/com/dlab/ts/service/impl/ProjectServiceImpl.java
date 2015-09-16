package com.dlab.ts.service.impl;

import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.dao.ProjectDao;
import com.dlab.ts.service.ProjectService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("projectService")
public class ProjectServiceImpl implements ProjectService{
	final static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);	
	@Autowired
	private ProjectDao projectDao;
        @Autowired UserSessionBean userSessionBean;

    @Override
    @Transactional
    public ModelServiceResult addProject(Map<String, Object> model) {
        ModelServiceResult res = new ModelServiceResult();
        try {
            model.put("createdBy", userSessionBean.getUserId());
            model.put("createdOn", new Date());
            model.put("modifiedBy", userSessionBean.getUserId());
            Map<String,Object> resultSet = this.projectDao.addProject(model);
            res.setData(resultSet);
            res.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:addProject",ex);
        }
        return res;
        
    }

    @Override
    @Transactional
    public ServiceResult updateProject(Map<String, Object> model) {
        ServiceResult res=new ServiceResult();
        try {
        model.put("modifiedBy", userSessionBean.getUserId());
        model=this.projectDao.updateProject(model);
        res.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:updateProject",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult deleteProject(String id) {
        ServiceResult res=new ServiceResult();
        try {
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("modifiedBy", userSessionBean.getUserId());
        param.put("projectId",id);
        param.put("deleted",1);
        param.put("deletedOn",new Date());
        param.put("deletedBy",userSessionBean.getUserId());// Need to set this column
        
        if(this.projectDao.deleteProject(param)==1){
           res.setSuccess(true);
        }
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:deleteProject",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ModelServiceResult getProject(String id) {
        ModelServiceResult res=new ModelServiceResult();
        try{
             res.setData(this.projectDao.getProject(id));
             res.setSuccess(true);
        }catch(Exception ex){
            res.setErrorCode(501);
            logger.error("Exception in Method:getProject",ex);
        } 
        return res;
    }

    @Override
    @Transactional
    public ServiceResult getAllProject(String userid,int roleid) {
        ServiceResult res=new ServiceResult();
        try{
        res.setData(this.projectDao.getAllProject(userid,roleid));
         res.setSuccess(true);
        }catch(Exception ex){
            res.setErrorCode(501);
            logger.error("Exception in Method:getAllProject",ex);
        } 
        return res;
    }

    @Override
    @Transactional
    public ServiceResult getUserProject(String userId) {
        ServiceResult res=new ServiceResult();
        try {
        res.setData(this.projectDao.getUserProject(userId));
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:getUserProject",ex);
        }
        return res;
        
    }

    @Override
    @Transactional
    public ServiceResult getProjectMember(String projId) {
        ServiceResult res=new ServiceResult();
        try {
        res.setData(this.projectDao.getProjectMember(projId));
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:getProjectMember",ex);
        }
        
        return res;
    }

    @Override
    @Transactional
    public ServiceResult addProjectMember(String projId, String[] members) {
      ServiceResult res=new ServiceResult();
      try {
      res.setData(this.projectDao.addProjectMember(projId,members));
      }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:addProjectMember",ex);
      }
      return res;
    }

    @Override
    @Transactional
    public ServiceResult deleteProjectMember(String resourceId) {
        ServiceResult res=new ServiceResult();
        try {
        HashMap<String,Object> member=new HashMap<String,Object>();
        member.put("resourceId",resourceId);
        member.put("deleted",1);
        member.put("deletedOn",new Date());
        member.put("deletedBy",userSessionBean.getUserId());// Need to set this column
        int flag=this.projectDao.deleteProjectMember(member);
        if(flag==1)
           res.setErrorMessage(1);
        else
           res.setErrorMessage(0); 
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:deleteProjectMember",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult getProjectActivity(String projId) {
        ServiceResult res=new ServiceResult();
        try {
        res.setData(this.projectDao.getProjectActivity(projId));
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:getProjectActivity",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ModelServiceResult addProjectActivity(Map<String, Object> model) {
       ModelServiceResult res=new ModelServiceResult();
       //:projectId,:activityId,tot_hour,:comment,:createdBy,createdOn,modifiedBy,modifiedOn
       try {
       model.put("createdBy",userSessionBean.getUserId());
       model.put("modifiedBy",userSessionBean.getUserId());
       model.put("createdOn",new Date());
       model.put("modifiedOn",new Date());
       Map<String,Object> resultSet = this.projectDao.addProjectActivity(model);
       res.setData(resultSet);
       if(Integer.parseInt(resultSet.get("preexistence").toString())==1)
           res.setErrorMessage(1);
       res.setSuccess(true);
       }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:addProjectActivity",ex);
        }
       return res;
    }
    
    @Override
    @Transactional
    public ServiceResult addActivityActualProgress(Map<String, Object> model) {
       ServiceResult res=new ServiceResult();
       try {
       Map<String,Object> resultSet = this.projectDao.addActivityActualProgress(model);
       //res.setData("data",resultSet);
       if(Integer.parseInt(resultSet.get("result").toString())==1)
       {
           res.setErrorMessage(1);
           res.setSuccess(true);
       }else{
           res.setErrorMessage(0);
           res.setSuccess(false);
       }
           
       }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:addProjectActivity",ex);
        }
       return res;
    }
    @Override
    @Transactional
    public ServiceResult deleteProjectActivity(String projId,String activityId) {
        ServiceResult res=new ServiceResult();
        
        HashMap<String,Object> activity=new HashMap<String,Object>();
        try {
        activity.put("projectId",projId);
        activity.put("activityId",activityId);
        activity.put("deleted",1);
        activity.put("deletedOn",new Date());
        activity.put("deletedBy",userSessionBean.getUserId());// Need to set this column
        int flag=this.projectDao.deleteProjectActivity(activity);
        if(flag==0){
            res.setSuccess(false);
            res.setErrorMessage(0);
        }else{
            res.setSuccess(true);
            res.setErrorMessage(flag);
        }}catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:deleteProjectActivity",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult getProjectManager(String projId) {
        ServiceResult res=new ServiceResult();
        try {
        res.setData(this.projectDao.getProjectManager(projId));
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:getProjectManager",ex);
        }
        return res;
    }

    
    @Override
    @Transactional
    public ModelServiceResult addProjectMember(Map<String, Object> model) {
        ModelServiceResult res = new ModelServiceResult();
        try {
            model.put("createdBy", userSessionBean.getUserId());
            model.put("createdOn", new Date());
            model.put("modifiedBy", userSessionBean.getUserId());
            Map<String,Object> resultSet = this.projectDao.addProjectMember(model);
            res.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:addProjectMember",ex);
        }
        return res;

    }

    @Override
    @Transactional
    public ServiceResult getProjecActivityReport(String projId) {
        ServiceResult res=new ServiceResult();
        try {
        res.setData(this.projectDao.getProjecActivityReport(projId));
        }catch (Exception ex) {
            res.setErrorCode(501);
            logger.error("Exception in Method:getProjecActivityReport",ex);
        }
        return res;        
    }

    @Override
    public PMUReport projectPMUReport(String projectid) {
        return this.projectDao.projectPMUReportData(projectid);
    }

    @Override
    public ProgressReport projectProgressReport(String projectid) {
        return this.projectDao.projectProcessReportData(projectid);
    }

}
