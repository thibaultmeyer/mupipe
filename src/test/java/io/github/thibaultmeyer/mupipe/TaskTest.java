package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.CollectionSink;
import io.github.thibaultmeyer.mupipe.source.CollectionSource;
import io.github.thibaultmeyer.mupipe.task.AccumulatorTask;
import io.github.thibaultmeyer.mupipe.task.GroupByTask;
import io.github.thibaultmeyer.mupipe.task.RouterTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class TaskTest {

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

    @Test
    void router() {

        // Arrange
        final List<Integer> evenSinkStoreList = new ArrayList<>();
        final List<Integer> oddSinkStoreList = new ArrayList<>();

        final Pipeline evenPipeline = Pipeline.newBuilder()
            .<Integer>noSource()
            .addSink(new CollectionSink<>(evenSinkStoreList))
            .build();

        final Pipeline oddPipeline = Pipeline.newBuilder()
            .<Integer>noSource()
            .addSink(new CollectionSink<>(oddSinkStoreList))
            .build();

        final Pipeline pipeline = Pipeline.newBuilder()
            .addSource(new CollectionSource<>(List.of(1, 2, 3, 4, 5, 6)))
            .addTask(new RouterTask<>(List.of(evenPipeline, oddPipeline), (element) -> element % 2))
            .build();

        // Act
        pipeline.execute();

        // Assert
        Assertions.assertEquals(3, evenSinkStoreList.size());
        Assertions.assertEquals(3, oddSinkStoreList.size());
        Assertions.assertEquals(List.of(2, 4, 6), evenSinkStoreList);
        Assertions.assertEquals(List.of(1, 3, 5), oddSinkStoreList);
    }
}
