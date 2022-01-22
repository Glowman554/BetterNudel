package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.GoogleTtsApi;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;

public class TtsCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Sends a mp3 file of the text specified", String.format("Use '%stts [text]' to send a mp3 file of the text specified", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("No text specified");
        } else {
            String text = ArrayUtils.stringify(event.get_arguments(), " ");

            GoogleTtsApi api = new GoogleTtsApi();
            api.setLang("en");
            api.getTts(text);

            String path = FileUtils.randomTmpFile("mp3");

            api.download(path);

            event.send_picture(new File(path));
        }
    }

    @Override
    public void on_register() {

    }
}
