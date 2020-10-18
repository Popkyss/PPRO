package com.popkyss.sweetShop.setting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.config.Configuration;

public class ExceptionHandler {
	public void handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);

		if (throwable instanceof net.sourceforge.stripes.exception.ActionBeanNotFoundException) {
			throwable = new RuntimeException(PropertyReader.bundle("999.controlerNotFound"), throwable);
		} else if (throwable instanceof net.sourceforge.stripes.controller.FileUploadLimitExceededException) {
			throwable = new RuntimeException(PropertyReader.bundle("999.fileTooBig"), throwable);
		}

		ErrorBean.createErrorBean(throwable, request);
		response.sendRedirect(getErrorPageUrl());
	}

	private String getErrorPageUrl() {
		try {
			String actionClass = "com.popkyss." + WebAppSettings.getAppFolderName() + ".stripes.action."
					+ WebAppSettings.getBeanPrefix() + WebAppSettings.getErrorPageNumber();
			Class<?> clazz = Class.forName(actionClass);
			UrlBinding ub = clazz.<UrlBinding>getAnnotation(UrlBinding.class);
			return ub.value().substring(1);
		} catch (Exception e) {

			return String.valueOf(WebAppSettings.getAppPrefix()) + WebAppSettings.getErrorPageNumber() + ".action";
		}
	}

	public void init(Configuration arg0) throws Exception {
	}
}