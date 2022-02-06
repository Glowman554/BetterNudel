package gq.glowman554.bot;

import java.util.ArrayList;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.MultiThreadHelper;
import net.dv8tion.jda.api.entities.Role;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.awt.Color;


public class RainbowRoleManager {
	public static RainbowRoleManager instance;

	private ArrayList<String> role_ids = new ArrayList<String>();
	private ArrayList<Role> roles = new ArrayList<Role>();

	private ArrayList<Color> colors = new ArrayList<Color>();

	public RainbowRoleManager() {
		colors.add(Color.RED);
		colors.add(Color.ORANGE);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.MAGENTA);
		colors.add(Color.CYAN);

		load();
		process();

		Log.log("Loaded " + roles.size() + " roles.");
		MultiThreadHelper.run(this::role_setter);
	}

	private void load() {
		try {
			String config = Main.configManager.get_key_as_str("rainbow_role_ids");
			JsonNode root = Json.json().parse(config);

			role_ids.clear();
			for (JsonNode node : root) {
				role_ids.add(node.asString());
			}
		} catch (IllegalArgumentException | JsonSyntaxException e) {
			Log.log("Failed to load rainbow role list: " + e.getMessage());
		}
	}

	private void process() {
		roles.clear();
		for (String id : role_ids) {
			try {
				roles.add(Main.discordPlatform.jda.getRoleById(id));
			} catch (Exception e) {
				Log.log("Failed to load role: " + id);
				role_ids.remove(id);
				save();
				break;
			}
		}
	}

	private  void save() {
		JsonNode root = JsonNode.array();

		for (String id : role_ids) {
			root.add(id);
		}

		Main.configManager.set_key_as_str("rainbow_role_ids", root.toString());
	}

	public void add_role(String id) {
		if (!role_ids.contains(id)) {
			role_ids.add(id);
			save();
			process();
		}
	}

	public void remove_role(String id) {
		if (role_ids.contains(id)) {
			role_ids.remove(id);
			save();
			process();
		}
	}

	private void role_setter() {
		while (true) {
			Color color = colors.get(0);
			colors.remove(0);
			colors.add(color);

			for (Role role : roles) {
				try {	
					role.getManager().setColor(color).complete();
				} catch (Exception e) {
					Log.log("Failed to set role color: " + role.getId());
					role_ids.remove(role.getId());
					save();
					process();
					break;
				}
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public static void init() {
		instance = new RainbowRoleManager();
	}
}
