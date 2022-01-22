package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestFurryApi {
    @Test
    public void test1() throws JsonSyntaxException, IOException {
        FurryApi api = new FurryApi();

        String result = api.furry_fursuit().url;

        Log.log(result);
    }
}
