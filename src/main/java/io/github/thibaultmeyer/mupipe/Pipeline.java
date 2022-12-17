package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.Sink;
import io.github.thibaultmeyer.mupipe.source.Source;
import io.github.thibaultmeyer.mupipe.task.Task;

import java.util.List;

/**
 * Pipeline.
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
     */
    public static PipelineBuilder newBuilder() {

        return new PipelineBuilder();
    }

    /**
     * Starts the pipeline.
     */
    public void execute() {

        for (int idx = 0; idx < this.sourceList.size(); idx += 1) {

            final Source<?> source = this.sourceList.get(idx);
            while (source.hasNext()) {
                final Object element = source.nextElement();
                final boolean isLastElementFromSource = !source.hasNext() && (idx >= this.sourceList.size() - 1);

                this.processCurrentElement(element, isLastElementFromSource);
            }
        }
    }

    private void processCurrentElement(final Object element, final boolean isLastElementFromSource) {

        Object currentElement = element;
        for (final Task<Object, Object> task : this.taskList) {
            try {
                currentElement = task.execute(currentElement, isLastElementFromSource);
            } catch (final Exception ex) {
                ex.printStackTrace();
                // TODO: Do something with exception
            }

            if (currentElement == null) {
                return;
            }
        }

        for (final Sink<Object> sink : this.sinkList) {
            try {
                sink.execute(currentElement);
            } catch (final Exception ex) {
                ex.printStackTrace();
                // TODO: Do something with exception
            }
        }
    }
}
