
package gq.glowman554;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    public Http() {
    }

    public static String request(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.setRequestProperty("Accept", "application/json");
        String response = "";

        int curr;
        for(InputStream input = con.getInputStream(); (curr = input.read()) != -1; response = response + (char)curr) {
        }

        con.getInputStream().close();
        con.disconnect();
        return response;
    }
}
