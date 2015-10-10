(function(self){
   
   
    var visible = false,
    selectedRow=null;
    var $editor=null,$tsEditor = null,tsBody=null,dataTable=null,$grid=null,$dynamic=null,
    $actionHistory=null, $actions;
    var rowTpl = '<tr class="search_main_tr">'+
        '<td><button class="add"></button></td>'+
        '<td><button class="remove"></button></td>'+
        '<td><input name"p" class="button_manage_s select1 proj-drop-down"></td>'+
        '<td><input name="" class="button_manage_s select2 activity-drop-down"> </td>'+
        '<td><input name="" class="button_manage_s select2 location-drop-down"> </td>'+
        '<td><input type="text" class="text1 description required min-length" /></td>'+
        '<td class="td-hours" data-col="mon" ><input type="text" data-column="mon" class="mon hours" /></td>'+
        '<td class="td-hours" data-col="tues" ><input type="text" data-column="tues" class="tues hours" /></td>'+
        '<td class="td-hours" data-col="wed" ><input type="text" data-column="wed" class="wed hours" /></td>'+
        '<td class="td-hours" data-col="thurs" ><input type="text" data-column="thurs" class="thurs hours" /></td>'+
        '<td class="td-hours" data-col="fri"><input type="text" data-column="fri" class="fri hours" /></td>'+
        '<td class="td-hours" data-col="sat" ><input type="text" data-column="sat" class="sat hours" /></td>'+
        '<td class="td-hours" data-col="sun" ><input type="text" data-column="sun" class="sun hours" /></td>'+
        '<td class="sum-row" data-col="sum-row">0</td>'+
    ' </tr>'
    var mgrTpl = '<tr class="search_main_tr">'+
    '<td><input name"p" class="button_manage_s select1 proj-drop-down"></td>'+
    '<td><input name="" class="button_manage_s select2 activity-drop-down"> </td>'+
    '<td><input name="" class="button_manage_s select2 location-drop-down"> </td>'+
    '<td><input type="text" class="text1 description required min-length" /></td>'+
    '<td  class="td-hours" data-col="mon" ><input type="text" data-column="mon" class="mon hours" /></td>'+
    '<td  class="td-hours" data-col="tues" ><input type="text" data-column="tues" class="tues hours" /></td>'+
    '<td  class="td-hours" data-col="wed" ><input type="text" data-column="wed" class="wed hours" /></td>'+
    '<td  class="td-hours" data-col="thurs" ><input type="text" data-column="thurs" class="thurs hours" /></td>'+
    '<td  class="td-hours" data-col="fri"><input type="text" data-column="fri" class="fri hours" /></td>'+
    '<td  class="td-hours" data-col="sat" ><input type="text" data-column="sat" class="sat hours" /></td>'+
    '<td  class="td-hours" data-col="sun" ><input type="text" data-column="sun" class="sun hours" /></td>'+
    '<td  class="sum-row" data-col="sum-row">0</td>'+
    ' </tr>'
   var actionTpl = "<div class='actionTpl ui-widget-content'>"+
       '<div><span class="action-label">Action:</span><span class="user-action value"></span></div>'+
       '<div style="overflow: hidden;"><div class="left" style="width:45%"><span class="action-label">Action By: </span><span class="action-by value"></span></div><div class="right" style="width:150px"><span class="action-label">Action Date: </span><span class="action-on value"></span></div></div>'+
       '<div class="action-table border-box"><table></table></div>'+
       '<div class="div-comment"><span class="action-label">Comment:</span> <span class="action-comment value"></span></div>'+
       "</div>";
    var actionRowTpl = '<tr class="search_main_tr">'+
    '<td><input class="proj-drop-down" /></td>'+
    '<td class="td1"><input class="activity-drop-down" /> </td>'+
    '<td class="td1"><input class="location-drop-down" /></td>'+
    '<td class="td2"><input class="description" /></td>'+
    '<td class="td3" data-col="mon"><input class="mon hours" /></td>'+
    '<td class="td3" data-col="tues"><input class="tues hours" /></td>'+
    '<td class="td3" data-col="wed"><input class="wed hours" /></td>'+
    '<td class="td3" data-col="thurs"><input class="thurs hours" /></td>'+
    '<td class="td3" data-col="fri"><input class="fri hours" /></td>'+
    '<td class="td3" data-col="sat"><input class="sat hours" /></td>'+
    '<td class="td3" data-col="sun"><input class="sun hours" /></td>'+
    '<td class="td3 sum-row" data-col="sum-row" class="sum-row">0</td>'+
    '</tr>'
     function getTpl(){
         return visible?mgrTpl:rowTpl;
     }
     function isSelfTs(){
         return selectedRow.userid==$.users.userId;
     }
     function isValid(){
         //validate drop down
         var valid =true,msg="",filed="" ;
         $table=$editor.find(".timesheet-editor");
         var dd =  $table.find("input.proj-drop-down,input.activity-drop-down,input.location-drop-down");
         for(var i=0;i<dd.length;i++){
             if($(dd[i]).val()===""){
                valid = false;
                msg = "Please fill all required field.";

//               var idx = $(dd[i]).closest('td').index();
//               var th = $editor.find(".timesheet-editor th")[idx];
//               msg = $(th).text();
                break;
             }
         }
         //validate hours
         var inputFields = $table.find(".invalid");
         if(inputFields.length>0){
            valid = false;
            msg = '"'+$(inputFields[0]).val() + '" is invalid hours.Please fill valid hours.'; 
         }
         if(!valid){
               jQuery.MsgBox.show({
                       title:'Warning',
                       msg :msg,
                       icon:3,
                       width:400
               }); 
           }
           return valid;
        
     }
     function setRowValue($tr,obj){
        $tr.find('.proj-drop-down').select2('val',obj.projectId,true);
        $tr.find('.activity-drop-down').select2('val',obj.activityId);
        $tr.find('.location-drop-down').select2('val',obj.locationid);
        $tr.find('.description').val(obj.description);
        $tr.find('.mon').val(obj.mon==0?'':obj.mon);
        $tr.find('.tues').val(obj.tues==0?'':obj.tues);
        $tr.find('.wed').val(obj.wed==0?'':obj.wed);
        $tr.find('.thurs').val(obj.thurs==0?'':obj.thurs);
        $tr.find('.fri').val(obj.fri==0?'':obj.fri);
        $tr.find('.sat').val(obj.sat==0?'':obj.sat);
        $tr.find('.sun').val(obj.sun==0?'':obj.sun);
        var sum=0;
        $.each($tr.find('.hours'), function(i,item){
               var v=$(item).val()
               if(v){
                   sum = sum+parseFloat($(item).val());   
               }
           })
            $tr.find('.sum-row').text(sum);
        
        return obj;
    }
    function getRowValue($tr){
        var obj={}; 
        obj.projectId = $tr.find('.proj-drop-down').select2('val');
        obj.activityId = $tr.find('.activity-drop-down').select2('val');
        obj.locationId = $tr.find('.location-drop-down').select2('val');
        obj.description = $tr.find('.description').val();
        obj.mon = $tr.find('.mon').val()||0;
        obj.tues = $tr.find('.tues').val()||0;
        obj.wed = $tr.find('.wed').val()||0;
        obj.thurs = $tr.find('.thurs').val()||0;
        obj.fri = $tr.find('.fri').val()||0;
        obj.sat = $tr.find('.sat').val()||0;
        obj.sun = $tr.find('.sun').val()||0;
        
        return obj;
    }
   
    function _addRow(data){
        tsBody=$tsEditor.find("tbody");
        var row = $(getTpl()).appendTo(tsBody);
        row.find(".proj-drop-down").select2({
            placeholder:"Select project",
            height:20,
            data:$.projectArray.map(function(item){
                return {
                    id:item.projectId,
                    text:item.projectName
                };         
            })
        }).on("change", function(combo){
            var proj = getProjActivity(combo.val);
            if(proj!=null && proj.length>0) {
            var data = proj.map(function(item){
                return {
                    id:item.activityid,
                    text:item.activityname+' - '+item.activitycode
                };
            })
            data.push({id:'00',text:'General Holiday -00'});
            data.push({id:'100',text:'General Leave -100'})
            row.find(".activity-drop-down").removeClass('select2offscreen').select2({
                data:data,
                dropdownAutoWidth : true,
                placeholder:"Select Activity"
            });
            }
        });
         row.find(".activity-drop-down").select2({
            data:[],
            placeholder:"Select Activity"
        });
        row.find(".location-drop-down").select2({
            data:$.projectLocation,
            placeholder:"Select Location"
        });
//        getCombo(row.find(".location-drop-down"),'GET_PROJ_LOC',{
//                    placeholder:"Select Location"
//        },data?data.locationid:null);
        if(data){
            row.data("data",data);
            setRowValue(row, data);
        }
        
        row.find('.add').on('click', function(ev){
            //event.stopPropagation();
            _addRow();
        });
        row.find('.remove').on('click', function(){
            //event.stopPropagation();
            $(this).closest('tr').remove();
            setTotolRow($dynamic);
        });
        row.find('.hours').on('change', function(){
            $(this).removeClass("invalid");
           var column= $(this).data('column');
           var $total =$tsEditor.find('.'+column+"-total");
           var total = 0;
           $.each($("."+column), function(i,item){
               var v=$(item).val();
               if(v){
                  if(isNaN(v)){
                       $(this).addClass("invalid");
                   }else{
                       total = total+parseFloat(v);
                   } 
               }
           })
            $total.text(total);
            var sum=0;
            $.each(row.find('.hours'), function(i,item){
                var v=$(item).val()
               if(v){
                   if(isNaN(v)){
                       $(this).addClass("invalid");
                   }else{
                      sum = sum+parseFloat($(item).val()); 
                   }
                   
               }
           })
            row.find('.sum-row').text(sum);
            
             //set sum of total
        var sum_total = 0;
        $.each($tsEditor.find(".sum-row"), function(i,item){
               var v = $(item).text();
               if(v){
                  sum_total = sum_total+parseFloat(v); 
               }
           })
           $tsEditor.find('.all-total').text(sum_total);
            
        });
    }
    function _addLogHeader($actionRowTable){
        var header = "<thead><tr>"+                   
                               '<th  class="ui-state-default cols col-2">Project</th>'+
                                '<th  class="ui-state-default cols col-3">Activity</th>'+
                                '<th  class="ui-state-default cols col-4">Location</th>'+
                                '<th class="ui-state-default cols col-5">Description</th>'+
                                '<th class="ui-state-default cols col-6">Mon</th>'+
                                '<th class="ui-state-default cols col-7">Tues</th>'+
                                '<th class="ui-state-default cols col-8">Wed</th>'+
                                '<th class="ui-state-default cols col-9">Thu</th>'+
                                '<th class="ui-state-default cols col-10">Fri</th>'+
                                '<th class="ui-state-default cols col-11">Sat</th>'+
                                '<th class="ui-state-default cols col-12">Sun</th>'+
                                '<th class="ui-state-default cols col-13">Total</th>'+                  
                            '</tr></thead>'
        $(header).appendTo($actionRowTable);
    }
     function _addLogFooter($actionRowTable){
        var header = "<tfoot><tr>"+
                                '<td colspan="4">Total</td>'+
                                '<td class="mon-total">0</td>'+
                                '<td class="tues-total">0</td>'+
                                '<td class="wed-total">0</td>'+
                                '<td class="thurs-total">0</td>'+
                                '<td class="fri-total">0</td>'+
                                '<td class="sat-total">0</td>'+
                                '<td class="sun-total">0</td>'+
                                '<td class="all-total">0</td>'+
                            '</tr></tfoot>'
        $(header).appendTo($actionRowTable);
    }
    
    function _addLogRow(data,body){
        var row = $(actionRowTpl).appendTo(body);
        row.find(".proj-drop-down").select2({
            data:$.projectArray.map(function(item){
                return {
                    id:item.projectId,
                    text:item.projectName
                };         
            })
        }).on("change", function(combo){
           var proj = getProjActivity(combo.val);
           if(proj!=null && proj.length>0) { 
            var data = proj.map(function(item){
                return {
                    id:item.activityid,
                    text:item.activityname
                };
            
            })
            row.find(".activity-drop-down").removeClass('select2offscreen').select2({
                data:data,
                dropdownAutoWidth : true
            });
           }
        });
         row.find(".activity-drop-down").select2({
            data:[]
        });
         row.find(".location-drop-down").select2({
            data:$.projectLocation,
            placeholder:"Select Location"
        });
//        getCombo(row.find(".location-drop-down"),'GET_PROJ_LOC',{
//                    placeholder:"Select Location"
//        },data?data.locationid:null);
        row.data("data",data);
        setRowValue(row, data);
        
        
        
       
    }
   function setTotolRow($el){
        var arr=["mon","tues","wed","thurs","fri","sat","sun"];
        for(var i=0; i<arr.length;i++){
            var selector = "."+arr[i]+".hours";
             var total = 0;
           $.each($el.find(selector), function(i,item){
               var v = $(item).val();
               if(v){
                  total = total+parseFloat(v); 
               }
           })
           $el.find('.'+arr[i]+"-total").text(total);
        }
        
        //set sum of total
        var sum_total = 0;
        $.each($el.find(".sum-row"), function(i,item){
               var v = $(item).text();
               if(v){
                  sum_total = sum_total+parseFloat(v); 
               }
           })
           $el.find('.all-total').text(sum_total);
    }
    function setComments(d){
        if(selectedRow.userid==$.users.userId){
            $dynamic.find("textarea[name=comment]").val(d.comment)
        }else{
            $dynamic.find("textarea[name=userComment]").val(d.comment)
            .prop("disabled",true);
        } 
    }
    function removeEditor(){
        $editor.find(".timesheet-editor tbody *").remove();
        $editor.find(".timesheet-editor tfoot .total").text('0');
        $editor.find(".comment-div textarea").val('');
        $actions.find("*").remove();
        
        $editor.find(".button-container *").remove();
    }
    self.Timeesheet = function(selector,editor, vis){
        visible = vis;
        var req={};
        $editor = $(editor);
        dataTable= $(selector);
        $tsEditor = $editor.find(".timesheet-editor");
        $dynamic = $editor.find(".dynamic");
        $actionHistory = $editor.find(".actionHistory");
        $actions = $actionHistory.find(".action-history");
        function rereshGrid(){
          
            var f={
                status:$grid.find(".status-filter").select2('val')
            }
 	    /*if($("#timesheet-grid_wrapper .start-date").datepicker('getDate')){
                f.fromdate=$("#timesheet-grid_wrapper .start-date").datepicker('getDate').getTime();
            }
            if($("#timesheet-grid_wrapper .end-date").datepicker('getDate')){
                f.todate=$grid.find("#timesheet-grid_wrapper .end-date").datepicker('getDate').getTime();
            } */          
            var v_date=$grid.find(".week-filter").select2('val');
            if(v_date!=null){
            f.fromdate=v_date.substring(0,v_date.indexOf("-"));
            f.todate  =v_date.substring(v_date.indexOf("-")+1,v_date.length);        
            }else{
            f.fromdate=null;
            f.todate  =null;        
            }
            
            dataTable.fnReloadAjax({
		url:vis?"service/manager/timesheet/getall":"service/timesheet/getall",
                "type": "POST",
                data:f,
                contentType: "application/json",
                dataType:'json'
             });	          
	     //$grid.find(".end-date").datepicker();	
             $tsEditor.find("tbody *").remove();
             $actionHistory.hide();
             $dynamic.hide();
             
        }
        
        function _renderLog(data){
            
            var $actions = $editor.find(".actionHistory .action-history");  
            if(data.length>0){
                $actionHistory.show();
                for(var i=0;i<data.length;i++){
                    if(data[i].newStatus!=1){ //nt saved
                    var action =data[i];
                    var $actionRow = $(actionTpl).appendTo($actions);
                    if(action.newStatus==4){
                        var $actionRowTable = $actionRow.find('table');
                       _addLogHeader($actionRowTable);
                       _addLogFooter($actionRowTable);
                        var body=$('<tbody></tbody>').appendTo($actionRowTable);
                      for(var j=0;j<action.rows.length;j++){
                          
                          _addLogRow(action.rows[j],body);
                      }  
                    }
                    setTotolRow($actionRow);
                    $actionRow.find(".user-action").text(getActStatus(action.newStatus));
                    $actionRow.find(".action-by").text(action.actionBy);
                    $actionRow.find(".action-on").text(getUsersDateFormate(action.actionOn));
                    $actionRow.find(".action-comment").text(action.comment);
                     
                    $actionRow.find("input").prop("disabled",true);
                }
                }  
                
            }
        }
        function _renderTimeSheet(data, disabled){ 
           $dynamic = $editor.find(".dynamic").show();
            var btnEl = $editor.find(".button-container");
            if(data){
                  for(var i=0;i<data.rows.length;i++){
                      _addRow(data.rows[i]);
                  }
                  setComments(data);
                  setTotolRow($dynamic);
                  
                  if(req.oldStatus==1 || req.oldStatus==2||  req.oldStatus==3 || req.oldStatus==5){
                      if(isSelfTs()){
                      btnEl.append("<button class='btn-save'>Save</button>")
                        .append("<button class='btn-submit'>Submit</button>");
                        $dynamic.find('.btn-save').button()
                            .on('click', saveTimesheet);
                        $dynamic.find('.btn-submit').button()
                            .on('click', submitTimesheet);
                      }
                  } else if(req.oldStatus==4){
                     
                      if(isSelfTs()){
                           btnEl.append("<button class='btn-withraw'>Withraw</button>");
                           $dynamic.find('.btn-withraw').button()
                            .on('click', withrawTimesheet);
                      } else{
                           btnEl.append("<button class='btn-revert'>Revert</button>");
                           $dynamic.find('.btn-revert').button()
                           .on('click',revertTimesheet);
                           btnEl.append("<button class='btn-reject'>Reject</button>");
                           $dynamic.find('.btn-reject').button()
                           .on('click',rejectTimesheet);
                           btnEl.append("<button class='btn-approve'>Approve</button>");
                           $dynamic.find('.btn-approve').button()
                           .on('click',apprveTimesheet);
                           
                      } 
                  }
                  if(disabled){
                      $editor.find(".timesheet-editor").find("input").prop("disabled",disabled);
                      $editor.find(".timesheet-editor").find(".add").off("click");
                      $editor.find(".timesheet-editor").find(".remove").off("click");
                  }
            } else{
                _addRow();
                btnEl.append("<button class='btn-save'>Save</button>")
                .append("<button class='btn-submit'>Submit</button>");
                $dynamic.find('.btn-save').button()
                .on('click', saveTimesheet);
                $dynamic.find('.btn-submit').button()
                .on('click', submitTimesheet);
            }
            
        }
        function saveTimesheet(){
            req.newStatus=1;
            saveSubmiTimesheet();
        }
        function submitTimesheet(){
            req.newStatus=4;
            saveSubmiTimesheet();
        }
        function withrawTimesheet(){
            req.newStatus=2;
            saveSubmiTimesheet();
        }
         function revertTimesheet(){
            req.newStatus=3;
            saveSubmiTimesheet();
        }
         function rejectTimesheet(){
            req.newStatus=5;
            saveSubmiTimesheet();
        }
         function apprveTimesheet(){
            req.newStatus=6;
            saveSubmiTimesheet();
        }
        
        function saveSubmiTimesheet(){
            if(isValid()){
            var data = [];
            var trs=$tsEditor.find('tbody tr');
            for(var i=0; i<trs.length;i++){
                var $tr=$(trs[i]);
                var obj= getRowValue($tr);
                data.push(obj);
            };
            
            req.timesheet=data;
            req.comment=$dynamic.find("textarea[name='comment']").val();
            
            $.ajaxService({
                url:"service/timesheet/"+req.id+'/'+req.newStatus,
                type:'PUT',
                data:JSON.stringify(req),
                contentType: "application/json",
                dataType:'json',
                success: function(){
                    if(req.newStatus=='1')
                    jQuery.MsgBox.show({
                       title:'Info',
                        msg :'Your Timesheet Saved Successfully',
                        icon:1,
                        width:400
                    });
                    else if(req.newStatus=='4')    
                    jQuery.MsgBox.show({
                       title:'Info',
                        msg :'Your Timesheet Submitted Successfully',
                        icon:1,
                        width:400
                    });
                        
                    else if(req.newStatus=='2')       
                    jQuery.MsgBox.show({
                       title:'Info',
                        msg :'Your Timesheet Withdrawn Successfully',
                        icon:4,
                        width:400
                    });
                    else if(req.newStatus=='3')       
                    jQuery.MsgBox.show({
                       title:'Info',
                        msg :'Your Timesheet Reverted back to user Successfully',
                        icon:4,
                        width:400
                    });
                    else if(req.newStatus=='5')       
                    jQuery.MsgBox.show({
                       title:'Info',
                        msg :'Timesheet has been rejected Successfully',
                        icon:4,
                        width:400
                    });
                    rereshGrid();
                }
            });
            }
        }
        var columns=[{
                "data":'id',
                title:"",
                sortable:false,
                render: function(val,b,c){
                        return "<span class='bg-status status-"+getActStatus(c.status)+"'>&nbsp;</span>"
                }
            }];
        if(vis){
           columns.push({
                data: 'userName',
                title:"User Name",
                visible:vis
            }) 
        }
        columns.push({
                "data": 'fromdate',
                title:"Start Date",
                render: function(a,b,c){
                  var sdate=new Date(a);
                  return sdate.getDate()+"/"+(sdate.getMonth()+1)+"/"+sdate.getFullYear();
                }
            },{
                "data": 'todate',
                title:"End Date",
                render: function(a){
                  var edate=new Date(a);
                  return edate.getDate()+"/"+(edate.getMonth()+1)+"/"+edate.getFullYear();

                }
            }, {
                "data": 'status',
                title:"Status",
                render: function(a){
                    return getActStatus(a);
                }
            });
        if(vis){
            columns.push({
                data: 'modifiedon',
                title:"Last Action On",
                 visible:vis,
                 render: function(a){
                  var edate=new Date(a);
                  return edate.getDate()+"/"+edate.getMonth()+"/"+edate.getFullYear();

                }
            });
        }
        var today = new Date().getTime();
        var ed = today+ 604800000;
        var fd = today- (604800000*2);
        dataTable.dataTable({
            "info": false,
            "scrollY": "1px",
//            "bLengthChange": false,
            "bFilter": false,
            "bPaginate": false,
//            "bAutoWidth":true,
            "fnRowCallback": function( nRow, aData ) {
//                var status = aData.status; 
//                var $nRow = $(nRow); 
//                if (status == '0') {
//                  $nRow.css({"background-color":"#FFF380"});
//                }
//                else if (status == '1'){
//                  $nRow.css({"background-color":"#87AFC7"});
//                }
//                else if (status == '4'){
//                  $nRow.css({"background-color":"yellowgreen"});
//                }
//                else if (status == '5'){
//                  $nRow.css({"background-color":"red"});
//                }else if (status == '6'){
//                  $nRow.css({"background-color":"#86b324"});
//                }
                return nRow
            },
            "ajax": {
                url:vis?"service/manager/timesheet/getall":"service/timesheet/getall",
                "type": "POST",
                contentType: "application/json",
                dataType:'json',
                data:{fromdate:fd,todate:ed}
            },
            "columns":columns
        });
        
        var $grid = dataTable.closest(".dataTables_wrapper");
        dataTable.find('tbody').on('click', 'tr', function () {           
            var idx = dataTable.fnGetPosition(this);
            var data = dataTable.fnGetData(idx);
            //saved and pending only visible to user
            if((data.status==0 || data.status==1) && data.userid!=$.users.userId){
                jQuery.MsgBox.show({
                       title:'Info',
                        msg :'Timesheet is not submited.',
                        icon:4,
                        width:400
                });
                return false;
            } 
            selectedRow=data;
            req.oldStatus=data.status;
            if(!$(this).hasClass("row-selected")){ //
                $(".timesheet-ed.no-datapdisplay").hide();  
                removeEditor();
                if(data.status>0){
                   
                    $.ajax({
                        url:"service/timesheet/"+data.id,
                        type:'GET',
                        dataType:'json',
                        success: function(res){
                            var results = res.data;
                            if(data.status!=4 && data.status!=6){ //saved, revert, withdraw,Rejected  
                               var curRow=null; 
                               var latestRow= curRow=results[0];
                               if(data.status==1){ // saved time sheet
                                   curRow=results[0];  
                               }else if(data.status==2 || data.status==3 || data.status==5){
                                   for(var i=0; i<results.length;i++){
                                       var r=results[i];
                                       if(r.newStatus == 4){
                                           curRow=results[i ];
                                           break;
                                       }
                                   }
                               }
                      
                                req.version=latestRow.version;
                                req.vId=data.status==1?curRow.vId:''; //!saved create new version
                                req.oldStatus=latestRow.newStatus;
                                req.id = latestRow.tsid;
                                if(data.userid==$.users.userId){
                                    _renderTimeSheet(curRow,false);
                                    _renderLog(results);
                                }else{                              
                                    _renderLog(results);
                                }
                            } else if(data.status==4){
                                var curRow=results[0];        
                                req.version=curRow.version;
                                req.vId=curRow.vId; //!saved create new version
                                req.oldStatus=curRow.newStatus;
                                 req.id = curRow.tsid;
                                _renderTimeSheet(curRow,true);
                                _renderLog(results.slice(0));
                            }else{ //apprved
                                $dynamic.hide();
                                _renderLog(results);
                            }
                            
                        }
                    });
                } else{
                    req.vId='';
                    req.version=1;
                    req.id = data.id;
                    _renderTimeSheet();
                }
            
                 dataTable.find('.row-selected').removeClass("row-selected");
                $(this).addClass("row-selected");
                //if 
            }			
        });
        //var toolbar = "<div class=''><div ><input type='text' class='status-filter' /><label>Start Date</label><input type='text' class='start-date'><label>Start Date</label><input type='text' class='end-date'><button class='refresh'></button></div>";
        var toolbar = "<div style='float:left;'>"+
                "<input type='text' class='status-filter'/>"+
                "<div class='separator'></div>"+
                "<input type='text' class='week-filter'/>"+
                "<button class='btnReset'>Clear</button>"+
            "</div>";              
        
        
        var $toolbar = $grid.find(".ui-toolbar.ui-corner-tl").append(toolbar);
        $.ajaxService({
                      url:'service/generic/GET_DATE_COMBO1',
                      success: function(data){
                            var max = 0 ;
                            var d = data.map(function(d){
                                max = Math.max(max, d.endDate);
                                d.id = d.startDate+"-"+d.endDate;
                                return d
                            })
                            var startDate = max-(1123198812); 
                            //prev 2 week
                            d.push({
                                id:startDate+"-"+max,
                                startDate:startDate,
                                endDate:max,
                                text:"Prev 2 weeks"
                            });
                           $toolbar.find(".week-filter").select2({
                            placeholder:"Select Week",
                            data:d,
                            style:"font-size:9px",
                            width:120,
                            dropdownAutoWidth : true
                           });
                           $toolbar.find(".week-filter").select2('val',startDate+"-"+max);
                     }
        });
       

         var statusFilter =$toolbar.find(".status-filter").select2({
              placeholder:"Select Status",
              data:TMS.TIMESHEET_STATUS,
              dropdownAutoWidth : true,
              width:102
         }).change(function() {
            var obj=$(".status-filter");
            rereshGrid();
        });

	var weekFilter = $toolbar.find(".week-filter" ).change(function() {
            rereshGrid();
        }); 
        $toolbar.find(".btnReset" ).button({
            icons: {
		primary: "ui-icon-cancel"
	     }
        }).on('click',function() {
            statusFilter.select2('val',null);
            weekFilter.select2('val',null);
            rereshGrid();
        }); 
       
        
    }	
})(this);
