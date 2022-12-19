package io.github.thibaultmeyer.mupipe.datastore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class DataStoreKeyTest {

    @Test
    void key() {

        // Act
        final DataStoreKey<List<String>> key = DataStoreKey.of("key", new ValueType<>() {
        });
        final DataStoreKey<List<String>> key2 = DataStoreKey.of("key", new ValueType<>() {
        });
        final DataStoreKey<String> key3 = DataStoreKey.of("key", new ValueType<>() {
        });
        final DataStoreKey<String> key4 = DataStoreKey.of("key2", new ValueType<>() {
        });

        // Assert
        Assertions.assertEquals(key, key2);
        Assertions.assertNotEquals(key, key3);
        Assertions.assertNotEquals(key2, key3);
        Assertions.assertNotEquals(key3, key4);
    }
}
