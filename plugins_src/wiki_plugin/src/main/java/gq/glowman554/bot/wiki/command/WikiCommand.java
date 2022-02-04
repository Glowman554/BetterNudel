package gq.glowman554.bot.wiki.command;


import java.io.File;
import java.net.URLDecoder;
import java.util.Date;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.WikiPlugin;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.FileUtils;
import gq.glowman554.bot.wiki.PageManager;
import gq.glowman554.bot.wiki.Page;
import net.shadew.json.JsonNode;

public class WikiCommand implements Command {

	@Override
	public void execute(CommandEvent event) throws Exception {
		if (!(event.get_arguments().length == 1 || event.get_arguments().length == 2)) {
			event.message_send("Expected 1 or 2 arguments!");
		} else {
			String sub_command = event.get_arguments()[0];

			switch (sub_command) {
				case "changelog": {
					if (event.get_arguments().length != 1) {
						event.message_send("Expected 1 argument!");
						break;
					}

					JsonNode root = WikiPlugin.pageChangeLogHandlerInstance.toJson();

					StringBuilder sb = new StringBuilder();

					for (JsonNode node : root) {
						sb.append(node.get("what").asString()).append(" at ").append(new Date(node.get("when").asLong()).toString()).append("\n");
					}

					event.message_send(sb.toString());
				}
				break;

				case "list": {
					if (event.get_arguments().length != 1) {
						event.message_send("Expected 1 argument!");
						break;
					}

					StringBuilder sb = new StringBuilder();

					for (String page : PageManager.instance.known_pages) {
						sb.append(new URLDecoder().decode(PageManager.instance.load(page).page_title)).append(" -> ").append(page).append("\n");
					}

					event.message_send(sb.toString());
				}
				break;

				case "get": {
					if (event.get_arguments().length != 2) {
						event.message_send("Expected 1 argument!");
						break;
					}

					String _page = event.get_arguments()[1];
					Page page = PageManager.instance.load(_page);

					String file = FileUtils.randomTmpFile("md");
					FileUtils.writeFile(file, new URLDecoder().decode(page.page_text));

					event.send_file(new File(file));
				}
				break;

				default: {
					event.message_send("Unknown command!");
				}
				break;
			}
		}
	}

	@Override
	public CommandConfig get_config() {
		return new CommandConfig("Access the wiki", String.format("Use '%swiki [get/list/changelog][page_id?]' to access the wiki!", Main.commandManager.prefix), null);
	}

	@Override
	public void on_register() {
	}
}
