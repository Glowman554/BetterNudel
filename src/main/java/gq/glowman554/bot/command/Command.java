package gq.glowman554.bot.command;

public interface Command {
    CommandConfig get_config();
    void execute(CommandEvent event) throws Exception;
    void on_register();
}
