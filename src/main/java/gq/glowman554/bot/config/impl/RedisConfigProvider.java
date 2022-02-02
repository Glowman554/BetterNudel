package gq.glowman554.bot.config.impl;

import java.util.HashMap;

import gq.glowman554.bot.config.ConfigProvider;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class RedisConfigProvider implements ConfigProvider {
    private Jedis jedis;
    private JedisShardInfo jedisShardInfo;

    private boolean connected = false;

	private HashMap<String, Pair<Long, String>> config_cache = new HashMap<>();
	private HashMap<String, Pair<Long, Boolean>> has_key_cache = new HashMap<>();

    private void debug_cache() {
        if (config_cache.size() != 0) {
            Log.log("config_cache: " + ArrayUtils.stringify(config_cache.keySet().toArray(new String[0]), ", "));
        }

        if (has_key_cache.size() != 0) {
            Log.log("has_key_cache: " + ArrayUtils.stringify(has_key_cache.keySet().toArray(new String[0]), ", "));
        }
    }

    public RedisConfigProvider() {
        try {
            Log.log("Connecting to redis...");
            jedisShardInfo = new JedisShardInfo(System.getenv("REDISHOST"), Integer.parseInt(System.getenv("REDISPORT")), System.getenv("REDISUSER"));
            jedisShardInfo.setPassword(System.getenv("REDISPASSWORD"));

            Log.log(jedisShardInfo.toString());

            jedis = new Jedis(jedisShardInfo);
            jedis.connect();

            connected = true;
        } catch (Exception e) {
            Log.log("Could not connect to redis!");
            Log.log(e.toString());
        }
    }

    private String load_value(String key) {
        debug_cache();

        if (config_cache.containsKey(key)) {
            Pair<Long, String> loaded_from_cache = config_cache.get(key);

            if (loaded_from_cache.t1 + 1000 * 60 * 30 < System.currentTimeMillis()) {
                Log.log("cached value for " + key + " to old!");
            } else {
                // Log.log("Returning cached value for " + key);
                return loaded_from_cache.t2;
            }
        }

        jedis.connect();

        String result = jedis.get(key);
        config_cache.put(key, new Pair<>(System.currentTimeMillis(), result));

        return result;
    }

    @Override
    public String get_key_as_str(String key) {
        return load_value(key);
    }

    @Override
    public int get_key_as_int(String key) {
        return Integer.parseInt(load_value(key));
    }

    @Override
    public void set_key_as_str(String key, String value) {
        if (!connected) {
            return;
        }

		config_cache.put(key, new Pair<>(System.currentTimeMillis(), value));

        jedis.connect();
        jedis.set(key, value);
    }

    @Override
    public void set_key_as_int(String key, int value) {
        set_key_as_str(key, String.valueOf(value));
    }

    @Override
    public boolean has_key(String key) {
        debug_cache();

        if (!connected) {
            return false;
        }

        if (has_key_cache.containsKey(key)) {
            Pair<Long, Boolean> loaded_from_cache = has_key_cache.get(key);

            if (loaded_from_cache.t1 + 1000 * 60 * 30 < System.currentTimeMillis()) {
                Log.log("cached value for " + key + " to old!");
            } else {
                // Log.log("Returning cached value for " + key);
                return loaded_from_cache.t2;
            }
        }

        jedis.connect();

        boolean result = jedis.exists(key);
        has_key_cache.put(key, new Pair<>(System.currentTimeMillis(), result));

        return result;
    }

    @Override
    public String[] get_all_keys() {
        if (!connected) {
            return new String[0];
        }

        jedis.connect();

        return jedis.keys("*").toArray(new String[0]);
    }

    @Override
    public int get_priority() {
        return 2;
    }
}
