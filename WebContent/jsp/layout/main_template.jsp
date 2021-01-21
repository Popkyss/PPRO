<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.popkyss.sweetShop.setting.HibernateTransactionFilter"%>
<%@page import="com.popkyss.sweetShop.setting.WebAppSettings"%>
<%@page import="com.popkyss.sweetShop.setting.PropertyReader"%>
<%@page import="com.popkyss.sweetShop.setting.ApplicationFilter"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>


<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>
<%
	String pageTitle = PropertyReader.bundle((String) request.getAttribute(ApplicationFilter.ACTION), "pageTitle");
%>


<s:layout-definition>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

	<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title><c:out value="<%=pageTitle%>" /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no" />

<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/ikony.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.10.1.custom.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-datetime_picker.css" rel="stylesheet" type="text/css" />


<script src="js/base.js" type="text/javascript"></script>
<script src="js/default.js" type="text/javascript"></script>
<script src="js/prototype.js" type="text/javascript"></script>
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker-cs.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>

<s:layout-component name="script" />
</head>

<%@include file="../base/taglibs.jsp" %>

<c:set var="onLoadScript">
	<s:layout-component name="bodyOnLoad" />
</c:set>

<c:set var="onLoadScript">
	<s:layout-component name="bodyOnLoad" />
</c:set>
<body
	onload="changeLanguageMenu('<%=(String)session.getAttribute(LocaleFilter.REQUEST_LANGUAGE)%>'); 
		setFocus('<%=(String)request.getAttribute("label_focus") %>'); writeCurrentDate(); ${empty onLoadScript ? '' : onLoadScript};">

	<div id="hlavicka">
		<input type="hidden" id="appName" name="appName" value="${appNazev}" />
		<div id="hlavicka_logo">
			<h1><c:out value="${appNazev}"></c:out></h1>

<%if (session.getAttribute("username") == null) {%>
	<%@include file="../base/login.jsp"%>
<%} else {%>
	<%@include file="../base/logout.jsp"%>
<%}%>
					
		</div>
	</div>
	<div id="page">
		<s:layout-component name="header">
			<jsp:include page="/jsp/layout/header.jsp" />
		</s:layout-component>
		<div id="content">
			<s:layout-component name="contents" />
		</div>
		<div id="paticka">
			<s:layout-component name="footer">
				<jsp:include page="/jsp/layout/footer.jsp" />
			</s:layout-component>
		</div>
	</div>
</body>

	</html>


</s:layout-definition>

