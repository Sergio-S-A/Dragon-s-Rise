package core.physics;


public class Vector2D {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2D sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2D scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public Vector2D normalize() {
        double magnitude = this.magnitude();
        if (magnitude == 0) {
            return this;
        }
        this.x /= magnitude;
        this.y /= magnitude;
        return this;
    }

    public Vector2D rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double tmpX = this.x;
        double tmpY = this.y;
        this.x = tmpX * cos - tmpY * sin;
        this.y = tmpX * sin + tmpY * cos;
        return this;
    }

    public Vector2D rotateTo(double angle) {
        double angleDiff = angle - this.angle();
        return this.rotate(angleDiff);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Vector2D(%.3f, %.3f)", x, y);
    }
}
