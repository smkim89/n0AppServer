<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<c:if test="${result == true}">
		<c:if test="${fn:length(list) > 0}">
			<c:forEach items="${list}" var="list" varStatus="status">
			<li>
				<a href="javascript:$.Goods.viewDetail('${list.MENU_SQ}','${list.MENU_IMG}','${list.MENU_NM}','${list.MENU_AMT}','${list.MENU_DETAIL}');" class="prodWrap">
					<div class="backImg"><img src="/resources/img/shop/shop_bg_01.png" alt="" /></div>
					<strong class="name"><c:out value="${list.MENU_NM}" /></strong>
					<p class="brand"><c:out value="${list.MENU_SUB_NM}" /></p>
					<div class="prodThum"><img src="${list.MENU_IMG}" alt="" /></div>
					<div class="price"> <c:out value="${list.MENU_AMT}" /></div>
				</a>
			</li>
			</c:forEach>
		</c:if>
	</c:if>
	<c:if test="${result == false && page<2}">
		<div style="position:absolute; top:50%; left:50%; width:300px; height:200px; overflow:hidden; margin-top:-50px; margin-left:-70px; ">
			<p class="Center"><font color="#8c8c8c">상품을 준비중 입니다.</font></p>
		</div>
	</c:if>

