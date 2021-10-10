import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.FactApi;

public class TestFactApi
{
	@Test
	public void testFactApi() throws IOException
	{
		FactApi factApi = new FactApi();
		String fact = factApi.getFact();
		System.out.println(fact);
	}
}
