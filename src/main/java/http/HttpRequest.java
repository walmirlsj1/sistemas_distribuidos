package http;

import java.io.*;
import java.net.Socket;

public class HttpRequest implements Runnable {
    Socket client;

    public HttpRequest(Socket client) {
        //INPUT
        this.client = client;

    }

    private void process() throws HttpException {
        System.out.printf("Processando requisição do cliente: %s\n", client.getInetAddress().getHostAddress());

        StringBuilder request = new StringBuilder();
        try {
            var input = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            do {
                request.append(input.readLine());
                request.append("\t\t");
            } while (input.ready());
        } catch (Exception e) {
            System.err.println(e.getMessage());
//            httpBadRequest();
            throw new HttpBadRequest(client);
        }


        String importante = request.substring(0, request.indexOf("\t\t"));

        String[] req = importante.split(" ");
//        String file_req = "";

        if (req.length != 3) throw new HttpBadRequest(client);
        else if (!"HTTP/1.1".equals(req[2])) throw new HttpVesionNotSupported(client); //Exception
        else if (!"GET".equals(req[0])) throw new HttpMethodUnauthorized(client); //Exception
        else {
            try {
                HttpResponse response = new HttpResponse(client, req[1]);
                response.processResponseFile();
                client.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar conexão com cliente.");
            }
        }
    }


    @Override
    public void run() {
        try {
            this.process();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
