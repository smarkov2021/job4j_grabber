package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public final void put(K key, V value) {
        cache.put(key, new SoftReference<V>(value));
    }

    public final V get(K key) {
        V rsl = null;
        SoftReference<V> link = cache.get(key);
        V elem = link == null ? null : link.get();
        if (elem != null) {
            rsl = elem;
        } else {
            rsl = load(key);
            put(key, rsl);
        }
        return rsl;
    }

    protected abstract V load(K key);

}