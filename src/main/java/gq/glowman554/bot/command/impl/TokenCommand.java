package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.http.server.api.auth.AuthManager;

public class TokenCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Create tokens!", String.format("Use '%stoken [user]' to create tokens!", Main.commandManager.prefix), "token");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 1) {
            event.message_send("Command takes 1 argument!");
        } else {
            String user = event.get_arguments()[0];
            String token = AuthManager.instance.random_token();
            AuthManager.instance.add_token(user, token);

            event.message_send(String.format("The new token for the user %s is %s!", user, token));
        }
    }

    @Override
    public void on_register() {

    }
}
