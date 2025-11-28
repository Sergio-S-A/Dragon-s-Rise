package core.physics;

import core.math.Vector2d;

public class Acceleration extends Vector2d {

    public Acceleration(float x, float y) {
        super(x, y);
    }

    public void fromForce(Force force, float inverseMass) {
        this.scale(0);
        this.addScaled(force, inverseMass);
    }

    public void limitMagnitude(float maxAcceleration) {
        float magnitudeSquared = this.magnitudeSquared();
        if (magnitudeSquared > maxAcceleration * maxAcceleration) {
            float inMag = maxAcceleration * this.fastInverseSqrt(magnitudeSquared);
            this.scale(inMag);
        }
    }
}
