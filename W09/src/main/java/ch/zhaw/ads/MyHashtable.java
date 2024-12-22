package ch.zhaw.ads;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyHashtable<K,V> implements Map<K,V> {
    private static final double LOAD_FACTOR = 0.8;

    private final K DELETED = (K) new Object();

    private int threshold;
    private K[] keys;
    private V[] values;

    private int hash(Object k) {
        return hash(k, keys.length);
    }

    private int hash(Object k, int size) {
        int h = Math.abs(k.hashCode());
        return h % size;
    }

    private void resize() {
        int newSize = keys.length * 2;
        K[] oldKeys = this.keys;
        V[] oldValues = this.values;

        this.keys = (K[]) new Object[newSize];
        this.values = (V[]) new Object[newSize];


        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                this.put(oldKeys[i], oldValues[i]);
            }
        }

        threshold = (int) (newSize * LOAD_FACTOR);
    }

    public MyHashtable(int size) {
        this.keys = (K[]) new Object[size];
        this.values = (V[]) new Object[size];
        this.threshold = (int) (size * LOAD_FACTOR);
    }

    //  Removes all mappings from this map (optional operation). 
    public void clear() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = null;
            values[i] = null;

        }
    }

    //  Associates the specified value with the specified key in this map (optional operation).
    public V put(K key, V value) {
        if (sizeWithDeleted() >= threshold) {
            resize();
        }

        int idx = hash(key);

        for (int offset = 0; offset < keys.length; offset++) {
            int newIdx = (idx + offset) % keys.length;

            if (keys[newIdx] == null || keys[newIdx].equals(key)) {
                keys[newIdx] = key;
                values[newIdx] = value;

                return value;
            }
        }

        throw new IllegalStateException("Unable to insert");
    }

    //  Returns the value to which this map maps the specified key.
    public V get(Object key) {
        int idx = hash(key);

        for (int offset = 0; offset < keys.length; offset++) {
            int newIdx = (idx + offset) % keys.length;
            K storedKey = keys[newIdx];

            if (storedKey == null) {
                return null;
            }

            if (storedKey.equals(key)) {
                return values[newIdx];
            }
        }

        return null;
    }

    //  Returns true if this map contains no key-value mappings. 
    public boolean isEmpty() {
        // to be done
        throw new UnsupportedOperationException();
    }

    //  Removes the mapping for this key from this map if present (optional operation). 
    public V remove(Object key) {
        int idx = hash(key);
        for (int offset = 0; offset < keys.length; offset++) {
            int newIdx = (idx + offset) % keys.length;

            if (keys[newIdx] == null) {
                return null;
            }

            if (keys[newIdx] == DELETED) {
                continue;
            }

            if (keys[newIdx].equals(key)) {
                V value = values[newIdx];
                values[newIdx] = null;
                keys[newIdx] = DELETED;

                return value;
            }
        }

        return null;
    }

    //  Returns the number of key-value mappings in this map. 
    public int size() {
        int size = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null && keys[i] != DELETED) {
                size++;
            }
        }

        return size;
    }

    private int sizeWithDeleted() {
        int size = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                size++;
            }
        }

        return size;
    }

    // =======================================================================
    //  Returns a set view of the keys contained in this map. 
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    //  Copies all of the mappings from the specified map to this map (optional operation). 
    public void putAll(Map t) {
        throw new UnsupportedOperationException();
    }

    //  Returns a collection view of the values contained in this map. 
    public Collection values() {
        throw new UnsupportedOperationException();
    }
    //  Returns true if this map contains a mapping for the specified key.
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }
    //  Returns true if this map maps one or more keys to the specified value. 
    public boolean containsValue(Object value)  {
        throw new UnsupportedOperationException();
    }
    //  Returns a set view of the mappings contained in this map.
    public Set entrySet() {
        throw new UnsupportedOperationException();
    }
    //  Compares the specified object with this map for equality. 
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }
    //  Returns the hash code value for this map.
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

}