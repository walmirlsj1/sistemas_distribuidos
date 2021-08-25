package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Locale;

public class HttpResponse {
    private String path;
    private final Socket client;

    public HttpResponse(Socket client, String path) {
        this.client = client;
        this.path = path;

        if (path.length() < 2) this.path = "/index.html";
        else decodePath();
    }

    private void decodePath() {
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        }
    }

//    public void checkPath() throws HttpException {
//        if (getClass().getResource(path) == null) throw new HttpNotFound(client);
//        else processResponseFile(path);
//    }

    protected void processResponseFile() throws HttpException {
        if (getClass().getResource(path) == null) throw new HttpNotFound(client);
        try {
            var output = new DataOutputStream(client.getOutputStream());

            output.writeBytes("HTTP/1.1 200 OK\n");
            output.writeBytes("Content-type: " + getMimeType(path) + "\n");
            output.writeBytes("Connection: close\n");
            output.writeByte('\n'); //fim do cabeçalho

            sendFile(output, path);

            output.flush();
            client.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new HttpInternalServerError(client);
        }
    }

    private void sendFile(DataOutputStream output, String file) {

        try (InputStream fileStream = getClass().getResourceAsStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead = -1;

            while ((bytesRead = fileStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String getMimeType(String file_required) {
        String file_ext = file_required.substring(file_required.lastIndexOf(".") + 1, file_required.length());

        file_ext = file_ext.toLowerCase(Locale.ROOT);

        if (file_ext.equals("html")) return "text/html";
        if (file_ext.equals("js"))   return "application/javascript";
        if (file_ext.equals("css"))  return "text/css";
        if (file_ext.equals("png"))  return "image/png";
        if (file_ext.equals("gif"))  return "image/gif";
        if (file_ext.equals("jpg"))  return "image/jpeg";

        return "text/plain";
    }

}
