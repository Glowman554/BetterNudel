package gq.glowman554.modules.impl;

import gq.glowman554.modules.Module;

public class EchoCommand implements Module {
    public EchoCommand() {
    }

    public String execute(String target) {
        return target;
    }
}