package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.api.IpInfoApi;
import io.github.glowman554.nudel.api.IpInfoApi.IpInfo;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.Json;

public class ApiIpinfoHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String ip = query.get("ip-override");
		if(ip == null)
		{
			ip = query.get("ip");
		}

		if(ip == null)
		{
			return "No IP specified";
		}

		IpInfo info = new IpInfoApi().request_info(ip);

		Json _json = Json.json();

		return _json.serialize(info.toJson());
	}
	
}
