package com.dlab.ts.controller;

//import com.dlabs.audittrail.AuditManager;
//import com.dlabs.config.Property;
//import com.dlabs.dao.DAO;

//import com.dlabs.session.RequestHandler;
import com.dlab.constants.URLMap;
import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.service.UserService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Kamlesh Kumar Sah
 */
@Controller
public class UserController {
    
  
    
    @Autowired
    private UserService userService;
    //@Autowired MailService mailService;
    @Autowired UserSessionBean userSessionBean;
   // @Autowired AuditTrailService auditTrailService;


//    @RequestMapping(value=URLMap.GET_ROLES, method= RequestMethod.GET)
//    @ResponseBody
//    public JsonResult<UserRole> getRoles(){
//        JsonResult<UserRole> result = new JsonResult<UserRole>();
//        try{
//            conn = DbPool.getConnection();
//            result.setRows(userDAO.getAllRoles(conn));
//            result.setRecords(userDAO.getCount());
//        }
//       
//        catch(Exception ex){
//            DbPool.rollback(conn);
//        }finally{
//            DbPool.close(conn);
//        }
//        return result; 
//     }
    @RequestMapping(value=URLMap.USER, method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult addUser(@RequestBody Map<String,Object> user){
        return this.userService.addUser(user); 
    }

    @RequestMapping(value=URLMap.EDIT_USER, method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult editUser(@RequestBody Map<String,Object> user){
        user.put("modifiedby",userSessionBean.getUserId());
        return this.userService.editUser(user); 
    }
    
    @RequestMapping(value=URLMap.DELETE_USER+"/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public ServiceResult deleteUser(@PathVariable("id") String userId)  {
       return this.userService.deleteUser(userId,userSessionBean.getUserId()/*send currently logged in userid*/);
   }
   @RequestMapping(value=URLMap.DISABLE_USER+"/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public ServiceResult disableUser(@PathVariable("id") String userId)  {
       return this.userService.disableUser(userId,userSessionBean.getUserId());
   }

    @RequestMapping(value=URLMap.ADMIN_CHANGE_PASSWORD, method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult adminChangePassword(@RequestBody Map<String,Object> obj){
        return this.userService.adminChangePassword(obj); 
    }
    @RequestMapping(value=URLMap.CHANGE_PASSWORD, method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult changePassword(@RequestBody Map<String,Object> obj){
        return this.userService.changePassword(obj); 
    }
   
//   @RequestMapping(value=URLMap.DISABLE_USER, method=RequestMethod.POST)
//   @ResponseBody
//    public String disableUser(@RequestParam("userId") String userId) throws ReadableException {
//       String res = "{Success:false}";
//       try{
//           conn = DbPool.getConnection();
//           if (userDAO.disableuser(conn, userId) > 0) {
//        	   res = "{Success:true}";
//           }
//          conn.commit();
//       }
//       catch(Exception ex){
//           DbPool.rollback(conn);
//       }finally{
//           DbPool.close(conn);
//       }
//       return res; 
//   } 
//    
//    @RequestMapping(value=URLMap.GET_USER, method= RequestMethod.GET)
//    @ResponseBody
//    public User getUser(HttpServletRequest req) throws ReadableException {
//        String res = "{}";
//        return AuthHandler.getUser(req);
//    }
    @RequestMapping(value=URLMap.USER, method= RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAllUser()  {
         try {
//           conn = DbPool.getConnection();
//           Paging page =Paging.getInstance(req);
//        if (request.getParameter("page") != null) {
//            page = Integer.parseInt(request.getParameter("page"));
//        }
//        if (request.getParameter("rows") != null) {
//            rows = Integer.parseInt(request.getParameter("rows"));
//        }
        //String ss = RequestHandler.getSearchString(request);
        //String orderBy = RequestHandler.getOrderBy(request);
        return this.userService.getAllUsers();
        }
       catch (Exception ex) {
        //throw new ReadableException(ex.getCause(),ex.getMessage(),"UserController", "getAllUser");
        }
       return null;
    }
    @RequestMapping(value=URLMap.USERS_FULL_DETAILS, method= RequestMethod.GET)
    @ResponseBody
    public ModelServiceResult getUserFullDetails()  {
        return this.userService.getUserFullDetails(userSessionBean.getUserId());
    }

    @RequestMapping(value=URLMap.VERIFY_USERS, method= RequestMethod.POST)
    @ResponseBody
    public ModelServiceResult verifyUser(HttpServletRequest request)  {
        ModelServiceResult<UserSessionBean> u =null;
         try {
        String uid = request.getParameter("u");
        String pass = request.getParameter("p");
        u = this.userService.validateUser(uid, pass);
        if (u.isSuccess()) {
        
            userSessionBean.setIpAddress(request.getRemoteAddr());
            userSessionBean.setUserId(u.getData().getUserId());
            userSessionBean.setUserName(u.getData().getUserName());
            userSessionBean.setEmailId(u.getData().getEmailId());
            userSessionBean.setfName(u.getData().getfName());
             userSessionBean.setmName(u.getData().getmName());
            userSessionBean.setlName(u.getData().getlName());
            userSessionBean.setRoleId(u.getData().getRoleId());  
        }
         }catch(Exception ex){
             ex.printStackTrace();
         }finally{
             
         }
        
        return u;
    }


    @RequestMapping(value=URLMap.SIGN_OUT, method= RequestMethod.POST)
    @ResponseBody
    public ServiceResult signOut(HttpServletRequest request) {
        //String uid = AuthHandler.getUserId(request);
        //AuditManager.insertLog(conn, request, 101, null);
        return this.userService.signOut(request);
    }



//    @RequestMapping(value=URLMap.UPLOAD_RPOF, method=RequestMethod.POST)
//    @ResponseBody
//    public String changeProfilePic(HttpServletRequest request,HttpServletResponse response) throws ReadableException {
//        String res = "{failure:true}";
//        UserDAO dao = new UserDAO();
//        Connection conn = null;
//        try {
//            conn = DbPool.getConnection();
//            HashMap arrmap=dao.changeProfilePic(conn,request, response);
//            if(arrmap!=null && arrmap.size()>0)
//            res = "{success:true}";
//        } catch (SQLException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return res;
//    }
//    
//    @RequestMapping(value=URLMap.GET_AUDITTRAIL, method= RequestMethod.GET)
//    @ResponseBody
//    public String getAuditLog(HttpServletRequest req) throws ReadableException {
//        JSONObject res = null;
//        Connection conn = null;
//        try {
//            conn = DbPool.getConnection();
//            Paging page =Paging.getInstance(req);
//            res = this.auditTrailService.getAuditTrail(conn,page);
//           
//        } catch (SQLException ex) {
//            DbPool.rollback(conn);
//             throw new ReadableException(ex, ex.getMessage(), UserController.class, "");
//        } finally {
//            DbPool.close(conn);
//        }
//        return res.toString();
//    }
//    
//    @RequestMapping(value=URLMap.GET_QUESTION, method=RequestMethod.GET)
//    @ResponseBody
//    public String getSecurityQuestion(HttpServletRequest request) throws ReadableException {
//        String res = "{success:'false'}";
//        UserDAO dao = new UserDAO();
//        JSONObject job = null;
//        Connection conn = null;
//        try {
//            conn = DbPool.getConnection();
//            job=dao.getSecurityQuestion(conn,request);
//            if(job != null)
//              return job.toString();
//        } catch (SQLException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return res;
//    }
//    
//    @RequestMapping(value=URLMap.FORGOT_PASSWORD, method=RequestMethod.POST)
//    @ResponseBody
//    public String forgotPassword(HttpServletRequest request,HttpServletResponse response) throws ReadableException {
//        String res = "{success:'false'}";
//        UserDAO dao = new UserDAO();
//        Connection conn = null;
//        try {
//            conn = DbPool.getConnection();
//            int str=dao.forgotPassword(conn,request, response);
//            if(str==1)
//            res = "{\"success\":true}";
//        } catch (SQLException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return res;
//    }   
//    @RequestMapping(value=URLMap.ACCOUNT_SETTING, method=RequestMethod.POST)
//    @ResponseBody
//    public String accountSetting(@RequestBody AccountSetting obj) throws ReadableException {
//        String res = "0";
//        UserDAO dao = new UserDAO();
//        Connection conn = null;
//        try {
//            conn = DbPool.getConnection();
//            int str=dao.accountSetting(conn,obj);
//            if(str==1)
//            res = "1";
//        } catch (SQLException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return res;
//    }   
//    @RequestMapping(value=URLMap.ADD_TASK_TOWORKLIST, method= RequestMethod.POST)
//    @ResponseBody
//    public MyWorkList addTaskToWorkList(@RequestBody MyWorkList obj,HttpServletRequest request) throws ReadableException {
//        String res = "{Success:false}";
//        try {
//        Connection conn=DbPool.getConnection(); 
//        UserDAO dao = new UserDAO();
//        String uid = AuthHandler.getUserId(request);
//        obj.setUserid(uid);
//        obj=dao.addTaskToWorkList(conn,obj);
//        
//        if(obj.getPid()!=null) {
//            conn.commit();
//        }}
//        catch(SQLException ex){
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return obj;
//    }
//    @RequestMapping(value=URLMap.GET_TASK_TOWORKLIST, method= RequestMethod.GET)
//    @ResponseBody
//    public String getTaskWorkList(@RequestParam("status") int status,
//                                  HttpServletRequest request){
//       UserDAO dao = new UserDAO(); 
//       try{
//           conn = DbPool.getConnection();
//           String uid = AuthHandler.getUserId(request); 
//           return dao.getTaskWorkList(conn,uid,status,0,25).toString();
//        }
//       
//        catch(Exception ex){            
//        }finally{
//            DbPool.close(conn);
//        }
//        return ""; 
//     }
//    
//    @RequestMapping(value=URLMap.DELETE_TASK_TOWORKLIST, method= RequestMethod.POST)
//    @ResponseBody
//    public MyWorkList delTaskToWorkList(@RequestBody MyWorkList obj) throws ReadableException {
//        String res = "{Success:false}";
//        try {
//        Connection conn=DbPool.getConnection(); 
//        UserDAO dao = new UserDAO();
//        //String uid = AuthHandler.getUserId(request);
//        //obj.setUserid(uid);
//        obj=dao.delTaskToWorkList(conn,obj);
//        
//        if(obj.getPid()==null) {
//            conn.commit();
//        }}
//        catch(SQLException ex){
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return obj;
//    }
//    @RequestMapping(value=URLMap.MARK_TASK_IMP, method= RequestMethod.POST)
//    @ResponseBody
//    public MyWorkList markTaskImp(@RequestBody MyWorkList obj) throws ReadableException {
//        String res = "{Success:false}";
//        try {
//        Connection conn=DbPool.getConnection(); 
//        UserDAO dao = new UserDAO();
//        //String uid = AuthHandler.getUserId(request);
//        //obj.setUserid(uid);
//        obj=dao.markTaskImp(conn,obj);
//        
//        if(obj.getResult()==1) {
//            conn.commit();
//        }}
//        catch(SQLException ex){
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return obj;
//    }
//    @RequestMapping(value=URLMap.MARK_TASK_COMPLETED, method= RequestMethod.POST)
//    @ResponseBody
//    public MyWorkList markTaskCompleted(@RequestBody MyWorkList obj) throws ReadableException {
//        String res = "{Success:false}";
//        try {
//        Connection conn=DbPool.getConnection(); 
//        UserDAO dao = new UserDAO();
//        //String uid = AuthHandler.getUserId(request);
//        //obj.setUserid(uid);
//        obj=dao.markTaskCompleted(conn,obj);
//        
//        if(obj.getResult()==1) {
//            conn.commit();
//        }}
//        catch(SQLException ex){
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return obj;
//    }

   
}

