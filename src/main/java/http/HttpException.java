package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HttpException extends Exception {

    HttpException(Socket client, String exceptionMessage) {
        try {
            var output = new DataOutputStream(client.getOutputStream());
            output.writeBytes("HTTP/1.1 " + exceptionMessage + "\n");
            output.writeBytes("Connection: close\n");
            output.writeByte('\n'); //fim do cabeçalho
            client.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
//    private void httpInternalServerError() {
//        HttpException("500 Internal Server Error");
//    }
//
//    private void httpBadRequest() {
//        HttpException("400 Bad Request");
//    }
//
//    private void httpNotFound() {
//        HttpException("404 Not Found");
//    }
//
//    private void HttpMethodUnauthorized() {
//        HttpException("401 Unauthorized");
//    }
//
//    private void HttpVesionNotSupported() {
//        HttpException("505 HTTP Version Not Supported");
//    }

//    private void HttpException(String code_msg) {
//        try {
//            var output = new DataOutputStream(client.getOutputStream());
//            output.writeBytes("HTTP/1.1 " + code_msg + "\n");
//            output.writeBytes("Connection: close\n");
//            output.writeByte('\n'); //fim do cabeçalho
//            client.close();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//    }
}

class HttpMethodUnauthorized extends HttpException {
    HttpMethodUnauthorized(Socket client) {
        super(client, "401 Unauthorized");
    }
}

class HttpNotFound extends HttpException {
    HttpNotFound(Socket client) {
        super(client, "404 Not Found");
    }
}

class HttpVesionNotSupported extends HttpException {
    HttpVesionNotSupported(Socket client) {
        super(client, "505 HTTP Version Not Supported");
    }
}
class HttpBadRequest extends HttpException {
    HttpBadRequest(Socket client) {
        super(client, "400 Bad Request");
    }
}

class HttpInternalServerError extends HttpException {
    HttpInternalServerError(Socket client) {
        super(client, "500 Internal Server Error");
    }
}
