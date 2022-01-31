package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.http.server.filehost.FileHostObject;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;

import java.io.File;
import java.util.Map;

public class ApiUploadsDeleteHandler extends HttpApiHandler {
    public ApiUploadsDeleteHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "Invalid token";
        }

        String file_id = query.get("file_id");
        if (file_id == null) {
            return "Missing file_id";
        }

        FileHostObject fileHostObject = new FileHostObject(Json.json().parse(FileUtils.readFile(FileHostObject.get_http_host_path() + "/files/" + file_id + "!!hidden!!.json")));

        if (!((!fileHostObject.getUploader().getSystem() && fileHostObject.getUploader().getId().equals(user)) || Main.commandManager.permissionManager.has_permission(user, "full_access"))) {
            return "Cannot delete file!";
        }

        new File(FileHostObject.get_http_host_path() + "/files/" + file_id + "!!hidden!!.json").delete();
        new File(FileHostObject.get_http_host_path() + "/files/" + file_id).delete();

        return "ok";
    }
}
