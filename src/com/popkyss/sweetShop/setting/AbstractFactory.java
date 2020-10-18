package com.popkyss.sweetShop.setting;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class AbstractFactory {
	private static final String FACTORY_DEFINITION_FILE_KEY = "factoryDefinitionPath";
	private static final String FACTORY_DEFINITION_FILE = "resources/factoryDefinition.properties";
	private static Logger log = Logger.getLogger(AbstractFactory.class);

	private static volatile Map<Class<?>, Map<Class<?>, Object>> instances = new HashMap<Class<?>, Map<Class<?>, Object>>();

	@SuppressWarnings("unchecked")
	protected static <T> T createInstance(Class<?> interfaceClass, String filePath) {
		try {
			String implementationClass = getClassFromPropertyFile(interfaceClass.getName(), filePath);
			if (implementationClass != null) {
				Object o = ReflectionUtils.getClassInstance(implementationClass);
				return (T) o;
			}
			throw new RuntimeException(
					"V property souboru nebyla nalezena pozadovana instance tridy " + interfaceClass.toString());
		} catch (Exception e) {
			throw new RuntimeException("Neporadilo se vytvorit instanci tridy " + interfaceClass.getName(), e);
		}
	}

	protected <T> T createInstance(Class<?> interfaceClass) {
		return createInstance(interfaceClass, getFilePath(getClass().getName()));
	}

	protected synchronized <T> T createAndStoreInstance(Class<?> interfaceClass) {
		return createAndStoreInstance(getClass(), interfaceClass, getFilePath(getClass().getName()));
	}

	@SuppressWarnings("unchecked")
	protected static synchronized <T> T createAndStoreInstance(Class<?> factoryClass, Class<?> interfaceClass,
			String filePath) {
		Map<Class<?>, Object> factoryInstances = instances.get(factoryClass);
		if (factoryInstances == null) {
			factoryInstances = new HashMap<Class<?>, Object>();
			instances.put(factoryClass, factoryInstances);
		}

		T instance = (T) factoryInstances.get(interfaceClass);
		if (instance == null) {
			instance = createInstance(interfaceClass, filePath);
			factoryInstances.put(interfaceClass, instance);
		}
		return instance;
	}

	private static String getFilePath(String factoryClassName) {
		try {

			String factoryFilePathFromParams = System.getProperty(factoryClassName);
			if (factoryFilePathFromParams != null) {
				return factoryFilePathFromParams;
			}

			String factoryFilePath = getFactoryDefinition().getProperty(factoryClassName);
			if (factoryFilePath != null) {
				return factoryFilePath;
			}

			String defaultFilePath = getFactoryFromDefault(factoryClassName);
			if (defaultFilePath == null) {
				throw new RuntimeException("Nenalezena definice pro factory " + factoryClassName);
			}
			return defaultFilePath;
		} catch (Exception e) {
			throw new RuntimeException("Nepodarilo se nacist zakladni soubor pro definici factories", e);
		}
	}

	private static String getFactoryFromDefault(String factoryClassName) throws IOException {
		String path = String.valueOf(factoryClassName.replaceAll("\\.", "/")) + ".properties";
		if (!loadResources(path, AbstractFactory.class.getClassLoader()).isEmpty()) {
			return path;
		}
		return null;
	}

	@SuppressWarnings("resource")
	private static Properties getFactoryDefinition() throws IOException {
		Properties properties = new Properties();
		List<URL> defaultProperties = loadResources(FACTORY_DEFINITION_FILE,AbstractFactory.class.getClassLoader());
		URL localProperties = getLocalFactoryDefinitionFile();
		if (localProperties != null) {
			defaultProperties.add(localProperties);
		}
		InputStream input = null;
		try {
			for (URL url : defaultProperties) {
				input = url.openStream();
				properties.load(input);
				log.trace("Loaded properties: " + url);
				input.close();
			}
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

		return properties;
	}

	public static List<URL> loadResources(String name, ClassLoader classLoader) throws IOException {
		List<URL> list = new ArrayList<URL>();
		Enumeration<URL> systemResources = classLoader.getResources(name);
		while (systemResources.hasMoreElements()) {
			list.add(systemResources.nextElement());
		}
		return list;
	}

	private static String getClassFromPropertyFile(String interfaceClass, String filePath) throws IOException {
		URL fileUrl = AbstractFactory.class.getClassLoader().getResource(filePath);
		Properties properties = CommonsIO.makeProperties(fileUrl);
		if (properties.isEmpty()) {
			throw new IOException(
					"Soubor " + filePath + " nebyl nalezen nebo neni validni property soubor nebo je soubor prazdny");
		}
		String implementationClass = properties.getProperty(interfaceClass);
		return implementationClass;
	}

	private static URL getLocalFactoryDefinitionFile() throws IOException {
		String value = System.getProperty(FACTORY_DEFINITION_FILE_KEY);
		if (value != null) {
			URL fileUrl = AbstractFactory.class.getClassLoader().getResource(value);
			if (fileUrl != null) {
				return fileUrl;
			}
			throw new IOException("Soubor " + value + " nebyl nalezen");
		}

		return null;
	}
}