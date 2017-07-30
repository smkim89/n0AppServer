<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes" />
	<link rel="stylesheet" type="text/css" href="/resources/css/goods.css"/>
    <title></title>
</head>
<body>
<input type="hidden" id="groupId" name="groupId" value="${groupId }"/>
<input type="hidden" id="userId" name="userId" value="${userId}"/>
<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
<input type="hidden" id="page" name="page" value="${page}"/>
<input type="hidden" id="menuSq" name="menuSq" value=""/>
<input type="hidden" id="menuCnt" name="menuCnt" value="1" />
<input type="hidden" id="orderSq" name="orderSq" value="" />

<div class="allWrap">
	<ul class="shopList" id="shopList">
	</ul>	
	
</div>
	<jsp:include page="../include/script.jsp" />
	<script type="text/javascript">
		$(document).ready(function() {
			init();
		});
		
		function init() {
			doSearch2(1);
			$(window).scroll(function(){
				if ($(window).scrollTop() == $(document).height() - $(window).height()){
					var page = $("#page").val();
					console.log(page);
					doSearch2(page+1);
				}
			});
			
			function doSearch2(page){
				var cd = $("#groupId").val();
				var param = {"groupId": cd, "page" : page};
				$.ajax({
			        type: 'POST',
			        url: "/n0App/menu/getList",
			        data: param,
			        dataType: 'html',
			        success: function(data) {
			        	if(page == 1){
							$("#shopList").html(data);
			        	}else{
			        		$("#page").val(page);
			        		$("#shopList").append(data);
			        	}
			        }
				});
			}
			
			$(document).on("click", ".btn-success", function(){
				var cnt = parseInt($("#menuCnt").val());
				document.getElementById('productCnt').innerHTML = cnt+1;
				$("#menuCnt").val(cnt+1);
			});
			
			$(document).on("click", ".btn-danger", function(){
				var cnt = parseInt($("#menuCnt").val());
				if(cnt>=0){
					document.getElementById('productCnt').innerHTML = cnt-1;
					$("#menuCnt").val(cnt-1);
				}
			});
			
			$(document).on("click", "#updateUserForm .btn-cancel", function(){
				$.PostUrl.postUrl($.User.listUser);
			});
			
			$("#btnOrder").on("click", function(){
				if($("#menuCnt").val()=="") {
					alert("수량을 입력해 주세요.");
					return;
				}
				var param = {"userId": $("#userId").val()
						, "groupId" : $("#groupId").val()
						, "uuid" : $("#uuid").val()
						, "menuSq" : $("#menuSq").val()
						, "menuCnt" : $("#menuCnt").val()
						, "orderSq" : $("#orderSq").val()	
				};
				$.ajax({
					url :  "/n0App/menu/order/insert",
					data : param,
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.result) {
							$('#modal-detail').modal('hide');
							$("#menuCnt").val(1);
							$("#orderSq").val(data.orderSq);
							window.HybridApp.passOrderSq(data.orderSq);
						} else {
							$.tmonet.alert("등록 실패 TODO : 사유");
						}
						$.unblockUI();
					}
				});
			
			});
		}
		
		$.Goods = {
			viewDetail : function (id, imgUrl, nm, amt, detail) {
				//window.HybridApp.showGoods(goodsId, goodsPrice);
				$("#menuCnt").val("1");
				 var image = document.getElementById('img1');
				image.src = imgUrl;
				$("#menuSq").val(id);
				document.getElementById('productNm').innerHTML = nm;
				document.getElementById('productDetail').innerHTML = detail;
				document.getElementById('productAmt').innerHTML = amt;
				document.getElementById('productCnt').innerHTML = 1;
				$('#modal-detail').modal('show');
			}	
		
			
		}
	</script>
	<div id="modal-detail" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="productNm"></h4>
				</div>
				
				<div class="modal-body">
				
					<div class="row">
                    <div class="col-md-6 product_img">
                        <img src="" id="img1" class="img-responsive">
                    </div>
                    <div class="col-md-6 product_content">
                        <div class="rating">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                        </div>
                        <br>
                        <p id="productDetail"></p>
                        <br>
                        <h3 class="cost" id="productAmt"><span class="glyphicon glyphicon-usd"></span></h3>
                        <div class="row">
                        	<br>
                        	<label class="col-md-6 col-sm-6 col-xs-12 control-label" for="qty">수량</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <strong id="productCnt">1</strong>
	                            <a href="javascript:void(0)" class="btn btn-xs btn-success" data-toggle="tooltip" title="Add"><i class="fa fa-plus"></i></a>
	                            <a href="javascript:void(0)" class="btn btn-xs btn-danger" data-toggle="tooltip" title="Remove"><i class="fa fa-minus"></i></a>
                            </div>
                        </div>
                        <div class="space-ten"></div>
                        
                    </div>
                </div>
					
				</div>
				<div class="modal-footer">
					<a class="btn btn-danger btn-ok" id="btnOrder">주문</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">메뉴</button>					
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>


