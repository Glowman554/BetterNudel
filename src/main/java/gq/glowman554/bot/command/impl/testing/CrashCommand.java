package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class CrashCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return Testing.default_config;
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        throw new Exception("Error :o");
    }

    @Override
    public void on_register() {

    }
}
