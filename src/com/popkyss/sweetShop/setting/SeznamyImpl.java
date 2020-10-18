package com.popkyss.sweetShop.setting;

import java.text.Collator;
import java.util.List;

@SuppressWarnings("deprecation")
public class SeznamyImpl implements ISeznamy {
	public <T extends ARadekSeznamu> int findPageWithColumnProperty(String property, String value,
			StrankovanySeznam<T> ss) {
		String getter = ReflectionUtils.createGetterMethodName(property, "get");
		value = StringUtils.stringToChar(value, 10);
		Collator col = Collator.getInstance(LocaleAction.getLocale());
		col.setStrength(0);
		int stranka = 1;
		int pocetRadkuNS = ss.getPocetRadkuStranky();
		int poradi = 0;
		List<T> list = ss.getRadkySeznamu();

		for (ARadekSeznamu aRadekSeznamu : list) {
			aRadekSeznamu.setFind((byte) 0);
		}
		if (!list.isEmpty()) {
			Class<?> clazz = ((ARadekSeznamu) ss.getRadkySeznamu().get(0)).getClass();
			String temp = (String) ReflectionUtils.invokeMethod(clazz, getter, list.get(list.size() - 1),
					new Object[0]);

			if (col.compare(temp, value) > 0) {
				for (ARadekSeznamu aRadekSeznamu : list) {
					temp = (String) ReflectionUtils.invokeMethod(clazz, getter, aRadekSeznamu, new Object[0]);
					if (col.compare(temp, value) == 0) {
						poradi = aRadekSeznamu.getPoradi();
						stranka = (poradi - 1) / pocetRadkuNS + 1;
						aRadekSeznamu.setFind((byte) 2);
						break;
					}
					if (col.compare(temp, value) > 0) {
						poradi = aRadekSeznamu.getPoradi();
						stranka = (poradi - 1) / pocetRadkuNS + 1;
						aRadekSeznamu.setFind((byte) 1);

						break;
					}
				}
			} else {
				poradi = ((ARadekSeznamu) list.get(list.size() - 1)).getPoradi();
				stranka = (poradi - 1) / pocetRadkuNS + 2;
				((ARadekSeznamu) list.get(list.size() - 1)).setFind((byte) 1);
			}
		}
		return stranka;
	}
}