package io.github.thibaultmeyer.mupipe.sink;

/**
 * Writes pipeline output into console output.
 *
 * @param <I> Input element type
 */
public class ConsoleOutputSink<I> implements Sink<I> {

    @Override
    public void execute(final I element) throws Exception {

        System.out.println(element);
    }
}
