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
import java.util.Map;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class NamedRouterTaskTest {

    @Test
    void namedRouter() {

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
            .addTask(new NamedRouterTask<>(
                Map.of("even", evenPipeline, "odd", oddPipeline),
                (element) -> element % 2 == 0 ? "even" : "odd"))
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
