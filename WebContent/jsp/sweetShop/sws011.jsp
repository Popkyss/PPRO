<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../base/taglibs.jsp" %>

<%@page import="com.popkyss.sweetShop.setting.HibernateTransactionFilter"%>


<c:set var="currentJsp" value="${actionBean.className}" />
<c:set var="viewJsp" value="com.popkyss.sweetShop.stripes.action.Sws012" />

<f:setBundle var="b" basename="lng.sws${actionBean.actionNumber}" scope="page" />
<f:message bundle="${b}" key="btn.title.view" var="btnProhlizet" />
<f:message bundle="${b}" key="btn.title.smazat" var="btnSmazat" />

<s:layout-render name="/jsp/layout/main_template.jsp">
	<s:layout-component name="contents">
		<div id="divMain" style="background-image: url('images/avengers.png');">
			
			<c:forEach items="${actionBean.actionBean.produkty}" var="produkt" varStatus="stat">
				<div class="regUziv">
					<s:link beanclass="${viewJsp}" title="${btnProhlizet}" event="view">
						<s:param name="idProdukt" value="${produkt.idProdukt}"></s:param>
						<table>
							<tr>
								<th><s:label name="actionBean.nazev"/>:</th>
								<td colspan="2"><c:out value="${produkt.nazev}"/></td>
							</tr>
							<tr>
								<th><s:label name="actionBean.cena"/>:</th>
								<td colspan="2"><c:out value="${produkt.cena}"/> Kč</td>
							</tr>
						</table>
					</s:link>
				</div>
			</c:forEach>
		</div>
	</s:layout-component>
</s:layout-render>