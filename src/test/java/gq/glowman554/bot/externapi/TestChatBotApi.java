package gq.glowman554.bot.externapi;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestChatBotApi {
    @Test
    public void test1() throws IOException, JsonSyntaxException {
        Main.load_config();
        ChatBotApi api = new ChatBotApi();

        if (api.key == null || api.bid == null) {
            Log.log("API key or bid not set skipping...");
            return;
        }

        String res = api.response("Hello", "[test]");

        Log.log(res);
    }

    @Test
    public void test2() throws IOException, JsonSyntaxException {
        Main.load_config();
        ChatBotApi api = new ChatBotApi();

        if (api.key == null || api.bid == null) {
            Log.log("API key or bid not set skipping...");
            return;
        }

        String res = api.response("Hello world", "[test]");

        Log.log(res);
    }
}
