package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class RoleCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Manage roles!", String.format("Use '%srole [add,remove,list] [user?] [role?]' to manage roles.", Main.commandManager.prefix), "role");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length < 1) {
            event.message_send("Expected at least 1 arguments");
        } else {
            switch (event.get_arguments()[0]) {
                case "add": {
                    if (event.get_arguments().length != 3 || event.get_mention_ids().length != 1) {
                        event.message_send("Expected 3 arguments");
                    } else {
                        String user = event.get_mention_ids()[0];
                        String user_text = event.get_arguments()[1];
                        String role = event.get_arguments()[2];

                        Main.commandManager.permissionManager.add_permission(user, role);

                        event.message_send("Added " + user_text + " to " + role);
                    }
                }
                break;

                case "remove": {
                    if (event.get_arguments().length != 3 || event.get_mention_ids().length != 1) {
                        event.message_send("Expected 3 arguments");
                    } else {
                        String user = event.get_mention_ids()[0];
                        String user_text = event.get_arguments()[1];
                        String role = event.get_arguments()[2];

                        Main.commandManager.permissionManager.remove_permission(user, role);

                        event.message_send("Removed " + user_text + " from " + role);
                    }
                }
                break;

                case "list": {
                    if (event.get_arguments().length != 2 || event.get_mention_ids().length != 1) {
                        event.message_send("Expected 2 arguments");
                    } else {
                        String user = event.get_mention_ids()[0];
                        String user_text = event.get_arguments()[1];

                        StringBuilder sb = new StringBuilder();
                        sb.append("Permissions for " + user_text + ": ");

                        for (String role : Main.commandManager.permissionManager.get_permissions(user)) {
                            sb.append(role + ", ");
                        }

                        sb.deleteCharAt(sb.length() - 2);

                        event.message_send(sb.toString());
                    }
                }
                break;

                default: {
                    event.message_send("Unknown command");
                }
                break;
            }
        }
    }

    @Override
    public void on_register() {

    }
}
