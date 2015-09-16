
(function(obj){
	function onFail(request, response){
            LOG("Status",response);
            if(response.errorCode==501){
                jQuery.MsgBox.show({
                        title:'Error',
                        msg :'501 Internal Server Error !!<br> Please Refresh and try again.',
                        icon:4,
                        width:400
                });
            }else{
                jQuery.MsgBox.show({
                       title:'Error',
                        msg :'Error in Loading page.Please contact Administrator',
                        icon:4,
                        width:400
                });
            }
	};
	
	function onError(request,response){
		jQuery.MsgBox.show({
                       title:'test',
                        msg :msg,
                        icon:type,
                        width:400
                 });
	};
       
	
	var serviceCall = $.ajax;
	$.ajaxService = function(options){
                var defaults ={
                     type: "GET",
                     dataType:"json",
                     contentType:"application/json"
                }
		defaults = $.extend(defaults,options);
		serviceCall({
			url : defaults.url,
                        type: defaults.type,
                        dataType:defaults.dataType,
                        contentType: defaults.contentType,
                        data:defaults.data,
                        success: function(response){
                            if(response.success){
                                    options.success.call(this,response.data);
                            } else{
                                    onFail.call(this,defaults.data, response);
                            }
                        },
                        error: function(response){
                                onError.call(this,defaults.data, response);
                        }
                    });
	};
	
})(this);
