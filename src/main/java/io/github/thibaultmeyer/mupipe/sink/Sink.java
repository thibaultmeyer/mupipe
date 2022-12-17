package io.github.thibaultmeyer.mupipe.sink;

/**
 * Sink is an end point which accepts an element without returning any.
 *
 * @param <I> Input element type
 */
public interface Sink<I> {

    /**
     * Execute sink.
     *
     * @param element Element on which perform sink
     * @throws Exception If something goes wrong
     */
    void execute(final I element) throws Exception;
}
