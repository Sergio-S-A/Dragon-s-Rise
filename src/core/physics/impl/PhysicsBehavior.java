package core.physics.impl;

import core.physics.PhysicsSystem;
import features.entities.base.domain.model.GameEntity;

public abstract class PhysicsBehavior {

    private final PhysicsSystem physicsSystem;

    public PhysicsBehavior(PhysicsSystem physicsSystem) {
        this.physicsSystem = physicsSystem;
    }

    public abstract void update(GameEntity gameEntity);

    public void updatePhysics(GameEntity gameEntity, float deltaSeconds) {
        physicsSystem.applyForce(gameEntity);
        physicsSystem.updateAcceleration(gameEntity);
        physicsSystem.limitAcceleration(gameEntity);
        physicsSystem.updateVelocity(gameEntity, deltaSeconds);
        physicsSystem.limitVelocity(gameEntity, deltaSeconds);
        physicsSystem.updatePosition(gameEntity, deltaSeconds);
    }
}
