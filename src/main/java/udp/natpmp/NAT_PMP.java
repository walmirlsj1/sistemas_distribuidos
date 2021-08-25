package udp.natpmp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class NAT_PMP {
    public static void main(String[] args) throws IOException {
        var socket = new DatagramSocket();
        var server = new InetSocketAddress("meu ip gateway", 5351);
        var datagram = new DatagramPacket(new byte[]{0, 0}, 2, server);

        socket.send(datagram);

        var answer = new byte[12];
        datagram.setData(answer);
        socket.setSoTimeout(12000);
        socket.receive(datagram);

        var buff = ByteBuffer.wrap(answer);
        System.out.println("Version: " + buff.get());
        System.out.println("OP code: " + buff.get());
        System.out.println("Result.: " + buff.getShort());
        System.out.println("Time...: " + buff.getInt());
        System.out.printf("IP Ext.: %d.%d.%d.%d\n", buff.get(), buff.get(), buff.get(), buff.get());

        System.out.println(Arrays.toString(answer));
        socket.close();
    }
}
