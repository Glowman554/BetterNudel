package gq.glowman554.bot.plugin;

import gq.glowman554.bot.http.client.HttpClient;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PluginLoader {
    private final String plugin_dir;
    ArrayList<Plugin> plugins = new ArrayList<Plugin>();

    public PluginLoader(String plugin_dir) {
        this.plugin_dir = plugin_dir;

        if (!new File(this.plugin_dir).isDirectory()) {
            new File(this.plugin_dir).mkdir();
        }
    }

    public void load() throws IOException {
        Log.log("Loading plugins...");
        Files.walk(new File(plugin_dir).toPath()).forEach(this::loader);
    }

    public void load_from_url_or_path(String url_or_path) throws IOException {
        String regex = "^(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$";
        if (url_or_path.matches(regex)) {
            // URL
            String download_path = this.plugin_dir + "/" + url_or_path.substring(url_or_path.lastIndexOf("/") + 1);
            System.out.println("Downloading " + url_or_path + " to " + download_path);

            HttpClient.download(download_path, url_or_path);

            loader(new File(download_path).toPath());
        } else {
            // Path
            loader(new File(url_or_path).toPath());
        }
    }

    private void loader(Path file) {
        if (file.equals(new File(plugin_dir).toPath())) {
            return;
        }

        File f = file.toFile();
        if (!f.isFile()) {
            Log.log(String.format("[%s] Not a file!", f.getName()));
        } else if (f.getName().endsWith(".jar")) {
            Log.log(String.format("[%s] Loading plugin...", f.getName()));
            PluginJar pl = new PluginJar(f.getAbsolutePath());
            try {
                pl.init();
                Log.log(String.format("[%s] Loading %s...", f.getName(), pl.toString()));
                plugins.add(pl.instantiate());
                pl.load();

                Log.log(String.format("[%s] Loaded %s!", f.getName(), pl.toString()));
            } catch (PluginLoadException e) {
                Log.log(String.format("[%s] Failed to load plugin: %s", f.getName(), e.getMessage()));
            }
        } else {
            Log.log("Unsupported plugin format: " + FileUtils.getFileExtension(f.getAbsolutePath()));
        }
    }
}
