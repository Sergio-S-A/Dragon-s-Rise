package core.physics;

import core.main.Core;
import entities.entity.Entity;

public class PhysicsEngine implements PhysicsUpdater {

    private final Vector2D tempVector2D = new Vector2D(0, 0);

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
        tempVector2D.set(entity.getForce().x(), entity.getForce().y());
        tempVector2D.scale(1 / entity.getMass());
        entity.getAcceleration().add(tempVector2D);
    }

    private void limitAcceleration(Entity entity) {
        if (entity.getAcceleration().magnitude() > entity.getMaxAcceleration()) {
            entity.getAcceleration().normalize();
            entity.getAcceleration().scale(entity.getMaxAcceleration());
        }
    }

    private void updateVelocity(Entity entity) {
        tempVector2D.set(entity.getAcceleration().x(), entity.getAcceleration().y());
        tempVector2D.scale(Core.getDeltaSeconds());
        entity.getVelocity().add(tempVector2D);
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
        double friction = entity.getFrictionCoefficient() * entity.getMass() * Core.getDeltaSeconds();
        double velocity = entity.getVelocity().magnitude();
        if (velocity > friction) {
            tempVector2D.set(entity.getVelocity().x(), entity.getVelocity().y());
            tempVector2D.normalize().scale(friction);
            entity.getVelocity().sub(tempVector2D);
        } else {
            entity.getVelocity().scale(0);
        }
    }

    private void updatePosition(Entity entity) {
        tempVector2D.set(entity.getVelocity().x(), entity.getVelocity().y());
        tempVector2D.scale(Core.getDeltaSeconds());
        entity.getPosition().add(tempVector2D);
    }
}
