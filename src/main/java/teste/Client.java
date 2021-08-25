package teste;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "Coloque o IP";
        Socket socket;// = new Socket(host, 8080);
        int n = 0;
//        while(n<63000) {
            System.out.println("Lock");
            socket = new Socket(host, 8080);
            System.out.println(socket.getLocalPort());

            var input = socket.getInputStream();
            var output = socket.getOutputStream();
            output.write("Ola Mundo".getBytes(StandardCharsets.UTF_8));
            output.flush();
//            String retorno = new String(input.readAllBytes());

//            System.out.println(retorno);
//            socket.close();
//            n++;
//        }
    }
}
