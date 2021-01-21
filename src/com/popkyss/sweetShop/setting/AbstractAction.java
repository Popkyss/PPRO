package com.popkyss.sweetShop.setting;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

public abstract class AbstractAction implements ActionBean {
	
	public static final String URL_EXPORT_PDF = "ukazFILE.jsp";
	
	public static final String URL_EXPORT_XLSX = "ukazFILE.jsp";
	
	public static final String URL_EXPORT_XLS = "ukazFILE.jsp";
	
	public static final String EXPORT_PDF = "file.AbstractAction";
	
	public static final String EXPORT_XLS = "file.AbstractAction";
	public static final String URL_EXPORT_FILE = "ukazFILE.jsp";
	public static final String EXPORT_FILE = "file.AbstractAction";
	public static final String EXPORT_FILE_TYPE = "file_type.AbstractAction";
	public static final String EXPORT_FILE_NAME = "file_name.AbstractAction";
	public static final String EXPORT_FILE_DOWNLOAD = "file_down.AbstractAction";
	private ActionBeanContext context;

	public static String getAPPLICATION_PREFIX() {
		return WebAppSettings.getAppPrefix();
	}

	public static String getURL_SUFIX() {
		return WebAppSettings.getUrlSuffix();
	}

	protected void initDataBean() {
	}

	public ActionBeanContext getContext() {
		return this.context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = context;
		initDataBean();
	}

	public Resolution findForward() {
		return (Resolution) new RedirectResolution(getClass());
	}

	public Resolution findForward(String actionUrl) {
		return (Resolution) new RedirectResolution(actionUrl);
	}

	public String getClassName() {
		return getClass().getName();
	}

	public String getActionNumber() {
		return getClass().getSimpleName().substring(3, 6);
	}

	protected HttpSession session() {
		return request().getSession(true);
	}

	protected HttpServletRequest request() {
		return getContext().getRequest();
	}

	protected HttpServletResponse response() {
		return getContext().getResponse();
	}

	protected Resolution exportToXlsx(ByteArrayOutputStream xlsData) {
		return exportToFile(xlsData, Konstanty.TypSouboru.XLSX,
				"exported_data" + Konstanty.TypSouboru.XLSX.getPripona());
	}

	protected Resolution exportToXls(ByteArrayOutputStream xlsData) {
		return exportToFile(xlsData, Konstanty.TypSouboru.XLS, "exported_data" + Konstanty.TypSouboru.XLS.getPripona());
	}

	protected Resolution exportToPdf(ByteArrayOutputStream pdfData) {
		return exportToFile(pdfData, Konstanty.TypSouboru.PDF, "exported_data" + Konstanty.TypSouboru.PDF.getPripona());
	}

	protected Resolution exportToFile(ByteArrayOutputStream fileData, Konstanty.TypSouboru typ, String nazevSouboru) {
		return exportToFile(fileData.toByteArray(), typ, false, nazevSouboru);
	}

	protected Resolution exportToFile(ByteArrayOutputStream fileData, Konstanty.TypSouboru typ, boolean stahnout,
			String nazevSouboru) {
		return exportToFile(fileData.toByteArray(), typ, stahnout, nazevSouboru);
	}

	protected Resolution exportToFile(byte[] fileData, Konstanty.TypSouboru typ, String nazevSouboru) {
		return exportToFile(fileData, typ, false, nazevSouboru);
	}

	protected Resolution exportToFile(byte[] fileData, Konstanty.TypSouboru typ, boolean stahnout,
			String nazevSouboru) {
		String nazev = (nazevSouboru == null) ? getContext().getEventName() : nazevSouboru;
		if (fileData == null) {
			throw new NullPointerException(
					"Nelze exportovat soubor " + nazev + " - atribut fileData neobsahuje zadna data (null)");
		}
		if (typ == null) {
			throw new NullPointerException("Nelze zjistit typ souboru " + nazev + " - atribut typ je (null)");
		}
		request().setAttribute("file.AbstractAction", fileData);
		request().setAttribute("file_type.AbstractAction", typ);
		request().setAttribute("file_name.AbstractAction", nazev);
		request().setAttribute("file_down.AbstractAction", Boolean.valueOf(stahnout));

		return (Resolution) new ForwardResolution("ukazFILE.jsp");
	}
}