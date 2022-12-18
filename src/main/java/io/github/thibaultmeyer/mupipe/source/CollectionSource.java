package io.github.thibaultmeyer.mupipe.source;

import java.util.Collection;
import java.util.Iterator;

/**
 * Reads elements from a collection.
 *
 * @param <T> Element type
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
    public T nextElement() {

        return this.source.next();
    }
}
