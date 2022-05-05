package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.TranslateApi;
import gq.glowman554.bot.utils.ArrayUtils;

public class TranslateCommand implements Command {

    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Translate a text!", String.format("Use '%stranslate [language][text]' to translate a text!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length < 2) {
            event.message_send("Not enough arguments!");
        } else {
            String language = event.get_arguments()[0];
            String text = ArrayUtils.stringify(ArrayUtils.remove(event.get_arguments(), language), " ");

            TranslateApi api = new TranslateApi();
            String response = api.translate(text, language);

            event.message_send(response);
        }
    }

    @Override
    public void on_register() {

    }

}
