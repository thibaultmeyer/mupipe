package io.github.thibaultmeyer.mupipe.source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Reads elements from network via User Datagram Packet (UDP) protocol.
 */
public class NetworkUDPSource implements Source<byte[]> {

    private final DatagramSocket datagramSocket;
    private final DatagramPacket datagramPacket;

    /**
     * Build a new instance.
     *
     * @param listenPort Port on which listen for incomming data
     * @param bufferSize Buffer size. If a packet is larger than given size, the excess will be discarded
     * @throws SocketException If an error occurs when creating the socket
     */
    public NetworkUDPSource(final int listenPort, final int bufferSize) throws SocketException {

        this.datagramSocket = new DatagramSocket(listenPort);
        this.datagramPacket = new DatagramPacket(new byte[bufferSize], bufferSize);
    }

    @Override
    public boolean hasNext() {

        return true;
    }

    @Override
    public byte[] nextElement() {

        try {
            this.datagramSocket.receive(this.datagramPacket);

            final byte[] data = Arrays.copyOfRange(
                this.datagramPacket.getData(),
                0,
                this.datagramPacket.getLength());

            this.datagramPacket.setLength(0);
            return data;
        } catch (final IOException ignore) {
            return null;
        }
    }

    @Override
    public void close() {

        this.datagramSocket.close();
    }
}
