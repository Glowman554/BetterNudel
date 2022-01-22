package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class IpInfoApi {
    public class IpInfo {
        public String ip;
        public String hostname;
        public String city;
        public String region;
        public String country;
        public String loc;
        public String postal;

        public IpInfo(String ip, String hostname, String city, String region, String country, String loc, String postal) {
            this.ip = ip;
            this.hostname = hostname;
            this.city = city;
            this.region = region;
            this.country = country;
            this.loc = loc;
            this.postal = postal;
        }

        @Override
        public String toString() {
            return "IpInfo [ip=" + ip + ", hostname=" + hostname + ", city=" + city + ", region=" + region + ", country=" + country + ", loc=" + loc + ", postal=" + postal + "]";
        }

        public JsonNode toJson() {
            JsonNode root = JsonNode.object();
            root.set("ip", ip);
            root.set("hostname", hostname);
            root.set("city", city);
            root.set("region", region);
            root.set("country", country);
            root.set("loc", loc);
            root.set("postal", postal);
            return root;
        }
    }

    public IpInfo request_info(String ip) throws IOException, JsonSyntaxException {
        String raw_data = HttpClient.request("https://ipinfo.io/" + ip);

        Json _json = Json.json();
        JsonNode root = _json.parse(raw_data);

        String ip_address = root.get("ip").asString();
        String hostname = root.get("hostname").asString();
        String city = root.get("city").asString();
        String region = root.get("region").asString();
        String country = root.get("country").asString();
        String loc = root.get("loc").asString();
        String postal = root.get("postal").asString();

        return new IpInfo(ip_address, hostname, city, region, country, loc, postal);
    }
}
