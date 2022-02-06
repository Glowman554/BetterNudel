package gq.glowman554.bot;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;

public class RainbowRolePlugin implements Plugin {

    @Override
    public void on_load() throws Exception {
        Log.log("Hello World!");

		RainbowRoleManager.init();

		Main.commandManager.add_command("rainbow", new RainbowColeCommand());
    }
}
