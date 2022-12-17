package io.github.thibaultmeyer.mupipe.task;

/**
 * Task is a small component that performs operations on every element passing through the pipeline.
 *
 * @param <I> Input element type
 * @param <O> Output element type
 */
public interface Task<I, O> {

    /**
     * Executes task.
     *
     * @param element                 Element on which perform task
     * @param isLastElementFromSource Indicates if the current element is the last one,
     * @return Element transformed by the task
     * @throws Exception If something goes wrong
     */
    O execute(final I element, final boolean isLastElementFromSource) throws Exception;
}
