package core.physics;

import core.math.Vector2d;

public class Velocity extends Vector2d {

    public Velocity(float x, float y) {
        super(x, y);
    }

    public void limitMagnitude(float maxSpeed) {
        float magnitudeSquared = this.magnitudeSquared();
        if (magnitudeSquared > maxSpeed * maxSpeed) {
            float inMag = maxSpeed * this.fastInverseSqrt(magnitudeSquared);
            this.scale(inMag);
        }
    }

}
