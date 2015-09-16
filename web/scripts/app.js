require.config({
    paths:{
        jq:"lib/jquery/jquery-1.9.1.min",
	jqUi:"lib/jquery/ui/jquery-ui",
	jqLayout:"lib/plugin/jquery.layout-latest",
        datatables:"lib/plugin/jquery.dataTables",
        dataTablesui:"lib/plugin/jquery.dataTablesui",
        S2:"lib/plugin/select2.min",
        Ajax:"app/util/ajax",
        Util:"app/util/util",
        Constants:"app/util/Constants",
        NavigationController:"app/controller/NavigationContrller",
        User:"app/user/user",
        Project:"app/project/project",
        ProjectResource:"app/project/ProjectResource",
        ProjectActivity:"app/project/ProjectActivity",         
        ProjectActivityReport:"app/project/ProjectActivityReport",
        ProjectMemberList:"app/project/ProjectMemberList",               
        timesheet:"app/timesheet/timesheet",
        timesheetReport:"app/timesheet/timesheetreport",
        fnReloadAjax:"lib/jquery/fnReloadAjax",
        D3:"lib/d3/d3.v3.min",
        bullet:"lib/d3/d3.bullet",
        sha:"app/util/sha"
    },
    shim:{
        jqUi:{
          deps:['jq'],
          exports:'jqUi'
        },
//        D3:{
//            exports:'D3'
//        },
//        bullet:{
//            deps:['d3'],
//            exports:'bullet'
//        },
        Ajax:{
          deps:['jq'],
          exports:'Ajax'
        },
        Util:{
          deps:['jq','jqUi'],
          exports:'Util'
        }
    }
});
function LOG(a,b){
    console.log(a,b)
}
require(['jq','jqUi','Ajax','Util'], function(jQuery, jqUi,Ajax,Util){
    $.LoadMask.show(); 
    Tabs = [];
    $.users = $.users || {};
    $.users.role = getCookie('zrole'); 
    getUsersDetails();
    $( document ).ajaxStart(function() {
        $.LoadMask.show();			
    });
    $( document ).ajaxStop(function() {
        $.LoadMask.hide();
    });
    function admin(){
        var nc=NavigationContrller();
        $( "#datepicker" ).datepicker();
        myLayout = $('body').layout({
            //        applyDefaultStyles: true,
            west__size:240,
            spacing_open:1,
            spacing_close:1,
            //north_closable:false,
            //sizable:false,
            west__sizable:6,
            west__spacing_open:6,
            west__spacing_close:6
        });
        $Tabs=$("#tabs_div").tabs({
            heightStyle:'fill',
            tabTemplate:'<LI><A href="#{href}">#{label}<span class="ui-icon ui-icon-close"></span></A></LI>',
            add: function(e, ui) {
                $(ui).find('span.ui-icon-close')
                .click(function(ev) {
                    ev.preventDefault();
                    $Tabs.tabs('remove', $(ui).index());
                });
            },
            load:onTabLoad
        });
        $('#Tab_Dashboard').layout({       
            north__size:'50%'
        });
    
        function resizeLayout(){
            fitToParent($("#project-grid"));
            fitToParent($("#all-timesheet-report-grid"));
        //$("#project-grid").fnAdjustColumnSizing();
        }
        resizeLayout();
        //setTimeout( myLayout.resizeAll, 1000 ); /* allow time for browser to re-render with new theme */
        $( document ).tooltip();
    }
    function manager(){
        var nc=NavigationContrller();
        $( "#datepicker" ).datepicker();
        myLayout = $('body').layout({
            spacing_open:2,
            spacing_close:2
        });
        $Tabs=$("#tabs_div").tabs({
            heightStyle:'fill',
            tabTemplate:'<LI><A href="#{href}">#{label}<span class="ui-icon ui-icon-close"></span></A></LI>',
            add: function(e, ui) {
                $(ui).find('span.ui-icon-close')
                .click(function(ev) {
                    ev.preventDefault();
                    $Tabs.tabs('remove', $(ui).index());
                });
            },
            load:onTabLoad          
        });
        $('#Tab_Dashboard').layout({
            west__paneSelector:'.ui-layout-one',
            north__size:220
        });
        fitToParent($("#allTimesheet table"));
        //setTimeout( myLayout.resizeAll, 1000 ); /* allow time for browser to re-render with new theme */
        $( document ).tooltip();
    }
    function accountant(){
        var nc=NavigationContrller();
        $( "#datepicker" ).datepicker();
        myLayout = $('body').layout({
            west__size:240,
            spacing_open:2,
            spacing_close:2,
            //north_closable:false,
            //sizable:false,
            west_sizable:8,
            west__spacing_open:8,
            west__spacing_close:8
        });
        $Tabs=$("#tabs_div").tabs({
            heightStyle:'fill',
            tabTemplate:'<LI><A href="#{href}">#{label}<span class="ui-icon ui-icon-close"></span></A></LI>',
            add: function(e, ui) {
                $(ui).find('span.ui-icon-close')
                .click(function(ev) {
                    ev.preventDefault();
                    $Tabs.tabs('remove', $(ui).index());
                });
            },
            load:onTabLoad 
        });
    
        fitToParent($("#all-timesheet-report-grid"));
        $( document ).tooltip();
        setTimeout( myLayout.resizeAll, 1000 ); /* allow time for browser to re-render with new theme */
        
    }
    function employee(){
        var nc=NavigationContrller();
        $( "#datepicker" ).datepicker();
        myLayout = $('body').layout({
            spacing_open:2,
            spacing_close:2
        });
        $Tabs=$("#tabs_div").tabs({
            heightStyle:'fill',
            tabTemplate:'<LI><A href="#{href}">#{label}<span class="ui-icon ui-icon-close"></span></A></LI>',
            add: function(e, ui) {
                $(ui).find('span.ui-icon-close')
                .click(function(ev) {
                    ev.preventDefault();
                    $Tabs.tabs('remove', $(ui).index());
                });
            }          
        });
        $('#Tab_Dashboard').layout({
            west__paneSelector:'.ui-layout-one',
            west__size:320
        });
        $('#tabs_div ul li a span').html("My TimeSheet");
        fitToParent($("#myTimesheet table"));
        //setTimeout( myLayout.resizeAll, 1000 ); /* allow time for browser to re-render with new theme */
        $( document ).tooltip();
    }
    var role =getRole();
    require(['jqLayout','datatables','dataTablesui','S2','NavigationController','sha'],
        function(jqLayout,dataTables,dataTablesui,S2,NavigationController,sha){
            if(role==1){
                require(['Project','timesheet','timesheetReport','fnReloadAjax'],
                    function(Project,timesheet,timesheetReport,fnReloadAjax){
                        $("#Tab_Dashboard").load("html/admin.html", admin);
                });
            
            }else if(role==2){
                require(['Project','timesheet','timesheetReport','fnReloadAjax'],
                    function(Project,timesheet,timesheetReport,fnReloadAjax){
                        $("#Tab_Dashboard").load("html/AllTimesheet.html", manager);
                    });
            }else if(role==3){
                require(['timesheet','timesheetReport','fnReloadAjax'],
                    function(timesheet,timesheetReport,fnReloadAjax){
                        $("#Tab_Dashboard").load("html/TimesheetReport.html", accountant);
                    });
            } else if(role==4){
                require(['timesheet','fnReloadAjax'],
                    function(timesheet,fnReloadAjax){
                        $("#west-panel").remove();
                        $("#Tab_Dashboard").load("html/MyTimesheet.html", employee);
                    })
            }
        })
    $(".modal-mask").on('click', function(){
        $(".modal-mask").hide()
        $(".modal-win").slideToggle(500);
    }); 
   
   (function(){
    $.ajaxService({
            url:'service/generic/GET_PROJ_LOC',
            success: function(data){
                $.projectLocation = data;
            }
     });
    })();
})


function getCookie(c_name){
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) 
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}
function getRole(){
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf("zrole=");
        if (c_start != -1) {
            c_start = c_start + "zrole".length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) 
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}


function getUsersDetails(){
    $.ajaxService({
        url:"service/user/fulldetais",
        type:'GET',
        dataType:'json',
        success: function(data){
            //$.users={};
            $.users.name=data.name;
            $.users.role=data.role;
            $.users.userId=data.userId;   
            $.projectArray = data.projects;
            $('.link-username').text($.users.name);
        }
    });
};

function onTabLoad(event,ui){
            if(ui.panel.selector=="#Tab_AllTimesheet" || ui.panel.selector=="#Tab_MyTimesheet"){
                if(ui.panel.selector=="#Tab_MyTimesheet"){
                    $(ui.panel.selector).layout({
                        north__paneSelector:'.ui-layout-one',
                        north__size:203
                    });
                    fitToParent($("#myTimesheet table"));
                }
                if(ui.panel.selector=="#Tab_AllTimesheet"){
                     $(ui.panel.selector).layout({
                        north__size:203
                    });
                    fitToParent($("#allTimesheet table"));
                }
            } else if(ui.panel.selector=="#Tab_ProjectManagement"){
                fitToParent($("#project-grid"));
            }
}   

