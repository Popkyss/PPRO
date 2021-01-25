<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>

<f:setBundle var="g" basename="lng.global" scope="page" />

<table>
	<tr>
		<td align="left">
			&copy; <f:message bundle="${g}" key="app.info.name" />
		</td>
		<td align="right">
			<f:message bundle="${g}" key="app.zobrazeno" />
			<span id="footerCurrentDateId">00.00.0000 00:00:00</span>
		</td>
	</tr>
</table>

