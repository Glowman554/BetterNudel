package gq.glowman554.bot.event;

public class ExsCallbackEvent extends Event {
	private final String callback_id;
	private final String client_id;
	private final String text;

	public ExsCallbackEvent(String callback_id, String client_id, String text) {
		this.callback_id = callback_id;
		this.client_id = client_id;
		this.text = text;
	}

	public String getCallbackId() {
		return callback_id;
	}

	public String getClientId() {
		return client_id;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return String.format("ExsCallbackEvent [callback_id=%s, client_id=%s, text=%s]", callback_id, client_id, text);
	}
}
