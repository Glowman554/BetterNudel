package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import io.github.glowman554.nudel.utils.TokenUtils;

public class ApiScienceV2Handler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);
		
		return FileUtils.readFile("sciencev2.json");
	}
	
}
