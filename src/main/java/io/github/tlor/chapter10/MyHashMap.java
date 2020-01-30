package io.github.tlor.chapter10;

import java.util.*;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

	// 재해시하기 전 하위 맵당 평균 엔트리 개수
	protected static final double FACTOR = 1.0;

	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);

		// 하위 맵당 엔트리의 개수가 임계치를 넘지 않는지 확인합니다.
		if (size() > maps.size() * FACTOR) {
			rehash();
		}
		return oldValue;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	protected void rehash() {

		List<MyLinearMap<K, V>> oldMaps = maps;

		int k = maps.size() * 2;
		makeMaps(k);

		for(MyLinearMap<K, V> map : oldMaps) {
			for(Map.Entry<K, V> entry : map.getEntries()) {
				put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}

	/**
	 * Implementation of a Map using a List of entries, so most
	 * operations are linear time.
	 *
	 * @author downey
	 * @param <K>
	 * @param <V>
	 *
	 */
	public static class MyLinearMap<K, V> implements Map<K, V> {

		private List<Entry> entries = new ArrayList<Entry>();

		public class Entry implements Map.Entry<K, V> {
			private K key;
			private V value;

			public Entry(K key, V value) {
				this.key = key;
				this.value = value;
			}

			@Override
			public K getKey() {
				return key;
			}
			@Override
			public V getValue() {
				return value;
			}
			@Override
			public V setValue(V newValue) {
				value = newValue;
				return value;
			}
		}

		@Override
		public void clear() {
			entries.clear();
		}

		@Override
		public boolean containsKey(Object target) {
			return findEntry(target) != null;
		}

		/**
		 * Returns the entry that contains the target key, or null if there is none.
		 *
		 * @param target
		 */
		private Entry findEntry(Object target) {
			for(Entry entry: entries) {
				if(equals(target, entry.getKey())) {
					return entry;
				}
			}
			return null;
		}

		/**
		 * Compares two keys or two values, handling null correctly.
		 *
		 * @param target
		 * @param obj
		 * @return
		 */
		private boolean equals(Object target, Object obj) {
			if (target == null) {
				return obj == null;
			}
			return target.equals(obj);
		}

		@Override
		public boolean containsValue(Object target) {
			for (Map.Entry<K, V> entry: entries) {
				if (equals(target, entry.getValue())) {
					return true;
				}
			}
			return false;
		}

		@Override
		public Set<Map.Entry<K, V>> entrySet() {
			throw new UnsupportedOperationException();
		}

		/**
		 * 키에 매핑된 값을 리턴합니다.
		 * 또는 매핑되어진 키가 없으면 null 을 리턴합니다.
		 * @param key		관련된 값을 돌려주는 키
		 * @return			지정된 키가 매핑 된 값 또는 키가 없는 경우 null
		 */
		@Override
		public V get(Object key) {
			Entry entry = findEntry(key);
			if(entry == null) {
				return null;
			}
			return entry.getValue();
		}

		@Override
		public boolean isEmpty() {
			return entries.isEmpty();
		}

		@Override
		public Set<K> keySet() {
			Set<K> set = new HashSet<K>();
			for (Entry entry: entries) {
				set.add(entry.getKey());
			}
			return set;
		}

		/**
		 * 지정된 값을 맵의 지정된 키와 연관 시킵니다.
		 * 맵에 이전 키에 대한 매핑이 포함된 경우 이전 값이 지정된 값으로 대체 됩니다.
		 * @param key				지정된 값을 관련 지을 수 있는 키
		 * @param value				지정된 키와 관련되는 값
		 * @return					이전의 값과 관련 키 매핑이 없는 경우 null
		 */
		@Override
		public V put(K key, V value) {
			Entry entry = findEntry(key);
			if(entry == null) {
				entries.add(new Entry(key, value));
				return null;
			} else {
				V old = entry.getValue();
				entry.setValue(value);
				return old;
			}
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> map) {
			for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}

		/**
		 * 키로 매핑된 Entry 를 제거합니다.
		 * @param key 		맵에서 매핑을 제거할 키
		 * @return			이전 값과 관련 키 매핑이 없는 경우 Null
		 */
		@Override
		public V remove(Object key) {

			Entry entry = findEntry(key);
			if(entry == null) {
				return null;
			} else {
				V old = entry.getValue();
				entries.remove(entry);
				return old;
			}
		}

		@Override
		public int size() {
			return entries.size();
		}

		@Override
		public Collection<V> values() {
			Set<V> set = new HashSet<V>();
			for (Entry entry: entries) {
				set.add(entry.getValue());
			}
			return set;
		}

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			Map<String, Integer> map = new MyLinearMap<String, Integer>();
			map.put("Word1", 1);
			map.put("Word2", 2);
			Integer value = map.get("Word1");
			System.out.println(value);

			for (String key: map.keySet()) {
				System.out.println(key + ", " + map.get(key));
			}
		}

		/**
		 * Returns a reference to `entries`.
		 *
		 * This is not part of the Map interface; it is here to provide the functionality
		 * of `entrySet` in a way that is substantially simpler than the "right" way.
		 *
		 * @return
		 */
		protected Collection<? extends Map.Entry<K, V>> getEntries() {
			return entries;
		}
	}
}
