package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientDNS {
    public static void main(String[] args) {
        try {
            var socket = new DatagramSocket();
            var server = new InetSocketAddress("8.8.8.8", 53);
            var data = questionDNS("www.cpcx.ufms.br");//www.cpcx.ufms.br www.pearson.com

            var datagram = new DatagramPacket(data, data.length, server);

            socket.send(datagram);

            var answer = new byte[128];

            datagram.setData(answer);

            socket.receive(datagram);

            System.out.println(Arrays.toString(answer));
            answerDNS(answer);
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

    private static void answerDNS(byte[] answer) {
        ByteBuffer buffer = ByteBuffer.wrap(answer);

        for (int a = 0; a < 6; a++) {
            System.out.printf("%X\n", buffer.getShort());
        }
//        buffer.rewind();

        System.out.println("QUESTION");
        byte n = buffer.get();
//        while (n != 0) {
//            System.out.print((char) n);
//            n = buffer.get();
//        }
        do{
            for(int i = 0; i<n; i++){
                System.out.print((char) buffer.get());
            }
            System.out.print('.');
            n = buffer.get();
        }while (n>0);
        System.out.println();
        System.out.printf("Type: %04X, Class: %04X\n", buffer.getShort(), buffer.getShort());
    }

    private static byte[] questionDNS(String s) {
        var buffer = ByteBuffer.allocate(s.length() + 18);
        buffer.putShort((short) 0x7ABC);
        buffer.putShort((short) 0x0100);
        buffer.putShort((short) 0x0001);
        buffer.putShort((short) 0x0000);
        buffer.putShort((short) 0x0000);
        buffer.putShort((short) 0x0000);

        String[] name = s.split("\\.");

        for (String teste : name) {
            buffer.put((byte) teste.length());
            buffer.put(teste.getBytes());
        }
        buffer.put((byte) 0);

        buffer.putShort((short) 0x0001); //TYPE A IP4
        buffer.putShort((short) 0x0001); //Class Internet

        return buffer.array();
    }
}
