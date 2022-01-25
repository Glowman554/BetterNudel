package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.http.server.filehost.FileHostObject;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class ApiUploadsHandler extends HttpApiHandler {


    public ApiUploadsHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "Invalid token";
        }

        JsonNode root = JsonNode.array();

        Files.walk(new File(FileHostObject.get_http_host_path() + "/files").toPath()).forEach(path -> {
            if (Files.isRegularFile(path) && path.toString().endsWith("!!hidden!!.json")) {
                try {
                    FileHostObject fileHostObject = new FileHostObject(Json.json().parse(FileUtils.readFile(path.toFile().getAbsolutePath())));

                    if ((!fileHostObject.getUploader().getSystem() && fileHostObject.getUploader().getId().equals(user)) || Main.commandManager.permissionManager.has_permission(user, "full_access")) {
                        root.add(fileHostObject.toJson());
                    }

                } catch (JsonSyntaxException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return Json.json().serialize(root);
    }
}
