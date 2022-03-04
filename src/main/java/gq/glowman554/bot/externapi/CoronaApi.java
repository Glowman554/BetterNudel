package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class CoronaApi {
    public static String[] countries = new String[]{
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

    public CoronaApiResult fetchCountry(String country) throws IOException, JsonSyntaxException {
        String res = HttpClient.get("https://disease.sh/v3/covid-19/countries/" + country);

        Json json = Json.json();
        JsonNode root = json.parse(res);

        if (root.get("message") != null && root.get("message").asString().equals("Country not found or doesn't have any cases")) {
            throw new IllegalArgumentException();
        }

        String countryName = root.get("country").asString();
        int confirmed = root.get("cases").asInt();
        int deaths = root.get("deaths").asInt();
        int recovered = root.get("recovered").asInt();
        int active = root.get("active").asInt();

        return new CoronaApiResult(countryName, confirmed, deaths, recovered, active);
    }

    public static class CoronaApiResult {
        String country;
        int confirmed;
        int deaths;
        int recovered;
        int active;

        public CoronaApiResult(String country, int confirmed, int deaths, int recovered, int active) {
            this.country = country;
            this.confirmed = confirmed;
            this.deaths = deaths;
            this.recovered = recovered;
            this.active = active;
        }

        @Override
        public String toString() {
            String res = "";

            res += "Country: " + country + "\n";
            res += "Confirmed: " + confirmed + "\n";
            res += "Deaths: " + deaths + "\n";
            res += "Recovered: " + recovered + "\n";
            res += "Active: " + active + "\n";

            return res;
        }
    }
}
