package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class Im18Command implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Gives the 18+ role to the user", String.format("Use '%sim_18' to get the 18+ role", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("This command takes no arguments!");
        } else {
            Main.commandManager.permissionManager.add_permission(event.get_sender_id(), "im_18");

            event.message_send("You are now in the 18+ role!");
        }
    }

    @Override
    public void on_register() {

    }
}
