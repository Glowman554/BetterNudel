package gq.glowman554.bot.config;

public interface ConfigProvider {
    String get_key_as_str(String key);
    int get_key_as_int(String key);

    void set_key_as_str(String key, String value);
    void set_key_as_int(String key, int value);

    boolean has_key(String key);

    String[] get_all_keys();

    int get_priority();
}
