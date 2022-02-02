package gq.glowman554.bot.command;

import java.util.ArrayList;
import java.util.HashMap;

import gq.glowman554.bot.Exs;
import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;

public class ExsCommand implements Command {
	private final Exs exs;

	public ExsCommand(Exs exs) {
		this.exs = exs;
	}

	@Override
	public void execute(CommandEvent event) throws Exception {
		exs.last_command = event;
		
		if (event.get_arguments().length == 0) {
			event.message_send("Missing arguments!");
		} else {
			String command = ArrayUtils.stringify(event.get_arguments(), " ");

			if (exs.command_buffer.size() >= 1000) {
				Log.log("Clearing command buffer...");
				exs.command_buffer = new HashMap<>();
			}

			String command_callback_id = String.format("exs-cmd-%d-%d", (int) (Math.random() * 1000000), (int) (Math.random() * 1000000));
			exs.command_buffer.put(command, command_callback_id);

			ArrayList<String> clients = exs.get_clients();
			for (String client : clients) {
				exs.command_callback_buffer.put(client, command_callback_id);
			}
		}
	}

	@Override
	public CommandConfig get_config() {
		return new CommandConfig("Control the exs BotNet!", String.format("Use '%sexs [command]' to control the exs BotNet!", Main.commandManager.prefix), "exs");
	}

	@Override
	public void on_register() {
	}
	
}
