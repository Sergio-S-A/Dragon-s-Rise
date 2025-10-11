package core.physics;


public class Vector2D {

    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public float magnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public float angle() {
        return (float) Math.atan2(y, x);
    }

    public Vector2D normalize() {
        float magnitude = this.magnitude();
        if (magnitude == 0) {
            return this;
        }
        this.x /= magnitude;
        this.y /= magnitude;
        return this;

    }

    public Vector2D rotate(double angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float tmpX = this.x;
        float tmpY = this.y;
        this.x = tmpX * cos - tmpY * sin;
        this.y = tmpX * sin + tmpY * cos;
        return this;
    }

    public Vector2D rotateTo(float angle) {
        float angleDiff = angle - this.angle();
        return this.rotate(angleDiff);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addScaled(Vector2D other, float scalar) {
        this.x += other.x * scalar;
        this.y += other.y * scalar;
    }

    public void subScaled(Vector2D other, float scalar) {
        this.x -= other.x * scalar;
        this.y -= other.y * scalar;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Vector2D(%.3f, %.3f)", x, y);
    }
}
