/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.dao;

import com.dlab.spring.web.dao.AbstractNamedDao;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository("reportDownloadExcelDAO")
public class ReportDownloadExcelDAO extends AbstractNamedDao{
    Logger LOG = Logger.getLogger(UserDao.class);
    
    public SqlRowSet getAllProjectForExcelDownload(String userid,int roleid) {

        String query = this.sqlQueries.getProperty("GET_PROJECT_LIST_FOR_EXCEL_DATA");
        if(roleid==1){
        return this.jdbcTemplate.queryForRowSet(query,new HashMap());
        }else{
        query=query+" AND p.projectmanagerid=:projectmanagerid";    
        Map<String, Object> model=new HashMap();
        model.put("projectmanagerid", userid);
        return this.jdbcTemplate.queryForRowSet(query,model);
    }
    }
    
}
