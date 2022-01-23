package gq.glowman554.bot.plugin;

public class PluginLoadException extends Exception {
    public PluginLoadException(Exception e) {
        super("Could not load plugin because of a " + e.getClass().getSimpleName() + " exception! Message: " + e.getMessage());
    }
}
