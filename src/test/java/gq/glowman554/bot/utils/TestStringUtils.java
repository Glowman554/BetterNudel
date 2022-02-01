package gq.glowman554.bot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStringUtils {
    @Test
    public void test1() {
        String[] res = StringUtils.partition("ab\ncd\nefghj", 6);

        assertEquals("ab\ncd, \nefghj", ArrayUtils.stringify(res, ", "));
    }
}
