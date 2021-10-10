package io.github.glowman554.nudel.api;

import java.io.IOException;

public class FactApi extends BaseApi
{
	public String getFact() throws IOException
	{
		String fact = request("https://uselessfacts.jsph.pl/random.txt?language=en").split("\n")[0];

		if (fact.startsWith("> "))
		{
			fact = fact.substring(2);
		}

		return fact;
	}
}
