package gq.glowman554.bot.config;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigManager {
    private ArrayList<ConfigProvider> configProviders = new ArrayList<>();

    public String get_key_as_str(String key) {
        for (ConfigProvider provider : configProviders) {
            Log.log(String.format("Looking for key %s in %s.", key, provider.getClass().getSimpleName()));
            if (provider.has_key(key)) {
                Log.log(String.format("Found key %s in %s it is '%s'", key, provider.getClass().getSimpleName(), provider.get_key_as_str(key)));
                return provider.get_key_as_str(key);
            }
        }

        throw new IllegalArgumentException("Key " + key + " not found!");
    }

    public int get_key_as_int(String key) {
        for (ConfigProvider provider : configProviders) {
            Log.log(String.format("Looking for key %s in %s.", key, provider.getClass().getSimpleName()));
            if (provider.has_key(key)) {
                Log.log(String.format("Found key %s in %s it is %s", key, provider.getClass().getSimpleName(), provider.get_key_as_int(key)));
                return provider.get_key_as_int(key);
            }
        }

        throw new IllegalArgumentException("Key " + key + " not found!");
    }

    public void set_key_as_int(String key, int value) {
        for (ConfigProvider provider : configProviders) {
            Log.log(String.format("Setting key %s in %s to %s", key, provider.getClass().getSimpleName(), value));
            provider.set_key_as_int(key, value);
        }
    }

    public void set_key_as_str(String key, String value) {
        for (ConfigProvider provider : configProviders) {
            Log.log(String.format("Setting key %s in %s to '%s'", key, provider.getClass().getSimpleName(), value));
            provider.set_key_as_str(key, value);
        }
    }

    public void register(ConfigProvider provider) {
        Log.log(String.format("New config provider %s!", provider.getClass().getSimpleName()));
        configProviders.add(provider);
    }

    public void sync() {
        Log.log("Syncing config providers...");
        HashMap<String, String> config = new HashMap<>();

        for (ConfigProvider provider : configProviders) {
            for (String key : provider.get_all_keys()) {
                if (config.containsKey(key)) {
                    Log.log("--- WARNING --- overwriting key " + key + " in " + provider.getClass().getSimpleName());
                }

                config.put(key, provider.get_key_as_str(key));
            }
        }

        config.forEach(this::set_key_as_str);
    }
}
