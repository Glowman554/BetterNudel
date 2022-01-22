package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class TestMessageDeleteCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return Testing.default_config;
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        event.message_delete();
    }

    @Override
    public void on_register() {

    }
}
