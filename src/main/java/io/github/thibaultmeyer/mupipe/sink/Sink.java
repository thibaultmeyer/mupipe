package io.github.thibaultmeyer.mupipe.sink;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

/**
 * Sink is an end point which accepts an element without returning any.
 *
 * @param <I> Input element type
 * @since 1.0.0
 */
public interface Sink<I> extends AutoCloseable {

    /**
     * Opens sink.
     *
     * @throws Exception If this sink cannot be opened
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
     * Execute sink.
     *
     * @param element   Element on which perform sink
     * @param dataStore Data store
     * @throws Exception If something goes wrong
     * @since 1.0.0
     */
    void execute(final I element, final DataStore dataStore) throws Exception;
}
