/**
 * 这是一个自定义的js文件
 */
	//自定义的jquery将表单转成json的方法
			$.fn.serializeJson=function(){  
	            var serializeObj={};  
	            var array=this.serializeArray();  
	            var str=this.serialize();  
	            $(array).each(function(){  
	                if(serializeObj[this.name]){  
	                    if($.isArray(serializeObj[this.name])){  
	                        serializeObj[this.name].push(this.value);  
	                    }else{  
	                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
	                    }  
	                }else{  
	                    serializeObj[this.name]=this.value;   
	                }  
	            });  
	            return serializeObj;  
	        };