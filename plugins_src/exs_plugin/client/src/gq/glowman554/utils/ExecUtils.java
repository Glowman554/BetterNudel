package gq.glowman554.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecUtils {
    public static String exec(String command) throws IOException, InterruptedException {
        Log.log("cmd start: " + command);

        Process process = Runtime.getRuntime().exec(command);
        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String output;
        String full_output;
        for(full_output = ""; (output = bufferedReader.readLine()) != null; full_output = full_output + output + "\n") {
            Log.log("cmd: " + output);
        }

        process.waitFor();
        bufferedReader.close();
        process.destroy();
        return full_output;
    }
}
