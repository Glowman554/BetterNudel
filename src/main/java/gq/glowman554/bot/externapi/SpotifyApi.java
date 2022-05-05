package gq.glowman554.bot.externapi;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.client.HttpClient;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.MultiThreadHelper;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;
import net.shadew.json.JsonType;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SpotifyApi {
    public final String client_id;
    private final String client_secret;
    private String auth_token = null;
    private String refresh_token = null;

    public SpotifyApi(String client_id, String client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;

        try {
            String cfg = Main.configManager.get_key_as_str("spotify");

            JsonNode root = Json.json().parse(cfg);

            auth_token = root.get("access_token").asString();
            refresh_token = root.get("refresh_token").asString();

            refresh_token();
        } catch (IllegalArgumentException | IOException e) {
            Log.log("Could not load spotify config...");
            Log.log("Please open /spotify/login to start the login process...");
            login_start();
        }
    }

    private void refresh_token() throws IOException, JsonSyntaxException {
        Log.log("Running spotify refresh...");
        String authorization = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());

        HashMap<String, String> headers_ = new HashMap<>();
        headers_.put("Authorization", "Basic " + authorization);

        RequestBody body = new FormBody.Builder().add("grant_type", "refresh_token").add("refresh_token", refresh_token).build();

        String response = HttpClient.post("https://accounts.spotify.com/api/token", body, headers_);

        JsonNode root = Json.json().parse(response);
        JsonNode old_root = Json.json().parse(Main.configManager.get_key_as_str("spotify"));

        auth_token = root.get("access_token").asString();
        old_root.set("access_token", root.get("access_token").asString());

        Main.configManager.set_key_as_str("spotify", old_root.toString());
    }

    private void login_start() {
        new SpotifyLoginHandler(HttpApi.instance, "/spotify/login", this);
        new SpotifyCallbackHandler(HttpApi.instance, "/spotify/callback", this);
    }

    private JsonNode call_spotify(String url) throws JsonSyntaxException, IOException {
        refresh_token();

        HashMap<String, String> headers_ = new HashMap<>();
        headers_.put("Authorization", "Bearer " + auth_token);

        return Json.json().parse(HttpClient.get(url, headers_));
    }

    public SpotifyTrack[] search(String query, int max) throws JsonSyntaxException, IOException {
        JsonNode res = call_spotify(String.format("https://api.spotify.com/v1/search?q=%s&type=track&limit=%d", URLEncoder.encode(query), max));

        ArrayList<SpotifyTrack> tracks = new ArrayList<>();

        for (JsonNode node : res.get("tracks").get("items")) {
            if (node.get("preview_url") != null && node.get("preview_url").type() != JsonType.NULL) {
                tracks.add(new SpotifyTrack(node.get("name").asString(), node.get("preview_url").asString(), node.get("external_urls").get("spotify").asString()));
            }
        }

        return tracks.toArray(new SpotifyTrack[0]);
    }

    public static class SpotifyTrack {
        public final String name;
        public final String preview_url;
        public final String external_url;

        public SpotifyTrack(String name, String preview_url, String external_url) {
            this.name = name;
            this.preview_url = preview_url;
            this.external_url = external_url;
        }

        @Override
        public String toString() {
            return "SpotifyTrack{" +
                    "name='" + name + '\'' +
                    ", preview_url='" + preview_url + '\'' +
                    ", external_url='" + external_url + '\'' +
                    '}';
        }
    }

    public static class SpotifyLoginHandler extends HttpApiHandler {
        private final SpotifyApi spotify_api;

        public SpotifyLoginHandler(HttpApi api, String path, SpotifyApi spotify_api) {
            super(api, path);
            this.spotify_api = spotify_api;
        }

        @Override
        public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
            return String.format("<a href=\"https://accounts.spotify.com/authorize?response_type=code&client_id=%s&redirect_uri=http://localhost:%d/spotify/callback\">Login</a>", spotify_api.client_id, HttpApi.port);
        }
    }

    public static class SpotifyCallbackHandler extends HttpApiHandler {
        private final SpotifyApi spotify_api;

        public SpotifyCallbackHandler(HttpApi api, String path, SpotifyApi spotify_api) {
            super(api, path);
            this.spotify_api = spotify_api;
        }

        @Override
        public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
            String authorization = Base64.getEncoder().encodeToString((spotify_api.client_id + ":" + spotify_api.client_secret).getBytes());

            HashMap<String, String> headers_ = new HashMap<>();
            headers_.put("Authorization", "Basic " + authorization);

            RequestBody body = new FormBody.Builder().add("grant_type", "authorization_code").add("code", query.get("code")).add("redirect_uri", String.format("http://localhost:%d/spotify/callback", HttpApi.port)).build();

            String response = HttpClient.post("https://accounts.spotify.com/api/token", body, headers_);

            Main.configManager.set_key_as_str("spotify", response);

            MultiThreadHelper.run(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            });

            return "Big success!";
        }
    }
}
