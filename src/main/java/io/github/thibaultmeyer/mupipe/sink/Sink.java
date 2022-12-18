package io.github.thibaultmeyer.mupipe.sink;

/**
 * Sink is an end point which accepts an element without returning any.
 *
 * @param <I> Input element type
 */
public interface Sink<I> extends AutoCloseable {

    /**
     * Opens sink.
     *
     * @throws Exception If this sink cannot be opened
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
     * @param element Element on which perform sink
     * @throws Exception If something goes wrong
     */
    void execute(final I element) throws Exception;
}
