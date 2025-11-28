package core.physics;

import core.math.Vector2d;

public class Force extends Vector2d {

    public Force(float x, float y) {
        super(x, y);
    }

    public void limitMagnitude(float maxForce) {
        float magnitudeSquared = this.magnitudeSquared();
        if (magnitudeSquared > maxForce * maxForce) {
            float inMag = maxForce * this.fastInverseSqrt(magnitudeSquared);
            this.scale(inMag);
        }
    }
}
