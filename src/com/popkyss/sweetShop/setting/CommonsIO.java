package com.popkyss.sweetShop.setting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class CommonsIO {
	private static Logger log = Logger.getLogger(CommonsIO.class);

	public static Properties getProperties(String path) {
		Properties prop = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(path));
			prop.load(in);

			return prop;
		} catch (FileNotFoundException e) {
			log.error("Nepodarilo se nacist soubor", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("Nepodarilo se nacist property", e);
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("Nepodarilo se uzavrit inputStram", e);
				}
			}
		}
	}

	public static Properties makeProperties(URL url) {
		Properties props = new Properties();
		if (url == null) {
			return props;
		}

		InputStream input = null;
		try {
			input = url.openStream();
			props.load(input);

			log.trace("Loaded properties: " + props);
		} catch (IOException ioe) {
			log.error("IO ERROR reading properties", ioe);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("IO ERROR (close stream) while reading properties", e);
				}
			}
		}
		return props;
	}

	public static byte[] ulozObrazek(BufferedImage obrazek, String format) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(obrazek, format, baos);
		return baos.toByteArray();
	}

	public static BufferedImage nactiImage(byte[] imageData) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(imageData);
			BufferedImage image = ImageIO.read(in);
			return image;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] nactiImage(InputStream image, int sirka, int vyska) throws IOException {
		BufferedImage desImg;
		ByteArrayOutputStream baos = null;

		BufferedImage img = ImageIO.read(image);

		int w = Math.min(img.getWidth(), sirka);
		int h = Math.min(img.getHeight(), vyska);

		double pomerImg = img.getWidth() / img.getHeight();
		double pomerObl = sirka / vyska;
		if (pomerImg > pomerObl) {
			int v = (int) (w * 1.0D / pomerImg);
			desImg = new BufferedImage(w, v, 1);
		} else {
			int s = (int) (h * pomerImg);
			desImg = new BufferedImage(s, h, 1);
		}
		Graphics2D g = desImg.createGraphics();
		g.drawImage(img, 0, 0, desImg.getWidth(), desImg.getHeight(), null);

		baos = new ByteArrayOutputStream();
		ImageIO.write(desImg, "jpg", baos);

		return baos.toByteArray();
	}

	public static void writeTextToFile(String text, String path) {
		File file = new File(path);
		writeTextToFile(text, file);
	}

	public static void writeTextToFile(String text, File file) {
		Writer out = null;
		try {
			boolean mkdirs = file.getParentFile().mkdirs();
			if (!mkdirs) {
				throw new IOException("nelze vytvorit slozky na ceste " + file.getAbsolutePath());
			}
			FileOutputStream fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos, "utf-8");
			out.write(text);
			out.flush();
		} catch (IOException e) {
			log.error("Nepodarilo se zapsat do souboru", e);
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("Chyba pri zavirani souboru", e);
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static String readAllTextFromStream(InputStream stream) {
		StringBuilder contents = new StringBuilder();

		try {
			InputStreamReader isr = new InputStreamReader(stream, "utf-8");
			BufferedReader input = new BufferedReader(isr);

			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(StringUtils.LINE_SEPARATOR);
				}
				contents.setLength(contents.lastIndexOf(StringUtils.LINE_SEPARATOR));
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			log.error("chyba pri cteni vstupniho proudu", ex);
			throw new RuntimeException(ex);
		}
		return contents.toString();
	}

	public static String readAllTextFromFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			String text = readAllTextFromStream(fis);
			return text;
		} catch (IOException ex) {
			log.error("chyba pri cteni souboru", ex);
			throw new RuntimeException(ex);
		}
	}

	public static String readAllTextFromURL(URL url) {
		try {
			return readAllTextFromStream(url.openStream());
		} catch (IOException ex) {
			log.error("chyba pri cteni url", ex);
			throw new RuntimeException(ex);
		}
	}
}