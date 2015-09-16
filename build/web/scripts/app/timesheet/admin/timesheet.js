(function(self){
   
    var tsEditor = $("#timesheet-editor"),tsBody=$("#timesheet-editor tbody");
    var rowTpl = '<tr class="search_main_tr">'+
    '<td class="proj-drop-down"></td>'+
    '<td class="td1 activity-drop-down"> </td>'+
    '<td class="td2"><input type="text" class="text1 description required min-length" /></td>'+
    '<td class="td3" data-col="mon" ><input type="text" class="mon hours" /></td>'+
    '<td class="td3" data-col="tues" ><input type="text" class="tues hours" /></td>'+
    '<td class="td3" data-col="wed" ><input type="text" class="wed hours" /></td>'+
    '<td class="td3" data-col="thurs" ><input type="text" class="thurs hours" /></td>'+
    '<td class="td3" data-col="fri"><input type="text" class="fri hours" /></td>'+
    '<td class="td3" data-col="sat" ><input type="text" class="sat hours" /></td>'+
    '<td class="td3" data-col="sun" ><input type="text" class="sun hours" /></td>'+
    '<td class="td3" data-col="sum-row" >0</td>'+
    ' </tr>'
    
    function setRowValue($tr,obj){
        $tr.find('.proj-drop-down').select2('val',obj.projectId,true);
        $tr.find('.activity-drop-down').select2('val',obj.activityId);
        $tr.find('.description').val(obj.description);
        $tr.find('.mon').val(obj.mon==0?'':obj.mon);
        $tr.find('.tues').val(obj.tues==0?'':obj.tues);
        $tr.find('.wed').val(obj.wed==0?'':obj.wed);
        $tr.find('.fri').val(obj.fri==0?'':obj.fri);
        $tr.find('.sat').val(obj.sat==0?'':0);
        $tr.find('.sun').val(obj.sun==0?'':0);
        
        return obj;
    }
    function getRowValue($tr){
        var obj={}; 
        obj.projectId = $tr.find('.proj-drop-down').select2('val');
        obj.activityId = $tr.find('.activity-drop-down').select2('val');
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
    function _renderLog(data){
            
    }
    function _addRow(data){
        tsBody=$("#timesheet-editor tbody")
        var row = $(rowTpl).appendTo(tsBody);
        row.find(".proj-drop-down").select2({
            data:$.projectArray.map(function(item){
                return {
                    id:item.projectId,
                    text:item.projectName
                };         
            })
        }).on("change", function(combo){
            var proj = getProjActivity(combo.val);
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
        });
         row.find(".activity-drop-down").select2({
            data:[]
        })
        if(data){
            row.data("data",data);
            setRowValue(row, data);
        }
        
        row.find('.add').on('click', function(){
            _addRow();
        });
        row.find('.remove').on('click', function(){
            $(this).closest('tr').remove();
        });
        row.find('.hours').on('change', function(){
            var column= $(this).attr('name');
            $('.'+column+"-total").text('ok');
        });
    }
    
    self.AdminTimeesheet = function(vis){
        var req={};
        var dataTable= $("#timesheet-grid");
        function rereshGrid(){
            alert(3);
            var f={
                status:$("#timesheet-grid_wrapper .status-filter").select2('val')
            }
            if($("#timesheet-grid_wrapper .start-date").datepicker('getDate')){
                f.fromdate=$("#timesheet-grid_wrapper .start-date").datepicker('getDate').getTime();
            }
            if($("#timesheet-grid_wrapper .end-date").datepicker('getDate')){
                f.todate=$("#timesheet-grid_wrapper .end-date").datepicker('getDate').getTime();
            }           
            
        	dataTable.fnReloadAjax({
		 url:"service/timesheet/getall",
                "type": "POST",
                data:f,
                contentType: "application/json",
                dataType:'json'
             });	          
		
        $("#timesheet-grid_wrapper .end-date").datepicker();			
        }
        function _renderTimeSheet(data, disabled){  
            var btnEl = $("#button-container");
            if(data){
                  for(var i=0;i<data.rows.length;i++){
                      _addRow(data.rows[i]);
                  }  
                  if(req.oldStatus==1){
                      btnEl.append("<button class='btn-save'>Save</button>")
                        .append("<button class='btn-submit'>Submit</button>");
                        $('#dynamic .btn-save').button()
                            .on('click', saveTimesheet);
                        $('#dynamic .btn-submit').button()
                            .on('click', submitTimesheet);
                  } else if(req.oldStatus==4){
                      var role =getCookie('zrole');
                      if(role==4){
                           btnEl.append("<button class='btn-withraw'>Withraw</button>");
                           $('#dynamic .btn-withraw').button()
                            .on('click', withrawTimesheet);
                      } else{
                           btnEl.append("<button class='btn-withraw'>Revert</button>");
                           btnEl.append("<button class='btn-withraw'>Reject</button>");
                           btnEl.append("<button class='btn-withraw'>Approve</button>");
                           
                      }
                     
                  }
                  if(disabled){
                      $("#timesheet-editor input").prop("disabled",disabled);                  }
            } else{
                _addRow();
                btnEl.append("<button class='btn-save'>Save</button>")
                .append("<button class='btn-submit'>Submit</button>");
                $('#dynamic .btn-save').button()
                .on('click', saveTimesheet);
                $('#dynamic .btn-submit').button()
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
        
        
        function saveSubmiTimesheet(){
            var data = [];
            var trs=$('#timesheet-editor tbody tr');
            for(var i=0; i<trs.length;i++){
                var $tr=$(trs[i]);
                var obj= getRowValue($tr);
                data.push(obj);
            };
            
            req.timesheet=data;
            req.comment=$("#dynamic input[name='comment']").val();
           
            $.ajaxService({
                url:"service/timesheet/"+req.id+'/'+req.newStatus,
                type:'PUT',
                data:JSON.stringify(req),
                contentType: "application/json",
                dataType:'json',
                success: function(){
                    alert("success");
                }
            });
        }
        dataTable.dataTable({
            "info": false,
//            "bLengthChange": false,
            "bFilter": false,
            "bPaginate": false,
//            "bAutoWidth":true,
            "ajax": {
                url:"service/timesheet/getall",
                "type": "POST",
                contentType: "application/json",
                dataType:'json'
            },
            "columns": [{
                data: 'userName',
                title:"User Name"
            },{
                "data": 'fromdate',
                title:"Start Date",
                render: function(a,b,c){
                    return new Date(a);
                }
            },{
                "data": 'todate',
                title:"End Date",
                render: function(a){
                    return new Date(a);
                }
            }, {
                "data": 'status',
                title:"Status",
                render: function(a){
                    return '<b>'+getActStatus(a)+'</b>';
                }
            }, {
                "data": 'modifiedon',
                title:"Last Action Date"
                /*render: function(a,b,c){
                    return new Date(a);
               }*/
            }                       
            ]
        });
        
        
        $('#timesheet-grid tbody').on('click', 'tr', function () {
            $(".timesheet-ed.no-datapdisplay").hide();
             $("#dynamic").show();
            var idx = dataTable.fnGetPosition(this);
            var data = dataTable.fnGetData(idx);
            req.oldStatus=data.status;
            if(!$(this).hasClass("row-selected")){
                
                if(data.status>0){
                   
                    $.ajax({
                        url:"service/timesheet/"+data.id,
                        type:'GET',
                        dataType:'json',
                        success: function(res){
                            var results = res.data;
                            if(data.status!=4 && data.status!=6){ //saved, revert, withdraw,Rejected  
                                var curRow=results[0];        
                                req.version=curRow.version;
                                req.vId=data.status==1?curRow.vId:''; //!saved create new version
                                req.oldStatus=curRow.newStatus;
                                 req.id = curRow.tsid;
                                _renderTimeSheet(curRow,false);
                            
                            } else if(data.status==4){
                                var curRow=results[0];        
                                req.version=curRow.version;
                                req.vId=curRow.vId; //!saved create new version
                                req.oldStatus=curRow.newStatus;
                                 req.id = curRow.tsid;
                                _renderTimeSheet(curRow,true);
                            }
                            _renderLog(res);
                        }
                    });
                } else{
                    req.vId='';
                    req.version=1;
                    req.id = data.id;
                    _renderTimeSheet();
                }
            
                $('#timesheet-grid .row-selected').removeClass("row-selected");
                $(this).addClass("row-selected");	  
            }			
        });
        var toolbar = "<div class=''><div ><input type='text' class='status-filter' /><label>Start Date</label><input type='text' class='start-date'><label>Start Date</label><input type='text' class='end-date'><button class='refresh'></button></div>";
//        var toolbar = "<div class='left' style='width:30%'><button class='backward'><<</button><input type='text' class='start-date'></div><div  class='left' style='width:40%;text-align:center;'>My Timesheet</div><div  class='left' style='width:30%;text-align:right;'><input type='text' class='end-date'><button class='forward'>>></button></div>";
        $("#timesheet-grid_wrapper .ui-toolbar.ui-corner-tl").append(toolbar);
         var filter =$("#timesheet-grid_wrapper .status-filter").select2({
              placeholder:"Select Status",
              data:TMS.TIMESHEET_STATUS,
              width:120
         });
        $("#timesheet-grid_wrapper .refresh").button({
		icons: {
			primary: "ui-icon-refresh"
		}
	}).on('click', rereshGrid);
        $("#timesheet-grid_wrapper .start-date").datepicker();
        $("#timesheet-grid_wrapper .end-date").datepicker();
        
    }	
})(this);
