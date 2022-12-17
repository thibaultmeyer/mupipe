package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;
import io.github.thibaultmeyer.mupipe.sink.CollectionSink;
import io.github.thibaultmeyer.mupipe.source.CollectionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class AccumulatorTaskTest {

    @Test
    void accumulator() {

        // Arrange
        final List<List<String>> sinkStoreList = new ArrayList<>();
        final Pipeline pipeline = Pipeline.newBuilder()
            .addSource(new CollectionSource<>(List.of("apple", "banana", "pear")))
            .addSource(new CollectionSource<>(List.of("cranberry", "strawberry")))
            .addTask(new AccumulatorTask<>(2))
            .addSink(new CollectionSink<>(sinkStoreList))
            .build();

        // Act
        pipeline.execute();

        // Assert
        Assertions.assertEquals(3, sinkStoreList.size());
        Assertions.assertEquals(List.of("apple", "banana"), sinkStoreList.get(0));
        Assertions.assertEquals(List.of("pear", "cranberry"), sinkStoreList.get(1));
        Assertions.assertEquals(List.of("strawberry"), sinkStoreList.get(2));
    }
}
