package gq.glowman554.bot.config.impl;

import gq.glowman554.bot.config.ConfigProvider;
import gq.glowman554.bot.log.Log;

public class EnvConfigProvider implements ConfigProvider {

    @Override
    public String get_key_as_str(String key) {
        return System.getenv(key.toUpperCase());
    }

    @Override
    public int get_key_as_int(String key) {
        return Integer.parseInt(System.getenv(key.toUpperCase()));
    }

    @Override
    public void set_key_as_str(String key, String value) {
        Log.log("Read only");
    }

    @Override
    public void set_key_as_int(String key, int value) {
       Log.log("Read only");
    }

    @Override
    public boolean has_key(String key) {
        return System.getenv(key.toUpperCase()) != null;
    }

    @Override
    public String[] get_all_keys() {
		Log.log("--- WARNING --- ignoring get_all_keys() call!");
		return new String[0];
    }
}
