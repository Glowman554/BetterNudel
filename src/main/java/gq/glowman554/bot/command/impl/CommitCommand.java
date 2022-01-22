package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.CommitApi;

public class CommitCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Get a random commit message!", String.format("Use '%scommit' to get a random commit message!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("Command takes no arguments");
        } else {
            CommitApi api = new CommitApi();
            event.message_send(api.getCommit());
        }
    }

    @Override
    public void on_register() {

    }
}
