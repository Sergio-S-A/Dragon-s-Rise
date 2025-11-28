package core.math;

public class Vector2d {

    protected float x;
    protected float y;

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(Vector2d other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public float magnitudeSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public float fastInverseSqrt(float x) {
        float xhalf = 0.5f * x;               // Guarda la mitad de x
        int i = Float.floatToIntBits(x);      // Interpreta los bits de x como un entero (IEEE 754)
        i = 0x5f3759df - (i >> 1);            // "Magia": estimación inicial mediante un truco con bits
        x = Float.intBitsToFloat(i);          // Convierte los bits de nuevo a float
        x *= (1.5f - xhalf * x * x);          // Una iteración de refinamiento (Newton-Raphson)
        return x;
    }

    public float magnitude() {
        return (float) Math.sqrt(magnitudeSquared());
    }

    public float dotProduct(Vector2d other) {
        return this.x * other.x + this.y * other.y;
    }

    public float angle() {
        return (float) Math.atan2(y, x);
    }

    public Vector2d normalize() {
        float magnitudeSquared = this.magnitudeSquared();
        if (magnitudeSquared == 0) return this;
        float invMag = fastInverseSqrt(magnitudeSquared);
        this.x *= invMag;
        this.y *= invMag;
        return this;
    }

    public Vector2d rotate(float radianAngle) {
        float cos = FastMath.cos(radianAngle);
        float sin = FastMath.sin(radianAngle);
        float tmpX = this.x;
        float tmpY = this.y;
        this.x = tmpX * cos - tmpY * sin;
        this.y = tmpX * sin + tmpY * cos;
        return this;
    }

    public Vector2d rotateTo(float angle) {
        float angleDiff = angle - this.angle();
        return this.rotate(angleDiff);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addScaled(Vector2d other, float scalar) {
        this.x += other.x * scalar;
        this.y += other.y * scalar;
    }

    public void subScaled(Vector2d other, float scalar) {
        this.x -= other.x * scalar;
        this.y -= other.y * scalar;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }
}