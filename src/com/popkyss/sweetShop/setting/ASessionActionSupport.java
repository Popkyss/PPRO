package com.popkyss.sweetShop.setting;

import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.controller.LifecycleStage;

@HttpCache(allow = false)
public abstract class ASessionActionSupport<Bean extends AbstractBean> extends ARequestActionSupport<Bean> {
	@SuppressWarnings("unchecked")
	protected void initDataBean() {
		setActionBean((Bean) session().getAttribute(getBeanName()));
		if (getActionBean() == null) {
			createActionBeanInstance();
			session().setAttribute(getBeanName(), getActionBean());
		}
	}

	@After(stages = { LifecycleStage.EventHandling })
	public void saveStateInterceptor() {
		session().setAttribute(getBeanName(), getActionBean());
	}
}