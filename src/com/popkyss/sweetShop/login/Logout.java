package com.popkyss.sweetShop.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.popkyss.sweetShop.setting.HlavickaBean;

/**
 * Servlet implementation class Logout
 */

//@WebServlet("/logout")
public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = -6995442456568624832L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.removeAttribute("username");
		session.removeAttribute("password");
		session.removeAttribute("isZamestnancem");
		session.removeAttribute("kosik");
		session.setAttribute("nasetovany", false);
		session.setAttribute("hlavickaBean", new HlavickaBean());
		
		response.sendRedirect("sws000.action");
	}

}
