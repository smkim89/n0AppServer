/**
 *
 * JavaScript에서 사용 할 기본 Script를 정의한다.
 *
 * @author gupark
 * @update 2015-06-08
 */

// JavaScript 에서 사용 할 context path
var contextPath;
function setContextPath(contextPath) {
    this.contextPath = contextPath;
}

$(function () {
    /**
     * XMLREQUEST 구해오는 함수 
     * IE -> ActiveX객체 IE 옛날꺼는 microsoft.XMLHTTP, 나머지는 XMLHttpRequest 내장되어있음.
     */
	var httpRequest = null;
	
	function getXMLHttpRequest(){
		if(window.ActiveXObject){
			try{
				return new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					return new ActiveXObject("Microsoft.XMLHTTP");
				}catch(e1){return null;}
			}
		}else if(window.XMLHttpRequest){
			return new XMLHttpRequest();
			
		}else{
			return null;
		}
	}
	
    $(document).ready(function () {
        $(document).on("click", "#btnLogout", function () {
            $("#logoutForm").submit();
        });
        
        $(document).on("submit", "#searchForm", function(e){
        	loading();
        });
        
        $(document).on("submit", "#excelForm", function(e){
    		var cookies= document.cookie;
        	loading();
        
        	var test = setInterval(function(){
        		if(document.cookie.length > cookies.length){
        			$.unblockUI();
        			console.log(document.cookie);
        			clearInterval(test);
        		}
        	}, 1000);
        	
        });
        
        function submitExcel(url){
        	var req = getXMLHttpRequest();
        	req.open('POST',url, true);
        	req.send(url);
        	req.onreadystatechange = function() {
        		console.log(req.status);
        		if(req.readyState != 4) return;
        		if (req.status != 200 && req.status != 304){
        			alert('HTTP ERROR ' + req.status);
        			return;
        		}
        	}
        	req.onreadystatechange = function() {
        		if(req.readyState != 4) return;
        		if (req.status != 200 && req.status != 304){
        			alert('HTTP ERROR ' + req.status);
        			return;
        		}
        	}
        	//req.send(form.attr('action'));
        }
        

        
    });

    function loading() {
        $.blockUI({
            message: '<i class="fa fa-asterisk fa-spin fa-2x text-danger"></i> <span style="font-size: 20px;">Wait.</span>',
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#ddd',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: 5,
                color: '#000'
            }
        });
        
    }
    
    function getCookie(c_name)
    {
    	var i,x,y,ARRcookies=document.cookie.split(";");
    	for (i=0;i<ARRcookies.length;i++)
    	{
    	  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
    	  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
    	  x=x.replace(/^\s+|\s+$/g,"");
    	  if (x==c_name)
    		{
    		return unescape(y);
    		}
    	  }
    }
    
     
    function setCookie( cookieName, cookieValue, expireDate )
    {
     var today = new Date();
     today.setDate( today.getDate() + parseInt( expireDate ) );
     document.cookie = cookieName + "=" + escape( cookieValue ) + "; path=/; expires=" + today.toGMTString() + ";";
    } 

    function deleteCookie(cookieName)
	{
	  var expireDate = new Date();
	  expireDate.setDate( expireDate.getDate() - 1 );
	  document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
	}









    
    
    
    
    /**
     * Ajax 사용시 환경을 설정한다.
     * - Spring Security _csrf 설정
     * Spring Security에서 POST, PUT, DELETE를 사용하기 위해서는 CSRF를 설정해야한다.
     * AJAX에서 이를 사용하기 위해 Header를 설정한다.
     *
     * - jQuery BlockUI
     * ajax가 동작할 경우 자동으로 화면에 메세지를 표시해 준다.
     */
    $.ajaxSetup({
        beforeSend: function (request) {

            request.setRequestHeader("ajax", "true");
            request.setRequestHeader("X-CSRF-TOKEN", $("input[name='_csrf']").val());
            loading();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            var status = xhr.status;
            if (status == 403) {
                alert("403 권한 오류");
                location.href = "/";
            } else if (status == 404) {
                alert("404 Page Not Found.\n관리자에게 문의하세요.");
            }
        },
        complete: function () {
            $.unblockUI();
        }
    });
    
    
});

$.tmonet = {
    alert: function (message, title, level, fun) {
        if (typeof message === 'object') {
            var obj = message;
            title = obj.title;
            message = obj.message;
            level = obj.level;
            fun = obj.success;
        }
        if (!level) {
            level = 'info';
        }
        if (title == null || title == '') {
            title = '확인';
        }

        $("#alertModal .btn-submit").off();
        if (typeof fun == 'function') {
            $(document).on("click", "#alertModal .alert-submit", fun);
        } else {
            $(document).on("click", "#alertModal .alert-submit", function (e) {
                e.preventDefault();
                $("#alertModal").modal('hide');
            });
        }

        $("#alertModal .modal-header").html(title);
        $("#alertModal .modal-body").html(message);
        $("#alertModal").modal('show');
    },


    expressions: {
        alphanumeric: /^([a-z0-9_\-])$/,                                                         // Characters a-z, 0-9, underscores and hyphens in lowercase only
        text: /^(^[a-z])+$/,                                                                     // Characters a-z of any length in either case
        email: /^([a-z0-9_\.\-]+)@([\da-z\.\-]+)\.([a-z\.]{2,6})$/,                              // TLD email address
        url: /^(https?:\/\/)?([\da-z\.\-]+)\.([a-z\.]{2,6})([\/\w \.\-]*)*\/?$/,                 // URL with or without http(s)/www
        date: /^(([0-3][0-9]|[1-9])[\/\-\.]([0-1][0-9]|[1-9])[\/\-\.]([0-9][0-9])?[0-9][0-9])$/, // Date in format 'day, month, year' with or without leading zeroes or century and separated by a point, hyphen or forward slash
        time: /^(([0-2])?[0-9]:[0-5][0-9])$/,                                                     // Time in format hours:minutes with or without leading hour zero in 12 or 24 hour format
        ip:	/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
    },

    test: function (value, regex) {
        return value.match($.tmonet.expressions[regex]) != null ? true : false;
    },
    sleep: function (ms) {
        var ts1 = new Date().getTime() + ms;
        do ts2 = new Date().getTime(); while (ts2 < ts1);
    },
    numberWithCommas : function (x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
};
