<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>

<f:setBundle  var="g" basename="lng.global" scope="application" />

<div>
	<form action="login" method="post">
		<table class="login">
			<tr>
				<th class="detailr"><c:out value="Username: "></c:out></th>
				<td><input type="text" name="uname" required="required"></td>
				<th class="detailr"><c:out value="Heslo: "></c:out></th>
				<td><input type="password" name="pass" required="required"></td>
				<td colspan="2"><input type="submit" value="Ok"></td>
			</tr>
		</table>
	</form>
</div>