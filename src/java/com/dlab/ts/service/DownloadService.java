/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.service;

import com.dlab.ts.model.ExcelModel;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;
import jxl.write.WritableWorkbook;

/**
 *
 * @author user
 */
public interface DownloadService {

    public WritableWorkbook writeToWorkBook(WritableWorkbook workbook, ExcelModel model);
    public WritableWorkbook writeToWorkBookOfPMUReport(WritableWorkbook workbook, PMUReport model);
    public WritableWorkbook writeToWorkBookOfProgressReport(WritableWorkbook workbook, ProgressReport model);
    
}
