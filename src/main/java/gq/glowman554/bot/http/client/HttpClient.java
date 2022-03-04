package gq.glowman554.bot.http.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    public static String get(String _url, Map<String, String> headers) throws IOException {
        /*
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.setRequestProperty("Accept", "application/json");

        for (String key : headers.keySet()) {
            con.setRequestProperty(key, headers.get(key));
        }

        String response = "";

        for (byte b : con.getInputStream().readAllBytes()) {
            response += (char) b;
        }

        con.getInputStream().close();
        con.disconnect();

        return response;
         */

        OkHttpClient client = new OkHttpClient();

        var req = new Request.Builder();

        req.url(_url);

        req.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        req.addHeader("Accept", "application/json");

        for (String key : headers.keySet()) {
            req.addHeader(key, headers.get(key));
        }

        Response res = client.newCall(req.build()).execute();

        assert res.body() != null;
        return res.body().string();

    }

    public static String get(String _url) throws IOException {
        return get(_url, new HashMap<>());
    }

    public static String post(String _url, RequestBody body, Map<String, String> headers) throws IOException {
        /*
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Length", String.valueOf(body.length()));

        for (String key : headers.keySet()) {
            con.setRequestProperty(key, headers.get(key));
        }

        con.getOutputStream().write(body.getBytes());

        String response = "";

        for (byte b : con.getInputStream().readAllBytes()) {
            response += (char) b;
        }

        con.getInputStream().close();
        con.disconnect();

        return response;
         */

        OkHttpClient client = new OkHttpClient();

        var req = new Request.Builder();

        req.url(_url);
        req.method("POST", body);

        req.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        req.addHeader("Accept", "application/json");

        for (String key : headers.keySet()) {
            req.addHeader(key, headers.get(key));
        }

        Response res = client.newCall(req.build()).execute();

        assert res.body() != null;
        return res.body().string();
    }

    public static String post(String _url, RequestBody body) throws IOException {
        return post(_url, body, new HashMap<>());
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
