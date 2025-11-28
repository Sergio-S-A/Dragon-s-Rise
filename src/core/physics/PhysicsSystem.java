package core.physics;

import features.entities.base.domain.model.GameEntity;

public interface PhysicsSystem {
    void applyForce(GameEntity gameEntity);

    void updateAcceleration(GameEntity gameEntity);

    void limitAcceleration(GameEntity gameEntity);

    void updateVelocity(GameEntity gameEntity, float deltaSeconds);

    void limitVelocity(GameEntity gameEntity, float deltaSeconds);

    void updatePosition(GameEntity gameEntity, float deltaSeconds);

}
