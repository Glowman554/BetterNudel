package io.github.glowman554.nudel.exs;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.ArrayUtils;

import java.util.HashMap;

public class ExsCommand implements Command
{
    private final Exs exs;

    public ExsCommand(Exs exs)
    {
        this.exs = exs;
    }

    @Override
    public void execute(CommandEvent event) throws Exception
    {
        exs.last_command = event.event.getTextChannel();

        if (event.args.length == 0)
        {
            event.commandFail("Please specify a command to execute!");
        }
        else
        {
            String command = ArrayUtils.stringify(event.args, " ");

            if (exs.command_buffer.size() >= 1000)
            {
                System.out.println("Clearing command buffer...");
                exs.command_buffer = new HashMap<>();
            }

            String command_callback_id = String.format("exs-cmd-%d-%d", (int) (Math.random() * 1000000), (int) (Math.random() * 1000000));
            exs.command_buffer.put(command, command_callback_id);

            HashMap<String, String> clients = exs.get_clients();
            for (String key : clients.keySet())
            {
                exs.command_callback_buffer.put(key, command_callback_id);
            }

            event.commandSuccess("Command is now in the command buffer.\nIt can take up to 30s for the client to execute!");
        }
    }

    @Override
    public void on_register()
    {

    }

    @Override
    public String get_short_help()
    {
        return "Control the exs BotNet!";
    }

    @Override
    public String get_long_help()
    {
        return String.format("Use '%sexs [command]' to control the exs BotNet!", Discord.discord.commandManager.prefix);
    }

    @Override
    public String get_permission()
    {
        return "exs";
    }
}
