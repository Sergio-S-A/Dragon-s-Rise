package entities.mobs;

import core.main.GameConstants;
import core.physics.Vector2D;
import entities.entity.Entity;
import graphics.AnimationManager;
import resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class Mob extends Entity {

    protected final AnimationManager animationManager;
    private final ResourceManager resourceManager;
    private final String PATH_ATTACK_FRAMES;
    private final String PATH_RUN_FRAMES;
    private final String PATH_IDLE_FRAMES;
    private final int tileSize;
    protected boolean isAttacking;
    protected boolean isMoving;

    public Mob(MobBuilder mobBuilder) {
        super(mobBuilder);
        this.animationManager = mobBuilder.animationManager;
        this.resourceManager = ResourceManager.getInstance();
        this.tileSize = mobBuilder.tileSize;
        this.PATH_IDLE_FRAMES = mobBuilder.pathIdleFrames;
        this.PATH_RUN_FRAMES = mobBuilder.pathRunFrames;
        this.PATH_ATTACK_FRAMES = mobBuilder.pathAttackFrames;
        this.isAttacking = false;
        this.isMoving = false;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D graphics2d) {
    }

    public abstract void loadAnimations();

    protected BufferedImage[][] loadAnimation(String path, int columns, int rows) {
        int width = columns * tileSize;
        int height = rows * tileSize;
        BufferedImage image = resourceManager.getImage(path, width, height);

        BufferedImage[][] animation = new BufferedImage[rows][columns];
        for (int i = 0; i < animation.length; i++) {
            for (int j = 0; j < animation[i].length; j++) {
                animation[i][j] = image.getSubimage(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }
        return animation;
    }

    protected String getPATH_ATTACK_FRAMES() {
        return PATH_ATTACK_FRAMES;
    }

    protected String getPATH_RUN_FRAMES() {
        return PATH_RUN_FRAMES;
    }

    protected String getPATH_IDLE_FRAMES() {
        return PATH_IDLE_FRAMES;
    }

    protected int getTileSize() {
        return tileSize;
    }

    public static abstract class MobBuilder<T extends MobBuilder<T>> extends EntityBuilder<T> {

        private final AnimationManager animationManager;
        private int tileSize;
        private String pathAttackFrames;
        private String pathRunFrames;
        private String pathIdleFrames;

        public MobBuilder(Vector2D position) {
            super(position);
            tileSize = GameConstants.TILE_SIZE_BIG;
            animationManager = new AnimationManager(new HashMap<>());
            pathIdleFrames = "";
            pathRunFrames = "";
            pathAttackFrames = "";
        }


        public T setTileSize(int tileSize) {
            this.tileSize = tileSize;
            return self();
        }

        public T setPathAttackFrames(String pathAttackFrames) {
            this.pathAttackFrames = pathAttackFrames;
            return self();
        }

        public T setPathRunFrames(String pathRunFrames) {
            this.pathRunFrames = pathRunFrames;
            return self();
        }

        public T setPathIdleFrames(String pathIdleFrames) {
            this.pathIdleFrames = pathIdleFrames;
            return self();
        }

        public T setAnimationSpeeds(Map<AnimationManager.AnimationState, Long> animationSpeeds) {
            this.animationManager.setAnimationSpeeds(animationSpeeds);
            return self();
        }

        protected void validateBaseMob() {
            if (tileSize <= 0)
                throw new IllegalArgumentException("tileSize must be greater than 0");
        }
    }
}
