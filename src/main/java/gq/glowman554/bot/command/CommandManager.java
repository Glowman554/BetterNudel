package gq.glowman554.bot.command;

import gq.glowman554.bot.log.Log;

import java.util.HashMap;

public class CommandManager {
    public final String prefix;
    public HashMap<String, Command> commands;
    public PermissionManager permissionManager;

    public CommandManager(String prefix) {
        this.prefix = prefix;
        commands = new HashMap<String, Command>();
        permissionManager = new PermissionManager("perms.json");
    }

    public void on_command(CommandEvent event) throws Exception {
        if (!event.get_command().startsWith(prefix)) {
            return;
        }

        if (event.get_command().equals(prefix + "help")) {
            switch (event.get_arguments().length) {
                case 0: {
                    var ref = new Object() {
                        String help = "";
                    };
                    ref.help += event.command_platform.format_bold("Help") + "\n";

                    commands.forEach((key, value) -> {
                        if (value.get_config().permission == null) {
                            ref.help += key + " -> " + value.get_config().help_short + "\n";
                        } else {
                            if (permissionManager.has_permission(event.get_sender_id(), value.get_config().permission)) {
                                ref.help += key + " -> " + value.get_config().help_short + "\n";
                            }
                        }
                    });

                    event.message_send(ref.help);
                }
                break;

                case 1: {
                    String _command;
                    if (!event.get_arguments()[0].startsWith(prefix)) {
                        _command = prefix + event.get_arguments()[0];
                    } else {
                        _command = event.get_arguments()[0];
                    }

                    Command command = commands.get(_command);
                    if (command != null) {
                        String help = event.command_platform.format_bold(_command + " Help") + "\n\n";
                        help += command.get_config().help_long;

                        event.message_send(help);
                    } else {
                        event.message_send("Command not found.");
                    }
                }
                break;

                default:
                    event.message_send("Too many arguments.");
                    break;
            }
        } else {
            Command command = commands.get(event.get_command());

            if (command != null) {
                String permission = command.get_config().permission;

                if (permission != null) {
                    if (!permissionManager.has_permission(event.get_sender_id(), permission)) {
                        event.message_send(event.command_platform.format_bold("Missing permission: " + permission));
                        return;
                    }
                }

                command.execute(event);
            } else {
                event.message_send("Command not found.");
            }
        }
    }

    public void add_command(String what, Command command) {
        what = prefix + what;

        command.on_register();

        commands.put(what, command);
        Log.log(String.format("[%s] Command register complete", what));
    }
}
