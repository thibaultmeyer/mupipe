package io.github.thibaultmeyer.mupipe.sink;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@TestMethodOrder(MethodOrderer.MethodName.class)
final class ConsoleSinkTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void execute() throws Exception {

        // Arrange
        final Sink<String> sink = new ConsoleOutputSink<>();

        // Act
        sink.execute("apple");
        sink.execute("banana");
        sink.execute("cranberry");

        // Assert
        Assertions.assertEquals(26, outputStreamCaptor.size());

        final String removedLineFeed = outputStreamCaptor.toString().replace("\r", "").replace("\n", "");
        Assertions.assertEquals("applebananacranberry", removedLineFeed);
    }
}
