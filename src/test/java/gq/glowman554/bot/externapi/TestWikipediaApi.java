package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestWikipediaApi {
    @Test
    public void test1() throws IOException, JsonSyntaxException {
        WikipediaApi api = new WikipediaApi();
        String res = api.searchWiki("CPU");
        Log.log(res);
    }

    @Test
    public void test2() throws JsonSyntaxException, IOException {
        WikipediaApi api = new WikipediaApi();
        String res = api.searchWiki("world war 2");
        Log.log(res);
    }
}
