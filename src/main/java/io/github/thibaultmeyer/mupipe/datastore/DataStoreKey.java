package io.github.thibaultmeyer.mupipe.datastore;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Data store key.
 *
 * @param <T> Type of the value associated to the key
 */
public class DataStoreKey<T> {

    private final Type type;
    private final String name;

    /**
     * Build a new instance.
     *
     * @param name      Key name
     * @param valueType Type of the value associated to the key
     */
    public DataStoreKey(final String name, final ValueType<T> valueType) {

        this.type = valueType.getType();
        this.name = name;
    }

    /**
     * Creates a new data store key.
     *
     * @param name      Key name
     * @param valueType Type of the value associated to the key
     * @param <T>       Type of the value associated to the key
     * @return Newly created data store key
     */
    public static <T> DataStoreKey<T> of(final String name, final ValueType<T> valueType) {

        return new DataStoreKey<>(name, valueType);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DataStoreKey<?> that = (DataStoreKey<?>) o;
        return type.equals(that.type) && name.equals(that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, name);
    }
}
