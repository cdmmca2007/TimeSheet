var TMS = this.TMS || {};
TMS.ACTIVITY ={
    PENDING:0,
    SAVE:1,
    SUBMIT:2,
    REVERT:3,
    APPROVE:4,
    REJECT:5
};
TMS.TIMESHEET_STATUS =[
{
    id:'0',
    text:'Pending'
},
{
    id:'1',
    text:'Save'
},
{
    id:'2',
    text:'Withdraw'
},
{
    id:'3',
    text:'Revert Back'
},
{
    id:'4',
    text:'Submit'
},
{
    id:'5',
    text:'Rejected'
},
{
    id:'6',
    text:'Approved'
}
]
function getProject(projId){
    for(var i=0;i<$.projectArray.length;i++ ){
        if(projId == $.projectArray[i].projectId){
            return $.projectArray[i];
        }
    }
}
function getProjActivity(projId){
    //return getProject(projId).activity;
    var proj=getProject(projId);
    if(proj!=null)
      return proj.activity;
    else
      return {};     
}

function getUsersDateFormate(date){
    if(!isNaN(date)){
       var d = new Date();
       return  d.getDate()+"/"+d.getMonth()+"/"+d.getFullYear();
    } else{
        return  date.getDate()+"/"+date.getMonth()+"/"+date.getFullYear();
    }
}
function getCombo(selector,q,op,val){
        $.ajaxService({
            url:'service/generic/'+q,
            success: function(data){
                var d =$.extend({
                    data:data
                },op);
                $(selector).select2(d);
                if(val){
                    $(selector).select2('val',val);
                }
            }
        });
}
function getActStatus(s){
    var a =["Pending","Save","Withdraw","Revert Back","Submited","Rejected","Approved"];
    return a[s];
}

function fitToParent(grid,parent){
    var wrapper = grid.closest(".dataTables_wrapper");
    var p = parent || wrapper.parent();
    grid.closest(".dataTables_scrollBody").height((p.height()-wrapper.height()));
    
}
function download(column,data){
    var json={
       column: column,
       data:data
    }
    $("#downloadForm input").val(JSON.stringify(json));
    $("#downloadForm").attr("method","POST")
    .submit();
}
function downloadPMU(data){
    var json={
       data:data
    };
    alert(data.projectId);
    $("#downloadFormPMU input").val(data.projectId);
    $("#downloadFormPMU").attr("method","POST")
    .submit();
}
function downloadProgressReport(data){
    var json={
       data:data
    };
    $("#downloadPR input").val(data.projectId);
    $("#downloadPR").attr("method","POST")
    .submit();
}

function isValidForm(sel){
    
    var res={valid:true,msg:''};
    ///var items=$('#dialog-form .form-item');
    
    var items=$(sel+' .form-item');
    
    for(var i=0;i<items.length;i++){
       var item=items[i];
       
       var opt= $(item).data('option');
       
       if(opt){
           var obj=eval('('+opt+')');
           //for text
           if(!obj.type || (obj.type=='text')){
               if(obj.required && $(item).find('input').val()==''){
                   res={valid:false,msg:$(item).find('label').text()+' field is required'};
                   return res
               }
           }
           //for select2
           else if(obj.type=='dd'){
               if(obj.required && $(item).find('input').select2('val')==''){
                   res={valid:false,msg:$(item).find('label').text()+' field is required'};
                   return res
               }
           }
           //for datepicker
           else if(obj.type=='datepicker'){
               if(obj.required && $(item).find('input').val()==''){
                   res={valid:false,msg:$(item).find('label').text()+' field is required'};
                   return res
               }
           }
           //for number
           
          
       }
    };
    return res;
}
