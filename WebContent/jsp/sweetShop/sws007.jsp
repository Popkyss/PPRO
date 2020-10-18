<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<c:set var="currentJsp" value="${actionBean.className}" />
<c:set var="nextJsp" value="com.popkyss.sweetShop.stripes.action.Sws002" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />
<f:message bundle="${b}" key="btn.title.zapHeslo" var="btnZapomenuteHeslo" />
<f:message bundle="${b}" key="btn.title.noveHeslo" var="btnNoveHeslo" />
<f:message bundle="${b}" key="btn.title.zpet" var="btnZpet" />
<f:message bundle="${b}" key="btn.title.registrace" var="btnRegistrace" />

<s:layout-render name="/jsp/layout/main_template.jsp">
	<s:layout-component name="script">
	</s:layout-component>
	<s:layout-component name="bodyOnLoad">
	</s:layout-component>
    <s:layout-component name="contents">

<div id="divMain" style="background-image: url('images/avengers.png');">
	<s:form beanclass="${actionBean.className}" >

		<%@ include file="../layout/messages.jsp"%>

		<div class="regUziv" id="regUziv">
			<h2><s:label name="actionBean.prihlaseni"/></h2>
			<hr>
			<c:if test="${!actionBean.actionBean.zmenHeslo}">
			<table>
				<tr>
					<th><s:label name="actionBean.username"/> </th>
					<td><s:text name="actionBean.username" /></td>
				</tr>
				<tr>
					<th><s:label name="actionBean.heslo"/> </th>
					<td><s:text name="actionBean.heslo" /></td>
				</tr>
					<tr>
						<th colspan="2"><s:submit name="prihlaseni">OK</s:submit></th>
					</tr>
					
			</table>
			<h3><s:link beanclass="${currentJsp}" title="${btnZapomenuteHeslo}" event="zmenZapomenuteHeslo">
					${btnZapomenuteHeslo}
				</s:link>
			</h3>
			<h3><s:link beanclass="${nextJsp}" title="${btnRegistrace}">${btnRegistrace}</s:link></h3>		
			</c:if>
			<c:if test="${actionBean.actionBean.zmenHeslo}">
			<table>
				<tr>
					<th><s:label name="actionBean.email"/> </th>
					<td><s:text name="actionBean.email"/></td>
				</tr>
				<tr>
					<th>
						<s:link beanclass="${currentJsp}" class="btn ZpetBtn" title="${btnZpet}" event="zmenZapomenuteHeslo">
						&nbsp;
						</s:link>
					<th>
					<th><s:submit name="zapomenuteHeslo" class="btn OkBtn" title="${btnNoveHeslo}" value=" "/></th>
				</tr>
			</table>
			</c:if>
		</div>
	</s:form>
</div>
	</s:layout-component>
	</s:layout-render>


