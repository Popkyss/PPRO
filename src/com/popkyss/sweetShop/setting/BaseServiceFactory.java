package com.popkyss.sweetShop.setting;


public final class BaseServiceFactory extends AbstractFactory {
	private static final BaseServiceFactory INSTANCE = new BaseServiceFactory();

	public static IDateTime getDateTime() {
		return (IDateTime) INSTANCE.createAndStoreInstance(IDateTime.class);
	}
	public static IExportyPdf getPdfExporty() {
		return (IExportyPdf) INSTANCE.createAndStoreInstance(IExportyPdf.class);
	}

	public static IExportyXls getXlsExporty() {
		return (IExportyXls) INSTANCE.createAndStoreInstance(IExportyXls.class);
	}

	public static IHelp getHelp() {
		return (IHelp) INSTANCE.createAndStoreInstance(IHelp.class);
	}

	public static ISeznamy getISeznamyService() {
		return (ISeznamy) INSTANCE.createAndStoreInstance(ISeznamy.class);
	}

	public static IHlavickaManipulator getHlavickaManipulator() {
		return (IHlavickaManipulator) INSTANCE.createAndStoreInstance(IHlavickaManipulator.class);
	}

	
}