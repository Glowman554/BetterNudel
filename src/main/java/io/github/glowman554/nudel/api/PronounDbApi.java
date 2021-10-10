package io.github.glowman554.nudel.api;

import java.io.IOException;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class PronounDbApi extends BaseApi
{
	public String _pronoun;
	public String _platform = "discord";

	public String fetchPronoun(String id) throws IOException, JsonSyntaxException
	{
		try
		{
			String res = request("https://pronoundb.org/api/v1/lookup?platform=" + _platform + "&id=" + id);
		
			Json json = Json.json();
			JsonNode root = json.parse(res);
			
			String pronoun = root.get("pronouns").asString();
	
			if (pronoun == null)
			{
				pronoun = "unspecified";
			}
	
			_pronoun = pronoun;
	
			return pronoun;
		}
		catch (IOException e)
		{
			_pronoun = "unspecified";

			return _pronoun;
		}
	}

	public String translatePronoun(String pr)
	{
		switch (pr)
		{
			case "unspecified": return "Unspecified";
			case "hh": return "He/him";
			case "hi": return "He/it";
			case "hs": return "He/she";
			case "ht": return "He/they";
			case "ih": return "It/him";
			case "ii": return "It/its";
			case "ssh": return "She/he";
			case "sh": return "She/her";
			case "si": return "She/it";
			case "st": return "She/they";
			case "th": return "They/he";
			case "ti": return "They/it";
			case "ts": return "They/she";
			case "tt": return "They/them";
			case "any": return "Any pronouns";
			case "other": return "Other pronouns";
			case "ask": return "Ask me my pronouns";
			case "avoid": return "Avoid pronouns, use my name";
			default: return "Unknown: " + pr;
		}
	}

	public String translatePronoun()
	{
		return translatePronoun(_pronoun);
	}

	public void setPlatform(String platform)
	{
		_platform = platform;
	}
}
