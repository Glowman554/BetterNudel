import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.WikipediaApi;
import net.shadew.json.JsonSyntaxException;

public class TestWikipediaApi
{
	@Test
	public void testWikipediaApi() throws IOException, JsonSyntaxException
	{
		WikipediaApi api = new WikipediaApi();
		String res = api.searchWiki("CPU");
		System.out.println(res);

		String res2 = api.searchWiki("world war 2");
		System.out.println(res2);
	}
}
