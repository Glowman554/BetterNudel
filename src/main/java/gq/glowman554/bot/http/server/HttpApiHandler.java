package gq.glowman554.bot.http.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpApiHandler {
    public String http_host_path;

    public HttpApiHandler(HttpApi api, String path) {
        HttpContext context = api.server.createContext(path);
        context.setHandler(this::handleRequest);

        Log.log(String.format("[%s] Registered handler!", path));
        api.handlers.put(path, this);

        try {
            http_host_path = Main.configManager.get_key_as_str("http_host_path");
        } catch (IllegalArgumentException e) {
            http_host_path = System.getProperty("user.dir") + "/host";
        }
    }

    private Map<String, String> query_to_map(String query) {
        if (query == null) {
            return new HashMap<>();
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        Log.log("Request: " + exchange.getRequestMethod() + " " + exchange.getRequestURI() + " " + exchange.getProtocol() + " " + exchange.getRequestHeaders().getFirst("X-Forwarded-For") + " " + exchange.getRemoteAddress().getAddress().getHostAddress());

        String request_uri = http_host_path + exchange.getRequestURI().toString();

        if (new File(request_uri).isDirectory()) {
            if (new File(request_uri + "/index.html").exists()) {
                Log.log("Adding index.html to request");
                request_uri += "/index.html";
            }
        }

        if (new File(request_uri).exists() && new File(request_uri).isFile()) {
            if (request_uri.contains("!!hidden!!")) {
                Log.log("Requested file is hidden!!");
                exchange.sendResponseHeaders(403, 0);
                exchange.getResponseBody().write("403 Forbidden".getBytes());
                exchange.getResponseBody().close();
                return;
            } else {
                Log.log(String.format("Sending file: %s\n", request_uri));
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, 0);
                InputStream s = new File(request_uri).toURI().toURL().openStream();
                exchange.getResponseBody().write(s.readAllBytes());
                s.close();
                exchange.getResponseBody().close();
                exchange.close();
                return;
            }
        }

        Map<String, String> query = query_to_map(exchange.getRequestURI().getQuery());

        try {
            Map<String, String> headers = new HashMap<>();
            exchange.getRequestHeaders().forEach((key, header) -> {
                headers.put(key.toLowerCase(), header.get(0));
            });

            String respone = execute(query, headers);

            if (respone != null) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, respone.length());
                exchange.getResponseBody().write(respone.getBytes());
            } else {
                exchange.sendResponseHeaders(404, 0);
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, e.getMessage().length());
            exchange.getResponseBody().write(e.getMessage().getBytes());
        }

        exchange.close();
    }

    public abstract String execute(Map<String, String> query, Map<String, String> headers) throws Exception;
}
