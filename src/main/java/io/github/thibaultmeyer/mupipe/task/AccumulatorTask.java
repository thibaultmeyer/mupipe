package io.github.thibaultmeyer.mupipe.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Accumulates elements up to the threshold.
 *
 * @param <T> Element type
 */
public final class AccumulatorTask<T> implements Task<T, List<T>> {

    private final int threshold;
    private List<T> elementList;

    /**
     * Build a new instance.
     *
     * @param threshold Threshold value
     */
    public AccumulatorTask(final int threshold) {

        this.threshold = threshold;
        this.elementList = new ArrayList<>(threshold + 1);
    }

    @Override
    public List<T> execute(final T element, final boolean isLastElementFromSource) {

        this.elementList.add(element);
        if (this.elementList.size() >= this.threshold || isLastElementFromSource) {
            final List<T> elementListToReturn = this.elementList;
            this.elementList = new ArrayList<>(threshold + 1);

            return elementListToReturn;
        }

        return null;
    }
}
