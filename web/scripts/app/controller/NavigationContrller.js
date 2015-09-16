/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
(function(){
    function removeLinks(){
        var role =getCookie('zrole');
        if(role == 4){
        } else if(role==3){
            $("#link_Dashboard,#link_MyTimesheet").removeClass("hidden");
        } else if(role==2){
            $("#link_Dashboard,#link_ProjectManagement,#link_MyTimesheet").removeClass("hidden");
        } else if(role==1){
            $("#link_Dashboard,#link_UserManagement,#link_AllTimesheet,#link_MyTimesheet").removeClass("hidden");
        }
        $("#navcontainer li.hidden").remove(); 
    }
    function getCmp(selector){
        var cmp = $(selector);
        return cmp && cmp.length;
    } 
    function changePassWord(){
       $("#modal-win .modal-inner-panel").load("forms/changePassword.html", function(){
           $("#form-item-oldpass").show();
       });
       $("#modal-win").dialog({
                modal:true,
                width:440,
                height:250,
                title:'Change User Password',
                buttons: {
                    "Submit": function() {
                        var obj = {};
                        var arr = $("#modal-win form").serializeArray();
                        for(var i=0; i<arr.length; i++){
                            obj[arr[i].name] = arr[i].value;
                        }
                        obj.oldpassword=Sha1.hash(obj.oldpassword);
                        obj.newpassword=Sha1.hash(obj.newpassword);
                        obj.renewpasswrd=Sha1.hash(obj.renewpasswrd);
                        $.ajax({
                            url:"service/chngpadwrd",
                            type:'POST',
                            data:JSON.stringify(obj),
                            contentType: "application/json",
                            dataType:'json',
                            success: function(){
                                  alert("Password Changed Successfully");
                            }
                        });
                    },
                    Cancel: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
    }
    function signout(){
     $.ajax({
        url:"service/signOut.do",
        method:"POST",
        success:function(result){
            _rc('zpv');
            _rc('zrole');
            window.top.location.href = "./";
        }
    });
    }
    function _rc(n){
    document.cookie = n + "=" + ";path=/;expires=Thu, 01-Jan-1970 00:00:01 GMT";
    }
    NavigationContrller = function(){
        
        removeLinks();
        var role =getCookie('zrole');
        if(role==1){
            //var timeesheet = new Timeesheet("");
        }else if(role==2){
        }else if(role==3){
        }else if(role==4){
        }
         $("#navcontainer li a.tab-link").on('click', function(){
             var id=$(this).closest('li').attr('id');
             var _a=id.split("_");
             var tabId = "Tab_"+_a[1];
             if($("#"+tabId).length==0){
                //$Tabs.tabs( "add", "tabpanel_"+id, "Tab_"+id );
                $Tabs.tabs( "add", "html/"+_a[1]+".html", $(this).text(),tabId ); 
              
             var idx = $("ul.ui-tabs-nav li").length-1;
             $Tabs.tabs("option","active", idx ); 
             }
             else{
                 $Tabs.tabs( "option","active", $("#"+tabId).index()-1 ); 
             }       
         });

         $("#top-link .user-menu").on('click', function(){
              $(".modal-mask").show()
             $(".usermenu-div").toggle();
         });
         $(".menu-options .chnage-password").on('click', changePassWord);
         $(".menu-options .signout").on('click',signout);
         $(".menu-options li").on('click', function(){
              $(".modal-mask").hide()
             $(".usermenu-div").slideToggle(500);
         });
         
    }
    
})();



