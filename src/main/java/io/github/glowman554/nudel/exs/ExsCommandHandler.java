package io.github.glowman554.nudel.exs;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.JsonNode;

import java.util.Map;

public class ExsCommandHandler implements HttpApiHandler
{
    private final Exs exs;

    public ExsCommandHandler(Exs exs)
    {
        this.exs = exs;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception
    {
        String ip = query.get("ip");
        if (exs.get_clients().get(ip) == null)
        {
            return "{\"msg\": \"unknown client call /api/exs/register first!\"}";
        }
        else
        {
            JsonNode root = JsonNode.array();

            for (String key : exs.command_buffer.keySet())
            {
                JsonNode node = JsonNode.object();
                node.set("key", key);
                node.set("value", exs.command_buffer.get(key));

                root.add(node);
            }

            return root.toString();
        }
    }
}
