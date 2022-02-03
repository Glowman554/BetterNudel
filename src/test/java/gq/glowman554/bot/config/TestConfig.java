package gq.glowman554.bot.config;

import gq.glowman554.bot.config.impl.FileConfigProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConfig {
    @Test
    public void test1() {
        ConfigManager configManager = new ConfigManager();

        configManager.register(new FileConfigProvider("test.json"));

        configManager.set_key_as_int("int_test", 0xc0ffe);
        configManager.set_key_as_str("str_test", "Hello world");

        Assertions.assertEquals(0xc0ffe, configManager.get_key_as_int("int_test"));
        Assertions.assertEquals("Hello world", configManager.get_key_as_str("str_test"));

        configManager.set_key_as_str("str_test_2", "Hello world");
        Assertions.assertEquals("Hello world", configManager.get_key_as_str("str_test_2"));

        configManager.set_key_as_str("str_test_2", "");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            configManager.get_key_as_str("str_test_2");
        });
    }
}
