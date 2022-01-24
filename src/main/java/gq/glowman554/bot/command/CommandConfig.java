package gq.glowman554.bot.command;

import net.shadew.json.JsonNode;

public class CommandConfig {
    public final String help_short;
    public final String help_long;
    public final String permission;

    public CommandConfig(String help_short, String help_long, String permission) {
        this.help_short = help_short;
        this.help_long = help_long;
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "CommandConfig{" +
                "help_short='" + help_short + '\'' +
                ", help_long='" + help_long + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }

    public JsonNode toJson() {
        JsonNode root = JsonNode.object();

        root.set("help_long", help_long);
        root.set("help_short", help_short);
        root.set("permission", permission);

        return root;
    }
}
