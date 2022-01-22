package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.http.server.filehost.FileHostObject;
import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;

import java.io.File;
import java.util.HashMap;

public class UploadCommand implements Command {
    private String http_host_url;

    HashMap<String, Long> rate_limit = new HashMap<String, Long>();

    private void load_host_url() {
        try {
            http_host_url = Main.configManager.get_key_as_str("http_host_url");
        } catch (IllegalArgumentException e) {
            Log.log("Could not load http_host_url! Using default http_host_url!");
            http_host_url = "http://localhost:8888/";
        }
    }

    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Uploads a file to the server", String.format("Use '%supload' to upload the attached files!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0 || event.get_num_files() == 0) {
            event.message_send("Invalid arguments or missing file");
        } else {
            if (rate_limit.containsKey(event.get_sender_id())) {
                long last_upload = rate_limit.get(event.get_sender_id());
                int rate_limit_time = 1000 * 60;
                if (System.currentTimeMillis() - last_upload < rate_limit_time) {
                    event.message_send("Rate limit exceeded please wait " + ((rate_limit_time - (System.currentTimeMillis() - last_upload)) / 1000) + "s");
                    return;
                } else {
                    rate_limit.put(event.get_sender_id(), System.currentTimeMillis());
                }
            } else {
                rate_limit.put(event.get_sender_id(), System.currentTimeMillis());
            }

            String ret = String.format("Uploaded %s files to:\n", event.get_num_files());

            Json _json = Json.json();

            for (int i = 0; i < event.get_num_files(); i++) {
                File downloaded_file = new File(event.get_file(i));

                FileHostObject object = FileHostObject.new_object(downloaded_file, event.get_sender_id(), false);

                ret += http_host_url + "/files/" + object.getFile_id() + "\n";
            }

            event.message_send(ret);
        }
    }

    @Override
    public void on_register() {
        load_host_url();
    }
}
