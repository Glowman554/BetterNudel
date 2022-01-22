package gq.glowman554.bot.event;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEvent {
    boolean worked = false;
    @Test
    public void test1() {
        EventManager.register(this);
        new gq.glowman554.bot.event.impl.TestEvent().call();
        EventManager.unregister(this);

        assertTrue(worked);
    }

    @EventTarget
    public void onTestEvent(gq.glowman554.bot.event.impl.TestEvent event) {
        Log.log("Worked!");
        worked = true;
    }
}
