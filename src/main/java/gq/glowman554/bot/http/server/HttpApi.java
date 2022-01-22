package gq.glowman554.bot.http.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.api.HelloHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.http.server.api.legacy.ApiCollectHandler;
import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class HttpApi {

    public HttpServer server;
    public HttpContext context;

    public static HttpApi instance;
    public static int port;

    public HashMap<String, HttpApiHandler> handlers = new HashMap<String, HttpApiHandler>();

    public HttpApi(int port) throws IOException {
        Log.log(String.format("Listening on port %d!", port));

        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        server.start();
    }

    public static void load() throws IOException, JsonSyntaxException {
        Log.log("Starting http api...");

        try {
            port = Main.configManager.get_key_as_int("http_port");
        } catch (IllegalArgumentException e) {
            Log.log("Using default port!");
            port = 8888;
        }

        instance = new HttpApi(port);

        new HelloHandler(instance, "/hello");

        // -------------- legacy api -----------------------
        new ApiCollectHandler(instance, "/api/collect");
        new ApiCollectHandler(instance, "/api/science");

        AuthManager.load(instance);
    }
}
