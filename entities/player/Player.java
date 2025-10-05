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

public class Player extends Mob {

    private final Keyboard keyboard;
    private final Vector2D isometricCenter;

    public Player(PlayerBuilder playerBuilder) {
        super(playerBuilder);
        this.keyboard = InputManager.getInstance().getKeyboard();
        this.isometricCenter = getIsometricCenter();
        loadAnimations();
    }

    private Vector2D getIsometricCenter() {
        double offsetX = GameConstants.WINDOW_WIDTH_MEDIUM;
        double offsetY = GameConstants.WINDOW_HEIGHT_QUARTER;
        return new Vector2D(offsetX - (double) getTileSize() / 2, offsetY + (double) getTileSize() / 2);
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
        BufferedImage[][] idleAnimation = loadAnimation(getPATH_IDLE_FRAMES(), getTileSize() * 4, getTileSize() * 4);
        animationManager.loadIdleAnimations(idleAnimation);

        BufferedImage[][] runAnimation = loadAnimation(getPATH_RUN_FRAMES(), getTileSize() * 8, getTileSize() * 4);
        animationManager.loadRunAnimations(runAnimation);

        BufferedImage[][] attackAnimation = loadAnimation(getPATH_ATTACK_FRAMES(), getTileSize() * 15, getTileSize() * 4);
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
        if (inputs.isKeyPressed(KeyEvent.VK_UP) || inputs.isKeyPressed(KeyEvent.VK_W)) {
            isMoving = true;
            isAttacking = false;
            return AnimationManager.Direction.NORTH;
        }
        if (inputs.isKeyPressed(KeyEvent.VK_DOWN) || inputs.isKeyPressed(KeyEvent.VK_S)) {
            isMoving = true;
            isAttacking = false;
            return AnimationManager.Direction.SOUTH;
        }
        if (inputs.isKeyPressed(KeyEvent.VK_LEFT) || inputs.isKeyPressed(KeyEvent.VK_A)) {
            isMoving = true;
            isAttacking = false;
            return AnimationManager.Direction.WEST;
        }
        if (inputs.isKeyPressed(KeyEvent.VK_RIGHT) || inputs.isKeyPressed(KeyEvent.VK_D)) {
            isMoving = true;
            isAttacking = false;
            return AnimationManager.Direction.EAST;
        }
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
