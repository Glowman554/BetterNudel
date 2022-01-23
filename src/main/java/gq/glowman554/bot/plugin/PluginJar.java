package gq.glowman554.bot.plugin;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginJar {
    private final String jar_file;

    private URLClassLoader child;
    private Class<? extends Plugin> plugin_main_class;

    private Plugin plugin_main_class_instance;
    private JsonNode plugin_info_root;

    public PluginJar(String jar_file) {
        this.jar_file = jar_file;
    }

    public void init() throws PluginLoadException {
        try {
            this.child = new URLClassLoader(new URL[] { new URL("jar:file:" + jar_file + "!/") }, this.getClass().getClassLoader());

            InputStream plugin_info = child.getResourceAsStream("plugin.json");
            String plugin_json = FileUtils.readFile(plugin_info);

            Log.log(plugin_json);

            plugin_info_root = Json.json().parse(plugin_json);

            plugin_main_class = (Class<? extends Plugin>) child.loadClass(plugin_info_root.get("main").asString());
        } catch (Exception e) {
            throw new PluginLoadException(e);
        }
    }

    public Plugin instantiate() throws PluginLoadException {
        try {
            plugin_main_class_instance = plugin_main_class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PluginLoadException(e);
        }

        return plugin_main_class_instance;
    }

    public void load() throws PluginLoadException {
        try {
            plugin_main_class_instance.on_load();
        } catch (Exception e) {
            throw new PluginLoadException(e);
        }
    }

    @Override
    public String toString() {
        return plugin_info_root.get("name").asString() + "@" + plugin_info_root.get("version").asString();
    }
}
