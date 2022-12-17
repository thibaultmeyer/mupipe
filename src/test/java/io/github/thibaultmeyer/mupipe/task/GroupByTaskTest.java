package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;
import io.github.thibaultmeyer.mupipe.source.CollectionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class GroupByTaskTest {

    @Test
    void groupBy() {

        // Arrange
        final Map<String, List<String>> sinkStoreMap = new HashMap<>();
        final Pipeline pipeline = Pipeline.newBuilder()
            .addSource(new CollectionSource<>(List.of("cherry", "blueberry", "banana", "apple")))
            .addTask(new GroupByTask<>((element) -> String.valueOf(element.charAt(0))))
            .addSink(sinkStoreMap::putAll)
            .build();

        // Act
        pipeline.execute();

        // Assert
        Assertions.assertEquals(3, sinkStoreMap.size());
        Assertions.assertEquals(List.of("apple"), sinkStoreMap.get("a"));
        Assertions.assertEquals(List.of("blueberry", "banana"), sinkStoreMap.get("b"));
        Assertions.assertEquals(List.of("cherry"), sinkStoreMap.get("c"));
    }
}
