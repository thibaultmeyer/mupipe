package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;
import io.github.thibaultmeyer.mupipe.exception.PipelineException;
import io.github.thibaultmeyer.mupipe.sink.Sink;
import io.github.thibaultmeyer.mupipe.source.Source;
import io.github.thibaultmeyer.mupipe.task.Task;

import java.util.List;

/**
 * Pipeline.
 *
 * @since 1.0.0
 */
public final class Pipeline {

    private final List<Source<Object>> sourceList;
    private final List<Task<Object, Object>> taskList;
    private final List<Sink<Object>> sinkList;

    /**
     * Build a new instance.
     *
     * @param sourceList Sources to use
     * @param taskList   Tasks to execute
     * @param sinkList   Sinks to use
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    Pipeline(final List<Source<?>> sourceList,
             final List<Task<?, ?>> taskList,
             final List<Sink<?>> sinkList) {

        this.sourceList = (List<Source<Object>>) (Object) sourceList;
        this.taskList = (List<Task<Object, Object>>) (Object) taskList;
        this.sinkList = (List<Sink<Object>>) (Object) sinkList;
    }

    /**
     * Creates a new pipeline builder.
     *
     * @return Newly created pipeline builder
     * @since 1.0.0
     */
    public static PipelineBuilder newBuilder() {

        return new PipelineBuilder();
    }

    /**
     * Starts the pipeline.
     *
     * @throws PipelineException If an error occurs during the processing of the pipeline
     * @since 1.0.0
     */
    public void execute() {

        final DataStore dataStore = new DataStore();
        this.execute(dataStore);
    }

    /**
     * Starts the pipeline.
     *
     * @param dataStore Data store
     * @throws PipelineException If an error occurs during the processing of the pipeline
     * @since 1.0.0
     */
    public void execute(final DataStore dataStore) {

        try {
            this.openAllSourceAndSink();
        } catch (final PipelineException exception) {
            this.closeAllSourceAndSink();
            throw exception;
        }

        for (int idx = 0; idx < this.sourceList.size(); idx += 1) {

            final Source<?> source = this.sourceList.get(idx);
            while (source.hasNext()) {
                final Object element = source.nextElement();
                final boolean isLastElementFromSource = !source.hasNext() && (idx >= this.sourceList.size() - 1);

                this.handleElement(element, dataStore, isLastElementFromSource);
            }
        }

        this.closeAllSourceAndSink();
    }

    /**
     * Opens all registered sources and sinks.
     *
     * @throws PipelineException.CannotOpenSink   If a sink cannot be opened
     * @throws PipelineException.CannotOpenSource If a source cannot be opened
     * @since 1.0.0
     */
    private void openAllSourceAndSink() {

        for (final Source<?> source : this.sourceList) {
            try {
                source.open();
            } catch (final Exception exception) {
                throw new PipelineException.CannotOpenSource(source, exception);
            }
        }

        for (final Sink<?> sink : this.sinkList) {
            try {
                sink.open();
            } catch (final Exception exception) {
                throw new PipelineException.CannotOpenSink(sink, exception);
            }
        }
    }

    /**
     * Closes all registered sources and sinks.
     *
     * @since 1.0.0
     */
    private void closeAllSourceAndSink() {

        for (final Source<?> source : this.sourceList) {
            this.closeWithoutException(source);
        }

        for (final Sink<?> sink : this.sinkList) {
            this.closeWithoutException(sink);
        }
    }

    /**
     * Handles an element.
     *
     * @param element                 Element to handle
     * @param dataStore               Data store
     * @param isLastElementFromSource Indicates if the current element is the last one
     * @since 1.0.0
     */
    private void handleElement(final Object element, final DataStore dataStore, final boolean isLastElementFromSource) {

        Object currentElement = element;
        for (final Task<Object, Object> task : this.taskList) {
            try {
                currentElement = task.execute(currentElement, dataStore, isLastElementFromSource);
            } catch (final Exception ex) {
                throw new PipelineException.TaskFailure(ex);
            }

            if (currentElement == null) {
                return;
            }
        }

        for (final Sink<Object> sink : this.sinkList) {
            try {
                sink.execute(currentElement, dataStore);
            } catch (final Exception ex) {
                throw new PipelineException.SinkFailure(ex);
            }
        }
    }

    /**
     * Closes the given closeable without any exceptions.
     * This is typically used in finally blocks.
     *
     * @param autoCloseable The closeable to close
     * @since 1.0.0
     */
    private void closeWithoutException(final AutoCloseable autoCloseable) {

        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (final Exception ignore) {
                // This exception is not important
            }
        }
    }
}
