package io.github.thibaultmeyer.mupipe.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Groups elements by a specific key.
 *
 * @param <I>  Input element type
 * @param <OK> Output map key type
 */
public final class GroupByTask<I, OK> implements Task<I, Map<OK, List<I>>> {

    private final Function<I, OK> keyExtractorFunction;
    private final Map<OK, List<I>> elementMap;

    /**
     * Build a new instance.
     *
     * @param keyExtractorFunction Function to extract element key
     */
    public GroupByTask(final Function<I, OK> keyExtractorFunction) {

        this.keyExtractorFunction = keyExtractorFunction;
        this.elementMap = new HashMap<>();
    }

    @Override
    public Map<OK, List<I>> execute(final I element, final boolean isLastElementFromSource) {

        final OK key = this.keyExtractorFunction.apply(element);
        final List<I> elementList = this.elementMap.computeIfAbsent(key, (k) -> new ArrayList<>());

        elementList.add(element);

        return isLastElementFromSource
            ? this.elementMap
            : null;
    }
}
