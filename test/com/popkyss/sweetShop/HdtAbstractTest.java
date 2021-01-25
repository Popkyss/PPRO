package com.popkyss.sweetShop;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;

public class HdtAbstractTest {

	public static final String BASE_URL = "http://localhost:8080/Eshop/";
	public static final String BASE_ENCODING = "UTF-8";
	
	private static Logger LOG = Logger.getLogger(HdtAbstractTest.class);


	/**
	 * Metoda, ktera vytvori URL POST pozadavek na servlet a vlozi do nej predane parametry
	 * @param servlet nazev servletu 
	 * @param params parametry, ktere se vlozi do tela POST pozadavku
	 * @return vraci otevrenou connection pro zpracovani odpovedi
	 */
	protected HttpURLConnection getConnection(String servlet, String params) {
		HttpURLConnection httpCon = null;
		try {
			URL url = new URL(BASE_URL + servlet);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setInstanceFollowRedirects(false);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpCon.setRequestProperty("charset", BASE_ENCODING);
			httpCon.setRequestProperty("Content-Length", Integer.toString(params.length()));
			httpCon.setUseCaches(false);
			
			nasetujData(httpCon, params);
		} catch (MalformedURLException e) {
			LOG.error("URL je ve spatnem tvaru", e);
		} catch (ProtocolException e) {
			LOG.error("Doslo k chybe protokolu", e);
		} catch (IOException e) {
			LOG.error("Chyba pri IO operaci", e);
		}
		return httpCon;
	}
	
	private void nasetujData(HttpURLConnection httpCon, String params) {
		try {
			DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
			wr.write(params.getBytes(BASE_ENCODING));
		} catch (IOException e) {
			LOG.error("Chyba pri zapisovani dat do connection", e);
		}
	}


	/**
	 * Metoda, ktera ziska XML soubor z odpovedi servletu
	 * @param httpCon connection na niz probiha dana komunikace
	 * @return vraci String precteny z odpovedi
	 */
	protected String getXml(HttpURLConnection httpCon) {
		InputStream in;
		String xml = new String();
		try {
			in = httpCon.getInputStream();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);
			for (String line; (line = br.readLine()) != null; xml += line);
		} catch (IOException e) {
			LOG.error("Chyba pri cteni prichozich dat", e);
		}
		return xml;
	}
}
