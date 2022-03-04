package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;

import java.io.IOException;

public class CommitApi {
    public String getCommit() throws IOException {
        return HttpClient.get("http://whatthecommit.com/index.txt");
    }
}
