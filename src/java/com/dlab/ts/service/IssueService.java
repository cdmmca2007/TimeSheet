/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dlab.ts.service;

import com.dlab.spring.web.util.ServiceResult;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public interface IssueService {
    
    public ServiceResult addBug(Map<String, Object> user);
    
}
