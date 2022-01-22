package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.command.impl.testing.Testing;
import gq.glowman554.bot.log.Log;

public class SetTestingCommand implements Command {
    boolean already_loaded = false;

    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Set testing mode!", String.format("Use '%sset_testing [mode]' to set the testing mode!", Main.commandManager.prefix), "testing");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 1) {
            event.message_send("To much or not enough arguments!");
        } else {
            String testing_mode = event.get_arguments()[0];

            Main.configManager.set_key_as_str("testing", testing_mode);

            if (testing_mode.equals("true")) {
                if (!already_loaded) {
                    Testing.load();
                    already_loaded = true;
                }

                event.message_send("Testing mode is now " + testing_mode);
            } else {

                event.message_send("Testing mode is now " + testing_mode + (already_loaded ? "\nThis needs an restart to apply!" : ""));
            }
        }
    }

    @Override
    public void on_register() {
        try {
            if (Main.configManager.get_key_as_str("testing").equals("true")) {
                Testing.load();
                already_loaded = true;
            }
        } catch (IllegalArgumentException e) {
            Log.log("Could not find testing config ignoring...");
        }
    }
}
