package io.github.thibaultmeyer.mupipe.datastore;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @param <T> Type of the value
 * @see <a href="https://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime">stackoverflow.com</a>
 * @since 1.0.0
 */
public abstract class ValueType<T> implements Comparable<ValueType<T>> {

    protected final Type javaValueType;

    /**
     * Build a new instance.
     *
     * @since 1.0.0
     */
    protected ValueType() {

        final Type genericSuperclass = this.getClass().getGenericSuperclass();
        this.javaValueType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    /**
     * Returns the value type.
     *
     * @return Java type
     * @since 1.0.0
     */
    public Type getType() {

        return this.javaValueType;
    }

    /**
     * Compare current value type with another.
     *
     * @param o the object to be compared.
     * @return 0 if the value type are same
     * @since 1.0.0
     */
    public int compareTo(final ValueType<T> o) {

        if (o == null) {
            return -1;
        }

        return this.javaValueType == o.javaValueType ? 0 : 1;
    }
}
