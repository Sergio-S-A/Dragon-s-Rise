package entities.mobs;

import core.inputs.Keyboard;
import core.main.GameConstants;
import core.physics.Vector2D;
import entities.entity.Entity;
import graphics.AnimationManager;
import resources.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Wolf extends Entity {

    private final ResourceManager resourceManager;
    private final AnimationManager animationManager;
    private final Keyboard keyboard;
    private final int TILE_SIZE;
    private boolean isMoving;
    private boolean isAttacking;
    private Vector2D isometricCenter;

    public Wolf(WolfBuilder builder) {
        super(builder);
        this.resourceManager = builder.resourceManager;
        this.animationManager = builder.animationManager;
        this.keyboard = builder.keyboard;
        this.TILE_SIZE = builder.TILE_SIZE;
        this.isMoving = false;
        this.isAttacking = false;
        loadAnimations();
        isometricCenter = getIsometricCenter();
    }

    @Override
    public void update() {
        updatePhysics();
        processInputToAttack();
        processInputToAnimate();
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

    @Override
    public void draw(Graphics2D graphics2d) {
        graphics2d.drawImage(getAnimation(), (int) isometricCenter.x(), (int) isometricCenter.y(), null);
    }

    private Vector2D getIsometricCenter() {
        double offsetX = GameConstants.WINDOW_WIDTH / 2.0;
        double offsetY = GameConstants.WINDOW_HEIGHT / 4.0;
        return new Vector2D(offsetX - TILE_SIZE / 2, offsetY + TILE_SIZE / 2);
    }

    private BufferedImage getAnimation() {
        return animationManager.getCurrentFrame();
    }

    private void loadAnimations() {
        BufferedImage[][] idleAnimation = loadAnimation("wolf/wolf-idle.png", TILE_SIZE * 4, TILE_SIZE * 4);
        animationManager.loadIdleAnimations(idleAnimation);

        BufferedImage[][] runAnimation = loadAnimation("wolf/wolf-run.png", TILE_SIZE * 8, TILE_SIZE * 4);
        animationManager.loadRunAnimations(runAnimation);

        BufferedImage attackAnimation[][] = loadAnimation("wolf/wolf-bite.png", TILE_SIZE * 15, TILE_SIZE * 4);
        animationManager.loadAttackAnimations(attackAnimation);
    }

    private BufferedImage[][] loadAnimation(String path, int width, int height) {
        BufferedImage image = resourceManager.loadImage(path, width, height);
        BufferedImage[][] animation = new BufferedImage[height / TILE_SIZE][width / TILE_SIZE];
        for (int i = 0; i < animation.length; i++) {
            for (int j = 0; j < animation[i].length; j++) {
                animation[i][j] = image.getSubimage(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        return animation;
    }

    public static class WolfBuilder extends Entity.Builder<WolfBuilder> {

        private ResourceManager resourceManager;
        private AnimationManager animationManager;
        private Keyboard keyboard;
        private int TILE_SIZE;

        public WolfBuilder(ResourceManager resourceManager) {
            super(new Vector2D(0, 0));
            this.resourceManager = resourceManager;
        }

        public WolfBuilder setTILE_SIZE(int TILE_SIZE) {
            this.TILE_SIZE = TILE_SIZE;
            return this;
        }

        public WolfBuilder setAnimationManager(AnimationManager animationManager) {
            this.animationManager = animationManager;
            return this;
        }

        public WolfBuilder setKeyboard(Keyboard keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        private void validateTileSize() {
            if (TILE_SIZE <= 0) {
                throw new IllegalArgumentException("TILE_SIZE must be greater than 0");
            }
        }

        private void validateAnimationManager() {
            if (animationManager == null) {
                throw new IllegalArgumentException("animationManager must not be null");
            }
        }

        private void validateKeyboard() {
            if (keyboard == null) {
                throw new IllegalArgumentException("keyboard must not be null");
            }
        }

        @Override
        protected WolfBuilder self() {
            return this;
        }

        public Wolf build() {
            validateTileSize();
            validateAnimationManager();
            validateKeyboard();
            return new Wolf(this);
        }

    }

}