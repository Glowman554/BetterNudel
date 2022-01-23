package gq.glowman554.bot.utils;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTimeUtils {
    @Test
    public void test1() {
        assertEquals("1 hour, 10 minutes", TimeUtils.millisecond_to_dhms(4200000));
    }

    @Test
    public void test2() {
        assertEquals("4 days, 20 hours, 40 minutes", TimeUtils.second_to_dhms(420000));
    }
}
