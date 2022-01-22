package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestGoogleTtsApi {
    @Test
    public void test1() throws IOException {
        GoogleTtsApi googleTtsApi = new GoogleTtsApi();
        String res = googleTtsApi.getTts("Hello world");
        Log.log(res);

        String path = FileUtils.randomTmpFile("mp3");
        Log.log(path);
        googleTtsApi.download(path);
    }

    @Test
    public void test2() throws IOException {
        GoogleTtsApi googleTtsApi = new GoogleTtsApi();
        googleTtsApi.setLang("de");
        String res = googleTtsApi.getTts("Hallo welt");
        Log.log(res);

        String path = FileUtils.randomTmpFile("mp3");
        Log.log(path);
        googleTtsApi.download(path);
    }
}
