package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.JokeApi;

public class JokeCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Get a random joke.", String.format("Use '%sjoke' to get a random joke.", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("This command doesn't take any arguments.");
        } else {
            JokeApi api = new JokeApi();
            event.message_send(api.getJoke());
        }
    }

    @Override
    public void on_register() {

    }
}
