package gq.glowman554.bot;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;

public class TestPlugin implements Plugin {
    @Override
    public void on_load() throws Exception {
        Log.log("Hello World!");
    }
}
