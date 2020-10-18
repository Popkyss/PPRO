<%@page import="com.popkyss.sweetShop.model.PrepravkaKosik"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@page import="java.util.Date"%>

<%@page import="com.popkyss.sweetShop.setting.MenuBean"%>
<%@page import="com.popkyss.sweetShop.setting.StrankaNavratBean"%>
<%@page import="com.popkyss.sweetShop.setting.WebAppSettings"%>
<%@page import="com.popkyss.sweetShop.setting.HlavickaBean"%>
<%@page import="com.popkyss.sweetShop.setting.ApplicationFilter"%> 

<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="s" uri="http://stripes.sourceforge.net/stripes.tld"%>

<%
 	String url = (String) request.getAttribute(ApplicationFilter.ACTION);
 	if (url == null) url = WebAppSettings.getAppPrefix() + "001";
 	String pageNumber = url.substring(0, 6);
 	HlavickaBean hb = (HlavickaBean) session.getAttribute(HlavickaBean.HLAVICKA_SESSION_SPACE);
 	if (hb == null) {
 		hb = new HlavickaBean();
 	}
 	hb.setStranka(pageNumber);
 	request.setAttribute("appPrefix", WebAppSettings.getAppPrefix());
 	session.setAttribute(HlavickaBean.HLAVICKA_SESSION_SPACE, hb);
 	PrepravkaKosik kosik = (PrepravkaKosik) session.getAttribute("kosik");
 %>

<%@include file="../base/taglibs.jsp" %>

<f:message bundle="${g}" key="app.kosik" var="btnKosik"/>
<jsp:useBean id="localesBean" scope="session" class="com.popkyss.sweetShop.setting.LocalesBean"></jsp:useBean>


<div id="nazev_aplikace">
	<div id="pageNumber"><strong><f:message bundle="${g}" key="app.nazev" /></strong> | <%=pageNumber%></div> 
	<div id="pageTitle"><h1><f:setBundle basename="lng.${hlavickaBean.stranka}" var="locBundle" scope="page" /><f:message bundle="${locBundle}" key="pageName" /></h1></div>		
	<div id="pageKosik">
		<% if(kosik != null) { %>
			<s:link beanclass="com.popkyss.sweetShop.stripes.action.Sws010"  title="${btnKosik}">
			<strong>${btnKosik} (<%=kosik.getPocet()%>)</strong>
			</s:link>
		<% } %>
	</div> 
	<div class="clear"></div>
</div>
<div id="menu">
	
	<c:forEach items="${hlavickaBean.menuSeznam}" var="x2" varStatus="stat">
	<span class="noWrap">
		<a href="${appPrefix}${x2.cisloMenu}.action" class="${x2.selected ? 'active' : ''}"> <f:message bundle="${g}" key="menu.${x2.cisloMenu}" /></a>
	</span>
	</c:forEach>
</div>
<div id="navigace">
	<div id="navigace_menu">
		<c:forEach items="${hlavickaBean.navratSeznam}" var="x2" varStatus="stat">
			<c:if test="${stat.index > 0 }">&gt;</c:if>
			<f:setBundle basename="lng.${x2.stranka}" var="locBundle" scope="page" />
			<s:link beanclass="${x2.action}"><f:message bundle="${locBundle}" key="pageName" /></s:link>
		</c:forEach>
	</div>
</div>


