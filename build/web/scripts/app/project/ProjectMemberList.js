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
    };
    self.ProjectMemberList = function(selectedProject){
        var tabId = "#ProjDetail_"+selectedProject.projectId;
        var dataTable=$(tabId+" .project-member-grid").dataTable({
            "scrollY": "150px",
            "bLengthChange": false,
            "ajax": {
                url:"service/member/"+selectedProject.projectId,
                "type": "GET"
            },
            "columns": [{
                "data": 'userId',
                "visible":false
                },{
                "data": 'userName',
                title:"Name",
                render:function(val){
                    return "<b>"+val+"</b>";
            }
            }, {
                "data": 'role',
                title:"Role in Project",
                render:function(val){
                    if(val=='1'||val=='100')
                    return "<font color=red><b>Project Manager</b></font>";
                    else
                    return "<b>Team Member</b>";    
            }
            },{
                "data": 'designation',
                title:"Designation"
            },{
                "data": 'startDate',
                title:"Start Date",
//                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    return dat.getDate()+"/"+dat.getMonth()+"/"+dat.getFullYear();
            }
            },{
                "data": 'endDate',
                title:"End Date",
//                width:'15%',
                render:function(val){
                    var dat=new Date(val);
                    return dat.getDate()+"/"+dat.getMonth()+"/"+dat.getFullYear();
            }
            }
            ]
        });
        var toolbar = "<button id='addResrce'><span>Add Resource</span></button>"+
        "<button id='editResrce'><span>Edit Resource</span></button>"+                
        "<button id='delResrce'><span>Remove Resource</span></button>";
        $("#project-resource-grid_filter").prepend(toolbar); 
        $('#project-resource-grid tbody').on( 'click', 'tr', function () {
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
        $('#addResrce').button().on('click', function(){
            showAddResourceForm(null);
        });
        $('#editResrce').button().click( function () {
            
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                alert("Please select the Resource from Grid to Edit Details");
            }
            else {
            var idx = dataTable.fnGetPosition(tr[0]);
            var data = dataTable.fnGetData(idx);
            if(data!=null)
              showAddResourceForm(data);
            else
              alert("Please select user from Grid to Edit Details");  

           }  // End of Else
        });
        $('#delResrce').button().click( function () {
            
            var tr = dataTable.find('tr.row-selected');
            if(tr.length==0){
                alert("Please select the Porject from Grid to Delete");
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
                            alert('Project Deleted Successfully');
                        }
             });
           }  
           }  // End of Else
        });
    };	
})(this);
