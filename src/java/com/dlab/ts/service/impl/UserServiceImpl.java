package com.dlab.ts.service.impl;

import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.dao.GenericDao;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.dao.ProjectDao;
import com.dlab.ts.dao.UserDao;
import com.dlab.ts.model.SessionUser;
import com.dlab.ts.model.User;
import com.dlab.ts.service.MailService;
import com.dlab.ts.service.ProjectService;
import com.dlab.ts.service.UserService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("userService")
public class UserServiceImpl implements UserService{
	final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);	
	@Autowired
	private UserDao userDao;
        @Autowired
	private ProjectService projectService;
        @Autowired
	private ProjectDao projectDao;
        
        @Autowired
        UserSessionBean userSessionBean;
        @Autowired 
        MailService mailService;
	
        
    @Override
    @Transactional
    public ModelServiceResult<UserSessionBean> validateUser(String uid, String pass) {
        ModelServiceResult<UserSessionBean> res=new ModelServiceResult<UserSessionBean>();
        try{        
            res.setData(this.userDao.validateUser(uid, pass));
            res.setSuccess(true);
        }
        catch(Exception ex){
            res.setErrorCode(403);
            ex.printStackTrace();
            logger.error("Exception in User Validation Method:validateUser",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult getAllUsers() {
        ServiceResult<Map<String,Object>> res=new ServiceResult<Map<String,Object>>();
        try{        
            res.setData(this.userDao.getAllUsers());
        }
        catch(Exception ex){
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:getAllUsers",ex);
        }
        return res;
    }

    
    @Override
    @Transactional
    public ServiceResult addUser(Map<String, Object> user) {
        ServiceResult res=new ServiceResult();
        try{
            String uid = UUID.randomUUID().toString();
            user.put("userId", uid);
            user.put("createdOn",new Date());
            user.put("createdBy",userSessionBean.getUserId());//Need to set this column
            if(user.get("empno")==null || user.get("empno").equals(""))
               user.put("empno",this.userDao.getMaxEmpNo()); 
            if(this.userDao.addUser(user)==0)
              res.setSuccess(false);
            else{
            this.mailService.sendCreateUserNotification(user);
            res.setSuccess(true);
            }
        }
        catch(Exception ex){
            res.setErrorCode(501);
            ex.printStackTrace();
            logger.error("Exception in Method:addUser",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult editUser(Map<String, Object> user) {
        ServiceResult res=new ServiceResult();
        try {
        this.userDao.editUser(user);
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:editUser",ex);
        }
        return res;
    }
    
    
    @Override
    @Transactional
    public ServiceResult deleteUser(String userId,String actionBy) {
       ServiceResult res=new ServiceResult();
       try {
       this.userDao.deleteUser(userId,actionBy);
       }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:deleteUser",ex);
        }
        return res;
    }
    @Override
    @Transactional
    public ServiceResult disableUser(String userId,String actionBy) {
       ServiceResult res=new ServiceResult();
       try {
       this.userDao.disableUser(userId,actionBy);
       }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:disableUser",ex);
        }
       return res;
    }
    @Override
    @Transactional
    public ServiceResult changePassword(Map<String, Object> obj){
        ServiceResult res=new ServiceResult();
        try {
        obj.put("passwordchangedby",this.userSessionBean.getUserId());
        obj.put("userid",this.userSessionBean.getUserId());
        
        if(this.userDao.changePassword(obj)==1)
           res.setSuccess(true);
        else
           res.setSuccess(false); 
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:changePassword",ex);
        }
        return res;
    }
    @Override
    @Transactional
    public ServiceResult adminChangePassword(Map<String,Object> obj){
        ServiceResult res=new ServiceResult();
        try {
        obj.put("passwordchangedon",new Date());
        obj.put("passwordchangedby",this.userSessionBean.getUserId());// Need to set this column
        this.userDao.adminChangePassword(obj);
        List userdet=this.userDao.getUserDetailForEmailTemp(obj);
        for(int i=0;i<userdet.size();i++){
            Map record = (Map) userdet.get(i);
            obj.put("empname",record.get("empname").toString());
            obj.put("emailId",record.get("emailid").toString());
        }
        this.mailService.sendAdminChangePasswordNotification(obj);
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:adminChangePassword",ex);
        }
        return res;
    
    }

    @Override
    @Transactional
    public ModelServiceResult getUserFullDetails(String userId) {
        ModelServiceResult<SessionUser> res=new ModelServiceResult<SessionUser>();
        try{
            List<Map<String,Object>> projects =null;
            if(this.userSessionBean.getRoleId()==4 || this.userSessionBean.getRoleId()==3){
                projects = this.projectDao.getUserProject(userId);
            } else if(this.userSessionBean.getRoleId()==3){
                projects = this.projectDao.getMangersProject(userId);
            } else{
                projects = this.userDao.getAllProject();
            }
            for(int i=0;i<projects.size();i++){
                Map  proj = projects.get(i);
                List activity=this.projectService.getProjectActivity(proj.get("projectId").toString()).getData();
                proj.put("activity", activity);
            }
            SessionUser su = new SessionUser();
            su.setProjects(projects);
            su.setUserId(this.userSessionBean.getUserId());
            su.setRole(this.userSessionBean.getRoleId());
            su.setName(this.userSessionBean.getlName()+" ,"+this.userSessionBean.getfName());
            res.setData(su);
            res.setSuccess(true);
        }
        catch(Exception ex){
            ex.printStackTrace();
            res.setSuccess(false);
            res.setErrorCode(501);
            logger.error("Exception in Method:getUserFullDetails",ex);
        }
        return res;
    }

    @Override
    @Transactional
    public ServiceResult signOut(HttpServletRequest request) {
        ServiceResult res=new ServiceResult();
        try {
        request.getSession(true).invalidate();
        res.setSuccess(true);
        }catch (Exception ex) {
            ex.printStackTrace();
            res.setErrorCode(501);
            logger.error("Exception in Method:signOut",ex);
        }
        return res;
    }
     
}
