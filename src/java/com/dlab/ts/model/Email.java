/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.model;

import java.util.Date;
/**
 *
 * @author cd
 */
public class Email {
      private String emailid;
      private String Schoolid;
      private String name;
      private String subject;
      private Date   createdon;
      private String value;
      private String comment;
      private String createdby;

    public String getSchoolid() {
        return Schoolid;
    }

    public void setSchoolid(String Schoolid) {
        this.Schoolid = Schoolid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
