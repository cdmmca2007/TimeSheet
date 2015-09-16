window.table=null;
(function(self){
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
 
    self.TimeesheetReport = function(){
       var dataTable; 
       function reloadGrid(id){
       dataTable.fnReloadAjax({
                               url:'service/timesheet/mainreport/'+id,
                               "type": "GET",
                               data:id,
                               contentType: "application/json",
                               dataType:'json'
       });
       }
       var columns = [{
                "data": 'empno',
                title:"Emp Id",
                render:function(val){
                    if(val!=null)
                       return "<b>"+val+"</b>"; 
                    else
                       return "<b>N/A</b>";  
             }
            },{
                "data": 'userName',
                title:"Name",
                render:function(val){
                       return "<b>"+val+"</b>"; 
                    
            }
            },{
                "data": 'mon',
                title:"Mon",
                visible:$.users.role<3?true:false
            },{
                "data": 'tues',
                title:"Tues",
                visible:$.users.role<3?true:false
            },{
                "data": 'wed',
                title:"Wed",
                visible:$.users.role<3?true:false
            },{
                "data": 'thurs',
                title:"Thurs",
                visible:$.users.role<3?true:false
            },{
                "data": 'fri',
                title:"Fri",
                visible:$.users.role<3?true:false
            },{
                "data": 'sat',
                title:"Sat",
                visible:$.users.role<3?true:false
            },{
                "data": 'sun',
                title:"Sun",
                visible:$.users.role<3?true:false
            },{
                "data": 'tot_hour',
                title:"Total Hour"
                
            },{
                "data": 'status',
                title:"Status"
            }                       
       ];
       dataTable= $("#all-timesheet-report-grid").dataTable({
//            'dom':'<"toolbar">frtip',
//            "info": false,
            "bLengthChange": false,
//            "bFilter": false,
            "bPaginate": $.users.role<3,
//            "bAutoWidth":true,
            "scrollY": "1px",
"fnRowCallback": function( nRow, aData ) {
                var status = aData.status; 
                var $nRow = $(nRow); 
                if (status == "Pending") {
                  $nRow.css({"background-color":"#8bccf0"});
                }
                else if (status == "Submitted"){
                  $nRow.css({"background-color":"#87AFC7"});
                }
                else if (status == "Approved"){
                  $nRow.css({"background-color":"yellowgreen"});
                }
                
                return nRow
              },
             "ajax": {
                url:"service/timesheet/mainreport"+null,
                "type": "GET"
            },
            
            "columns": columns
        });
       var toolbar = "<div style='float:left;'><input type='text' class='status-filter'/><input type='text' id='week-filter' class='week-filter'/><button id='downLoad'><span>Download</span></button></div>";              
       
        $("#all-timesheet-report-grid_filter").prepend(toolbar).before('<span  class="grid-title">Timesheet Report</span>'); 
        var filter =$("#all-timesheet-report-grid_wrapper .status-filter").select2({
              placeholder:"Select Status",
              data:TMS.TIMESHEET_STATUS,
              width:120
        });
        $.ajaxService({
                      url:'service/generic/GET_DATE_COMBO',
                      success: function(data){
                           $("#all-timesheet-report-grid_wrapper .week-filter").select2({
                            placeholder:"Select Week",
                            data:data,
                            style:"font-size:9px",
                            width:170
                           });
        }
        });
     $("#downLoad").button().on('click',function(){
         var data=[{
                 empno:'123',
                 userName:'Kalesh',
                 mon:7,
                 tues:4,
                 wed:8,
                 thurs:2,
                 fri:3,
                 sat:6,
                 sun:7,
                 tot_hour:50,
                 status:5
         },{
                 empno:'123',
                 userName:'CCDD',
                 mon:7,
                 tues:4,
                 wed:8,
                 thurs:2,
                 fri:3,
                 sat:6,
                 sun:4,
                 tot_hour:50,
                 status:5
         }
             
         ]
         download(columns,dataTable.fnGetData());
         //download(columns,data);
     });   
     $("#all-timesheet-report-grid_wrapper .week-filter" ).change(function() {
            var obj=$("#week-filter");
            reloadGrid(obj.val());
     });
//     fitToParent(dataTable);
   };
    
})(this);
