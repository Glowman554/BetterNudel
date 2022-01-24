package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class RollCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Roll a dice", String.format("Use '%sroll [num_sites?]' to roll a dice!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (!(event.get_arguments().length == 0 || event.get_arguments().length == 1)) {
            event.message_send("Expected 0 or 1 arguments!");
        } else {
            int num_sides = 6;

            if (event.get_arguments().length == 1) {
                num_sides = Integer.parseInt(event.get_arguments()[0]);
            }

            event.message_quote("You rolled a " + ((int) (Math.random() * num_sides) + 1));
        }
    }

    @Override
    public void on_register() {

    }
}
