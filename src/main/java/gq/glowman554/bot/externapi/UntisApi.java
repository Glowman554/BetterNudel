package gq.glowman554.bot.externapi;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.ExecutionEngine;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class UntisApi {
    private final String[] users;

    public UntisApi(String[] users) {
        this.users = users;
    }

    @Override
    public String toString() {
        File random_tmp_file = new File(FileUtils.randomTmpFile("txt"));

        try {
            new ExecutionEngine(false).execute("node webuntis_module/index.js " + random_tmp_file.getAbsolutePath() + " " + ArrayUtils.stringify(users, " "));

            return FileUtils.readFile(random_tmp_file.getAbsolutePath());
        } catch (ExecutionEngine.ExecutionEngineError | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start_edit_loop() throws InterruptedException {
        String untis_channel = Main.configManager.get_key_as_str("untis_channel");
        try {
            Main.configManager.get_key_as_str("untis_msg");
        } catch (IllegalArgumentException e) {
            String message_id = Main.discordPlatform.jda.getTextChannelById(untis_channel).sendMessage("Please wait...").complete().getId();
            Main.configManager.set_key_as_str("untis_msg", message_id);
        }

        var untis_message = Main.discordPlatform.jda.getTextChannelById(untis_channel).retrieveMessageById(Main.configManager.get_key_as_str("untis_msg")).complete();

        while (true) {
            untis_message.editMessage("Fetching data...").queue();
            untis_message.editMessage(toString()).queue();

            Thread.sleep(1000 * 60 * 60);
        }
    }
}
