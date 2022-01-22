package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestIpInfoApi {
    @Test
    public void test1() throws IOException, JsonSyntaxException {
        IpInfoApi.IpInfo i = new IpInfoApi().request_info("8.8.8.8");
        Log.log(i.toString());

        Json json = Json.json();
        Log.log(json.serialize(i.toJson()));
    }
}
