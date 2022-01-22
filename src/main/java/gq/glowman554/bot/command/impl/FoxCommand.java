package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.FoxApi;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;

public class FoxCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("See a cute fox!", String.format("Use '%sfox' to see a cute cat!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0) {
            event.message_send("Command takes no arguments");
        } else {
            FoxApi api = new FoxApi();
            api.getFox();

            String file = FileUtils.randomTmpFile(FileUtils.getFileExtension(api._url));
            api.download(file);

            event.send_picture(new File(file));
        }
    }

    @Override
    public void on_register() {

    }
}
