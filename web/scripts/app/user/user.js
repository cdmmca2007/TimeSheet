
(function(self){
    var dataTable=null;
    function reloadGrid(){
       dataTable.fnReloadAjax({
                url:'service/user',
                "type": "GET"
       });
    }
    var width=239;
     function addUser(data){
            $("#modal-win .modal-inner-panel").load("forms/adduser.html", function(){
               $.ajaxService({
                      url:'service/generic/GET_ROLES',
                      success: function(data){
                           $("#modal-win #roleId").select2({
                               minimumResultsForSearch: -1,
                            placeholder:"Select Role",
                            data:data,
                            width:width
                           });
                      }
             });
                $.ajaxService({
                      url:'service/generic/GET_DESIGNATION',
                      success: function(data){
                           $("#modal-win #designation").select2({
                               minimumResultsForSearch: -1,
                            placeholder:"Select Designation",
                            data:data,
                            width:width
                           });
                      }
                });
                $.ajaxService({
                      url:'service/generic/GET_MANAGER',
                      success: function(data){
                           $("#modal-win #managerid").select2({
                            placeholder:"Select Manager",
                            data:data,
                            width:width
                           });
                      }
                });
                $.ajaxService({
                      url:'service/generic/GET_GROUP',
                      success: function(data){
                           $("#modal-win #groupdept").select2({
                               minimumResultsForSearch: -1,
                            placeholder:"Select Group/Dept",
                            data:data,
                            width:width
                           });
                      }
                });$.ajaxService({
                      url:'service/generic/GET_SUPERRATE_PERCNT',
                      success: function(data){
                           $("#modal-win #superRate").select2({
                               minimumResultsForSearch: -1,
                            placeholder:"Select Super Rate",
                            data:data,
                            width:width
                           });
                      }
                });
                
                $("#modal-win .form-item1").hide();
                
                $("#modal-win select").select2({
                    minimumResultsForSearch: -1,
                    width:width
                });
                 $("#modal-win form .datepicker").datepicker({
                     changeMonth: true,
                    changeYear: true
                 });
                 $("#modal-win form .datepicker_doj").datepicker();
                 $("#modal-win form .datepicker_lwd").datepicker();
                 
                  if(data!=null){ 
                      
                  var doj=new Date(data.doj);
                  doj=doj.getDate()+"/"+doj.getMonth()+"/"+doj.getFullYear();
                  var lwd=new Date(data.lwd);
                  lwd=lwd.getDate()+"/"+lwd.getMonth()+"/"+lwd.getFullYear();
                  var dob=new Date(data.dob);
                  dob=dob.getDate()+"/"+dob.getMonth()+"/"+dob.getFullYear();
                      
                  $("#modal-win #empno").val(data.empno)
                  .prop("disabled",true);
                  $("#modal-win #fname").val(data.fname);
                  $("#modal-win #mname").val(data.mname);
                  $("#modal-win #lname").val(data.lname);
                  $("#modal-win #dob").val(dob);
                  $("#modal-win #emailId").val(data.emailId);
                  $("#modal-win #contactNo").val(data.contactNo);
                  $("#modal-win #address").val(data.address);
                  $("#modal-win #city").val(data.city);
                  $("#modal-win #gender").val(data.gender);
                  $("#modal-win #designation").val(data.designation);
                  $("#modal-win #roleId").val(data.roleId);
                  $("#modal-win #managerid").val(data.managerid);
                  $("#modal-win #personalEmail").val(data.personalEmail);
                  $("#modal-win #emergencyNo").val(data.emergencyNo);
                  $("#modal-win #groupdept").val(data.groupdept);
                  $("#modal-win #doj").val(doj);
                  $("#modal-win #lwd").val(lwd);
                  $("#modal-win #baseRate").val(data.baseRate);
                  $("#modal-win #superRate").val(data.superRate);
                  $("#modal-win #superfund").val(data.superfund);
                  $("#modal-win #tfnno").val(data.tfnno);
                  $("#modal-win #accountno").val(data.accountno);
                  $("#modal-win #bsbno").val(data.bsbno);
                  $("#modal-win #spousename").val(data.spousename);
                  //$("#modal-win #rateperhour").val(data.baseRate + data.superRate);                  
                  };
                  $("#modal-win #superRate").change(function() {
                        var obj=$("#modal-win #superRate");
                        var brobj=$("#modal-win #baseRate");
                        var rateperhour=parseFloat(brobj.val().trim())+(($("#s2id_superRate").text().trim()*brobj.val().trim())/100);
                        $("#modal-win #rateperhour").val(rateperhour);
                  });
            });
            
            $("#modal-win").dialog({
                modal:true,
                width:810,
                height:525,
                title:data?'Edit User':'Add New User',
                buttons: {
                    "Save": function() {
                        var valid =isValidForm("#dialog-form"); 
                        if(valid.valid){
                        var obj = {};
                        var arr = $("#modal-win form").serializeArray();
                        for(var i=0; i<arr.length; i++){
                            obj[arr[i].name] = arr[i].value;
                        }
                        if($(".datepicker").datepicker('getDate')!=null)
                        obj.dob = $(".datepicker").datepicker('getDate').getTime();    
                        else
                        obj.dob = null;
                        if($(".datepicker_doj").datepicker('getDate')!=null)
                        obj.doj = $(".datepicker_doj").datepicker('getDate').getTime();    
                        else
                        obj.doj = null;
                        if($(".datepicker_lwd").datepicker('getDate')!=null)
                        obj.lwd = $(".datepicker_lwd").datepicker('getDate').getTime();    
                        else
                        obj.lwd = null;
                        
                        if(obj.baseRate==null ||obj.baseRate=='')
                          obj.baseRate=0; 
                        if(obj.superRate==null||obj.superRate=='')
                          obj.superRate=0; 
                        
                        if(data!=null)
                        obj.userId=data.userId;
                        $.ajax({
                            url:data?"service/edtusr":"service/user",
                            type:'POST',
                            data:JSON.stringify(obj),
                            contentType: "application/json",
                            dataType:'json',
                            success: function(res){
                                
                               if(data==null) {
                                 if(res.success==true) 
                                    jQuery.MsgBox.show({
                                         title:'Info',
                                         msg :'New User in Application Added Successfully',
                                         icon:1,
                                         width:400
                                   });
                                 else
                                   jQuery.MsgBox.show({
                                    title:'Error',
                                     msg :'Unexpected Error occured , Please contact Technical Admin',
                                     icon:4,
                                     width:400
                                   });  
                               }
                              else
                               jQuery.MsgBox.show({
                                         title:'Info',
                                         msg :'Existing User Details modified Successfully',
                                         icon:1,
                                         width:400
                              });
                             
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
                    }},
                    Cancel: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
    }
    
   function changePass(data){
       
       $("#modal-win .modal-inner-panel").load("forms/changePassword.html", function(){
        });
           
       $("#modal-win").dialog({
                modal:true,
                width:440,
                height:230,
                title:'Change User Password',
                buttons: {
                    "Save": function() {
                        var obj = {};
                        var arr = $("#modal-win form").serializeArray();
                        for(var i=0; i<arr.length; i++){
                            obj[arr[i].name] = arr[i].value;
                        }
                        if(data!=null)
                        obj.userId=data.userId;
                        obj.newpassword=Sha1.hash(obj.newpassword);
                        $.ajax({
                            url:"service/admchngpadwrd",
                            type:'POST',
                            data:JSON.stringify(obj),
                            contentType: "application/json",
                            dataType:'json',
                            success: function(){
                                  jQuery.MsgBox.show({
                                         title:'Info',
                                         msg :'Password Changed Successfully',
                                         icon:1,
                                         width:400
                              });
                            }
                        });
                    },
                    Cancel: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
    }    
    self.User = function(){
        dataTable=$("#user-grid").dataTable({
  //            'dom':'<"toolbar">frtip',
//            "info": false,
            "scrollY": "1px",
            "bLengthChange": false,
//            //"bFilter": false,
            "bPaginate": false,
//            "bAutoWidth":true,
            "ajax": {
                url:"service/user",
                "type": "GET"
            },
            "columns": [
            {
                "data":'userId',
                "visible":false
            },
            {
                "data": 'empno',
                title:"Emp Num",
                width:'12%',
                render:function(val){
                    return "<b>"+val+"</b>";
               }
            },
            {
                "data": 'fullName',
                title:"Name",
                width:'15%',
                render:function(val){
                    return "<b>"+val+"</b>";
             }
            },
            {
                "data": 'gender',
                title:"Gender",
                "visible": false
            },
            {
                "data": 'emailId',
                title:"Email Id",
                width:'15%'
            },
            {
                "data": 'contactNo',
                title:"Contact No",
                width:'13%'
            },
            {
                "data": 'designation',
                title:"Designation",
                "visible": false
            },
            {
                "data": 'designationname',
                title:"Designation",
                width:'15%'
            },
            {
                "data": 'dob',
                title:"DOB",
                "visible": false,
                render:function(val){
                    return new Date(val);
             }
            },
            {
                "data": 'address',
                title:"Address",
                "visible": false
            },
            {
                "data": 'city',
                title:"City",
                width:'10%',
                "visible": false
            },{
                "data": 'managerid',
                "visible": false
            },{
                "data": 'managername',
                title:"Manager",
                width:'15%',
                render:function(val){
                    if(val!=null)
                       return "<b>"+val+"</b>" 
                    else
                       return "<font color=red><b>Not Assigned</b></font>"  
                    
             }
            },
            {
                "data": 'roleId',
                title:"Role",
                "visible": false
            },
            {
                "data": 'rolename',
                title:"Role",
                width:'15%'
            }         
            ]
        });
        var toolbar = "<button id='addUser'><span>Add</span></button>"+
            "<button id='editUser'><span>Edit</span></button>"+
            "<button id='delUser'><span>Delete</span></button>"+
            "<div class='separator'></div>"+
            "<button id='changePass'><span>Change Password</span></button>"+
            "<button id='disableUsr'><span>Deactivate Account</span></button>"+
            "<button id='viewUsr'><span>Full Details</span></button>"+
            "<div class='separator'></div>";

        $("#user-grid_filter").prepend(toolbar);
        $('#user-grid tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('row-selected') ) {
                $(this).removeClass('row-selected');
            }
            else {
                dataTable.$('tr.row-selected').removeClass('row-selected');
                $(this).addClass('row-selected');
            }
        } );
        $('#addUser').button({
            icons: {
		primary: "ui-icon-plus"
	     }

            }).on('click', function(){
            addUser(null);
        });
        $('#editUser').button().on('click', function(){
        var tr = dataTable.find('tr.row-selected');
        if(tr.length>0){
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            if(data!=null){
                addUser(data);
            }
        }      
        else
            jQuery.MsgBox.show({
                title:'Warning',
                msg :'Please select user from Grid to Edit',
                icon:4,
                width:330
            });
    });
        $('#changePass').button().on('click', function(){
            var tr = dataTable.find('tr.row-selected');
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            if(data!=null){
              changePass(data);
            }  
            else
              jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select user from Grid to Change Password',
                        icon:4,
                        width:400
                });
        });
        $('#delUser').button().click( function () {
            
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select the User from Grid to Delete',
                        icon:4,
                        width:400
                });
                
            }
            else {
            var idx = dataTable.fnGetPosition(tr[0]);
            ///LOG("idx",idx);
            var data = dataTable.fnGetData(idx);
            ///LOG("data",data);
            var r = confirm("Are you sure want to Delete "+data.fullName+"'s User Account");
            if (r == true) { 
            $.ajax({
                        url:"service/user/"+data.userId,
                        type:'DELETE',
                        dataType:'json',
                        success: function(res){
                            
                            jQuery.MsgBox.show({
                                title:'Info',
                                 msg :'User Deleted Successfully',
                                 icon:1,
                                 width:400
                         });
                         reloadGrid();
                        }
             });
           }  
           }  // End of Else
           });
        $('#viewUsr').button().on('click', function(){
            var tr = dataTable.find('tr.row-selected');
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            if(data!=null)
              jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Functionality under Developement',
                        icon:4,
                        width:400
                });
            else
              jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select user from Grid to Change Password',
                        icon:4,
                        width:400
                });
        });   
           
        $('#disableUsr').button().click( function () {
            
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                jQuery.MsgBox.show({
                       title:'Warning',
                        msg :'Please select the User from Grid to Delete',
                        icon:4,
                        width:400
                });
                
            }
            else {
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            var r = confirm("Are you sure want to Disable "+data.fullName+"'s User Account");
            if (r == true) {
                $.ajax({
                        url:"service/disableusr/"+data.userId,
                        type:'DELETE',
                        dataType:'json',
                        success: function(res){
                        jQuery.MsgBox.show({
                        title:'Info',
                        msg :'User is Diactivated in Application Successfully',
                        icon:1,
                        width:400
                });
                reloadGrid();
                }
                });
            }
           }  // End of Else
           });
           fitToParent(dataTable);
    };
})(this);
