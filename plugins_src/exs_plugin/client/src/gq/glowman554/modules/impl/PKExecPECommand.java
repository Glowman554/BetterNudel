package gq.glowman554.modules.impl;

import gq.glowman554.modules.Module;
import gq.glowman554.utils.PKExecPEUtils;

import java.io.IOException;

public class PKExecPECommand implements Module {
    @Override
    public String execute(String target) {
        try {
            return PKExecPEUtils.perform(target);
        } catch (IOException | InterruptedException e) {
            return "Could not execute command: " + e.getMessage();
        }
    }
}
