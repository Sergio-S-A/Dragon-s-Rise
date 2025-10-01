package core.math;

import core.inputs.InputManager;
import core.inputs.Keyboard;
import core.main.Core;
import entities.entity.Entity;
import entities.entity.PhysicsUpdater;

import java.awt.event.KeyEvent;

public class PhysicsClasicToPlayer implements PhysicsUpdater {

    private Keyboard keyboard;

    public PhysicsClasicToPlayer() {
        this.keyboard = InputManager.getInstance().getKeyboard();
    }

    @Override
    public void update(Entity entity, double deltaTime) {
        processInputToMove(entity);
        updateAcceleration(entity);
        limitAcceleration(entity);
        updateVelocity(entity);
        limitVelocity(entity);
        updatePosition(entity);
        entity.getForce().scale(0);
    }

    private void processInputToMove(Entity entity) {
        entity.getVelocity().scale(0);
        if (keyboard.isKeyPressed(KeyEvent.VK_W) || keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            entity.getVelocity().add(new Vector2D(0, -entity.getForceScale()));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_S) || keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            entity.getVelocity().add(new Vector2D(0, entity.getForceScale()));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_A) || keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            entity.getVelocity().add(new Vector2D(-entity.getForceScale(), 0));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_D) || keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            entity.getVelocity().add(new Vector2D(entity.getForceScale(), 0));
        }
    }

    private void updateAcceleration(Entity entity) {
        entity.getAcceleration().add(entity.getForce().scale(1 / entity.getMass()));
    }

    private void limitAcceleration(Entity entity) {
        if (entity.getAcceleration().magnitude() > entity.getMaxAcceleration()) {
            entity.getAcceleration().normalize();
        }
    }

    private void updateVelocity(Entity entity) {
        entity.getVelocity().add(entity.getAcceleration().scale(Core.getDeltaTime()));
    }

    private void limitVelocity(Entity entity) {
        if (entity.getVelocity().magnitude() > entity.getMaxSpeed()) {
            entity.getVelocity().normalize().scale(entity.getMaxSpeed());
        }
    }

    private void updatePosition(Entity entity) {
        entity.getPosition().add(entity.getVelocity().scale(Core.getDeltaTime()));
    }
}
