package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Groups elements by a specific key.
 *
 * @param <I> Input element type
 * @param <K> Output map key type
 * @since 1.0.0
 */
public final class GroupByTask<I, K> implements Task<I, Map<K, List<I>>> {

    private final Function<I, K> keyExtractorFunction;
    private final Map<K, List<I>> elementMap;

    /**
     * Build a new instance.
     *
     * @param keyExtractorFunction Function to extract element key
     * @since 1.0.0
     */
    public GroupByTask(final Function<I, K> keyExtractorFunction) {

        this.keyExtractorFunction = keyExtractorFunction;
        this.elementMap = new HashMap<>();
    }

    @Override
    public Map<K, List<I>> execute(final I element, final DataStore dataStore, final boolean isLastElementFromSource) {

        final K key = this.keyExtractorFunction.apply(element);
        final List<I> elementList = this.elementMap.computeIfAbsent(key, (k) -> new ArrayList<>());

        elementList.add(element);

        return isLastElementFromSource ? this.elementMap : null;
    }
}
