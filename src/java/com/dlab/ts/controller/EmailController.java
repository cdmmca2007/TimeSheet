/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.controller;

import com.dlab.constants.Constants;
import com.dlab.constants.URLMap;
import com.dlab.ts.dao.EmailDAO;
import com.dlab.ts.model.Email;
import com.kjava.base.db.DbPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author cd
 */
@Controller
public class EmailController {

    Connection conn = null;

    @Autowired
    private EmailDAO emailDAO;

    public void setEmailDAO(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;

    }
    @RequestMapping(value=URLMap.ADD_EMAIL, method=RequestMethod.POST)
    @ResponseBody
    public Email addEmail(@RequestBody Email email){
        try{
            conn = DbPool.getConnection();
           return emailDAO.addOrEditEmail(conn,email);
        }
        catch(Exception ex){

        }finally{
            DbPool.close(conn);
        }
        return email;
    }
    /*@RequestMapping(value=URLMap.GET_EMAIL, method= RequestMethod.GET)
    @ResponseBody
    public String getEmail(){
       try{
            conn = DbPool.getConnection();
           return emailDAO.getAllEmailAsJson(conn,0,15).toString();
        }

        catch(Exception ex){

        }finally{
            DbPool.close(conn);
        }
        return "";
     }*/

    

}
