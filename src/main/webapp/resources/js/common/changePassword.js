/**
 * 
 * 
 */

var contextPath;
function setContextPath(contextPath) {
	this.contextPath = contextPath;
}

$(function(){
	$(document).ready(function() {
		
		if($("#pwdUpdateReqYn").val() == 'Y'){
			alert('한달 동안 패스워드를 변경하지 않았습니다.\n변경해주시기 바랍니다.');			
			$("#modal-user-settings").modal("show");
		}else if($("#pwdUpdateReqYn").val() == 'P'){
			alert('두달 동안 패스워드를 변경하지 않았습니다.\n변경해주시기 바랍니다.');
			$("#modal-user-settings").modal("show");
		}
		$(document).on("click", "#modal_continue_btn", function () {
			updatePasswordStatus();
		});
		$(document).on("click", "#modal_update_btn", function () {
			updatePassword();
		});
		$(document).on("click", "#modal_close_btn", function () {
			//document.password_change_form.reset();
			$("#modal-user-settings").modal("hide");
		});
		$(document).on("click", "#modal_logout_btn", function () {
            $("#logoutForm").submit();
        });
		$("#modal-user-settings").on("hidden.bs.modal", function() {
			if(typeof($("#biz_no").attr("name"))!="undefined"){
				$("#biz_no").attr("readonly", true);
			}
			$("#password").attr("readonly", true);
			$("#new_password").attr("readonly", true);			
			$("#new_password_repeat").attr("readonly", true);
			document.password_change_form.reset();

		});
	});

	function checkPassword(){
				
		if(typeof($("#biz_no").attr("name"))!="undefined"){
			if($("#biz_no").val() == ""){
				alert("사업자등록번호를 입력하십시오.");
				return false;
			}	
		}
		
		if($("#password").val() == ""){
			alert("현재 사용중인 비밀번호를 입력하십시오.");
			return false;
		}
		if($("#new_password").val() == ""){
			alert("신규 비밀번호를 입력하십시오.");
			return false;
		}
		if($("#new_password_repeat").val() == ""){
			alert("비밀번호 확인을 입력하십시오.");
			return false;
		}
		
		if($("#new_password").val() != $("#new_password_repeat").val()){
			alert("입력하신 신규 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return false;
		}			
		if($("#password").val() == $("#new_password").val()){
			alert("기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
			return false;
		}			
		if($("#new_password").val().length < 8){
			alert("비밀번호는 문자, 숫자, 특수문자의 조합으로 8~20자리로 입력하여주십시오.");
			return false;
		}
		if(!$("#new_password").val().match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)){
			alert("비밀번호는 문자, 숫자, 특수문자의 조합으로 8~20자리로 입력하여주십시오.");
			return false;
		}
		
		var samePass_0 = 0; //동일문자 카운트
		var samePass_1 = 0; //연속성(+) 카운트
		var samePass_2 = 0; //연속성(-) 카운트
		var chr_pass_0;
		var chr_pass_1;
		var chr_pass_2;
		
		for(var i=0; i<$("#new_password").val().length; i++){
			chr_pass_0 = $("#new_password").val().charAt(i);
			chr_pass_1 = $("#new_password").val().charAt(i+1);
			
			//동일문자 카운트
			if(chr_pass_0 == chr_pass_1){
				samePass_0 = samePass_0 + 1;
			}
			
			chr_pass_2 = $("#new_password").val().charAt(i+2);
			//연속성(+) 카운트
			if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1){
				samePass_1 = samePass_1 + 1;
			}
			//연속성(-) 카운트
			if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1){
				samePass_2 = samePass_2 + 1;
			}
		}
		/*
		if(samePass_0 > 1){
			alert("동일문자를 3번 이상 사용할 수 없습니다.");
			return false;
		}
		
		if(samePass_1 > 1 || samePass_2 > 1){
			alert("연속된 문자열(123 또는 321, abc, cba 등)을\n 3자 이상 사용 할 수 없습니다.");
			return false;
		}
		*/
		return true;
	}

	function updatePassword(){
		
		if(checkPassword()){			

			var passwordInfo = new Object();
			var bizNo = "";
			
			if(typeof($("#biz_no").attr("name"))!="undefined"){
				bizNo = $("#biz_no").val(); 
			}
			passwordInfo.password = $("#password").val();
			passwordInfo.new_password = $("#new_password").val();
			passwordInfo.biz_no = bizNo;
			var jsonInfo = JSON.stringify(passwordInfo);			
			$.ajax({
				url : "/commonManagement/systemManagement/userManagement/updatePassword",
				data : {obj : jsonInfo},
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.result) {	
						alert("비밀번호가 변경되었습니다.");
						$("#modal-user-settings").modal("hide");
					}else{
						if (!data.chkResult) {
							alert("입력하신 정보가 틀렸습니다.");
						}else{
							alert(data.message);
						}
					}
				}
			});
		}
	}
	
	function updatePasswordStatus(){

		$.ajax({
			url : "/commonManagement/systemManagement/userManagement/updatePasswordStatus",				
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.result) {							
					$("#modal-user-settings").modal("hide");
				}else{
					alert(data.message);
				}
			}
		});
	}
});