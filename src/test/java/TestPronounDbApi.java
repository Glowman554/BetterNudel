import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.PronounDbApi;
import net.shadew.json.JsonSyntaxException;

public class TestPronounDbApi
{
	@Test
	public void testPronounDbApi() throws IOException, JsonSyntaxException
	{
		PronounDbApi api = new PronounDbApi();

		String pronoun = api.fetchPronoun("584344177257480192");
		System.out.println(pronoun);

		assertEquals("hh", pronoun);

		String translated_pronoun = api.translatePronoun();
		System.out.println(translated_pronoun);

		assertEquals("He/him", translated_pronoun);
	}

	@Test
	public void testPronounDbApiPlatform() throws IOException, JsonSyntaxException
	{
		PronounDbApi api = new PronounDbApi();

		api.setPlatform("twitch");

		String pronoun = api.fetchPronoun("255883366");
		System.out.println(pronoun);

		assertEquals("hh", pronoun);

		String translated_pronoun = api.translatePronoun();
		System.out.println(translated_pronoun);

		assertEquals("He/him", translated_pronoun);
	}

	@Test
	public void testPronounDbApiNonexistent() throws IOException, JsonSyntaxException
	{
		PronounDbApi api = new PronounDbApi();

		String pronoun = api.fetchPronoun("584344177257483192");
		System.out.println(pronoun);

		assertEquals("unspecified", pronoun);

		String translated_pronoun = api.translatePronoun();
		System.out.println(translated_pronoun);

		assertEquals("Unspecified", translated_pronoun);
	}
}
