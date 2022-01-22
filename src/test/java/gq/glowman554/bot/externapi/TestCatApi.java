package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestCatApi {
    @Test
    public void test1() throws JsonSyntaxException, IOException {
        CatApi catApi = new CatApi();

        Log.log(catApi.getCat());
    }
}
