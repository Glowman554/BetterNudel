package gq.glowman554.utils;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;

public class UploadUtils {
    public static String upload(File f) throws IOException, InterruptedException {
        String command = String.format("curl -F file=@%s https://api.anonfiles.com/upload", f.getAbsolutePath());

        Json json = Json.json();
        JsonNode root = json.parse(ExecUtils.exec(command));

        return root.get("data").get("file").get("url").get("full").asString();
    }
}
