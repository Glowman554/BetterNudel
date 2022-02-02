package gq.glowman554.bot;

import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.event.ExsCallbackEvent;
import gq.glowman554.bot.event.ExsLogEvent;
import gq.glowman554.bot.log.Log;

public class ExsLogger {
	public ExsLogger() {
		EventManager.register(this);
	}

	@EventTarget
	public void onCallback(ExsCallbackEvent event) {
		Log.log(event.toString());
	}

	@EventTarget
	public void onLog(ExsLogEvent event) {
		Log.log(String.format("[%s] %s", event.get_id(), event.get_text()));
	}
}
