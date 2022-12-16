package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.CollectionSink;
import io.github.thibaultmeyer.mupipe.sink.Sink;
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
    void builder() {

        final List<String> sinkStoreList = new ArrayList<>();
        new PipelineBuilder()
            .addSource(new CollectionSource<>(List.of("1", "2", "3")))
            .addSource(new CollectionSource<>(List.of("4")))
            .addTask((item, isLastItemFromSource) -> Integer.parseInt(item))
            .addTask((item, isLastItemFromSource) -> String.valueOf(item))
            .addSink(new CollectionSink<>(sinkStoreList))
            .build()
            .execute();

        System.err.println(sinkStoreList);
    }

    @Test
    void accumulator() {

        // Arrange
        final List<List<String>> sinkStoreList = new ArrayList<>();
        final Pipeline pipeline = new Pipeline()
            .addSource(new CollectionSource<>(List.of("apple", "banana", "pear")))
            .addSource(new CollectionSource<>(List.of("cranberry", "strawberry")))
            .addTask(new AccumulatorTask<String>(2))
            .addSink(new CollectionSink<>(sinkStoreList));

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
        final Pipeline pipeline = new Pipeline()
            .addSource(new CollectionSource<>(List.of("cherry", "blueberry", "banana", "apple")))
            .addTask(new GroupByTask<String, String>((item) -> String.valueOf(item.charAt(0))))
            .addSink((Sink<Map<String, List<String>>>) sinkStoreMap::putAll);

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

        final Pipeline evenPipeline = new Pipeline()
            .addSink(new CollectionSink<>(evenSinkStoreList));

        final Pipeline oddPipeline = new Pipeline()
            .addSink(new CollectionSink<>(oddSinkStoreList));

        final Pipeline pipeline = new Pipeline()
            .addSource(new CollectionSource<>(List.of(1, 2, 3, 4, 5, 6)))
            .addTask(new RouterTask<Integer>(List.of(evenPipeline, oddPipeline), (item) -> item % 2));

        // Act
        pipeline.execute();

        // Assert
        Assertions.assertEquals(3, evenSinkStoreList.size());
        Assertions.assertEquals(3, oddSinkStoreList.size());
        Assertions.assertEquals(List.of(2, 4, 6), evenSinkStoreList);
        Assertions.assertEquals(List.of(1, 3, 5), oddSinkStoreList);
    }
}
