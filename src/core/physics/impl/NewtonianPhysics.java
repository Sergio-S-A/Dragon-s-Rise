package core.physics.impl;

import core.physics.PhysicsSystem;
import features.entities.base.domain.model.GameEntity;

public class NewtonianPhysics implements PhysicsSystem {

    public NewtonianPhysics() {
    }

    @Override
    public void applyForce(GameEntity gameEntity) {
    }

    @Override
    public void updateAcceleration(GameEntity gameEntity) {
        gameEntity.getAcceleration().fromForce(gameEntity.getForce(), gameEntity.getInverseMass());
    }

    @Override
    public void limitAcceleration(GameEntity gameEntity) {
        gameEntity.getAcceleration().limitMagnitude(gameEntity.getMaxAcceleration());
    }

    @Override
    public void updateVelocity(GameEntity gameEntity, float deltaSeconds) {
        gameEntity.getVelocity().addScaled(gameEntity.getAcceleration(), deltaSeconds);
    }

    @Override
    public void limitVelocity(GameEntity gameEntity, float deltaSeconds) {
        gameEntity.getVelocity().limitMagnitude(gameEntity.getMaxSpeed());
        if (gameEntity.getForce().getX() == 0 && gameEntity.getForce().getY() == 0) {
            applyFriction(gameEntity, deltaSeconds);
        }
    }

    private void applyFriction(GameEntity gameEntity, float deltaSeconds) {
        float friction = gameEntity.getFrictionCoefficient() * gameEntity.getMass() * deltaSeconds;
        float velocity = gameEntity.getVelocity().magnitude();
        if (velocity > friction) {
            gameEntity.getVelocity().scale((velocity - friction) / velocity);
        } else {
            gameEntity.getVelocity().scale(0);
        }
    }

    @Override
    public void updatePosition(GameEntity gameEntity, float deltaSeconds) {
        gameEntity.getPosition().updatePosition(gameEntity.getVelocity(), deltaSeconds);
    }
}
