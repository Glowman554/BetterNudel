package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.externapi.SpotifyApi;
import gq.glowman554.bot.http.client.HttpClient;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.FileUtils;

import java.io.File;

public class SpotifyCommand implements Command {
    private SpotifyApi spotifyApi;

    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Search spotify for songs!", String.format("Use '%sspotify [query]' to get a list of songs and small previews of them!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length < 1) {
            event.message_send("Please specify a query to search");
        } else {
            String query = ArrayUtils.stringify(event.get_arguments(), " ");

            SpotifyApi.SpotifyTrack[] res = spotifyApi.search(query, 5);

            for (SpotifyApi.SpotifyTrack track : res) {
                String path = FileUtils.randomTmpFile("mp3");
                HttpClient.download(path, track.preview_url);

                event.message_send("Maybe you like " + track.name + "\n" + track.external_url);
                event.send_audio(new File(path));
            }
        }
    }

    @Override
    public void on_register() {
        String client_id = Main.configManager.get_key_as_str("spotify_client_id");
        String client_secret = Main.configManager.get_key_as_str("spotify_client_secret");

        spotifyApi = new SpotifyApi(client_id, client_secret);
    }
}
