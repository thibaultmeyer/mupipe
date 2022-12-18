package io.github.thibaultmeyer.mupipe.source;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class IteratorSourceTest {

    @Test
    void hasNext() throws Exception {

        // Arrange
        final Iterator<String> dataList = List.of("apple").iterator();
        final Source<String> source = new IteratorSource<>(dataList);

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
        final Iterator<String> dataList = List.of("apple").iterator();
        final Source<String> source = new IteratorSource<>(dataList);

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
        final Iterator<String> dataList = Collections.emptyIterator();
        final Source<String> source = new IteratorSource<>(dataList);

        // Act
        final NoSuchElementException exception = Assertions.assertThrows(
            NoSuchElementException.class,
            source::nextElement);
        source.close();

        // Assert
        Assertions.assertNotNull(exception);
    }
}
