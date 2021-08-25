package teste;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // TCP
    Server() throws IOException {
        var server = new ServerSocket(9090);
        while (true) {
            var cliente = server.accept();
            new Thread(()->this.atenderCliente(cliente)).start();
        }
//        server.close();
    }
    public static void main(String[] args) throws IOException {
        new Server();
    }

    public void atenderCliente(Socket cliente){

        try{
            //inicio
            System.out.println(cliente);
            var input = cliente.getInputStream();
            var msg = input.readAllBytes();

            System.out.println(new String( msg ));
            cliente.close();
            //fim do atendimento
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
