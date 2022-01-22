package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestFoxApi {
    @Test
    public void test1() throws IOException, JsonSyntaxException {
        FoxApi api = new FoxApi();
        String url = api.getFox();
        Log.log(url);
    }
}
