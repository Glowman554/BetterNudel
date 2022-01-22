package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.FurryApi;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;

public class YiffCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Yiff me daddy", String.format("Use '%syiff [method?]' to see yiff!", Main.commandManager.prefix), "im_18");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (!(event.get_arguments().length == 0 || event.get_arguments().length == 1)) {
            event.message_send("Invalid arguments");
        } else {
            String method = "straight";
            FurryApi api = new FurryApi();

            if (event.get_arguments().length == 1) {
                method = event.get_arguments()[0];
            }

            method = "furry/yiff/" + method;

            if (!ArrayUtils.contains(api._methods, method)) {
                event.message_send("Invalid method");
            } else {
                FurryApi.FurryResult result = api.random_image(method);
                String tmp_file = FileUtils.randomTmpFile(FileUtils.getFileExtension(result.url));

                result.download(tmp_file);

                event.send_picture(new File(tmp_file));
            }
        }
    }

    @Override
    public void on_register() {

    }
}
