package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.TokenUtils;

public class ApiCheckTokenHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);

		return "OK";
	}
	
}
