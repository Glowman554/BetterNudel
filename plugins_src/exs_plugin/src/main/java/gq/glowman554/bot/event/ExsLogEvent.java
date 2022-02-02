package gq.glowman554.bot.event;

public class ExsLogEvent extends Event {
	private final String text;
	private final String id;

	public ExsLogEvent(String text) {
		this.text = text;
		id = null;
	}

	public ExsLogEvent(String text, String id) {
		this.text = text;
		this.id = id;
	}

	public String get_text() {
		return text;
	}

	public String get_id() {
		return id;
	}
}
