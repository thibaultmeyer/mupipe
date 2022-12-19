package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.CollectionSink;
import io.github.thibaultmeyer.mupipe.source.CollectionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class BuilderTest {

    @Test
    void builderSingleAdd() {

        // Arrange
        final List<Integer> sinkStore1List = new ArrayList<>();
        final List<Integer> sinkStore2List = new ArrayList<>();
        final List<String> firstNumberList = List.of("1", "2", "3");
        final List<String> secondNumberList = List.of("4");

        // Act
        new PipelineBuilder()
            .addSource(new CollectionSource<>(firstNumberList))
            .addSource(new CollectionSource<>(secondNumberList))
            .addTask((element, dataStore, isLastElementFromSource) -> Integer.parseInt(element))
            .addTask((element, dataStore, isLastElementFromSource) -> String.valueOf(element))
            .addTask((element, dataStore, isLastElementFromSource) -> Integer.parseInt(element))
            .addSink(new CollectionSink<>(sinkStore1List))
            .addSink(new CollectionSink<>(sinkStore2List))
            .build()
            .execute();

        // Assert
        Assertions.assertEquals(4, sinkStore1List.size());
        Assertions.assertEquals(List.of(1, 2, 3, 4), sinkStore1List);

        Assertions.assertEquals(4, sinkStore2List.size());
        Assertions.assertEquals(List.of(1, 2, 3, 4), sinkStore2List);
    }

    @Test
    void builderMultipleAdd() {

        // Arrange
        final List<Integer> sinkStore1List = new ArrayList<>();
        final List<Integer> sinkStore2List = new ArrayList<>();
        final List<String> firstNumberList = List.of("1", "2", "3");
        final List<String> secondNumberList = List.of("4");
        final List<String> thirdNumberList = List.of("5", "6");

        // Act
        new PipelineBuilder()
            .addSource(List.of(new CollectionSource<>(firstNumberList)))
            .addSource(List.of(new CollectionSource<>(secondNumberList), new CollectionSource<>(thirdNumberList)))
            .addTask((element, dataStore, isLastElementFromSource) -> Integer.parseInt(element))
            .addTask((element, dataStore, isLastElementFromSource) -> String.valueOf(element))
            .addTask((element, dataStore, isLastElementFromSource) -> Integer.parseInt(element))
            .addSink(List.of(new CollectionSink<>(sinkStore1List), new CollectionSink<>(sinkStore2List)))
            .build()
            .execute();

        // Assert
        Assertions.assertEquals(6, sinkStore1List.size());
        Assertions.assertEquals(List.of(1, 2, 3, 4, 5, 6), sinkStore1List);

        Assertions.assertEquals(6, sinkStore2List.size());
        Assertions.assertEquals(List.of(1, 2, 3, 4, 5, 6), sinkStore2List);
    }
}
