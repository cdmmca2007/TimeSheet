package com.dlab.ts.controller;


import com.dlab.constants.URLMap;
import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.service.GenericService;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.service.ProjectService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ProjectController {
	final static Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;
	@Autowired UserSessionBean userSessionBean;
	/**
         * create  new project
         * @param model
         * @return 
         */

	@RequestMapping(value=URLMap.PROJECT, method=RequestMethod.POST)
	@ResponseBody
	public ModelServiceResult add(@RequestBody Map<String,Object> model){
            logger.debug("Inside Add Proect : " + model);

		return this.projectService.addProject(model);
	}

        /**
         * edit existing project.
         * @param id
         * @param model
         * @return 
         */
	@RequestMapping(value=URLMap.EDIT_PROJECT, method=RequestMethod.POST)
	@ResponseBody
	public ServiceResult update(@RequestBody Map<String,Object> model){
		return this.projectService.updateProject(model);
	}
        /**
         * Delete existing project.
         * @param id
         * @return 
         */
	@RequestMapping(value=URLMap.PROJECT+"/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ServiceResult delete(@PathVariable("id") String id){
		System.out.println("Inside controller");
		return this.projectService.deleteProject(id);
	}
        /**
         * 
         * @param id
         * @return 
         */
	@RequestMapping(value=URLMap.PROJECT+"/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ModelServiceResult get( @PathVariable("id") String id){
		return this.projectService.getProject(id);
	}
	/**
         * 
         * @return 
         */
	@RequestMapping(value=URLMap.PROJECT, method=RequestMethod.GET)
	@ResponseBody
	public ServiceResult getAll(){
		return this.projectService.getAllProject(userSessionBean.getUserId(),userSessionBean.getRoleId());
	}
        /**
         * 
         * @param userId
         * @return 
         */
        @RequestMapping(value=URLMap.USER_PROJECT+"/{uid}", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResult getAll( @PathVariable("uid") String userId){
		return this.projectService.getUserProject(userId);
	}
        /**
         * 
         * @param projId
         * @return 
         */
        
        @RequestMapping(value=URLMap.PROJECT_MEMBER, method=RequestMethod.POST)
	@ResponseBody
	public ModelServiceResult addProjectMember(@RequestBody Map<String,Object> model){
            logger.debug("Inside Add Proect Resource: " + model);
                model.put("action",0);
		return this.projectService.addProjectMember(model);
	}
        @RequestMapping(value=URLMap.EDIT_PROJECT_MEMBER, method=RequestMethod.POST)
	@ResponseBody
	public ModelServiceResult editProjectMember(@RequestBody Map<String,Object> model){
            logger.debug("Inside Add Proect Resource: " + model);
                model.put("action",1);////1 Mean Editing the project activity 
		return this.projectService.addProjectMember(model);
	}
        
        @RequestMapping(value=URLMap.PROJECT_MEMBER+"/{pid}", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResult getProjectMember( @PathVariable("pid") String projId){
		return this.projectService.getProjectMember(projId);
	}
        /**
         * 
         * @param projId
         * @param members
         * @return 
         */
        
        ////Not in used metheod
        @RequestMapping(value=URLMap.PROJECT_MEMBER+"/{pid}", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResult addProjectMember(@PathVariable("pid") String projId, @RequestParam("members") String[] members){
		return this.projectService.addProjectMember(projId,members);
	}
        
        @RequestMapping(value=URLMap.PROJECT_MEMBER+"/{rid}", method=RequestMethod.DELETE)
	@ResponseBody
	public ServiceResult deleteProjectMember(@PathVariable("rid") String resourceId){
		return this.projectService.deleteProjectMember(resourceId);
	}
        /**
         * 
         * @param projId
         * @return 
         */
        @RequestMapping(value=URLMap.PROJECT_MANAGER+"/{pid}", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResult getProjectManager( @PathVariable("pid") String projId){
		return this.projectService.getProjectManager(projId);
	}
        
        
        /**
         * 
         * @param projId
         * @return 
         */
        @RequestMapping(value=URLMap.PROJECT_ACTIVITY+"/{pid}", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResult getProjectActivity( @PathVariable("pid") String projId){
		return this.projectService.getProjectActivity(projId);
	}
        /**
         * 
         * @param projId
         * @param members
         * @return 
         */
        @RequestMapping(value=URLMap.ADD_PROJECT_ACTIVITY, method=RequestMethod.POST)
	@ResponseBody
	public ModelServiceResult addProjectActivity(@RequestBody Map<String,Object> model){
                model.put("action",0);////0 Mean adding the project activity
		return this.projectService.addProjectActivity(model);
	}
        @RequestMapping(value=URLMap.EDIT_PROJECT_ACTIVITY, method=RequestMethod.POST)
	@ResponseBody
	public ModelServiceResult editProjectActivity(@RequestBody Map<String,Object> model){
                model.put("action",1);////1 Mean Editing the project activity
		return this.projectService.addProjectActivity(model);
	}
        @RequestMapping(value=URLMap.ACTUAL_PROGRESS, method=RequestMethod.POST)
	@ResponseBody
	public ServiceResult addActivityActualProgress(@RequestBody Map<String,Object> model){

            model.put("createdby",userSessionBean.getUserId());
            return this.projectService.addActivityActualProgress(model);
	}
        @RequestMapping(value=URLMap.PROJECT_ACTIVITY+"/{pid}/{aid}", method=RequestMethod.PUT)
	@ResponseBody
	public ServiceResult deleteProjectActivity(@PathVariable("pid") String projId,@PathVariable("aid") String activityId){
		return this.projectService.deleteProjectActivity(projId,activityId);
	}
        @RequestMapping(value=URLMap.PROJECT_ACTIVITY_REPORT+"/{pid}", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResult getProjecActivityReport( @PathVariable("pid") String projId){
		return this.projectService.getProjecActivityReport(projId);
	}

}
