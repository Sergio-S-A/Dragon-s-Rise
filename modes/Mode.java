package modes;

import core.main.Core;
import resources.ResourceManager;

import java.awt.*;

public abstract class Mode {

    private static Mode nowMode = null;
    protected final ResourceManager resourceManager;

    public Mode() {
        this.resourceManager = Core.getResourceManager();
    }

    public static Mode getNowMode() {
        return nowMode;
    }

    public static void changeNowMode(Mode newMode) {
        nowMode = newMode;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2d);
}
