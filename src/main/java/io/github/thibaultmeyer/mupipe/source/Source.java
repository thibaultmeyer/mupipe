package io.github.thibaultmeyer.mupipe.source;

/**
 * Source is a starting point of a pipeline which produce elements to process.
 *
 * @param <R> Type of elements produced by the source
 * @since 1.0.0
 */
public interface Source<R> extends AutoCloseable {

    /**
     * Opens source.
     *
     * @throws Exception If this source cannot be opened
     * @since 1.0.0
     */
    default void open() throws Exception {

        // By default, this method does nothing. It is the responsibility of
        // implementations to know if they need to implement this method.
    }

    @Override
    default void close() throws Exception {

        // By default, this method does nothing. It is the responsibility of
        // implementations to know if they need to implement this method.
    }

    /**
     * Checks if the source has element available.
     *
     * @return {@code true} if the source has element available, otherwise {@code false}
     * @since 1.0.0
     */
    boolean hasNext();

    /**
     * Gets the next element.
     *
     * @return Next element
     * @since 1.0.0
     */
    R nextElement();
}
