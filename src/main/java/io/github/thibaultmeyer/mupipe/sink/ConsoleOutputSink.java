package io.github.thibaultmeyer.mupipe.sink;

/**
 * Writes pipeline output into console output.
 */
public class ConsoleOutputSink implements Sink<Object> {

    @Override
    public void execute(final Object item) throws Exception {

        System.out.println(item);
    }
}
