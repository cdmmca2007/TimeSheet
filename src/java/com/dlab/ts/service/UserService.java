package com.dlab.ts.service;

import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.service.*;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.model.User;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface UserService {
//
//	Object add(String queryName, Map<String, String> model);
//
//	Object update(String queryName, String id, Map<String, String> model);
//
//	Object delete(String queryName, String id);
//
//	Object get(String queryName, String id);
//
//	List<Map<String, Object>> getAll(String queryName, Map<String, String> model);

    public ModelServiceResult<UserSessionBean> validateUser(String uid, String pass);

    public ServiceResult getAllUsers();

    public ServiceResult addUser(Map<String, Object> user);

    public ServiceResult deleteUser(String userId,String actionId);
    
    public ServiceResult disableUser(String userId,String actionBy);
   
    public ServiceResult editUser(Map<String, Object> user);   
    
    public ServiceResult changePassword(Map<String, Object> obj);
    public ServiceResult adminChangePassword(Map<String,Object> obj);
    public ServiceResult signOut(HttpServletRequest request);
    public ModelServiceResult getUserFullDetails(String userId);
    
}
