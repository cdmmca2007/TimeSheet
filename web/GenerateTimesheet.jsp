<%@page import="com.dlab.ts.dao.TimesheetDao"%>
<%@page import="com.dlab.ts.service.impl.TimesheetServiceImpl"%>
<%@page import="com.dlab.ts.service.TimesheetService"%>
<%
   TimesheetDao ts=new TimesheetDao(); 
   int tot=ts.generateWeeklyTimesheet();
   out.print("Total Number of Users :"+tot);
%>
