package gq.glowman554.modules.impl;

import gq.glowman554.modules.Module;
import gq.glowman554.utils.ExecUtils;

import java.io.IOException;

public class SystemCommand implements Module {
    public SystemCommand() {
    }

    public String execute(String target) {
        return this.run_command(target);
    }

    public String run_command(String command) {
        try {
            return ExecUtils.exec(command);
        } catch (InterruptedException | IOException e) {
            return "Could not execute command: " + e.getMessage();
        }
    }
}
