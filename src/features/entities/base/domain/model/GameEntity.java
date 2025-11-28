package features.entities.base.domain.model;

import core.physics.Acceleration;
import core.physics.Force;
import core.physics.Position;
import core.physics.Velocity;

public abstract class GameEntity {

    private final Position position;
    private final Velocity velocity;
    private final Acceleration acceleration;
    private final Force force;
    private float forceScale;
    private float mass;
    private float inverseMass;
    private float maxSpeed;
    private float maxAcceleration;
    private float frictionCoefficient;

    public GameEntity(EntityBuilder entityBuilder) {
        this.position = entityBuilder.position;
        this.mass = entityBuilder.mass;
        this.inverseMass = 1 / entityBuilder.mass;
        this.maxSpeed = entityBuilder.maxSpeed;
        this.maxAcceleration = entityBuilder.maxAcceleration;
        this.frictionCoefficient = entityBuilder.frictionCoefficient;
        this.velocity = new Velocity(0, 0);
        this.acceleration = new Acceleration(0, 0);
        this.force = new Force(0, 0);
        this.forceScale = entityBuilder.forceScale;
    }

    public Position getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    public Force getForce() {
        return force;
    }

    public float getForceScale() {
        return forceScale;
    }

    public void setForceScale(float forceScale) {
        this.forceScale = forceScale;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass > 0 ? mass : this.mass;
        this.inverseMass = mass > 0 ? 1 / mass : this.inverseMass;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(float maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public float getInverseMass() {
        return inverseMass;
    }

    public static abstract class EntityBuilder<T extends EntityBuilder<T>> {

        private final Position position;
        private float mass;
        private float maxSpeed;
        private float maxAcceleration;
        private float frictionCoefficient;
        private float forceScale;

        public EntityBuilder(Position position) {
            this.position = position;
            this.mass = 10;
            this.maxSpeed = 10;
            this.maxAcceleration = 10;
            this.frictionCoefficient = 0.1f;
            this.forceScale = 0.1f;
        }

        public T setMass(float mass) {
            this.mass = mass;
            return self();
        }

        public T setMaxSpeed(float maxSpeed) {
            this.maxSpeed = maxSpeed;
            return self();
        }

        public T setMaxAcceleration(float maxAcceleration) {
            this.maxAcceleration = maxAcceleration;
            return self();
        }

        public T setFrictionCoefficient(float frictionCoefficient) {
            this.frictionCoefficient = frictionCoefficient;
            return self();
        }

        public T setForceScale(float forceScale) {
            this.forceScale = forceScale;
            return self();
        }

        protected abstract T self();

        public abstract GameEntity build();
    }
}
