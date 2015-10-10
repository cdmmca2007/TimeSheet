/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.ts.service;

import com.dlab.ts.model.ExcelModel;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;
import java.util.List;
import java.util.Map;
import jxl.write.WritableWorkbook;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author user
 */
public interface DownloadService {

    public WritableWorkbook writeToWorkBook(WritableWorkbook workbook, ExcelModel model);
    public WritableWorkbook writeToWorkBookOfPMUReport(WritableWorkbook workbook, PMUReport model);
    public WritableWorkbook writeToWorkBookOfProgressReport(WritableWorkbook workbook, ProgressReport model);
    public WritableWorkbook writeToWorkBookFromSqlRowSet(WritableWorkbook workbook, SqlRowSet data);
}
