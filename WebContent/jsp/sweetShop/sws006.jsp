<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<c:set var="currentJsp" value="${actionBean.className}" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />
<f:message bundle="${b}" key="btn.title.editAdresu" var="btnEditAdresu" />
<f:message bundle="${b}" key="btn.title.editHeslo" var="btnEditHeslo" />
<f:message bundle="${b}" key="btn.title.ulozitHeslo" var="btnUlozitHeslo" />
<f:message bundle="${b}" key="btn.title.ulozitAdresu" var="btnUlozitAdresu" />
<f:message bundle="${b}" key="btn.title.ulozit" var="btnUlozit" />
<f:message bundle="${b}" key="btn.title.smazat" var="btnSmazat" />
<f:message bundle="${b}" key="btn.title.zpet" var="btnZpet" />

<s:layout-render name="/jsp/layout/main_template.jsp">
	<s:layout-component name="script">
	</s:layout-component>
	<s:layout-component name="bodyOnLoad">
	</s:layout-component>
    <s:layout-component name="contents">

<div id="divMain" style="background-image: url('images/avengers.png');">
	<s:form name="test1" id="test1" beanclass="${actionBean.className}" >

		<%@ include file="../layout/messages.jsp"%>

		<div class="regUziv" id="regUziv">
			<h2><s:label name="actionBean.editace"/></h2>
			<hr>
			<c:if test="${!actionBean.actionBean.zmenitAdresu && !actionBean.actionBean.zmenitHeslo}">
			<table>
				<tr>
					<th><s:label name="actionBean.uzivatel.email"/> </th>
					<td><c:out value="${actionBean.actionBean.uzivatel.email}"/></td>
				</tr>
				<tr>
					<th><s:label name="actionBean.uzivatel.username"/> </th>
					<td><c:out value="${actionBean.actionBean.uzivatel.username}"/></td>
				</tr>
				<tr>
					<th><s:label name="actionBean.uzivatel.jmeno"/> </th>
					<td><s:text name="actionBean.uzivatel.jmeno" /></td>
				</tr>
				<tr>
					<th><s:label name="actionBean.uzivatel.prijmeni"/> </th>
					<td><s:text name="actionBean.uzivatel.prijmeni" /></td>
				</tr>
				<tr>
					<th><s:label name="actionBean.uzivatel.telefon"/> </th>
					<td><s:text name="actionBean.uzivatel.telefon" /></td>
				</tr>
				</table>
				</c:if>
				
				<c:if test="${actionBean.actionBean.zmenitHeslo}">
				<table>
					<tr>
						<th><s:label name="actionBean.uzivatel.heslo2"/> </th>
						<td><s:password name="actionBean.uzivatel.heslo2"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.noveHeslo"/> </th>
						<td><s:password name="actionBean.noveHeslo"/></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.noveHeslo2"/> </th>
						<td><s:password name="actionBean.noveHeslo2"/></td>
					</tr>
					<tr>
						<td>
							<s:link beanclass="${currentJsp}" class="btn Zpet2Btn" title="${btnZpet}" event="editovatHeslo">&nbsp;</s:link>
						</td>
						<td class="detailr"><s:submit name="ulozitHeslo" class="btn SaveBtn" title="${btnUlozitHeslo}" value=" "/></td>
					</tr>
				</table>
				</c:if>
				
				<c:if test="${actionBean.actionBean.zmenitAdresu}">
				<table>
					<tr>
						<th><s:label name="actionBean.uzivatel.mesto"/> </th>
						<td><s:text name="actionBean.uzivatel.mesto"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.uzivatel.ulice"/> </th>
						<td><s:text name="actionBean.uzivatel.ulice"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.uzivatel.cp"/> </th>
						<td><s:text name="actionBean.uzivatel.cp" size="6"></s:text> / <s:text name="actionBean.uzivatel.cp2" size="3"></s:text></td>
					</tr>
					<tr>
						<th><s:label name="actionBean.uzivatel.psc"/> </th>
						<td><s:text name="actionBean.uzivatel.psc" size="6"></s:text></td>
					</tr>
					<tr>
						<th>
							<s:link beanclass="${currentJsp}" class="btn Zpet2Btn" title="${btnZpet}" event="editovatAdresu">&nbsp;</s:link>
						</th>
						<th><s:submit name="ulozitAdresu" class="btn SaveBtn" title="${btnUlozitAdresu}" value=" "/></th>
					</tr>
				</table>
				</c:if>
			
			<c:if test="${!actionBean.actionBean.zmenitAdresu && !actionBean.actionBean.zmenitHeslo}">
			<br />
			<table>
				<tr>
					<td>	
						<s:link beanclass="${currentJsp}" onclick="return confirmSmazat(this,'${btnSmazat}');" class="SmazatBtn btn" title="${btnSmazat}" event="smazat">
							<s:param name="idZamestnance" value="${actionBean.actionBean.uzivatel.idUzivatel}"></s:param>
							&nbsp;
						</s:link>
					</td>
					<td>
						<s:submit name="ulozit" class="btn SaveBtn" title="${btnUlozit}" value=" " />
					</td>
					<td align="center">
						<s:link beanclass="${currentJsp}" class="EditBtn btn" title="${btnEditAdresu}" event="editovatAdresu">&nbsp;</s:link>
					</td>
					<td align="center">
						<s:link beanclass="${currentJsp}" class="ZamekBtn btn" title="${btnEditHeslo}" event="editovatHeslo">&nbsp;</s:link>
					</td>
				</tr>
			</table>
			</c:if>
		</div>
	</s:form>
</div>
	</s:layout-component>
	</s:layout-render>


