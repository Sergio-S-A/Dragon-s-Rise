package core.main;

import resources.ResourceManager;
import ui.GameFrame;

public class Core {

    private static final ResourceManager resourceManager = new ResourceManager();
    private static double deltaSeconds = (double) 1 / GameConstants.FPS;
    private final GameFrame gameFrame;
    private boolean isRunning;
    private double deltaTime;
    private long nowTime;
    private long lastTime;
    private long sleepTime;

    public Core() {
        isRunning = false;
        deltaTime = 0;
        nowTime = 0;
        lastTime = 0;
        sleepTime = 0;
        gameFrame = new GameFrame();
    }

    public static ResourceManager getResourceManager() {
        return resourceManager;
    }

    public static double getDeltaSeconds() {
        return deltaSeconds;
    }

    private void update() {
        gameFrame.update();
    }

    private void draw() {
        gameFrame.draw();
    }

    public void init() {
        gameFrame.init();
        isRunning = true;
        lastTime = System.nanoTime();
        gameLoop();
    }

    private void gameLoop() {
        while (isRunning) {
            updateDeltaTime();
            if (deltaTime >= GameConstants.TIME_PER_TICK) {
                processFrame();
            } else {
                sleepIfNeeded();
            }
        }
    }

    private void updateDeltaTime() {
        nowTime = System.nanoTime();
        deltaTime += (nowTime - lastTime);
        lastTime = nowTime;
    }

    private void processFrame() {
        deltaTime -= GameConstants.TIME_PER_TICK;
        update();
        draw();
    }

    private void sleepIfNeeded() {
        sleepTime = (long) (
                (GameConstants.TIME_PER_TICK - deltaTime) / GameConstants.ONE_MILLION
        );
        if (sleepTime >= 1) {
            try {
                Thread.sleep(sleepTime - 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
