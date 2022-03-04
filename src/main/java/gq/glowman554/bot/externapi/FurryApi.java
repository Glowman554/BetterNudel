package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class FurryApi {

    public String[] _methods = {
            "animals/birb",
            "animals/blep",
            "furry/boop",
            "furry/cuddle",
            "furry/flop",
            "furry/fursuit",
            "furry/hold",
            "furry/howl",
            "furry/hug",
            "furry/kiss",
            "furry/lick",
            "furry/propose",
            "furry/bulge",
            "furry/yiff/gay",
            "furry/yiff/straight",
            "furry/yiff/lesbian",
            "furry/yiff/gynomorph"
    };

    public FurryResult random_image(String endpoint) throws IOException, JsonSyntaxException {
        String response = HttpClient.get(String.format("https://v2.yiff.rest/%s?limit=1&notes=disabled", endpoint));

        Json json = Json.json();
        JsonNode root = json.parse(response);
        String url = root.get("images").get(0).get("yiffMediaURL").asString();

        return new FurryResult(url);
    }

    public FurryResult animal_birb() throws IOException, JsonSyntaxException {
        return random_image("animals/birb");
    }

    public FurryResult animal_blep() throws IOException, JsonSyntaxException {
        return random_image("animals/blep");
    }

    public FurryResult furry_boop() throws IOException, JsonSyntaxException {
        return random_image("furry/boop");
    }

    public FurryResult furry_cuddle() throws IOException, JsonSyntaxException {
        return random_image("furry/cuddle");
    }

    public FurryResult furry_flop() throws IOException, JsonSyntaxException {
        return random_image("furry/flop");
    }

    public FurryResult furry_fursuit() throws IOException, JsonSyntaxException {
        return random_image("furry/fursuit");
    }

    public FurryResult furry_hold() throws IOException, JsonSyntaxException {
        return random_image("furry/hold");
    }

    public FurryResult furry_howl() throws IOException, JsonSyntaxException {
        return random_image("furry/howl");
    }

    public FurryResult furry_hug() throws IOException, JsonSyntaxException {
        return random_image("furry/hug");
    }

    public FurryResult furry_kiss() throws IOException, JsonSyntaxException {
        return random_image("furry/kiss");
    }

    public FurryResult furry_lick() throws IOException, JsonSyntaxException {
        return random_image("furry/lick");
    }

    public FurryResult furry_propose() throws IOException, JsonSyntaxException {
        return random_image("furry/propose");
    }

    public FurryResult furry_bulge() throws IOException, JsonSyntaxException {
        return random_image("furry/bulge");
    }

    public FurryResult furry_yiff_gay() throws IOException, JsonSyntaxException {
        return random_image("furry/yiff/gay");
    }

    public FurryResult furry_yiff_straight() throws IOException, JsonSyntaxException {
        return random_image("furry/yiff/straight");
    }

    public FurryResult furry_yiff_lesbian() throws IOException, JsonSyntaxException {
        return random_image("furry/yiff/lesbian");
    }

    public FurryResult furry_yiff_gynomorph() throws IOException, JsonSyntaxException {
        return random_image("furry/yiff/gynomorph");
    }

    public static class FurryResult {
        public String url;

        public FurryResult(String url) {
            this.url = url;
        }

        public void download(String path) throws IOException {
            HttpClient.download(path, url);
        }
    }
}
