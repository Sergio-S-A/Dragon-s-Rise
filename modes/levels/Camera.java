package modes.levels;

import entities.entity.Entity;

public class Camera {

    private double offsetX;
    private double offsetY;
    private int tileSize;

    public Camera(int tileSize) {
        this.tileSize = tileSize;
    }

    public void update(Entity target) {
        offsetX = (target.getPosition().x() - target.getPosition().y()) * (tileSize / 2.0);
        offsetY = (target.getPosition().x() + target.getPosition().y()) * (tileSize / 4.0);
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }
}
