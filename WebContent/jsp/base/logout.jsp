<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form action="logout">
	<table class="login">
		<tr>
			<td>
				<input class="logout" type="submit" value="Odhlásit uživatele: <%out.print(session.getAttribute("username"));%>">
			</td>
		</tr>
	</table>
</form>
