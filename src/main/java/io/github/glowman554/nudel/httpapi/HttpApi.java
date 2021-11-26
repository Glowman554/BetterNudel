package io.github.glowman554.nudel.httpapi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class HttpApi
{
	public HttpServer server;
	public HttpContext context;

	public HashMap<String, HttpApiHandler> handlers = new HashMap<String, HttpApiHandler>();

	public HttpApi(int port) throws IOException
	{
		this.server = HttpServer.create(new InetSocketAddress(port), 0);
		server.start();
	}

	public static Map<String, String> query_to_map(String query)
	{
		if(query == null)
		{
			return new HashMap<>();
		}
		Map<String, String> result = new HashMap<>();
		for (String param : query.split("&"))
		{
			String[] entry = param.split("=");
			if (entry.length > 1)
			{
				result.put(entry[0], entry[1]);
			}
			else
			{
				result.put(entry[0], "");
			}
		}
		return result;
	}
}
