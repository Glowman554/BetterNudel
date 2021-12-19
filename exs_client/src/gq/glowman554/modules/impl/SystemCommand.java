package gq.glowman554.modules.impl;

import gq.glowman554.modules.Module;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemCommand implements Module {
    public SystemCommand() {
    }

    public String execute(String target) {
        return this.run_command(target);
    }

    public String run_command(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output = null;

            String full_output;
            for(full_output = ""; (output = bufferedReader.readLine()) != null; full_output = full_output + output + "\n") {
            }

            process.waitFor();
            bufferedReader.close();
            process.destroy();
            return full_output;
        } catch (InterruptedException | IOException e) {
            return "Could not execute command: " + e.getMessage();
        }
    }
}
