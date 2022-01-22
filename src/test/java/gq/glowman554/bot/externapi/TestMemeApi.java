package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestMemeApi {
    @Test
    public void test1() throws IOException, JsonSyntaxException {
        MemeApi api = new MemeApi();
        String url = api.getMeme();
        Log.log(url);
    }
}
