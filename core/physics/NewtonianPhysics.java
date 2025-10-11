package core.physics;

import core.main.Core;
import entities.entity.Entity;

public class NewtonianPhysics implements PhysicsStrategy {

    private final Vector2D tempVector2D;

    public NewtonianPhysics() {
        tempVector2D = new Vector2D(0, 0);
    }

    @Override
    public void applyForce(Entity entity) {
    }

    @Override
    public void updateAcceleration(Entity entity) {
        entity.getAcceleration().scale(0);
        entity.getAcceleration().addScaled(entity.getForce(), entity.getInverseMass());
    }

    @Override
    public void limitAcceleration(Entity entity) {
        if (entity.getAcceleration().magnitude() > entity.getMaxAcceleration()) {
            entity.getAcceleration().normalize();
            entity.getAcceleration().scale(entity.getMaxAcceleration());
        }
    }

    @Override
    public void updateVelocity(Entity entity) {
        entity.getVelocity().addScaled(entity.getAcceleration(), Core.getDeltaSeconds());
    }

    @Override
    public void limitVelocity(Entity entity) {
        if (entity.getVelocity().magnitude() > entity.getMaxSpeed()) {
            entity.getVelocity().normalize();
            entity.getVelocity().scale(entity.getMaxSpeed());
        }
        if (entity.getForce().magnitude() == 0) {
            applyFriction(entity);
        }
    }

    private void applyFriction(Entity entity) {
        float friction = entity.getFrictionCoefficient() * entity.getMass() * Core.getDeltaSeconds();
        float velocity = entity.getVelocity().magnitude();
        if (velocity > friction) {
            tempVector2D.set(entity.getVelocity().x(), entity.getVelocity().y());
            tempVector2D.normalize().scale(friction);
            entity.getVelocity().sub(tempVector2D);
        } else {
            entity.getVelocity().scale(0);
        }
    }

    @Override
    public void updatePosition(Entity entity) {
        entity.getPosition().addScaled(entity.getVelocity(), Core.getDeltaSeconds());
    }
}
