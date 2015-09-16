/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.model;


import java.util.List;
import java.util.Map;


/**
 *
 * @author XPPRESP3
 */
public class PMUReport {
    
    Map<String,Object> projdet;
    Map<String,Object> datelist;
    List<Map<String,Object>> activitysumlist;

    public Map<String, Object> getProjdet() {
        return projdet;
    }

    public void setProjdet(Map<String, Object> projdet) {
        this.projdet = projdet;
    }

    public Map<String, Object> getDatelist() {
        return datelist;
    }

    public void setDatelist(Map<String, Object> datelist) {
        this.datelist = datelist;
    }

    public List<Map<String, Object>> getActivitysumlist() {
        return activitysumlist;
    }

    public void setActivitysumlist(List<Map<String, Object>> activitysumlist) {
        this.activitysumlist = activitysumlist;
    }

}
