package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.Sink;
import io.github.thibaultmeyer.mupipe.source.Source;
import io.github.thibaultmeyer.mupipe.task.Task;

import java.util.ArrayList;
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
     */
    public Pipeline() {

        this.sourceList = new ArrayList<>();
        this.taskList = new ArrayList<>();
        this.sinkList = new ArrayList<>();
    }

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

    @SuppressWarnings("unchecked")
    public Pipeline addSink(final Sink<?> sink) {

        this.sinkList.add((Sink<Object>) sink);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Pipeline addSource(final Source<?> source) {

        this.sourceList.add((Source<Object>) source);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Pipeline addTask(final Task<?, ?> task) {

        this.taskList.add((Task<Object, Object>) task);
        return this;
    }

    public void execute() {

        for (int idx = 0; idx < this.sourceList.size(); idx += 1) {

            final Source<?> source = this.sourceList.get(idx);
            while (source.hasNext()) {
                final Object item = source.nextItem();
                final boolean isLastItemFromSource = !source.hasNext() && (idx >= this.sourceList.size() - 1);

                this.proceedOnItem(item, isLastItemFromSource);
            }
        }
    }

    private void proceedOnItem(final Object item, final boolean isLastItemFromSource) {

        Object currentItem = item;
        for (final Task<Object, Object> task : this.taskList) {
            try {
                currentItem = task.execute(currentItem, isLastItemFromSource);
            } catch (final Exception ex) {
                ex.printStackTrace();
                // TODO: Do something with exception
            }

            if (currentItem == null) {
                return;
            }
        }

        for (final Sink<Object> sink : this.sinkList) {
            try {
                sink.execute(currentItem);
            } catch (final Exception ex) {
                ex.printStackTrace();
                // TODO: Do something with exception
            }
        }
    }
}
