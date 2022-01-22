package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.DogApi;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;

public class DogCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("See a cute dog!", String.format("Use '%sdog' to see a cute dog!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("Command takes no arguments");
        } else {
            DogApi api = new DogApi();
            api.getDog();

            String file = FileUtils.randomTmpFile(FileUtils.getFileExtension(api._url));
            api.download(file);

            event.send_picture(new File(file));
        }
    }

    @Override
    public void on_register() {

    }
}
