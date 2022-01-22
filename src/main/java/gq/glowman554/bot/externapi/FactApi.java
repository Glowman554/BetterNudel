package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;

import java.io.IOException;

public class FactApi {
    public String getFact() throws IOException {
        String fact = HttpClient.request("https://uselessfacts.jsph.pl/random.txt?language=en").split("\n")[0];

        if (fact.startsWith("> ")) {
            fact = fact.substring(2);
        }

        return fact;
    }
}
