package gq.glowman554.bot.command;

import gq.glowman554.bot.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPermissionManager {
    @Test
    public void test1() {
        Main.load_config("test.json");
        PermissionManager permissionManager = new PermissionManager();

        permissionManager.add_permission("test", "furry");
        permissionManager.add_permission("test", "weeb");

        assertTrue(permissionManager.has_permission("test", "furry"));
        assertFalse(permissionManager.has_permission("test", "gay"));

        assertTrue(permissionManager.has_permission("test", "weeb"));

        permissionManager.remove_permission("test", "weeb");

        assertFalse(permissionManager.has_permission("test", "weeb"));

    }
}
