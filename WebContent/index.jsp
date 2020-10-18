<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.popkyss.sweetShop.setting.WebAppSettings"%>
<%
	String url = "./";
	url += WebAppSettings.getAppPrefix() + WebAppSettings.getWelcomePageNumber();
	url += ".action";
	response.setContentType("text/html");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	response.setHeader("Location", url);
	response.setStatus(301);
%>
