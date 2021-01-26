package com.popkyss.sweetShop.setting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DatexCollections {
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> clone(Collection<T> source) {
		Collection<T> clone = getNewCollectionInstance(source);
		for (T in : source) {
			T o = (T) ReflectionUtils.invokeMethod(in.getClass(), "clone", in, new Object[0]);
			clone.add(o);
		}
		return clone;
	}

	public static <T> Collection<T> getNewCollectionInstance(Collection<T> c) {
		if (c instanceof ArrayList) {
			return new ArrayList<T>();
		}
		if (c instanceof LinkedList) {
			return new LinkedList<T>();
		}
		if (c instanceof List) {
			return new ArrayList<T>();
		}
		if (c instanceof LinkedHashSet) {
			return new LinkedHashSet<T>();
		}
		if (c instanceof HashSet) {
			return new HashSet<T>();
		}
		if (c instanceof TreeSet) {
			return new TreeSet<T>();
		}
		if (c instanceof Set) {
			return new HashSet<T>();
		}
		return null;
	}

	public static Object getSafeFromLut(Map<?, ?> lut, Object... keys) {
		if (lut == null) {
			return null;
		}
		Map<?, ?> meziUroven = lut;
		for (int i = 0; i < keys.length - 1; i++) {
			meziUroven = (Map<?, ?>) meziUroven.get(keys[i]);
			if (meziUroven == null) {
				return null;
			}
		}

		return meziUroven.get(keys[keys.length - 1]);
	}

	public static String collectionToString(Collection<?> collection, String defaultValue, String oddelovac) {
		if (collection == null || collection.isEmpty()) {
			return defaultValue;
		}
		StringBuilder sb = new StringBuilder();
		for (Iterator<?> it = collection.iterator(); it.hasNext();) {
			sb.append(String.valueOf(it.next()));
			if (it.hasNext()) {
				sb.append(oddelovac);
			}
		}

		return sb.toString();
	}

	public static String collectionToString(Collection<?> collection, String defaultValue) {
		return collectionToString(collection, defaultValue, ", ");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object putSafeIntoLut(Map<Object, Map<Object, Object>> map, Object value, Object... keys) {
		Map<Object, Object> map1 = null;
		if (map == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < keys.length - 1; i++) {
			Map<Object, Object> tmp = (Map) map.get(keys[i]);
			if (tmp == null) {
				map.put(keys[i], tmp = new HashMap<Object, Object>());
			}
			map1 = tmp;
		}
		Object last = map1.put(keys[keys.length - 1], value);
		return last;
	}

	public static SerializableComparator reverseOrder(SerializableComparator comp) {
		return new ReverseSerializableComparator(comp);
	}

	public static boolean isPrazdnePole(Object[] pole) {
		return !(pole != null && pole.length != 0 && (pole.length != 1 || pole[0] != null));
	}

	public static boolean isPrazdnaKolekce(Collection<?> collection) {
		return !(collection != null && !collection.isEmpty());
	}

	public static <K, V> List<V> convertMapValuesToList(Map<K, V> mapa) {
		if (mapa == null) {
			return null;
		}

		Set<V> collection = new LinkedHashSet<V>();
		for (V val : mapa.values()) {
			collection.add(val);
		}

		return new ArrayList<V>(collection);
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> convertListToMap(List<V> list, String attribute) throws RuntimeException {
		if (list == null) {
			return null;
		}

		String[] attributes = attribute.split("\\.");
		Map<K, V> mapa = new HashMap<K, V>();
		for (V value : list) {
			Object key = value;
			for (int i = 0; i < attributes.length; i++) {

				if (key == null) {
					break;
				}

				String method = ReflectionUtils.getGetterMethod(key, attributes[i]);
				key = ReflectionUtils.invokeMethod(key.getClass(), method, key, new Object[0]);
			}

			mapa.put((K) key, value);
		}

		return mapa;
	}

	
}