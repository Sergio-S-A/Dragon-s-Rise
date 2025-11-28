package core.loop;

import core.config.GameConfig;
import platform.desktop.graphics.DesktopWindow;


public class GameLoop {

    private static final float DELTA_SECONDS = (float) 1 / GameConfig.FPS;
    private static final float NANOSECONDS_TO_MILLIS = 1.0f / GameConfig.ONE_MILLION;
    private final DesktopWindow desktopWindow;
    private boolean isRunning = false;
    private float deltaTime = 0;
    private long lastTime = 0;

    public GameLoop() {
        desktopWindow = new DesktopWindow();
    }

    public static float getDeltaSeconds() {
        return DELTA_SECONDS;
    }

    private void update() {
        desktopWindow.update();
    }

    private void draw() {
        desktopWindow.draw();
    }

    public void init() {
        desktopWindow.init();
        isRunning = true;
        lastTime = System.nanoTime();
        gameLoop();
    }

    private void gameLoop() {
        while (isRunning) {
            updateDeltaTime();
            if (deltaTime >= GameConfig.TIME_PER_TICK) {
                processFrame();
            } else {
                sleepIfNeeded();
            }
        }
    }

    private void updateDeltaTime() {
        long nowTime = System.nanoTime();
        deltaTime += (nowTime - lastTime);
        lastTime = nowTime;
    }

    private void processFrame() {
        deltaTime -= GameConfig.TIME_PER_TICK;
        update();
        draw();
    }

    private void sleepIfNeeded() {
        long sleepTime = (long) ((GameConfig.TIME_PER_TICK - deltaTime) * NANOSECONDS_TO_MILLIS);
        if (sleepTime >= 1) {
            try {
                Thread.sleep(sleepTime - 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
