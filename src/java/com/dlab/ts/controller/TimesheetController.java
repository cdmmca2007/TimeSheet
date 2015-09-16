package com.dlab.ts.controller;


import com.dlab.constants.URLMap;
import com.dlab.session.UserSessionBean;
import com.dlab.spring.web.service.GenericService;
import com.dlab.spring.web.util.ModelServiceResult;
import com.dlab.spring.web.util.ServiceResult;
import com.dlab.ts.service.ProjectService;
import com.dlab.ts.service.TimesheetService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;





@Controller
public class TimesheetController {
	
	@Autowired
	private TimesheetService timesheetService;
        @Autowired UserSessionBean userSessionBean;

    @RequestMapping(value=URLMap.TIMESHEET_GENERATE, method= RequestMethod.POST)
    @ResponseBody
    public ServiceResult addAllTimesheets()  {
        //System.out.print("inside timesheet"+param);
        return this.timesheetService.generateWeeklyTimesheet();  
    }
        
    @RequestMapping(value=URLMap.TIMESHEET_GETALL, method= RequestMethod.POST)
    @ResponseBody
    public ServiceResult getAllTimesheets(@RequestBody  Map<String,Object> param)  {
        System.out.print("inside timesheet"+param);
        return this.timesheetService.getUsersTimesheets(param);  
    }
    @RequestMapping(value=URLMap.MGR_TIMESHEET_GETALL, method= RequestMethod.POST)
    @ResponseBody
    public ServiceResult getManagerTimesheets(@RequestBody  Map<String,Object> param)  {
        System.out.print("inside timesheet"+param);
        return this.timesheetService.getManagerTimesheets(param);  
    }
    
    @RequestMapping(value=URLMap.TIMESHEET+"/{id}", method= RequestMethod.GET)
    @ResponseBody
    public ServiceResult getTimesheet(@PathVariable("id") String id)  {
        return this.timesheetService.getTimesheetLog(id);  
    }
    
    @RequestMapping(value=URLMap.TIMESHEET+"/{id}/{status}", method= RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateUserTimesheet(@PathVariable("id") String id,@PathVariable("status") int status,@RequestBody Map<String,Object> param)  {
        System.out.print("para"+param.toString());
        return timesheetService.updateUserTimeSheet(id,  status,param);//this.timesheetService.getUsersTimesheets(userSessionBean.getUserId());  
    }
    @RequestMapping(value=URLMap.GETALL_TIMESHEET_REPORT+"/{id}", method= RequestMethod.GET)
    @ResponseBody
    public ServiceResult getTSForAcctntOrForUser(@PathVariable("id") String id/*,@PathVariable("userid") String userId*/)  {
        String userId=null;
        return this.timesheetService.getTSForAcctntOrForUser(id,userId);  
    }
}
