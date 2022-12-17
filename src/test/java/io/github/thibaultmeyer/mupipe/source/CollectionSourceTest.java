package io.github.thibaultmeyer.mupipe.source;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class CollectionSourceTest {

    @Test
    void hasNext() throws Exception {

        // Arrange
        final List<String> dataList = List.of("apple");
        final Source<String> source = new CollectionSource<>(dataList);

        // Act
        final boolean hasNextFirst = source.hasNext();
        source.nextElement();

        final boolean hasNextSecond = source.hasNext();
        source.close();

        // Assert
        Assertions.assertTrue(hasNextFirst);
        Assertions.assertFalse(hasNextSecond);
    }

    @Test
    void nextElement() throws Exception {

        // Arrange
        final List<String> dataList = List.of("apple");
        final Source<String> source = new CollectionSource<>(dataList);

        // Act
        final String element = source.nextElement();
        source.close();

        // Assert
        Assertions.assertNotNull(element);
        Assertions.assertEquals("apple", element);
    }

    @Test
    void nextElementNoMoreElement() throws Exception {

        // Arrange
        final List<String> dataList = List.of();
        final Source<String> source = new CollectionSource<>(dataList);

        // Act
        final NoSuchElementException exception = Assertions.assertThrows(
            NoSuchElementException.class,
            source::nextElement);
        source.close();

        // Assert
        Assertions.assertNotNull(exception);
    }
}
