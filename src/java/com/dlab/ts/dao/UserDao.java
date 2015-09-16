package com.dlab.ts.dao;


//import com.dlabs.config.Property;
//import com.dlabs.dao.DAO;
//import com.dlabs.dao.DaoHelper;
//import com.dlabs.dao.MySqlQuery;
//import com.dlabs.permission.PermissionDAO;
//import com.dlabs.session.AuthHandler;
import com.dlab.secuirity.JceSha;
import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.dao.AbstractNamedDao;
import com.dlab.ts.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kamlesh Kumar Sah
 */
@Repository("userDao")
public class UserDao  extends AbstractNamedDao{
   
   
//   
//   @Autowired UserSessionBean userSessionBean;
   
   Logger LOG = Logger.getLogger(UserDao.class);
  
   public List<Map<String,Object>> getAllUsers() {
       	String query = this.sqlQueries.getProperty("GET_USERS");
	System.out.println("query:"+query);
        return this.jdbcTemplate.queryForList(query, new HashMap());
   }
   
    public int addUser(Map<String, Object> user)  {
        
        String query = this.sqlQueries.getProperty("ADD_USER");
        String luquery = this.sqlQueries.getProperty("ADD_USER_LOGIN");
        if(this.jdbcTemplate.update(query, user) > 0){
             ////If user has no emailid provided then create 
             /// his user name by first char of first name
             /// + lastname
            String username="";
            username=user.get("emailId").toString();
            if(user.get("emailId")==null||user.get("emailId").equals(""))
               {
                 username=user.get("fname").toString()+user.get("lname").toString().substring(0,3);
               }    
               user.put("username", username);
               String pass = java.util.UUID.randomUUID().toString().split("-")[4];
               user.put("password",JceSha.getHash(pass));
               user.put("password_copy",pass);
               user.put("status",1);
           if(this.jdbcTemplate.update(luquery, user) > 0){
               return 1;
           }
        }
        return 0;
    }
    
   public int editUser(Map<String, Object> user)  {
        
        String query = this.sqlQueries.getProperty("EDIT_USER");
        if(this.jdbcTemplate.update(query, user) > 0){
              //Put Audit Trial Code 
               return 1;
        }
        return 0;
     }    

    public UserSessionBean validateUser(String uid, String pass) {
        String query=this.sqlQueries.getProperty("VERIFY_USER");
        Map<String,String> param=new HashMap<String,String>();
        param.put("username", uid);
        param.put("PASSWORD", pass);
        LOG.debug(query);
         LOG.debug(param);
        return this.jdbcTemplate.queryForObject(query, param, new UserMapper());
    }

    public int deleteUser(String userId,String actionBy) {
        String query = this.sqlQueries.getProperty("DELETE_USER"); 
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("userid",userId);
        param.put("deleted",1);
        param.put("deletedon",new Date());
        param.put("deletedby",actionBy);
        if(this.jdbcTemplate.update(query, param) > 0){
          // Put Audit Trial Code  
          return disableUser(userId,actionBy);
        }
        return 0;
    }
    
    public int disableUser(String userId,String actionBy) {
        String query = this.sqlQueries.getProperty("DISABLE_USER"); //cd add insert query
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("userid",userId);
        param.put("status",0);
        param.put("diactivatedon",new Date());
        param.put("diactivatedby",actionBy);// Need to set this column
        if(this.jdbcTemplate.update(query, param) > 0){
          //Put Audit Trial Code  
          return 1;
        }
        return 0;
    }

    public int changePassword(Map<String, Object> obj) {
        String query = this.sqlQueries.getProperty("CHANGE_PASSWORD"); 
        if(this.jdbcTemplate.update(query, obj) > 0){
          //Put Audit Trial Code  
          return 1;
        }
        return 0;
    }
    public int adminChangePassword(Map<String, Object> obj) {
        String query = this.sqlQueries.getProperty("ADMIN_CHANGE_PASSWORD");
        if(this.jdbcTemplate.update(query, obj) > 0){
          //Put Audit Trial Code  
          return 1;
        }
        return 0;
    }
    public List<Map<String,Object>> getUserDetailForEmailTemp(Map<String,Object> obj) {
        String query = this.sqlQueries.getProperty("GET_USER_DETAIL");
        return this.jdbcTemplate.queryForList(query, obj);
    }
    
//        private UserRole getRoleObject(ResultSet rs) throws SQLException{
//        UserRole u = new UserRole();
//        if(rs.getObject("id")!=null)
//            u.setId(rs.getInt("id"));
//        if(rs.getObject("name")!=null)
//            u.setName(rs.getString("name"));
//        if(rs.getObject("permValue")!=null)
//            u.setPermValue(rs.getInt("permValue"));
//        return u;
//    }
//    
//    public int getCount(){
//        return count;
//    }
//
//    public List<UserRole> getAllRoles(Connection conn) throws ReadableException, SQLException {
//        List<UserRole> result = new ArrayList<UserRole>();
//        ResultSet rs =null;
//        rs = DaoUtil.executeQuery(conn, "select * from roles");
//        while(rs.next()){
//            result.add(getRoleObject(rs));
//        }
//        this.count = result.size();
//        return result;
//    }
//

    public int getMaxEmpNo() {
        String query = this.sqlQueries.getProperty("GET_MAX_EMP_NO");
        int empno=0;
        List<Map<String,Object>> obj=this.jdbcTemplate.queryForList(query, new HashMap());
        for (int i = 0; i < obj.size(); i++) {
            Map record = (Map) obj.get(i);
            empno=Integer.parseInt(record.get("empno").toString());
        }
        if(obj.size()==0)
           empno=0; 
        return empno+1;
    }

    public List getAllProject() {
        String query = this.sqlQueries.getProperty("GET_USER_PROJECTS_ADMIN");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("endDate", System.currentTimeMillis());
        return this.jdbcTemplate.queryForList(query,param);
    }

    
    
  
//    public int getUserRole(Connection conn, String userid) throws ReadableException{
//         
//        if(userid!=null){
//            try {
//                ResultSet rs=DaoUtil.executeQuery(conn,"select roleid from users where userid=?",new Object[]{userid});
//                if(rs.next() && rs.getObject("roleid")!=null)  
//                  return rs.getInt("roleid");
//            } catch (SQLException ex) {
//                LOG.debug(ex.getMessage());
//            } 
//         }        
//        return 0;
//    
//    }

//    public int forgotPassword(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ReadableException {
//
//       String usrnme=request.getParameter("u");
//       String checkquery="SELECT ul.password,u.name,u.userid,u.emailid AS email FROM userlogin ul INNER JOIN users u ON u.userid=ul.userid WHERE username=? AND STATUS=1";
//       String insertquery="INSERT INTO forgotpasswordrequest (pid, userid,oldpassword, newpassword) VALUES(?,?,?,?)";
//       String updatequery="UPDATE userlogin SET PASSWORD = ? WHERE	username = ?";
//       ResultSet rs=DaoUtil.executeQuery(conn, checkquery,new Object[]{usrnme});
//       try {
//            if(rs.next()){
//              String emailid="",name="",userid="",oldpass="";    
//
//              if(rs.getObject("userid")!=null)  
//                userid=rs.getString("userid");
//              
//             if(CheckSecurityQuestionAns(conn,request,userid)) { 
//              if(rs.getObject("email")!=null)  
//                emailid=rs.getString("email");
//              if(rs.getObject("name")!=null)  
//                name=rs.getString("name");
//              if(rs.getObject("password")!=null)  
//                oldpass=rs.getString("password");
//              
//              String password=new GenerateString().getString();
//              //mailService.sendLinkOnForgotPassword(conn,name,emailid,password);  
//              String uid = UUID.randomUUID().toString();
//              if(DaoUtil.executeUpdate(conn,insertquery,new Object[]{uid,userid,oldpass,password})==1){
//                  if(DaoUtil.executeUpdate(conn,updatequery,new Object[]{password,usrnme})==1)
//                  {
//                      conn.commit();
//                      return 1;
//                  }    
//              }
//             } 
//            }
//       } catch (SQLException ex) {
//            java.util.logging.Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       return 0; 
//    }
     private static final class UserMapper implements RowMapper<UserSessionBean>{

        @Override
        public UserSessionBean mapRow(ResultSet rs, int i) throws SQLException {
           UserSessionBean obj=new UserSessionBean();
           obj.setDesignation("");
           obj.setUserId(rs.getString("userid"));
           obj.setEmailId(rs.getString("emailid"));
           obj.setfName(rs.getString("fname"));
           obj.setlName(rs.getString("lname"));
           obj.setmName(rs.getString("mname"));
           obj.setRoleId(rs.getInt("roleid"));
           return obj;
           
        }
         
     }
  
    
}
