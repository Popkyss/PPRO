package com.popkyss.sweetShop.setting;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class HibernateTransactionFilter implements Filter {
	private static final Logger log = Logger.getLogger(HibernateTransactionFilter.class);

	public static final String TRANSACTION_STATUS = "transaction_status";
	public static final String REQUEST_ID = "double.click.request.id";
	private static final String DOUBLE_CLICK_PARAM_NAME = "doubleClick";
	private static final String IGNORE_AJAX_PARAM_NAME = "ignoreAjax";
	private static boolean useDoubleClickProtect;
	private static boolean ignoreAjaxRequests;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try {
			markInRequest(request);
			startHibernateSession(request);
			chain.doFilter((ServletRequest) request, (ServletResponse) response);
			endTransaction(request);
		} catch (Exception e) {
			log.error("Hibernate transaction filter exception");
			request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);
			try {
				endTransaction(request);
			} catch (Exception e2) {
				log.fatal("Nepovedlo se rollbackovat transakci!!!");
				throw new RuntimeException(e2);
			}
			throw new ServletException("Chyba v HibernateTransactionFiltr: " + e.getMessage(), e);
		}
	}

	private static void markInRequest(HttpServletRequest request) {
		if (ignoreAjaxRequests && isAjaxRequest(request)) {
			log.trace("Ajax pozadavek na zacatku zpracovani - ignoruji");

			return;
		}
		HttpSession session = request.getSession();
		if (session != null && useDoubleClickProtect) {
			String value = String.valueOf(request.getMethod()) + "_" + (new Date()).getTime();
			session.setAttribute("double.click.request.id", value);
			request.setAttribute("double.click.request.id", value);
			log.trace("Pozadavek " + value + " vstupuje do zpracovani");
		}
	}

	private static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return (header != null && header.equals("XMLHttpRequest"));
	}

	private static void checkOutRequest(HttpServletRequest request) {
		if (ignoreAjaxRequests && isAjaxRequest(request)) {
			log.trace("Ajax pozadavek na konci zpracovani - ignoruji");

			return;
		}
		HttpSession session = request.getSession();
		if (session != null && useDoubleClickProtect) {
			String sessionValue = (String) session.getAttribute("double.click.request.id");
			String requestValue = (String) request.getAttribute("double.click.request.id");

			if (sessionValue == null || requestValue == null) {

				log.trace("Request id nebylo nalezeno v session nebo v requestu");
				request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);
			} else if (!sessionValue.equals(requestValue)) {
				String page = (String) request.getAttribute("Action");
				log.info("Detekovan dvojklik!!!!!! - odstrizeno " + page + " " + requestValue);
				request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);
			} else {
				log.trace("POSILAM DAL " + requestValue);
			}
		}
	}

	private void startHibernateSession(HttpServletRequest request) throws HibernateSessionContextException {
		TransactionSwitchSet etss = (TransactionSwitchSet) request.getAttribute("transaction_status");
		if (log.isTraceEnabled()) {
			log.trace("hibernate transaction start status: " + etss);
		}

		if (log.isDebugEnabled()) {
			log.debug("hibernate: vytvari se nova transakce");
		}
		createHs(request);
	}

	private static void createHs(HttpServletRequest request)
			throws HibernateSessionContextException, IllegalStateException {
		if (HibernateSessionContext.begin()) {
			if (log.isTraceEnabled()) {
				log.trace("Nastartovana nova transakce");
			}
			request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_PROCESS);
			if (log.isTraceEnabled()) {
				log.trace("hibernate status transakce: " + TransactionSwitchSet.TRANSACTION_PROCESS.toString());
			}
		}
	}

	public static void endTransaction(HttpServletRequest request) throws HibernateSessionContextException {
		if (request != null) {
			HttpSession session = request.getSession();
			if (session != null) {
				checkOutRequest(request);

				TransactionSwitchSet etss = (TransactionSwitchSet) request.getAttribute("transaction_status");
				if (etss == null) {
					etss = TransactionSwitchSet.TRANSACTION_ROLLBACK;
				}
				if (log.isTraceEnabled()) {
					log.trace("Ukoncovani transakce se statusem: " + etss.toString());
				}
				try {
					endHsTransaction(etss, request);
				} catch (HibernateSessionContextException e) {
					request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_NEW);
					log.error("Chyba pri zapisovani do DB: - commit nezdaren ", (Throwable) e);
					throw e;
				}
			} else {
				log.info("vyprsela session uzivatele - session je null");
			}
		} else {
			log.info("vyprsela session uzivatele - request je null");
		}
	}

	private static void endHsTransaction(TransactionSwitchSet etss, HttpServletRequest request)
			throws HibernateSessionContextException {

		if (etss == null) {
			HibernateSessionContext.tryFlush();
			HibernateSessionContext.tryClear();
		}
		if (HibernateSessionContext.commit()) {
			request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_NEW);
			if (log.isDebugEnabled()) {
				log.debug("hibernate transaction: Proveden commit");
			}
			if (log.isTraceEnabled()) {
				log.trace("hibernate end transaction status: " + TransactionSwitchSet.TRANSACTION_NEW.toString());
			}
			return;
		}
		
		HibernateSessionContext.tryClear();

		if (HibernateSessionContext.rollback()) {
			request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_NEW);
			if (log.isDebugEnabled()) {
				log.debug("hibernate transaction: Proveden rollback");
			}
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig cfg) throws ServletException {
		useDoubleClickProtect = getInitValue(DOUBLE_CLICK_PARAM_NAME, cfg);
		ignoreAjaxRequests = getInitValue(IGNORE_AJAX_PARAM_NAME, cfg);
		log.debug("Double click protection: " + useDoubleClickProtect);
		if (useDoubleClickProtect) {
			log.debug("Ignoring ajax requests: " + ignoreAjaxRequests);
		}
	}

	private boolean getInitValue(String paramName, FilterConfig cfg) {
		String value = cfg.getInitParameter(paramName);
		boolean booleanValue = Boolean.parseBoolean(value);
		return booleanValue;
	}
}