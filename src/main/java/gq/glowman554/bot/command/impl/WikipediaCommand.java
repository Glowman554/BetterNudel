package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.WikipediaApi;
import gq.glowman554.bot.utils.ArrayUtils;

public class WikipediaCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Searches Wikipedia for a given query", String.format("Use '%swikipedia [query]' to search Wikipedia for a given query", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("Invalid arguments");
        } else {
            WikipediaApi api = new WikipediaApi();

            event.message_send(api.searchWiki(ArrayUtils.stringify(event.get_arguments(), " ")));
        }
    }

    @Override
    public void on_register() {

    }
}
