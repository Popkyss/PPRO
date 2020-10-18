<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<c:set var="currentJsp" value="${actionBean.className}" />
<c:set var="nextJsp" value="com.popkyss.sweetShop.stripes.action.Sws002" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />
<f:message bundle="${b}" key="btn.title.registrace" var="registrace" />

<s:layout-render name="/jsp/layout/main_template.jsp">
	<s:layout-component name="contents">
		<div id="divMain" style="background-image: url('images/avengers.png');">
			<form>
				<div class="uvod">
				<h1><s:label name="uvod"/></h1>
				<table>
					<tr><th><s:label name="zaklad"/></th></tr>
					<tr><th><s:label name="zaklad2"/></th></tr>
					<tr>
						<th><s:label name="zaklad3"/><s:link beanclass="${nextJsp}" title="${registrace}"> zde.</s:link></th>
					</tr>
					<tr><th><s:label name="zaklad4"/><a href="mailto:honzapopkyss@gmail.com?subject=SweetShop">honzapopkyss@gmail.com</a></th></tr>				
				</table>
				</div>
				
			</form>
		</div>
	</s:layout-component>
</s:layout-render>
