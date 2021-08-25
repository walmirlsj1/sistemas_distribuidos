package http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int MAX_THREAD = 50;
    private int port, numThreads = 10;
    protected ExecutorService threadPool;

    public Server(int port, int numThreads) {
        this.port = port;

        if (numThreads <= MAX_THREAD) this.numThreads = numThreads;

        threadPool = Executors.newFixedThreadPool(this.numThreads);
    }

    public void start() throws Exception {
        ServerSocket socketServidor = null;
        try {
            socketServidor = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(String.format("start: Falha ao criar socket na porta: %d ", this.port));
        }
        System.out.println(" ::::::: PORT: " + this.port + " ::::::: ");

        System.out.println("Aguardando clientes");

        while (!socketServidor.isClosed()) {
            Socket client = null;

            try {
                client = socketServidor.accept();
            } catch (IOException e) {
                throw new RuntimeException("start: Falha ao aceitar conexão do cliente");
            }

            /***
             * Abaixo trata a requisição
             */
            this.threadPool.execute(new HttpRequest(client));
//            var sckt = client.accept(); //espera por um cliente
//            System.out.println(sckt);

        }
        socketServidor.close();
    }

    public static void main(String[] args) throws Exception {
        var server = new Server(8082, 2);
        server.start();

    }
}
