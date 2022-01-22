package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.http.server.api.auth.AuthManager;

public class AuthCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Authenticate on the website!", String.format("Use '%sauth [id]' to authenticate on the website", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 1) {
            event.message_send("Expected 1 argument!");
        } else {
            String login_id = event.get_arguments()[0];

            AuthManager.instance.ack(login_id, event.get_sender_id());

            event.message_send("Done, please look back in your browser the process will continue there!");
        }
    }

    @Override
    public void on_register() {

    }
}
