<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="d" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix ="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="sec" uri="http://www.stripes-stuff.org/security.tld"%>

<%@ taglib prefix="ckeditor" uri="http://ckeditor.com" %>
<%@ taglib prefix="poky" uri="popkyss.tld" %>

<%@page import="java.util.Locale"%>
<%@page import="com.popkyss.sweetShop.setting.LocaleFilter"%>
<% 

	String language = (String)session.getAttribute(LocaleFilter.REQUEST_LANGUAGE);
	Locale locale = (Locale)session.getAttribute(LocaleFilter.REQUEST_LOCALE);
	if(language == null || locale == null){
		locale = new Locale(LocaleFilter.DEFAULT_LANGUAGE); 
		session.setAttribute(LocaleFilter.REQUEST_LOCALE, locale);
		session.setAttribute(LocaleFilter.REQUEST_LANGUAGE, LocaleFilter.DEFAULT_LANGUAGE);
	}
	
%>

<f:setLocale value="<%=((Locale)session.getAttribute(LocaleFilter.REQUEST_LOCALE)).getLanguage() %>" scope="page"/>
<f:setBundle  var="g" basename="lng.global" scope="application"  />
<f:message bundle="${g}" key="app.nazev" var="appNazev" />

