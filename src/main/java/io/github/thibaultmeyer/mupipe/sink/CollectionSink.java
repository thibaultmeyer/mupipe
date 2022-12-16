package io.github.thibaultmeyer.mupipe.sink;

import java.util.Collection;

/**
 * Stores pipeline output into a collection.
 *
 * @param <T> Wrote item type
 */
public class CollectionSink<T> implements Sink<T> {

    private final Collection<T> itemList;

    /**
     * Build a new instance.
     *
     * @param itemList List in which to write the items
     */
    public CollectionSink(final Collection<T> itemList) {

        this.itemList = itemList;
    }

    @Override
    public void execute(final T item) throws Exception {

        this.itemList.add(item);
    }
}
