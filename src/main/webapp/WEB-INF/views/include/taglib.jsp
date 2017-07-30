<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ui" uri="http://www.t-monet.com/ctl/ui"%>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="requestURI" value="${requestScope['javax.servlet.forward.request_uri'] }"/>


	<link rel="shortcut icon" href="/resources/img/favicon.ico">
		<!-- Stylesheets -->
		<link rel="stylesheet" href="/resources/css/bootstrap.min.css" type="text/css">
		<link rel="stylesheet" href="/resources/css/plugins.css" type="text/css">
		<link rel="stylesheet" href="/resources/css/main.css" type="text/css">
		<link rel="stylesheet" href="/resources/css/themes.css" type="text/css">
		<link rel="stylesheet" href="/resources/css/bootstrap-datetimepicker.css" type="text/css">
		<link rel="stylesheet" href="/resources/css/bootstrap-datetimepicker-standalone.css" type="text/css">
		<link rel="stylesheet" href="/resources/css/cleanBlog.css?ver=1" type="text/css">