package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.log.Log;

import java.util.Map;

public class ApiOctoPrintHandler extends HttpApiHandler {
    public ApiOctoPrintHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "Invalid token";
        }

        switch (query.get("event")) {
            case "state":
                if (query.get("payload").equals("OPERATIONAL")) {
                    Main.discordPlatform.setDefaultRP();
                } else {
                    Log.log("wtf is the " + query.get("payload") + " state!");
                }
                break;

            case "progress":
                String[] data = query.get("payload").split(":");
                String rp = String.format("Printing %s (%s%%) in 3d!", data[0], data[1]);
                Main.discordPlatform.setRP(rp);
                break;

            default:
                Log.log("wtf is " + query.get("event") + "!");
                return "wtf is " + query.get("event") + "!";
        }

        return "ok";
    }
}
