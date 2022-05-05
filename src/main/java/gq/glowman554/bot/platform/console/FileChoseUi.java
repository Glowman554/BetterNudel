package gq.glowman554.bot.platform.console;

import javax.swing.*;
import java.io.File;

public class FileChoseUi extends JDialog {
    public String chose() {
        String userDirectory = System.getProperty("user.dir");
        JFileChooser jfc = new JFileChooser(userDirectory);
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    public boolean want_chose() {
        String[] options = new String[]{"ok", "cancel"};
        int x = JOptionPane.showOptionDialog(null, "Programs wants file length. Do you want to add a file?", "File request", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

        return x == 0;
    }
}
