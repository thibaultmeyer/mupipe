package io.github.thibaultmeyer.mupipe.sink;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.util.Collection;

/**
 * Stores pipeline elements into a collection.
 *
 * @param <I> Input element type
 */
public class CollectionSink<I> implements Sink<I> {

    private final Collection<I> elementCollection;

    /**
     * Build a new instance.
     *
     * @param elementCollection Collection in which to write the elements
     */
    public CollectionSink(final Collection<I> elementCollection) {

        this.elementCollection = elementCollection;
    }

    @Override
    public void execute(final I element, final DataStore dataStore) throws Exception {

        this.elementCollection.add(element);
    }
}
