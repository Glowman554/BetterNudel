package io.github.glowman554.nudel.exs;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApi;
import io.github.glowman554.nudel.httpapi.HttpApiBaseHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Exs
{
    public HashMap<String, String> command_buffer = new HashMap<>();
    public HashMap<String, String> command_callback_buffer = new HashMap<>();

    public TextChannel last_command = null;

    public String config_file = "exs.json";

    public Exs(HttpApi api) throws IOException
    {
        if (!new File(config_file).exists())
        {
            System.out.println("Creating exs.json...");

            JsonNode root = JsonNode.array();
            Json json = Json.json();

            FileUtils.writeFile(config_file, json.serialize(root));
        }

        // example command system_command||ls
        //                    Seperator  ^^

        HttpApiBaseHandler exs_register_path = new HttpApiBaseHandler(new ExsRegisterHandler(this), api, "/api/exs/register");
        HttpApiBaseHandler exs_callback_path = new HttpApiBaseHandler(new ExsCallbackHandler(this), api, "/api/exs/callback");
        HttpApiBaseHandler exs_command_path = new HttpApiBaseHandler(new ExsCommandHandler(this), api, "/api/exs/command");

        Discord.discord.commandManager.addCommand("exs", new ExsCommand(this));
    }

    public HashMap<String, String> get_clients() throws IOException, JsonSyntaxException
    {
        HashMap<String, String> result = new HashMap<>();

        String config_file_content = FileUtils.readFile(config_file);
        Json json = Json.json();
        JsonNode root = json.parse(config_file_content);

        for (JsonNode node : root)
        {
            result.put(node.get("key").asString(), node.get("value").asString());
        }

        return result;
    }

    public void save_clients(HashMap<String, String> clients) throws IOException
    {
        Json json = Json.json();
        JsonNode root = JsonNode.array();

        for (String key : clients.keySet())
        {
            JsonNode node = JsonNode.object();
            node.set("key", key);
            node.set("value", clients.get(key));

            root.add(node);
        }

        FileUtils.writeFile(config_file, json.serialize(root));
    }

    public String add_client(String name) throws IOException, JsonSyntaxException
    {
        HashMap<String, String> clients = this.get_clients();

        for (String key : clients.keySet())
        {
            if (name.equals(key))
            {
                return clients.get(key);
            }
        }

        String id = String.format("exs-%d-%d", (int) (Math.random() * 1000000), (int) (Math.random() * 1000000));
        clients.put(name, id);

        this.save_clients(clients);

        return id;
    }

    public String is_client(String name) throws IOException, JsonSyntaxException
    {
        HashMap<String, String> clients = this.get_clients();

        for (String key : clients.keySet()) {
            if (name.equals(key)) {
                return clients.get(key);
            }
        }

        return null;
    }
}
