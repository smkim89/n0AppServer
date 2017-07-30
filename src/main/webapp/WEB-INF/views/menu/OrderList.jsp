<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglib.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]><html class="no-js lt-ie9"> <![endif]-->
<!--[if IE 9]><html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!--><html class="no-js"><!--<![endif]-->
<style>
.menuInfo {
	font-size: 200%;
}
.userInfo {
	font-size: 200%;
	color: #FF0000;
}
</style>
<head>
<body>
	<section class="site-content site-section">
    <div class="container">
        <div class="table-responsive">
            <table class="table table-bordered table-vcenter">
                <thead>
                    <tr>
                        <th colspan="2">상품</th>
                        <th class="text-center">수량</th>
                        <th class="text-right">상품금액</th>
                        <th class="text-right">결제금액</th>
                    </tr>
                </thead>
                <tbody>
                	<c:choose>
                	<c:when test="${fn:length(orderList) > 0}">
						<c:forEach items="${orderList}" var="orderList" varStatus="status">
							<tr>
		                        <td style="width: 200px;">
		                            <img src="${orderList.MENU_IMG }" alt="" style="width: 180px;">
		                        </td>
		                        <td>
		                            <h1 class="menuInfo"><strong>${orderList.MENU_NM }</strong></h1><br>
		                            ${orderList.MENU_SUB_NM }<br>
		                            <!-- <strong class="text-success">In stock</strong> - 24h Delivery -->
		                        </td>
		                        <td class="text-center">
		                            <h1 class="menuInfo"><strong>1</strong></h1>
		                            <a href="javascript:void(0)" class="btn btn-xs btn-success" data-toggle="tooltip" title="Add"><i class="fa fa-plus"></i></a>
		                            <a href="javascript:void(0)" class="btn btn-xs btn-danger" data-toggle="tooltip" title="Remove"><i class="fa fa-minus"></i></a>
		                        </td>
		                        <td class="text-right"><h1 class="menuInfo"><c:out value="${orderList.MENU_AMT}" /></h1></td>
		                        <td class="text-right"><h1 class="userInfo"><strong><c:out value="${orderList.ORDER_AMT}" /></strong></h1></td>
		                    </tr>
						</c:forEach>
							<tr>
		                        <td colspan="4" class="text-right h4"><h1 class="menuInfo"><strong>결제 금액</strong></h1></td>
		                        <td class="text-right h4"><h1 class="menuInfo"><strong>907,00</strong></h1></td>
		                    </tr>
		                    <tr>
		                        <td colspan="4" class="text-right h4"><h1 class="menuInfo"><strong>수수료 (20%)</strong></h1></td>
		                        <td class="text-right h4"><h1 class="menuInfo"><strong>$181,40</strong></h1></td>
		                    </tr>
		                    <tr class="active">
		                        <td colspan="4" class="text-right text-uppercase h4"><h1 class="menuInfo"><strong>합계 금액</strong></h1></td>
		                        <td class="text-right text-success h4"><h1 class="userInfo"><strong>$1088,40</strong></h1></td>
		                    </tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="5" class="text-center">
							조회된 정보가 없습니다.
							</td>
						</tr>
					</c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        
        <div class="row">
            <div class="col-xs-7 col-md-3">
                <a href="ecom_product_list.php" class="btn btn-block btn-primary">Continue Shopping</a>
            </div>
            <div class="col-xs-5 col-md-3 col-md-offset-6">
                <a href=ecom_checkout.php class="btn btn-block btn-danger">Checkout</a>
            </div>
        </div>
    </div>
</section>

	
	
	
	

	<%-- <jsp:include page="../include/script.jsp" />
	<script type="text/javascript" src="/resources/js/payco/tradeManagement/payTradeManagement/inAppTradeIqry.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$.InAppTrade.listInit();
	});
	</script> --%>
	
	
</body>
</html>