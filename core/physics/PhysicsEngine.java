package core.physics;

import core.main.Core;
import entities.entity.Entity;
import entities.entity.PhysicsUpdater;

public class PhysicsEngine implements PhysicsUpdater {
    @Override
    public void update(Entity entity) {
        updateAcceleration(entity);
        limitAcceleration(entity);
        updateVelocity(entity);
        limitVelocity(entity);
        updatePosition(entity);
    }

    private void updateAcceleration(Entity entity) {
        entity.getAcceleration().scale(0);
        entity.getAcceleration().add(entity.getForce().copy().scale(1 / entity.getMass()));
    }

    private void limitAcceleration(Entity entity) {
        if (entity.getAcceleration().magnitude() > entity.getMaxAcceleration()) {
            entity.getAcceleration().normalize();
            entity.getAcceleration().scale(entity.getMaxAcceleration());
        }
    }

    private void updateVelocity(Entity entity) {
        entity.getVelocity().add(entity.getAcceleration().copy().scale(Core.getDeltaTime()));
    }

    private void limitVelocity(Entity entity) {
        if (entity.getVelocity().magnitude() > entity.getMaxSpeed()) {
            entity.getVelocity().normalize();
            entity.getVelocity().scale(entity.getMaxSpeed());
        }
        if (entity.getForce().magnitude() == 0) {
            applyFriction(entity);
        }
    }

    private void applyFriction(Entity entity) {
        double friction = entity.getFrictionCoefficient() * entity.getMass() * Core.getDeltaTime();
        double velocity = entity.getVelocity().magnitude();
        if (velocity > friction) {
            entity.getVelocity().sub(entity.getVelocity().copy().normalize().scale(friction));
        } else {
            entity.getVelocity().scale(0);
        }
    }

    private void updatePosition(Entity entity) {
        entity.getPosition().add(entity.getVelocity().copy().scale(Core.getDeltaTime()));
    }
}
