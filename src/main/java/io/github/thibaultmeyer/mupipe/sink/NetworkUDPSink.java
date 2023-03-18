package io.github.thibaultmeyer.mupipe.sink;

import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Write elements to network via User Datagram Packet (UDP) protocol.
 *
 * @since 1.2.0
 */
public class NetworkUDPSink implements Sink<byte[]> {

    private final DatagramSocket datagramSocket;
    private final InetAddress destInetAddress;
    private final int destPort;

    /**
     * Build a new instance.
     *
     * @param destPort    Destination port
     * @param destAddress Destination address
     * @throws SocketException      If an error occurs when creating the socket
     * @throws UnknownHostException If destination or localhost address cannot be resolved
     * @since 1.2.0
     */
    public NetworkUDPSink(final int destPort, final String destAddress) throws SocketException, UnknownHostException {

        this(destPort, InetAddress.getByName(destAddress));
    }

    /**
     * Build a new instance.
     *
     * @param destPort    Destination port
     * @param destAddress Destination address
     * @throws SocketException      If an error occurs when creating the socket
     * @throws UnknownHostException If localhost address cannot be resolved
     * @since 1.2.0
     */
    public NetworkUDPSink(final int destPort, final InetAddress destAddress) throws SocketException, UnknownHostException {

        this.datagramSocket = new DatagramSocket(0, InetAddress.getLocalHost());
        this.destPort = destPort;
        this.destInetAddress = destAddress;
    }

    @Override
    public void close() {

        this.datagramSocket.close();
    }

    @Override
    public void execute(final byte[] element, final DataStore dataStore) throws Exception {

        final DatagramPacket packet = new DatagramPacket(
            element,
            element.length,
            this.destInetAddress,
            this.destPort);

        this.datagramSocket.send(packet);
    }
}
