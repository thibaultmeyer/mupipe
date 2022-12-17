package io.github.thibaultmeyer.mupipe.sink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class CollectionSinkTest {

    @Test
    void execute() throws Exception {

        // Arrange
        final List<String> sinkStoreList = new ArrayList<>();
        final Sink<String> sink = new CollectionSink<>(sinkStoreList);

        // Act
        sink.execute("apple");
        sink.execute("banana");
        sink.execute("cranberry");

        // Assert
        Assertions.assertEquals(3, sinkStoreList.size());
        Assertions.assertEquals(List.of("apple", "banana", "cranberry"), sinkStoreList);
    }
}
