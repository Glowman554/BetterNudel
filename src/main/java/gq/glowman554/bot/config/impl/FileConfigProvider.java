package gq.glowman554.bot.config.impl;

import gq.glowman554.bot.config.ConfigProvider;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;

public class FileConfigProvider implements ConfigProvider {
    private final String config_file;
    private Json json = Json.json();
    private JsonNode config_json = null;

    public FileConfigProvider(String config_file) {
        this.config_file = config_file;

        Log.log(String.format("Loading config file %s!", config_file));

        if (new File(config_file).exists()) {
            try {
                config_json = json.parse(FileUtils.readFile(config_file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.log(String.format("Could not find file %s!", config_file));
        }
    }

    @Override
    public String get_key_as_str(String key) {
        return config_json.get(key).asString();
    }

    @Override
    public int get_key_as_int(String key) {
        return config_json.get(key).asInt();
    }

    @Override
    public void set_key_as_str(String key, String value) {
        if (config_json == null) {
            config_json = JsonNode.object();
        }

        config_json.set(key, value);
        save();
    }

    @Override
    public void set_key_as_int(String key, int value) {
        if (config_json == null) {
            config_json = JsonNode.object();
        }

        config_json.set(key, value);
        save();
    }

    @Override
    public boolean has_key(String key) {
        if (config_json == null) {
            return false;
        }

        return config_json.get(key) != null;
    }

    @Override
    public String[] get_all_keys() {
        if (config_json == null) {
            return new String[0];
        }

        String[] keys = new String[0];

        for (String key : config_json.keys()) {
            keys = ArrayUtils.add(keys, key);
        }

        return keys;
    }

    @Override
    public int get_priority() {
        return 1;
    }

    @Override
    public void delete_key(String key) {
        config_json.remove(key);
        save();
    }

    private void save() {
        // Log.log(String.format("Saving config file %s!", config_file));

        try {
            FileUtils.writeFile(config_file, json.serialize(config_json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
