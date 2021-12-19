package io.github.glowman554.nudel.exs;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.JsonNode;

import java.util.Map;

public class ExsRegisterHandler implements HttpApiHandler
{
    private final Exs exs;

    public ExsRegisterHandler(Exs exs)
    {
        this.exs = exs;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception
    {
        String ip = query.get("ip");

        String maybe_id = exs.is_client(ip);

        if (maybe_id != null)
        {
            JsonNode response = JsonNode.object();
            response.set("id", maybe_id);
            response.set("msg", "Client already exists!");
            return response.toString();
        }
        else
        {
            String id = exs.add_client(ip);
            JsonNode response = JsonNode.object();
            response.set("id", id);
            return response.toString();
        }
    }
}
