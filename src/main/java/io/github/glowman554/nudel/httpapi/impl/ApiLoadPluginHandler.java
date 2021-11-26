package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.TokenUtils;

public class ApiLoadPluginHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);

		if (query.get("url") == null)
		{
			return "Missing url";
		}

		Main.pluginsLoader.load_from_url_or_path(query.get("url"));

		return "OK";
	}
}
