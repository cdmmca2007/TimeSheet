/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class SessionUser {
    private String userId;
    private int role;
    private String name;
    private List<Map<String,Object>> projects;
    public SessionUser(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getProjects() {
        return projects;
    }

    public void setProjects(List<Map<String, Object>> projects) {
        this.projects = projects;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

   
    
}
