/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.controller;

import com.dlab.constants.URLMap;
import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.service.IssueService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author LENOVO
 */
@Controller
public class IssueController {
    
    @Autowired UserSessionBean userSessionBean;
    @Autowired IssueService issueService;
    @RequestMapping(value=URLMap.ADD_BUG, method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult addUser(@RequestBody Map<String,Object> user){
        return this.issueService.addBug(user); 
    }
    
}
