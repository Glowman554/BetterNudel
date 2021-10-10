import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.JokeApi;
import net.shadew.json.JsonSyntaxException;

public class TestJokeApi
{
	@Test
	public void testJokeApi() throws IOException, JsonSyntaxException
	{
		JokeApi jokeApi = new JokeApi();
		String joke = jokeApi.getJoke();
		System.out.println(joke);
	}
}
