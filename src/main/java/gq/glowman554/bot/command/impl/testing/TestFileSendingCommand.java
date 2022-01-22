package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.http.client.HttpClient;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;

public class TestFileSendingCommand implements Command {
    private final String image_url = "https://cdn.discordapp.com/attachments/899739974436540546/934123434579984474/Bildschirmfoto_vom_2022-01-21_16-31-58.png";
    private final String audio_url = "https://cdn.discordapp.com/attachments/899739974436540546/934123617573306418/nudel_1641323504220_0.07409357198073341.ogg";
    private final String video_url = "https://cdn.discordapp.com/attachments/888034911062216714/901455032426328075/bri_ish_1.mp4";
    private final String file_url = "https://cdn.discordapp.com/attachments/899739974436540546/934124342890102824/pom.xml";

    @Override
    public CommandConfig get_config() {
        return Testing.default_config;
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        String image_path = FileUtils.randomTmpFile(FileUtils.getFileExtension(image_url));
        HttpClient.download(image_path, image_url);
        event.send_picture(new File(image_path));

        String audio_path = FileUtils.randomTmpFile(FileUtils.getFileExtension(audio_url));
        HttpClient.download(audio_path, audio_url);
        event.send_audio(new File(audio_path));

        String video_path = FileUtils.randomTmpFile(FileUtils.getFileExtension(video_url));
        HttpClient.download(video_path, video_url);
        event.send_video(new File(video_path));

        String file_path = FileUtils.randomTmpFile(FileUtils.getFileExtension(file_url));
        HttpClient.download(file_path, file_url);
        event.send_file(new File(file_path));
    }

    @Override
    public void on_register() {

    }
}
