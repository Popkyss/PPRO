<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<%@page import="com.popkyss.sweetShop.setting.HibernateTransactionFilter"%>


<c:set var="currentJsp" value="${actionBean.className}" />
<c:set var="nextJsp" value="com.popkyss.sweetShop.stripes.action.Sws000" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />


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
						<th><s:label name="actionBean.novyZamestnanec.mesto"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.mesto"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.ulice"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.ulice"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.cp"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.cp" size="6"></s:text> / <s:text name="actionBean.novyZamestnanec.cp2" size="3"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.psc"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.psc" size="6"></s:text></td>
					</tr>
					<tr>
						<th colspan="2"><s:submit name="zkontroulujAdresu">POTVRDIT</s:submit></th>
					</tr>
				</c:if>
				<c:if test="${actionBean.actionBean.adresaAdded}">
					<h3><s:link beanclass="${currentJsp}" title="${btnZpet}" event="zpetNaAdresu">⟵&nbsp;</s:link></h3>
					<h3><s:label name="actionBean.zakaznikUdaje"/></h3>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.email"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.email"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.username"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.username"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.heslo"/> </th>
						<td><s:password name="actionBean.novyZamestnanec.heslo"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.heslo2"/> </th>
						<td><s:password name="actionBean.novyZamestnanec.heslo2"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.jmeno"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.jmeno"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.prijmeni"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.prijmeni"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.novyZamestnanec.telefon"/> </th>
						<td><s:text name="actionBean.novyZamestnanec.telefon"/></td>
					</tr>
					<tr>
						<th colspan="2"><s:submit name="ulozZamestnance">OK</s:submit></th>
					</tr>
				</c:if>
			</table>
		</div>

	</s:form>
</div>


	</s:layout-component>
	</s:layout-render>


