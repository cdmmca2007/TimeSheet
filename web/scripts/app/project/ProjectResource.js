
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
    function reloadGrid(selectedProject){
       dataTable.fnReloadAjax({
                 url:"service/member/"+selectedProject.projectId,
                 type: "GET"
       });
    };
    function showAddResourceForm(selectedProject,data){
        var width=238;
        var win = $("#modal-win");
        $("#modal-win .modal-inner-panel").load("forms/addResourceToProj.html",
            function(){
                
                $.ajaxService({
                      url:'service/generic/GET_RESOURCE',
                      success: function(data){
                           $("#modal-win #presources").select2({
                                placeholder:"Select Employee",
                                data:data,
                                width:width
                           });
                      }
                });
                $.ajaxService({
                      url:'service/generic/GET_PROJECT_ROLES',
                      success: function(data){
                           $("#modal-win #proleId").select2({
                            placeholder:"Select Project Role",
                            data:data,
                            width:width
                           });
                      }
                });
                $("#modal-win form .datepicker").datepicker();
                $("#modal-win form .datepicker_1").datepicker();
                if(data!=null){ 
                  var sdate=new Date(data.startDate);
                  var startDate=sdate.getDate()+"/"+sdate.getMonth()+"/"+sdate.getFullYear();
                  var edate=new Date(data.endDate);
                  var endDate=edate.getDate()+"/"+edate.getMonth()+"/"+edate.getFullYear();
                  $("#modal-win #presources").val(data.userId);
                  $("#modal-win #proleId").val(data.role);
                  $("#modal-win #pstartDate").val(startDate);
                  $("#modal-win #pendDate").val(endDate);
                  $("#modal-win #comment").val(data.comment);
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
		obj.startDate = $(".datepicker").datepicker('getDate').getTime(); 
                obj.endDate = $(".datepicker_1").datepicker('getDate').getTime(); 
                obj.projectId = selectedProject.projectId;
                obj.userId=$("#modal-win input[name=presources]").select2('val');
                obj.roleId=$("#modal-win input[name=proleId]").select2('val');
                
                if(data!=null) 
                    obj.resourceId=data.resourceId;
                 
                    var self = this;
                    $.ajaxService({
                        url:data?"service/editmember":"service/member",
                        type:'POST',
                        data:JSON.stringify(obj),
                        contentType: "application/json",
                        dataType:'json',
                        success: function(data1){
                            if(data!=null)
                            jQuery.MsgBox.show({
                                     title:'Info',
                                     msg :'Project Resource detail updated Successfully',
                                     icon:1,
                                     width:400
                             });
                            else
                            jQuery.MsgBox.show({
                                     title:'Info',
                                     msg :'Project Resource Details Added Successfully',
                                     icon:1,
                                     width:400
                             });          
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
    self.ProjectResource = function(grid, id, selectedProject){
        dataTable=grid.dataTable({
            //            'dom':'<"toolbar">frtip',
            //            "info": false,
            "bLengthChange": false,
            "scrollY": "1px",
            //            //"bFilter": false,
            //            "bPaginate": false,
            //            "bAutoWidth":true,
            "ajax": {
                url:"service/member/"+selectedProject.projectId,
                "type": "GET"
            },
            "columns": [{
                "data": 'resourceId',
                "visible":false
                },{
                "data": 'userId',
                "visible":false
                },
                {
                "data": 'userName',
                title:"Name",
                render:function(val){
                    return "<b>"+val+"</b>";
            }
            }, {
                "data": 'role',
                title:"Role in Project",
                render:function(val){
                    if(val=='1' || val=='100')
                    return "<font color=red><b>Project Manager</b></font>";
                    else
                    return "<b>Team Member</b>";    
            }
            },{
                "data": 'designation',
                title:"Designation",
                width:'15%'
            },{
                "data": 'startDate',
                title:"Start Date",
                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    return (dat.getDate())+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
            }
            },{
                "data": 'endDate',
                title:"End Date",
                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    return (dat.getDate())+"/"+(dat.getMonth()+1)+"/"+dat.getFullYear();
            }
            },{
                "data": 'comment',
                title:"Comment"
            }
            ]
        });
        var toolbar = "<button class='addResrce'><span>Add Resource</span></button>"+
        "<button class='editResrce'><span>Edit Resource</span></button>"+                
        "<button class='delResrce'><span>Remove Resource</span></button>";
        var $tbar = $("#project-res-grid_"+selectedProject.projectId+"_filter").prepend(toolbar); 
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
        $tbar.find('.addResrce').button({
            icons: {
		primary: "ui-icon-plus"
	     }
            }).on('click', function(){
            showAddResourceForm(selectedProject,null);
        });
        $tbar.find('.editResrce').button().click( function () {
            
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
              showAddResourceForm(selectedProject,data);
            else

              jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select user from Grid to Edit Details',
                        icon:4,
                        width:400
                });

           }  // End of Else
        });
        $tbar.find('.delResrce').button().click( function () {
            
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
          
            var r = confirm("Are you sure want to Delete "+data.userName+". Please make sure in case of project delete later you will not be able to view the project or project details");
            if (r == true) { 
            $.ajax({
                        url:"service/member/"+data.resourceId,
                        type:'DELETE',
                        dataType:'json',
                        success: function(res){
                            if(res.errorMessage=='1')
                            {    
                            jQuery.MsgBox.show({
                                                   title:'Info',
                                                    msg :'Project Memeber Deleted Successfully',
                                                    icon:1,
                                                    width:400
                                            });                        
                             reloadGrid(selectedProject);               
                            }                
                            else
                            jQuery.MsgBox.show({
                                    title:'Error',
                                     msg :'Unexpected Error occured , Please contact Technical Admin',
                                     icon:4,
                                     width:400
                             });
                        }
             });
           }  
           }  // End of Else
        });
        fitToParent(dataTable);
    };	
})(this);
