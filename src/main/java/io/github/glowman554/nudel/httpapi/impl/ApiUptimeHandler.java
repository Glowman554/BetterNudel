package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class ApiUptimeHandler implements HttpApiHandler
{
	long startTime = System.currentTimeMillis();

	public String second_to_dhms(int seconds)
	{
		int day = (int) Math.floor(seconds / (3600 * 24));
		int hour = (int) Math.floor(seconds % (3600 * 24) / 3600);
		int minute = (int) Math.floor(seconds % 3600 / 60);
		int second = (int) Math.floor(seconds % 60);

		String day_s = day > 0 ? day + (day == 1 ? " day, " : " days, ") : "";
		String hour_s = hour > 0 ? hour + (hour == 1 ? " hour, " : " hours, ") : "";
		String minute_s = minute > 0 ? minute + (minute == 1 ? " minute, " : " minutes, ") : "";
		String second_s = second > 0 ? second + (second == 1 ? " second" : " seconds") : "";

		return day_s + hour_s + minute_s + second_s;
	}

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		Json json = Json.json();

		JsonNode root = JsonNode.object();

		root.set("uptime", System.currentTimeMillis() - startTime);
		root.set("uptime-sek", (System.currentTimeMillis() - startTime) / 1000);
		root.set("uptime-dhms", second_to_dhms((int) ((System.currentTimeMillis() - startTime) / 1000)));

		return json.serialize(root);
	}
	
}
