package io.github.thibaultmeyer.mupipe.source;

import java.util.Collection;
import java.util.Iterator;

/**
 * Reads item from a collection.
 *
 * @param <T> Item type
 */
public final class CollectionSource<T> implements Source<T> {

    private final Iterator<T> source;

    /**
     * Build a new instance.
     *
     * @param source Source to use
     */
    public CollectionSource(final Collection<T> source) {

        this.source = source.iterator();
    }

    @Override
    public boolean hasNext() {

        return this.source.hasNext();
    }

    @Override
    public T nextItem() {

        return this.source.next();
    }
}
