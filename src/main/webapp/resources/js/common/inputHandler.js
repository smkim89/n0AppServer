	$.inputHandler = {
			
		addInputHandler : function (conditions) {
			function remainOnlyTargetValue(regEx, $input,event) {
		        if ((!(event.keyCode >=34 && event.keyCode<=40)) && event.keyCode != 16) {
		            var inputVal = $input.val();
		            if (regEx.test(inputVal)) {
		                $input.val(inputVal.replace(regEx,''));    
		            }
		        }
		    }
            var $input = conditions.input;
            var dataType = conditions.dataType;
            var eventType = conditions.eventType;
            if ((!$input) || (!dataType)) {
                throw {error:"NotEnoughArguments", errorMsg:"required argument is missing " +((!$input)?" target input element":" dataType")}
                return;
            }
            if ($input[0].tagName != "INPUT") {
                throw {error:"IlregalTargetElement", errorMsg:"target element is not input"};
                return;
            }
            if ((!eventType)) {
                eventType = "keyup";
            }
            var handlerFunc = conditions.handler;
            if ((!handlerFunc)) {
                handlerFunc = function(event){
                    $("#divKeyCode").empty().html("<span> event key code = "+event.keyCode+"</span>");
                    var regEx = null;
                    if (dataType == "N") { // 숫자
                        regEx = /[^0-9]/gi;
                    }else if (dataType == "AP") { //영문
                        regEx = /[^a-z]/gi;
                    }else if (dataType == "AN") { //영어 숫자
                        regEx = /[^a-z0-9]/gi;
                    }else if (dataType == "HA") { //한글만
                        regEx = /[a-z0-9]/gi;
                    }else if (dataType == "PHONE") { //전화번호
                        regEx = /[^0-9-]/gi;  
                    }else if (dataType == "NOHA") { //한글빼고
                        regEx = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]/;  
                    }else if (dataType == "PN") { //소수
                        regEx = /[^0-9.]/gi;  
                    }else if (dataType == "ID") { //ID
                        regEx = /[^a-z0-9_]/gi; 
                    }else if(dataType == "CHECKSQL"){
                    	regEx = /[^[:alnum:]]|select|delete|update|insert|create|alter|drop/gi;
                    }else{
                        throw {error:"IlregalDataType", errorMsg:"dataType("+dataType+") is incorrect"}     
                    }
                    remainOnlyTargetValue(regEx, $input,event);
                    // return true;
                }; 
            } 
            $input.on(eventType,handlerFunc);
            
            if (conditions.maxlength) {
                $input.attr("maxlength",conditions.maxlength);
            }
            
        },

		cut_200 : function (obj, su) {
			function getTextLength(str) {
		        var len = 0;
		        for (var i = 0; i < str.length; i++) {
		            if (escape(str.charAt(i)).length == 6) { // 한글이냐
		                len = len +2; // 글자당 바이트
		            }
		            len++;
		        }
		        return len;
		    }
			 var text = $(obj).val();
		        var leng = text.length;
		        while(getTextLength(text) > su){
		            leng--;
		            text = text.substring(0, leng);
		        }
		        $(obj).val(text);
				
		}	
};

