package core.physics;

import core.math.Vector2d;

public class Position extends Vector2d {

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public Position(float x, float y) {
        super(x, y);
    }

    public void updatePosition(Velocity velocity, float deltaSeconds) {
        this.addScaled(velocity, deltaSeconds);
    }

    public float distanceTo(Vector2d other) {
        float dx = this.getX() - other.getX();
        float dy = this.getY() - other.getY();
        float distSq = dx * dx + dy * dy;
        return distSq * fastInverseSqrt(distSq);
    }

    public void limitY(float minY, float maxY) {
        this.minY = minY;
        this.maxY = maxY;
    }

    public void limitX(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    public void limit() {
        this.x = Math.max(minX, Math.min(maxX, this.x));
        this.y = Math.max(minY, Math.min(maxY, this.y));
    }

}
