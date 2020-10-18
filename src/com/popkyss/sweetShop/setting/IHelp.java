package com.popkyss.sweetShop.setting;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface IHelp {
	String getHtmlContent(String paramString1, Locale paramLocale, String paramString2, Map<String, String> paramMap);

	List<HelpMenu> getHelpMenu(String paramString);

	boolean isHelpMenuCreated(String paramString1, String paramString2, Locale paramLocale);
}