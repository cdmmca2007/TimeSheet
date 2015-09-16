/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.controller;

import com.dlab.session.UserSessionBean;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cd
 */
@Controller
public class HomeController {

    @Autowired
    UserSessionBean userSessionBean;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView redirect(HttpServletRequest req) {
        int role = userSessionBean.getRoleId();
        if (userSessionBean!=null && role!=0) {
            ModelAndView view = null;
            if(role == 3 || role==4){
                view = new ModelAndView("/WEB-INF/jsp/emp.html");
            } else{
                view = new ModelAndView("/WEB-INF/jsp/index.html");
            }
            view.addObject("role", userSessionBean.getRoleId());
            return view;
        } else{
            ModelAndView view = new ModelAndView("/login.html");
            return view;
        }
    }
}
