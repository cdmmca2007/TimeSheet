/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.dao;

import com.dlab.spring.web.dao.AbstractNamedDao;
import com.dlab.ts.model.Email;
import com.kjava.base.ReadableException;
//import com.mysql.jdbc.Connection;
import com.kjava.base.db.DaoUtil;
import com.kjava.base.util.ExtJsonUtil;
import com.kjava.base.util.JSONUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
//import java.util.HashSet;
//import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 *
 * @author cd
 */
@Repository("emailDAO")
public class EmailDAO extends AbstractNamedDao implements InitializingBean{
    
     @Override
     public void afterPropertiesSet() throws Exception {
            System.out.println("Inside Project Dao");
            System.out.println("Generic Dao:"+ this.jdbcTemplate);
            System.out.println("sqlQueries:"+ this.sqlQueries);
     }
     public void setSqlQueries(Properties sqlQueries) {
         this.sqlQueries = sqlQueries;
     }
     public Email addOrEditEmail(Connection conn, Email obj) throws ReadableException {
        int flag=0;
        try {
            String query = "insert into class(schoolid,classid,name,classteacher,feetemplate,comment) values(?,?,?,?,?,?)";
            String classid = "";
            obj.setSchoolid("1000");
            if (obj.getSchoolid()!=null) {
                query = "update class set feetemplate=? , classteacher=? , comment=? where schoolid=? and classid = ?";
                classid =obj.getSchoolid();
            }else{
               classid = java.util.UUID.randomUUID().toString();
               obj.setEmailid(classid);
            }
           if( DaoUtil.executeUpdate(conn, query, new Object[]{obj.getSchoolid()})==1)
           {
               flag=1;
               conn.commit();
           }
        } catch (SQLException ex) {
            throw new ReadableException(ex,ex.getMessage(),"ClassDAO", "addoredit");
        }
        return obj;
    }

    /*public Object getAllEmailAsJson(Connection conn, int page,int rows) throws ReadableException {
        JSONObject job = null;
        ResultSet rs = null;
        int count =0;
        String schoolid="1000";
        try{
            rs = DaoUtil.executeQuery(conn, "SELECT count(1) as count from class");
            if(rs.next()) {
                count = rs.getInt("count");

            }
            rs = DaoUtil.executeQuery(conn,"SELECT name,classteacher,feetemplate,comment from class where schoolid=? limit ? offset ?",new Object[]{schoolid,15,0});
           // this.jdbcTemplate.queryForList(query, new HashMap());             
            job = jsonUtil.getJsonObject(rs, count, page,rows, false);
        }
        catch (SQLException ex) {
            Logger.getLogger(EmailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return job;
    }*/  
     public Email getMail(int mailTplId) throws ReadableException {
        String query = this.sqlQueries.getProperty("GET_MAIL_TEMP");
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("templateid",mailTplId);
        return getMailObject(this.jdbcTemplate.queryForList(query, param));
    }

    private Email getMailObject(List<Map<String,Object>> obj) {
       Email mail = new Email();
        try {
            for (int i = 0; i < obj.size(); i++) {
            Map record = (Map) obj.get(i);
            mail.setSubject(record.get("subject").toString());
            mail.setValue(record.get("template").toString());
        }
        } catch (Exception ex) {
            Logger.getLogger(EmailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mail;
    }
}
