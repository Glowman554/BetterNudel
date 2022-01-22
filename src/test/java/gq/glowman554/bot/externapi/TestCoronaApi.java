package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCoronaApi {
    @Test
    public void test1() throws IOException, JsonSyntaxException {
        CoronaApi coronaApi = new CoronaApi();
        CoronaApi.CoronaApiResult result = coronaApi.fetchCountry("germany");

        Log.log(result.toString());
    }

    @Test
    public void test2() throws IOException, JsonSyntaxException {
        CoronaApi coronaApi = new CoronaApi();
        CoronaApi.CoronaApiResult result = coronaApi.fetchCountry("usa");

        Log.log(result.toString());
    }

    @Test
    public void test3() throws IOException, JsonSyntaxException {
        assertThrows(FileNotFoundException.class, () -> {
            CoronaApi coronaApi = new CoronaApi();
            CoronaApi.CoronaApiResult result = coronaApi.fetchCountry("invalid");

            Log.log(result.toString());
        });
    }

    @Test
    public void test4() throws IOException, JsonSyntaxException {
        Log.log("Length: " + CoronaApi.countries.length);
        for (String country : CoronaApi.countries) {
            CoronaApi coronaApi = new CoronaApi();
            CoronaApi.CoronaApiResult result = coronaApi.fetchCountry(country);

            Log.log(result.toString());
        }
    }
}
