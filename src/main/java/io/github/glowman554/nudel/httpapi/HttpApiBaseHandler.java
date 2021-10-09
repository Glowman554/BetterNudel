package io.github.glowman554.nudel.httpapi;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpContext;


public class HttpApiBaseHandler
{
	private HttpApiHandler handler;

	public HttpApiBaseHandler(HttpApiHandler handler, HttpApi api, String path)
	{
		this.handler = handler;

		HttpContext context = api.server.createContext(path);
		context.setHandler(this::handleRequest);
	}

	private void handleRequest(HttpExchange exchange) throws IOException
	{
		Map<String, String> query = HttpApi.query_to_map(exchange.getRequestURI().getQuery());

		try
		{
			String respone = handler.execute(query);

			if (respone != null)
			{
				exchange.sendResponseHeaders(200, respone.length());
				exchange.getResponseBody().write(respone.getBytes());
			}
			else
			{
				exchange.sendResponseHeaders(404, 0);
			}
		}
		catch (Exception e)
		{
			exchange.sendResponseHeaders(500, e.getMessage().length());
			exchange.getResponseBody().write(e.getMessage().getBytes());
		}

		exchange.close();
	}
}
