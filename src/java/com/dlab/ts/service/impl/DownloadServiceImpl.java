package com.dlab.ts.service.impl;

import com.dlab.ts.model.Column;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dlab.ts.model.ExcelModel;
import com.dlab.ts.model.PMUReport;
import com.dlab.ts.model.ProgressReport;
import com.dlab.ts.service.DownloadService;
import java.lang.Number;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import jxl.CellView;
import jxl.format.Orientation;
import jxl.write.*;
import org.springframework.stereotype.Service;


@Service("downloadService")
public class DownloadServiceImpl implements DownloadService {

    final static Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);
    private static final Logger LOG = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Override
    public WritableWorkbook writeToWorkBook(WritableWorkbook workbook, ExcelModel model) {
        try {
            LOG.info("writeToWorkBook --> Work Book creation Started");
            String sheetName = "sheet1";
            WritableSheet workSheet = initialize(workbook, sheetName);
            WritableCellFormat headerFormat = setHeaderFormat();
            WritableCellFormat dataFormat = setDataFormat();
            WritableCellFormat labelFormat = this.getLabelCellFormat();

            createExcelData(workSheet, headerFormat, dataFormat, labelFormat, model);

            return workbook;
        } catch (Exception ex) {
            logger.error("Exceptin in getLabelCellFormat",ex);
        }
        return workbook;
    }

    /**
     * Initialize the workbook object
     *
     * @param workbook
     * @param sheetName
     * @return Workbook
     */
    private WritableSheet initialize(WritableWorkbook workbook, String sheetName) {
        WritableSheet workSheet = null;
        workSheet = workbook.createSheet(sheetName, 0);
//			        workSheet.setColumnView(0, 30);
//					workSheet.setColumnView(1, 30);
//					workSheet.setColumnView(2, 30);
//					workSheet.setColumnView(3, 30);
//					workSheet.setColumnView(4, 30);
//					workSheet.setColumnView(5, 30);
        return workSheet;
    }

    /**
     * *
     * This method is used to set the Header Format
     *
     * @return WritableCellFormat
     * @throws FatalException
     */
    private WritableCellFormat setHeaderFormat() {
        WritableCellFormat headerFormat = null;
        try {
            WritableFont headerFont = new WritableFont(WritableFont.createFont("Helvetica"),
                    WritableFont.DEFAULT_POINT_SIZE,
                    WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);
            headerFormat = new WritableCellFormat(headerFont);
            headerFormat.setWrap(true);
            headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            headerFormat.setWrap(true);
            headerFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            headerFormat.setBackground(jxl.format.Colour.GRAY_25);
        } catch (WriteException we) {
            
        }
        return headerFormat;
    }

    /**
     * *
     * This method is used to set the Label Format
     *
     * @return WritableCellFormat
     * @throws FatalException
     */
    private WritableCellFormat getLabelCellFormat() {
        try {
            WritableFont dataFont = new WritableFont(WritableFont.createFont("Helvetica"),
                    WritableFont.DEFAULT_POINT_SIZE,
                    WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);

            WritableCellFormat dataFormat = new WritableCellFormat(dataFont);
            dataFormat.setWrap(true);
            dataFormat.setAlignment(jxl.format.Alignment.LEFT);
            dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            dataFormat.setWrap(true);
            dataFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
                    jxl.format.Colour.BLACK);
            return dataFormat;
        } catch (WriteException we) {
            logger.error("Exceptin in getLabelCellFormat",we);
        }
        return null;
    }

    /**
     * *
     * This method is used to set the Data Format
     *
     * @return WritableCellFormat
     * @throws FatalException
     */
    private WritableCellFormat setDataFormat() {
        try {
            WritableFont dataFont = new WritableFont(WritableFont.createFont("Helvetica"),
                    WritableFont.DEFAULT_POINT_SIZE,
                    WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);

            WritableCellFormat dataFormat = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
            dataFormat.setFont(dataFont);
            dataFormat.setWrap(true);
            dataFormat.setAlignment(jxl.format.Alignment.RIGHT);
            dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            dataFormat.setWrap(true);
            dataFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
                    jxl.format.Colour.BLACK);
            return dataFormat;
        } catch (WriteException we) {
            logger.error("Exceptin in getLabelCellFormat",we);
        }
        return null;
    }
   private void createExcelData(WritableSheet workSheet, WritableCellFormat headerFormat, WritableCellFormat dataFormat, WritableCellFormat labelFormat, ExcelModel model) throws Exception {
        int row = 1;
        try {
            List<Column> cols = model.getColumn();
            int c = 0;
            for (Column col : cols) {
                workSheet.addCell(new Label(c, row, col.getTitle(), headerFormat));
                c++;
            }
            List<Map<String, Object>> data = model.getData();
            row++;
            String field=new String();
            for (int i = 0; i < data.size(); i++) {
                c = 0;
                Map<String,Object> record=data.get(i);
                for (Column col : cols) {
                    String key=col.getData();
                    
                    if(record.get(key)!=null)
                    field=record.get(key).toString();
                    else
                    field=null;    
                    workSheet.addCell(new Label(c, row, field, dataFormat));
                    c++;
                }
                row++;
            }
        } catch (WriteException we) {
            throw new Exception("Error in Writing to excel", we);
        }
    }

    @Override
    public WritableWorkbook writeToWorkBookOfPMUReport(WritableWorkbook workbook, PMUReport model) {
        try {
            LOG.info("writeToorkBookOfPMUReport --> PMU Work Book creation Started");
            String sheetName =new String();
            if(model.getProjdet()!=null && model.getProjdet().get("projectname")!=null)
               sheetName = model.getProjdet().get("projectname").toString()+"PMU_REPORT";
            
            WritableSheet workSheet = initialize(workbook, sheetName);
            WritableCellFormat headerFormat = setHeaderFormatPMU();
            WritableCellFormat dataFormat = setDataFormat();
            WritableCellFormat labelFormat = this.getLabelCellFormat();

            createExcelDataForPMU(workSheet, headerFormat, dataFormat, labelFormat, model);

            return workbook;
        } catch (Exception ex) {
            logger.error("Exceptin in getLabelCellFormat",ex);
        }
        return workbook;        
    }

    private void createExcelDataForPMU(WritableSheet workSheet, WritableCellFormat headerFormat, WritableCellFormat dataFormat, WritableCellFormat labelFormat, PMUReport model) throws Exception {
        int row = 1;
        try {
            String reportname="PMU Report (Project Manpower Utilisation Report)";
            int c = 0;
            workSheet.addCell(new Label(0, row,"Report Name",getLabelCellFormatForPmu(1/*For Bold*/,
                                                                                      "RED",/*For Font Color*/
                                                                                      "YELLOW"/*Cell Background Color*/
                                                                                     )));
            workSheet.addCell(new Label(1, row,reportname,getLabelCellFormatForPmu(1/*For Bold*/,
                                                                                      "RED",/*For Font Color*/
                                                                                      "YELLOW"/*Cell Background Color*/
                                                                                     )));
            CellView cv = workSheet.getColumnView(0);
            cv.setAutosize(true);
            workSheet.setColumnView(0, cv);
            cv = workSheet.getColumnView(1);
            cv.setAutosize(true);
            workSheet.setColumnView(1, cv);
            row=row+1;
            
            workSheet.addCell(new Label(c,row ,"", dataFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Project Name", dataFormat));
            workSheet.addCell(new Label(1,row ,model.getProjdet().get("projectname").toString(), dataFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Project Code", dataFormat));
            workSheet.addCell(new Label(1,row ,model.getProjdet().get("projectcode").toString(), dataFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Client Name", dataFormat));
            workSheet.addCell(new Label(1,row ,model.getProjdet().get("clientname").toString(), dataFormat));
            row=row+1;
            Calendar cal = Calendar.getInstance();
            String date="";
            if(model.getProjdet().get("startdate").toString()!=null)
            {
                cal.setTimeInMillis(Long.parseLong(model.getProjdet().get("startdate").toString()) );            
                date=cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            }
            workSheet.addCell(new Label(0,row ,"Project Start Date", dataFormat));
            workSheet.addCell(new Label(1,row ,date, dataFormat));
            row=row+1;
            if(model.getProjdet().get("enddate").toString()!=null)
            {
                cal.setTimeInMillis(Long.parseLong(model.getProjdet().get("enddate").toString()) );            
                date=cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            }  
            workSheet.addCell(new Label(0,row ,"Project End Date", dataFormat));
            workSheet.addCell(new Label(1,row ,date, dataFormat));
            row=row+3;
            workSheet.addCell(new Label(c,row ,"", dataFormat));
            
            Map datelist=model.getDatelist();
            workSheet.addCell(new Label(0, row,"",headerFormat));
            c = 2;
            int x=0;
            workSheet.addCell(new Label(1, row,"Total Allocated Hour",headerFormat));            
            while(x<datelist.size())
            {
              workSheet.addCell(new Label(c, row, datelist.get("date_"+x).toString(),headerFormat));
              c++;
              x++;
            }
            workSheet.addCell(new Label(c, row,"Balance",headerFormat));            
            workSheet.addCell(new Label(c+1, row,"Productivity",headerFormat));            
            List<Map<String,Object>> asl=model.getActivitysumlist();
            int i=0;
            int tot_alloted=0,tot_done=0;
            while(i<asl.size()){
               Map rec=asl.get(i);
               int j=0;
               row=row+1;
               while(j<rec.size())    {           
                 if(j==0)  
                 workSheet.addCell(new Label(j, row, rec.get("activityname").toString(),headerFormat));               
                 else if(j==1){
                 workSheet.addCell(new Label(j, row, rec.get("allotedhour").toString(),dataFormat));     
                 tot_alloted=Integer.parseInt(rec.get("allotedhour").toString());
                 }
                 else{
                     int txt=0;
                     if(rec.get("date_"+j)!=null)
                       txt=Integer.parseInt(rec.get("date_"+j).toString());
                       tot_done=tot_done+txt;
                       workSheet.addCell(new Label(j,row,txt+"",dataFormat));         
                 }
                 
                 j++;
               }
               workSheet.addCell(new Label(j,row,(tot_alloted-tot_done)+"",dataFormat));         
               tot_done=0;
               i++;
            }
            
        } catch (WriteException we) {
            throw new Exception("Error in Writing to excel", we);
        }
    }
    private WritableCellFormat getLabelCellFormatForPmu(int font_wght,String fontColor, String cellBkgd) {
        try {
            WritableFont dataFont = new WritableFont(WritableFont.createFont("Helvetica"),
                    WritableFont.DEFAULT_POINT_SIZE,
                    font_wght==1?WritableFont.BOLD:WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);

            WritableCellFormat dataFormat = new WritableCellFormat(dataFont);
            dataFormat.setWrap(true);
            dataFormat.setAlignment(jxl.format.Alignment.LEFT);
            dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            dataFormat.setWrap(true);
            dataFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
                    jxl.format.Colour.BLACK);
            if(cellBkgd.equals("YELLOW"))
            dataFormat.setBackground(jxl.format.Colour.SKY_BLUE);
            
            return dataFormat;
        } catch (WriteException we) {
            logger.error("Exceptin in getLabelCellFormat",we);
        }
        return null;
    }
    
    private WritableCellFormat setHeaderFormatPMU() {
        WritableCellFormat headerFormat = null;
        try {
            WritableFont headerFont = new WritableFont(WritableFont.createFont("Helvetica"),
                    WritableFont.DEFAULT_POINT_SIZE,
                    WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);
            headerFormat = new WritableCellFormat(headerFont);
            headerFormat.setWrap(true);
            headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            headerFormat.setWrap(true);
            headerFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            headerFormat.setBackground(jxl.format.Colour.SKY_BLUE);
        } catch (WriteException we) {
            
        }
        return headerFormat;
    }

    @Override
    public WritableWorkbook writeToWorkBookOfProgressReport(WritableWorkbook workbook, ProgressReport model) {
        try {
            LOG.info("writeToorkBookOfPMUReport --> PMU Work Book creation Started");
            String sheetName =new String();
            if(model.getProjdet()!=null && model.getProjdet().get("projectname")!=null)
               sheetName = model.getProjdet().get("projectname").toString()+"Progress_Report";
            
            WritableSheet workSheet = initialize(workbook, sheetName);
            WritableCellFormat headerFormat = setHeaderFormatPMU();
            WritableCellFormat dataFormat = setDataFormat();
            WritableCellFormat labelFormat = this.getLabelCellFormat();

            createExcelDataForProgressReport(workSheet, headerFormat, dataFormat, labelFormat, model);

            return workbook;
        } catch (Exception ex) {
            logger.error("Exceptin in getLabelCellFormat",ex);
        }
        return workbook; 
    }

    private void createExcelDataForProgressReport(WritableSheet workSheet, WritableCellFormat headerFormat, WritableCellFormat dataFormat, WritableCellFormat labelFormat, ProgressReport model) throws Exception {
        int row = 1;
        try {
            String reportname="Prgress Report (Weekly Manpower Plan Vs Utilisation)";
            int c = 0;
            workSheet.addCell(new Label(0, row,"Report Name",getLabelCellFormatForPmu(1/*For Bold*/,
                                                                                      "RED",/*For Font Color*/
                                                                                      "YELLOW"/*Cell Background Color*/
                                                                                     )));
            workSheet.addCell(new Label(1, row,reportname,getLabelCellFormatForPmu(1/*For Bold*/,
                                                                                      "RED",/*For Font Color*/
                                                                                      "YELLOW"/*Cell Background Color*/
                                                                                     )));
            CellView cv = workSheet.getColumnView(0);
            cv.setAutosize(true);
            workSheet.setColumnView(0, cv);
            cv = workSheet.getColumnView(1);
            cv.setAutosize(true);
            workSheet.setColumnView(1, cv);
            row=row+1;
            
            workSheet.addCell(new Label(c,row ,"", labelFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Project Name", labelFormat));
            workSheet.addCell(new Label(1,row ,model.getProjdet().get("projectname").toString(), dataFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Project Code", labelFormat));
            workSheet.addCell(new Label(1,row ,model.getProjdet().get("projectcode").toString(), dataFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Client Name", labelFormat));
            workSheet.addCell(new Label(1,row ,model.getProjdet().get("clientname").toString(), dataFormat));
            row=row+1;
            workSheet.addCell(new Label(0,row ,"Project Start Date", labelFormat));
            Calendar cal = Calendar.getInstance();
            String date="";
            if(model.getProjdet().get("startdate").toString()!=null)
            {
                cal.setTimeInMillis(Long.parseLong(model.getProjdet().get("startdate").toString()) );            
                date=cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            }
            workSheet.addCell(new Label(1,row ,date, dataFormat));
            row=row+1;
            if(model.getProjdet().get("enddate").toString()!=null)
            {
                cal.setTimeInMillis(Long.parseLong(model.getProjdet().get("enddate").toString()) );            
                date=cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
            }    
            workSheet.addCell(new Label(0,row ,"Project End Date", labelFormat));
            workSheet.addCell(new Label(1,row ,date, dataFormat));
            row=row+3;
            workSheet.addCell(new Label(0,row ,"Activity Code", headerFormat));
            workSheet.addCell(new Label(1,row ,"Activity Name", headerFormat));
            workSheet.addCell(new Label(2,row ,"Total Used Hrs", headerFormat));
            workSheet.addCell(new Label(3,row ,"Total Timesheet Hrs", headerFormat));
            workSheet.addCell(new Label(4,row ,"Progress as Per Time Booking", headerFormat));
            workSheet.addCell(new Label(5,row ,"Actual Progress", headerFormat));
            workSheet.addCell(new Label(6,row ,"Productivity", headerFormat));
            
            List<Map<String,Object>> asl=model.getActivitysumlist();
            int i=0;
            while(i<asl.size()){
               Map rec=asl.get(i);
               row=row+1;
               workSheet.addCell(new Label(0, row, rec.get("activitycode").toString(),dataFormat));               
               workSheet.addCell(new Label(1, row, rec.get("activityname").toString(),dataFormat));               
               workSheet.addCell(new Label(2, row, rec.get("actualplanhour").toString(),dataFormat));               
               workSheet.addCell(new Label(3, row, rec.get("progpertsbooking").toString(),dataFormat));               
               workSheet.addCell(new Label(4, row, rec.get("prog_per_boking_percent").toString()+"%",dataFormat));               
               workSheet.addCell(new Label(5, row, rec.get("actualprogress").toString()+"%",dataFormat));               
               workSheet.addCell(new Label(6, row, rec.get("productivity").toString()+"%",dataFormat));               
               i++;
            }
            
        } catch (WriteException we) {
            throw new Exception("Error in Writing to excel", we);
        }
    }
}
