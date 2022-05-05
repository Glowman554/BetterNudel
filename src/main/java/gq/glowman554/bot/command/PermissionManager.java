package gq.glowman554.bot.command;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.utils.ArrayUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class PermissionManager {
    public String file_content;

    public PermissionManager() {
        this.load();
    }

    public boolean has_permission(String user, String permission) {
        try {
            JsonNode root = Json.json().parse(file_content);

            if (root.has(user)) {
                JsonNode user_node = root.get(user);
                String[] permissions = user_node.asStringArray();

                return ArrayUtils.contains(permissions, permission);
            } else {
                JsonNode user_node = JsonNode.stringArray();
                root.set(user, user_node);

                this.file_content = root.toString();
                this.save();
                return false;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] get_permissions(String user) {
        try {
            JsonNode root = Json.json().parse(file_content);

            if (root.has(user)) {
                JsonNode user_node = root.get(user);
                String[] permissions = user_node.asStringArray();

                return permissions;
            } else {
                JsonNode user_node = JsonNode.stringArray();
                root.set(user, user_node);

                this.file_content = root.toString();
                this.save();
                return new String[]{};
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    public void add_permission(String user, String permission) {
        try {
            JsonNode root = Json.json().parse(file_content);

            if (root.has(user)) {
                JsonNode user_node = root.get(user);
                String[] permissions = user_node.asStringArray();

                if (!ArrayUtils.contains(permissions, permission)) {
                    permissions = ArrayUtils.add(permissions, permission);
                    user_node = JsonNode.stringArray(permissions);
                    root.set(user, user_node);
                    this.file_content = root.toString();
                    this.save();
                }
            } else {
                JsonNode user_node = JsonNode.stringArray(permission);
                root.set(user, user_node);
                this.file_content = root.toString();
                this.save();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public void remove_permission(String user, String permission) {
        try {
            JsonNode root = Json.json().parse(file_content);

            if (root.has(user)) {
                JsonNode user_node = root.get(user);
                String[] permissions = user_node.asStringArray();

                if (ArrayUtils.contains(permissions, permission)) {
                    permissions = ArrayUtils.remove(permissions, permission);
                    user_node = JsonNode.stringArray(permissions);
                    root.set(user, user_node);
                    this.file_content = root.toString();
                    this.save();
                }
            } else {
                JsonNode user_node = JsonNode.stringArray();
                root.set(user, user_node);
                this.file_content = root.toString();
                this.save();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            file_content = Main.configManager.get_key_as_str("permission_config");
        } catch (IllegalArgumentException e) {
            file_content = "{}";
            save();
        }
    }

    private void save() {
        Main.configManager.set_key_as_str("permission_config", file_content);
    }
}