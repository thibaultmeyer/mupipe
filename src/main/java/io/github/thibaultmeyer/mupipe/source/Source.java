package io.github.thibaultmeyer.mupipe.source;

/**
 * Source is a starting point of a pipeline which produce elements to process.
 *
 * @param <R> Type of elements produced by the source
 */
public interface Source<R> extends AutoCloseable {

    /**
     * Opens source.
     *
     * @throws Exception If this source cannot be opened
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
     */
    boolean hasNext();

    /**
     * Gets the next element.
     *
     * @return Next element
     */
    R nextElement();
}
