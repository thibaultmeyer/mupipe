package io.github.thibaultmeyer.mupipe.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Groups items by a specific key.
 *
 * @param <I>  Input item type
 * @param <OK> Output map key type
 */
public final class GroupByTask<I, OK> implements Task<I, Map<OK, List<I>>> {

    private final Function<I, OK> keyExtractorFunction;
    private final Map<OK, List<I>> itemMap;

    /**
     * Build a new instance.
     *
     * @param keyExtractorFunction Function to extract item key
     */
    public GroupByTask(final Function<I, OK> keyExtractorFunction) {

        this.keyExtractorFunction = keyExtractorFunction;
        this.itemMap = new HashMap<>();
    }

    @Override
    public Map<OK, List<I>> execute(final I item, final boolean isLastItemFromSource) {

        final OK key = this.keyExtractorFunction.apply(item);
        final List<I> itemList = this.itemMap.computeIfAbsent(key, (k) -> new ArrayList<>());

        itemList.add(item);

        return isLastItemFromSource
            ? this.itemMap
            : null;
    }
}
