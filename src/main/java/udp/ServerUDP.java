package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ServerUDP {
    public static void main(String[] args) throws IOException {
        var server = new InetSocketAddress("10.0.1.1"   ,53);
        var socket = new DatagramSocket();
        var msg = "montar protocolo DNS";
        var datagram = new DatagramPacket(msg.getBytes(), msg.length(), server);
        socket.send(datagram);
    }
}
