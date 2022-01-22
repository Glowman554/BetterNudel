package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class PingCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Ping the bot!", String.format("Use '%sping' to ping the bot!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("Command doesn't take arguments");
        } else {
            event.message_send("Pong!");
        }
    }

    @Override
    public void on_register() {

    }
}
