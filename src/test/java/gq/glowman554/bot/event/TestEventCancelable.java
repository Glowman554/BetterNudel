package gq.glowman554.bot.event;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEventCancelable {
    boolean should_cancel = false;

    @Test
    public void test1() {
        EventManager.register(this);

        should_cancel = false;
        gq.glowman554.bot.event.impl.TestEventCancelable event = new gq.glowman554.bot.event.impl.TestEventCancelable();
        event.call();
        assertEquals(should_cancel, event.isCanceled());

        should_cancel = true;
        gq.glowman554.bot.event.impl.TestEventCancelable event2 = new gq.glowman554.bot.event.impl.TestEventCancelable();
        event2.call();
        assertEquals(should_cancel, event2.isCanceled());

        EventManager.unregister(this);

    }

    @EventTarget
    public void onTestEvent(gq.glowman554.bot.event.impl.TestEventCancelable event) {
        Log.log("Worked!");

        if (should_cancel) {
            Log.log("Canceled!");
            event.setCanceled(true);
        }
    }
}
