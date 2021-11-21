import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.ChatBotApi;
import net.shadew.json.JsonSyntaxException;

public class TestChatBotApi
{
	@Test
	public void testChatBotApiSingleWord() throws IOException, JsonSyntaxException
	{
		ChatBotApi api = new ChatBotApi();

		if (api.key == null || api.bid == null)
		{
			System.out.println("API key or bid not set skipping...");
			return;
		}

		String res = api.response("Hello", "[test]");

		System.out.println(res);
	}

	@Test
	public void testChatBotApiMultipleWords() throws IOException, JsonSyntaxException
	{
		ChatBotApi api = new ChatBotApi();

		if (api.key == null || api.bid == null)
		{
			System.out.println("API key or bid not set skipping...");
			return;
		}
		
		String res = api.response("Hello world", "[test]");

		System.out.println(res);
	}
}
