package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.ArrayUtils;

public class SayCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Says a message.", String.format("Use '%ssay [what]' to say something!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("You need to specify a message to say.");
        } else {
            String message = ArrayUtils.stringify(event.get_arguments(), " ");

            event.message_send(message);
        }
    }

    @Override
    public void on_register() {

    }
}
