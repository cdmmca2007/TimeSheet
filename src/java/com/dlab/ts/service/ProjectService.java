package com.dlab.ts.service;

import com.dlab.spring.web.service.*;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;
import java.util.List;
import java.util.Map;

public interface ProjectService {

    public ModelServiceResult addProject(Map<String, Object> model);

    public ServiceResult updateProject(Map<String, Object> model);

    public ServiceResult deleteProject(String id);

    public ModelServiceResult getProject(String id);

    public ServiceResult getAllProject(String userid,int roleid);

    public ServiceResult getUserProject(String userId);

    public ServiceResult getProjectMember(String projId);

    public ServiceResult addProjectMember(String projId, String[] members);

    public ServiceResult deleteProjectMember(String resosurceId);

    public ServiceResult getProjectActivity(String projId);

    public ModelServiceResult addProjectActivity(Map<String, Object> model);

    public ServiceResult deleteProjectActivity(String projId,String activityId);

    public ServiceResult addActivityActualProgress(Map<String, Object> model);
    
    public ServiceResult getProjectManager(String projId);

    public ModelServiceResult addProjectMember(Map<String, Object> model);

    public ServiceResult getProjecActivityReport(String projId);	

    public PMUReport projectPMUReport(String projectid);
    public ProgressReport projectProgressReport(String projectid);
}
