package io.github.thibaultmeyer.mupipe.sink;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;
import io.github.thibaultmeyer.mupipe.datastore.DataStoreKey;
import io.github.thibaultmeyer.mupipe.datastore.ValueType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Set;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class DataStoreSinkTest {

    @Test
    void executeStorageStrategyList() throws Exception {

        // Arrange
        final DataStoreKey<List<String>> key = DataStoreKey.of("key", new ValueType<>() {
        });
        final Sink<String> sink = DataStoreSink.createStoreAsList(key);
        final DataStore dataStore = new DataStore();

        // Act
        sink.execute("apple", dataStore);
        sink.execute("banana", dataStore);
        sink.execute("banana", dataStore);
        sink.close();

        // Assert
        Assertions.assertEquals(1, dataStore.size());
        Assertions.assertEquals(List.of("apple", "banana", "banana"), dataStore.get(key));
    }

    @Test
    void executeStorageStrategySet() throws Exception {

        // Arrange
        final DataStoreKey<Set<String>> key = DataStoreKey.of("key", new ValueType<>() {
        });
        final Sink<String> sink = DataStoreSink.createStoreAsSet(key);
        final DataStore dataStore = new DataStore();

        // Act
        sink.execute("apple", dataStore);
        sink.execute("banana", dataStore);
        sink.execute("banana", dataStore);
        sink.close();

        // Assert
        Assertions.assertEquals(1, dataStore.size());

        final Set<String> valueSet = dataStore.get(key);
        Assertions.assertEquals(Set.of("apple", "banana"), valueSet);
    }

    @Test
    void executeStorageStrategySingle() throws Exception {

        // Arrange
        final DataStoreKey<String> key = DataStoreKey.of("key", new ValueType<>() {
        });
        final Sink<String> sink = DataStoreSink.createStoreAsSingle(key);
        final DataStore dataStore = new DataStore();

        // Act
        sink.execute("apple", dataStore);
        sink.execute("banana", dataStore);
        sink.close();

        // Assert
        Assertions.assertEquals(1, dataStore.size());
        Assertions.assertEquals("banana", dataStore.get(key));
    }
}
