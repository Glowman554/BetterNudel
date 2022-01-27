package gq.glowman554.bot.utils.math;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBinParser {
    @Test
    public void test1() {
        assertEquals(16, BinParser.from_bin("0b10000"));
    }

    @Test
    public void test2() {
        assertEquals(255, BinParser.from_bin("0b11111111"));
    }

    @Test
    public void test3() {
        assertEquals(57005, BinParser.from_bin("0b1101111010101101"));
    }
}
