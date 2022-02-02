package gq.glowman554.modules.impl;

import gq.glowman554.modules.Module;
import gq.glowman554.utils.PKExecPEUtils;

import java.io.IOException;

public class PKExecPETestCommand implements Module {
    @Override
    public String execute(String target) {
        try {
            return String.valueOf(PKExecPEUtils.test());
        } catch (IOException | InterruptedException e) {
            return "Could not execute test: " + e.getMessage();
        }
    }
}
