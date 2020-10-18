package com.popkyss.sweetShop.setting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public interface IExportyXls {
	ByteArrayOutputStream exportSeznamXls(List<? extends Object> paramList, List<FormatSloupce> paramList1,
			Properties paramProperties, Locale paramLocale, List<KriteriumSestavy> paramList2)
			throws IOException, RuntimeException;

	ByteArrayOutputStream exportSeznamXls(List<? extends Object> paramList, List<FormatSloupce> paramList1,
			Properties paramProperties, Locale paramLocale, List<KriteriumSestavy> paramList2, int paramInt)
			throws IOException, RuntimeException;
}