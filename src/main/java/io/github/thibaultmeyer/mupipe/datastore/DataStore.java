package io.github.thibaultmeyer.mupipe.datastore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The data store allows for the persistence of information that
 * can then be accessed in all subsequent stages of the pipeline.
 *
 * @since 1.0.0
 */
public final class DataStore {

    private final Map<DataStoreKey<?>, Object> internalMap;

    /**
     * Build a new instance.
     *
     * @since 1.0.0
     */
    public DataStore() {

        this.internalMap = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves the value associated to the specified key.
     *
     * @param key Typed key
     * @param <T> Value type
     * @return Value associated to the specified key, otherwise, {@code null}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final DataStoreKey<T> key) {

        return (T) this.internalMap.get(key);
    }

    /**
     * Associates the specified value with the specified key.
     *
     * @param key   Typed key
     * @param value Value to associate with the given key
     * @param <T>   Value type
     * @return Previous value associated with key, otherwise, {@code null}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T> T put(final DataStoreKey<T> key, T value) {

        return (T) this.internalMap.put(key, value);
    }

    /**
     * Removes the value associated to the specified key.
     *
     * @param key Typed key
     * @param <T> Value type
     * @return Value associated to the specified key, otherwise, {@code null}
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T> T remove(final DataStoreKey<T> key) {

        return (T) this.internalMap.remove(key);
    }

    /**
     * Returns the number value stored in the data store.
     *
     * @return Number value stored in the data store
     * @since 1.0.0
     */
    public int size() {

        return this.internalMap.size();
    }
}
