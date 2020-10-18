package com.popkyss.sweetShop.setting;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

public class FormatHlavicky extends CellStyle {
	private static Logger log = Logger.getLogger(FormatHlavicky.class);
	private static Image UP_ARROW = null;
	private static Image DOWN_ARROW = null;

	public static Image getUP_ARROW() {
		if (UP_ARROW == null) {
			try {
				UP_ARROW = Image.getInstance(FormatHlavicky.class.getResource("img/sys_table-sort-up1.gif"));
			} catch (BadElementException e) {
				LoggUtils.logE(e.getMessage(), log);
			} catch (MalformedURLException e) {
				LoggUtils.logE(e.getMessage(), log);
			} catch (IOException e) {
				LoggUtils.logE(e.getMessage(), log);
			}
		}
		return UP_ARROW;
	}

	public static Image getDOWN_ARROW() {
		if (DOWN_ARROW == null) {
			try {
				DOWN_ARROW = Image.getInstance(FormatHlavicky.class.getResource("img/sys_table-sort-down1.gif"));
			} catch (BadElementException e) {
				LoggUtils.logE(e.getMessage(), log);
			} catch (MalformedURLException e) {
				LoggUtils.logE(e.getMessage(), log);
			} catch (IOException e) {
				LoggUtils.logE(e.getMessage(), log);
			}
		}
		return DOWN_ARROW;
	}
}