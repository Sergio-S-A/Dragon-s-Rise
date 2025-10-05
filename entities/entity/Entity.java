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
    protected double forceScale;
    private double mass;
    private double maxSpeed;
    private double maxAcceleration;
    private double frictionCoefficient;

    public Entity(EntityBuilder entityBuilder) {
        this.position = entityBuilder.position;
        this.mass = entityBuilder.mass;
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

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setFrictionCoefficient(double frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public double getForceScale() {
        return forceScale;
    }

    public void setForceScale(double forceScale) {
        this.forceScale = forceScale;
    }

    public static abstract class EntityBuilder<T extends EntityBuilder<T>> {

        private final Vector2D position;
        private double mass = 1;
        private double maxSpeed = 1;
        private double maxAcceleration = 1;
        private double frictionCoefficient = 0.1;
        private double forceScale = 0.01;
        private PhysicsUpdater physicsUpdater;

        public EntityBuilder(Vector2D position) {
            this.position = position;
        }

        public T setMass(double mass) {
            this.mass = mass;
            return self();
        }

        public T setMaxSpeed(double maxSpeed) {
            this.maxSpeed = maxSpeed;
            return self();
        }

        public T setMaxAcceleration(double maxAcceleration) {
            this.maxAcceleration = maxAcceleration;
            return self();
        }

        public T setPhysicsUpdater(PhysicsUpdater physicsUpdater) {
            this.physicsUpdater = physicsUpdater;
            return self();
        }

        public T setFrictionCoefficient(double frictionCoefficient) {
            this.frictionCoefficient = frictionCoefficient;
            return self();
        }

        public T setForceScale(double forceScale) {
            this.forceScale = forceScale;
            return self();
        }

        protected abstract T self();

        public abstract Entity build();
    }
}
