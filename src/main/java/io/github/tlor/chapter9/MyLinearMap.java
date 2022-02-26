package io.github.tlor.chapter9;

import java.util.*;

public class MyLinearMap<K, V> implements Map<K, V> {

    private List<Entry> entries = new ArrayList<>();

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
    public boolean containsKey(Object key) {
        return findEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for(Map.Entry<K, V> entry : entries) {
            if(equals(value, entry.getValue())) {
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
     * 지정된 키가 배핑되는 값을 리턴하거나 맵에 키에 대한 매핑이 없는 경우 null 을 리턴합니다.
     * @param key               관련된 값을 돌려주는 키
     * @return                  지정된 키가 매핑 된 값 또는 맵에 키의 매핑이 없는 경우 null
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
        Set<K> set = new HashSet<>();
        for(Entry entry : entries) {
            set.add(entry.getKey());
        }
        return set;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 이 맵에서 키에 대한 매핑을 제거합니다.
     * @param key               맵에서 매핑을 제거할 키
     * @return                  이전의 값과 관련된 키 또는 매핑이 없는 경우 null
     */
    @Override
    public V remove(Object key) {
        Entry entry = findEntry(key);
        if(entry == null) {
            return null;
        }
        entries.remove(entry);
        return entry.getValue();
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<>();
        for(Entry entry : entries) {
            set.add(entry.getValue());
        }
        return set;
    }

    /**
     * 지정된 값을 맵의 지정된 키와 연관시킵니다.
     * 키가 존재하는 경우 이전값이 지정된 값으로 대치됩니다.
     * @param key               지정된 값을 관련 지을 수 있는 키
     * @param value             지정된 키와 관련되는 값
     * @return                  이전 값과 관련 키 또는 null(매핑이 없는 경우)
     */
    @Override
    public V put(K key, V value) {
        Entry entry = findEntry(key);
        if(entry != null) {
            V old = entry.getValue();
            entry.value = value;
            return old;
        } else {
            entries.add(new Entry(key, value));
            return null;
        }
    }


    /**
     * 타겟 키를 포함하는 entry 를 반환합니다. 찾지 못하는 경우 null 을 반환합니다.
     * @param target            타켓키
     */
    private Entry findEntry(Object target) {
        for(Entry entry : entries) {
            if(equals(target, entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    /**
     * 두 개의 키 혹은 값을 비교하고 null 의 경우 올바르게 처리합니다.
     */
    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }
}
