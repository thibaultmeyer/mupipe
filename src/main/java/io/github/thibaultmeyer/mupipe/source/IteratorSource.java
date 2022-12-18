package io.github.thibaultmeyer.mupipe.source;

import java.util.Iterator;

/**
 * Reads elements from an iterator.
 *
 * @param <T> Element type
 */
public final class IteratorSource<T> implements Source<T> {

    private final Iterator<T> source;

    /**
     * Build a new instance.
     *
     * @param source Source to use
     */
    public IteratorSource(final Iterator<T> source) {

        this.source = source;
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
