package com.dlab.ts.controller;

import com.dlab.constants.URLMap;
import com.dlab.session.UserSessionBean;
import com.dlab.ts.dao.ProjectDao;
import com.dlab.ts.dao.ReportDownloadExcelDAO;
import com.dlab.ts.dao.UserDao;
import com.dlab.ts.model.ExcelModel;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;
import com.dlab.ts.service.DownloadService;
import com.dlab.ts.service.ProjectService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author EXILANT Technologies Pvt. Ltd. This controller class is used for for
 * downloading graph matrix in keynote/png/excel for both E-Learning and
 * Executive dashboard
 */
@Controller
public class DownloadController {

    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired UserSessionBean userSessionBean;
    @Autowired ReportDownloadExcelDAO reportDownloadExcelDAO; 

    
    private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

    /**
     * **
     * This method is used for Excel download
     *
     * @param request
     * @param response
     * @param sendExcelModelObj return
     * @throws BusinessRulesException
     * @throws FatalException
     * @throws CustomDataAccessExcepton
     */
    @RequestMapping(value = URLMap.EXCEL, method = RequestMethod.POST)
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        String data = request.getParameter("data");
        
        LOG.info("downloadExcel --> sendExcelModelObj : " + data);
        ServletOutputStream out = null;
        WritableWorkbook workbook = null;
        try {
          ObjectMapper mapper = new ObjectMapper();
         ExcelModel model =mapper.readValue(data, ExcelModel.class);
            out = response.getOutputStream();
            workbook = Workbook.createWorkbook(out);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=tms.xls");
            downloadService.writeToWorkBook(workbook, model);

        } catch (IOException e) {
           LOG.error("error on excel dwnload", e);
        } finally {
            try {
                out.flush();
                workbook.write();
                workbook.close();
                out.close();
            } catch (IOException e) {
                LOG.error("Exception Closing stream :", e);
            } catch (WriteException e) {
                LOG.error("Exception Closing workbook :", e);
            }
        }
    }
    
    @RequestMapping(value = URLMap.EXCEL_REPORT_1, method = RequestMethod.POST)
    public void downloadExcelPMUReport(HttpServletRequest request, HttpServletResponse response) {
        String projectid = request.getParameter("data");
                
        PMUReport pmucls=projectService.projectPMUReport(projectid);

        LOG.info("downloadExcel --> sendExcelModelObj : " + pmucls);
        ServletOutputStream out = null;
        WritableWorkbook workbook = null;
        try {
            out = response.getOutputStream();
            workbook = Workbook.createWorkbook(out);
            response.setContentType("application/vnd.ms-excel");
            String filename=pmucls.getProjdet().get("projectname").toString()+"_PMU_Report.xls";
            filename=filename.replaceAll(" ", "_");
            response.setHeader("Content-Disposition", "attachment; filename="+filename);
            ///response.setHeader("Content-Disposition", "attachment; filename=PMU_Report.xls");
            downloadService.writeToWorkBookOfPMUReport(workbook, pmucls);

        } catch (IOException e) {
           LOG.error("error on excel dwnload", e);
        } finally {
            try {
                out.flush();
                workbook.write();
                workbook.close();
                out.close();
            } catch (IOException e) {
                LOG.error("Exception Closing stream :", e);
            } catch (WriteException e) {
                LOG.error("Exception Closing workbook :", e);
            }
        }
    }
    @RequestMapping(value = URLMap.EXCEL_REPORT_3, method = RequestMethod.POST)
    public void downloadExcelProgressReport(HttpServletRequest request, HttpServletResponse response) {
        String projectid = request.getParameter("data");
        ProgressReport prcls=projectService.projectProgressReport(projectid);
        LOG.info("downloadExcel --> sendExcelModelObj : " + prcls);
        ServletOutputStream out = null;
        WritableWorkbook workbook = null;
        try {
            out = response.getOutputStream();
            workbook = Workbook.createWorkbook(out);
            response.setContentType("application/vnd.ms-excel");
            String filename=prcls.getProjdet().get("projectname").toString()+"_Progress_Report.xls";
            filename=filename.replaceAll(" ", "_");
            response.setHeader("Content-Disposition", "attachment; filename="+filename);
            downloadService.writeToWorkBookOfProgressReport(workbook, prcls);

        } catch (IOException e) {
           LOG.error("error on excel dwnload", e);
        } finally {
            try {
                out.flush();
                workbook.write();
                workbook.close();
                out.close();
            } catch (IOException e) {
                LOG.error("Exception Closing stream :", e);
            } catch (WriteException e) {
                LOG.error("Exception Closing workbook :", e);
            }
        }
    }
    
    @RequestMapping(value = URLMap.PROJECT_LIST, method = RequestMethod.POST)
    public void downloadExcelProjectList(HttpServletRequest request, HttpServletResponse response) {
        
        SqlRowSet srs=reportDownloadExcelDAO.getAllProjectForExcelDownload(userSessionBean.getUserId(),userSessionBean.getRoleId());
        ServletOutputStream out = null;
        WritableWorkbook workbook = null;
        try {
            out = response.getOutputStream();
            workbook = Workbook.createWorkbook(out);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=ProjectList.xls");
            downloadService.writeToWorkBookFromSqlRowSet(workbook,srs);

        } catch (IOException e) {
           LOG.error("error on excel dwnload", e);
        } finally {
            try {
                out.flush();
                workbook.write();
                workbook.close();
                out.close();
            } catch (IOException e) {
                LOG.error("Exception Closing stream :", e);
            } catch (WriteException e) {
                LOG.error("Exception Closing workbook :", e);
            }
        }
    }
       
    @RequestMapping(value = URLMap.USER_LIST, method = RequestMethod.POST)
    public void downloadExcelUsersList(HttpServletRequest request, HttpServletResponse response) {

        SqlRowSet srs=userDao.getAllUsersForExcelDownload();
        ServletOutputStream out = null;
        WritableWorkbook workbook = null;
        try {
            out = response.getOutputStream();
            workbook = Workbook.createWorkbook(out);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=EmployeeList.xls");
            downloadService.writeToWorkBookFromSqlRowSet(workbook,srs);

        } catch (IOException e) {
           LOG.error("error on excel dwnload", e);
        } finally {
            try {
                out.flush();
                workbook.write();
                workbook.close();
                out.close();
            } catch (IOException e) {
                LOG.error("Exception Closing stream :", e);
            } catch (WriteException e) {
                LOG.error("Exception Closing workbook :", e);
            }
        }
    }
}
