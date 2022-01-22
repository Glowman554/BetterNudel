package gq.glowman554.bot.externapi;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestCommitApi {
    @Test
    public void test1() throws IOException {
        CommitApi commitApi = new CommitApi();
        String commit = commitApi.getCommit();
        Log.log(commit);
    }
}
