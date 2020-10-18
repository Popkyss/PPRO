<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../base/taglibs.jsp" %>

<%@page import="com.popkyss.sweetShop.setting.HibernateTransactionFilter"%>

<c:set var="currentJsp" value="${actionBean.className}" />
<c:set var="nextJsp" value="com.popkyss.sweetShop.stripes.action.Sws000" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />
<f:message bundle="${b}" key="btn.title.zpet" var="btnZpet" />

<s:layout-render name="/jsp/layout/main_template.jsp">
	<s:layout-component name="contents">
	
	<div id="divMain" style="background-image: url('images/avengers.png');">
	<s:form beanclass="${actionBean.className}" >
		<%@ include file="../layout/messages.jsp"%>
		<div class="regUziv" id="regUziv">
			<h2><s:label name="actionBean.registraceZakaznika"/></h2>
			<hr>
			<table>
				<c:if test="${!actionBean.actionBean.adresaAdded}">
					<h3><s:label name="actionBean.adresaUdaje"/></h3>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.mesto"/> </th>
						<td><s:text name="actionBean.novyZakaznik.mesto"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.ulice"/> </th>
						<td><s:text name="actionBean.novyZakaznik.ulice"></s:text></td>
						
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.cp"/> </th>
						<td><s:text name="actionBean.novyZakaznik.cp" size="6"></s:text> / <s:text name="actionBean.novyZakaznik.cp2" size="3"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.psc"/> </th>
						<td><s:text name="actionBean.novyZakaznik.psc" size="6"></s:text></td>
					</tr>
					<tr>
						<th colspan="2"><s:submit name="zkontroulujAdresu">POTVRDIT</s:submit></th>
					</tr>
				</c:if>
				<c:if test="${actionBean.actionBean.adresaAdded}">
					<h3><s:link beanclass="${currentJsp}" title="${btnZpet}" event="zpetNaAdresu">⟵&nbsp;</s:link></h3>
					<h3><s:label name="actionBean.zakaznikUdaje"/></h3>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.email"/> </th>
						<td><s:text name="actionBean.novyZakaznik.email"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.username"/> </th>
						<td><s:text name="actionBean.novyZakaznik.username"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.heslo"/> </th>
						<td><s:password name="actionBean.novyZakaznik.heslo"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.heslo2"/> </th>
						<td><s:password name="actionBean.novyZakaznik.heslo2"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.jmeno"/> </th>
						<td><s:text name="actionBean.novyZakaznik.jmeno"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.prijmeni"/> </th>
						<td><s:text name="actionBean.novyZakaznik.prijmeni"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZakaznik.telefon"/> </th>
						<td><s:text name="actionBean.novyZakaznik.telefon"/></td>
					</tr>
					<tr>
						<th colspan="2"><s:submit id="tlacitko" name="ulozZakaznika">OK</s:submit></th>
					</tr>
				</c:if>
			</table>
		</div>

	</s:form>
</div>


	</s:layout-component>
	</s:layout-render>


