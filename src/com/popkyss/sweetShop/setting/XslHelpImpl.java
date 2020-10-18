package com.popkyss.sweetShop.setting;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml2.transform.Transformer;
import javax.xml2.transform.TransformerException;
import javax.xml2.transform.TransformerFactory;
import javax.xml2.transform.stream.StreamResult;
import javax.xml2.transform.stream.StreamSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XslHelpImpl implements IHelp {
	private static final String HELP_MENU_XML = "help_menu.xml";

	public String getHtmlContent(String idPage, Locale locale, String helpPath, Map<String, String> properties) {
		String fName;
		if (isHelpMenuCreated(idPage, helpPath, locale)) {
			fName = "/" + helpPath + "/" + idPage.toLowerCase() + "_" + locale.getLanguage() + ".xml";
		} else {
			fName = "/" + helpPath + "/404_" + locale.getLanguage() + ".xml";
		}

		String fxsl = "/" + helpPath + "/" + WebAppSettings.getAppPrefix().toLowerCase() + "001.xsl";
		if (!MessageUtils.isFileResourceExists(fxsl)) {
			throw new RuntimeException("soubor se sablonou: " + fxsl + " nenalezen");
		}
		StringWriter sW = new StringWriter(10000);
		try {
			URL url = MessageUtils.getFileResourceURL(fxsl);
			String template = StringUtils.replaceKeysInString(CommonsIO.readAllTextFromURL(url), properties);

			Transformer trans = TransformerFactory.newInstance()
					.newTransformer(new StreamSource(new StringReader(template)));
			trans.transform(new StreamSource(MessageUtils.getFileResourceAsStream(fName)), new StreamResult(sW));
		} catch (TransformerException tE) {
			throw new RuntimeException(tE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return sW.toString();
	}

	public List<HelpMenu> getHelpMenu(String helpPath) {
		XStream xstream = new XStream((HierarchicalStreamDriver) new DomDriver());
		xstream.alias("helpMenu", HelpMenu.class);
		try {
			URL url = MessageUtils.getFileResourceURL("/" + helpPath + "/" + HELP_MENU_XML);
			HelpMenu hm = (HelpMenu) xstream.fromXML(url);
			return hm.getSubMenus();
		} catch (IOException e) {
			throw new RuntimeException("Nepodarilo nacist strukturu menu ze souboru /" + helpPath + "/" + HELP_MENU_XML,
					e);
		}
	}

	public boolean isHelpMenuCreated(String menuId, String helpPath, Locale locale) {
		String resPath = "/" + helpPath + "/" + menuId.toLowerCase() + "_" + locale.getLanguage() + ".xml";
		return MessageUtils.isFileResourceExists(resPath);
	}
}