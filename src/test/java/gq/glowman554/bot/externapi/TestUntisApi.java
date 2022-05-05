package gq.glowman554.bot.externapi;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestUntisApi {
    @Test
    public void test1() {
        Main.load_config();

        if (!new File("webuntis_module/node_modules").exists()) {
            Log.log("Skipping test");
            return;
        }

        try {
            String[] users = Main.configManager.get_key_as_str("untis_cfg").split(";");
            Log.log(new UntisApi(users).toString());

        } catch (IllegalArgumentException e) {
            Log.log("Skipping test");
        }
    }
}
