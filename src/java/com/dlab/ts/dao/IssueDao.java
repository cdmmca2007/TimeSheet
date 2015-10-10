/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.dao;

import com.dlab.spring.web.dao.AbstractNamedDao;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository("issueDao")
public class IssueDao extends AbstractNamedDao{
    
    Logger LOG = Logger.getLogger(UserDao.class);
    
    public int addIssue(Map<String, Object> user)  {
        
        String query = this.sqlQueries.getProperty("ADD_BUG");
        if(this.jdbcTemplate.update(query, user) > 0){
              //Put Audit Trial Code 
               return 1;
        }
        return 0;
     } 
    
}
