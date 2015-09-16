<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />
	<title>Timesheet</title>
	<link rel="stylesheet" type="text/css" href="scripts/lib/jquery/themes/layout-default-latest.css" />
	<link href="scripts/lib/jquery/themes/cupetino/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/select2.css" rel="stylesheet" type="text/css"/>
	 <script type="text/javascript">
		/*<![CDATA[*/
			function _cC(){ _u = _gC('zrole'); if (!_u || _u == "") _r('login.html');}
			function _gC(_c){ _ck = document.cookie; if (_ck.length > 0) { _s = _ck.indexOf(_c + "="); if (_s != -1) { _s = _s + _c.length + 1; _e = _ck.indexOf(";", _s); if (_e == -1) _e = _ck.length; return unescape(_ck.substring(_s, _e));}} return "";}
			function _r(url){ window.top.location.href = url;}
			_cC();
		/*]]>*/
    </script> 
</head>
<body>
<div class="ui-layout-north ui-widget-content" style="display: none;">
	<div id="top-link" style="float: right; margin-right: 160px;">
            <span class="my-profile top-link" title="My Profile">&nbsp;</span>
            <span class="alert top-link" title="Alert">&nbsp;</span>
            <span class="notification top-link" title="Notification">&nbsp;</span>
            <span class="singout top-link">&nbsp;</span>
	</div>
	North Pane
</div>

<div id="tabs_div" class="ui-layout-center" style="display: none;"> 
	<UL style="-moz-border-radius-bottomleft: 0; -moz-border-radius-bottomright: 0;">
		<LI><A href="#tab_timesheet"><SPAN>Timesheet</SPAN></A></LI>
	</UL>
	<div id="tab_timesheet"> 
            <div class="ui-layout-north"> 
             <table id="timesheet-grid"></table>
            </div>
            <div class="ui-layout-center">
                <div id="dynamic">
                    <div  class="ui-widget-header">&nbsp;</div>
                    <table id="timesheet-editor" cellpadding="0" width="100%" cellspacing="0" border="0" class="display">
                        <thead><tr>
                                <th >Project</th>
                                <th  class="ui-widget-header">Activity</th>
                                <th class="ui-widget-header">Description</th>
                                <th class="ui-widget-header">Mon</th>
                                <th class="ui-widget-header">Tue</th>
                                <th class="ui-widget-header">Wed</th>
                                <th class="ui-widget-header">Thu</th>
                                <th class="ui-widget-header">Fri</th>
                                <th class="ui-widget-header">Sat</th>
                                <th class="ui-widget-header">Sun</th>
                                <th class="ui-widget-header" colspan="2">Action</th>                     
                            </tr></thead>       
                        <tbody></tbody>
                        <tfoot><tr>
                                <td colspan="3">Total</td>
                                <td class="mon-total">0</td>
                                <td class="tue-total">0</td>
                                <td class="wed-total">0</td>
                                <td class="thurs-total">0</td>
                                <td class="fri-total">0</td>
                                <td class="sat-total">0</td>
                                <td class="sun-total">0</td>
                                <td class="all-total">0</td>
                            </tr></tfoot>
                    </table>
                    <div><label>Comment:</label><input type="text" name="comment" style="width: 80%;"></div>
                    <div id="button-container" class="right-aln"></div>
                </div>
            </div>
        </div>
</div>
<div class="ui-layout-west" style="display: none;">
	<h3 class="ui-widget-header">East Pane111</h3>
	<div class="ui-layout-content">
            <div id="datepicker"></div>
        </div>
        <%= session.getAttributeNames()%>
        <div id="navcontainer" class="main-menu navitem-list ui-widget-content ui-state-hover">
            <div id="navseparator">&nbsp;</div>
            <ul class="nav-menu">
                <li class="hidden"><a id="linkDashboard" href="#" title="Dashboard" class="dashboard tab"><span class="body">Dashboard</span></a></li>     
                <li class="hidden"><a id="linkUserManagement" href="#" title="User Management " class="user tab"><span class="body">User Management</span></a></li> 
                <li class="hidden"><a id="linkProjectManagement" href="#" title="Project Management " class="project tab"><span class="body">Project Management</span></a></li>
                <li class="hidden"><a id="linkTimesheet" href="#" title="Timesheet Management" class="timesheet tab"><span class="body">Timesheet Management</span></a></li>       
            </ul>
	</div>
</div>
<%="${requestScope.role}" %>
<div class="ui-layout-south ui-widget-content ui-state-error" style="display: none;"> South Pane </div>
<div id="modal-win" style="display: none;"></div>
<script type="text/javascript">
    var page="ADMIN";
</script>
<!-- REQUIRED scripts for layout widget -->
	<script type="text/javascript" src="scripts/lib/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="scripts/lib/jquery/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="scripts/lib/plugin/jquery.layout-latest.js"></script>
        <script type="text/javascript" src="scripts/lib/jquery/themeswitchertool.js"></script> 
	<script src="scripts/lib/plugin/jquery.dataTables.js"></script>
        <script src="scripts/lib/plugin/select2.min.js"></script>
        <script src="scripts/app/util/ajax.js"></script>
        <script src="scripts/app/util/Constants.js"></script>
        <script src="scripts/app/user/user.js"></script>
        <script src="scripts/app/project/project.js"></script>
        <script src="scripts/app/timesheet/timesheet.js"></script>
	<script src="scripts/app.js"></script>
</body>
</html> 
