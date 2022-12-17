package io.github.thibaultmeyer.mupipe.source;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
final class NetworkUDPSourceTest {

    private static final int SOCKET_PORT = 43568;
    private static final int BUFFER_SIZE = 1024;

    private static DatagramSocket datagramSocket;
    private static ScheduledExecutorService executor;

    @BeforeAll
    public static void initUDPServer() throws SocketException, UnknownHostException {

        datagramSocket = new DatagramSocket();
        final DatagramPacket datagramPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
        datagramPacket.setData("Hello World!".getBytes(StandardCharsets.UTF_8));
        datagramPacket.setLength("Hello World!".length());
        datagramPacket.setAddress(InetAddress.getLocalHost());
        datagramPacket.setPort(SOCKET_PORT);

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                datagramSocket.send(datagramPacket);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    @AfterAll
    public static void closeUDPServer() {

        executor.shutdown();
        datagramSocket.close();
    }

    @Test
    void hasNext() throws Exception {

        // Arrange
        final Source<byte[]> source = new NetworkUDPSource(SOCKET_PORT, BUFFER_SIZE);

        // Act
        final boolean hasNext = source.hasNext();
        source.close();

        // Assert
        Assertions.assertTrue(hasNext);
    }

    @Test
    void nextElement() throws Exception {

        // Arrange
        final Source<byte[]> source = new NetworkUDPSource(43568, 1024);

        // Act
        final byte[] element = source.nextElement();
        source.close();

        // Assert
        Assertions.assertNotNull(element);
        Assertions.assertEquals("Hello World!", new String(element, StandardCharsets.UTF_8));
    }
}
