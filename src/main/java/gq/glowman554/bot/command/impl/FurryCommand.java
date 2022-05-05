package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.externapi.FurryApi;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class FurryCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("See a random furry image", String.format("Use '%sfurry [method?]' to see something furry related!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (!(event.get_arguments().length == 0 || event.get_arguments().length == 1)) {
            event.message_send("Invalid arguments");
        } else {
            String method = "fursuit";
            FurryApi api = new FurryApi();

            if (event.get_arguments().length == 1) {
                method = event.get_arguments()[0];
            }

            method = "furry/" + method;

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
        EventManager.register(this);
    }

    @EventTarget
    public void onMessage(MessageEvent event) {
        if (event.commandEvent.get_message().startsWith(Main.commandManager.prefix)) {
            return;
        }

        if (event.commandEvent.get_message().toLowerCase().contains("fursuit") || event.commandEvent.get_message().toLowerCase().contains("furry") || event.commandEvent.get_message().toLowerCase().contains("furries")) {
            try {
                FurryApi.FurryResult result = new FurryApi().furry_fursuit();
                String tmp_file = FileUtils.randomTmpFile(FileUtils.getFileExtension(result.url));
                result.download(tmp_file);
                event.commandEvent.send_picture(new File(tmp_file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (event.commandEvent.get_message().toLowerCase().contains("hug")) {
            try {
                FurryApi.FurryResult result = new FurryApi().furry_hug();
                String tmp_file = FileUtils.randomTmpFile(FileUtils.getFileExtension(result.url));
                result.download(tmp_file);
                event.commandEvent.send_picture(new File(tmp_file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (event.commandEvent.get_sender_id().equals("542063844932190219")) {
            try {
                FurryApi.FurryResult result = new FurryApi().furry_fursuit();
                String tmp_file = FileUtils.randomTmpFile(FileUtils.getFileExtension(result.url));
                result.download(tmp_file);
                event.commandEvent.send_picture(new File(tmp_file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
