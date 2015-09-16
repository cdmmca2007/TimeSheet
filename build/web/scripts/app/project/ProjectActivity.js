
(function(self){
    var dataTable = null;
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
    };
    function reloadGrid(row){
       dataTable.fnReloadAjax({
                url:'service/projectactivity/'+row.projectId,
                "type": "GET"
       });
    };
    function addActualActivityProgressForm(selectedProject,data){
        var width=238;
        var win = $("#modal-win");
        $("#modal-win .modal-inner-panel").load("forms/addActivitProgressForm.html",
            function(){
                if(data!=null){ 
                  $("#modal-win #pactivity").val(data.activityname+' - '+ data.activitycode);
                  $("#modal-win #tot_hour").val(data.tot_hour);
               };
            });
             
        var  buttons= [{
            text:"Save",
            click: function() {
                if(buttons[0].text=="Save"){
                    var obj = {};
                    var arr = $("#modal-win form").serializeArray();
                    for(var i=0; i<arr.length; i++){
                        obj[arr[i].name] = arr[i].value;
                    }
                    obj.projectId = selectedProject.projectId;
                    obj.activityId=data.activityid;
                    var self = this;
                    $.ajaxService({
                        url:"service/actualprogress",
                        type:'POST',
                        data:JSON.stringify(obj),
                        contentType: "application/json",
                        dataType:'json',
                        success: function(res){
                            if(res==null) {   
                            jQuery.MsgBox.show({
                                     title:'Info',
                                     msg :'Actual Project Activity Progress details updated Successfully',
                                     icon:1,
                                     width:400
                            });                        
                            reloadGrid(selectedProject);
                            }
                            else{
                            jQuery.MsgBox.show({
                                    title:'Error',
                                     msg :'Unexpected Error Occured, Please contact Admin',
                                     icon:4,
                                     width:400
                             });    
                            }
                        }
                    });
                }
            }
        },{
            text:"Cancel",
            click: function() {
                $( this ).dialog( "close" );
            }
        }];   
        $("#modal-win").dialog({
            title:"Update Actual Project Activity Progress",
            modal:true,
            width:450,
            height:400,
            buttons:buttons
        }); 
    }
    function showAddActivityForm(selectedProject,data){
        var width=238;
        var win = $("#modal-win");
        $("#modal-win .modal-inner-panel").load("forms/addActivitToProj.html",
            function(){
                
                $.ajaxService({
                      url:'service/generic/GET_ACTIVITIES',
                      success: function(data){
                           $("#modal-win #pactivity").select2({
                            placeholder:"Select Activity",
                            data:data,
                            width:width
                           });
                      }
                });
                if(data!=null){ 
                  $("#modal-win #pactivity").val(data.activityid);
                  $("#modal-win #tot_hour").val(data.tot_hour);
                  $("#modal-win #comment").val(data.comment);
                  //$("#modal-win #pactivity").hide();
               };
            });
             
        var  buttons= [{
            text:"Save",
            click: function() {
                if(buttons[0].text=="Save"){
                    var obj = {};
                    var arr = $("#modal-win form").serializeArray();
                    for(var i=0; i<arr.length; i++){
                        obj[arr[i].name] = arr[i].value;
                    }
                obj.projectId = selectedProject.projectId;
                obj.activityId=$("#modal-win input[name=pactivity]").select2('val');
                    var self = this;
                    $.ajaxService({
                        url:data?"service/editactivity":"service/activity",
                        type:'POST',
                        data:JSON.stringify(obj),
                        contentType: "application/json",
                        dataType:'json',
                        success: function(res){
                            if(data!=null){
                            jQuery.MsgBox.show({
                                     title:'Info',
                                     msg :'Project Activity detail updated Successfully',
                                     icon:1,
                                     width:400
                            });
                            }
                            else
                            {
                            //Kamlesh Plese verify this    
                            //var result1=res.data;  
                            //var cur_row=result1[0];
                            if(/*cur_row.preexistence!=null && cur_row.preexistence*/2=='1')    
                            jQuery.MsgBox.show({
                                     title:'Warning',
                                     msg :'Project Activity is already present in Project',
                                     icon:4,
                                     width:400
                            });                        
                            else    
                            jQuery.MsgBox.show({
                                     title:'Info',
                                     msg :'Project Activity details added Successfully',
                                     icon:1,
                                     width:400
                            });                        
                            }
                            reloadGrid(selectedProject);
                        }
                    });
                }
            }
        },{
            text:"Cancel",
            click: function() {
                $( this ).dialog( "close" );
            }
        }];   
        $("#modal-win").dialog({
            title:data?"Edit Project Details":"Add New Project",
            modal:true,
            width:450,
            height:400,
            buttons:buttons
        });
    };
    function setResurceFrm(data){
         $("#resourcemapping").dataTable({
            "bLengthChange": false,
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
    self.ProjectActivity = function(grid, id, selectedProject){
        dataTable=grid.dataTable({
            //            'dom':'<"toolbar">frtip',
            //            "info": false,
            "bLengthChange": false,
            "scrollY": "1px",
            //            //"bFilter": false,
            //            "bPaginate": false,
            //            "bAutoWidth":true,
            "ajax": {
                url:"service//projectactivity/"+selectedProject.projectId,
                "type": "GET"
            },
            "columns": [{
                "data": 'activityid',
                "visible":false
                },
                {
                "data": 'activityname',
                title:"Activity Name",
                render:function(val){
                    return "<b>"+val+"</b>";
            }
            }, {
                "data": 'activitycode',
                title:"Activity Code"
            },{
                "data": 'tot_hour',
                title:"Tot Hour",
                width:'15%'
            },{
                "data": 'modified_on',
                title:"Modified Date",
                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    return dat.getDate()+"/"+dat.getMonth()+"/"+dat.getFullYear();
              }
            },{
                "data": 'modified_by',
                title:"Modified By",
                width:'15%',
                "visible":false
            },{
                "data": 'comment',
                title:"Comment"
            }
            ]
        });

        var toolbar = "<button class='addAct'><span>Add Activity</span></button>"+
        "<button class='editAct'><span>Edit Activity</span></button>"+                
        "<button class='delAct'><span>Remove Activity</span></button>"+
        "<button class='progAct'><span>Actual Progress</span></button>";
        var $tbar =$("#project-activity-grid_"+selectedProject.projectId+"_filter").prepend(toolbar); 
        dataTable.find('tbody').on( 'click', 'tr', function () {
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
        $tbar.find('.addAct').button({
            icons: {
		primary: "ui-icon-plus"
	     }

            }).on('click', function(){
            showAddActivityForm(selectedProject,null);
        });
        $tbar.find('.progAct').button({
            icons: {
		primary: "ui-icon-plus"
	     }
            }).on('click', function(){
            var tr = dataTable.find('tr.row-selected');  
            if(tr.length!=0){
                var idx = dataTable.fnGetPosition(tr[0]);
                var data = dataTable.fnGetData(idx);
                addActualActivityProgressForm(selectedProject,data);
            }   
            else
               jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select Activity from Grid to Enter Actual Progress Details',
                        icon:4,
                        width:400
               }); 
                
        });
        $tbar.find('.editAct').button().click( function () {
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select the Resource from Grid to Edit Details',
                        icon:4,
                        width:400
                });
            }
            else {
                var idx = dataTable.fnGetPosition(tr[0]);
                var data = dataTable.fnGetData(idx);
                if(data!=null)
                    showAddActivityForm(selectedProject,data);
                else
                    jQuery.MsgBox.show({
                        title:'Warning',
                        msg :'Please select user from Grid to Edit Details',
                        icon:4,
                        width:400
                    });

            }  // End of Else
        });
        $tbar.find('.delAct').button().click( function () {
            
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select the Project from Grid to Delete',
                        icon:4,
                        width:400
                });
            }
            else {
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            var r = confirm("Are you sure want to Delete "+data.activityname+" Activity from Project. Please make sure in case of activity delete later you will not be able to view the activity or activity details");
            if (r == true) { 
            $.ajax({    
                        url:"service/projectactivity/"+selectedProject.projectId+'/'+data.activityid,
                        type:'PUT',
                        dataType:'json',
                        success: function(res){
                            if(res.errorMessage=='2'){
                              jQuery.MsgBox.show({
                                    title:'Error',
                                     msg :"Activity you are trying to remove from Project is associated with timesheet data , Hence can't be Deleted",
                                     icon:4,
                                     width:400
                             });                              
                              
                            }
                            else if(res.errorMessage=='1'){
                              jQuery.MsgBox.show({
                                    title:'Info',
                                     msg :'Activity removed Successfully',
                                     icon:1,
                                     width:400
                             });
                             reloadGrid(selectedProject);
                            }
                            else if(res.errorMessage=='0'){
                              jQuery.MsgBox.show({
                                    title:'Error',
                                     msg :'Unexpected Error Occured, Please contact Admin',
                                     icon:4,
                                     width:400
                             });
                              
                            }
                                                      
                        }
             });
           }  
           }  // End of Else
        });
        fitToParent(dataTable);
    };	
})(this);
