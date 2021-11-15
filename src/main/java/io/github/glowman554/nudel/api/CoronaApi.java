package io.github.glowman554.nudel.api;

import java.io.IOException;
import java.util.Date;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class CoronaApi extends BaseApi
{
	public static String[] countries = new String[] {
		"germany",
		"usa",
		"italy",
		"spain",
		"france",
		"uk",
		"sweden",
		"norway",
		"denmark",
		"switzerland",
		"austria",
		"netherlands",
		"ireland",
		"poland",
		"finland",
		"serbia",
		"montenegro",
		"bulgaria",
		"greece",
		"malta",
		"cyprus",
		"macedonia",
		"slovenia",
		"russia"
	};
	public class CoronaApiResult
	{
		String country;
		int confirmed;
		int deaths;
		int recovered;
		int active;

		public CoronaApiResult(String country, int confirmed, int deaths, int recovered, int active)
		{
			this.country = country;
			this.confirmed = confirmed;
			this.deaths = deaths;
			this.recovered = recovered;
			this.active = active;
		}

		@Override
		public String toString()
		{
			String res = "";
	
			res += "Country: " + country + "\n";
			res += "Confirmed: " + confirmed + "\n";
			res += "Deaths: " + deaths + "\n";
			res += "Recovered: " + recovered + "\n";
			res += "Active: " + active + "\n";
	
			return res;
		}
	}

	public CoronaApiResult fetchCountry(String country) throws IOException, JsonSyntaxException
	{
		String res = request("https://disease.sh/v3/covid-19/countries/" + country);

		Json json = Json.json();
		JsonNode root = json.parse(res);

		String countryName = root.get("country").asString();
		int confirmed = root.get("cases").asInt();
		int deaths = root.get("deaths").asInt();
		int recovered = root.get("recovered").asInt();
		int active = root.get("active").asInt();

		CoronaApiResult result = new CoronaApiResult(countryName, confirmed, deaths, recovered, active);

		return result;
	}
}
