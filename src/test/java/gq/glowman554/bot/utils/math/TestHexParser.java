package gq.glowman554.bot.utils.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHexParser {
    @Test
    public void test1() {
        assertEquals(16, HexParser.from_hex("0x10"));
    }

    @Test
    public void test2() {
        assertEquals(255, HexParser.from_hex("0xff"));
    }

    @Test
    public void test3() {
        assertEquals(57005, HexParser.from_hex("0xdead"));
    }
}
