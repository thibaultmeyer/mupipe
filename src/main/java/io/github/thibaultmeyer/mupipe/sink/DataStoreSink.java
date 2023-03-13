package io.github.thibaultmeyer.mupipe.sink;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;
import io.github.thibaultmeyer.mupipe.datastore.DataStoreKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores pipeline elements into data store.
 *
 * @param <I> Input element type
 * @since 1.0.0
 */
public class DataStoreSink<I> implements Sink<I> {

    private final DataStoreKey<I> keySingle;
    private final DataStoreKey<List<I>> keyList;
    private final DataStoreKey<Set<I>> keySet;

    /**
     * Build a new instance.
     *
     * @param keySingle Key used to store a single element in the data store
     * @param keyList   Key used to store multiple elements (in list) in the data store
     * @param keySet    Key used to store multiple elements (in set) in the data store
     * @since 1.0.0
     */
    private DataStoreSink(final DataStoreKey<I> keySingle,
                          final DataStoreKey<List<I>> keyList,
                          final DataStoreKey<Set<I>> keySet) {

        this.keySingle = keySingle;
        this.keyList = keyList;
        this.keySet = keySet;
    }

    /**
     * Creates a new instance configured to store a single value.
     *
     * @param key Key used to store elements in the data store
     * @param <I> Input element type
     * @return Newly created data store sink
     * @since 1.0.0
     */
    public static <I> DataStoreSink<I> createStoreAsSingle(final DataStoreKey<I> key) {

        return new DataStoreSink<>(key, null, null);
    }

    /**
     * Creates a new instance configured to store a multiple values in a list.
     *
     * @param key Key used to store elements in the data store
     * @param <I> Input element type
     * @return Newly created data store sink
     * @since 1.0.0
     */
    public static <I> DataStoreSink<I> createStoreAsList(final DataStoreKey<List<I>> key) {

        return new DataStoreSink<>(null, key, null);
    }

    /**
     * Creates a new instance configured to store a multiple values in a set.
     *
     * @param key Key used to store elements in the data store
     * @param <I> Input element type
     * @return Newly created data store sink
     * @since 1.0.0
     */
    public static <I> DataStoreSink<I> createStoreAsSet(final DataStoreKey<Set<I>> key) {

        return new DataStoreSink<>(null, null, key);
    }

    @Override
    public void execute(final I element, final DataStore dataStore) throws Exception {

        if (this.keySingle != null) {
            dataStore.put(keySingle, element);
        } else {
            Collection<I> elementCollection;
            if (this.keyList != null) {
                elementCollection = dataStore.get(keyList);
                if (elementCollection == null) {
                    elementCollection = new ArrayList<>();
                    dataStore.put(keyList, (List<I>) elementCollection);
                }
            } else {
                elementCollection = dataStore.get(keySet);
                if (elementCollection == null) {
                    elementCollection = new HashSet<>();
                    dataStore.put(keySet, (Set<I>) elementCollection);
                }
            }

            elementCollection.add(element);
        }
    }
}
