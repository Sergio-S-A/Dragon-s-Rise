package core.physics;

import entities.entity.Entity;

public abstract class PhysicsUpdater {

    private final PhysicsStrategy physicsStrategy;

    public PhysicsUpdater(PhysicsStrategy physicsStrategy) {
        this.physicsStrategy = physicsStrategy;
    }

    public abstract void update(Entity entity);

    public void updatePhysics(Entity entity) {
        physicsStrategy.applyForce(entity);
        physicsStrategy.updateAcceleration(entity);
        physicsStrategy.limitAcceleration(entity);
        physicsStrategy.updateVelocity(entity);
        physicsStrategy.limitVelocity(entity);
        physicsStrategy.updatePosition(entity);
    }
}
