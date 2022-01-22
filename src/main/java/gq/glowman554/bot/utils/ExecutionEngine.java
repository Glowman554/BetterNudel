package gq.glowman554.bot.utils;

import gq.glowman554.bot.log.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecutionEngine {
    public boolean _safe_execution = true;

    private String[] _default_nsjail = new String[]{
            "-l 0",
            "-Mo",
            "--user 0",
            "--group 99999",
            "--chroot /",
            "-T /boot/",
            "-T /dev/",
            "-T /mnt/",
            "-T /media/",
            "-T /proc/",
            "--keep_caps"
    };

    private String[] _filter = new String[]{
            "$",
            "(",
            ")",
            "'",
            "\"",
            "|",
            "<",
            ">",
            "`",
            "\\"
    };

    public ExecutionEngine() {
    }

    public ExecutionEngine(boolean safe_execution) {
        _safe_execution = safe_execution;
    }

    public String execute(String command) throws ExecutionEngineError, IOException {
        if (_safe_execution) {
            // Check platform
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                throw new ExecutionEngineError("Windows is not supported for save execution mode!");
            } else {
                if (new File("./nsjail.elf").exists()) {
                    // check if command contains any of the filter characters
                    for (String filter : _filter) {
                        if (command.contains(filter)) {
                            throw new ExecutionEngineError("Command contains filter character: " + filter);
                        }
                    }

                    String _command = "echo \'" + command + "\' | " + new File(".").getAbsolutePath() + "/nsjail.elf " + ArrayUtils.stringify(_default_nsjail, " ") + " -- /bin/bash";

                    FileUtils.writeFile("tmp.sh", _command);

                    return _unsafe_execute("bash ./tmp.sh");
                } else {
                    Log.log("Trying to download and install nsjail...");

                    FileUtils.writeFile("nsjail.sh", "(git clone https://github.com/google/nsjail.git; cd nsjail; git checkout b09ad5e91ce8e6ffee68a1df38f23792aaf9c51f; make; cp ./nsjail ../nsjail.elf; cd ..;)");

                    _unsafe_execute("bash ./nsjail.sh");

                    Log.log("Downloaded and installed nsjail!");

                    // check if command contains any of the filter characters
                    for (String filter : _filter) {
                        if (command.contains(filter)) {
                            throw new ExecutionEngineError("Command contains filter character: " + filter);
                        }
                    }

                    String _command = "echo \'" + command + "\' | " + new File(".").getAbsolutePath() + "/nsjail.elf " + ArrayUtils.stringify(_default_nsjail, " ") + " -- /bin/bash";

                    FileUtils.writeFile("tmp.sh", _command);

                    return _unsafe_execute("bash ./tmp.sh");
                }
            }
        } else {
            return _unsafe_execute(command);
        }
    }

    private String _unsafe_execute(String command) throws ExecutionEngineError {
        try {
            Process process = Runtime.getRuntime().exec(command);

            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder _output = new StringBuilder();

            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                _output.append(output + "\n");
                Log.log(output);
            }

            return _output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutionEngineError("Error while executing command: " + command);
        }
    }

    public static class ExecutionEngineError extends Exception {
        public ExecutionEngineError(String message) {
            super(message);
        }
    }
}
