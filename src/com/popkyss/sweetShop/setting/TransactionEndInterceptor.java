package com.popkyss.sweetShop.setting;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts({ LifecycleStage.ResolutionExecution })
public class TransactionEndInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(TransactionEndInterceptor.class);

	public Resolution intercept(ExecutionContext context) throws Exception {
		HttpServletRequest request = context.getActionBeanContext().getRequest();

		TransactionSwitchSet ets = (TransactionSwitchSet) request.getAttribute("transaction_status");
		if (context.isResolutionFromHandler()) {

			if (ets != null && ets != TransactionSwitchSet.TRANSACTION_ROLLBACK) {
				request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_COMMIT);
			}

			HibernateSessionContext.tryFlush();
		} else {

			if (log.isTraceEnabled()) {
				log.trace("Nestandardni ukonceni pozadavku na bean: " + context.getActionBean().getClass().getName()
						+ ", metoda: " + context.getHandler().getName());
			}
			request.setAttribute("transaction_status", TransactionSwitchSet.TRANSACTION_ROLLBACK);
		}

		return context.proceed();
	}
}