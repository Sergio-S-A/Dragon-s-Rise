package modes.levels;

import core.main.GameConstants;
import entities.entity.Entity;

public class Camera {
    private final int TILE_SIZE;
    private double offsetX;
    private double offsetY;

    public Camera(int TILE_SIZE) {
        this.TILE_SIZE = TILE_SIZE;
    }

    public void update(Entity player) {
        double playerX = player.getPosition().x();
        double playerY = player.getPosition().y();

        offsetX = (playerX - playerY) * (TILE_SIZE / 2.0) - (GameConstants.WINDOW_WIDTH_MEDIUM - TILE_SIZE / 2.0);
        offsetY = (playerX + playerY) * (TILE_SIZE / 4.0) - (GameConstants.WINDOW_HEIGHT_MEDIUM - TILE_SIZE / 2.0);
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }
}

