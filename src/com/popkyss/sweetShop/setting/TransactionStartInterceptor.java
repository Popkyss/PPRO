package com.popkyss.sweetShop.setting;


import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import org.apache.log4j.Logger;

@Intercepts({ LifecycleStage.ActionBeanResolution })
public class TransactionStartInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(TransactionStartInterceptor.class);

	public Resolution intercept(ExecutionContext context) throws Exception {
		if (log.isTraceEnabled()) {
			log.trace("invoke transaction start interceptor");
		}
		return context.proceed();
	}
}