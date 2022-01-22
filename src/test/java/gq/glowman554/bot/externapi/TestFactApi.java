package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestFactApi {
    @Test
    public void test1() throws IOException {
        FactApi factApi = new FactApi();
        String fact = factApi.getFact();
        Log.log(fact);
    }
}
