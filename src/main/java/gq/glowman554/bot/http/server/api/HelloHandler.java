package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;

import java.util.Map;

public class HelloHandler extends HttpApiHandler {
    public HelloHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        var ref = new Object() {
            String _return = "";
        };

        query.forEach((key, value) -> {
            ref._return += key + "="+ value + "\n";
        });

        headers.forEach((key, value) -> {
            ref._return += key + "="+ value + "\n";
        });

        return ref._return;
    }
}
