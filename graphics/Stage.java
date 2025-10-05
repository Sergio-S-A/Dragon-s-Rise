package graphics;

import core.main.GameConstants;
import modes.Mode;
import modes.menus.Menu;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Stage extends Canvas {

    private BufferStrategy bufferStrategy;

    public Stage() {
        Menu menu = new Menu();
        Mode.changeNowMode(menu);
    }

    public void update() {
        updateMode();
    }

    public void draw() {
        if (bufferStrategy == null) {
            createBufferStrategy(GameConstants.NUM_OF_BUFFERS);
            bufferStrategy = getBufferStrategy();
            return;
        }

        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        try {
            clearScreen(g2d);
            drawMode(g2d);
        } finally {
            g2d.dispose();
            bufferStrategy.show();
        }
    }

    private void clearScreen(Graphics2D graphics2d) {
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawMode(Graphics2D graphics2d) {
        if (Mode.getNowMode() != null) {
            Mode.getNowMode().draw(graphics2d);
        }
    }

    private void updateMode() {
        if (Mode.getNowMode() != null) {
            Mode.getNowMode().update();
        }
    }
}
