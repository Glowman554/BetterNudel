package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class CoinFlipCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Flip a coin!", String.format("Use '%scoinflip' to flip a coin!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("To many arguments");
            return;
        }

        boolean random = Math.random() < 0.5;

        if (random) {
            event.message_send("You've landed on heads!");
        } else {
            event.message_send("You've landed on tails!");
        }
    }

    @Override
    public void on_register() {

    }
}
