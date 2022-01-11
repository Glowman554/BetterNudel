package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.api.VirusTotalApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.FileUtils;

import java.io.File;
import java.util.HashMap;

public class AvScanCommand implements Command {
    HashMap<String, Long> rate_limit = new HashMap<String, Long>();

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.args.length != 0 || event.event.getMessage().getAttachments().size() == 0)
        {
            event.commandFail("Invalid arguments or missing file");
        }
        else {
            if (rate_limit.containsKey(event.event.getAuthor().getId())) {
                long last_upload = rate_limit.get(event.event.getAuthor().getId());
                int rate_limit_time = 1000 * 60;
                if (System.currentTimeMillis() - last_upload < rate_limit_time) {
                    event.commandFail("Rate limit exceeded please wait " + ((rate_limit_time - (System.currentTimeMillis() - last_upload)) / 1000) + "s");
                    return;
                } else {
                    rate_limit.put(event.event.getAuthor().getId(), System.currentTimeMillis());
                }
            } else {
                rate_limit.put(event.event.getAuthor().getId(), System.currentTimeMillis());
            }

            String ret = "";


            for (int i = 0; i < event.event.getMessage().getAttachments().size(); i++) {
                String file_path = FileUtils.randomTmpFile(FileUtils.getFileExtension(event.event.getMessage().getAttachments().get(i).getFileName()));

                event.event.getMessage().getAttachments().get(i).downloadToFile(file_path);

                VirusTotalApi.SampleResult result = new VirusTotalApi().upload_sample(new File(file_path));

                ret += result.toString() + "\n";
            }

            event.commandSuccess(ret);
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public String get_short_help() {
        return "Scan a vile for possible viruses!";
    }

    @Override
    public String get_long_help() {
        return String.format("Use'%sav_scan' to scan the attached file for possible viruses!", Discord.discord.commandManager.prefix);
    }

    @Override
    public String get_permission() {
        return null;
    }
}
