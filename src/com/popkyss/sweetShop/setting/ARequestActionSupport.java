package com.popkyss.sweetShop.setting;

import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;

import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

@HttpCache(allow = false)
public abstract class ARequestActionSupport<Bean extends AbstractBean> extends AbstractAction
		implements ValidationErrorHandler {
	public static final String JSP_NOT_FOUND = "/jsp/base/notFound.jsp";
	protected final Logger log = Logger.getLogger(getClass());
	

	@Validate(encrypted = true)
	private boolean sestavaNaVysku = true;

	@Validate(encrypted = true)
	private boolean stahovatSoubor = false;

	protected String getActionUrl() {
		try {
			Class<?> beanClass = Class.forName(getClassName());
			UrlBinding ub = beanClass.<UrlBinding>getAnnotation(UrlBinding.class);

			return ub.value().substring(1);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getBeanName() {
		return String.valueOf(AbstractBean.getBEAN_PREFIX()) + getActionNumber() + AbstractBean.getBEAN_SUFIX();
	}

	protected void initDataBean() {
		createActionBeanInstance();
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	protected void createActionBeanInstance() {
		try {
			Object o = ((Class) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[getDataBeanGenerikumPoradi()]).newInstance();
			setActionBean((Bean) o);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected int getDataBeanGenerikumPoradi() {
		return 0;
	}

	public abstract Bean getActionBean();

	public abstract void setActionBean(Bean paramBean);

	public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
		request().setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);
		return null;
	}

	protected void msgE(String key, Object... parameters) {
		msgE(key, true, parameters);
	}

	protected void msgE(String key, boolean rollback, Object... parameters) {
		if (rollback) {
			request().setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);
		}
		msg(key, parameters, "msgE");
	}

	protected void msgW(String key, Object... parameters) {
		msg(key, parameters, "msgW");
	}

	protected void msgI(String key, Object... parameters) {
		msg(key, parameters, "msgI");
	}

	protected final void msg(String key, Object[] parameters, String spanClass) {
		Object[] params = (parameters == null) ? new Object[0] : parameters;

		Object[] msgParams = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			msgParams[i] = "<b>" + params[i] + "</b>";
		}

		String template = PropertyReader.bundleExists(String.valueOf(getAPPLICATION_PREFIX()) + getActionNumber(), key)
				? PropertyReader.bundle(String.valueOf(getAPPLICATION_PREFIX()) + getActionNumber(), key)
				: PropertyReader.bundle(key);

		getContext().getMessages().add(new SimpleMessage(
				"<p class=\"" + spanClass + "\">" + StringUtils.htmlFilterTrim(template, true) + "</p>", msgParams));
	}


	public void setSestavaNaVysku(boolean sestavaNaVysku) {
		this.sestavaNaVysku = sestavaNaVysku;
	}

	public boolean isSestavaNaVysku() {
		return this.sestavaNaVysku;
	}

	public void setStahovatSoubor(boolean stahovatSoubor) {
		this.stahovatSoubor = stahovatSoubor;
	}

	public boolean isStahovatSoubor() {
		return this.stahovatSoubor;
	}
}