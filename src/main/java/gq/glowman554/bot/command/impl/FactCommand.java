package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.FactApi;

public class FactCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Get a random fact.", String.format("Use '%sfact' to get a random fact.", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("This command does not take any arguments.");
        } else {
            FactApi api = new FactApi();
            event.message_send(api.getFact());
        }
    }

    @Override
    public void on_register() {

    }
}
