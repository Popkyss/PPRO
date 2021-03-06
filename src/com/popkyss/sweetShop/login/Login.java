package com.popkyss.sweetShop.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.IZbozi;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.HlavickaBean;

public class Login extends HttpServlet  {
	
	private static final long serialVersionUID = 1L; 
	
	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();
	private final IZbozi zbozi = SweetShopServiceFactory.getZbozi();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		String uname = request.getParameter("uname");
		String pass = request.getParameter("pass");	
		
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		if(session != null) {
			Uzivatel u = opravneni.najdiUzivatele(uname, pass);
			if(u != null) {	
				session.setAttribute("username", uname);
				session.setAttribute("password", pass);
				session.setAttribute("nasetovany", false);
				
				if(u.getIdZamestnanec() != null) {
					session.setAttribute("isZamestnancem", true);
				} else {
					session.setAttribute("isZamestnancem", false);
					session.setAttribute("kosik",zbozi.najdiKosikZakaznika(u.getIdZakaznik()));
				}
				
				HlavickaBean hb  = (HlavickaBean) session.getAttribute("hlavickaBean");
				hb.hlavicka.omezHlavickuSeznam((Boolean) session.getAttribute("isZamestnancem"));
				session.setAttribute("hlavickaBean", hb);
				response.sendRedirect("sws000.action");
			} else {
				if(session != null) {
					response.sendRedirect("sws007.action");				
				}
			}
		}
	}

}
