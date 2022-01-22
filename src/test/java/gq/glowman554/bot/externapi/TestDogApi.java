package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestDogApi {
    @Test
    public void test1() throws JsonSyntaxException, IOException {
        DogApi dogApi = new DogApi();

        Log.log(dogApi.getDog());
    }
}
