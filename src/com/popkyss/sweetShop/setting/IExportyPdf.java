package com.popkyss.sweetShop.setting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public interface IExportyPdf {
	ByteArrayOutputStream exportSeznamPdf(List<?> paramList, List<FormatSloupce> paramList1, Properties paramProperties,
			Locale paramLocale, boolean paramBoolean, List<KriteriumSestavy> paramList2)
			throws IOException, RuntimeException;
}