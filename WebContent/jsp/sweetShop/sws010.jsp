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
			
			<c:forEach items="${actionBean.actionBean.polozky}" var="polozka" varStatus="stat">
				<div class="regUziv">
					<table>
						<tr>
							<th><s:label name="actionBean.nazev"/>:</th>
							<td colspan="2">
								<s:link beanclass="${viewJsp}" title="${btnProhlizet}" event="view">
									<s:param name="idProdukt" value="${polozka.idProdukt}"></s:param>
									<c:out value="${polozka.nazev}"/>
								</s:link>
							</td>
						</tr>
						<tr>
							<th><s:label name="actionBean.pocet"/>:</th>
							<td><c:out value="${polozka.pocet}"/></td>
							<td>
								<s:link beanclass="${currentJsp}" onclick="return confirmSmazat(this,'${btnSmazat}');" class="SmazatBtnM btnM" title="${btnSmazat}" event="smazat">
									<s:param name="idPolozka" value="${polozka.idPolozka}"></s:param>
									&nbsp;
								</s:link>
							</td>
						</tr>
						<tr>
							<th><s:label name="actionBean.cena"/>:</th>
							<td colspan="2"><c:out value="${polozka.cena}"/> Kč</td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>
	</s:layout-component>
</s:layout-render>