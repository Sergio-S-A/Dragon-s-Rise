package entities.entity;

import core.physics.PhysicsUpdater;
import core.physics.Vector2D;

import java.awt.*;

public abstract class Entity {

    private final PhysicsUpdater physicsUpdater;
    private final Vector2D position;
    private final Vector2D velocity;
    private final Vector2D acceleration;
    private final Vector2D force;
    private float forceScale;
    private float mass;
    private float inverseMass;
    private float maxSpeed;
    private float maxAcceleration;
    private float frictionCoefficient;

    public Entity(EntityBuilder entityBuilder) {
        this.position = entityBuilder.position;
        this.mass = entityBuilder.mass;
        this.inverseMass = 1 / entityBuilder.mass;
        this.maxSpeed = entityBuilder.maxSpeed;
        this.maxAcceleration = entityBuilder.maxAcceleration;
        this.physicsUpdater = entityBuilder.physicsUpdater;
        this.frictionCoefficient = entityBuilder.frictionCoefficient;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.force = new Vector2D(0, 0);
        this.forceScale = entityBuilder.forceScale;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2d);

    public void updatePhysics() {
        if (physicsUpdater != null) {
            physicsUpdater.update(this);
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public Vector2D getForce() {
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

        private final Vector2D position;
        private float mass = 1;
        private float maxSpeed = 1;
        private float maxAcceleration = 1;
        private float frictionCoefficient = 0.1f;
        private float forceScale = 0.1f;
        private PhysicsUpdater physicsUpdater;

        public EntityBuilder(Vector2D position) {
            this.position = position;
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

        public T setPhysicsUpdater(PhysicsUpdater physicsUpdater) {
            this.physicsUpdater = physicsUpdater;
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

        public abstract Entity build();
    }
}
