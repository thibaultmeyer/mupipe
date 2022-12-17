package io.github.thibaultmeyer.mupipe.source;

/**
 * Source is a starting point of a pipeline which produce elements to process.
 *
 * @param <R> Type of elements produced by the source
 */
public interface Source<R> {

    /**
     * Checks if the source has element available.
     *
     * @return {@code true} if the source has element available, otherwise {@code false}
     */
    boolean hasNext();

    /**
     * Gets the next element.
     *
     * @return Next element
     */
    R nextElement();
}
