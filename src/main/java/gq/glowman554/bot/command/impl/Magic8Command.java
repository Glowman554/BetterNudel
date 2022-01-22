package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class Magic8Command implements Command {
    private final String[] answers = new String[]{
            "No",
            "Yes",
            "Maybe",
            "Think about is a bit more then try again...",
            "Absolutely",
            "Not at all",
            "Of couse!",
            "As it seems... Yes",
            "As it seems... No",
            "Could be",
            "Hell NO!"
    };

    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Ask the magic 8 ball a question.", String.format("Use '%smagic8 [question]' to ask the magic 8 ball a question.", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("You should ask a question didn't you know?");
        } else {
            String response = answers[(int) (Math.random() * answers.length)];

            event.message_send(response);
        }
    }

    @Override
    public void on_register() {

    }
}
