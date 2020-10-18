package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;

public class LoggUtils {
	public static void logE(String message, Logger log) throws RuntimeException {
		log.error(message);
		throw new RuntimeException(message);
	}
}