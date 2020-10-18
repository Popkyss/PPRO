<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<%@page import="com.popkyss.sweetShop.setting.HibernateTransactionFilter"%>


<c:set var="currentJsp" value="${actionBean.className}" />
<c:set var="nextJsp" value="com.popkyss.sweetShop.stripes.action.Sws000" />
<c:set var="editJsp" value="com.popkyss.sweetShop.stripes.action.Sws004" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />

<s:layout-render name="/jsp/layout/main_template.jsp">
	<s:layout-component name="contents">
		<div id="divMain" style="background-image: url('images/avengers.png');">
			
			<c:forEach items="${actionBean.actionBean.uzivatele}" var="user" varStatus="stat">
				
				<s:link beanclass="${editJsp}" title="${editUzivatel}" event="edit">
					<s:param name="idZamestnanec" value="${user.idZamestnanec}"></s:param>
				
				<div class="regUziv">
					<table>
						<tr>
							<th><s:label name="actionBean.username"/> </th>
							<td><c:out value="${user.username}"></c:out></td>
						</tr>
						<tr>
							<th><s:label name="actionBean.jmeno"/> </th>
							<td><c:out value="${user.jmeno}"></c:out></td>
						</tr>
						<tr>
							<th><s:label name="actionBean.telefon"/> </th>
							<td><c:out value="${user.telefon}"></c:out></td>
						</tr>
						<tr>
							<th><s:label name="actionBean.email"/> </th>
							<td><c:out value="${user.email}"></c:out></td>
						</tr>
					</table>
				</div>
				</s:link>
			</c:forEach>
		</div>
	</s:layout-component>
</s:layout-render>