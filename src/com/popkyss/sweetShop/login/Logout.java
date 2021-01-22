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

public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = -6995442456568624832L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		session.removeAttribute("username");
		session.removeAttribute("password");
		session.removeAttribute("isZamestnancem");
		session.removeAttribute("kosik");
		session.setAttribute("nasetovany", false);
		
		HlavickaBean hb  = (HlavickaBean) session.getAttribute("hlavickaBean");
		hb.hlavicka.omezHlavickuSeznam((Boolean) session.getAttribute("isZamestnancem"));
		session.setAttribute("hlavickaBean", hb);
		
		response.sendRedirect("sws000.action");
	}

}
