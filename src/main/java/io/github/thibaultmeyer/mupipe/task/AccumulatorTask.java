package io.github.thibaultmeyer.mupipe.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Accumulates items up to the threshold.
 *
 * @param <T> Item type
 */
public final class AccumulatorTask<T> implements Task<T, List<T>> {

    private final int threshold;
    private List<T> itemList;

    /**
     * Build a new instance.
     *
     * @param threshold Threshold value
     */
    public AccumulatorTask(final int threshold) {

        this.threshold = threshold;
        this.itemList = new ArrayList<>(threshold + 1);
    }

    @Override
    public List<T> execute(final T item, final boolean isLastItemFromSource) {

        this.itemList.add(item);
        if (this.itemList.size() >= this.threshold || isLastItemFromSource) {
            final List<T> itemListToReturn = this.itemList;
            this.itemList = new ArrayList<>(threshold + 1);

            return itemListToReturn;
        }

        return null;
    }
}
