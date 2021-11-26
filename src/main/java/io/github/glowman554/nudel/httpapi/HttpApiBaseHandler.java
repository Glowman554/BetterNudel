package io.github.glowman554.nudel.httpapi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import io.github.glowman554.nudel.Main;

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
		System.out.println("Request: " + exchange.getRequestMethod() + " " + exchange.getRequestURI() + " " + exchange.getProtocol() + " " + exchange.getRequestHeaders().getFirst("X-Forwarded-For") + " " + exchange.getRemoteAddress().getAddress().getHostAddress());

		String request_uri = Main.http_host_path + exchange.getRequestURI().toString();

		if (new File(request_uri).isDirectory())
		{
			if (new File(request_uri + "/index.html").exists())
			{
				System.out.println("Adding index.html to request");
				request_uri += "/index.html";
			}
		}

		if (new File(request_uri).exists() && new File(request_uri).isFile())
		{
			if (request_uri.contains("!!hidden!!"))
			{
				System.out.println("Requested file is hidden!!");
				exchange.sendResponseHeaders(403, 0);
				exchange.getResponseBody().write("403 Forbidden".getBytes());
				exchange.getResponseBody().close();
				return;
			}
			else
			{
				System.out.printf("Sending file: %s\n", request_uri);
				exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
				exchange.sendResponseHeaders(200, 0);
				InputStream s = new File(request_uri).toURI().toURL().openStream();
				exchange.getResponseBody().write(s.readAllBytes());
				s.close();
				exchange.getResponseBody().close();
				exchange.close();
				return;
			}
		}

		Map<String, String> query = HttpApi.query_to_map(exchange.getRequestURI().getQuery());

		String ip = exchange.getRemoteAddress().getAddress().getHostAddress();
		String ip_forwarded = exchange.getRequestHeaders().getFirst("X-Forwarded-For");

		if (ip_forwarded != null)
		{
			query.put("ip", ip_forwarded);
		}
		else
		{
			query.put("ip", ip);
		}

		query.put("user_agent", exchange.getRequestHeaders().getFirst("User-Agent"));

		try
		{
			String respone = handler.execute(query);

			if (respone != null)
			{
				exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
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
