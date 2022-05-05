package gq.glowman554.bot.http.server.filehost;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHostObject {
    private final String file_id;
    private final String original_name;
    private final long upload_time;
    private final FileHostObjectUploader uploader;

    public FileHostObject(String file_id, String original_name, long upload_time, FileHostObjectUploader uploader) {
        this.file_id = file_id;
        this.original_name = original_name;
        this.upload_time = upload_time;
        this.uploader = uploader;
    }

    public FileHostObject(JsonNode root) {
        file_id = root.get("file_id").asString();
        original_name = root.get("original_name").asString();
        upload_time = root.get("upload_time").asLong();
        uploader = new FileHostObjectUploader(root.get("uploader"));
    }

    public static String get_http_host_path() {
        String http_host_path;
        try {
            http_host_path = Main.configManager.get_key_as_str("http_host_path");
        } catch (IllegalArgumentException e) {
            http_host_path = System.getProperty("user.dir") + "/host";
        }

        return http_host_path;
    }

    public static FileHostObject new_object(File file, String uploader, boolean system) throws IOException {
        String file_id = FileUtils.randomFileId(FileUtils.getFileExtension(file.getAbsolutePath()));

        String http_host_path = get_http_host_path();

        if (!new File(http_host_path + "/files/").isDirectory()) {
            Log.log("Creating " + http_host_path + "/files/...");
            new File(http_host_path + "/files/").mkdir();
        }

        Path copied = Paths.get(http_host_path + "/files/" + file_id);
        Path originalPath = file.toPath();
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

        FileHostObject object = new FileHostObject(file_id, file.getName(), System.currentTimeMillis(), new FileHostObjectUploader(uploader, system));

        Log.log(Json.json().serialize(object.toJson()));

        FileUtils.writeFile(http_host_path + "/files/" + file_id + "!!hidden!!.json", Json.json().serialize(object.toJson()));

        return object;
    }

    public JsonNode toJson() {
        JsonNode root = JsonNode.object();

        root.set("file_id", file_id);
        root.set("original_name", original_name);
        root.set("upload_time", upload_time);
        root.set("uploader", uploader.toJson());

        return root;
    }

    public String getFile_id() {
        return file_id;
    }

    public FileHostObjectUploader getUploader() {
        return uploader;
    }

    public static class FileHostObjectUploader {
        private final String id;
        private final boolean system;

        public FileHostObjectUploader(String id, boolean system) {
            this.id = id;
            this.system = system;
        }

        public FileHostObjectUploader(JsonNode root) {
            id = root.get("id").asString();
            system = root.get("system").asBoolean();
        }

        public JsonNode toJson() {
            JsonNode root = JsonNode.object();

            root.set("id", id);
            root.set("system", system);

            return root;
        }

        public String getId() {
            return id;
        }

        public boolean getSystem() {
            return system;
        }
    }
}
