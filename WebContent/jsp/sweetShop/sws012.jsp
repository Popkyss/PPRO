<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<c:set var="currentJsp" value="${actionBean.className}" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />
<f:message bundle="${b}" key="btn.title.pridat" var="btnPridat" />

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
			<h2><s:label name="actionBean.detailProduktu"/></h2>
			<hr>
			<table>
				<tr>
					<th><s:label name="actionBean.produkt.nazev"/></th>
					<td><c:out value="${actionBean.actionBean.produkt.nazev}"/></td>
				</tr>
				<tr>
					<th><s:label name="actionBean.produkt.cena"/> </th>
					<td><c:out value="${actionBean.actionBean.produkt.cena}"/> Kč</td>
				</tr>
				<tr>
					<th colspan="2">
						<s:submit name="pridatDoKosiku" title="${btnPridat}">
							${btnPridat}
						</s:submit>
					</th>
				</tr>
			</table>
		</div>
	</s:form>
</div>
	</s:layout-component>
	</s:layout-render>


