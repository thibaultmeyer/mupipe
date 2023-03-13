package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.util.function.Function;

/**
 * Filters an element according to the result of the decision function.
 * If the decision function returns {@code true}, the element will be filtered.
 *
 * @param <T> Input and Output element type
 * @since 1.0.0
 */
public class FilterTask<T> implements Task<T, T> {

    private final Function<T, Boolean> filteringDecisionFunction;

    /**
     * Build a new instance.
     *
     * @param filteringDecisionFunction Filtering decision function
     * @since 1.0.0
     */
    public FilterTask(final Function<T, Boolean> filteringDecisionFunction) {

        this.filteringDecisionFunction = filteringDecisionFunction;
    }

    @Override
    public T execute(final T element, final DataStore dataStore, final boolean isLastElementFromSource) throws Exception {

        if (this.filteringDecisionFunction.apply(element) == Boolean.TRUE) {
            return element;
        }

        return null;
    }
}
