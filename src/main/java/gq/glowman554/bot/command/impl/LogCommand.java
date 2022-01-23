package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.log.Log;

import java.io.File;

public class LogCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Get the current log file!", String.format("Use '%slog' to get the current log file!.", Main.commandManager.prefix), "log");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("Command takes no arguments!");
        } else {
            event.send_file(new File(Log.getCurrent_log_file()));
        }
    }

    @Override
    public void on_register() {

    }
}
