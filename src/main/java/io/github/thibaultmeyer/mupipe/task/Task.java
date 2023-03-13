package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

/**
 * Task is a small component that performs operations on every element passing through the pipeline.
 *
 * @param <I> Input element type
 * @param <O> Output element type
 * @since 1.0.0
 */
public interface Task<I, O> {

    /**
     * Executes task.
     *
     * @param element                 Element on which perform task
     * @param dataStore               Data store
     * @param isLastElementFromSource Indicates if the current element is the last one
     * @return Element transformed by the task
     * @throws Exception If something goes wrong
     * @since 1.0.0
     */
    O execute(final I element, final DataStore dataStore, final boolean isLastElementFromSource) throws Exception;
}
