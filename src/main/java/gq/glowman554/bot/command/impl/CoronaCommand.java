package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.CoronaApi;

public class CoronaCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Get the latest corona stats for a country", String.format("Use '%scorona [country]' to get the latest corona stats for a country", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 1) {
            event.message_send("Please specify one country");
        } else {
            String country = event.get_arguments()[0];

            CoronaApi api = new CoronaApi();
            CoronaApi.CoronaApiResult result = api.fetchCountry(country);

            event.message_send(result.toString());
        }
    }

    @Override
    public void on_register() {

    }
}
