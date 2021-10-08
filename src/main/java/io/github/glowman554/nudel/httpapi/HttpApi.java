package io.github.glowman554.nudel.httpapi;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class HttpApi
{
	public HttpServer server;
	public HttpContext context;

	public HttpApi(int port) throws IOException
	{
		this.server = HttpServer.create(new InetSocketAddress(port), 0);
		this.context = server.createContext("/");
		context.setHandler(this::handleRequest);
		server.start();
	}

	public Map<String, String> query_to_map(String query)
	{
		if(query == null)
		{
			return null;
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

	private void handleRequest(HttpExchange exchange) throws IOException
	{
		// Map<String, String> query = this.query_to_map(exchange.getRequestURI().getQuery());

		String response = "Hello world!";

		exchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
