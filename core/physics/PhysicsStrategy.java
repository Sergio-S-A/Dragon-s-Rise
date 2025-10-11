package core.physics;

import entities.entity.Entity;

public interface PhysicsStrategy {
    void applyForce(Entity entity);

    void updateAcceleration(Entity entity);

    void limitAcceleration(Entity entity);

    void updateVelocity(Entity entity);

    void limitVelocity(Entity entity);

    void updatePosition(Entity entity);

}
