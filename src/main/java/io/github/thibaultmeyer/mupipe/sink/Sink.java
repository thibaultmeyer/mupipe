package io.github.thibaultmeyer.mupipe.sink;

/**
 * Sink.
 *
 * @param <T> Wrote item type
 */
public interface Sink<T> {

    /**
     * Execute sink.
     *
     * @param item Item to write
     * @throws Exception If something goes wrong
     */
    void execute(final T item) throws Exception;
}
