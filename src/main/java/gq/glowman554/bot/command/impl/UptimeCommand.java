package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.TimeUtils;

public class UptimeCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("See how long the bot is online!", String.format("Use '%suptime' to see how long the bit is online!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("Command takes no arguments!");
        } else {
            event.message_send(TimeUtils.millisecond_to_dhms(System.currentTimeMillis() - Main.startTime));
        }
    }

    @Override
    public void on_register() {

    }
}
