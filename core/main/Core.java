package core.main;

import resources.ResourceManager;
import ui.GameFrame;

public class Core extends Thread {

    private static ResourceManager resourceManager = new ResourceManager();
    private static double DELTA_TIME;
    private boolean isRunning;
    private double deltaTime;
    private long nowTime;
    private long lastTime;
    private long sleepTime;
    private GameFrame gameFrame;

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

    public static double getDeltaTime() {
        return DELTA_TIME / GameConstants.ONE_MILLION;
    }

    private void update() {
        gameFrame.update();
    }

    private void draw() {
        gameFrame.draw();
    }

    public void init() {
        gameFrame.init(resourceManager);
        isRunning = true;
        lastTime = System.nanoTime();
        gameLoop();
    }

    private void gameLoop() {
        while (isRunning) {
            updateDeltaTime();
            if (deltaTime >= GameConstants.TIME_PER_TICK) {
                DELTA_TIME = deltaTime;
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
