package modes;

import resources.ResourceManager;

import java.awt.*;

public abstract class Mode {

    private static Mode nowMode = null;
    protected final ResourceManager resourceManager;

    public Mode(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public static Mode getNowMode() {
        return nowMode;
    }

    public static void changeNowMode(Mode newMode) {
        nowMode = newMode;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2d);
}
