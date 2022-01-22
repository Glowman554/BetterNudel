package gq.glowman554.bot.http.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public static String request(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.setRequestProperty("Accept", "application/json");

        String response = "";

        for (byte b : con.getInputStream().readAllBytes()) {
            response += (char) b;
        }

        con.getInputStream().close();
        con.disconnect();

        return response;
    }

    public static void download(String path, String _url) throws IOException {
        URL url = new URL(_url);
        File destination = new File(path);
        BufferedInputStream in = new BufferedInputStream(url.openStream());

        FileOutputStream fileOutputStream = new FileOutputStream(destination);

        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }

        in.close();
        fileOutputStream.close();
    }
}
