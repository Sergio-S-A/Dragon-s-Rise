package entities.player;

import core.inputs.InputManager;
import core.inputs.Keyboard;
import core.main.GameConstants;
import core.physics.Vector2D;
import entities.mobs.Mob;
import graphics.AnimationManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Mob {

    private final Keyboard keyboard;
    private final Vector2D isometricCenter;
    private final Map<Integer, AnimationManager.Direction> directionMap;

    public Player(PlayerBuilder playerBuilder) {
        super(playerBuilder);
        this.keyboard = InputManager.getInstance().getKeyboard();
        this.isometricCenter = getIsometricCenter();
        directionMap = new HashMap<>();
        loadDirectionMap();
        loadAnimations();
    }

    private void loadDirectionMap() {
        directionMap.put(KeyEvent.VK_UP, AnimationManager.Direction.NORTH);
        directionMap.put(KeyEvent.VK_DOWN, AnimationManager.Direction.SOUTH);
        directionMap.put(KeyEvent.VK_LEFT, AnimationManager.Direction.WEST);
        directionMap.put(KeyEvent.VK_RIGHT, AnimationManager.Direction.EAST);
        directionMap.put(KeyEvent.VK_W, AnimationManager.Direction.NORTH);
        directionMap.put(KeyEvent.VK_S, AnimationManager.Direction.SOUTH);
        directionMap.put(KeyEvent.VK_A, AnimationManager.Direction.WEST);
        directionMap.put(KeyEvent.VK_D, AnimationManager.Direction.EAST);
    }

    private Vector2D getIsometricCenter() {
        float offsetX = GameConstants.WINDOW_WIDTH_MEDIUM;
        float offsetY = GameConstants.WINDOW_HEIGHT_MEDIUM;
        return new Vector2D(offsetX - (float) getTileSize() / 2, offsetY - getTileSize());
    }

    @Override
    public void update() {
        updatePhysics();
        processInputToAttack();
        processInputToAnimate();
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        graphics2d.drawImage(
                animationManager.getCurrentFrame(),
                (int) isometricCenter.x(),
                (int) isometricCenter.y(),
                null
        );
    }

    @Override
    public void loadAnimations() {
        int NUM_OF_DIRECTIONS = 4;
        final int NUM_OF_ATTACK_FRAMES = 15;
        final int NUM_OF_RUN_FRAMES = 8;
        final int NUM_OF_IDLE_FRAMES = 4;

        BufferedImage[][] idleAnimation = loadAnimation(getPATH_IDLE_FRAMES(), NUM_OF_IDLE_FRAMES, NUM_OF_DIRECTIONS);
        animationManager.loadIdleAnimations(idleAnimation);

        BufferedImage[][] runAnimation = loadAnimation(getPATH_RUN_FRAMES(), NUM_OF_RUN_FRAMES, NUM_OF_DIRECTIONS);
        animationManager.loadRunAnimations(runAnimation);

        BufferedImage[][] attackAnimation = loadAnimation(getPATH_ATTACK_FRAMES(), NUM_OF_ATTACK_FRAMES, NUM_OF_DIRECTIONS);
        animationManager.loadAttackAnimations(attackAnimation);
    }


    private void processInputToAttack() {
        if (keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
            isAttacking = true;
        }
    }

    private void processInputToAnimate() {
        isMoving = false;
        animationManager.setCurrentDirection(getDirection(keyboard));
        animationManager.updateAnimation(isMoving, isAttacking);
    }

    private AnimationManager.Direction getDirection(Keyboard inputs) {
        for (Map.Entry<Integer, AnimationManager.Direction> entry : directionMap.entrySet()) {
            if (inputs.isKeyPressed(entry.getKey())) {
                isMoving = true;
                isAttacking = false;
                return entry.getValue();
            }
        }
        isMoving = false;
        isAttacking = false;
        return animationManager.getCurrentDirection();
    }

    public static class PlayerBuilder extends MobBuilder<PlayerBuilder> {

        public PlayerBuilder(Vector2D position) {
            super(position);
        }

        @Override
        protected PlayerBuilder self() {
            return this;
        }

        @Override
        public Player build() {
            validateBaseMob();
            return new Player(this);
        }
    }
}
