package core.physics;

import core.inputs.InputManager;
import core.inputs.Keyboard;
import entities.entity.Entity;
import entities.entity.PhysicsUpdater;

import java.awt.event.KeyEvent;

public class PlayerController implements PhysicsUpdater {

    private PhysicsEngine physicsEngine;

    private Keyboard keyboard;
    private double forceX;
    private double forceY;

    public PlayerController() {
        this.physicsEngine = new PhysicsEngine();
        this.keyboard = InputManager.getInstance().getKeyboard();
    }

    @Override
    public void update(Entity entity) {
        processInputToMove(entity);
        physicsEngine.update(entity);
    }

    private void processInputToMove(Entity entity) {
        forceX = 0;
        forceY = 0;
        if (keyboard.isKeyPressed(KeyEvent.VK_W) || keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            forceY = -1;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_S) || keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            forceY = 1;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_A) || keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            forceX = -1;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_D) || keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            forceX = 1;
        }
        entity.getForce().scale(0);
        entity.getForce().add(new Vector2D(forceX, forceY));
        entity.getForce().normalize().scale(entity.getForceScale());
    }
}
