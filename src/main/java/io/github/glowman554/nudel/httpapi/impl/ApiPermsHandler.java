package io.github.glowman554.nudel.httpapi.impl;

import java.io.IOException;
import java.util.Map;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;

public class ApiPermsHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		return FileUtils.readFile(Discord.discord.commandManager.permissionManager.file);
	}
	
}
