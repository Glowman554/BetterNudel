package gq.glowman554.bot.utils;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPair {
    @Test
    public void test1() {
        Pair<String, String> pair = new Pair<>("hello", "world");
        Log.log(pair.toString());

        assertEquals("hello", pair.t1);
        assertEquals("world", pair.t2);
    }
}
