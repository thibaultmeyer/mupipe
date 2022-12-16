package io.github.thibaultmeyer.mupipe.source;

/**
 * Source.
 *
 * @param <T> Read item type
 */
public interface Source<T> {

    /**
     * Checks if the source has item available.
     *
     * @return {@code true} if the source has item available, otherwise {@code false}
     */
    boolean hasNext();

    /**
     * Gets the next item.
     *
     * @return Next item
     */
    T nextItem();
}
