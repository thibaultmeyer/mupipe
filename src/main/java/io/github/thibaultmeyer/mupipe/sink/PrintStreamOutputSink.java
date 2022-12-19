package io.github.thibaultmeyer.mupipe.sink;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.io.PrintStream;

/**
 * Writes pipeline output into given print stream.
 *
 * @param <I> Input element type
 */
public class PrintStreamOutputSink<I> implements Sink<I> {

    private final PrintStream printStream;

    /**
     * Build a new instance.
     *
     * @param printStream Print stream to use
     */
    public PrintStreamOutputSink(final PrintStream printStream) {

        this.printStream = printStream;
    }

    @Override
    public void execute(final I element, final DataStore dataStore) throws Exception {

        printStream.println(element);
    }
}
