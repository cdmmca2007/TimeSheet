window.projectdet=null;
window.projectdet1=null;

(function(self){
   var dataTable =null; 
   function initCombo(name,q,op){
        $.ajaxService({
            url:'service/generic/'+q,
            success: function(data){
                var d =$.extend({
                    data:data
                },op);
                $("#modal-win input[name="+name+"]").select2(d);
            }
        });
    }
    function showProjectDetails(d){
         
        require(['ProjectActivityReport','ProjectMemberList','bullet'],
            function(projectActivityReport,projectMemberList,Bullet){
                $.ajaxService({
                    url:'service/project/'+d.projectId,
                    success: function(res){
                        console.log(res);
                        var data=res[0];
                        Tabs.push(data);
                        var id = "ProjDetail_"+data.projectId;
                        if($("#"+id).length==0){
                            $Tabs.tabs( "add", "html/ProjectDetails.html",data.projectName,id);
                            var idx = $("ul.ui-tabs-nav li").length-1;
                            $Tabs.tabs("option","active", idx ); 
                        } else{
                            $Tabs.tabs( "option","active", $("#"+id).index()-1 );
                        }
                    }
                });
            });
    } 
    function showProjectResource(data){
        require(['ProjectResource'],
            function(projectResource){
                var id = "#ProjResourcePanel_"+data.projectId;
                if($(id).length==0){
                    $Tabs.tabs( "add",id,data.projectName+" Team");
                    var grid = $('<table  id="project-res-grid_'+data.projectId+'"></table>').appendTo($(id))
                    var idx = $("ul.ui-tabs-nav li").length-1;
                    $Tabs.tabs("option","active", idx ); 
                    new ProjectResource(grid, idx, data);
                } else{
                    $Tabs.tabs( "option","active", $(id).index()-1 );
                }  
            }); 
    }
    function showProjectActivity(data){
        require(['ProjectActivity'],
            function(projectActivity){
                var id = "#ProjActivityPanel_"+data.projectId;
                if($(id).length==0){
                    $Tabs.tabs( "add",id,data.projectName+" Activity");
                    var grid = $('<table  id="project-activity-grid_'+data.projectId+'"></table>').appendTo($(id))
                    var idx = $("ul.ui-tabs-nav li").length-1;
                    $Tabs.tabs("option","active", idx ); 
                    new ProjectActivity(grid, idx, data);
                } else{
                    $Tabs.tabs( "option","active", $(id).index()-1 );
                }  
            });
    }
    function reloadGrid(){
       dataTable.fnReloadAjax({
                               url:'service/project',
                               "type": "GET"
       });
    }
    function showCreateForm(data){
        var win = $("#modal-win");
        $("#modal-win .modal-inner-panel").load("forms/addProject.html",function(){
                initCombo('resources','GET_RESOURCE',{
                    placeholder:"Select Resources",
                    multiple:true,
                    width:235
                });
                initCombo('activities','GET_ACTIVITIES',{
                    placeholder:"Select Activities",
                    multiple:true,
                    width:235
                });
                initCombo('projectManager','GET_MANAGER',{
                    placeholder:"Select Manager",
                    width:235
                });
                initCombo('projectPriority','GET_PROJ_PRTY',{
                    placeholder:"Select Priority",
                    width:235
                });
                initCombo('projectType','GET_PROJ_TYP',{
                    placeholder:"Select Project Type",
                    width:235
                });
                initCombo('projectStatus','GET_PROJ_STATUS',{
                    placeholder:"Select Project status",
                    width:235
                });
                
             
                $("#modal-win form .dtpicker").datepicker({
                    buttonImage: "resources/images/calendar.gif"
                });
               if(data!=null){ 
                  var sdate=new Date(data.startDate);
                  var startDate=sdate.getDate()+"/"+(sdate.getMonth()+1)+"/"+sdate.getFullYear();
                  var edate=new Date(data.endDate);
                  var endDate=edate.getDate()+"/"+(edate.getMonth()+1)+"/"+edate.getFullYear();
                 
                  $("#modal-win #projectName").val(data.projectName);
                  $("#modal-win #projectCode").val(data.projectCode);
                  $("#modal-win #startDate").val(startDate);
                  $("#modal-win #endDate").val(endDate);
                  $("#modal-win #clientName").val(data.clientName);
                  $("#modal-win #contactNo").val(data.contactNo);
                  $("#modal-win #address").val(data.address);
                  $("#modal-win #projectStatus").val(data.projectStatus);
                  $("#modal-win #projectPriority").val(data.projectPriority);
                  $("#modal-win #projectType").val(data.projectType);
                  $("#modal-win #projectManager").val(data.projectManager);
                  $("#modal-win #description").val(data.description);
                  $("#modal-win #projectValue").val(data.projectvalue);
               };
            });
        $("#modal-win").dialog({
            title:data?"Edit Project Details":"Add New Project",
            modal:true,
            width:800,
            height:420,
            buttons: {
               "Save":function() {
                    var valid =isValidForm("#dialog-form");
                    if(valid.valid){
                        var obj = {};
                        var arr = $("#modal-win form").serializeArray();
                        for(var i=0; i<arr.length; i++){
                            obj[arr[i].name] = arr[i].value;
                        }
                        obj.startDate = $(".startdate").datepicker('getDate').getTime();    
                        obj.endDate = $(".enddate").datepicker('getDate').getTime(); 
                        if(data!=null)
                            obj.projectId=data.projectId;
                        
                        var self = this;
                        $.ajaxService({
                            url:data?"service/editProj":"service/project",
                            type:'POST',
                            data:JSON.stringify(obj),
                            contentType: "application/json",
                            dataType:'json',
                            success: function(res){
                               
                                if(data==null) { 
                                   
                                   if(res!=null) {  
                                       jQuery.MsgBox.show({
                                        title:'Info',
                                        msg :'Project detail added Successfully',
                                        icon:1,
                                        width:400
                                    });
                                   } 
                                   else {
                                   jQuery.MsgBox.show({
                                    title:'Error',
                                     msg :'Unexpected Error occured , Please contact Technical Admin',
                                     icon:4,
                                     width:400
                                   }); 
                                   }
                               }
                                else{
                                       jQuery.MsgBox.show({
                                        title:'Info',
                                        msg :'Project detail updated Successfully',
                                        icon:1,
                                        width:400
                                    });
                                 
                               }
                               reloadGrid();      
                            }
                        });
                    }else{
                        jQuery.MsgBox.show({
                                 title:'Error',
                                 msg :valid.msg,
                                 icon:3,
                                 width:400
                         });
                    }
                
                },
                    Cancel: function() {
                        $( this ).dialog( "close" );
                    }
                }
        });
    };
    function setResurceFrm(data){
         $("#resourcemapping").dataTable({
            //            'dom':'<"toolbar">frtip',
            //            "info": false,
            "bLengthChange": false,
            //            //"bFilter": false,
            //            "bPaginate": false,
            //            "bAutoWidth":true,
            data:data,
            "columns": [{
                data: 'userName',
                title:"Member Name"
            }, {
                data: 'role',
                title:"Role",
                "visible":false
            }, {
                data: 'startDate',
                title:"Start Date"
            },{
                data: 'endDate',
                title:"End Date"
            }]
        });
    }
    self.Proect = function(){
       
       dataTable=$("#project-grid").dataTable({
            //            'dom':'<"toolbar">frtip',
            //            "info": false,
            "scrollY": "1px",
            "bLengthChange": false,
            //            //"bFilter": false,
            //            "bPaginate": false,
            //            "bAutoWidth":true,
            "fnRowCallback": function( nRow, aData ) {
                var status = aData.projectStatus; 
                var $nRow = $(nRow); 
                if (status == '601') {
                  $nRow.css({"background-color":"#FFF380"});//
                }
                else if (status == '602'){
                  $nRow.css({"background-color":"#8bccf0"});
                }
                else if (status == '603'){
                  $nRow.css({"background-color":"yellowgreen"});
                }
                else if (status == '604'){
                  $nRow.css({"background-color":"#FFF380"});
                }
                else if (status == '605'){
                  $nRow.css({"background-color":"#87AFC7"});
                }
                
                return nRow
              },
            "ajax": {
                url:"service/project",
                "type": "GET"
            },
            "columns": [{
                "data": 'projectName',
                title:"Name",
                render:function(val){
                    return "<b>"+val+"</b>";
            }
            }, {
                "data": 'projectType',
                title:"Type",
                "visible":false
            }, {
                "data": 'projectCode',
                title:"Project Code",
                width:'15%'
            },{
                "data": 'startDate',
                title:"Start Date",
                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    return dat.getDate()+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
            }
            }, {
                "data": 'clientName',
                title:"Client Name",
                "visible":false
            }, {
                "data": 'createdBy',
                title:"createdBy",
                "visible":false
            }, {
                "data": 'createdOn',
                title:"Created On",
                "visible":false
            },{
                "data": 'description',
                title:"Description",
                "visible":false
            },{
                "data": 'endDate',
                title:"End Date",
                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    if(new Date().getTime()>val)
                    return '<font color="red"><b>'+dat.getDate()+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear()+'</b></font>';
                    else
                    return dat.getDate()+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
            }
            },{
                "data": 'isCompleted',
                title:"Completed",
                "visible":false
            },{
                "data": 'projectManager',
                title:"Manager",
                width:'15%',
                "visible":false
            },{
                "data": 'projectManagername',
                title:"Manager",
                width:'15%'
            },{
                "data": 'projectPriority',
                title:"Priority",
                "visible":false
            },{
                "data": 'projStatusname',
                title:"Status",
                width:'15%',
                render:function(val){
                 if(val==null) return "<b>N/A</b>";
                 else
                   return "<b>"+val+"</b>";  
            }
            }]
        });
       dataTable_= dataTable;
        var toolbar = "<button id='addProject'><span>Add</span></button>"+
        "<button id='editProject'><span>Edit</span></button>"+
        "<button id='delProject'><span>Delete</span></button>"+
        "<div class='separator'></div>"+
        "<button id='viewProject'><span>View Details</span></button>"+
        "<div class='dropdown' style='display:inline;'>"+
            "<button id='repProj' type='button' data-toggle='dropdown' aria-haspopup='true' role='button' aria-expanded='false'>"+
                "Reports"+
            "</button>"+
            "<ul class='dropdown-menu' role='menu'>"+
            "<li><a href='#'>PMU</a></li><li><a href='#'>IEU</a></li><li><a href='#'>Progress Report</a></li></ul>"+
         "</div>"+
         "<div class='separator'></div>"+
        "<button id='actProj'><span>Activities</span></button>"+
        "<button id='resrceProj'><span>Resources</span></button>"+
        "<div class='separator'></div>";
        $("#project-grid_filter").prepend(toolbar).before('<span  class="grid-title">Project List</span>'); 
        $('#project-grid tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('row-selected') ) {
                $(this).removeClass('row-selected');
            }
            else {
                dataTable.$('tr.row-selected').removeClass('row-selected');
                $(this).addClass('row-selected');
            }
        } );
 
        $('#button').click( function () {
            dataTable.row('.selected').remove().draw( false );
        } );
        $('#addProject').button({
            icons: {
		primary: "ui-icon-plus"
	     }

        }).on('click', function(){
            showCreateForm(null);
        });
        $('#editProject').button().click( function () {
            var tr = dataTable.find('tr.row-selected');
            if(tr.length>0){
                var idx = dataTable.fnGetPosition(tr[0]);
                var data = dataTable.fnGetData(idx);
                if(data!=null)
                    showCreateForm(data);
            } else
              jQuery.MsgBox.show({
                title:'Warning',
                msg :'Please select a row from Grid to Edit Project Details',
                icon:4,
                width:400
               });

        });
        $('#delProject').button().click( function () {
            
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                jQuery.MsgBox.show({
                        title:'Warning',
                        msg :'Please select the Porject from Grid to Delete',
                        icon:4,
                        width:400
                });
                
            }
            else {
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            var r = confirm("Are you sure want to Delete "+data.projectName+". Please make sure in case of project delete later you will not be able to view the project or project details");
            if (r == true) { 
            $.ajax({
                        url:"service/project/"+data.projectId,
                        type:'DELETE',
                        dataType:'json',
                        success: function(res){
                            if(res!=null) {
                            jQuery.MsgBox.show({
                                title:'Info',
                                 msg :'Project Deleted Successfully',
                                 icon:1,
                                 width:400
                         });
                         }else{
                            jQuery.MsgBox.show({
                                title:'Info',
                                 msg :'Error in Deleting Project details.Please contact Administrator',
                                 icon:1,
                                 width:400
                         }); 
                         }
                         reloadGrid();
                        }
             });
           }  
           }  // End of Else
        });
        $('#viewProject').button().click( function () {
             var tr = dataTable.find('tr.row-selected');
             if(tr.length>0){
                var idx = dataTable.fnGetPosition(tr[0]);
                var data = dataTable.fnGetData(idx);
                if(data!=null)
                    showProjectDetails(data)
            }
            else
              jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select Project from Grid to view Project Details',
                        icon:4,
                        width:400
                });
            
        });
        $('#repProj').button({
             icons: {
		secondary: "ui-icon-triangle-1-s"
        }
        }).click( function (event) {
              
            event.stopPropagation()
            var menu = $( this ).parent().find('ul').show().position({
			my: "left top",
			at: "left bottom",
			of: this
	     });
             $( document ).one( "click", function() {
			menu.hide();
             });
        }).next().hide().menu()
        .find("li").on('click', function(){
            var reportName =$(this).text();
            if(reportName=='PMU')
            {
             var tr = dataTable.find('tr.row-selected');
             var idx = dataTable.fnGetPosition(tr[0]);
             var data = dataTable.fnGetData(idx);
             if(data!=null)
             {
                downloadPMU(data);  
             }     
            }else if(reportName=='Progress Report')
            {

             var tr = dataTable.find('tr.row-selected');
             var idx = dataTable.fnGetPosition(tr[0]);
             var data = dataTable.fnGetData(idx);
             if(data!=null)
             {
                downloadProgressReport(data);  
             }     
            }    
            else {
               jQuery.MsgBox.show({
                        title:'Warning',
                        msg :reportName+' Report is in Dev phase and not avialable for view or download.',
                        icon:4,
                        width:400
              }); 
            }
        });
        $('#resrceProj').button({
           
        }).on('click', function(){
            var tr = dataTable.find('tr.row-selected');
            if(tr.length>0){
                 var idx = dataTable.fnGetPosition(tr[0]);
                 var data = dataTable.fnGetData(idx);
                    if(data!=null)
                        showProjectResource(data);
                 }
            else
              jQuery.MsgBox.show({
                        title:'Warning',
                        msg :'Please select Project from Grid to Add/Edit Project Resource',
                        icon:4,
                        width:400
              });
        });
        $('#actProj').button().on('click', function(){
            var tr = dataTable.find('tr.row-selected');
            if(tr.length>0){
                var idx = dataTable.fnGetPosition(tr[0]);
                var data = dataTable.fnGetData(idx);
                if(data!=null)
                    showProjectActivity(data);
            }
            else
              jQuery.MsgBox.show({
                        title:'Warning',
                        msg :'Please select Project from Grid to Add/Edit Project Activities',
                        icon:4,
                        width:400
                });
        });
        $('#projProgress').button().on('click', function(){
            var tr = dataTable.find('tr.row-selected');
            if(tr.length>0){
                var idx = dataTable.fnGetPosition(tr[0]);
                var data = dataTable.fnGetData(idx);
                if(data!=null)
                    showProjectActivityProgress(data);
            }
            else
              jQuery.MsgBox.show({
                        title:'Warning',
                        msg :'Please select Project from Grid to Add/Edit Project Activities',
                        icon:4,
                        width:400
                });
        });
        //load project data
        
    };	
})(this);


