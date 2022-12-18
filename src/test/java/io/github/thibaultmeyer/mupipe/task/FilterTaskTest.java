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
final class FilterTaskTest {

    @Test
    void filter() {

        // Arrange
        final List<Integer> evenSinkStoreList = new ArrayList<>();

        final Pipeline pipeline = Pipeline.newBuilder()
            .addSource(new CollectionSource<>(List.of(1, 2, 3, 4, 5, 6)))
            .addTask(new FilterTask<>((element) -> element % 2 == 0))
            .addSink(new CollectionSink<>(evenSinkStoreList))
            .build();

        // Act
        pipeline.execute();

        // Assert
        Assertions.assertEquals(3, evenSinkStoreList.size());
        Assertions.assertEquals(List.of(2, 4, 6), evenSinkStoreList);
    }
}
