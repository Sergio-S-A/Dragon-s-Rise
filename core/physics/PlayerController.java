package core.physics;

import core.inputs.InputManager;
import core.inputs.Keyboard;
import entities.entity.Entity;

import java.awt.event.KeyEvent;

public class PlayerController extends PhysicsUpdater {


    private final Keyboard keyboard;
    private float forceX;
    private float forceY;

    public PlayerController(PhysicsStrategy physicsStrategy) {
        super(physicsStrategy);
        this.keyboard = InputManager.getInstance().getKeyboard();
    }

    @Override
    public void update(Entity entity) {
        processInputToMove(entity);
        updatePhysics(entity);
    }

    private void processInputToMove(Entity entity) {
        forceX = 0;
        forceY = 0;
        for (InputDirection inputDirection : InputDirection.values()) {
            if (inputDirection.isKeyPressed(keyboard)) {
                forceX += inputDirection.forceX;
                forceY += inputDirection.forceY;
            }
        }
        entity.getForce().set(forceX, forceY);
        entity.getForce().normalize().scale(entity.getForceScale());
    }

    public enum InputDirection {
        NORTH(KeyEvent.VK_W, KeyEvent.VK_UP, 0, -1),
        SOUTH(KeyEvent.VK_S, KeyEvent.VK_DOWN, 0, 1),
        WEST(KeyEvent.VK_A, KeyEvent.VK_LEFT, -1, 0),
        EAST(KeyEvent.VK_D, KeyEvent.VK_RIGHT, 1, 0);

        private final int key1, key2;
        private final int forceX, forceY;

        InputDirection(int key1, int key2, int forceX, int forceY) {
            this.key1 = key1;
            this.key2 = key2;
            this.forceX = forceX;
            this.forceY = forceY;
        }

        private boolean isKeyPressed(Keyboard keyboard) {
            return keyboard.isKeyPressed(key1) || keyboard.isKeyPressed(key2);
        }
    }
}
