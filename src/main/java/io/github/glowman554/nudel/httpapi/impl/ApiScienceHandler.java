package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;

public class ApiScienceHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		return FileUtils.readFile("science.json");
	}
	
}
