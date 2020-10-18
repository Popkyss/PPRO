package com.popkyss.sweetShop.setting;


public interface ISeznamy {
	<T extends ARadekSeznamu> int findPageWithColumnProperty(String paramString1,
			String paramString2, StrankovanySeznam<T> paramStrankovanySeznam);
}
