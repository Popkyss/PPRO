package com.popkyss.sweetShop.setting;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;

public final class ReflectionUtils {
	private static final Logger log = Logger.getLogger(ReflectionUtils.class);

	public static boolean isMethodExist(String method, Class<?> clazz) {
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (method.equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	public static String createGetterMethodName(String attribute, String prefix) {
		return String.valueOf(prefix) + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
	}

	public static Object invokeMethod(Class<?> clazz, String method, Object o, Object... args) {
		try {
			if (isMethodExist(method, clazz)) {
				Method m = clazz.getMethod(method, new Class[0]);
				m.setAccessible(true);
				return m.invoke(o, args);
			}
			log.error("metoda: " + method + " neexistuje");
			throw new RuntimeException("metoda: " + method + " neexistuje");
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static String getGetterMethod(Object o, String attribute) {
		if (o != null && attribute != null) {

			String methodName, methodTemp = createGetterMethodName(attribute, "get");
			if (isMethodExist(methodTemp, o.getClass())) {
				methodName = methodTemp;
			} else {

				methodTemp = createGetterMethodName(attribute, "is");
				if (isMethodExist(methodTemp, o.getClass())) {
					methodName = methodTemp;
				} else {

					RuntimeException re = new RuntimeException("Nepodarilo se nalezt getter k  atributu " + attribute
							+ " v objektu " + o + "\n Zkontrolujte, zdali existuje prislusna get(is) metoda ");
					log.error("Nepodarilo se nalezt getter k a atributu " + attribute, re);
					throw re;
				}
			}
			return methodName;
		}
		throw new NullPointerException();
	}

	@SuppressWarnings("rawtypes")
	public static Method getMethod(Object o, String nazevMetody, Class... parametry) throws RuntimeException {
		try {
			return o.getClass().getMethod(nazevMetody, parametry);
		} catch (NoSuchMethodException e) {
			log.error("Nepodarilo se najit metodu s nazvem " + nazevMetody + " prijimajici parametry: "
					+ Arrays.toString((Object[]) parametry), e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getMethod(String methodName, Class<?> clazz, Object... args) {
		for (Class<?> cl = clazz; cl != null; cl = cl.getSuperclass()) {
			Method[] methods = cl.getDeclaredMethods();
			byte b;
			int i;
			Method[] arrayOfMethod1;
			for (i = (arrayOfMethod1 = methods).length, b = 0; b < i;) {
				Method met = arrayOfMethod1[b];
				Class[] params = met.getParameterTypes();
				if (met.getName().equals(methodName) && args.length == params.length)

				{
					int j = 0;
					while (true) {
						if (j >= args.length) {

							return met;
						}
						if (args[j] != null)
							if (!params[j].isAssignableFrom(args[j].getClass()))
								break;
						j++;
					}
				}
				b++;
			}

		}
		return null;
	}

	
	@SuppressWarnings("deprecation")
	public static Class<?> getAttributeClass(Class<?> clazz, String attribute) throws RuntimeException {
		try {
			Object o = clazz.newInstance();
			return getMethod(o, getGetterMethod(o, attribute), new Class[0]).getReturnType();
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			log.error("Nepodarilo se zjistit tridu atributu " + attribute, e);
			throw new RuntimeException(e);
		}
	}


	@SuppressWarnings("deprecation")
	public static Object getClassInstance(String className) {
		Class<?> classObj = null;
		try {
			classObj = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.error("Nepodarilo se nalezt tridu " + className, e);
			throw new RuntimeException(e);
		}

		try {
			return classObj.newInstance();
		} catch (InstantiationException e) {
			log.error("Nepodarilo inicializovat tridu " + className, e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("Neverejny konstruktor tridy " + className + " - nelze vytvorit instanci", e);
			throw new RuntimeException(e);
		}
	}

	public static Object getFieldValue(Object obj, Field field) {
		Object hodnota;
		if (obj == null) {
			return null;
		}

		try {
			hodnota = field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			throw new RuntimeException();
		}
		return hodnota;
	}

	public static void makeFieldAccesible(Field field) {
		if (field.getModifiers() != 1)
			field.setAccessible(true);
	}
}