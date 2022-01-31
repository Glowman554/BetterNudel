package gq.glowman554.bot.config.impl;

import java.util.HashMap;

import gq.glowman554.bot.config.ConfigProvider;
import gq.glowman554.bot.log.Log;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class RedisConfigProvider implements ConfigProvider {
    private Jedis jedis;
    private JedisShardInfo jedisShardInfo;

    private boolean connected = false;

	private HashMap<String, String> config_cache = new HashMap<String, String>();
	private HashMap<String, Boolean> has_key_cache = new HashMap<String, Boolean>();

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

    @Override
    public String get_key_as_str(String key) {
		if (config_cache.containsKey(key)) {
			Log.log("Returning cached value for " + key);
			return config_cache.get(key);
		}

		String result = jedis.get(key);
		config_cache.put(key, result);

        return result;
    }

    @Override
    public int get_key_as_int(String key) {
		if (config_cache.containsKey(key)) {
			Log.log("Returning cached value for " + key);
			return Integer.parseInt(config_cache.get(key));
		}

		String result = jedis.get(key);
		config_cache.put(key, result);

		return Integer.parseInt(result);
    }

    @Override
    public void set_key_as_str(String key, String value) {
        if (!connected) {
            return;
        }

		config_cache.put(key, value);

        jedis.set(key, value);
    }

    @Override
    public void set_key_as_int(String key, int value) {
        if (!connected) {
            return;
        }

		config_cache.put(key, Integer.toString(value));

        jedis.set(key, String.valueOf(value));
    }

    @Override
    public boolean has_key(String key) {
        if (!connected) {
            return false;
        }

		if (has_key_cache.containsKey(key)) {
			Log.log("Returning cached value for " + key);
			return has_key_cache.get(key);
		}

		boolean result = jedis.exists(key);
		has_key_cache.put(key, result);

        return result;
    }

    @Override
    public String[] get_all_keys() {
        if (!connected) {
            return new String[0];
        }

        return jedis.keys("*").toArray(new String[0]);
    }
}
