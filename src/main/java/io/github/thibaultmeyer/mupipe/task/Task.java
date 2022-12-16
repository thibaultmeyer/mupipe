package io.github.thibaultmeyer.mupipe.task;

/**
 * Task.
 *
 * @param <I> Input item type
 * @param <O> Output item type
 */
public interface Task<I, O> {

    /**
     * Executes task.
     *
     * @param item                 Item on which perform task
     * @param isLastItemFromSource Indicates if the current item is the last one,
     * @return Item transformed by the task
     * @throws Exception If something goes wrong
     */
    O execute(final I item, final boolean isLastItemFromSource) throws Exception;
}
